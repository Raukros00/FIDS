package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Farmacia;
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

public class HomeFarmaciaControl {

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

        nomeCognomeLabel.setText( user.getNome() + " " + user.getCognome());
        numConsegneArrivoLabel.setText(String.valueOf(farmacia.getNumConsegne()));
        nomeFarmaciaLabel.setText(farmacia.getNomeSede());
        indirizzoLabel.setText(farmacia.getIndirizzoSede());
        cittaLabel.setText(farmacia.getCitta());

    }


    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AccessApplication.class.getResource("Login/login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void gestioneInventario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("InventarioFarmacia.fxml"));
        Parent root = loader.load();
        InventarioFarmaciaControl inventarioFControl = loader.getController();
        inventarioFControl.setFarmacia(farmacia.getIDFarmacia());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventario Farmacia");
        stage.setScene(new Scene(root));
        stage.show();
    }
}

