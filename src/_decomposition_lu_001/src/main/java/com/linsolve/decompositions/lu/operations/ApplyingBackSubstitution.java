package com.linsolve.decompositions.lu.operations;

import com.linsolve.decompositions.lu.components.Matrix;
import com.linsolve.decompositions.lu.components.Identity;
import com.linsolve.decompositions.lu.components.basicoperations.*;
import com.linsolve.decompositions.lu.components.helper.*;
import com.linsolve.decompositions.lu.components.elementaryoperations.*;


public class ApplyingBackSubstitution 
{
	
	private ApplyingGaussElimination operator;
	private Matrix initialMatrix;
	private Matrix generatedMatrix;
	private Matrix computedMatrix;
	private Matrix finalGeneratedMatrix;
	private Matrix finalComputedMatrix;
	private Li_plus_lamda_Lj li_plus_lamda_lj= null;
	private Li_lamda_Li li_lamda_li=null;
	private Matrix upper;
	private Matrix lower;
	private Matrix inverse;
	private Matrix l;
	private Matrix u;
	private Matrix reconstructed;
	private InvertingLowerMatrix ilm;//=InvertingLowerMatrix(Matrix initialMatrix)
	private InvertingUpperMatrix ium;
	private int index;
	private int size;
	
	public ApplyingBackSubstitution(int index, int size) {
		super();
		this.index = index;
		this.size = size;
		this.index = index;
		this.size=size;
		this.operator=new ApplyingGaussElimination(index,size);
		this.initialMatrix=this.operator.getInitialMatrix();
		this.operator.apply_gauss_elimination();
		this.operator.format_matrix_result();
		this.operator.build_lower_matrix();
		this.generatedMatrix=operator.getGeneratedMatrix();
		this.computedMatrix=new Identity(this.initialMatrix.getM());
		this.computedMatrix=operator.getComputedMatrix();
		this.finalGeneratedMatrix=new Matrix(this.initialMatrix.getM(), this.initialMatrix.getN());
		this.finalGeneratedMatrix.setT(this.generatedMatrix.getT());
		this.finalComputedMatrix= new Identity(operator.getInitialMatrix().getM());
		this.li_lamda_li=  new Li_lamda_Li(this.operator.getInitialMatrix().getM());
		this.li_plus_lamda_lj=new Li_plus_lamda_Lj(this.operator.getInitialMatrix().getM(), this.operator.getInitialMatrix().getM());
		this.lower=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.lower=this.operator.getLower();
		this.upper=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.l=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.u=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.inverse=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.reconstructed=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
	}

	public ApplyingBackSubstitution(ApplyingGaussElimination operator) {
		this.operator = operator;
		this.initialMatrix=this.operator.getInitialMatrix();
		this.operator.apply();
		this.generatedMatrix=this.operator.getGeneratedMatrix();
		this.computedMatrix=new Identity(this.initialMatrix.getM());
		this.computedMatrix=operator.getComputedMatrix();
		this.finalGeneratedMatrix=new Matrix(this.initialMatrix.getM(), this.initialMatrix.getN());
		this.finalGeneratedMatrix.setT(this.generatedMatrix.getT());
		this.finalComputedMatrix= new Identity(operator.getInitialMatrix().getM());
		this.li_lamda_li=  new Li_lamda_Li(this.operator.getInitialMatrix().getM());
		this.li_plus_lamda_lj=new Li_plus_lamda_Lj(this.operator.getInitialMatrix().getM(), this.operator.getInitialMatrix().getM());
		this.lower=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.lower=this.operator.getLower();
		this.upper=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.l=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.u=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
		this.inverse=new Matrix(this.initialMatrix.getM(),this.getInitialMatrix().getN());
	}

	public void apply_back_substitution(int j)
	{
		li_plus_lamda_lj= null;
		for(int i=0;i<this.finalGeneratedMatrix.getM();i++)
		{
			if(i<j)
			{
				li_plus_lamda_lj= new Li_plus_lamda_Lj(this.finalGeneratedMatrix.getM(),-this.finalGeneratedMatrix.getT()[i][j] , i, j);
				this.finalGeneratedMatrix=MultiplyingMatrices.multiplyMatrix(li_plus_lamda_lj, this.finalGeneratedMatrix);
    			this.finalComputedMatrix=MultiplyingMatrices.multiplyMatrix(li_plus_lamda_lj,this.finalComputedMatrix);	
			}
		}
	}
	
	public void apply_back_substitution()
	{
		for(int i=finalGeneratedMatrix.getM()-1;i>0;i--)
		{
			this.apply_back_substitution(i);
		}
	}
	
	public void build_upper_matrix()
	{
		this.upper=this.getFinalComputedMatrix();
	}
	
	public void build_inverse_matrix()
	{
		this.inverse=MultiplyingMatrices.multiplyMatrix(this.upper,this.lower);

	}
	
	public void build_reconstructed_matrix()
	{    
		    this.ilm = new InvertingLowerMatrix(this.lower);
		    this.ilm.apply();
		    this.l = this.ilm.getInverse(); 
		    this.ium = new InvertingUpperMatrix(this.upper);
		    this.ium.apply();
		    this.u = this.ium.getInverse();
	}

	public void apply()
	{
		this.apply_back_substitution();
		this.build_upper_matrix();
		this.build_inverse_matrix();
		this.build_reconstructed_matrix();
	}

	public ApplyingGaussElimination getOperator() {
		return operator;
	}


	public void setOperator(ApplyingGaussElimination operator) {
		this.operator = operator;
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


	public Matrix getFinalGeneratedMatrix() {
		return finalGeneratedMatrix;
	}


	public void setFinalGeneratedMatrix(Matrix finalGeneratedMatrix) {
		this.finalGeneratedMatrix = finalGeneratedMatrix;
	}


	public Matrix getFinalComputedMatrix() {
		return finalComputedMatrix;
	}


	public void setFinalComputedMatrix(Matrix finalComputedMatrix) {
		this.finalComputedMatrix = finalComputedMatrix;
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


	public Matrix getInverse() {
		return inverse;
	}


	public void setInverse(Matrix inverse) {
		this.inverse = inverse;
	}


	public Matrix getL() {
		return l;
	}


	public void setL(Matrix l) {
		this.l = l;
	}


	public Matrix getU() {
		return u;
	}


	public void setU(Matrix u) {
		this.u = u;
	}


	public Matrix getReconstructed() {
		return reconstructed;
	}


	public void setReconstructed(Matrix reconstructed) {
		this.reconstructed = reconstructed;
	}


	public InvertingLowerMatrix getIlm() {
		return ilm;
	}


	public void setIlm(InvertingLowerMatrix ilm) {
		this.ilm = ilm;
	}


	public InvertingUpperMatrix getIum() {
		return ium;
	}


	public void setIum(InvertingUpperMatrix ium) {
		this.ium = ium;
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
