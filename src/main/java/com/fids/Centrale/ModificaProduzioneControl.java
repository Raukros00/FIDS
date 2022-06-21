package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.Farmaco;
import com.fids.PopUp.PopUpControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

public class ModificaProduzioneControl {
    @FXML
    private Spinner<Integer> periodicitaProduzioneField;
    @FXML
    private Spinner<Integer> quantitaProduzioneField;
    private DBMSBoundary dbms = new DBMSBoundary();
    private int idFarmaco;

    @FXML
    private Button modificaButton;
    SpinnerValueFactory<Integer> periodValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,31);
    SpinnerValueFactory<Integer> quantitValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000);

    public void setPresets(Farmaco farmaco){
        ResultSet result=dbms.getFarmaco(farmaco.getIDFarmaco());
        try {
            result.next();
            periodValues.setValue(result.getInt("periodicitaProduzione"));
            quantitValues.setValue(result.getInt("quantitaProduzione"));
        }catch(Exception e){
            periodValues.setValue(0);
            quantitValues.setValue(0);
        }
        periodicitaProduzioneField.setValueFactory(periodValues);
        quantitaProduzioneField.setValueFactory(quantitValues);
    }

    public void modificaProduzione(ActionEvent event) throws IOException {
        int periodo=periodicitaProduzioneField.getValue();
        int quantita=quantitaProduzioneField.getValue();
        if(dbms.modificaProduzione(periodo, quantita, idFarmaco)){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
            Parent root = loader.load();
            PopUpControl popControl = loader.getController();
            popControl.setPopUp("Produzione correttamente\naggiornata!");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avviso");
            stage.setScene(scene);
            stage.show();
        }else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PopUpControl.class.getResource("error.fxml"));
            Parent root = loader.load();
            PopUpControl popControl = loader.getController();
            popControl.setPopUp("Qualcosa è\nandato storto!");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avviso");
            stage.setScene(scene);
            stage.show();
        }

        Stage stage = (Stage) modificaButton.getScene().getWindow();
        stage.close();
    }
}
