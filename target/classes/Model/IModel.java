package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.ISearchingAlgorithm;
import javafx.scene.input.KeyEvent;

import java.net.UnknownHostException;
import java.util.Observer;


public interface IModel  {


    void Generate(int row, int col) throws UnknownHostException;

    void Solve(Maze maze, ISearchingAlgorithm algorithm) throws UnknownHostException;


    //void IsGoal(Position curPos);

    void MakeStep(KeyEvent keyEvent);
    void AssignObserver(Observer o);

    void setMaze(Maze maze);

    void setCurPos(Position playerPos);
}
