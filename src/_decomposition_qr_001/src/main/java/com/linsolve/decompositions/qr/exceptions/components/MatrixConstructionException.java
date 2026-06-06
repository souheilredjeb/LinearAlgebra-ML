package com.linsolve.decompositions.qr.exceptions.components;

public class MatrixConstructionException extends RuntimeException 
{
	
	 private static final long serialVersionUID = -2455223540465917920L;
	 private Integer code;

	 public MatrixConstructionException(MatrixConstructionEnum matrixConstructionEnum) 
	 {
		 super(matrixConstructionEnum.getMessage());
		 this.code = matrixConstructionEnum.getCode();
	 }

	 public Integer getCode() {
		 return code;
	 }

	 public void setCode(Integer code) {
		 this.code = code;
	 }
	 
	 

}
