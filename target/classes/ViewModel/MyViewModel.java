package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.ISearchingAlgorithm;
import javafx.scene.input.KeyEvent;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;


    public void UpdateModelProperties(Maze maze, Position playerPos)
    {
        this.model.setMaze(maze);
        this.model.setCurPos(playerPos);
    }
    public MyViewModel(IModel m) {
        this.model = m;
        m.AssignObserver(this);
    }

    public void GenerateMaze(int row, int col) throws UnknownHostException {

        model.Generate(row,col);

    }

    public void MakeStep(KeyEvent keyEvent) {
        this.model.MakeStep(keyEvent);


//        if(this.model.MakeStep(keyEvent)){
//                // this step is valid
//                this.curPos = this.model.getCurPos();
//                // send notification to CharacterDisplayer
//                this.setChanged();
//                this.notifyObservers(this.curPos);
//                //check if it was the last step
//                if(this.model.IsGoal(this.curPos))
//                { // the step is goal
//                    this.setChanged();
//                    this.notifyObservers("isGoal");
//                }
//        }


    }

    public void Solve(Maze maze,ISearchingAlgorithm algorithm) throws UnknownHostException {
        this.model.Solve(maze,algorithm);
    }

    @Override
    public void update(Observable o, Object arg) {
        // this function passes the Given args to  all Observer .
        setChanged();
        notifyObservers(arg);
    }
}
