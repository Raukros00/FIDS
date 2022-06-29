package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.Contratto;
import Entity.Farmacia;
import Entity.Farmaco;
import Entity.FarmacoContratto;
import com.fids.Farmacia.HomeFarmaciaControl;
import com.fids.PopUp.PopUpControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class ModificaContrattoCentraleControl {
    /*@FXML
    TableView<Farmaco> contrattoFarmaciTable = new TableView<>();
    @FXML
    TableColumn<Farmaco, String> nomeCol;
    @FXML TableColumn<Farmaco, Integer> quantitaPeriodicaCol;

    @FXML private Spinner<Integer> periodoConsegnaField;
    SpinnerValueFactory<Integer> valoriQuantita = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
    @FXML private Button aggiornaButton;
    DBMSBoundary dbms = new DBMSBoundary();
    Contratto contratto;
    LinkedList<Farmaco> farmaciDisponibili;
    LinkedList<FarmacoContratto> farmaciContratto;

    public void setDatiContrattoCentrale(Farmacia f){

    }

    public void updateFarmaciContratto(ActionEvent actionEvent) throws IOException {
        int periodo = periodoConsegnaField.getValue();
        if(periodo == contratto.getPerioditicita()) periodo = 0;

        dbms.updateContratto(farmaciContratto, periodo, contratto.getIDContratto());

        Stage stage = (Stage) aggiornaButton.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
        Parent root = loader.load();
        PopUpControl popControl = loader.getController();
        popControl.setPopUp("Contratto aggiornato!");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Avviso");
        stage.setScene(scene);
        stage.showAndWait();
    }*/
}
