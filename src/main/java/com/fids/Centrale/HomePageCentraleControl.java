package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.GlobalData;
import Entity.Utente;
import com.fids.AccessApplication;
import com.fids.Login.ModificaPasswordControl;
import com.fids.PopUp.OffsetControl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class HomePageCentraleControl extends GlobalData {
    @FXML
    public Button logoutButton;
    @FXML
    private Label nomeCognomeLabel;
    @FXML
    private Label numSegnalazioniLabel;
    @FXML
    private Label time;
    @FXML
    private Label dateLabel;
    @FXML
    public Button registraUtenteButton;
    @FXML
    public Button segnalazioniButton;
    @FXML
    public Button inventarioCentraleButton;
    @FXML
    public Button spedizioniButton;
    @FXML
    public Button sediEContrattiButton;
    private Utente user;
    private Calendar cal;
    private int minute;
    private int hour;
    DBMSBoundary dbms = new DBMSBoundary();

    public void setLabels() {
        nomeCognomeLabel.setText(NOMINATIVO);
        NUM_SEGNALAZIONI=dbms.richiediNumSegnalazioni();
        numSegnalazioniLabel.setText(String.valueOf(NUM_SEGNALAZIONI));
        startClock();
    }

    public void setUser(Utente user) {
        startClock();
        this.user = user;
        NUM_SEGNALAZIONI=dbms.richiediNumSegnalazioni();

        NOMINATIVO = user.getNome() + " " + user.getCognome();
        NOME = user.getNome();
        COGNOME = user.getCognome();
        ID_UTENTE = user.getIDUtente();
        EMAIL = user.getEmail();
        PASSWORD = user.getPassword();
        RUOLO = user.getRuolo();

        nomeCognomeLabel.setText(NOMINATIVO);
        numSegnalazioniLabel.setText(String.valueOf(NUM_SEGNALAZIONI));
    }


    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AccessApplication.class.getResource("Login/login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

   public void gestioneInventarioCentrale(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("InventarioCentrale.fxml"));
       Parent root = loader.load();
       InventarioCentraleControl inventarioCControl = loader.getController();
       inventarioCControl.setField();
       Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
       stage.setTitle("Inventario Centrale");
       stage.setScene(new Scene(root));
       stage.show();
    }

    public void registraUtente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RegistraUtente.fxml"));
        Parent root = loader.load();
        RegistraUtenteControl registraUtenteControl = loader.getController();
        registraUtenteControl.setFields();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Registra Utente");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void segnalazioni(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Segnalazioni.fxml"));
        Parent root = loader.load();
        SegnalazioniControl segnalazioniControl=loader.getController();
        segnalazioniControl.setSegnalazioni();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Segnalazioni");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void spedizioni(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ListaSediSpedizioni.fxml"));
        Parent root = loader.load();
        ListaSediSpedizioniControl listaSediSpedizioniControl= loader.getController();
        listaSediSpedizioniControl.setDatiSediSpedizioni();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Lista delle farmacie");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void gestioneSedieContratti(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SedieContratti.fxml"));
        Parent root = loader.load();
        SedieContrattiControl sedieContrattiControl= loader.getController();
        sedieContrattiControl.setDatiFarmacia();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Lista delle farmacie");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void startClock() {
        if(DAY==null)
            DAY=LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
        Timeline clock = new Timeline(new KeyFrame(Duration.millis(1000 - Calendar.getInstance().get(Calendar.MILLISECOND)), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cal = Calendar.getInstance();
                minute = cal.get(Calendar.MINUTE);
                if(cal.get(Calendar.HOUR_OF_DAY)+OFFSETHOUR>=24){
                    OFFSETDAY+=(cal.get(Calendar.HOUR_OF_DAY)+OFFSETHOUR)/24;
                }
                HOUR = (cal.get(Calendar.HOUR_OF_DAY)+OFFSETHOUR)%24;
                time.setText(String.format("%02d : %02d", HOUR, minute));
                String dt = LocalDate.now().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, OFFSETDAY);
                dt = sdf.format(c.getTime());
                DAY=dt;
                dateLabel.setText(dt);


            }
        }), new KeyFrame(Duration.seconds(3)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void modificaCredenziali(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ModificaPasswordControl.class.getResource("ModificaCredenziali.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modifica Password");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void offset(MouseEvent mouseEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(OffsetControl.class.getResource("Offset.fxml"));
        Parent root = loader.load();
        OffsetControl offsetControl = loader.getController();
        offsetControl.setPresets();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Offset");
        stage.setScene(scene);
        stage.show();
    }
}