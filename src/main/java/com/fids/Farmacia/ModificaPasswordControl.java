package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Utente;
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
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ModificaPasswordControl {

    @FXML
    public PasswordField vecchiaPasswordField;
    @FXML
    public PasswordField nuovaPasswordField;
    @FXML
    public PasswordField confermaPasswordField;
    @FXML
    public Label erroreLabel;
    @FXML
    public Button credenzialiButton;

    private DBMSBoundary dbms = new DBMSBoundary();

    private Utente user;


    public String setDatiPassword(Utente user) {
        String passwd = "";

        passwd = user.getPassword();

        return passwd;
    }

    public void aggiornaCredenziali(ActionEvent event) {
        String passwd = "c4ca4238a0b923820dcc509a6f75849b";
        String vecchiaPassword = vecchiaPasswordField.getText();
        String nuovaPassword = nuovaPasswordField.getText();
        String confermaPassword = confermaPasswordField.getText();

        if (vecchiaPassword.trim().isEmpty() || nuovaPassword.trim().isEmpty() || confermaPassword.trim().isEmpty()) {
            erroreLabel.setText("Tutti i campi sono obbligatori!");
            erroreLabel.setTextFill(Color.color(1, 0, 0));
        } else {
            if (vecchiaPassword.equalsIgnoreCase(passwd)) {
                if (nuovaPassword.equalsIgnoreCase(confermaPassword)) {
                    MessageDigest md5 = null;
                    try {
                        md5 = MessageDigest.getInstance("MD5");
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    md5.update(StandardCharsets.UTF_8.encode(confermaPassword));
                    confermaPassword = String.format("%032x", new BigInteger(1, md5.digest()));
                    if (dbms.modificaCredenziali(confermaPassword)) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        PopUpControl popControl = loader.getController();
                        popControl.setPopUp("Password correttamente\naggiornata!");
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Avviso");
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(PopUpControl.class.getResource("error.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        PopUpControl popControl = loader.getController();
                        popControl.setPopUp("Qualcosa è\nandato storto!");
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Avviso");
                        stage.setScene(scene);
                        stage.show();
                    }

                    Stage stage = (Stage) credenzialiButton.getScene().getWindow();
                    stage.close();
                } else {
                    erroreLabel.setText("Le due password non coincidono!");
                    erroreLabel.setTextFill(Color.color(1, 0, 0));
                }
            } else {
                erroreLabel.setText("Password vecchia errata!");
                erroreLabel.setTextFill(Color.color(1, 0, 0));
            }
        }

        /*byte[] bytesOfMessage = confermaPassword.getBytes("UTF-8"); //sostituire password con la stringa da criptare
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] passwMD5 = md.digest(bytesOfMessage); //passwMD5 è la nuova password criptata
        System.out.println(passwMD5);*/
    }
}
