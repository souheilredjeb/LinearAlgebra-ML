package com.linsolve.decompositions.qr.components;

import com.linsolve.decompositions.qr.exceptions.components.MatrixConstructionEnum;
import com.linsolve.decompositions.qr.exceptions.components.MatrixConstructionException;


public class Matrix 
{
	protected Integer m;
	protected Integer n;
	protected Double[][] t;
	
	public Matrix() 
	{
		super();	
	}
	
	public Matrix(Integer m, Integer n) 
	{
		super();
		this.m = m;
		this.n = n;
		this.t=new Double[m][n];
	}
	
	public Matrix(Integer m, Integer n, Double[][] t) {
		super();
		this.m = m;
		this.n = n;
		if(t.length !=m || t[0].length!=n)
		{
			try
			{
				throw new MatrixConstructionException(MatrixConstructionEnum.SizeDoubleArrayDoNotMatch);
			}
			catch (MatrixConstructionException e)
			 {
		            System.out.println(e.getMessage());            
			 }
		}
		else
		{
			this.t = t;
		}
		 
	}
	
	public static void display(Double[][] t)
	{
		for (int k = 0; k < t.length; k++) {
	        System.out.print("[");
	        for (int l = 0; l < t[0].length - 1; l++) {
	 
	            System.out.print(t[k][l]);
	            System.out.print("|| ");
	        }
	        System.out.print(t[k][t[0].length - 1]);
	        System.out.print("]");
	        System.out.println();
	    }
	}
	
	public static void display_vector(Double[] v)
	{	
		System.out.print("[");
        for (int l = 0; l < v.length - 1; l++) {
            System.out.print(v[l]);
            System.out.print("||");
        }
        System.out.print(v[v.length - 1]);
        System.out.print("]");
        System.out.println();	
	}
	
	public static void displayMatrix(Matrix matrix)
	{
		display(matrix.getT());	
	}
	
	

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public Double[][] getT() {
		return t;
	}

	public void setT(Double[][] t) {
		this.t = t;
	}

}

