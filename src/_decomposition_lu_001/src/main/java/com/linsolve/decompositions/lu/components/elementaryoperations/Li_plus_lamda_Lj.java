package com.linsolve.decompositions.lu.components.elementaryoperations;

import com.linsolve.decompositions.lu.components.Identity;
import com.linsolve.decompositions.lu.components.Eij;
import com.linsolve.decompositions.lu.components.SquareMatrix;
import com.linsolve.decompositions.lu.components.basicoperations.*;


public class Li_plus_lamda_Lj extends SquareMatrix
{
	private Integer i;
	private Integer j;
	private Integer m;
	private Eij eij;
	private Identity id;
	private Double lamda;
	
	public Li_plus_lamda_Lj(Integer m, Double lamda,Integer i, Integer j)
	{
		super(m);
		this.i=i;
		this.j=j;
		this.m=m;
		this.n=m;
		this.eij=new Eij(i,j,m,m);
		this.id= new Identity(m);
		this.lamda=lamda;
		this.setT(AddingMatrices.add(MultiplyingMatrixByScalar.multiplyByScalar(this.eij, this.lamda),this.id).getT());
	}
	
	public Li_plus_lamda_Lj(Integer n, Integer m) 
	{
		super(n);
		this.m = m;
		this.n=m;
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

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public Eij getEij() {
		return eij;
	}

	public void setEij(Eij eij) {
		this.eij = eij;
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
