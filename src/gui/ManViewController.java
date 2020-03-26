package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ManViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		System.out.println("onMenuItemDepartmentAction");
	}

	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("onMenuItemAboutAction");
		loadView("/gui/About.fxml");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	// Abrir janela
	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			// mostrar dentro da janela principal
			// referencia da Scene da classe Main
			Scene mainScene = Main.getMainScene();
			// Pegar VBox da classe Main - MainView
			// pega o primeiro elemento da view o ScrollPane e depois o content com getContent()
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();   
			
			// primeiro filho(children) do Vbox da MainView
			Node mainMenu = mainVBox.getChildren().get(0);
			// limpar todos os filhos do mainVbox.
			mainVBox.getChildren().clear();
			
			// Agora adicionar os filhos no MainView
			mainVBox.getChildren().add(mainMenu); // são os menus da MainView: registration:Seller/Department..About
			mainVBox.getChildren().addAll(newVBox.getChildren()); // adiciona os filhos que chegam por parametro
			
			
		} catch (IOException e) {
			
			Alerts.showAlert("IO Exception", "Error Loading View", e.getMessage(), AlertType.ERROR);
		}
		
	}
	

}
