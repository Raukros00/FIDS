package com.fids.Corriere;

import DBMSB.DBMSBoundary;
import Entity.Farmaco;
import Entity.Spedizione;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
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
    private TableColumn<Spedizione, Integer> distanzaCol;
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
            s.setDistanza(s.getDistanza() + " Km");



        }
        IDSpedizioneCol.setCellValueFactory(new PropertyValueFactory<Spedizione, Integer>("IDSpedizione"));
        farmaciaCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("nomeFarmacia"));
        indirizzoCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("indirizzoFarmacia"));
        distanzaCol.setCellValueFactory(new PropertyValueFactory<Spedizione, Integer>("distanza"));
        dataConsegnaCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("dataConsegna"));
        firmaCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        firmaCol.setCellFactory(param -> new TableCell<Spedizione, Spedizione>() {
            private final Button firmaButton = new Button("Firma");

            @Override
            protected void updateItem(Spedizione s, boolean empty) {
                super.updateItem(s, empty);

                if (s == null || !String.valueOf(LocalDate.now()).equals(s.getDataConsegna())) {
                    setGraphic(null);
                    return;
                }

                setGraphic(firmaButton);

                firmaButton.setOnAction(event -> {
                    Spedizione sp = getTableRow().getItem();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(ListaSpedizioniControl.class.getResource("Firma.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    FirmaControl firmaControl = loader.getController();
                    firmaControl.setPresets(sp);
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Firma consegna");
                    stage.setScene(new Scene(root));
                    stage.show();
                });


            }
        });
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
