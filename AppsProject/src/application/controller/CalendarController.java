package application.controller;
import java.io.IOException;

import application.Model;
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
    Model mod=new Model();
    
    public void handle(MouseEvent event) {
        count++;
        System.out.println(count);
    }
    

}
