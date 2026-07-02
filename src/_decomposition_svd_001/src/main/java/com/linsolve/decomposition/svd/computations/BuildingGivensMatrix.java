package com.linsolve.decomposition.svd.computations;

import com.linsolve.decomposition.svd.components.Identity;
import com.linsolve.decomposition.svd.components.Matrix;
import com.linsolve.decomposition.svd.components.basicoperations.AddingMatrices;
import com.linsolve.decomposition.svd.components.basicoperations.MultiplyingMatrices;
import com.linsolve.decomposition.svd.components.basicoperations.MultiplyingMatrixByScalar;
import com.linsolve.decomposition.svd.components.basicoperations.TransposingMatrix;
import com.linsolve.decomposition.svd.components.helper.GeneratingSymetricMatrix;


public class BuildingGivensMatrix {
    
    private Matrix A;             
    private Matrix G;             
    private Matrix V;              
    private Matrix Lambda;         
    private Matrix Sigma;         
    private Matrix U;             
    private int m;                
    private int n;                 
    private double tolerance;
    private int maxIterations;

    public BuildingGivensMatrix(Matrix A, double tolerance, int maxIterations) {
        this.A = A;
        this.m = A.getM();
        this.n = A.getN();
        this.tolerance = tolerance;
        this.maxIterations = maxIterations;
        
        // Step 1: Form Gram matrix G = A^T A
        Matrix A_T = TransposingMatrix.transposing(A);
        this.G = MultiplyingMatrices.multiplyMatrix(A_T, A);
        
        // Initialize V as identity
        this.V = new Identity(n);
    }
    
    // ================================================================
    // STEP 1: JACOBI METHOD ON GRAM MATRIX G
    // ================================================================
    public void jacobiOnGramMatrix() {
        int iter = 0;
        
        while (iter < maxIterations) {
            // Find largest off-diagonal
            ComputingMaximumCoefficientOffdiag cmcoff = 
                new ComputingMaximumCoefficientOffdiag(this.G);
            cmcoff.apply();
            
            double maxOffDiag = cmcoff.getMax_coefficient();
            int p = cmcoff.getP();
            int q = cmcoff.getQ();
            
            // Check convergence
            if (Math.abs(maxOffDiag) < tolerance) {
                System.out.println("✅ Converged after " + iter + " iterations");
                break;
            }
            
            // Apply Jacobi rotation
            applyJacobiRotation(p, q);
            iter++;
        }
        
        // Store eigenvalues
        this.Lambda = this.G;
        
        System.out.println("Eigenvalues of G = A^T A:");
        Matrix.displayMatrix(this.Lambda);
    }
    
    // ================================================================
    // STEP 2: APPLY JACOBI ROTATION
    // ================================================================
    private void applyJacobiRotation(int p, int q) {
        // Construct canonical vectors ep, eq
        Matrix ep = new Matrix(n, 1);
        Matrix eq = new Matrix(n, 1);
        Double[][] t_ep = new Double[n][1];
        Double[][] t_eq = new Double[n][1];
        
        for (int i = 0; i < n; i++) {
            t_ep[i][0] = (i == p) ? 1.0 : 0.0;
            t_eq[i][0] = (i == q) ? 1.0 : 0.0;
        }
        ep.setT(t_ep);
        eq.setT(t_eq);
        
        // Compute rotation angle using atan2 (stable)
        double a_pp = this.G.getT()[p][p];
        double a_qq = this.G.getT()[q][q];
        double a_pq = this.G.getT()[p][q];
        
        double theta = 0.5 * Math.atan2(2 * a_pq, a_qq - a_pp);
        double c = Math.cos(theta);
        double s = Math.sin(theta);
        
        // Build Givens rotation matrix J
        // J = I - (1-c)(ep*ep^T + eq*eq^T) + s(ep*eq^T - eq*ep^T)
        Matrix ep_epT = MultiplyingMatrices.multiplyMatrix(ep, TransposingMatrix.transposing(ep));
        Matrix eq_eqT = MultiplyingMatrices.multiplyMatrix(eq, TransposingMatrix.transposing(eq));
        Matrix ep_eqT = MultiplyingMatrices.multiplyMatrix(ep, TransposingMatrix.transposing(eq));
        Matrix eq_epT = MultiplyingMatrices.multiplyMatrix(eq, TransposingMatrix.transposing(ep));
        
        Matrix sum1 = AddingMatrices.add(ep_epT, eq_eqT);
        Matrix term1 = MultiplyingMatrixByScalar.multiplyByScalar(sum1, (1.0 - c));
        term1 = MultiplyingMatrixByScalar.multiplyByScalar(term1, -1.0);
        
        Matrix diff = AddingMatrices.add(ep_eqT, MultiplyingMatrixByScalar.multiplyByScalar(eq_epT, -1.0));
        Matrix term2 = MultiplyingMatrixByScalar.multiplyByScalar(diff, s);
        
        Identity I = new Identity(n);
        Matrix J = AddingMatrices.add(AddingMatrices.add(I, term1), term2);
        
        // Apply similarity transformation: G = J^T * G * J
        Matrix J_T = TransposingMatrix.transposing(J);
        this.G = MultiplyingMatrices.multiplyMatrix(J_T, this.G);
        this.G = MultiplyingMatrices.multiplyMatrix(this.G, J);
        
        // Update eigenvector matrix: V = V * J
        this.V = MultiplyingMatrices.multiplyMatrix(this.V, J);
    }
    
