package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import application.controller.AddPopUpController;
import application.controller.MainController;
import application.controller.fullArrayController;
import application.controller.popUpController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Model {
	// with it public it can be accessed anywhere with an import
	public static ArrayList<LocalDate> gameDays = new ArrayList<LocalDate>();
	public static HashMap<LocalDate, ArrayList<String>> gameTimes = new HashMap<LocalDate, ArrayList<String>>();

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
				gameDays.remove(gameDays.indexOf(date));
				//TODO: prompt the user for days that have multiple games to choose which time to remove.
				gameTimes.get(date).remove(0);
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
					//TODO: make a way so multiple labels don't overlap
					gameTimes.get(date).add(AddPopUpController.timeEntered);
					Label l = new Label(AddPopUpController.timeEntered);
					l.setPrefSize(50, 50);
					apn.getChildren().add(l);
				}
				
			}
			// if its delete finds the first and deletes the match
			else {
				gameDays.remove(gameDays.indexOf(date));
				//TODO: prompt the user for days that have multiple games to choose which time to remove.
				gameTimes.get(date).remove(0);
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
				apn.getChildren().add(l);
			}
		}
		// sorts the games 
		Collections.sort(gameDays);
		Collections.sort(gameTimes.get(date));
		// we will delete this later
		System.out.println(gameDays.toString());
	
		return null;
	}
	/****************************************
	 * im not 100 percent that we need the rest but if you think we do can you explain im not sure tho
	 * 
	 * 
	 * 
	 * 
	 * ****************************************************/
	
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
	public void teamSearch(String team) throws IOException {
		File schedule = new File(team+".txt");
		schedule.createNewFile();
		
	}

	//hopefully this can be used anywhere we need the arraylist of gameDays
	//will find matching dates and return differing prompts if there are 0,1,2 games
	public ArrayList<LocalDate> readFile(String fileName, String newGame) throws IOException {
		//clear the array in case it is used more than once
		gameDays.clear();
		String game = "x";
		String foundGame = "",secondGame="";
		int i =0;
		
		FileInputStream input = new FileInputStream(fileName);
		BufferedReader read = new BufferedReader(new InputStreamReader(input));
		
		//read all games into arraylist, find if selected day already has a game
		while((game = read.readLine())!=null && game.length()!=0 ){
			//get next game
			
			//add games to arraylists
			gameDays.add(game);
			
			if(game.matches(".*"+newGame)){
				//popout window to delete game or add second game
				if(i==1){
					secondGame=game;
				}
				else{
				foundGame = game;
				}
				i++;
			}
			
		}
		if(i>1){
			System.out.println("There are already 2 games on that day");
			gameExists(gameDays, foundGame,secondGame);
		}
		else if(foundGame !=""){
			gameDays=gameExists(gameDays, foundGame, secondGame);
		}
		read.close();
		return gameDays;
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
			//TODO: make a way so multiple labels don't overlap
			for(String time: gameTimes.get(apn.getDate())){
				Label l = new Label(time);
				l.setPrefSize(50, 50);
				apn.getChildren().add(l);
			}
		}
	}

}
