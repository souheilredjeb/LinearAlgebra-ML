package com.linsolve.decompositions.qr.components;


public class Eij extends Matrix
{
	private Integer i;
	private Integer j;
	private Kronecker kroneckerij;
	
	public Eij(Integer i, Integer j, Integer m, Integer n) {
		super(m,n);
		this.i = i;
		this.j = j;
		this.kroneckerij = new Kronecker(i, j);
		for(int k=0;k<super.m;k++)
		{
			for(int l=0;l<super.n;l++)
			{
				this.kroneckerij.applyKronecker(k, l);
				this.t[k][l]=this.kroneckerij.getKroneckerij();
			}
		}	
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

	public Kronecker getKroneckerij() {
		return kroneckerij;
	}

	public void setKroneckerij(Kronecker kroneckerij) {
		this.kroneckerij = kroneckerij;
	}
	
	public static void main(String[] args) {
		
		Eij eij = new Eij(2, 3,5,5);
		Matrix.displayMatrix(eij);
		
	}

	

}
