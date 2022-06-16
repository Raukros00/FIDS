package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RegistraUtenteControl {
    @FXML
    public TextField nomeField;
    @FXML
    public TextField cognomeField;
    @FXML
    public TextField emailField;
    @FXML
    public DatePicker dataNascitaField;
    @FXML
    public ChoiceBox<String> ruoloField;
    @FXML
    public ChoiceBox<String> sedeField;
    @FXML
    private Button confermaRegistraButton;
    private DBMSBoundary dbms = new DBMSBoundary();
    ResultSet utenti= dbms.getUtenti();

    public void setFields(){
        String idSede="-1";
        try{
            while(utenti.next()){
                if(!utenti.getString("IDSede").equals(idSede)){
                    sedeField.getItems().add(utenti.getString("nomeSede"));
                    idSede=utenti.getString("IDSede");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        ruoloField.getItems().add("Farmacista");
        ruoloField.getItems().add("Impiegato");
        ruoloField.getItems().add("Corriere");
    }

    public void ruoloFarmacista(ActionEvent event) throws IOException {
        if(ruoloField.getValue()=="Farmacista"){
            System.err.println(ruoloField.getValue());
        }
        else{
            System.err.println("Suca");
        }
    }
    /*public void registraUtente(ActionEvent event){
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String email = emailField.getText();
        String dataNascita = dataNascitaField.getValue();
        String ruolo = ruoloField.getValue();
        String sede = sedeField.getValue();
    }*/


}
