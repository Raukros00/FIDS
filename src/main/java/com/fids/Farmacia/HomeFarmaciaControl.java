package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Farmacia;
import Entity.GlobalData;
import Entity.Utente;
import com.fids.AccessApplication;
import com.fids.PopUp.PopUpControl;
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
    @FXML
    public Button modificaContrattoButton;
    @FXML
    public Button gestioneInventarioButton;
    @FXML
    public Button caricaConsegnaButton;
    @FXML
    public Button modificaCredenzialiButton;
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
    private boolean avviso;

    public void setUser(Utente user) {
        startClock();
        this.user = user;
        DBMSBoundary dbms = new DBMSBoundary();
        farmacia = dbms.richiediInfoHome(user.getIDSede());

        NOMINATIVO = user.getNome() + " " + user.getCognome();
        ID_UTENTE = user.getIDUtente();
        EMAIL = user.getEmail();
        PASSWORD = user.getPassword();
        NUM_CONSEGNE = farmacia.getNumConsegne();
        NOME_FARMACIA = farmacia.getNomeSede();
        INDIRIZZO_FARMACIA = farmacia.getIndirizzoSede();
        CITTA_FARMACIA = farmacia.getCitta();
        ID_FARMACIA = farmacia.getIDFarmacia();
        DISTANZA = farmacia.getDistanza();

        nomeCognomeLabel.setText(NOMINATIVO);
        numConsegneArrivoLabel.setText(String.valueOf(NUM_CONSEGNE));
        nomeFarmaciaLabel.setText(NOME_FARMACIA);
        indirizzoLabel.setText(INDIRIZZO_FARMACIA);
        cittaLabel.setText(CITTA_FARMACIA);

    }

    public void setLabels() {
        nomeCognomeLabel.setText(NOMINATIVO);
        numConsegneArrivoLabel.setText(String.valueOf(NUM_CONSEGNE));
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
        inventarioFControl.setDatiInventario();
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

    public void caricaConsegna(ActionEvent event) throws IOException {

        if(NUM_CONSEGNE == 0){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PopUpControl.class.getResource("error.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PopUpControl popControl = loader.getController();
            popControl.setPopUp("Non ci sono consegne da caricare!");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avviso");
            stage.setScene(scene);
            stage.show();
        }
        else{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CaricaConsegna.fxml"));
            Parent root = loader.load();
            CaricaConsegnaControl CCControl = loader.getController();
            CCControl.setDatiConsegna(ID_FARMACIA);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Carica Consegna");
            stage.setScene(new Scene(root));
            stage.show();

        }
    }

    public void modificaContratto(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModificaContratto.fxml"));
        Parent root = loader.load();
        ModificaContrattoControl ModificaContrattoFControl = loader.getController();
        ModificaContrattoFControl.setDatiContratto(ID_FARMACIA);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modifica Contratto");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void modificaCredenziali(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModificaCredenziali.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Modifica Password");
        stage.setScene(scene);
        stage.show();
    }

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

