package com.linsolve.decomposition.svd.computations;
import com.linsolve.decomposition.svd.components.Matrix;
import com.linsolve.decomposition.svd.components.helper.GeneratingSymetricMatrix;
public class ComputingMaximumCoefficientOffdiag {

	private Matrix S;
	private Double max_coefficient;
	private Integer p;	
	private Integer q;
	
	public ComputingMaximumCoefficientOffdiag(Matrix initialMatrix) {
		super();
		this.S = initialMatrix;
		if (this.S.getM() > 1 && this.S.getN() > 1) {
			this.max_coefficient = this.S.getT()[0][1];
			this.p = 0;
			this.q = 1;
		} else {
			this.max_coefficient = 0.0;
			this.p = 0;
			this.q = 0;
		}
	}
	
	public void apply() {
		if (this.S.getM() > 1 && this.S.getN() > 1) {
			this.max_coefficient = this.S.getT()[0][1];
			this.p = 0;
			this.q = 1;
		} else {
			this.max_coefficient = 0.0;
			this.p = 0;
			this.q = 0;
			return;
		}
		
		for(int i = 0; i < this.S.getM(); i++) {
			for(int j = i + 1; j < this.S.getN(); j++) {
				if(Math.abs(this.S.getT()[i][j]) > Math.abs(this.max_coefficient)) {
					this.max_coefficient = this.S.getT()[i][j];
					p = i;
					q = j;
				}
			}
		}
	}

	public Matrix getS() {
		return S;
	}

	public void setS(Matrix S) {
		this.S = S;
	}

	public Double getMax_coefficient() {
		return max_coefficient;
	}

	public void setMax_coefficient(Double max_coefficient) {
		this.max_coefficient = max_coefficient;
	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public Integer getQ() {
		return q;
	}

	public void setQ(Integer q) {
		this.q = q;
	}
}
