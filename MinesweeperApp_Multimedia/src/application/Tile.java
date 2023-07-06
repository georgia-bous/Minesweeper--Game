package application;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;


public class Tile extends StackPane {

    public int x, y;
    public boolean hasMine;
    public boolean isOpen = false;
    public boolean flagged = false;
    public boolean hasSuper=false;
    //public static final int tileSize = 400/SampleController.dimention;

    public Rectangle border = new Rectangle (SampleController.tileSize - 2, SampleController.tileSize - 2);
    public Text text = new Text();
    public Text flag = new Text();



    
    /**
     * Constructor of a Tile object
     * 
     * @param x the position of the tile in the x axis
     * @param y the position of the tile in the y axis
     * @param hasMine boolean parameter that indicates if the tile has a mine
     * @param hasSuper boolean parameter that indicates if the tile has a mine
     * @see SampleController
     */
    public Tile (int x, int y, boolean hasMine, boolean hasSuper) {
    	//define the characteristics of the tiles
        this.x = x;
        this.y = y;
        this.hasMine = hasMine;
        this.hasSuper= hasSuper;

        border.setStroke(Color.LIGHTGRAY);
        border.setFill(Color.WHITE);
        flag.setText ("");
        flag.setFill(Color.RED);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        flag.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        text.setVisible(false);

        getChildren().addAll(border,text,flag);

        setTranslateY(x * SampleController.tileSize);
        setTranslateX(y * SampleController.tileSize);

        setOnMouseClicked(e ->
        {
            if (e.getButton() == MouseButton.PRIMARY)
            {
            	SampleController.numOfMoves++;
            	SampleController.numOfTries++; //only left clicks are counted in the number of tries, need it for the score
                open();
            } else if (e.getButton() == MouseButton.SECONDARY)
            {
            	SampleController.numOfMoves++;
                setFlag();
            }
        });
    }

    
    /**
     * This method is called when the user left clicks on a tile
     * 
     * @see SampleController
     * @see Minesweeper
     */
    public void open(){
    	if(text.getText()=="X") {
    		Alert alert=new Alert(Alert.AlertType.NONE, "Cannot open tile with bomb", ButtonType.OK);
    		alert.showAndWait();
    		return;
    	}
        if (isOpen) return;
        isOpen = true;
        text.setVisible(true);
        flag.setText ("");
        if(flagged) {
        	SampleController.numOfFlags--;
        	SampleController.l4.setText(Integer.toString(SampleController.numOfFlags));
        }
        border.setFill(Color.LIGHTGRAY);
        
        if (hasMine) {
        	finish(false, false);
        }
        
        
        if (Minesweeper.tilesOpened == SampleController.dimention * SampleController.dimention - SampleController.minesGame - 1) {
        	finish(true, false);//every other tile is bomb
        } else{
            Minesweeper.tilesOpened++;
        }

        if (text.getText().isEmpty()){
            Minesweeper.getNeighbours(this).forEach(Tile::open);
        }
    }
    
    
    /**
     * This method is called when user flags the superbomb in the first 4 tries, for all the tiles that will be opened, differs from open() in that it doesn't recursively reveal other tiles
     * 
     * @see SampleController
     * @see Minesweeper
     */
    public void openSuper() {
    	if (isOpen) return;
        isOpen = true;
        text.setVisible(true);
        flag.setText ("");
        if(flagged) {
        	SampleController.numOfFlags--;
        	SampleController.l4.setText(Integer.toString(SampleController.numOfFlags));
        }
        border.setFill(Color.LIGHTGRAY);
        

        if (Minesweeper.tilesOpened == SampleController.dimention * SampleController.dimention - SampleController.minesGame - 1) {
        	finish(true, false);
        }
        if (hasMine) {
            text.setText("X");
            //text.setFill(Color.MAGENTA);
        }
        else{
            Minesweeper.tilesOpened++;
        }
    }


    
    /**
     * This method is called when user right clicks on a tile
     * 
     * @see SampleController
     * @see Minesweeper
     */
    public void setFlag(){
    	if (isOpen) return;
        if (flagged) {
        	SampleController.numOfFlags--;
        	SampleController.l4.setText(Integer.toString(SampleController.numOfFlags));
            flagged = false;
            flag.setText ("");
            return;
        }
    	if(SampleController.numOfFlags==SampleController.minesGame) {
    		Alert alert=new Alert(Alert.AlertType.NONE, "Number of flags cannot exceed number of mines.", ButtonType.OK);
    		alert.showAndWait();
    		return;
    	}
    	SampleController.numOfFlags++;
    	SampleController.l4.setText(Integer.toString(SampleController.numOfFlags));
        flagged = true;
        //flag.setText ("⚐");
        flag.setText("\ud83d\udea9");
        if(hasSuper && SampleController.numOfMoves<=4){ 
        	for(int i=0; i<SampleController.dimention; i++) {
        		Minesweeper.grid[this.x][i].openSuper();
        	}
        	for(int i=0; i<SampleController.dimention; i++) {
        		Minesweeper.grid[i][this.y].openSuper();
        	}
            	
        }        
    }
    
    
    
    /**
     * This method is called when the game is over
     * 
     * @param won boolean parameter that indicates whether the game is won or lost
     * @param showedSol boolean parameter that indicates wheter the user saw the solution or not
     * @see Minesweeper
     * @see Score
     */
    public static void finish(boolean won, boolean showedSol) {
    	Minesweeper.timeline.stop();
    	SampleController.dimention=0; //initialise again
    	//Score s=new Score(won);
    	//SampleController.scores.add(SampleController.scores.size(), s);
    	//if(SampleController.scores[0]==null)SampleController.scores[0]=s;
       	//else if(SampleController.scores[1]==null)SampleController.scores[1]=s;
    	Score.save(won);
        File myObj = new File("src\\application\\medialab\\mines.txt"); 
        if (myObj.delete()) { 
          System.out.println("Deleted the file: " + myObj.getName());
        } else {
          System.out.println("Failed to delete the file.");
        } 

        if(won) {
        	Alert alert = new Alert(Alert.AlertType.NONE, "You won. Well played!", ButtonType.YES);
        	alert.showAndWait();
        	if (alert.getResult() == ButtonType.YES) {
                //System.exit(0);
            	Main.root.setCenter(null);
                return;
            }
        }
        else if (!showedSol){
        	Alert alert = new Alert(Alert.AlertType.NONE, "You lost. Good game!", ButtonType.YES);
        	alert.showAndWait();
        	if (alert.getResult() == ButtonType.YES) {
                //System.exit(0);
            	Main.root.setCenter(null);
                return;
            }
        }
        else return;
    }
}
