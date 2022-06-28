package com.fids.Login;

import DBMSB.DBMSBoundary;
import Entity.Utente;
import com.fids.Centrale.HomePageCentraleControl;
import com.fids.Corriere.HomePageCorriereControl;
import com.fids.Farmacia.HomeFarmaciaControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccessControl {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label errorLabel;
    @FXML private Button accediButton;
    private DBMSBoundary dbms = new DBMSBoundary();
    private Utente user;
    Stage stage;
    FXMLLoader loader;
    Parent root;

    @FXML
    public void inserisciCredenziali(ActionEvent event) throws IOException {
        String username = String.valueOf(usernameField.getText());
        String password = String.valueOf(passwordField.getText());



        if(username.isEmpty()){

            usernameLabel.setText("L'username è obbligatorio!");
            usernameLabel.setTextFill(Color.color(1, 0, 0));

            if(password.isEmpty()){
                passwordLabel.setText("La password è obbligatoria!");
                passwordLabel.setTextFill(Color.color(1, 0, 0));;
            }

        }
        else {

            if(password.isEmpty()){
                passwordLabel.setText("La password è obbligatoria!");
                passwordLabel.setTextFill(Color.color(1, 0, 0));
            }
            else {

                MessageDigest md5 = null;
                try {
                    md5 = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                md5.update(StandardCharsets.UTF_8.encode(password));
                password = String.format("%032x", new BigInteger(1, md5.digest()));

                System.out.println("USERNAME: " + username + "\nPASSWORD: " + password);
                user = dbms.verificaCredenziali(username, password);
                if(user.getIDUtente() == -1){
                    errorLabel.setText("Password Errata!");
                }
                else if(user.getIDUtente() == -2){
                    errorLabel.setText("Username Errato!");
                }

                else {
                    switch(user.getRuolo()) {

                        case 1:
                            loader = new FXMLLoader();
                            loader.setLocation(HomeFarmaciaControl.class.getResource("HomeFarmacia.fxml"));
                            root = loader.load();
                            HomeFarmaciaControl homeFControl = loader.getController();
                            homeFControl.setUser(user);
                            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            stage.setTitle("Home Farmacia");
                            stage.setScene(new Scene(root));
                            stage.show();
                        break;

                        case 2:
                            loader = new FXMLLoader();
                            loader.setLocation(HomePageCentraleControl.class.getResource("HomePageCentrale.fxml"));
                            root = loader.load();
                            HomePageCentraleControl homeCControl = loader.getController();
                            homeCControl.setUser(user);
                            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            stage.setTitle("HomePage Centrale");
                            stage.setScene(new Scene(root));
                            stage.show();
                        break;

                        case 3:
                            loader = new FXMLLoader();
                            loader.setLocation(HomePageCorriereControl.class.getResource("HomePageCorriere.fxml"));
                            root = loader.load();
                            HomePageCorriereControl homeCoControl = loader.getController();
                            homeCoControl.setUser(user);
                            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            stage.setTitle("HomePage Corriere");
                            stage.setScene(new Scene(root));
                            stage.show();

                        break;
                    }
                }


            }
        }
    }

    public void recuperaCredenzialiButton(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("recuperaCredenziali.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Recupera Credenziali");
            stage.setScene(new Scene(root));
            stage.show();
        }

    public void setLabels() {
    }
}