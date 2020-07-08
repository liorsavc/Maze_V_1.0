package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.Main;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class SettingsController implements  IView,Initializable {
    public ChoiceBox<String> algorithmChoiceBox;
    public Button cancelBtn;
    public TextField rowTF;
    public TextField colTF;
    public Button applyBtn;
    private Logger logger = (Logger) LogManager.getLogger(this.getClass());
    public AnchorPane anchorPane;





    public void GoToMainWindow(ActionEvent actionEvent) throws IOException {
        Main.changeScene("MyView");


    }

    public void SetConfing(ActionEvent actionEvent) throws IOException {
        int rows,cols;
        try{
            // check if text field contains numbers only
            rows = Integer.valueOf(rowTF.getText());
            cols = Integer.valueOf(colTF.getText());
        }catch(NumberFormatException e){
            PopAlert("Rows and Cols should be numbers only");
            logger.error("user entered invalid  arguments of Rows and Cols ");
            return;
        }
        if (rows <2 || cols <2 ){
            PopAlert("Size of Rows or Columns Should be at least 2");
            logger.error("user entered invalid  arguments of Rows and Cols: smaller than 2 ");

            return;
        }

        Configurations.setConfig(algorithmChoiceBox.getValue(),rowTF.getText(),colTF.getText());
        Main.changeScene("MyView");
        logger.info("user changed configuration successfully ");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        algorithmChoiceBox.getItems().add("Best First Search");
        algorithmChoiceBox.getItems().add("Depth First Search");
        algorithmChoiceBox.getItems().add("Breadth First Search");
        algorithmChoiceBox.setValue("Best First Search");
        rowTF.setText(String.valueOf(Configurations.GetRows()));
        colTF.setText(String.valueOf(Configurations.GetCols()));

    }
    public void PopAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    @Override
    public void SetViewModelConnection(MyViewModel vm) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }


    public void Mute(ActionEvent actionEvent) {

        Main.MuteSound();
    }
}
