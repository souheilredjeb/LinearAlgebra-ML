package com.linsolve.decompositions.qr.components.basicoperations;

import com.linsolve.decompositions.qr.components.Matrix;

public class TransposingMatrix {

	public static Matrix transposing(Matrix m)
	{
		Double[][] t=new Double[m.getN()][m.getM()];
		Matrix transpose=new Matrix( m.getN(),m.getM());
		for(int i=0;i<m.getN(); i++)
		{
			for(int j=0;j<m.getM();j++)
			{
				t[i][j]=m.getT()[j][i];
			}
		}
		transpose.setT(t);
		return transpose;
	}

}
