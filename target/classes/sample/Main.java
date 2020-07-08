package sample;

import Model.IModel;
import Model.MyModel;
import Server.Server;
import View.IView;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;



import java.io.IOException;
import java.nio.file.Paths;


public class Main extends Application {

    IModel m;
    static MyViewModel vm;
    static IView v;
    public static Stage primaryStage;
    static MediaPlayer mediaPlayer;
    static Media media ;
    static Server mazeGeneratingServer;
    static Server solveSearchProblemServer;


    public static Logger logger = (Logger) LogManager.getLogger(Main.class);







    public static void MuteSound() {

        mediaPlayer.setMute(true);

    }

    public static void UnMuteSound() {
        mediaPlayer.setMute(false);
    }

    public static void SafeExit() {
        mazeGeneratingServer.stop();
        Main.logger.info("Maze generating server shuted down");
        solveSearchProblemServer.stop();
        Main.logger.info("Solve Search Problem server shuted down");

        primaryStage.close();
        Main.logger.info("Application closed safely !");

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("application started");
        //************************* MEDIA *******************//
        media = new Media(Paths.get("resources/mp3/MyView.mp3").toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        this.primaryStage = primaryStage;
//        Main.primaryStage.setResizable(false);
        this.m = new MyModel();
        vm = new MyViewModel(m);
        changeScene("MyView");
        vm.addObserver(v);
        v.SetViewModelConnection(vm);



        // server init:
        mazeGeneratingServer = new Server(5400, 1, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        logger.info("Solve search problem server started");
        mazeGeneratingServer.start();
        logger.info("Maze generating server started");

        ///// close request
        primaryStage.setOnCloseRequest(we -> SafeExit());
    }


    public static void ChangeMusic(String fileName) {
        mediaPlayer.stop();
        media= new Media(Paths.get("resources/mp3/"+fileName+".mp3" ).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();

    }

    public static void changeScene(String fileName) throws IOException {
        //Main.logger.info("Changing scene to "+fileName);
        ChangeMusic(fileName); //TODO remove comment
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("../View/"+fileName+".fxml"));
        Parent root = loader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("../View/MyView.fxml"));
        Main.primaryStage.setTitle("L&L Maze Game");
        Scene scene = new Scene(root,700,500);
        scene.getStylesheets().add(Paths.get("./src/main/java/View/"+fileName+".css" ).toUri().toString());
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
        Main.v = loader.getController();
        if (fileName=="MyView"){
            v.SetViewModelConnection(vm);
            vm.addObserver(v);
        }



    }
    public static void main(String[] args) {
        launch(args);
    }
}
