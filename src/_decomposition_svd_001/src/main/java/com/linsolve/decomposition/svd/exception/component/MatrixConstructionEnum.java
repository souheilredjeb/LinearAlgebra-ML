package com.linsolve.decomposition.svd.exception.component;

public enum MatrixConstructionEnum {
	
	SizeDoubleArrayDoNotMatch(0, "The size of Double Arrays does not match dimensions");
	 
	 private Integer code;

	 private String message;

	 private MatrixConstructionEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	 public Integer getCode() {
		return code;
	}

	 public void setCode(Integer code) {
		this.code = code;
	}

	 public String getMessage() {
		return message;
	}

	 public void setMessage(String message) {
		this.message = message;
	}
	 

}
