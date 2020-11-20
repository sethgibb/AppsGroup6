package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import application.controller.MainController;
import application.controller.fullArrayController;
import application.controller.popUpController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;

public class Model {
	// with it public it can be accessed anywhere with an import
	public static ArrayList<LocalDate> gameDays = new ArrayList<LocalDate>();

	//what executes on mouse clicking a date
	public EventHandler<? super MouseEvent> setDate( LocalDate date ) {
		
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
			if( fullArrayController.decesion == true ) {
				
				return null;
				
			}
			// if they choose delete it finds the first objcet that matches and deletes it
			else {
				
				for(int i = 0; i < gameDays.size(); ++i) {
					if( date == gameDays.get(i) ) {
						gameDays.remove(i);
						break;
					}
				}
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
				
				gameDays.add(date);
				
			}
			// if its delete finds the first and deletes the match
			else {
				System.out.print(date.toString());
				for(int i = 0; i < gameDays.size(); ++i) {
					
					if( date == gameDays.get(i) ) {
						gameDays.remove(i);
						break;
					}
				}
			}
		}
		// if its a new date it adds it
		else {
			gameDays.add(date);
		}
		// sorts the games 
		Collections.sort(gameDays);
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
	

}
