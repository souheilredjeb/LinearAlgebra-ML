package com.linsolve.illustrated.wirebond.model;

import java.util.List;

public class RegressionResult {
    private final double[] coefficients;  // [β0, β1, β2]
    private final List<Double> predictedValues;
    private final List<Double> residuals;
    private final double rSquared;
    private final String method;
    private final long computationTimeMs;

    public RegressionResult(double[] coefficients, List<Double> predictedValues, 
                           List<Double> residuals, double rSquared, 
                           String method, long computationTimeMs) {
        this.coefficients = coefficients;
        this.predictedValues = predictedValues;
        this.residuals = residuals;
        this.rSquared = rSquared;
        this.method = method;
        this.computationTimeMs = computationTimeMs;
    }

    public double[] getCoefficients() { return coefficients; }
    public List<Double> getPredictedValues() { return predictedValues; }
    public List<Double> getResiduals() { return residuals; }
    public double getRSquared() { return rSquared; }
    public String getMethod() { return method; }
    public long getComputationTimeMs() { return computationTimeMs; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(method).append(" Results ===\n");
        sb.append(String.format("β0 = %.5f\n", coefficients[0]));
        sb.append(String.format("β1 = %.5f\n", coefficients[1]));
        sb.append(String.format("β2 = %.5f\n", coefficients[2]));
        sb.append(String.format("R² = %.4f\n", rSquared));
        sb.append(String.format("Time: %d ms\n", computationTimeMs));
        return sb.toString();
    }
}