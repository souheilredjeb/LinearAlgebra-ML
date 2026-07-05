package com.linsolve.illustrated.wirebond.controller;

import com.linsolve.illustrated.wirebond.model.RegressionResult;
import com.linsolve.illustrated.wirebond.model.WireBondData;
import com.linsolve.illustrated.wirebond.service.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class RegressionController {
    
    @FXML private TableView<ResultRow> resultsTable;
    @FXML private TableColumn<ResultRow, String> methodColumn;
    @FXML private TableColumn<ResultRow, Double> beta0Column;
    @FXML private TableColumn<ResultRow, Double> beta1Column;
    @FXML private TableColumn<ResultRow, Double> beta2Column;
    @FXML private TableColumn<ResultRow, Double> rSquaredColumn;
    @FXML private TableColumn<ResultRow, Long> timeColumn;
    
    @FXML private TextArea detailsArea;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;
    @FXML private Button computeButton;
    
    @FXML private ScatterChart<Number, Number> scatterChart;
    @FXML private LineChart<Number, Number> lineChart;
    
    private WireBondData data;
    private List<RegressionResult> results;
    private List<RegressionService> services;
    
    @FXML
    public void initialize() {
        data = new WireBondData();
        results = new ArrayList<>();
        
        // Initialize services
        services = new ArrayList<>();
        services.add(new QRRegressionService());
        services.add(new SVDRegressionService());
        services.add(new SparkRegressionService());
        
        // Setup table columns
        methodColumn.setCellValueFactory(new PropertyValueFactory<>("method"));
        beta0Column.setCellValueFactory(new PropertyValueFactory<>("beta0"));
        beta1Column.setCellValueFactory(new PropertyValueFactory<>("beta1"));
        beta2Column.setCellValueFactory(new PropertyValueFactory<>("beta2"));
        rSquaredColumn.setCellValueFactory(new PropertyValueFactory<>("rSquared"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        
        // Setup charts
        setupCharts();
    }
    
    private void setupCharts() {
        scatterChart.setTitle("Pull Strength vs Wire Length");
        scatterChart.getXAxis().setLabel("Wire Length");
        scatterChart.getYAxis().setLabel("Pull Strength");
        
        lineChart.setTitle("Predicted vs Actual");
        lineChart.getXAxis().setLabel("Observation");
        lineChart.getYAxis().setLabel("Pull Strength");
    }
    
    @FXML
    private void handleCompute() {
        computeButton.setDisable(true);
        progressBar.setProgress(0.0);
        statusLabel.setText("Computing...");
        
        new Thread(() -> {
            results.clear();
            int total = services.size();
            
            for (int i = 0; i < total; i++) {
                RegressionService service = services.get(i);
                try {
                    RegressionResult result = service.computeRegression(data);
                    results.add(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Add error result
                    results.add(new RegressionResult(
                        new double[3], new ArrayList<>(), new ArrayList<>(), 
                        0.0, service.getMethodName() + " (ERROR)", 0
                    ));
                }
                
                final double progress = (double) (i + 1) / total;
                Platform.runLater(() -> progressBar.setProgress(progress));
            }
            
            Platform.runLater(() -> {
                updateUI();
                computeButton.setDisable(false);
                statusLabel.setText("Complete!");
            });
        }).start();
    }
    
    private void updateUI() {
        // Update table
        ObservableList<ResultRow> tableData = FXCollections.observableArrayList();
        for (RegressionResult result : results) {
            double[] coeff = result.getCoefficients();
            tableData.add(new ResultRow(
                result.getMethod(),
                coeff.length > 0 ? coeff[0] : 0.0,
                coeff.length > 1 ? coeff[1] : 0.0,
                coeff.length > 2 ? coeff[2] : 0.0,
                result.getRSquared(),
                result.getComputationTimeMs()
            ));
        }
        resultsTable.setItems(tableData);
        
        // Update details
        StringBuilder details = new StringBuilder();
        for (RegressionResult result : results) {
            details.append(result.toString());
            details.append("\n");
        }
        detailsArea.setText(details.toString());
        
        // Update charts
        updateScatterChart();
        updateLineChart();
    }
    
    private void updateScatterChart() {
        scatterChart.getData().clear();
        
        // Find best result (highest R²)
        RegressionResult bestResult = null;
        double bestR2 = -1;
        for (RegressionResult result : results) {
            if (result.getRSquared() > bestR2) {
                bestR2 = result.getRSquared();
                bestResult = result;
            }
        }
        
        if (bestResult != null) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(bestResult.getMethod());
            
            List<WireBondData.DataPoint> points = data.getDataPoints();
            for (int i = 0; i < points.size(); i++) {
                series.getData().add(new XYChart.Data<>(
                    points.get(i).getX1(),
                    bestResult.getPredictedValues().get(i)
                ));
            }
            scatterChart.getData().add(series);
        }
    }
    
    private void updateLineChart() {
        lineChart.getData().clear();
        
        // Plot predicted values for each method
        for (RegressionResult result : results) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(result.getMethod());
            
            List<Double> predictions = result.getPredictedValues();
            for (int i = 0; i < predictions.size(); i++) {
                series.getData().add(new XYChart.Data<>(i + 1, predictions.get(i)));
            }
            lineChart.getData().add(series);
        }
    }
    
    @FXML
    private void handleExit() {
        // Clean up Spark
        for (RegressionService service : services) {
            if (service instanceof SparkRegressionService) {
                ((SparkRegressionService) service).close();
            }
        }
        Platform.exit();
    }
    
    // Table row class
    public static class ResultRow {
        private final String method;
        private final Double beta0;
        private final Double beta1;
        private final Double beta2;
        private final Double rSquared;
        private final Long time;
        
        public ResultRow(String method, Double beta0, Double beta1, Double beta2, 
                        Double rSquared, Long time) {
            this.method = method;
            this.beta0 = beta0;
            this.beta1 = beta1;
            this.beta2 = beta2;
            this.rSquared = rSquared;
            this.time = time;
        }
        
        public String getMethod() { return method; }
        public Double getBeta0() { return beta0; }
        public Double getBeta1() { return beta1; }
        public Double getBeta2() { return beta2; }
        public Double getRSquared() { return rSquared; }
        public Long getTime() { return time; }
    }
}