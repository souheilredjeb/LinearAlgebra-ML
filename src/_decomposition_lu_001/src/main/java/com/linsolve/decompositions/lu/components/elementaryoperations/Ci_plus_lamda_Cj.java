package com.linsolve.decompositions.lu.components.elementaryoperations;

import com.linsolve.decompositions.lu.components.Identity;
import com.linsolve.decompositions.lu.components.Matrix;
import com.linsolve.decompositions.lu.components.Eij;
import com.linsolve.decompositions.lu.components.SquareMatrix;
import com.linsolve.decompositions.lu.components.basicoperations.*;
import com.linsolve.decompositions.lu.components.helper.GeneratingRandomMatrix;
import com.linsolve.decompositions.lu.components.basicoperations.AddingMatrices;
import com.linsolve.decompositions.lu.components.basicoperations.MultiplyingMatrixByScalar;


public class Ci_plus_lamda_Cj extends SquareMatrix 
{
	
	private Integer i;
	private Integer j;
	private Integer n;
	private Eij eji;
	private Identity id;
	private Double lamda;
	
	public Ci_plus_lamda_Cj(Integer n, Double lamda,Integer i, Integer j)
	{
		super(n);
		this.m=n;
		this.n=n;
		this.i=i;
		this.j=j;
		this.eji=new Eij(j,i,n,n);
		this.id= new Identity(n);
		this.lamda=lamda;
		this.setT(AddingMatrices.add(MultiplyingMatrixByScalar.multiplyByScalar(this.eji, this.lamda),this.id).getT());
	}

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

	public Integer getJ() {
		return j;
	}

	public void setJ(Integer j) {
		this.j = j;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public Eij getEji() {
		return eji;
	}

	public void setEji(Eij eji) {
		this.eji = eji;
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
		
	
		Ci_plus_lamda_Cj ci_plus_lamda_cj = new Ci_plus_lamda_Cj(5,2.0,1,3);
		Matrix m0=GeneratingRandomMatrix.supplyRandomMatrix(5,5);
		Matrix.displayMatrix(m0);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		Matrix.displayMatrix(MultiplyingMatrices.multiplyMatrix(m0,ci_plus_lamda_cj));
		
	}

}
