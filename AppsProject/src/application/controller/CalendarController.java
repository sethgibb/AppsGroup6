package application.controller;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CalendarController {

    // Get the pane to put the calendar on
    @FXML 
    Pane calendarPane;
    
    @FXML 
    Button submit;
    
    @FXML 
    Button back;
    
    int count = 1;
    String gameCounter = "";
    
    public void handle(MouseEvent event) {
        count++;
        System.out.println(count);
    }
    // this is for the submit button on the calendar that i cannot make because I didnt make the calendar so I dont understand it
    // creates the screen to show the games
    @FXML
    public void previewButton() {
    	try {
	    	Stage stage = new Stage();
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/previewScreen.fxml"));
			Parent root;
			root = loader.load();
			Scene scene = new Scene(root);
			stage.setTitle("Sports Star Scheduling");
			stage.setScene(scene);
			stage.showAndWait();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    // this is for the submit button on the calendar that i cannot make because I didnt make the calendar so I dont understand it
    // take it to the home page
    @FXML
    public void previousButton() {
    	try {
    		Stage stage = new Stage();
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("/application/view/Main.fxml"));
			Scene scene = new Scene(root,430,350);
			scene.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
			stage.setResizable(false);
			stage.setTitle("Sports Star Scheduling");
			stage.setScene(scene);
			stage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

}
