package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

//import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Minesweeper {
	
    //public static final int dimention = SampleController.dimention;
    //public static final int minesGame = SampleController.minesGame;
    //public static final boolean superMine=false;///////
    //public static final CheckBox hasSuper = SampleController.hasSuper;
   // public static List<Tile> allMines=new ArrayList<>();   /////
    public static final int width = 800;
    public static final int height = 600;
    public static int tilesOpened;
    public static Tile[][] grid; 
    
    //public static int numOfMoves;
    //public static int numOfFlags;

    public static Timeline timeline;
    public static Integer StartTime;
    public static Integer timeSeconds;
    
    
    //define the timer
    public static void time() {
        if (timeline != null) {
            timeline.stop();
        }
        StartTime=SampleController.timeGame;
        timeSeconds =StartTime;
 
        // update timerLabel
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                  new EventHandler<ActionEvent>() {
                    // KeyFrame event handler
                    public void handle(ActionEvent event) {
                        timeSeconds--;
                        // update timerLabel
                        SampleController.l6.setText(timeSeconds.toString()); //set the label at every second
                        if(timeSeconds<20)SampleController.l6.setTextFill(Color.RED);
                        if (timeSeconds <= 0) {
                            timeline.stop();
                            //Score.Score(false);
                            File myObj = new File("src\\application\\medialab\\mines.txt"); 
                	        if (myObj.delete()) { 
                	          System.out.println("Deleted the file: " + myObj.getName());
                	        } else {
                	          System.out.println("Failed to delete the file.");
                	        } 
                            Alert alert = new Alert(Alert.AlertType.NONE, "Times up!", ButtonType.OK);
                            alert.show();
            	            if (alert.getResult() == ButtonType.OK) {
            	            	Main.root.setCenter(null);
            	                return;
            	            }
                            //alert.setOnHidden(evt -> System.exit(0));
                            //alert.show();
                        }
                        
                      }
                }));
        timeline.playFromStart();
    }

    
    //creates returns the dashboard
    public static Parent createContent() {    	
        Pane root = new Pane();
        root.setPrefSize(width,height);
        tilesOpened = 0;
        int minesGenerated = 0;
        grid=new Tile[SampleController.dimention][SampleController.dimention];
        for (int y = 0; y < SampleController.dimention; y++) {
            for (int x = 0; x < SampleController.dimention; x++) {
                Tile tile = new Tile (x, y, false, false);
                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        while (minesGenerated < SampleController.minesGame) {
            Random random = new Random();
            int mine_x = random.nextInt(SampleController.dimention);
            int mine_y = random.nextInt(SampleController.dimention);
            Tile tile = grid[mine_x][mine_y];
            if (!tile.hasMine){
                tile.hasMine = true;
            	SampleController.allMines.add(tile);
                minesGenerated++;
            }
        }
        
        for (int y = 0; y < SampleController.dimention; y++) {
            for (int x = 0; x < SampleController.dimention; x++) {
                Tile tile = grid[x][y];
                if (tile.hasMine) {
                    tile.text.setText("\uD83D\uDCA3");
                    continue;
                }
                long mines = getNeighbours(tile).stream().filter(t -> t.hasMine).count();
                if (mines > 0) tile.text.setText(String.valueOf(mines));
                if (mines == 1) {
                    tile.text.setFill(Color.BLUE);
                }
                else if (mines == 2) {
                    tile.text.setFill(Color.GREEN);
                }
                else if (mines == 3) {
                    tile.text.setFill(Color.RED);
                }
                else if (mines == 4) {
                    tile.text.setFill(Color.PURPLE);
                }
                else if (mines == 5) {
                    tile.text.setFill(Color.MAROON);
                }
                else if (mines == 6) {
                    tile.text.setFill(Color.TURQUOISE);
                }
                else if (mines == 7) {
                    tile.text.setFill(Color.BLACK);
                }
                else if (mines == 8) {
                    tile.text.setFill(Color.GRAY);
                }
            }
        }

        return root;
    }

    public static List<Tile> getNeighbours(Tile tile) {

        List<Tile> neighbours = new ArrayList<>();

        int[] points = new int[] {
                -1,-1,
                -1,0,
                -1,1,
                0,-1,
                0,1,
                1,-1,
                1,0,
                1,1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points [i];
            int dy = points [++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (isValid (newX,newY)) {
                neighbours.add(grid[newX][newY]);
            }
        }

        return neighbours;
    }

    public static boolean isValid(int x, int y){
        return (x >= 0 && x < SampleController.dimention && y >= 0 && y < SampleController.dimention);
    }
}

