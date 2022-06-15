package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Farmacia;
import Entity.GlobalData;
import Entity.Utente;
import com.fids.AccessApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeFarmaciaControl extends GlobalData{

    public Button venditaFarmaciButton;
    public Button modificaContrattoButton;
    public Button gestioneInventarioButton;
    public Button caricaConsegnaButton;
    @FXML
    public Button logoutButton;
    @FXML
    private Label nomeCognomeLabel;
    @FXML
    private Label numConsegneArrivoLabel;
    @FXML
    private Label nomeFarmaciaLabel;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private Label cittaLabel;
    private Utente user;
    private Farmacia farmacia;

    public void setUser(Utente user) {
        this.user = user;
        DBMSBoundary dbms = new DBMSBoundary();
        farmacia = dbms.richiediInfoHome(user.getIDSede());

        NOMINATIVO = user.getNome() + " " + user.getCognome();
        NUM_CONSEGNE = String.valueOf(farmacia.getNumConsegne());
        NOME_FARMACIA = farmacia.getNomeSede();
        INDIRIZZO_FARMACIA = farmacia.getIndirizzoSede();
        CITTA_FARMACIA = farmacia.getCitta();
        ID_FARMACIA = farmacia.getIDFarmacia();


        nomeCognomeLabel.setText(NOMINATIVO);
        numConsegneArrivoLabel.setText(NUM_CONSEGNE);
        nomeFarmaciaLabel.setText(NOME_FARMACIA);
        indirizzoLabel.setText(INDIRIZZO_FARMACIA);
        cittaLabel.setText(CITTA_FARMACIA);

    }

    public void setLabels() {
        nomeCognomeLabel.setText(NOMINATIVO);
        numConsegneArrivoLabel.setText(NUM_CONSEGNE);
        nomeFarmaciaLabel.setText(NOME_FARMACIA);
        indirizzoLabel.setText(INDIRIZZO_FARMACIA);
        cittaLabel.setText(CITTA_FARMACIA);
    }


    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AccessApplication.class.getResource("Login/login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.setTitle("Login");
        window.setScene(new Scene(root));
    }

    public void gestioneInventario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("InventarioFarmacia.fxml"));
        Parent root = loader.load();
        InventarioFarmaciaControl inventarioFControl = loader.getController();
        inventarioFControl.setFarmacia(ID_FARMACIA);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventario Farmacia");
        stage.setScene(new Scene(root));
        stage.show();
    }
}

