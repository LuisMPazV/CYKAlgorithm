package model;

import java.util.HashSet;

public class Variable {
	
	
	/**
	 * Set de producciones
	 */
	private HashSet<String> producciones;
	
	/**
	 * Identificador de la variable
	 */
	private String identificador;

	
	/**
	 * Constructor de la variable
	 * @param identificador	identificador de la variable
	 */
	public Variable(String identificador) {
		this.producciones = new HashSet<>();
		this.identificador = identificador;
		
	}
	
	
	/**
	 * Agrega una produccion a la variable en caso de que no exista en el set de producciones
	 * @param prod nueva produccion
	 */
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
