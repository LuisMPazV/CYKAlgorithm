package model;

public class Test {

	public static void main(String[] args) {
		Gramatica g=new Gramatica();
		g.agregarVariables("S AB BB BC\nA BA a\nB AC b\nC CB CC c");
		System.out.println(g.CYKAlgorithm("acbb"));

	}

}
