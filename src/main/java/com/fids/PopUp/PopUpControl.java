package com.fids.PopUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopUpControl {

    @FXML private Button okayButton;
    @FXML private Label alertLabel;
    public void closePopUp(ActionEvent actionEvent) {
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }

    public void setPopUp(String s) {
        alertLabel.setText(s);
    }
}
