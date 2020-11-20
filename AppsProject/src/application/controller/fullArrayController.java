package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class fullArrayController {
	@FXML
	private Button cancel;
	@FXML
	private Button delete;
	
	public static boolean decesion;
	//  if they click cancel it just goes back to calender
	@FXML
	public void cancelButton(ActionEvent event) {
		Stage stage = (Stage) cancel.getScene().getWindow();
		
		decesion = true;
		stage.close();   
	}
	// if they click the delete it deletes the first occurrence in the array 
	@FXML
	public void deleteButton(ActionEvent event) {
		Stage stage = (Stage) delete.getScene().getWindow();
		
		decesion = false;
		stage.close();   
	}
}
