package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import com.fids.PopUp.PopUpControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    private DBMSBoundary dbms = new DBMSBoundary();
    ResultSet sedi= dbms.getSedi();

    public void setFields(){
        try{
            while(sedi.next()) {
                sedeField.getItems().add(sedi.getString("nomeSede"));
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
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String email = emailField.getText().toLowerCase();
        String dataNascita=null;
        if(dataNascitaField.getValue()!=null)
            dataNascita = dataNascitaField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ruolo = ruoloField.getValue();
        String sede = sedeField.getValue();
        String IDSede = String.valueOf(sedeField.getSelectionModel().getSelectedIndex()+1);
        if(nome.trim().isEmpty() || cognome.trim().isEmpty() || email.trim().isEmpty() || dataNascita==null || ruolo==null || (ruolo=="Farmacista" && sede==null)){
            erroreLabel.setText("Tutti i campi sono obbligatori!");
            erroreLabel.setTextFill(Color.color(1, 0, 0));
        } else{
            if(dbms.verificaEmail(email)) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(PopUpControl.class.getResource("error.fxml"));
                Parent root = loader.load();
                PopUpControl popControl = loader.getController();
                popControl.setPopUp("L'utente è gia stato registrato!");
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Avviso");
                stage.setScene(scene);
                stage.show();
            }else{
                ArrayList<String> usernames = dbms.getUsernames();
                String username=nome.toLowerCase()+"."+cognome.toLowerCase();
                int count=0;
                for (String u : usernames) {
                    if (u.toLowerCase().startsWith(username)){
                        count++;
                    }
                }
                if(count!=0)
                    username=username+String.valueOf(count);
                dbms.insertUtente(nome, cognome, dataNascita, email, username, ruolo, IDSede);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
                Parent root = loader.load();
                PopUpControl popControl = loader.getController();
                popControl.setPopUp(nome+ " "+ cognome+ " è stato inserito\nusername: "+ username);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Avviso");
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void homePageCentrale(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomePageCentrale.fxml"));
        Parent root = loader.load();
        HomePageCentraleControl homeCControl = loader.getController();
        homeCControl.setLabels();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("HomePage Centrale");
        stage.setScene(new Scene(root));
        stage.show();
    }
}