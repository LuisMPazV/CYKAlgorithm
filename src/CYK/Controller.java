package CYK;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import CYK.view.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Gramatica;

public class Controller extends Application {

	/**
	 * Ventana primaria del programa
	 */
	private Stage primaryStage;
	
	/**
	 * Layout principal del programa
	 */
	private BorderPane mainlayout;
	
	/**
	 * Clase pricipal que contiene la parte logica del programa
	 */
	private Gramatica gr;
	
	/**
	 * Mustra la vista pricipal
	 */
	public void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Controller.class.getResource("view/MainView.fxml"));
			loader.setController(new MainViewController(this));
			mainlayout = loader.load();
			Scene scene = new Scene(mainlayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Se encarga de manejar el evento de aplicar cyk
	 * @param grammar String que representa la gramatica
	 * @param chain String que representa la cadena con la cual se quiere aplicar el cyk
	 */
	public void aplyCYK(String grammar, String chain) {
		grammar = formatGrammar(grammar);
		gr = new Gramatica();
		gr.agregarVariables(grammar);
		boolean generates =  gr.CYKAlgorithm(chain);
		if (generates) {
			Alert a = new Alert(AlertType.INFORMATION, "La gramatica si genera la cadena w");
			a.show();
		} else {
			Alert a = new Alert(AlertType.INFORMATION, "La gramatica no genera la cadena w");
			a.show();
		}
	}
	
	/**
	 * Formatea la gramatica recibida por imput de parte del usuario a un string legible por la parte logica del programa
	 * @param grammar
	 * @return String con el formato que recibe el programa
	 */
	public String formatGrammar(String grammar) {
		String fGrammar = "";
		String[] variables = grammar.split("\n");
		for (int i = 0; i < variables.length; i++) {
			StringTokenizer skt = new StringTokenizer(variables[i], "->");
			String var = skt.nextToken().trim();
			fGrammar += var;
			String producciones = skt.nextToken();
			skt = new StringTokenizer(producciones.trim(), "|");
			while (skt.hasMoreTokens()) {
				fGrammar += " " + skt.nextToken();
			}
			if (i != variables.length-1) {
				fGrammar += "\n";
			}
		}
		return fGrammar;
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Inicia el componente grafico del programa
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CYK Algorithm");
		showMainView();
	}
	
}
