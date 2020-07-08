package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.Solution;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import sample.Main;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Observable;
import org.apache.logging.log4j.Logger;

public class MyViewController implements IView {
    /** connections to FXML file **/
    private MyViewModel vm;
    public MazeDisplayer mazeDisplayer;
    public CharacterDisplayer characterDisplayer;
    public MazeSolutionDisplayer mazeSolutionDisplayer;
    public MenuItem showSolution;

    public MenuItem muteMenuItem;
    public MenuItem unMuteMenuItem;
    public Pane mainPane;
    public BorderPane borderPane;
    public MenuBar menuBar;
    public MenuItem save;
    public Button quickGameBtn;
    private Logger logger = (Logger) LogManager.getLogger(this.getClass());
    /*******************************/


    private Maze maze=null;
    private Position playerPos;
    private Solution solution;



    public void GenerateMaze() throws FileNotFoundException, UnknownHostException {


        quickGameBtn.setVisible(false);
        int rows = Configurations.GetRows();
        int cols = Configurations.GetCols();
        vm.GenerateMaze(rows,cols);

        showSolution.setDisable(false);
        save.setDisable(false);

//        mazeDisplayer.setMaze(this.maze);// important for resizeable canvas
    }

        // My Ctor
       public void SetViewModelConnection(MyViewModel vm)
    {
        // set conection between vm an v!
        this.vm = vm;
        this.logger.info(" connection to viewModel created ");

        // Bind canvas size to stack pane size.
        HandleResize();





    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MyViewModel)
        {
            //this.logger.info(arg.toString()+" recieved from viewModel ");
            if(arg instanceof Maze){
                // Generate Maze !!!!!!!!!!
                this.maze = ((Maze)arg);
                this.playerPos = this.maze.getStartPosition();
                try {
                    this.mazeSolutionDisplayer.Clear();
//                    mazeDisplayer=new MazeDisplayer(this.maze);
                    this.mazeDisplayer.DrawMaze(this.maze);


                    this.characterDisplayer.DrawCharacter(this.playerPos,this.maze.getRow(),this.maze.getCol());

                } catch (FileNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (arg instanceof Position)
            {// Player Moved!!!!!!
                this.playerPos = ((Position)arg);
                try {

                    this.characterDisplayer.DrawCharacter(this.playerPos,this.maze.getRow(),this.maze.getCol());
                } catch (FileNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (arg instanceof Solution) {
                this.solution = ((Solution)arg);
                try {
                    this.mazeSolutionDisplayer.Clear();
                    this.mazeSolutionDisplayer.DrawSolution(this.solution,this.maze.getRow(),this.maze.getCol(),this.maze.getGoalPosition());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if(arg instanceof String && (String)arg=="isGoal") {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setContentText("CONGRATS YOU WON");
//                alert.show();
                try {
                    Main.changeScene("GoalWin");
                } catch (IOException e)
                {e.printStackTrace();

                }
            }
            }


    }


    public void PopMsg(String msg,String title){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(msg);
        alert.show();
    }

    public void MakeStep(KeyEvent keyEvent){
        if(this.maze!=null)
            vm.MakeStep(keyEvent);


    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }



    public void Solve() throws UnknownHostException {
        //update to current player position instead of old start point
        this.maze.setStart(this.playerPos);
        switch (Configurations.getSolveAlgorithm()){
            case "Best First Search":
                vm.Solve(this.maze,new BestFirstSearch());
                break;
            case "Depth First Search":
                vm.Solve(this.maze,new DepthFirstSearch());
                break;
            case "Breadth First Search":
                vm.Solve(this.maze,new BreadthFirstSearch());
                break;

        }

    }

    public void HandleSettingsClick() throws IOException {

        Main.changeScene("Settings");
    }


    public void HandleAboutClick(ActionEvent actionEvent) {
        PopMsg("Programers: Lior Tabachnik and Lior Savchenko\n" +
                "from Ben-Gurion University\n" +
                "The maze generated by Prim's algorithm\n" +
                "Solved by "+Configurations.getSolveAlgorithm()+" algorithm that you chose in settings","About");
    }

    public void MuteSound(ActionEvent actionEvent) {
        Main.MuteSound();
        unMuteMenuItem.setDisable(false);
        muteMenuItem.setDisable(true);

    }

    public void UnMuteSound(ActionEvent actionEvent) {
        Main.UnMuteSound();
        unMuteMenuItem.setDisable(true);
        muteMenuItem.setDisable(false);

    }

    public void SafeExit(ActionEvent actionEvent) {
        Main.SafeExit();
    }

    public void SaveGame(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");//filter for text files
        fileChooser.getExtensionFilters().add(extFilter);//filter to text files only
        File savedFile=fileChooser.showSaveDialog(Main.primaryStage);
        if(savedFile==null)
            return;//user clicked cancel
        FileOutputStream fos = new FileOutputStream(savedFile);

        // changing start pos to cur pos for saving and load the currect pos
        byte[] tempmaze = maze.toByteArray();
        if (playerPos.getRowIndex()>255){
            tempmaze[4] = ((byte)255);
            tempmaze[5] = ((byte)(playerPos.getRowIndex()-255));

        }
        else{
            tempmaze[4] = ((byte)(playerPos.getRowIndex()));
            tempmaze[5] = (byte)0;
        }
        if (playerPos.getColumnIndex()>255){
            tempmaze[6] = ((byte)255);
            tempmaze[7] = ((byte)(playerPos.getColumnIndex()-255));

        }
        else{
            tempmaze[6] = ((byte)(playerPos.getColumnIndex()));
            tempmaze[7] = (byte)0;
        }
        // if you want show start point uncomment this
        //fos.write(maze.toByteArray());
        fos.write(tempmaze);
        this.logger.info("Maze saved to "+savedFile.getName());

    }


    public void LoadGame(ActionEvent actionEvent) throws IOException, InterruptedException {

        byte[] loadedMaze=new byte[1000000];
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");//filter for text files
        fileChooser.getExtensionFilters().add(extFilter);//filter to text files only
        File loadedFile=fileChooser.showOpenDialog(Main.primaryStage);
        if(loadedFile==null)
            return;//user canceled the opening
        FileInputStream fis = new FileInputStream(loadedFile);
        fis.read(loadedMaze);
        // at this point we hold uncompreesed maze in loadedMaze
        this.maze=new Maze(loadedMaze);// the ctor parse the data to maze properties
        // update ViewModels Fields with the New Properties Loaded
        this.playerPos = this.maze.getStartPosition();



        //TODO : for start the maze from last save
        this.playerPos.setRowIndex((loadedMaze[4]+loadedMaze[5]));
        this.playerPos.setColumnIndex((loadedMaze[6]+loadedMaze[7]));
        this.vm.UpdateModelProperties(this.maze,this.playerPos);
        mazeDisplayer.DrawMaze(this.maze);


        characterDisplayer.DrawCharacter(this.playerPos,this.maze.getRow(),this.maze.getCol());
        //characterDisplayer.DrawCharacter(this.playerPos,this.maze.getRow(),this.maze.getCol());

        mazeSolutionDisplayer.Clear();
        showSolution.setDisable(false);
        this.quickGameBtn.setVisible(false);
        this.logger.info("Maze loaded successfully from "+loadedFile.getName());



    }

    public void CtrlZoom(ScrollEvent scrollEvent) {
        double delta = 1.01;
        if (scrollEvent.isControlDown()) {//ctrl pressed
            double scaleX = this.mazeDisplayer.getScaleX();
            double scaleY = this.mazeDisplayer.getScaleY();

            if (scrollEvent.getDeltaY() < 0) {
                scaleX /= delta;
                scaleY /= delta;
            } else {
                scaleX *= delta;
                scaleY *= delta;
            }

            this.mazeDisplayer.setScaleX(scaleX);
            this.mazeDisplayer.setScaleY(scaleY);
            this.characterDisplayer.setScaleX(scaleX);
            this.characterDisplayer.setScaleY(scaleY);
            this.mazeSolutionDisplayer.setScaleX(scaleX);
            this.mazeSolutionDisplayer.setScaleY(scaleY);


            scrollEvent.consume();
        }
    }

    public  void HandleResize()
    {
//        mainPane.widthProperty().addListener((observable, oldValue, newValue) ->{
//            mazeDisplayer.setWidth((double)newValue);
//            characterDisplayer.setWidth((double)newValue);
//            mazeSolutionDisplayer.setWidth((double)newValue);
//
//
//                    try {
//                        mazeDisplayer.DrawMaze(this.maze);
//                        characterDisplayer.DrawCharacter(this.playerPos,this.maze.getRow(),this.maze.getCol());
//                        mazeSolutionDisplayer.DrawSolution(this.solution,this.maze.getRow(),this.maze.getCol(),this.maze.getGoalPosition());
//
//                    } catch (FileNotFoundException | InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                );
//        mainPane.heightProperty().addListener((observable, oldValue, newValue) ->{
//            mazeDisplayer.setHeight((double)newValue);
//            characterDisplayer.setHeight((double)newValue);
//            mazeSolutionDisplayer.setHeight((double)newValue);
//            try {
//                mazeDisplayer.DrawMaze(this.maze);
//                characterDisplayer.DrawCharacter(this.playerPos,this.maze.getRow(),this.maze.getCol());
//                mazeSolutionDisplayer.DrawSolution(this.solution,this.maze.getRow(),this.maze.getCol(),this.maze.getGoalPosition());
//
//            } catch (FileNotFoundException | InterruptedException e) {
//                e.printStackTrace();
//            }
//                }
//
//        );
    }


    public void HandleInstructionClick(ActionEvent actionEvent) {
        PopMsg("Lead Hesel & Gretel to the candy house\n\n" +
                "KeyBoard:\nNum1 - Move diagonal LEFT and DOWN\n" +
                "Num2 - Move DOWN\n" +
                "Num3 - Move diagonal RIGHT & DOWN\n" +
                "Num4 - Move LEFT \n" +
                "Num6 - Move RIGHT\n" +
                "Num7 - Move diagonal LEFT & UP\n" +
                "Num8 - Move UP\n" +
                "Num9 - Move diagonal RIGHT & UP","Instructions");
    }
}
