package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Farmacia;
import Entity.GlobalData;
import Entity.Utente;
import com.fids.AccessApplication;
import com.fids.Login.ModificaPasswordControl;
import com.fids.PopUp.OffsetControl;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    @FXML Label dateLabel;
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
        NOME = user.getNome();
        COGNOME = user.getCognome();
        ID_UTENTE = user.getIDUtente();
        EMAIL = user.getEmail();
        PASSWORD = user.getPassword();
        RUOLO = user.getRuolo();
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
        startClock();
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
        loader.setLocation(ModificaPasswordControl.class.getResource("ModificaCredenziali.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Modifica Password");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void startClock() {
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
                if(HOUR >= 20 && NUM_CONSEGNE > 0 && !CHECKHOURS){
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PopUpControl.class.getResource("error.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    PopUpControl popControl = loader.getController();
                    popControl.setPopUp("Ci sono " + NUM_CONSEGNE + " consegne da caricare!");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Avviso");
                    stage.setScene(scene);
                    stage.show();
                    CHECKHOURS = true;
                }

            }
        }), new KeyFrame(Duration.seconds(3)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
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

