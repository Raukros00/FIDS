package com.fids.Farmacia;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FarmacoScadenzaControl {

    @FXML private Button rimuoviButton;
    @FXML private Button accettaButton;

    @FXML private Label alertLabel;
    boolean mantieni;
    Stage stage;

    public void setPopUp(String s) {
        alertLabel.setText(s);

        accettaButton.setOnAction(e -> {
            this.mantieni = true;
            stage = (Stage) accettaButton.getScene().getWindow();
            stage.close();
        });

        rimuoviButton.setOnAction(e -> {
            this.mantieni = false;
            stage = (Stage) rimuoviButton.getScene().getWindow();
            stage.close();
        });
    }

    public boolean returnMantieni() { return this.mantieni; }
}
