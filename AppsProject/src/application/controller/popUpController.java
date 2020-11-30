package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class popUpController {
	@FXML
	private Button add;
	@FXML
	private Button delete;
	
	public static boolean option;
	// if they click add it adds another game to the array list
	@FXML
	public void addButton(ActionEvent event) {
		Stage stage = (Stage) add.getScene().getWindow();
		
		option = true;
		stage.close();   
	}
	// if they click delete it deletes the first occurrence of the date
	@FXML
	public void deleteButton(ActionEvent event) {
		Stage stage = (Stage) delete.getScene().getWindow();
		
		option = false;
		stage.close();   
	}
}
