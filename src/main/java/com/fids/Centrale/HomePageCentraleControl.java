package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.Utente;
import com.fids.AccessApplication;
import com.fids.Farmacia.InventarioFarmaciaControl;
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

public class HomePageCentraleControl {
    @FXML
    public Button logoutButton;
    @FXML
    private Label nomeCognomeLabel;
    @FXML
    private Label numSegnalazioniLabel;
    @FXML
    public Button registraUtenteButton;
    @FXML
    public Button segnalazioniButton;
    @FXML
    public Button inventarioCentraleButton;
    private Utente user;

    public void setUser(Utente user) {
        this.user = user;
        DBMSBoundary dbms = new DBMSBoundary();

        nomeCognomeLabel.setText( user.getNome() + " " + user.getCognome());
        numSegnalazioniLabel.setText(dbms.richiediNumSegnalazioni());
    }


    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AccessApplication.class.getResource("Login/login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

   public void gestioneInventarioCentrale(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("InventarioCentrale.fxml"));
       Parent root = loader.load();
       InventarioCentraleControl inventarioCControl = loader.getController();
       inventarioCControl.stampaTabella();
       Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
       stage.setTitle("Inventario Centrale");
       stage.setScene(new Scene(root));
       stage.show();
    }

    public void registraUtente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RegistraUtente.fxml"));
        Parent root = loader.load();
        RegistraUtenteControl registraUtenteControl = loader.getController();
        registraUtenteControl.setFields();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Registra Utente");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void segnalazioni(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Segnalazioni.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Segnalazioni");
        stage.setScene(new Scene(root));
        stage.show();
    }
}