package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Gramatica {
	private String inicio;

	private HashMap<String, Variable> variables;

	public Gramatica() {
		this.variables = new HashMap<>();
		inicio="";
	}

	public void agregarVariables(String variables) {

		String[] lines = variables.split("\n");

		for (int i = 0; i < lines.length; i++) {
			StringTokenizer producciones = new StringTokenizer(lines[i].trim());
			String variable = producciones.nextToken();

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

			while (producciones.hasMoreTokens()) {
				actual.agregarProduccion(producciones.nextToken());
			}

		}
	}

	public boolean CYKAlgorithm(String cad) {
		boolean generates = false;
		if (cad.length() >= 1) {
			char[] characters = cad.toCharArray();
			String[][][] cykArray = new String[characters.length][characters.length][0];

			for (int j = 0; j < characters.length; j++) {
				ArrayList<String> actual = new ArrayList<>();
				for (Iterator iterator = variables.values().iterator(); iterator.hasNext();) {
					Variable variable = (Variable) iterator.next();
					if (variable.getProducciones().contains(characters[j] + "")) {
						actual.add(variable.getIdentificador());
					}
				}
				if(actual.size()>0) {
					String[] resultado=new String[actual.size()];
					for (int i = 0; i < resultado.length; i++) {
						resultado[0]=actual.get(i);
					}
					cykArray[j][0]=resultado;
				}else {
					cykArray[j][0]=null;
				}
			}
			
			
			
			for (int j = 1; j < cykArray.length; j++) {
				for (int i = 0; i < cykArray.length-j; i++) {
					HashSet<String> generados=new HashSet<>();
					for(int k=0;k<j;k++) {
						String [] primero=cykArray[i][k];
						String [] segundo=cykArray[i+k+1][j-k-1];
						if(primero!=null&&segundo!=null) {
							for (int l = 0; l < primero.length; l++) {
								for (int l2 = 0; l2 < segundo.length; l2++) {
									generados.add(primero[l]+segundo[l2]);
								}
							}
						}
					}
					if(generados.size()>0) {
						ArrayList<String> producen=new ArrayList<>();
						for (Iterator iterator = variables.values().iterator(); iterator.hasNext();) {
							Variable variable = (Variable) iterator.next();
							boolean encontrado=false;
							for (Iterator iterator2 = generados.iterator(); iterator2.hasNext()&&!encontrado;) {
								String string = (String) iterator2.next();
								if(variable.getProducciones().contains(string)) {
									encontrado=true;
								}
								
							}
							if(encontrado) {
								producen.add(variable.getIdentificador());
							}
						}
						
						if(producen.size()>0) {
							String[] resultado=new String[producen.size()];
							for (int k = 0; k < resultado.length; k++) {
								resultado[k]=producen.get(k);
							}
							cykArray[i][j]=resultado;
						}else {
							cykArray[i][j]=null;
						}
						
						
					}
				}
			}
			
			
			boolean containsInicio=false;
			
			String[] array0j=cykArray[0][cykArray.length-1];
			for (int i = 0; i < array0j.length&&!containsInicio; i++) {
				if(array0j[i].equals(inicio)) {
					containsInicio=true;
				}
			}
			generates=containsInicio;

		} else {
			return true;
		}

		return generates;
	}

}
