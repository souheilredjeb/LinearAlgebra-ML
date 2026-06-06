package com.linsolve.decompositions.lu.operations;

import com.linsolve.decompositions.lu.components.Matrix;

import com.linsolve.decompositions.lu.components.basicoperations.MultiplyingMatrices;

import com.linsolve.decompositions.lu.components.helper.GeneratingRandomMatrix;
import com.linsolve.decompositions.lu.components.helper.InvertingLowerMatrix;
import com.linsolve.decompositions.lu.components.helper.InvertingUpperMatrix;


public class FormulatingLUComponents
{
	
	private ApplyingGaussElimination operator1;
	private ApplyingBackSubstitution operator2;
	private InvertingLowerMatrix operator3;
	private InvertingUpperMatrix operator4;
	private Matrix initialMatrix;
	private Matrix upper;
	private Matrix lower;
	private Matrix product;
	private Matrix inverse;
	private int index;
	private int size;
	
	public FormulatingLUComponents(int index, int size) {
		super();
		this.index=index;
		this.size=size;
		this.initialMatrix=GeneratingRandomMatrix.supplyRandomMatrix(index,size);	
	}
	
	public FormulatingLUComponents(Matrix initialMatrix) {
		super();
		this.initialMatrix = initialMatrix;
		this.size=this.initialMatrix.getM();	
	}
	
	public void apply()
	{
		this.operator1=new ApplyingGaussElimination(this.initialMatrix);
		this.operator1.apply();
		this.operator2=new ApplyingBackSubstitution(this.operator1);
		this.initialMatrix=this.operator2.getInitialMatrix();
		this.operator2.apply();
		this.operator3=new InvertingLowerMatrix(this.operator2.getLower());
		this.operator4=new InvertingUpperMatrix(this.operator2.getUpper());
		this.operator3.apply();
		this.lower=this.operator3.getInverse();
		this.operator4.apply();
		this.upper=this.operator4.getInverse();
		this.product=new Matrix(this.initialMatrix.getM(),this.initialMatrix.getM());
		this.product=MultiplyingMatrices.multiplyMatrix(this.operator3.getInverse(),this.operator4.getInverse());	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FormulatingLUComponents operator=new FormulatingLUComponents(10,47);
		operator.apply();
		
		Matrix.displayMatrix(operator.getInitialMatrix());
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		Matrix.displayMatrix(operator.getProduct());
	}

	public ApplyingGaussElimination getOperator1() {
		return operator1;
	}

	public void setOperator1(ApplyingGaussElimination operator1) {
		this.operator1 = operator1;
	}

	public ApplyingBackSubstitution getOperator2() {
		return operator2;
	}

	public void setOperator2(ApplyingBackSubstitution operator2) {
		this.operator2 = operator2;
	}

	public InvertingLowerMatrix getOperator3() {
		return operator3;
	}

	public void setOperator3(InvertingLowerMatrix operator3) {
		this.operator3 = operator3;
	}

	public InvertingUpperMatrix getOperator4() {
		return operator4;
	}

	public void setOperator4(InvertingUpperMatrix operator4) {
		this.operator4 = operator4;
	}

	public Matrix getInitialMatrix() {
		return initialMatrix;
	}

	public void setInitialMatrix(Matrix initialMatrix) {
		this.initialMatrix = initialMatrix;
	}

	public Matrix getUpper() {
		return upper;
	}

	public void setUpper(Matrix upper) {
		this.upper = upper;
	}

	public Matrix getLower() {
		return lower;
	}

	public void setLower(Matrix lower) {
		this.lower = lower;
	}

	public Matrix getProduct() {
		return product;
	}

	public void setProduct(Matrix product) {
		this.product = product;
	}

	public Matrix getInverse() {
		return inverse;
	}

	public void setInverse(Matrix inverse) {
		this.inverse = inverse;
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
