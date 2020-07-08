package View;

import algorithms.mazeGenerators.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CharacterDisplayer extends Canvas  {
    private Position lastPos;


    public void DrawCharacter(Position fullStepPos,int numOfRowsInBoard,int numOfColsInBoard) throws FileNotFoundException, InterruptedException {


        double cellHeight = this.getHeight()/numOfRowsInBoard;
        double cellWidth = this.getWidth()/numOfColsInBoard;
        Image roadImage =null;
        Image charectersImage= null;
        try {

            roadImage = new Image(new FileInputStream("./resources/images/road.jpeg"));
            charectersImage = new Image(new FileInputStream("./resources/images/charecters.png"));

        }catch (FileNotFoundException e){
            System.out.println("File dont found");
        }

        // first clear the last position

        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0,0,this.getWidth(),this.getHeight());
        // first clear only the specific cell
        if (lastPos!=null) {
//            gc.drawImage(roadImage, lastPos.getColumnIndex()*cellWidth, lastPos.getRowIndex()*cellHeight, cellWidth, cellHeight);

            gc.drawImage(charectersImage,fullStepPos.getColumnIndex()*cellWidth,fullStepPos.getRowIndex()*cellHeight,cellWidth,cellHeight);
            this.lastPos = fullStepPos;
        }
            else{

            gc.drawImage(charectersImage,fullStepPos.getColumnIndex()*cellWidth,fullStepPos.getRowIndex()*cellHeight,cellWidth,cellHeight);
            this.lastPos = fullStepPos;
        }



    }
}
