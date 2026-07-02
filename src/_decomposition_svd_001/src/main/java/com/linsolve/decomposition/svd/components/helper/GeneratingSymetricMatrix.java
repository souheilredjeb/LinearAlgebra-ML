package com.linsolve.decomposition.svd.components.helper;

import java.util.Random;
import com.linsolve.decomposition.svd.components.*;
import com.linsolve.decomposition.svd.components.basicoperations.AddingMatrices;
import com.linsolve.decomposition.svd.components.basicoperations.TransposingMatrix;

public class GeneratingSymetricMatrix 
{
		
	
	
	public static Matrix generateSymetricMatrix(Integer n)
	{
		int i=0;
		Matrix m1=GeneratingRandomLowerMatrix.supplyRandomLowerMatrix(i, n);
		m1=AddingMatrices.add(TransposingMatrix.transposing(m1), m1)						;
		return m1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Matrix.displayMatrix(generateSymetricMatrix(5));
	}

}
