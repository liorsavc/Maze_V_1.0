package Model;


import Client.Client;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {
    // check if we need to hold vm here !

    private IMazeGenerator mazeGenerator;
    private ISearchingAlgorithm algorithm;
    private Maze maze;
    private Position curPos;
    private Logger logger = (Logger) LogManager.getLogger(this.getClass());





    public Position getCurPos() {
        return curPos;
    }

    public MyModel() {
    }

    @Override
    public void setMaze(Maze maze) {
        this.maze = maze;

    }

    @Override
    public void setCurPos(Position playerPos) {
        this.curPos = playerPos;
    }

    @Override
    public void Generate(int row, int col) throws UnknownHostException {
//        this.maze =  this.mazeGenerator.generate(row,col);
        ClientStrategyGenerate clientStrategyGenerate = new ClientStrategyGenerate(row,col);
        Client client = new Client(InetAddress.getLocalHost(), 5400,clientStrategyGenerate);
        client.communicateWithServer();
        this.maze = clientStrategyGenerate.getMaze();
        logger.info("successfully generated maze with "+row+"rows and "+col+" columns");


        /*********************************************************************/
        curPos=this.maze.getStartPosition();
        setChanged();
        notifyObservers(this.maze);
        setChanged();
        notifyObservers(curPos);
    }



    @Override
    public void Solve(Maze maze,ISearchingAlgorithm algorithm) throws UnknownHostException {

        // explain !!!!
//        ClientStrategySolve clientStrategySolve = new ClientStrategySolve(maze);
//        Client client = new Client(InetAddress.getLocalHost(), 5401,clientStrategySolve);
//        client.communicateWithServer();
//        Solution tempSol=clientStrategySolve.getSolution();

        SearchableMaze searchableMaze = new SearchableMaze(maze);

        Solution tempSol = algorithm.solve(searchableMaze);

        /**************************************************************/
        setChanged();
        notifyObservers(tempSol);
    }


    private boolean CanMakeStep(Position curPos) {
        // this func check index out of range or wall
        if (curPos.getRowIndex() > maze.getRow()-1 || curPos.getRowIndex() < 0 )
            return false; // step out from maze range
        if (curPos.getColumnIndex() > maze.getCol() -1 || curPos.getColumnIndex()<0)
            return false; // step ot from maze range
        //check if step goes into a wall (if so return false)
        return !maze.isWall(curPos);
    }


    private void IsGoal(Position curPos)
    {
        boolean isGoal =  curPos.equals(maze.getGoalPosition());

        if  (isGoal){
            setChanged();
            notifyObservers("isGoal");
        }
    }

    @Override
    public void MakeStep(KeyEvent keyEvent) {
        Position temp = new Position(curPos.getRowIndex(),curPos.getColumnIndex());
        switch (keyEvent.getCode()){
            case NUMPAD1:
                temp.setRowIndex(temp.getRowIndex()+1);
                temp.setColumnIndex(temp.getColumnIndex()-1);
                break;
            case NUMPAD2:
                temp.setRowIndex(temp.getRowIndex()+1);
                break;
            case NUMPAD3:
                temp.setRowIndex(temp.getRowIndex()+1);
                temp.setColumnIndex(temp.getColumnIndex()+1);
                break;
            case NUMPAD4:
                temp.setColumnIndex(temp.getColumnIndex()-1);
                break;
            case NUMPAD6:
                temp.setColumnIndex(temp.getColumnIndex()+1);
                break;
            case NUMPAD7:
                temp.setRowIndex(temp.getRowIndex()-1);
                temp.setColumnIndex(temp.getColumnIndex()-1);
                break;
            case NUMPAD8:
                temp.setRowIndex(temp.getRowIndex()-1);
                break;
            case NUMPAD9:
                temp.setRowIndex(temp.getRowIndex()-1);
                temp.setColumnIndex(temp.getColumnIndex()+1);
                break;
            default:break;
        }
        if (CanMakeStep(temp))
        {
            this.curPos = temp;
            setChanged();
            notifyObservers(this.curPos);
            IsGoal(curPos);
        }
//        setChanged();
//        notifyObservers("CannotMakeStep");

    }

    @Override
    public void AssignObserver(Observer o) {
        this.addObserver(o);

    }

}
