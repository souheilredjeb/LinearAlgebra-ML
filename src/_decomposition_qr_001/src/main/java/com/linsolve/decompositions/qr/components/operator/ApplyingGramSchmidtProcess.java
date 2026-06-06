package com.linsolve.decompositions.qr.components.operator;

import com.linsolve.decompositions.qr.components.Matrix;
import com.linsolve.decompositions.qr.components.Identity;
import com.linsolve.decompositions.qr.components.basicoperations.MultiplyingMatrices;
import com.linsolve.decompositions.qr.components.basicoperations.TransposingMatrix;
import com.linsolve.decompositions.qr.components.elementaryoperations.Ci_lamda_Ci;
import com.linsolve.decompositions.qr.components.elementaryoperations.Ci_plus_lamda_Cj;
import com.linsolve.decompositions.qr.helper.GeneratingRandomMatrix;
import com.linsolve.decompositions.qr.helper.InvertingUpperMatrix;

public class ApplyingGramSchmidtProcess {
	
	private Ci_plus_lamda_Cj ci_plus_lamda_cj;
	private Ci_lamda_Ci ci_lamda_ci;
	private int index;
	private int m;
	private int n;
	private Matrix initialMatrix;
	private Matrix finalComputedMatrix;
	private Matrix finalGeneratedMatrix;
	private Double dot;
	private Double norm;	
	private GeneratingRandomMatrix supplier;
	private Matrix Q_1;	
	private Matrix R_1;
	private Matrix Q;
	private Matrix R;
	private InvertingUpperMatrix ium;
	
	public ApplyingGramSchmidtProcess(int index_random, int m, int n) {
		super();
		this.m=m;
		this.n=n;
		this.initialMatrix=new Matrix(m, n);			
		this.initialMatrix.setT(supplier.supplyRandomMatrix(m, index_random).getT());	
		this.finalGeneratedMatrix=new Matrix(m,n);
		this.finalGeneratedMatrix.setT(this.initialMatrix.getT());
		this.finalComputedMatrix=new Identity(n);
		this.Q_1=new Matrix(m, m);
		this.R_1=new Matrix(n, n);
		this.Q=new Matrix(m, m);
		this.R=new Matrix(n,n);
		this.ium=new InvertingUpperMatrix();
	}
	
	public void apply(int j)
	{
		 if(j==0)
			{
				dot=0.0;
				norm=0.0;
				for(int i=0; i<initialMatrix.getM();i++)
				{
					norm=norm+initialMatrix.getT()[i][j]*initialMatrix.getT()[i][j];
				}
				norm=Math.sqrt(norm);
				dot=1.0/norm;
				ci_lamda_ci=new Ci_lamda_Ci(initialMatrix.getN(), dot, j);
				this.finalGeneratedMatrix=MultiplyingMatrices.multiplyMatrix(this.finalGeneratedMatrix,ci_lamda_ci);
		    	this.finalComputedMatrix=MultiplyingMatrices.multiplyMatrix(this.finalComputedMatrix, ci_lamda_ci);
			}	
		    else
		    {
			    for(int k=0;k<j;k++)
			    {
					dot=0.0;
			    	for(int i=0;i<initialMatrix.getM();i++)
			    	{
			    		dot+=finalGeneratedMatrix.getT()[i][k]*initialMatrix.getT()[i][j];
			    	} 	
			    	ci_plus_lamda_cj= new Ci_plus_lamda_Cj(initialMatrix.getN(), -dot,j, k);
					finalGeneratedMatrix=MultiplyingMatrices.multiplyMatrix(finalGeneratedMatrix, ci_plus_lamda_cj);
					finalComputedMatrix=MultiplyingMatrices.multiplyMatrix(finalComputedMatrix, ci_plus_lamda_cj);
					
			    }
			    norm=0.0;
				for(int i=0;i<finalGeneratedMatrix.getM();i++)// we normalize vector from column k to column j-1
				{
					norm+=finalGeneratedMatrix.getT()[i][j]*finalGeneratedMatrix.getT()[i][j];
				}
				norm=Math.sqrt(norm);
				ci_lamda_ci=new Ci_lamda_Ci(initialMatrix.getN(), 1.0/norm, j);
				finalGeneratedMatrix=MultiplyingMatrices.multiplyMatrix(finalGeneratedMatrix,ci_lamda_ci);
		    	finalComputedMatrix=MultiplyingMatrices.multiplyMatrix(finalComputedMatrix, ci_lamda_ci);
		    	this.R_1=this.finalComputedMatrix;
		   }	
	}
	