    // ================================================================
    // STEP 3: COMPUTE SINGULAR VALUES
    // ================================================================
    public void computeSingularValues() {
        int rank = 0;
        Double[][] sigmaData = new Double[m][n];
        
        // Initialize sigma with zeros
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sigmaData[i][j] = 0.0;
            }
        }
        
        // Compute singular values from eigenvalues
        for (int i = 0; i < n; i++) {
            double lambda = this.Lambda.getT()[i][i];
            double sigma = Math.sqrt(Math.max(lambda, 0.0)); // Ensure non-negative
            
            if (sigma > tolerance) {
                rank++;
                sigmaData[i][i] = sigma;
            }
        }
        
        this.Sigma = new Matrix(m, n);
        this.Sigma.setT(sigmaData);
        
        System.out.println("Rank of A: " + rank);
        System.out.println("Singular Values:");
        for (int i = 0; i < n; i++) {
            if (sigmaData[i][i] > 0) {
                System.out.println("σ[" + i + "] = " + sigmaData[i][i]);
            }
        }
    }
    
    // ================================================================
    // STEP 4: COMPUTE LEFT SINGULAR VECTORS U
    // ================================================================
    public void computeLeftSingularVectors() {
        Double[][] uData = new Double[m][m];
        
        // Initialize U with zeros
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                uData[i][j] = 0.0;
            }
        }
        
        // Compute u_i = (A * v_i) / σ_i for σ_i > 0
        int col = 0;
        for (int i = 0; i < n; i++) {
            double sigma = this.Sigma.getT()[i][i];
            
            if (sigma > tolerance) {
                // Extract v_i (i-th column of V)
                Matrix v_i = new Matrix(n, 1);
                Double[][] vData = new Double[n][1];
                for (int j = 0; j < n; j++) {
                    vData[j][0] = this.V.getT()[j][i];
                }
                v_i.setT(vData);
                
                // Compute A * v_i
                Matrix Av_i = MultiplyingMatrices.multiplyMatrix(this.A, v_i);
                
                // u_i = (A * v_i) / σ_i
                for (int j = 0; j < m; j++) {
                    uData[j][col] = Av_i.getT()[j][0] / sigma;
                }
                col++;
            }
        }
        
        // If m > n, extend U to an orthonormal basis using Gram-Schmidt
        if (col < m) {
            gramSchmidtExtension(uData, col);
        }
        
        this.U = new Matrix(m, m);
        this.U.setT(uData);
        
        System.out.println("Left singular vectors U:");
        Matrix.displayMatrix(this.U);
    }
    
    // ================================================================
    // STEP 5: GRAM-SCHMIDT EXTENSION (for m > n)
    // ================================================================
    private void gramSchmidtExtension(Double[][] uData, int startCol) {
        int m = this.m;
        
        for (int i = startCol; i < m; i++) {
            // Start with a random vector
            double[] w = new double[m];
            for (int j = 0; j < m; j++) {
                w[j] = Math.random() - 0.5;
            }
            
            // Orthogonalize against previous columns
            for (int k = 0; k < i; k++) {
                double dot = 0.0;
                for (int j = 0; j < m; j++) {
                    dot += w[j] * uData[j][k];
                }
                for (int j = 0; j < m; j++) {
                    w[j] -= dot * uData[j][k];
                }
            }
            
            // Normalize
            double norm = 0.0;
            for (int j = 0; j < m; j++) {
                norm += w[j] * w[j];
            }
            norm = Math.sqrt(norm);
            
            if (norm > tolerance) {
                for (int j = 0; j < m; j++) {
                    uData[j][i] = w[j] / norm;
                }
            }
        }
    }

    public void verifySVD() {
        Matrix V_T = TransposingMatrix.transposing(this.V);
        Matrix USigma = MultiplyingMatrices.multiplyMatrix(this.U, this.Sigma);
        Matrix A_reconstructed = MultiplyingMatrices.multiplyMatrix(USigma, V_T);  
        System.out.println("\n=== SVD Verification ===");
        System.out.println("Original A:");
        Matrix.displayMatrix(this.A);
        System.out.println("Reconstructed A = U * Sigma * V^T:");
        Matrix.displayMatrix(A_reconstructed);
      
        double error = 0.0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double diff = this.A.getT()[i][j] - A_reconstructed.getT()[i][j];
                error += diff * diff;
            }
        }
        error = Math.sqrt(error);
        System.out.println("Reconstruction error: " + error);
        
        if (error < 1e-10) {
            System.out.println("✅ SVD verified successfully!");
        } else {
            System.out.println("⚠️ SVD verification error: " + error);
        }
    }
    public void computeSVD() {
        System.out.println("=== Computing SVD using Jacobi Method ===");
        System.out.println("Matrix size: " + m + " × " + n);
        System.out.println("Tolerance: " + tolerance);
        System.out.println("\n--- Step 1: Jacobi on G = A^T A ---");
        jacobiOnGramMatrix();
        System.out.println("\n--- Step 2: Singular Values ---");
        computeSingularValues();
        System.out.println("\n--- Step 3: Left Singular Vectors ---");
        computeLeftSingularVectors();
        System.out.println("\n--- Step 4: Verification ---");
        verifySVD();
        
        System.out.println("\n=== SVD Complete ===");
        System.out.println("A = U · Σ · V^T");
        System.out.println("U: " + m + " × " + m);
        System.out.println("Σ: " + m + " × " + n);
        System.out.println("V: " + n + " × " + n);
    }
    
    // ================================================================
    // GETTERS
    // ================================================================
    public Matrix getU() { return U; }
    public Matrix getSigma() { return Sigma; }
    public Matrix getV() { return V; }
    public Matrix getLambda() { return Lambda; }
    
    // ================================================================
    // MAIN METHOD - EXAMPLE
    // ================================================================
    public static void main(String[] args) {
        // Example: Create a random 5x5 symmetric matrix
        int n = 5;
        Matrix A = GeneratingSymetricMatrix.generateSymetricMatrix(n);
        
        System.out.println("Original Symmetric Matrix A:");
        Matrix.displayMatrix(A);
        
        // Compute SVD
        double tolerance = 1e-10;
        int maxIterations = 1000;
        BuildingGivensMatrix svd = new BuildingGivensMatrix(A, tolerance, maxIterations);
        svd.computeSVD();
        
        // Print results
        System.out.println("\n=== Final Results ===");
        System.out.println("Eigenvalues (Lambda):");
        Matrix.displayMatrix(svd.getLambda());
        System.out.println("Right Singular Vectors (V):");
        Matrix.displayMatrix(svd.getV());
        System.out.println("Singular Values (Sigma):");
        Matrix.displayMatrix(svd.getSigma());
        System.out.println("Left Singular Vectors (U):");
        Matrix.displayMatrix(svd.getU());
    }
}