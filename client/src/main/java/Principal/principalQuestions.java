package Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.client.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class principalQuestions {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackToMenuBtn;

    @FXML
    private TableView<?> QuestionsTable;

    @FXML
    private Button ShowQuestionBtn;

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void ShowQuestion(ActionEvent event) {

    }

    @FXML
    void selectQuestion(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert BackToMenuBtn != null : "fx:id=\"BackToMenuBtn\" was not injected: check your FXML file 'principalQuestions.fxml'.";
        assert QuestionsTable != null : "fx:id=\"QuestionsTable\" was not injected: check your FXML file 'principalQuestions.fxml'.";
        assert ShowQuestionBtn != null : "fx:id=\"ShowQuestionBtn\" was not injected: check your FXML file 'principalQuestions.fxml'.";

    }

}