package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class foundGameController {
	@FXML
	private Button view;
	@FXML
	private Button delete;
	
	public static boolean deleteTeam;
	//View the data saved in the txt
	@FXML
	public void viewButton(ActionEvent event) {
		Stage stage = (Stage) view.getScene().getWindow();
		deleteTeam=false;
		stage.close();
		return;
	}
	// remove the data from the txt and go to the calendar window
	@FXML
	public void deleteButton(ActionEvent event) {
		Stage stage = (Stage) delete.getScene().getWindow();
		deleteTeam=true;
		stage.close();
		return;
	}
}
