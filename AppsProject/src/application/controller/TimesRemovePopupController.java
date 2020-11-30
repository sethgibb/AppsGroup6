package application.controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.*;

public class TimesRemovePopupController {

    @FXML
    private Button submitButton;

    @FXML
    private CheckBox time1Box;

    @FXML
    private CheckBox time2Box;

    @FXML
    private CheckBox time3Box;
    
    public static boolean time1Choice = false;
    public static boolean time2Choice = false;
    public static boolean time3Choice = false;
    public static ArrayList<String> times;

    @FXML
    void submitPressed(ActionEvent event) {
    	time1Choice = time1Box.isSelected();
    	time2Choice = time2Box.isSelected();
    	time3Choice = time3Box.isSelected();
    	Stage stage = (Stage) submitButton.getScene().getWindow();
    	stage.close();
    }
    
    public void initialize(){
    	time1Box.setText(times.get(0));
    	time2Box.setText(times.get(1));
    	if(times.size() >= 3){
    		time3Box.setVisible(true);
    		time3Box.setText(times.get(2));
    	}
    }

}