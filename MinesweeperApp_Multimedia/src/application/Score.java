package application;

//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Modality;
//import javafx.stage.Stage;

public class Score {
	public static String[] score=new String[5];
	

	
	public static void save(boolean won) {
		int i; //will be the line that will be written for this round
		if(score[0]==null)i=0;
		else if(score[1]==null)i=1;
		else if (score[2]==null)i=2;
		else if (score[3]==null)i=3;
		else if (score[4]==null)i=4;
		else {
			score[0]=score[1];
			score[1]=score[2];
			score[2]=score[3];
			score[3]=score[4];
			i=4;
		}
		if(won)score[i]=("				"+SampleController.minesGame+"	 		"+(SampleController.numOfTries)+"		"+SampleController.timeGame+"		"+"Won! Congrats!");
		else   score[i]=("				"+SampleController.minesGame+"			"+(SampleController.numOfTries-1)+"		 "+SampleController.timeGame+"		"+"Lost...Try again!");
	}
}
