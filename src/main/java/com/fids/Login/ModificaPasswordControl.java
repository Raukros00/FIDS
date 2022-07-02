package com.fids.Login;

import DBMSB.DBMSBoundary;
import Entity.GlobalData;
import Entity.Utente;
import com.fids.Centrale.HomePageCentraleControl;
import com.fids.Corriere.HomePageCorriereControl;
import com.fids.Farmacia.HomeFarmaciaControl;
import com.fids.PopUp.PopUpControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

        if (vecchiaPassword.isEmpty() || nuovaPassword.isEmpty() || confermaPassword.isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PopUpControl.class.getResource("error.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PopUpControl popControl = loader.getController();
            popControl.setPopUp("Tutti i campi sono obbligatori!");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avviso");
            stage.setScene(scene);
            stage.showAndWait();
        } else {

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


            if (vecchiaPassword.equalsIgnoreCase(PASSWORD)) {
                if (nuovaPassword.equalsIgnoreCase(confermaPassword)) {
                    if (dbms.aggiornaPassword(confermaPassword, EMAIL)) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        PopUpControl popControl = loader.getController();
                        popControl.setPopUp("Password aggiornata correttamente!");
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Avviso");
                        stage.setScene(scene);
                        stage.showAndWait();

                        switch(RUOLO) {

                            case 1:
                                loader = new FXMLLoader();
                                loader.setLocation(HomeFarmaciaControl.class.getResource("HomeFarmacia.fxml"));
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                HomeFarmaciaControl homeFControl = loader.getController();
                                //homeFControl.setUser(user);
                                homeFControl.setLabels();
                                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                                stage.setTitle("Home Farmacia");
                                stage.setScene(new Scene(root));
                                stage.show();
                                break;

                            case 2:
                                loader = new FXMLLoader();
                                loader.setLocation(HomePageCentraleControl.class.getResource("HomePageCentrale.fxml"));
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                homeFControl = loader.getController();
                                homeFControl.setUser(new Utente(NOME, COGNOME, ID_UTENTE, EMAIL, PASSWORD, RUOLO, ID_FARMACIA));
                                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                                stage.setTitle("HomePage Centrale");
                                stage.setScene(new Scene(root));
                                stage.show();
                                break;

                            case 3:
                                loader = new FXMLLoader();
                                loader.setLocation(HomePageCorriereControl.class.getResource("HomePageCorriere.fxml"));
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                HomePageCorriereControl homeCoControl = loader.getController();
                                homeCoControl.setUser(user);
                                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                                stage.setTitle("HomePage Corriere");
                                stage.setScene(new Scene(root));
                                stage.show();

                                break;
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
                        popControl.setPopUp("Qualcosa è\nandato storto!");
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
                    popControl.setPopUp("La nuova password non corrisponde!");
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
                popControl.setPopUp("La nuova vecchia \npassword non corrisponde!");
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Avviso");
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void backHome(ActionEvent event){
        FXMLLoader loader;
        Parent root;
        Stage stage;
        switch(RUOLO) {

            case 1:
                loader = new FXMLLoader();
                loader.setLocation(HomeFarmaciaControl.class.getResource("HomeFarmacia.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                HomeFarmaciaControl homeFControl = loader.getController();
                //homeFControl.setUser(new Utente(NOME, COGNOME, ID_UTENTE, EMAIL, PASSWORD, RUOLO, ID_FARMACIA));
                homeFControl.setLabels();
                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Home Farmacia");
                stage.setScene(new Scene(root));
                stage.show();
                break;

            case 2:
                loader = new FXMLLoader();
                loader.setLocation(HomePageCentraleControl.class.getResource("HomePageCentrale.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                HomePageCentraleControl homeCControl = loader.getController();
                homeCControl.setLabels();
                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("HomePage Centrale");
                stage.setScene(new Scene(root));
                stage.show();
                break;

            case 3:
                loader = new FXMLLoader();
                loader.setLocation(HomePageCorriereControl.class.getResource("HomePageCorriere.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                HomePageCorriereControl homeCoControl = loader.getController();
                homeCoControl.setLabels();
                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("HomePage Corriere");
                stage.setScene(new Scene(root));
                stage.show();

                break;
        }
    }
}
