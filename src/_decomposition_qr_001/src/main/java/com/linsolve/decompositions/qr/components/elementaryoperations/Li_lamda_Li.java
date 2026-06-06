package com.linsolve.decompositions.qr.components.elementaryoperations;

import com.linsolve.decompositions.qr.components.Eij;
import com.linsolve.decompositions.qr.components.Identity;
import com.linsolve.decompositions.qr.components.Matrix;
import com.linsolve.decompositions.qr.components.SquareMatrix;
import com.linsolve.decompositions.qr.components.basicoperations.AddingMatrices;
import com.linsolve.decompositions.qr.components.basicoperations.MultiplyingMatrices;
import com.linsolve.decompositions.qr.components.basicoperations.MultiplyingMatrixByScalar;
import com.linsolve.decompositions.qr.components.elementaryoperations.Li_lamda_Li;
import com.linsolve.decompositions.qr.helper.GeneratingRandomMatrix;

public class Li_lamda_Li extends SquareMatrix 
{
	private Integer i;
	private Integer m;
	private Eij eii;
	private Identity id;
	private Double lamda;
	
	public Li_lamda_Li(Integer m, Double lamda,Integer i)
	{
		super(m);
		this.i=i;
		this.m=m;
		this.eii=new Eij(i,i,m,m);
		this.id= new Identity(m);
		this.lamda=lamda;
		this.setT(AddingMatrices.add(AddingMatrices.add(MultiplyingMatrixByScalar.multiplyByScalar(this.eii, -1.0), MultiplyingMatrixByScalar.multiplyByScalar(this.eii, lamda)),this.id).getT());	
	}
	
	public Li_lamda_Li( Integer m) {
		super(m);
		this.m = m;
		this.n=m;
	}

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public Eij getEii() {
		return eii;
	}

	public void setEii(Eij eii) {
		this.eii = eii;
	}

	public Identity getId() {
		return id;
	}

	public void setId(Identity id) {
		this.id = id;
	}

	public Double getLamda() {
		return lamda;
	}

	public void setLamda(Double lamda) {
		this.lamda = lamda;
	}
	
	public static void main(String[] args) {
		
		System.out.println("hello world");
		
		Matrix m0=GeneratingRandomMatrix.supplyRandomMatrix(5,5);
		Li_lamda_Li lilamdali = new Li_lamda_Li(5, 2.0,0);
	    Matrix.displayMatrix(m0);
	    System.out.println();
	    System.out.println();
	    System.out.println();
		Matrix.displayMatrix(MultiplyingMatrices.multiplyMatrix(lilamdali, m0));
		System.out.println();
	    System.out.println();
	    System.out.println();
		Matrix m1=GeneratingRandomMatrix.supplyRandomMatrix(4,5);
		Matrix.displayMatrix(m1);
		System.out.println();
	    System.out.println();
	    System.out.println();
		Li_lamda_Li lilamdali2 = new Li_lamda_Li(4, 3.0,1);
		MultiplyingMatrices.multiplyMatrix(m1, lilamdali2);
		Matrix.displayMatrix(MultiplyingMatrices.multiplyMatrix(lilamdali2, m1));
	}

}