package com.fids.Corriere;

import DBMSB.DBMSBoundary;
import Entity.Farmaco;
import Entity.LottoSpedizione;
import Entity.Spedizione;
import com.fids.Farmacia.HomeFarmaciaControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class ListaSpedizioniControl {
    @FXML
    private Button indietroButton;
    @FXML
    private TableView<Spedizione> listaSpedizioniTable = new TableView<>();
    @FXML
    private TableColumn<Spedizione, Integer> IDSpedizioneCol;
    @FXML
    private TableColumn<Spedizione, String> farmaciaCol;
    @FXML
    private TableColumn<Spedizione, String> indirizzoCol;
    @FXML
    private TableColumn<Spedizione, String> dataConsegnaCol;
    @FXML
    private TableColumn<Spedizione, Spedizione> firmaCol;


    private ArrayList<Spedizione> listaSpedizioni=new ArrayList<Spedizione>();

    private DBMSBoundary dbms = new DBMSBoundary();



    public void setField() {

        listaSpedizioni = dbms.getListaSpedizioni();
        System.out.println(listaSpedizioni.size());
        stampaTabella(listaSpedizioni);
    }

    private void stampaTabella(ArrayList<Spedizione> listaSpedizioni){
        for(Spedizione s: listaSpedizioni){
            s.setIndirizzoFarmacia(s.getCitta()+", "+s.getIndirizzoFarmacia()+"\n"+"Distanza: "+s.getDistanza()+" Km");
        }
        IDSpedizioneCol.setCellValueFactory(new PropertyValueFactory<Spedizione, Integer>("IDSpedizione"));
        farmaciaCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("nomeFarmacia"));
        indirizzoCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("indirizzoFarmacia"));
        dataConsegnaCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("dataConsegna"));
        firmaCol.setCellValueFactory(new PropertyValueFactory<Spedizione, Spedizione>("firma"));

        listaSpedizioniTable.setItems(FXCollections.observableArrayList(listaSpedizioni));

    }

























        public void homeCorriere(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomePageCorriere.fxml"));
        Parent root = loader.load();
       /* HomeFarmaciaControl homeFControl = loader.getController();
        homeFControl.setLabels();
        */
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home Corriere");
        stage.setScene(new Scene(root));
        stage.show();
    }





}
