package View;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeSolutionDisplayer extends Canvas {


    @Override
    public boolean isResizable() {
        return true;
    }
    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }


    public void Clear()
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0,0,this.getWidth(),this.getHeight());
    }
    public void DrawSolution(Solution solution,int numOfRowsInBoard,int numOfColsInBoard,Position goalPosition) throws FileNotFoundException {
        int x,y;

        double cellHeight = this.getHeight()/numOfRowsInBoard;
        double cellWidth = this.getWidth()/numOfColsInBoard;
        Image solutionImage= null;
        try {

            solutionImage = new Image(new FileInputStream("./resources/images/solution.jpg"));

        }catch (FileNotFoundException e){
            System.out.println("solution.jpg File dont found");
        }

        // first clear the last position

        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0,0,this.getWidth(),this.getHeight());
        // first clear only the specific cell
        for (AState pos:solution.getSolutionPath()) {
            x=((MazeState)pos).getPosition().getColumnIndex();
            y=((MazeState)pos).getPosition().getRowIndex();
            if(!((MazeState) pos).getPosition().equals(goalPosition))
                 gc.drawImage(solutionImage,x*cellWidth,y*cellHeight,cellWidth,cellHeight);
        }
    }
}

