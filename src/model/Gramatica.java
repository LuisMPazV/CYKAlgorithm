package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Gramatica {
	
	/**
	 * Identificados de la Variable inicial
	 */
	private String inicio;

	/**
	 * Set de variables de la gramatica
	 */
	private HashMap<String, Variable> variables;
	
	/**
	 *Constructor de la gramatica
	 */
	public Gramatica() {
		this.variables = new HashMap<>();
		inicio="";
	}
	
	

	/**
	 * Metodo encargado de cargar las variables y sus
	 * producciones de la gramatica independiente de
	 * contexto G en FNC
	 * @param variables Variable con sus respectivas producciones separados por espacio. Cada variable en una linea diferente
	 */
	public void agregarVariables(String variables) {

		//Se separan las lineas
		String[] lines = variables.split("\n");

		
		for (int i = 0; i < lines.length; i++) {
			StringTokenizer producciones = new StringTokenizer(lines[i].trim());
			String variable = producciones.nextToken();

			//Se crea la variable
			Variable actual = this.variables.get(variable);
			if (actual == null) {
				actual = new Variable(variable);
				if(this.variables.size()==0) {
					this.variables.put(variable, actual);
					inicio=variable;
				}else {
					this.variables.put(variable, actual);
				}
			}

			//Se agregan las producciones de la variable
			while (producciones.hasMoreTokens()) {
				actual.agregarProduccion(producciones.nextToken());
			}

		}
	}

	/**
	 * Algoritmo CYK que determina si una cadena es producida por la gramatica G en FNC
	 * @param cad cadena que deseamos validar
	 * @return retorna si la cadena es generada
	 */
	public boolean CYKAlgorithm(String cad) {
		boolean generates = false;
		if (cad.length() >= 1) {			//Se valida que la cadena tenga al menos un caracter
			char[] characters = cad.toCharArray(); //Se separan los caracteres en un arreglo
			String[][][] cykArray = new String[characters.length][characters.length][0];	//Se crea una matriz de Strings de NxNx0 donde N es la longitud de la cadena

			
			for (int j = 0; j < characters.length; j++) {// Se itera sobre los caracteres para inicializar la primera columna de la matriz
				ArrayList<String> actual = new ArrayList<>();// ArrayList de variables que producen el caracter
				for (Iterator iterator = variables.values().iterator(); iterator.hasNext();) { //Se itera sobre las variables de la gramatica
					Variable variable = (Variable) iterator.next();
					if (variable.getProducciones().contains(characters[j] + "")) {
						actual.add(variable.getIdentificador());				//se Agrega al ArrayList si produce el caracter(terminal)
					}
				}
				if(actual.size()>0) {										//Se valida que al menos una variable genere el caracter
					String[] resultado=new String[actual.size()];
					for (int i = 0; i < resultado.length; i++) {
						resultado[0]=actual.get(i);
					}
					cykArray[j][0]=resultado;								//Se agregan a la matriz las variables que generen el caracter
				}else {
					cykArray[j][0]=null;									//Se coloca null la coordenada de la matriz ya que ninguna variable genera el caracter
				}
			}
			
			
			
			for (int j = 1; j < cykArray.length; j++) {						//Se itera sobre la columnas de la matriz
				for (int i = 0; i < cykArray.length-j; i++) {				//Se itera sobre las filas de la matriz
					HashSet<String> generados=new HashSet<>();				//Se inicializa un set de posibles producciones
					for(int k=0;k<j;k++) {									//  0<=K<j
						String [] primero=cykArray[i][k];					//Arreglo de variables en la pos i,k
						String [] segundo=cykArray[i+k+1][j-k-1];			//Arreglo de variables en la pos i+k+1,j-k-1
						if(primero!=null&&segundo!=null) {					//Se valida que ambos arreglos sean diferentes de null
							for (int l = 0; l < primero.length; l++) {
								for (int l2 = 0; l2 < segundo.length; l2++) {
									generados.add(primero[l]+segundo[l2]);	//Se agregan al set las posibles producciones
								}
							}
						}
					}
					if(generados.size()>0) {								//Se valida que se haya generado al menos una posible produccion
						ArrayList<String> producen=new ArrayList<>();		//ArrayList de variables que producen alguna de las posibles producciones
						for (Iterator iterator = variables.values().iterator(); iterator.hasNext();) {
							Variable variable = (Variable) iterator.next();
							boolean encontrado=false;
							for (Iterator iterator2 = generados.iterator(); iterator2.hasNext()&&!encontrado;) {
								String string = (String) iterator2.next();
								if(variable.getProducciones().contains(string)) {	//Se valida si esta variable genera alguna de las producciones
									encontrado=true;
								}
								
							}
							if(encontrado) {
								producen.add(variable.getIdentificador());			//Se agraga la Variable en caso de que genere alguna de las producciones posibles
							}
						}
						
						if(producen.size()>0) {										//Se valida que haya al menos una variable en el Arraylist
							String[] resultado=new String[producen.size()];
							for (int k = 0; k < resultado.length; k++) {
								resultado[k]=producen.get(k);
							}
							cykArray[i][j]=resultado;								//Se asignan a la pos i,j de la matriz las variables que producen
						}else {
							cykArray[i][j]=null;									//Se asigna null a la pos i,j de la matriz en caso de que ninguna variable produsca alguna de las producciones posibles
						}
						
						
					}else {
						cykArray[i][j]=null;										//Se asigna null a la pos i,j de la matriz en caso de que no haya posibles producciones
					}
				}
			}
			
			
			boolean containsInicio=false;
			
			String[] array0j=cykArray[0][cykArray.length-1];			//Variables en la pos 0,longitudCad-1. Se quiere varidar que la variable inicial este en estas variables
			for (int i = 0; i < array0j.length&&!containsInicio; i++) {
				if(array0j[i].equals(inicio)) {
					containsInicio=true;		//En caso de que pertenesca al arreglo la variable inicial
				}
			}
			generates=containsInicio;			//Se asigna el resultado al retorno

		} else {
			return true;
		}

		return generates;
	}

}
