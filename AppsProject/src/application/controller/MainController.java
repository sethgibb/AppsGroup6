package application.controller;

import java.io.IOException;
import java.time.YearMonth;
import java.util.regex.Pattern;

import application.FullCalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {
	
	@FXML
	public Pane calendarPane;
	
	@FXML
	private TextField teamName;
	
	@FXML
	private TextField gameNumber;
	
	public static String team;
	public static String games;
	
	@FXML
	public void handle1(ActionEvent event) throws IOException {
		team = teamName.getText().toString();
		games = gameNumber.getText().toString();
	    if (teamName.getText() == null || teamName.getText().trim().isEmpty()) {
		   Alert teamAlert = new Alert(AlertType.ERROR);
		   teamAlert.setHeaderText("Team name error");
		   teamAlert.setContentText("Please enter a team name!");
		   teamAlert.showAndWait();
		   return;
	    }
	    if(!(Pattern.matches("[0-9]+", games))) {
	    	Alert gameAlert = new Alert(AlertType.ERROR);
			gameAlert.setHeaderText("Game number error");
			gameAlert.setContentText("Enter a numeric quantity for number of games!");
			gameAlert.showAndWait();
			return;   
	    }
		calendarPane = FXMLLoader.load(getClass().getResource("/application/view/fullCalendar.fxml"));
		//Scene scene = new Scene(calendarPane);
		Scene scene1 = (new Scene(new FullCalendarView(YearMonth.now()).getView()));
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	//	window.setScene(scene);
		window.setScene(scene1);
		window.show();
		
	}
}
