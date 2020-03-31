package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setSellerService(new SellerService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemAboutAction() {
		// passa uma função que não faz nada
		loadView("/gui/About.fxml", x->{});
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	// Abrir janela
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
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
			
			// vai executar os parametros
			T controller = loader.getController();
			initializingAction.accept(controller);
			
			
		} catch (IOException e) {
			
			Alerts.showAlert("IO Exception", "Error Loading View", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	///////////////
	// Abrir janela
	private synchronized void loadView2(String absoluteName) {
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
			
			// Acessar o controller
			DepartmentListController controller = loader.getController();
			// Injetar a dependencia
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
			
			
		} catch (IOException e) {
			
			Alerts.showAlert("IO Exception", "Error Loading View", e.getMessage(), AlertType.ERROR);
		}
		
	}

	

}
