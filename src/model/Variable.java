package model;

import java.util.HashSet;

public class Variable {
	
	
	private HashSet<String> producciones;
	
	private String identificador;

	public Variable(String identificador) {
		this.producciones = new HashSet<>();
		this.identificador = identificador;
		
	}
	
	public void agregarProduccion(String prod) {
		if(!producciones.contains(prod)) {
			producciones.add(prod);
		}
	}
	

	public HashSet<String> getProducciones() {
		return producciones;
	}

	public void setProducciones(HashSet<String> producciones) {
		this.producciones = producciones;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	
	
	

}
