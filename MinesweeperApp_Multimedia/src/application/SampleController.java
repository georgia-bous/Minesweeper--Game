package application;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
//import javafx.stage.Popup;
//import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.control.TextField;


public class SampleController extends BorderPane {
	
	@FXML public TextField filename;
	@FXML public TextField level;
	@FXML public TextField numMines;
	@FXML public TextField time;
	@FXML public CheckBox hasSuper;
	@FXML public CheckBox hasnotSuper;
	
	
	//public String fileLoaded;
	public static int dimention, minesGame, timeGame;
	public boolean hasSuperGame;
	public String fileGame;
	public static int tileSize;
	
    @FXML public AnchorPane gameWindow;
    @FXML public GridPane gameGrid;
    
    public static int numOfMoves, numOfFlags, numOfTries;
    
    //public static ArrayList<Score> scores=new ArrayList();
   // public static Score[] scores=new Score[5];
    
    public static List<Tile> allMines=new ArrayList<>();   /////
    public static Label l4;
    public static Label l6;

	
	//is called when the user clicks "Create a new scenario" in the initial menu of the app
	public void createWindow() {
				
		Stage popupwindow=new Stage();	      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Create a new scenario");
		      
		try {
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("create.fxml"));
		Scene scene1 = new Scene(root,500,400);
		popupwindow.setScene(scene1);
		}catch(Exception e) {
			e.printStackTrace();
		}

