package com.fids.Corriere;

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

public class HomePageCorriereControl {
    @FXML
    public Button logoutButton;
    @FXML
    private Label nomeCognomeLabel;
    @FXML
    private Label numSpedizioniLabel;
    @FXML
    public Button listaSpedizioniButton;
    private Utente user;

    public void setUser(Utente user) {
        this.user = user;
        DBMSBoundary dbms = new DBMSBoundary();

        nomeCognomeLabel.setText( user.getNome() + " " + user.getCognome());
        numSpedizioniLabel.setText(dbms.richiediNumSpedizioni());
    }


    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AccessApplication.class.getResource("Login/login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }


    public void listaSpedizioni(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ListaSpedizioniControl.class.getResource("ListaSpedizioni.fxml"));
        Parent root = loader.load();
        ListaSpedizioniControl listaSpedizioniControl= loader.getController();
        listaSpedizioniControl.setField();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("ListaSpedizioni");
        stage.setScene(new Scene(root));
        stage.show();
    }


}