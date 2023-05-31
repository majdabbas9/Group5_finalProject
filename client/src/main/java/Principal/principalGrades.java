package Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.client.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class principalGrades {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackToMenuBtn;

    @FXML
    private Button CpyBtn;

    @FXML
    private TableColumn<?, ?> examName;

    @FXML
    private TableView<?> gradeTable;

    @FXML
    private TableColumn<?, ?> studentName;

    @FXML
    private TableColumn<?, ?> studentgrade;

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void ShowCopy(ActionEvent event) {

    }

    @FXML
    void selectgrade(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert BackToMenuBtn != null : "fx:id=\"BackToMenuBtn\" was not injected: check your FXML file 'principalGrades.fxml'.";
        assert CpyBtn != null : "fx:id=\"CpyBtn\" was not injected: check your FXML file 'principalGrades.fxml'.";
        assert examName != null : "fx:id=\"examName\" was not injected: check your FXML file 'principalGrades.fxml'.";
        assert gradeTable != null : "fx:id=\"gradeTable\" was not injected: check your FXML file 'principalGrades.fxml'.";
        assert studentName != null : "fx:id=\"studentName\" was not injected: check your FXML file 'principalGrades.fxml'.";
        assert studentgrade != null : "fx:id=\"studentgrade\" was not injected: check your FXML file 'principalGrades.fxml'.";

    }

}
