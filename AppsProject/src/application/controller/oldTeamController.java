package application.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.YearMonth;
import java.util.ResourceBundle;

import application.FullCalendarView;
import application.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class oldTeamController implements Initializable{
	
	@FXML
	private TextArea gameTxt;
	
	@FXML
	private Button back;
	
	@FXML
	public Pane calendarPane;
	
	@FXML
	private TextField teamName;
	
	@FXML
	private TextField gameNumber;
	
	String text = "";
	// this will run as soon as the submit button is pressed and sets the text area to the games
	//modified to show an existing teams schedule
	@Override
	public void initialize(URL location, ResourceBundle resources){
		String text = "";
		Object[] team = MainController.searchTeam.toArray();
		for(int i = 0; i<team.length; i++){
			text+= team[i]+System.lineSeparator();
		}
		gameTxt.setText(text);
		
	}
	// send the user back to the main menu
	@FXML
	public void backButton(ActionEvent event) {
		Stage oldStage = (Stage) back.getScene().getWindow();
		oldStage.close();
	}
}
