package com.linsolve.decompositions.qr.components.elementaryoperations;

import com.linsolve.decompositions.qr.components.Eij;
import com.linsolve.decompositions.qr.components.Identity;
import com.linsolve.decompositions.qr.components.SquareMatrix;
import com.linsolve.decompositions.qr.components.basicoperations.AddingMatrices;
import com.linsolve.decompositions.qr.components.basicoperations.MultiplyingMatrixByScalar;

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
	
	

}
