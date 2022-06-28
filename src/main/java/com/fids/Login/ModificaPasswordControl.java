package com.fids.Login;

import DBMSB.DBMSBoundary;
import Entity.GlobalData;
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

public class ModificaPasswordControl extends GlobalData {

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

    public void aggiornaCredenziali(ActionEvent event) {
        String vecchiaPassword = vecchiaPasswordField.getText();
        String nuovaPassword = nuovaPasswordField.getText();
        String confermaPassword = confermaPasswordField.getText();
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        md5.update(StandardCharsets.UTF_8.encode(vecchiaPassword));
        vecchiaPassword = String.format("%032x", new BigInteger(1, md5.digest()));

        md5.update(StandardCharsets.UTF_8.encode(nuovaPassword));
        nuovaPassword = String.format("%032x", new BigInteger(1, md5.digest()));

        md5.update(StandardCharsets.UTF_8.encode(confermaPassword));
        confermaPassword = String.format("%032x", new BigInteger(1, md5.digest()));

        System.out.println("DB: " + PASSWORD + "\nNW: " + vecchiaPassword);
        if (vecchiaPassword.trim().isEmpty() || nuovaPassword.trim().isEmpty() || confermaPassword.trim().isEmpty()) {
            erroreLabel.setText("Tutti i campi sono obbligatori!");
            erroreLabel.setTextFill(Color.color(1, 0, 0));
        } else {
            if (vecchiaPassword.equalsIgnoreCase(PASSWORD)) {
                if (nuovaPassword.equalsIgnoreCase(confermaPassword)) {
                    if (dbms.aggiornaPassword(confermaPassword, EMAIL)) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("successful.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        PopUpControl popControl = loader.getController();
                        popControl.setPopUp("Password correttamente\nmodificata");
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Avviso");
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("error.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        PopUpControl popControl = loader.getController();
                        popControl.setPopUp("Qualcosa è andato\nstorto");
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Avviso");
                        stage.setScene(scene);
                        stage.show();
                    }
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
                    popControl.setPopUp("La nuova non corrisponde!");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Avviso");
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                erroreLabel.setText("La vecchia password è errata!");
                erroreLabel.setTextFill(Color.color(1, 0, 0));
            }
        }
    }
}
