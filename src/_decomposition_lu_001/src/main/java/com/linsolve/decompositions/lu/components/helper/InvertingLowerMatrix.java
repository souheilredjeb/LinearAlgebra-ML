package com.linsolve.decompositions.lu.components.helper;

import com.linsolve.decompositions.lu.components.Matrix;
import com.linsolve.decompositions.lu.components.Identity;
import com.linsolve.decompositions.lu.components.basicoperations.*;
import com.linsolve.decompositions.lu.components.elementaryoperations.*;

public class InvertingLowerMatrix 
{
	private Matrix initialMatrix;
	private Matrix generatedMatrix;
	private Matrix computedMatrix;
	private Li_plus_lamda_Lj li_plus_lamda_lj;
	private Li_lamda_Li li_lamda_li;
	private Matrix inverse;
	private Matrix product;
	private int index;
	private int size;
	
	public InvertingLowerMatrix(int index, int size) {
		super();
		this.index = index;
		this.size=size;
		this.initialMatrix=new Matrix(size,size);
		this.initialMatrix.setT(GeneratingRandomLowerMatrix.supplyRandomLowerMatrix(index, size).getT());		
		this.generatedMatrix=new Matrix(this.initialMatrix.getM(),this.initialMatrix.getN());
		this.generatedMatrix.setT(this.initialMatrix.getT());
		this.computedMatrix=new Identity(this.initialMatrix.getM());
		this.li_lamda_li=  new Li_lamda_Li(size);
		this.li_plus_lamda_lj=new Li_plus_lamda_Lj(size, size);
		this.inverse=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.product=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		
	}
	
	public InvertingLowerMatrix(Matrix initialMatrix) {
		super();
		this.initialMatrix = initialMatrix;
		this.size=this.initialMatrix.getM();
		this.generatedMatrix=new Matrix(this.initialMatrix.getM(),this.initialMatrix.getN());
		this.generatedMatrix.setT(this.initialMatrix.getT());
		this.computedMatrix=new Identity(this.initialMatrix.getM());
		this.li_lamda_li=  new Li_lamda_Li(this.initialMatrix.getM());
		this.li_plus_lamda_lj=new Li_plus_lamda_Lj(this.initialMatrix.getM(), this.initialMatrix.getM());
		this.inverse=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.product=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
	}



	public void apply_gauss_elimination(int j)
	{
		li_lamda_li= new Li_lamda_Li(this.generatedMatrix.getM(),1.0/this.generatedMatrix.getT()[j][j] , j);
		this.generatedMatrix=MultiplyingMatrices.multiplyMatrix(li_lamda_li, this.generatedMatrix);
		this.computedMatrix=MultiplyingMatrices.multiplyMatrix(li_lamda_li,this.computedMatrix);
	}
	
	public void apply_gauss_elimination()
	{
		for(int j=0;j<this.generatedMatrix.getM();j++)
		{
			this.apply_gauss_elimination(j);
		}
	}

	public void apply_back_substitution(int j)
	{
		li_plus_lamda_lj= null;
		for(int i=0;i<this.generatedMatrix.getM();i++)
		{
			if(i>j)
			{
				li_plus_lamda_lj= new Li_plus_lamda_Lj(this.generatedMatrix.getM(),-this.generatedMatrix.getT()[i][j] , i, j);
				this.generatedMatrix=MultiplyingMatrices.multiplyMatrix(li_plus_lamda_lj, this.generatedMatrix);
				this.computedMatrix=MultiplyingMatrices.multiplyMatrix(li_plus_lamda_lj,this.computedMatrix);	
			}
		}
	}
	
	public void apply_back_substitution()
	{
		for(int j=generatedMatrix.getM()-1;j>=0;j--)
		{
			
			this.apply_back_substitution(j);
		}
	}
	
	public void build_upper_matrix()
	{
		this.inverse=this.getComputedMatrix();
	}
	
	public void build_inverse_matrix()
	{
		this.product=MultiplyingMatrices.multiplyMatrix(this.inverse,this.initialMatrix);

	}
	
	public void apply()
	{
		this.apply_gauss_elimination();
		this.apply_back_substitution();
		this.build_upper_matrix();
		this.build_inverse_matrix();	
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

	public Matrix getInverse() {
		return inverse;
	}

	public void setInverse(Matrix inverse) {
		this.inverse = inverse;
	}

	public Matrix getProduct() {
		return product;
	}

	public void setProduct(Matrix product) {
		this.product = product;
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
