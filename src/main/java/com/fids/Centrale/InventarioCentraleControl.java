package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.Farmaco;
import Entity.Lotto;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class InventarioCentraleControl {

    private String sommaquantita = "";
    @FXML
    private TreeTableView<Farmaco> listaFarmaciTable;
    @FXML
    private TreeTableColumn<Farmaco, String> nomeCol;
    @FXML
    private TreeTableColumn<Farmaco, String> principioCol;
    @FXML
    private TreeTableColumn<Farmaco, String> quantitaCol;
    @FXML
    private TreeTableColumn<Farmaco, String> lottoCol;
    @FXML
    private TreeTableColumn<Farmaco, String> scadenzaCol;
    @FXML
    private Button homePageCentraleButton;
    @FXML
    private TextField nomeFarmacoField;
    @FXML
    private ChoiceBox<String> principioAttivoField;
    @FXML
    private DatePicker dataDiScadenzaField;
    @FXML
    private Button filtraButton;
    private DBMSBoundary dbms = new DBMSBoundary();

    LinkedList<Farmaco> listaFarmaci = new LinkedList<>();

    public void stampaTabella(LinkedList<Farmaco> listaFarmaci){
        TreeItem root = new TreeItem(new Farmaco(" ", " ","",""," "));
        TreeItem farmaco;
        for(Farmaco f: listaFarmaci)
            principioAttivoField.getItems().add(f.getPrincipioAttivo());
        for(Farmaco f: listaFarmaci) {

            farmaco = new TreeItem(new Farmaco(f.getNomeFarmaco(), f.getPrincipioAttivo(), f.getQuantitaFarmaco(), " ", " "));
            root.getChildren().add(farmaco);

            //principioAttivoField.getItems().add(f.getPrincipioAttivo());

            for(Lotto l: f.getListaLotti()){
                farmaco.getChildren().add(new TreeItem<>(new Farmaco(" ", " ", l.getQuantitaLotto(), l.getCodiceLotto(), l.getDataScadenza())));
            }
            TreeItem empty = new TreeItem(new Farmaco("","","","",""));
            root.getChildren().add(empty);
        }

        nomeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNomeFarmaco()));
        principioCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getPrincipioAttivo()));
        quantitaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty((String.valueOf(param.getValue().getValue().getQuantitaFarmaco()))));
        lottoCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getLotto()));
        scadenzaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDataScadenza()));

        root.setExpanded(true);
        listaFarmaciTable.setRoot(root);
        listaFarmaciTable.setShowRoot(false);
    }

    public void homePageCentrale(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HomePageCentraleControl.class.getResource("HomePageCentrale.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) homePageCentraleButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void applicaFiltro(ActionEvent event) {
        String nomeFarmaco = nomeFarmacoField.getText();
        String principioAttivo = principioAttivoField.getValue();
        String dataDiScadenza = null;
        if(dataDiScadenzaField.getValue() != null)
            dataDiScadenza = dataDiScadenzaField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        TreeItem root = new TreeItem(new Farmaco(" ", " ","",""," "));
        TreeItem farmaco;
        for(Farmaco f: listaFarmaci) {
            farmaco = new TreeItem(new Farmaco(f.getNomeFarmaco(), f.getPrincipioAttivo(), f.getQuantitaFarmaco(), " ", " "));

            if((nomeFarmaco != null && f.getNomeFarmaco().toLowerCase().startsWith(nomeFarmaco.toLowerCase())) && (principioAttivo != null && principioAttivo.equals(f.getPrincipioAttivo()))) {
                root.getChildren().add(farmaco);

                // principioAttivoField.getItems().add(f.getPrincipioAttivo());
            }

            for(Lotto l: f.getListaLotti()){
                if((dataDiScadenza != null && dataDiScadenza.equalsIgnoreCase(l.getDataScadenza())) || dataDiScadenza == null)
                    farmaco.getChildren().add(new TreeItem<>(new Farmaco(" ", " ", l.getQuantitaLotto(), l.getCodiceLotto(), l.getDataScadenza()))); }
        }

        nomeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNomeFarmaco()));
        principioCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getPrincipioAttivo()));
        quantitaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty((String.valueOf(param.getValue().getValue().getQuantitaFarmaco()))));
        lottoCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getLotto()));
        scadenzaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDataScadenza()));

        root.setExpanded(true);
        listaFarmaciTable.setRoot(root);
        listaFarmaciTable.setShowRoot(false);


    }


}
