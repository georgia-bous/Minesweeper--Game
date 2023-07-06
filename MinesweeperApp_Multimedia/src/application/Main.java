package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Scene game;
	public static BorderPane root;
	@Override
	public void start(Stage primaryStage) throws Exception{
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("menu.fxml"));
			game= new Scene(root,400,500);
			primaryStage.setTitle("MediaLab Minesweeper");
			game.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(game);
			primaryStage.resizableProperty().setValue(Boolean.TRUE);
			ImageView img = new ImageView();
			Image image = new Image("file:src/images/bomb.jpg");
			img.setImage(image);
			root.setCenter(img);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
