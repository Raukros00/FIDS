package com.fids.PopUp;

import Entity.GlobalData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.datatype.DatatypeConstants;
import java.io.IOException;

public class OffsetControl extends GlobalData {
    @FXML
    private Spinner<Integer> offsetOraField;
    @FXML
    private Spinner<Integer> offsetGiorniField;
    @FXML
    private Button impostaOffsetButton;

    SpinnerValueFactory<Integer> offsetH = new SpinnerValueFactory.IntegerSpinnerValueFactory(-23,23);
    SpinnerValueFactory<Integer> offsetD = new SpinnerValueFactory.IntegerSpinnerValueFactory(-31,31);

    public void setPresets(){
        offsetH.setValue(0);
        offsetD.setValue(0);
        offsetOraField.setValueFactory(offsetH);
        offsetGiorniField.setValueFactory(offsetD);
    }
    public void impostaOffset(ActionEvent event) throws IOException {
        int offsetOraInserito=offsetOraField.getValue();;
        int offsetGiorniInserito=offsetGiorniField.getValue();;

        OFFSETHOUR+=offsetOraInserito;
        OFFSETDAY+=offsetGiorniInserito;
        Stage stage = (Stage) impostaOffsetButton.getScene().getWindow();
        stage.close();

    }
}
