package com.gunna.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coeficientes {
	@Expose
	@SerializedName("coeficient")
	private Integer coeficiente;
	@Expose
	@SerializedName("variable")
	private String  nomeVariavel;
	
	
	public String getNomeVariavel(){
		return nomeVariavel;
	}
	
	public void setNomeVariavel(String nome){
		this.nomeVariavel = nome;
	}
	
	public Integer getCoeficiente(){
		return coeficiente;
	}
	
	public void setCoeficiente(Integer c){
		this.coeficiente = c ; 
	}
	
}
