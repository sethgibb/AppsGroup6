package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddPopUpController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField textField;

    public static boolean isCanceled = true;
    public static String timeEntered ="";
    
    @FXML
    public void cancelPressed(ActionEvent e){
    	isCanceled = true;
    	addButton.getScene().getWindow().hide();
    }
    
    @FXML
    public void addPressed(ActionEvent e){
    	if(textField.getText().trim().isEmpty()){
    		Alert a = new Alert(AlertType.ERROR);
    		a.setTitle("ERROR");
    		a.setContentText("Enter a start time to add to schedule.");
    		a.setHeaderText("ERROR: no text");
    		a.show();
    		return;
    	}
    	isCanceled = false;
    	timeEntered = textField.getText();
    	addButton.getScene().getWindow().hide();
    }
    
    
}