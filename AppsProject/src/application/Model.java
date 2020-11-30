package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import application.controller.AddPopUpController;
import application.controller.MainController;
import application.controller.TimesRemovePopupController;
import application.controller.foundGameController;
import application.controller.fullArrayController;
import application.controller.popUpController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Model {
	// with it public it can be accessed anywhere with an import
	public static ArrayList<LocalDate> gameDays = new ArrayList<LocalDate>();
	public static HashMap<LocalDate, ArrayList<String>> gameTimes = new HashMap<LocalDate, ArrayList<String>>();
	public static Object[] Team;

	//what executes on mouse clicking a date
	public EventHandler<? super MouseEvent> setDate( LocalDate date , AnchorPaneNode apn) {
		
		Alert a = new Alert(AlertType.NONE);
		// if the array list is full see if they want to delete a game with a pop up
		if(Model.gameDays.size() == Integer.parseInt( MainController.games ) && gameDays.contains(date)) {
		
			Stage stage = new Stage();
			try {
				// this is the pop up with the fxml file
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/fullPopUp.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				stage.setHeight(50.0);
				stage.setWidth(50.0);
				stage.sizeToScene();
				stage.centerOnScreen();
				stage.setTitle("Sports Star Scheduling");
				stage.setScene(scene);
				stage.showAndWait();	
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// if they choose cancel it just returns
			if( fullArrayController.decision == true ) {
				return null;
			}
			// if they choose delete it finds the first objcet that matches and deletes it
			else {
				removeDate(date, apn);
				return null;
			}
			
		}
		// if its a new game date and the array list if full it returns with a message say its full
		if(Model.gameDays.size() == Integer.parseInt( MainController.games )){
			a.setAlertType(AlertType.INFORMATION);
			a.setTitle("Games edit");
			a.setHeaderText("Input for Games");
			a.setContentText("The number of games has reached the limit.");
			a.show();
			return null;
			
		}
		// if the date is in the array it ask the user if it wants to delete the game or add one
		if(gameDays.contains(date)) {
			Stage stage = new Stage();
			try {
				// loads the pop up
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/popUp.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				stage.setHeight(50.0);
				stage.setWidth(50.0);
				stage.sizeToScene();
				stage.centerOnScreen();
				stage.setTitle("Sports Star Scheduling");
				stage.setScene(scene);
				stage.showAndWait();
				
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// if they press add it adds the game
			if( popUpController.option == true ) {
				//room for cleanup here (kinda )
				if(!makeAddPopup(date)){
					gameDays.add(date);
					Label l = new Label(AddPopUpController.timeEntered);
					l.setPrefSize(50, 50);
					l.relocate(5, 5+(15*gameTimes.get(date).size()));
					gameTimes.get(date).add(AddPopUpController.timeEntered);
					apn.getChildren().add(l);
				}
				
			}
			// if its delete finds the first and deletes the match
			else {
				removeDate(date, apn);
				return null;
			}
		}
		// if its a new date it adds it
		else {
			if(!makeAddPopup(date)){
				gameDays.add(date);
				ArrayList<String> tempList = new ArrayList<String>(3);
				tempList.add(AddPopUpController.timeEntered);
				gameTimes.put(date, tempList);
				Label l = new Label(AddPopUpController.timeEntered);
				l.setPrefSize(50, 50);
				l.relocate(5, 5);
				apn.getChildren().add(l);
			}
		}
		// sorts the games 
		Collections.sort(gameDays);
		Collections.sort(gameTimes.get(date));	
		return null;
	}

	
	/*	makeAddPopup
	 * 	makes the pop-up associated with adding a new date to your schedule. returns true if the user completes
	 * 	the operation to add a date. False if they cancel.
	 */
	private boolean makeAddPopup(LocalDate date){
		Stage popup = new Stage();
		try{
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("/application/view/addPopUp.fxml"));
			Scene scene = new Scene(root);
			popup.setResizable(false);
			popup.setTitle("Add "+date+"?");
			popup.setScene(scene);
			popup.showAndWait();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return AddPopUpController.isCanceled;
	}
	
	private void makeRemovePopup(LocalDate date){
		Stage popup = new Stage();
		try{
			TimesRemovePopupController.times = gameTimes.get(date);
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("/application/view/TimesRemovePopup.fxml"));
			Scene scene = new Scene(root);
			popup.setResizable(false);
			popup.setTitle("Which times to remove?");
			popup.setScene(scene);
			popup.showAndWait();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//code for deleting a date.
	private void removeDate(LocalDate date, AnchorPaneNode apn){
		gameDays.remove(gameDays.indexOf(date));
		//only one time exists for this date.
		if(gameTimes.get(date).size() == 1){
			gameTimes.get(date).remove(0);
			apn.getChildren().remove(1);
		}
		//more than one time exists for this date.
		else{
			makeRemovePopup(date);
			if(TimesRemovePopupController.time3Choice){
				gameTimes.get(date).remove(2);
				apn.getChildren().remove(3);
			}
			if(TimesRemovePopupController.time2Choice){
				gameTimes.get(date).remove(1);
				apn.getChildren().remove(2);					
			}
			if(TimesRemovePopupController.time1Choice){
				gameTimes.get(date).remove(0);
				apn.getChildren().remove(1);
			}
		}
	}
	
	public void teamSearch(String team) throws IOException {
		File schedule = new File(team+".txt");
		schedule.createNewFile();
		
	}

	//should allow us to remove existing games or add on a second one
	private ArrayList<String> gameExists(ArrayList<String> gameDays, String game, String secondGame) {
		boolean remove= true;
		String selection ="";
		// choose to remove game or add second. only present add second if i <2
		System.out.println("Game already exists as: "+game);
		if(remove){
			selection=secondGame;
			System.out.println("Game "+ selection+" has been removed");
			//present games, get text from selection
			gameDays.remove(selection);
		}
		
		return gameDays;
	}
	
	public static void makeLabelsForNode(AnchorPaneNode apn){
		if(gameTimes.get(apn.getDate()) != null){
			int i = 0;
			for(String time: gameTimes.get(apn.getDate())){
				Label l = new Label(time);
				l.setPrefSize(50, 50);
				l.relocate(5, 5+(15*i));
				apn.getChildren().add(l);
				i++;
			}
		}
	}
	public void clearGames() {
		gameDays.clear();
		gameTimes.clear();
	}
	
	public ArrayList<String> scanFile(String sentName) {
		ArrayList<String> Team = new ArrayList<String>();
	try{
	//clear the arrays in case they are used more than once
		Team.clear();
		FileInputStream input = new FileInputStream("schedule.txt");
		BufferedReader read = new BufferedReader(new InputStreamReader(input));
		//read each item into a string before formatting
		String searchingNames = "x";
		//continue until user is found or end of file
		while((searchingNames = read.readLine())!=null){
			// read through all the teams
			if(searchingNames.matches("Team: "+sentName)){
				Team.add(searchingNames);
				while((searchingNames = read.readLine())!=null && searchingNames.contains("-")==true){
					//add all of teams lines to an array to be deleted or printed
					Team.add(searchingNames);
					//System.out.println(searchingNames.substring(searchingNames.lastIndexOf(": ") + 2));
				}
				input.close();
				read.close();
				return Team;
			}
		}
		input.close();
		read.close();
	}catch(IOException e){
		e.printStackTrace();
	}
	return Team;
}
	public void teamExists(ArrayList<String> searchTeam) throws IOException {
		//popup deciding choice
		
		Stage stage = new Stage();
		try {
			// this is the pop up with the fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/foundGame.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setHeight(50.0);
			stage.setWidth(50.0);
			stage.sizeToScene();
			stage.centerOnScreen();
			stage.setTitle("Sports Star Scheduling");
			stage.setScene(scene);
			stage.showAndWait();
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Team = searchTeam.toArray();
			
		if(foundGameController.deleteTeam == true){
			//reads the file, outputs copy to tempFile.txt. will then be renamed to shedule.txt to replace
			//with the team removed, allowing for new entry
			File inputFile = new File("schedule.txt");
			File tempFile = new File("tempFile.txt");
		
			try {
			BufferedReader reader;
				reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
				String currentLine;
				int i = 0;
		
				while((currentLine = reader.readLine()) != null) {
				    if(i<Team.length && currentLine.equals(Team[i])){
				    i++;
				    }
				    else{
				    writer.write(currentLine + System.getProperty("line.separator"));}
				}
			writer.close(); 
			reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			inputFile.delete();
			tempFile.renameTo(inputFile);
		}
		else{
			//show the previously entered data
	    	try {
		    	Stage stage1 = new Stage();
		    	Parent root = FXMLLoader.load(getClass().getResource("/application/view/oldTeam.fxml"));
				stage1.setTitle("Sports Star Scheduling");
				stage1.setScene(new Scene(root, 500.0, 352.0));
				stage1.setHeight(100.0);
				stage1.setWidth(500.0);
				stage1.sizeToScene();
				stage1.centerOnScreen();
				stage1.setResizable(false);
				stage1.showAndWait();
	    	} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
