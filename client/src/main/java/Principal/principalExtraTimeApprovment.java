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
import javafx.scene.text.Text;

public class principalExtraTimeApprovment {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ApproveBtn;

    @FXML
    private Button BackToMenuBtn;

    @FXML
    private Button DeclineBtn;

    @FXML
    private Text Errortxt;

    @FXML
    private TableView<?> RequestsTable;

    @FXML
    void Approve(ActionEvent event) {

    }

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void Decline(ActionEvent event) {

    }

    @FXML
    void selectRequest(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert ApproveBtn != null : "fx:id=\"ApproveBtn\" was not injected: check your FXML file 'principalExtraTimeApprovment.fxml'.";
        assert BackToMenuBtn != null : "fx:id=\"BackToMenuBtn\" was not injected: check your FXML file 'principalExtraTimeApprovment.fxml'.";
        assert DeclineBtn != null : "fx:id=\"DeclineBtn\" was not injected: check your FXML file 'principalExtraTimeApprovment.fxml'.";
        assert Errortxt != null : "fx:id=\"Errortxt\" was not injected: check your FXML file 'principalExtraTimeApprovment.fxml'.";
        assert RequestsTable != null : "fx:id=\"RequestsTable\" was not injected: check your FXML file 'principalExtraTimeApprovment.fxml'.";

    }

}
