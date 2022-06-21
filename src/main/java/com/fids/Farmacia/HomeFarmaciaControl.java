package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Farmacia;
import Entity.GlobalData;
import Entity.Utente;
import com.fids.AccessApplication;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Calendar;

public class HomeFarmaciaControl extends GlobalData{
    @FXML
    public Button venditaFarmaciButton;
    public Button modificaContrattoButton;
    public Button gestioneInventarioButton;
    public Button caricaConsegnaButton;
    @FXML
    public Button logoutButton;
    @FXML
    private Label nomeCognomeLabel;
    @FXML
    private Label numConsegneArrivoLabel;
    @FXML
    private Label nomeFarmaciaLabel;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private Label cittaLabel;
    @FXML Label time;
    private Utente user;
    private Farmacia farmacia;
    private Calendar cal;
    private int minute;
    private int hour;
    private String am_pm;
    boolean avviso;

    public void setUser(Utente user) {
        startClock();
        this.user = user;
        DBMSBoundary dbms = new DBMSBoundary();
        farmacia = dbms.richiediInfoHome(user.getIDSede());

        NOMINATIVO = user.getNome() + " " + user.getCognome();
        NUM_CONSEGNE = String.valueOf(farmacia.getNumConsegne());
        NOME_FARMACIA = farmacia.getNomeSede();
        INDIRIZZO_FARMACIA = farmacia.getIndirizzoSede();
        CITTA_FARMACIA = farmacia.getCitta();
        ID_FARMACIA = farmacia.getIDFarmacia();
        DISTANZA = farmacia.getDistanza();

        nomeCognomeLabel.setText(NOMINATIVO);
        numConsegneArrivoLabel.setText(NUM_CONSEGNE);
        nomeFarmaciaLabel.setText(NOME_FARMACIA);
        indirizzoLabel.setText(INDIRIZZO_FARMACIA);
        cittaLabel.setText(CITTA_FARMACIA);

    }

    public void setLabels() {
        nomeCognomeLabel.setText(NOMINATIVO);
        numConsegneArrivoLabel.setText(NUM_CONSEGNE);
        nomeFarmaciaLabel.setText(NOME_FARMACIA);
        indirizzoLabel.setText(INDIRIZZO_FARMACIA);
        cittaLabel.setText(CITTA_FARMACIA);
    }


    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AccessApplication.class.getResource("Login/login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.setTitle("Login");
        window.setScene(new Scene(root));
    }

    public void gestioneInventario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("InventarioFarmacia.fxml"));
        Parent root = loader.load();
        InventarioFarmaciaControl inventarioFControl = loader.getController();
        inventarioFControl.setDatiInventario(ID_FARMACIA);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventario Farmacia");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void vendita(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Vendita.fxml"));
        Parent root = loader.load();
        VenditaControl venditaFControl = loader.getController();
        venditaFControl.setDatiVendita(ID_FARMACIA);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Vendita");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void listaOrdini(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ListaSpedizioniFarmacia.fxml"));
        Parent root = loader.load();
        ListaSpedizioniControl listaSpedizioniFControl = loader.getController();
        listaSpedizioniFControl.setDatiOrdini(ID_FARMACIA);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Lista Ordini");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /*public void modificaCredenziali(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModificaPassword.fxml"));
        Parent root = loader.load();
        ModificaPasswordControl modificaPasswordControl = loader.getController();
        modificaPasswordControl.setPassword(PASSWORD);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modifica Password");
        stage.setScene(new Scene(root));
        stage.show();
    }*/

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.millis(1000 - Calendar.getInstance().get(Calendar.MILLISECOND)), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                cal = Calendar.getInstance();
                minute = cal.get(Calendar.MINUTE);
                hour = cal.get(Calendar.HOUR);
                am_pm = (cal.get(Calendar.AM_PM) == 0) ? "AM" : "PM";
                if(am_pm=="PM")
                    hour=hour+12;
                time.setText(String.format("%02d : %02d", hour, minute));

            }
        }), new KeyFrame(Duration.seconds(60)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}