	public void apply()
	{
		for(int j=0;j<this.n;j++)
		{
			apply(j);
		}
	}
	
	public void construct_qr()
	{
		
		this.R_1=this.finalComputedMatrix;
		this.Q_1=TransposingMatrix.transposing(this.getFinalGeneratedMatrix());
		
		Matrix inv= new Matrix(m,n);
		inv.setT(MultiplyingMatrices.multiplyMatrix(this.R_1, this.Q_1).getT());
		
		Matrix around_id=new Matrix(m,n);
		around_id.setT(MultiplyingMatrices.multiplyMatrix(inv, this.initialMatrix).getT());
		
		this.ium= new InvertingUpperMatrix(this.R_1);
		this.ium.apply();
		this.R=this.ium.getInverse();
		this.Q=this.getFinalGeneratedMatrix();
    	System.out.println("Matrix Q");
    	Matrix.displayMatrix(this.Q);
		System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println("Matrix R");
    	Matrix.displayMatrix(this.R);
		System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println("Initial Matrix");
    	Matrix.displayMatrix(this.initialMatrix);
		System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println("Product QR");
    	Matrix.displayMatrix(MultiplyingMatrices.multiplyMatrix(this.Q, this.R));
		
	}

	public static void main(String[] args) {
		int m=5;
		int n=5;
		ApplyingGramSchmidtProcess gramschmid=new ApplyingGramSchmidtProcess(10, m, n);
		gramschmid.apply();
    	gramschmid.construct_qr();
	}

	public Ci_plus_lamda_Cj getCi_plus_lamda_cj() {
		return ci_plus_lamda_cj;
	}

	public void setCi_plus_lamda_cj(Ci_plus_lamda_Cj ci_plus_lamda_cj) {
		this.ci_plus_lamda_cj = ci_plus_lamda_cj;
	}

	public Ci_lamda_Ci getCi_lamda_ci() {
		return ci_lamda_ci;
	}

	public void setCi_lamda_ci(Ci_lamda_Ci ci_lamda_ci) {
		this.ci_lamda_ci = ci_lamda_ci;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Matrix getInitialMatrix() {
		return initialMatrix;
	}

	public void setInitialMatrix(Matrix initialMatrix) {
		this.initialMatrix = initialMatrix;
	}

	public Matrix getFinalComputedMatrix() {
		return finalComputedMatrix;
	}

	public void setFinalComputedMatrix(Matrix finalComputedMatrix) {
		this.finalComputedMatrix = finalComputedMatrix;
	}

	public Matrix getFinalGeneratedMatrix() {
		return finalGeneratedMatrix;
	}

	public void setFinalGeneratedMatrix(Matrix finalGeneratedMatrix) {
		this.finalGeneratedMatrix = finalGeneratedMatrix;
	}

	public Double getDot() {
		return dot;
	}

	public void setDot(Double dot) {
		this.dot = dot;
	}

	public Double getNorm() {
		return norm;
	}

	public void setNorm(Double norm) {
		this.norm = norm;
	}

	public GeneratingRandomMatrix getSupplier() {
		return supplier;
	}

	public void setSupplier(GeneratingRandomMatrix supplier) {
		this.supplier = supplier;
	}

	public Matrix getQ_1() {
		return Q_1;
	}

	public void setQ_1(Matrix q_1) {
		Q_1 = q_1;
	}

	public Matrix getR_1() {
		return R_1;
	}

	public void setR_1(Matrix r_1) {
		R_1 = r_1;
	}

	public Matrix getQ() {
		return Q;
	}

	public void setQ(Matrix q) {
		Q = q;
	}

	public Matrix getR() {
		return R;
	}

	public void setR(Matrix r) {
		R = r;
	}

	
}
