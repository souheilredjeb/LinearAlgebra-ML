package com.linsolve.decompositions.lu.components.helper;

import java.util.Random;
import com.linsolve.decompositions.lu.components.Matrix;

public class GeneratingRandomUpperMatrix 
{
	public static Double[][] supplyRandomUpperDoubleArray(int size) {
		 Double[][] matrix = new Double[size][size];
		 Random random = new Random();  
		for (int i = 0; i < size; i++) 
	    {
	            for (int j = 0; j < size; j++) 
	            {
	                if (i == j) {
	                    matrix[i][j] = 1.0+i;
	                } else if (i < j) {
	                    matrix[i][j] = random.nextDouble() * 10; // Random values between 0 and 10
	                } else {
	                    matrix[i][j] = 0.0;
	                }
	            }
	   }    
	        return matrix;
	}
	
	
	public static Matrix supplyRandomUpperMatrix(int i, int size)
	{
		Double[][] t0= supplyRandomUpperDoubleArray(size);
		Matrix m0=new Matrix(size, size, t0);
		
		Double[][] t1= supplyRandomUpperDoubleArray(size);
		Matrix m1=new Matrix(size, size, t1);
		
		Double[][] t2= supplyRandomUpperDoubleArray(size);
		Matrix m2=new Matrix(size, size, t2);
		
		Double[][] t3= supplyRandomUpperDoubleArray(size);
		Matrix m3=new Matrix(size, size, t3);
		
		Double[][] t4= supplyRandomUpperDoubleArray(size);
		Matrix m4=new Matrix(size, size, t4);
		
		Double[][] t5= supplyRandomUpperDoubleArray(size);
		Matrix m5=new Matrix(size, size, t5);
		
		Double[][] t6= supplyRandomUpperDoubleArray(size);
		Matrix m6=new Matrix(size, size, t6);
		
		Double[][] t7= supplyRandomUpperDoubleArray(size);
		Matrix m7=new Matrix(size, size, t7);
		
		Double[][] t8= supplyRandomUpperDoubleArray(size);
		Matrix m8=new Matrix(size, size, t8);
		
		Double[][] t9= supplyRandomUpperDoubleArray(size);
		Matrix m9=new Matrix(size, size, t9);
		
		Double[][] t10= supplyRandomUpperDoubleArray(size);
		Matrix m10=new Matrix(size, size, t10);
		
		Double[][] t11= supplyRandomUpperDoubleArray(size);
		Matrix m11=new Matrix(size, size, t11);
		
		Double[][] t12= supplyRandomUpperDoubleArray(size);
		Matrix m12=new Matrix(size, size, t12);
		
		Double[][] t13= supplyRandomUpperDoubleArray(size);
		Matrix m13=new Matrix(size, size, t13);
		Double[][] t14= supplyRandomUpperDoubleArray(size);
		Matrix m14=new Matrix(size, size, t14);
		
		Double[][] t15= supplyRandomUpperDoubleArray(size);
		Matrix m15=new Matrix(size, size, t15);
		
		Double[][] t16= supplyRandomUpperDoubleArray(size);
		Matrix m16=new Matrix(size, size, t16);
		
		Double[][] t17= supplyRandomUpperDoubleArray(size);
		Matrix m17=new Matrix(size, size, t17);
		
		Double[][] t18= supplyRandomUpperDoubleArray(size);
		Matrix m18=new Matrix(size, size, t18);
		
		Double[][] t19= supplyRandomUpperDoubleArray(size);
		Matrix m19=new Matrix(size, size, t19);
		
		Double[][] t20= supplyRandomUpperDoubleArray(size);
		Matrix m20=new Matrix(size, size, t20);
		
		Double[][] t21= supplyRandomUpperDoubleArray(size);
		Matrix m21=new Matrix(size, size, t21);
		
		Double[][] t22= supplyRandomUpperDoubleArray(size);
		Matrix m22=new Matrix(size, size, t22);
		
		Double[][] t23= supplyRandomUpperDoubleArray(size);
		Matrix m23=new Matrix(size, size, t23);
		
		Double[][] t24= supplyRandomUpperDoubleArray(size);
		Matrix m24=new Matrix(size, size, t24);
		
		Double[][] t25= supplyRandomUpperDoubleArray(size);
		Matrix m25=new Matrix(size, size, t25);
		
		Double[][] t26= supplyRandomUpperDoubleArray(size);
		Matrix m26=new Matrix(size, size, t26);
		
		Double[][] t27= supplyRandomUpperDoubleArray(size);
		Matrix m27=new Matrix(size, size, t27);
		
		Double[][] t28= supplyRandomUpperDoubleArray(size);
		Matrix m28=new Matrix(size, size, t28);
		
		Double[][] t29=  supplyRandomUpperDoubleArray(size);
		Matrix m29=new Matrix(size, size, t29);
		
		Double[][] t30=  supplyRandomUpperDoubleArray(size);
		Matrix m30=new Matrix(size, size, t30);
		
		Double[][] t31= supplyRandomUpperDoubleArray(size);
		Matrix m31=new Matrix(size, size, t31);
		
		Double[][] t32=  supplyRandomUpperDoubleArray(size);
		Matrix m32=new Matrix(size, size, t32);
		
		Double[][] t33=  supplyRandomUpperDoubleArray(size);
		Matrix m33=new Matrix(size, size, t33);
		
		Double[][] t34=  supplyRandomUpperDoubleArray(size);
		Matrix m34=new Matrix(size, size, t34);
		
		Double[][] t35=  supplyRandomUpperDoubleArray(size);
		Matrix m35=new Matrix(size, size, t35);
		
		Double[][] t36=  supplyRandomUpperDoubleArray(size);
		Matrix m36=new Matrix(size, size, t36);
		
		Double[][] t37=  supplyRandomUpperDoubleArray(size);
		Matrix m37=new Matrix(size, size, t37);
		
		Double[][] t38=  supplyRandomUpperDoubleArray(size);
		Matrix m38=new Matrix(size, size, t38);
		
		Double[][] t39=  supplyRandomUpperDoubleArray(size);
		Matrix m39=new Matrix(size, size, t39);
		
		Double[][] t40=  supplyRandomUpperDoubleArray(size);
		Matrix m40=new Matrix(size, size, t40);
		
		Double[][] t41= supplyRandomUpperDoubleArray(size);
		Matrix m41=new Matrix(size, size, t41);
		
		Double[][] t42= supplyRandomUpperDoubleArray(size);
		Matrix m42=new Matrix(size, size, t42);
		
		Double[][] t43=  supplyRandomUpperDoubleArray(size);
		Matrix m43=new Matrix(size, size, t43);
		
		Double[][] t44=  supplyRandomUpperDoubleArray(size);
		Matrix m44=new Matrix(size, size, t44);
		
		Double[][] t45= supplyRandomUpperDoubleArray(size);
		Matrix m45=new Matrix(size, size, t45);
		
		Double[][] t46=  supplyRandomUpperDoubleArray(size);
		Matrix m46=new Matrix(size, size, t46);
		
		Double[][] t47=  supplyRandomUpperDoubleArray(size);
		Matrix m47=new Matrix(size, size, t47);
		
		Double[][] t48= supplyRandomUpperDoubleArray(size);
		Matrix m48=new Matrix(size, size, t48);
		
		Double[][] t49=  supplyRandomUpperDoubleArray(size);
		Matrix m49=new Matrix(size, size, t49);
		switch(i)
		{
			case 0: return m0;
			case 1: return m1;
			case 2: return m2;
			case 3: return m3;
			case 4: return m4;
			case 5: return m5;
			case 6: return m6;
			case 7: return m7;
			case 8: return m8;
			case 9: return m9;
			case 10: return m10;
			case 11: return m11;
			case 12: return m12;
			case 13: return m13;
			case 14: return m14;
			case 15: return m15;
			case 16: return m16;
			case 17: return m17;
			case 18: return m18;
			case 19: return m19;
			case 20: return m20;
			case 21: return m21;
			case 22: return m22;
			case 23: return m23;
			case 24: return m24;
			case 25: return m25;
			case 26: return m26;
			case 27: return m27;
			case 28: return m28;
			case 29: return m29;
			case 30: return m30;
			case 31: return m31;
			case 32: return m32;
			case 33: return m33;
			case 34: return m34;
			case 35: return m35;
			case 36: return m36;
			case 37: return m37;
			case 38: return m38;
			case 39: return m39;	
			case 40: return m40;
			case 41: return m41;
			case 42: return m42;
			case 43: return m43;
			case 44: return m44;
			case 45: return m45;
			case 46: return m46;
			case 47: return m47;
			case 48: return m48;
			case 49: return m49;
			default: return null;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i=10;
		int size=10;
		Matrix.displayMatrix(supplyRandomUpperMatrix(i,size));
	}

	

}
