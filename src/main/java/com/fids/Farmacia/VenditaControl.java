package com.fids.Farmacia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class VenditaControl {

    @FXML
    public Button aggiungiFarmacoButton;

    @FXML
    private Spinner<Integer> quantitaLottoField;
    SpinnerValueFactory<Integer> valoriQuantita = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);


    int IDFarmacia;
    public void setIDFarmacia(int IDFarmacia) {
        this.IDFarmacia = IDFarmacia;
        valoriQuantita.setValue(1);
        quantitaLottoField.setValueFactory(valoriQuantita);
    }


    public void aggiungiVendita(ActionEvent actionEvent) {
    }

    public void aggiornaQuantita(ActionEvent actionEvent) {
    }

    public void homeFarmacia(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomeFarmacia.fxml"));
        Parent root = loader.load();
        HomeFarmaciaControl homeFControl = loader.getController();
        homeFControl.setLabels();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home Farmacia");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
