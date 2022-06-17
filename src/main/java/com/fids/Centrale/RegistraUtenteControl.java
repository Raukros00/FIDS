package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    HashMap<String, String> utentiMap = new HashMap<String, String>();
    private DBMSBoundary dbms = new DBMSBoundary();
    ResultSet utenti= dbms.getUtenti();

    public void setFields(){
        String idSede="-1";
        try{
            while(utenti.next()) {
                if (!utenti.getString("IDSede").equals(idSede)) {
                    sedeField.getItems().add(utenti.getString("nomeSede"));
                    idSede = utenti.getString("IDSede");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        ruoloField.getItems().add("Farmacista");
        ruoloField.getItems().add("Impiegato");
        ruoloField.getItems().add("Corriere");
        System.out.println(utentiMap);
    }

    public void ruoloFarmacista(ActionEvent event) throws IOException {
        if(ruoloField.getValue()=="Farmacista"){
            sedeField.getSelectionModel().clearSelection();
            sedeField.setVisible(true);
            sedeLabel.setVisible(true);
        }
        else{
            sedeField.setVisible(false);
            sedeLabel.setVisible(false);
        }
    }
    public void registraUtente(ActionEvent event) throws IOException{
        String nome = nomeField.getText().toLowerCase();
        String cognome = cognomeField.getText().toLowerCase();
        String email = emailField.getText().toLowerCase();
        String dataNascita=null;
        if(dataNascitaField.getValue()!=null)
            dataNascita = dataNascitaField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ruolo = ruoloField.getValue();
        String sede = sedeField.getValue();
        String IDSede = String.valueOf(sedeField.getSelectionModel().getSelectedIndex()+1);
        System.err.println("1");
        if(nome.trim().isEmpty() || cognome.trim().isEmpty() || email.trim().isEmpty() || dataNascita==null || ruolo==null || (ruolo=="Farmacista" && sede==null)){
            erroreLabel.setText("Tutti i campi sono obbligatori!");
            erroreLabel.setTextFill(Color.color(1, 0, 0));
        } else{
            System.err.println("2");
            if(dbms.verificaEmail(email)) {
                erroreLabel.setText("L'utente inserito è già stato registrato!");
                erroreLabel.setTextFill(Color.color(1, 0, 0));
                System.err.println("Utente già inserito");
            }else{
                System.err.println("3");
                ArrayList<String> usernames = dbms.getUsernames();
                String username=nome+"."+cognome;
                int count=0;
                for (String u : usernames) {
                    if (u.toLowerCase().startsWith(username)){
                        count++;
                    }
                }
                if(count!=0)
                    username=username+String.valueOf(count);
                System.err.println(username);
                dbms.insertUtente(nome, cognome, dataNascita, email, username, ruolo, IDSede);
                erroreLabel.setText("Aggiunto!");
                erroreLabel.setTextFill(Color.color(1, 0, 0));
            }
        }
    }

}
