package View;

import ViewModel.MyViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import sample.Main;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class GoalWinController implements IView, Initializable {
    public MediaView mediaViewer;
    public javafx.scene.control.Button back44;
    MediaPlayer mediaPlayer;
    Media media;


    @Override
    public void SetViewModelConnection(MyViewModel vm) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.ChangeMusic("GoalWin");
        String path= new File("resources/video/GoalWin.mp4").getAbsolutePath();
        media=new Media(new File(path).toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        mediaViewer.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        DoubleProperty width= mediaViewer.fitWidthProperty();
        DoubleProperty height= mediaViewer.fitHeightProperty();


    }

    public void GoToMainWindow(ActionEvent actionEvent) throws IOException {
        Main.changeScene("MyView");
    }



}
