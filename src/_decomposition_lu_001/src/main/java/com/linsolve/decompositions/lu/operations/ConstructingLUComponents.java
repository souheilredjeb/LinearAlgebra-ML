package com.linsolve.decompositions.lu.operations;

import com.linsolve.decompositions.lu.components.Matrix;
import com.linsolve.decompositions.lu.components.helper.GeneratingRandomMatrix;



public class ConstructingLUComponents 
{
	private int size;
	private int index;
	
	private GeneratingRandomMatrix supplier;
	private FormulatingLUComponents operator;
	
	private Matrix initialMatrix;
	private Matrix upper;
	private Matrix lower;
	private Matrix product;
	
	public ConstructingLUComponents() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ConstructingLUComponents(Matrix initialMatrix) {
		super();
		this.initialMatrix = initialMatrix;
		this.operator=new FormulatingLUComponents(initialMatrix);
	}
	
	public ConstructingLUComponents(GeneratingRandomMatrix supplier) {
		super();	
		this.supplier = supplier;
		this.initialMatrix=supplier.supplyRandomMatrix(index, size);
		this.operator=new FormulatingLUComponents(initialMatrix);	
	}
	
	public void apply()
	{
		this.operator.apply();
		this.lower=this.operator.getLower();
		this.upper=this.operator.getUpper();
		this.product=this.operator.getProduct();
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		int index =40;
		int size=45;
		
		GeneratingRandomMatrix supplier = new GeneratingRandomMatrix() ;
		Matrix input =supplier.supplyRandomMatrix(index, size);
		ConstructingLUComponents constructlu= new ConstructingLUComponents(input);
		constructlu.apply();
		Matrix.displayMatrix(constructlu.getInitialMatrix());
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		Matrix.displayMatrix(constructlu.product);

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public GeneratingRandomMatrix getSupplier() {
		return supplier;
	}

	public void setSupplier(GeneratingRandomMatrix supplier) {
		this.supplier = supplier;
	}

	public FormulatingLUComponents getOperator() {
		return operator;
	}

	public void setOperator(FormulatingLUComponents operator) {
		this.operator = operator;
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
	
	
}
