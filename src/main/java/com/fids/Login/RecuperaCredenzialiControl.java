package com.fids.Login;

import DBMSB.DBMSBoundary;
import com.fids.PopUp.PopUpControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;


public class RecuperaCredenzialiControl{

    @FXML
    public Button indietroButton;
    @FXML
    public Button richiediPasswordButton;
    @FXML
    public TextField emailField;

    private DBMSBoundary dbms = new DBMSBoundary();

    public void richiediPassword(ActionEvent event){

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append((char) (97 + Math.random() * 25));
            sb.append((char) (65 + Math.random() * 25));
        }
        sb.append((int) (Math.random() * 1000) + 1);

        String password_toSend = sb.toString();

        String emailProg = "fids.farmacia@gmail.com";
        String password = "qiawfoyrzasnbpia";
        String email = emailField.getText();

        if(dbms.verificaEmail(email)) {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailProg, password);
                }
            });

            try{

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("fids.fortissimi@gmail.com"));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(email)
                );

                message.setSubject("Recupera Credenziali");
                message.setText("Ecco le tue credenziali di accesso a FIDS, Farmacie I Dei Sopravvissuti: " +
                        "\nMail: " + email +
                        "\nPassword: " + password_toSend);

                MessageDigest md5 = null;

                try {
                    md5 = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                md5.update(StandardCharsets.UTF_8.encode(password_toSend));
                password_toSend = String.format("%032x", new BigInteger(1, md5.digest()));

                dbms.aggiornaPassword(password_toSend, email);

                Transport.send(message);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                PopUpControl popControl = loader.getController();
                popControl.setPopUp("Email mandata\ncon successo!");
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Email mandata");
                stage.setScene(scene);
                stage.showAndWait();

                loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("login.fxml"));
                root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Login");
                stage.setScene(new Scene(root));
                stage.show();


            }catch (MessagingException mex){

                mex.printStackTrace();

            }

        }else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PopUpControl.class.getResource("error.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PopUpControl popControl = loader.getController();
            popControl.setPopUp("Email errata!");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avviso");
            stage.setScene(scene);
            stage.show();

        }
    }

    public void autenticazione(ActionEvent event){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AccessControl loginControl = loader.getController();
        loginControl.setLabels();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();


    }

}
