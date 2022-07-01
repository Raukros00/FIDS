package com.fids.Corriere;

import DBMSB.DBMSBoundary;
import Entity.Spedizione;
import com.fids.PopUp.PopUpControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FirmaControl {
    @FXML
    private Button indietroButton;
    @FXML
    public TextField usernameField;
    @FXML
    public TextField passwordField;
    @FXML
    private Label IDSpedizioneLabel;
    @FXML
    private Label nomeFarmaciaLabel;
    @FXML
    private Label indirizzoFarmaciaLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;

    @FXML
    public Button firmaButton;

    Spedizione sp;

    private DBMSBoundary dbms = new DBMSBoundary();




    public void setPresets(Spedizione s){
        this.sp=s;
        IDSpedizioneLabel.setText(String.valueOf(s.getIDSpedizione()));
        nomeFarmaciaLabel.setText(s.getNomeFarmacia());
        indirizzoFarmaciaLabel.setText(s.getIndirizzoFarmacia());
    }

    public void listaSpedizioni(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ListaSpedizioniControl.class.getResource("ListaSpedizioni.fxml"));
        Parent root = loader.load();
        ListaSpedizioniControl listaSpedizioniControl= loader.getController();
        listaSpedizioniControl.setField();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Lista Spedizioni");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void firma(ActionEvent event)throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println(username + password);
        if (username.isEmpty()) {

            usernameLabel.setText("L'username è obbligatorio!");
            usernameLabel.setTextFill(Color.color(1, 0, 0));

            if (password.isEmpty()) {
                passwordLabel.setText("La password è obbligatoria!");
                passwordLabel.setTextFill(Color.color(1, 0, 0));
                ;
            }

        } else {

            if (password.isEmpty()) {
                passwordLabel.setText("La password è obbligatoria!");
                passwordLabel.setTextFill(Color.color(1, 0, 0));
            } else {

                System.out.println("USERNAME: " + username + "\nPASSWORD: " + password);
                MessageDigest md5 = null;
                try {
                    md5 = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                md5.update(StandardCharsets.UTF_8.encode(password));
                password = String.format("%032x", new BigInteger(1, md5.digest()));
                int flag = dbms.verificaFirma(username, password, sp.getIDSede(), sp.getIDSpedizione());
                if (flag == -1) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PopUpControl.class.getResource("error.fxml"));
                    Parent root = loader.load();
                    PopUpControl popControl = loader.getController();
                    popControl.setPopUp("Username inesistente");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Avviso");
                    stage.setScene(scene);
                    stage.show();
                } else if (flag == -2) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PopUpControl.class.getResource("error.fxml"));
                    Parent root = loader.load();
                    PopUpControl popControl = loader.getController();
                    popControl.setPopUp("Password errata");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Avviso");
                    stage.setScene(scene);
                    stage.show();
                } else if(flag==-3){
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PopUpControl.class.getResource("error.fxml"));
                    Parent root = loader.load();
                    PopUpControl popControl = loader.getController();
                    popControl.setPopUp("Il farmacista non appartiene a questa farmacia");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Avviso");
                    stage.setScene(scene);
                    stage.show();
                } else if(flag==1){
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
                    Parent root2 = loader.load();
                    PopUpControl popControl = loader.getController();
                    popControl.setPopUp("Consegna firmata con successo!");
                    Scene scene = new Scene(root2);
                    Stage stage = new Stage();
                    stage.setTitle("Avviso");
                    stage.setScene(scene);
                    stage.showAndWait();

                    loader.setLocation(ListaSpedizioniControl.class.getResource("ListaSpedizioni.fxml"));
                    Parent root1 = loader.load();
                    ListaSpedizioniControl listaSpedizioniControl= loader.getController();
                    listaSpedizioniControl.setField();
                    stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Lista Spedizioni");
                    stage.setScene(new Scene(root1));
                    stage.show();

                }

            }
        }
    }






}
