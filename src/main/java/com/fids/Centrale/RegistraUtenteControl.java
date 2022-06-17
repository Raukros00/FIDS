package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;

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
    public Label sedeLabel;
    @FXML
    public Label erroreLabel;
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
            sedeField.setVisible(true);
            sedeLabel.setVisible(true);
        }
        else{
            sedeField.setVisible(false);
            sedeLabel.setVisible(false);
        }
    }
    public void registraUtente(ActionEvent event) throws IOException{
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String email = emailField.getText();
        String dataNascita=null;
        if(dataNascitaField.getValue()!=null) {
            dataNascita = dataNascitaField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//dataNascitaField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        String ruolo = ruoloField.getValue();
        String sede = sedeField.getValue();


        if(nome.trim().isEmpty() || cognome.trim().isEmpty() || email.trim().isEmpty() || dataNascita==null || ruolo==null || (ruolo=="Farmacista" && sede==null)){
            erroreLabel.setText("Tutti i campi sono obbligatori!");
            erroreLabel.setTextFill(Color.color(1, 0, 0));
        } else{
            System.err.println("nome:"+nome+nome.trim().isEmpty()+"\ncognome:"+cognome+cognome.trim().isEmpty()+"\nemail:"+email+email.trim().isEmpty()+"\ndataNascita:"+dataNascita+dataNascita.trim().isEmpty()+"\nruolo:"+ruolo+"\nsede:"+sede);
            try{
                //utenti.first();
                while(utenti.next()){
                    System.err.println(utenti.getString("email"));
                    if(utenti.getString("email").equals(email)){
                        erroreLabel.setText("L'utente inserito è già stato registrato!");
                        erroreLabel.setTextFill(Color.color(1, 0, 0));
                        break;
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
