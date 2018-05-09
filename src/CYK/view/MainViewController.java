package CYK.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import CYK.Controller;

public class MainViewController {

	/**
	 * Boton para aplicar CYK
	 */
	@FXML
	private Button aCYK;
	
	/**
	 * Area para ingresar la cadena
	 */
	@FXML
	private TextField txtCad;
	
	/**
	 * Area para ingresar la gramatica
	 */
	@FXML
	private TextArea txtArea;
	
	/**
	 * Controlador del programa
	 */
	private Controller controller;
	
	/**
	 * Constructor de la clase
	 * @param cont Controller. Controlador para la clase
	 */
	public MainViewController(Controller cont) {
		controller = cont;
	}
	
	/**
	 * Inicilializa ciertas funciones de los componentes graficos
	 */
	@FXML
	public void initialize() {
		aCYK.setOnAction(e -> controller.aplyCYK(txtArea.getText(), txtCad.getText()));
	}
	
}