		popupwindow.show();		
		
	}
	
	
	//is called when the user clicks the "Create" button in the "Create a new scenario" dialog
	public void createScenario() {
		
		//check if all fields have been entered
		if(filename.getText().isEmpty() || level.getText().isEmpty() || numMines.getText().isEmpty() || time.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.NONE, "Enter all the fields.", ButtonType.OK);			
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }
		}
		try {
			//create a new file with the inserted fields
			BufferedWriter writer=new BufferedWriter(new FileWriter("src\\application\\medialab\\"+ filename.getText()+".txt", false)); 
			writer.write(level.getText());
			writer.write("\n"+numMines.getText());			
			writer.write("\n"+time.getText());
			if(hasSuper.isSelected())writer.write("\n1");
			else if(hasnotSuper.isSelected()) writer.write("\n0");
			writer.close();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
		Stage stage = (Stage) filename.getScene().getWindow();
		stage.close();
	}
	
	
	public void cancel() {
		Stage stage = (Stage) filename.getScene().getWindow();
		stage.close();
    }
	
	
	//is called when user clicks the "Load a scenario" in the initial menu of the app
	public void loadWindow() {
		Stage popupwindow=new Stage();	      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Load a scenario");
		
		HBox hbox=new HBox();
		Label label=new Label("Load a file");
		label.setPrefWidth(140);
		label.setFont(Font.font("Georgia", FontWeight.BOLD, 20));
		TextField file=new TextField();
		file.setPrefWidth(140);
		
		Button loadB=new Button("Load");
		loadB.setPrefWidth(90);
		loadB.setOnAction(e -> {
			try {	
				loadScenario(file);
			}catch(Exception ex) {
				System.out.println("Try again, something went wrong");
			}
		});
		
		Button exitB=new Button("Exit");
		exitB.setPrefWidth(70);
		exitB.setOnAction(e -> popupwindow.close());
		        
		hbox.setPadding(new Insets(10));
		hbox.setStyle("-fx-background-color: white;");

		hbox.getChildren().addAll(label, file,new Separator(), loadB, new Separator(), exitB);		
		Scene scene2 = new Scene(hbox,500,200);
		popupwindow.setScene(scene2);		
		popupwindow.show();		
	}
	
	
	//is called when user clicks Load in the "Load a scenario" dialog
	public void loadScenario(TextField fileLoaded) throws InvalidDescriptionException, InvalidValueException {
		String[] input= new String[5];
		
		//read the loaded file
		try(BufferedReader reader = new BufferedReader(new FileReader( "src\\application\\medialab\\"+ fileLoaded.getText()+".txt"))){
			for(int i=0; i<5; i++) {
				input[i]=reader.readLine();
			}
			reader.close();				
				
			// check for IDE
			try  {     
				InvalidDescriptionException.validate(input[0], input[1], input[2], input[3], input[4]);
			}  
			catch (InvalidDescriptionException ex){  
				Alert alert = new Alert(Alert.AlertType.NONE, "IDE:Number of arguments is incorrect.", ButtonType.OK);
	            alert.showAndWait();
	            if (alert.getResult() == ButtonType.OK) {
	                return;
	            }
				System.out.println("Exception occured: " + ex);  
			}  
				
			//check for IVE		
			try {
				InvalidValueException.validate(input[0], input[1], input[2], input[3]);
			}
			catch(InvalidValueException ex){
				Alert alert = new Alert(Alert.AlertType.NONE, "IVE:Values of arguments ar invalid.", ButtonType.OK);
	            alert.showAndWait();
	            if (alert.getResult() == ButtonType.OK) {
	                return;
	            }
				System.out.println("Exception occured: " + ex);  
			}
				 
		
		}catch (IOException e) {
	        System.err.format("IOException: %s%n", e);
	        Alert alert = new Alert(Alert.AlertType.NONE, "This scenario does not exist", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }

	    }
		//fileGame is the last loaded file, the one which will be loaded when "Start playing" is clicked
		fileGame=fileLoaded.getText();
		Stage stage = (Stage) fileLoaded.getScene().getWindow();
		stage.close();
		
		 	    
	}
	
	
	//is called when "Exit" (from the game) is clicked
	public void exit() {
		if(fileGame!=null) {
			File myObj = new File("src\\application\\medialab\\mines.txt"); 
	        if (myObj.delete()) { 
	          System.out.println("Deleted the file: " + myObj.getName());
	        } else {
	          System.out.println("Failed to delete the file.");
	        } 
		}
        System.exit(0);
    }
	
	
	
	//is called when user clicks "Start playing" in the initial menu of the app
    public void launch() {
    	//a file has not be loaded
    	if(fileGame==null) {
    		Alert alert = new Alert(Alert.AlertType.NONE, "You should first load a file", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }
    	}
    	
    	String[] input= new String[5];
		//read the loaded file
		try(BufferedReader reader = new BufferedReader(new FileReader( "src\\application\\medialab\\"+ fileGame+".txt"))){
			for(int i=0; i<4; i++) {
				input[i]=reader.readLine();
			}
			reader.close();	
		}catch(IOException ex){
		}
		//initialize the parameters of the game
		if(Integer.parseInt(input[0])==1)dimention=9;
		else dimention=16;
		tileSize = 400/dimention;
		minesGame=Integer.parseInt(input[1]);
		timeGame=Integer.parseInt(input[2]);
		if(Integer.parseInt(input[3])==1)hasSuperGame=true;
		else hasSuperGame=false;
		numOfMoves=0;
        numOfFlags=0;
        numOfTries=0;
	    
        VBox vbox=new VBox();
        HBox hboxHead=new HBox();   //contains the labels of the game
        Label l1=new Label("Mines: ");        
        Label l2=new Label(Integer.toString(minesGame));
        Label l3=new Label("   Flags: ");
        l4=new Label(Integer.toString(numOfFlags));        
        Label l5=new Label("   Seconds left: ");       
        l6=new Label(Integer.toString(timeGame));
        l1.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        l2.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        l3.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        l4.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        l5.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        l6.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        hboxHead.setPadding(new Insets(8));
        hboxHead.getChildren().addAll(l1, l2, l3, l4, l5, l6);
        
        /*
        HBox hboxBottom=new HBox();
        Button solutionB=new Button("Show Solution");     
        solutionB.setOnAction(e->{
        	Minesweeper.timeline.stop();
        	Tile t;
        	for(int i=0; i<dimention; i++) {
        		for(int j=0; j<dimention; j++) {
        			t=Minesweeper.grid[i][j];
        			if (t.isOpen) continue;
        	        t.isOpen = true;
        	        t.text.setVisible(true);
        	        t.flag.setText ("");
        	        if(t.flagged) {
        	        	numOfFlags--;
        	        	SampleController.l4.setText(Integer.toString(numOfFlags));
        	        }
        	        t.border.setFill(Color.LIGHTGRAY);
        		}
        	}
        	Tile.finish(false);
        });
        hboxBottom.getChildren().addAll(solutionB);
        */
        
        vbox.getChildren().addAll(hboxHead, Minesweeper.createContent());// Minesweeper.createContent() returns the dashboard of the game        
        Main.root.setCenter(vbox);
    	
        //if there is a superbomb in the game, pick randomly a tile from the ones which have mine and make it superbomb
	    if(hasSuperGame) {
	    	Random random=new Random();
	    	int superM=random.nextInt(allMines.size());
	    	Tile t=allMines.get(superM);
	    	t.hasSuper=true;
	    }

	    //write a file with the mines
	    try {
			BufferedWriter writer=new BufferedWriter(new FileWriter("src\\application\\medialab\\mines.txt", false));
			int s;
			if(allMines.get(0).hasSuper)s=1;
			else s=0;
			writer.write(allMines.get(0).x+","+allMines.get(0).y+","+s);
			for(int i=1; i<allMines.size(); i++) {
				Tile t=allMines.get(i);
				if(t.hasSuper)s=1;
				else s=0;
				writer.write("\n"+t.x+","+t.y+","+s);
			}
			
			writer.close();
			
		}
		catch(IOException ex){
			ex.printStackTrace();
			
		}
	    
	    //start the timer
	    Minesweeper.time();	  
	
	}
    
    //is called when user clicks "Show solution" 
    public void showSolution() {
    	//check if a game has been started, if not then dimention variable has not been initialised, which means it has default value of 0
    	if(dimention==0) {
    		Alert alert = new Alert(Alert.AlertType.NONE, "You should first start a game.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }
    	}
    	else {
    	Tile t;
    	for(int i=0; i<dimention; i++) {
    		for(int j=0; j<dimention; j++) {
    			t=Minesweeper.grid[i][j];
    			if (t.isOpen) continue;
    	        t.isOpen = true;
    	        t.text.setVisible(true);
    	        t.flag.setText ("");
    	        if(t.flagged) {
    	        	numOfFlags--;
    	        	SampleController.l4.setText(Integer.toString(numOfFlags));
    	        }
    	        t.border.setFill(Color.LIGHTGRAY);
    		}
    	}
    	//the game is saved as a lost one
    	Tile.finish(false, true);
    	}
    }
    
    //is called when user clicks "Show sscore" in the initial menu of the app
    public void scoreWindow() {
    	Stage popupwindow=new Stage();	      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("These are your latest games:");
		Label l0=new Label();
		Label l1=new Label();
		Label l2=new Label();
		Label l3=new Label();
		Label l4=new Label();
		Label l5=new Label();
		VBox v=new VBox();
		Button exitB=new Button("Exit");
		exitB.setOnAction(e -> popupwindow.close());
		      
		try {
		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root,500,400);
		popupwindow.setScene(scene);
		l0.setText("Latest games"+"     "+"Total mines"+"     "+"Total tries"+"      "+"Total time"+"      "+"Result");
		
		if(Score.score[0]!=null)l1.setText(1+Score.score[0]);
		if(Score.score[1]!=null)l2.setText(2+Score.score[1]);
		if(Score.score[2]!=null)l3.setText(3+Score.score[2]);
		if(Score.score[3]!=null)l4.setText(4+Score.score[3]);
		if(Score.score[4]!=null)l5.setText(5+Score.score[4]);
		
		v.getChildren().addAll(l0, l1, l2, l3, l4, l5, exitB);
		root.getChildren().add(v);
		popupwindow.show();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	


}
	