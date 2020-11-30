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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class previewController implements Initializable{
	
	@FXML
	private TextArea gameTxt;
	
	@FXML
	private Button finish;
	
	@FXML
	private Button back;
	
	@FXML
	private Button newTeam;
	
	@FXML
	public Pane calendarPane;
	
	@FXML
	private TextField teamName;
	
	@FXML
	private TextField gameNumber;
	
	String text = "";
	// this will run as soon as the submit button is pressed and sets the text area to the games
	@Override
	public void initialize(URL location, ResourceBundle resources){
		int j = 0;
		text += "Team: " + MainController.team + System.lineSeparator();
		for(int i = 0; i < Model.gameDays.size(); i = i+0) {
			for(j = 0; j < Model.gameTimes.get( Model.gameDays.get(i) ).size(); ++j ) {
				
				text += "Game " + (i + j + 1) + ": " + Model.gameDays.get(i).toString() + ", Game time: " + Model.gameTimes.get(Model.gameDays.get(i)).get(j) +System.lineSeparator();
				
			}
			i = i + j;
		}
		gameTxt.setText(text);
		
	}
	// this will write the file once finish is pressed it should work but we should check once the submit for
	// the calendar is made
	@FXML
	public void finishButton(ActionEvent event) {
		Stage oldStage = (Stage) finish.getScene().getWindow();
		oldStage.close();
		try {
			File file = new File("schedule.txt");
			file.createNewFile();
			// makes the writer
			FileWriter writer = new FileWriter(file, true );
				/* writes the class string to the file in this format
				 * Game 1: 11-20-2020 
				 * Game 2: 11-21-2020
				 * */
			writer.write(text+System.lineSeparator());
				
			writer.close();	
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	// this will call the calendar back to edit games
	@FXML
	public void backButton(ActionEvent event) {
		try {
			 
			calendarPane = FXMLLoader.load(getClass().getResource("/application/view/fullCalendar.fxml"));
			//Scene scene = new Scene(calendarPane);
			Scene scene1 = (new Scene(new FullCalendarView(YearMonth.now()).getView()));
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			//window.setScene(scene);
			window.setScene(scene1);
			window.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void newButton(ActionEvent event) {
		Stage oldStage = (Stage) newTeam.getScene().getWindow();
		oldStage.close(); 
		Model.gameDays.clear();
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("schedule.txt", true)));
		    out.println(text);
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		text = "";
		try {
			Model mod = new Model();
    		mod.clearGames();
    		Stage stage = new Stage();
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("/application/view/Main.fxml"));
			Scene scene = new Scene(root,430,350);
			scene.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
			stage.setResizable(false);
			stage.setTitle("Sports Star Scheduling");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
