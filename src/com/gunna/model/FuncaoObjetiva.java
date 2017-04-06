package com.gunna.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FuncaoObjetiva {
	@Expose
	@SerializedName("objective_function")
	List<Coeficientes> coeficientes;
	
	public List<Coeficientes> getCoeficientes(){
		return coeficientes;
	}
	
	public void setCoeficientes(List<Coeficientes> coeficientes){
		this.coeficientes = coeficientes;
	}
	

}
