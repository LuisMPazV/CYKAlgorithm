package model;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Gramatica {
	
	
	
	private HashMap<String, Variable> variables;

	public Gramatica() {
		this.variables = new HashMap<>();
	}
	
	
	public void agregarVariables(String variables) {
		
		String[] lines=variables.split("\n");
		
		for (int i = 0; i < lines.length; i++) {
			StringTokenizer producciones=new StringTokenizer(lines[i].trim());
			String variable=producciones.nextToken();
			
			Variable actual=this.variables.get(variable);
			if(actual==null) {
				actual=new Variable(variable);
				this.variables.put(variable, actual);
			}
			
			while(producciones.hasMoreTokens()) {
				actual.agregarProduccion(producciones.nextToken());
			}
			
			
		}
	}
	
	
	
	
	
	

}
