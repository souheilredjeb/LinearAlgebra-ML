package com.linsolve.illustrated.wirebond.service;



import com.linsolve.illustrated.wirebond.model.RegressionResult;
import com.linsolve.illustrated.wirebond.model.WireBondData;
import com.linsolve.illustrated.wirebond.model.WireBondData.DataPoint;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

public class SparkRegressionService implements RegressionService {
    
    private SparkSession spark;
    
    public SparkRegressionService() {
        this.spark = SparkSession.builder()
            .appName("WireBondRegression")
            .master("local[*]")
            .config("spark.sql.adaptive.enabled", "false")
            .config("spark.ui.enabled", "false")
            .getOrCreate();
    }
    
    @Override
    public RegressionResult computeRegression(WireBondData data) {
        long startTime = System.currentTimeMillis();
        
        try {
            // Create Spark DataFrame
            List<Row> rows = new ArrayList<>();
            for (DataPoint dp : data.getDataPoints()) {
                rows.add(RowFactory.create(
                    dp.getY(),
                    Vectors.dense(dp.getX1(), dp.getX2())
                ));
            }
            
            // Option 1: Using VectorUDT
            StructType schema = new StructType()
                .add("label", DataTypes.DoubleType, false)
                .add("features", new VectorUDT(), false);
            
            Dataset<Row> df = spark.createDataFrame(rows, schema);
            
            // Fit linear regression model
            LinearRegression lr = new LinearRegression()
                .setFeaturesCol("features")
                .setLabelCol("label")
                .setPredictionCol("prediction");
            
            LinearRegressionModel model = lr.fit(df);
            
            // Extract coefficients
            double[] coefficients = model.coefficients().toArray();
            double intercept = model.intercept();
            
            // Build full coefficient array [β0, β1, β2]
            double[] fullCoefficients = new double[3];
            fullCoefficients[0] = intercept;
            fullCoefficients[1] = coefficients[0];
            fullCoefficients[2] = coefficients[1];
            
            // Get predictions
            Dataset<Row> predictions = model.transform(df);
            List<Row> predictionRows = predictions.collectAsList();
            
            List<Double> predictedValues = new ArrayList<>();
            List<Double> residuals = new ArrayList<>();
            double sumY = 0.0;
            
            for (int i = 0; i < predictionRows.size(); i++) {
                Row row = predictionRows.get(i);
                double y = row.getDouble(0);
                double y_pred = row.getDouble(2);
                predictedValues.add(y_pred);
                residuals.add(y - y_pred);
                sumY += y;
            }
            
            // Compute R²
            double meanY = sumY / data.getDataPoints().size();
            double ssTot = 0.0;
            double ssRes = 0.0;
            for (int i = 0; i < predictedValues.size(); i++) {
                double y = data.getDataPoints().get(i).getY();
                ssTot += Math.pow(y - meanY, 2);
                ssRes += Math.pow(residuals.get(i), 2);
            }
            double rSquared = 1.0 - (ssRes / ssTot);
            
            long endTime = System.currentTimeMillis();
            
            System.out.println("=== Spark Regression Results ===");
            System.out.println("Intercept (β0): " + intercept);
            System.out.println("Coefficients: " + java.util.Arrays.toString(coefficients));
            System.out.println("R²: " + rSquared);
            System.out.println("Time: " + (endTime - startTime) + " ms");
            
            return new RegressionResult(fullCoefficients, predictedValues, residuals, 
                                       rSquared, getMethodName(), endTime - startTime);
            
        } catch (Exception e) {
            e.printStackTrace();
            long endTime = System.currentTimeMillis();
            return new RegressionResult(new double[3], new ArrayList<>(), 
                                       new ArrayList<>(), 0.0, 
                                       getMethodName() + " (Error)", 
                                       endTime - startTime);
        }
    }
    
    @Override
    public String getMethodName() {
        return "Apache Spark";
    }
    
    public void close() {
        if (spark != null) {
            spark.close();
        }
    }
}