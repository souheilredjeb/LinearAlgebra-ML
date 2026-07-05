package com.linsolve.illustrated.wirebond.service;

import com.linsolve.illustrated.wirebond.model.RegressionResult;
import com.linsolve.illustrated.wirebond.model.WireBondData;
import java.util.List;

public interface RegressionService {
    RegressionResult computeRegression(WireBondData data);
    String getMethodName();
}