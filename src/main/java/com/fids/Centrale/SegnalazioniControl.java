package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.GlobalData;
import Entity.Segnalazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SegnalazioniControl extends GlobalData {

    @FXML
    private Button avantiButton;
    @FXML
    private Button indietroButton;
    @FXML
    private Button segnaComeLettoButton;
    @FXML
    private TextFlow segnalazioniField;
    private Text text;
    private int i;
    DBMSBoundary dbms=new DBMSBoundary();

    private ArrayList<Segnalazione> listaSegnalazioni=dbms.getSegnalazioni();

    public void setSegnalazioni() {


        segnalazioniField.getChildren().clear();
        if(NUM_SEGNALAZIONI==0){
            text = new Text("Non ci sono segnalazioni");
            segnalazioniField.getChildren().add(text);
        } else {
            String segnalazione = listaSegnalazioni.get(i).getDescrizione();
            Text s = new Text(segnalazione);
            s.setFont(Font.font("Helvetica", FontWeight.BOLD, 23));
            segnalazioniField.getChildren().add(s);
        }

    }
    public void segnalazioneLetta(ActionEvent event)throws IOException {
        if(NUM_SEGNALAZIONI != 0) {
            int row = dbms.deleteSegnalazione(listaSegnalazioni.get(i).getFKSpedizione());
            if (row == 1) {
                NUM_SEGNALAZIONI--;
                listaSegnalazioni.remove(i);
                if(!listaSegnalazioni.isEmpty())
                    i = (i + 1) % listaSegnalazioni.size();
                setSegnalazioni();
            }
        }
    }
    public void segnalazioneSuccessiva(ActionEvent event)throws IOException {
        if (NUM_SEGNALAZIONI != 0) {
            i = (i + 1) % listaSegnalazioni.size();
            setSegnalazioni();
        }
    }
    public void homePageCentrale(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomePageCentrale.fxml"));
        Parent root = loader.load();
        HomePageCentraleControl homeCControl = loader.getController();
        homeCControl.setLabels();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("HomePage Centrale");
        stage.setScene(new Scene(root));
        stage.show();
    }
}










