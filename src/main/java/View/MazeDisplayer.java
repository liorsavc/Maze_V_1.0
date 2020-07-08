package View;

import algorithms.mazeGenerators.Maze;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas  {


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

    public void DrawMaze(Maze maze) throws FileNotFoundException {

        if (maze == null || maze.getRow()<2 || maze.getCol()<2 ) {

            return; // invalid maze size !!
        }
        else
            {
            // getting size in pixels
            double canvasHieght = getHeight();
            double canvasWidth = getWidth();
            int row = maze.getRow();
            int col = maze.getCol();
            double cellHeight = canvasHieght/row;
            double cellWidth = canvasWidth/col;
            // this class for drawing in canvas
                GraphicsContext gc = this.getGraphicsContext2D();
                // first clear the last drawing
                gc.clearRect(0,0,canvasWidth,canvasHieght);
                double x,y;
                Image wallImage = null;
                Image roadImage = null;
                Image goalImage = null;
                // draw maze
                try {
                     wallImage = new Image(new FileInputStream("./resources/images/wall.jpeg"));
                     roadImage = new Image(new FileInputStream("./resources/images/road.jpeg"));
                    goalImage = new Image(new FileInputStream("./resources/images/goal.png"));

                    // move to different class

                    }
                catch (FileNotFoundException e)
                {
                    System.out.println("File not found");
                }
                for (int i = 0 ;i<row;i++)
                {
                    for(int j =0;j<col;j++)
                    {
                        x = j * cellWidth;
                        y = i * cellHeight;


                        if (maze.getGoalPosition().getRowIndex()==i && maze.getGoalPosition().getColumnIndex()==j)
                        {

                            /// fix the images
                            gc.drawImage(roadImage,x,y,cellWidth,cellHeight);
                            gc.drawImage(goalImage,x,y,cellWidth,cellHeight);
                            continue;
                        }

                        if (maze.isWall(i,j)){
//                            gc.setFill(Color.RED);
//                            gc.fillRect(x,y,cellWidth,cellHeight);
                            gc.drawImage(wallImage,x,y,cellWidth,cellHeight);
                        }
                        else{
//                            gc.setFill(Color.BLACK);
//                            gc.fillRect(x,y,cellWidth,cellHeight);
                            gc.drawImage(roadImage,x,y,cellWidth,cellHeight);
                        }

                    }
                }
        }
    }
}

