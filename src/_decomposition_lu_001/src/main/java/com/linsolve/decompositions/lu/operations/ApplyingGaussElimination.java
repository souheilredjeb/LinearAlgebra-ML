package com.linsolve.decompositions.lu.operations;

import com.linsolve.decompositions.lu.components.Matrix;
import com.linsolve.decompositions.lu.components.Identity;
import com.linsolve.decompositions.lu.components.basicoperations.MultiplyingMatrices;
import com.linsolve.decompositions.lu.components.helper.GeneratingRandomMatrix;
import com.linsolve.decompositions.lu.components.elementaryoperations.Li_lamda_Li;
import com.linsolve.decompositions.lu.components.elementaryoperations.Li_plus_lamda_Lj;


public class ApplyingGaussElimination 
{
	private Matrix initialMatrix;
	private Matrix generatedMatrix;
	private Matrix computedMatrix;
	private Li_plus_lamda_Lj li_plus_lamda_lj= null;
	private Li_lamda_Li li_lamda_li=null;
	private Matrix lower;
	private int index;
	private int size;

	

	public ApplyingGaussElimination(int index, int size) {
		super();
		this.index = index;
		this.size = size;
		this.initialMatrix=GeneratingRandomMatrix.supplyRandomMatrix(index, size);
		this.generatedMatrix=new Matrix(this.initialMatrix.getM(),this.initialMatrix.getN());
		this.generatedMatrix.setT(this.initialMatrix.getT());
		this.computedMatrix=new Identity(this.initialMatrix.getM());
		this.lower=new Matrix(this.initialMatrix.getM(),this.initialMatrix.getN());
	}

	

	public ApplyingGaussElimination(Matrix initialMatrix) {
		super();
		this.initialMatrix = initialMatrix;
		this.generatedMatrix=new Matrix(this.initialMatrix.getM(),this.initialMatrix.getN());
		this.generatedMatrix.setT(this.initialMatrix.getT());
		this.computedMatrix=new Identity(this.initialMatrix.getM());
		this.lower=new Matrix(this.initialMatrix.getM(),this.initialMatrix.getM());	
	}

	public void apply_gauss_elimination(int j)
	{
		
		li_lamda_li= new Li_lamda_Li(this.generatedMatrix.getM(),1.0/this.generatedMatrix.getT()[j][j] , j);
		this.generatedMatrix=MultiplyingMatrices.multiplyMatrix(li_lamda_li, this.generatedMatrix);
		this.computedMatrix=MultiplyingMatrices.multiplyMatrix(li_lamda_li,this.computedMatrix);		
		for(int i=0;i <generatedMatrix.getM();i++)
		{
			if(i>j)
			{
				li_plus_lamda_lj= new Li_plus_lamda_Lj(generatedMatrix.getM(),-generatedMatrix.getT()[i][j] , i, j);
				generatedMatrix=MultiplyingMatrices.multiplyMatrix(li_plus_lamda_lj,generatedMatrix);
				computedMatrix=MultiplyingMatrices.multiplyMatrix(li_plus_lamda_lj,computedMatrix);
			}	
		}
	}
	
	public void apply_gauss_elimination()
	{
		for(int j=0;j<this.generatedMatrix.getM();j++)
		{
			this.apply_gauss_elimination(j);
		}
	}
	
	public void build_lower_matrix()
	{
		this.lower=this.computedMatrix;
	}
	
	public void format_matrix_result()
	{
		Double[][] d_array_1=new Double[this.generatedMatrix.getM()][this.generatedMatrix.getM()];
		for(int i=0;i<this.generatedMatrix.getM();i++)
		{
			for(int j=0;j<this.generatedMatrix.getM();j++)
			{
				if(i>j)
				{
					d_array_1[i][j]=0.0;
				}
				else if(i<j)
				{
					d_array_1[i][j]=this.generatedMatrix.getT()[i][j];
				}
				else
				{
					d_array_1[i][j]=1.0;
				}
			}
		}
		this.generatedMatrix.setT(d_array_1);
	}
	
	public void apply()
	{
		this.apply_gauss_elimination();
		this.format_matrix_result();
		this.build_lower_matrix();
		
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		int k=0;
		int size=10;
		ApplyingGaussElimination oper=new ApplyingGaussElimination(k,size);
		oper.apply();
		Matrix.displayMatrix(oper.getLower());       ;

	}



	public Matrix getInitialMatrix() {
		return initialMatrix;
	}



	public void setInitialMatrix(Matrix initialMatrix) {
		this.initialMatrix = initialMatrix;
	}



	public Matrix getGeneratedMatrix() {
		return generatedMatrix;
	}



	public void setGeneratedMatrix(Matrix generatedMatrix) {
		this.generatedMatrix = generatedMatrix;
	}



	public Matrix getComputedMatrix() {
		return computedMatrix;
	}



	public void setComputedMatrix(Matrix computedMatrix) {
		this.computedMatrix = computedMatrix;
	}



	public Li_plus_lamda_Lj getLi_plus_lamda_lj() {
		return li_plus_lamda_lj;
	}



	public void setLi_plus_lamda_lj(Li_plus_lamda_Lj li_plus_lamda_lj) {
		this.li_plus_lamda_lj = li_plus_lamda_lj;
	}



	public Li_lamda_Li getLi_lamda_li() {
		return li_lamda_li;
	}



	public void setLi_lamda_li(Li_lamda_Li li_lamda_li) {
		this.li_lamda_li = li_lamda_li;
	}



	public Matrix getLower() {
		return lower;
	}



	public void setLower(Matrix lower) {
		this.lower = lower;
	}



	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}



	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}
	
	
}
