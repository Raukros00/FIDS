package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Farmaco;
import Entity.Lotto;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class InventarioFarmaciaControl {

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
    private Button homeFarmaciaButton;
    @FXML
    private TextField nomeFarmacoField;
    @FXML
    private ChoiceBox<String> principioAttivoField;
    @FXML
    private DatePicker dataDiScadenzaField;
    @FXML
    private Button filtraButton;
    private int idFarmacia;
    private DBMSBoundary dbms = new DBMSBoundary();

    LinkedList<Farmaco> listaFarmaci = new LinkedList<>();

    public void setDatiInventario(int idFarmacia) {
        this.idFarmacia = idFarmacia;
        listaFarmaci = dbms.getInventarioFarmacia(idFarmacia);
        stampaTabella(listaFarmaci);

        principioAttivoField.getItems().add("");
        for(Farmaco f: listaFarmaci)
            principioAttivoField.getItems().add(f.getPrincipioAttivo());
    }

    public void stampaTabella(LinkedList<Farmaco> listaFarmaci) {
        TreeItem root = new TreeItem(new Farmaco(" ", " ", "", "", " "));
        TreeItem farmaco;

        for (Farmaco f : listaFarmaci) {

            farmaco = new TreeItem(new Farmaco(f.getNomeFarmaco(), f.getPrincipioAttivo(), f.getQuantitaFarmaco(), " ", " "));
            root.getChildren().add(farmaco);


            for (Lotto l : f.getListaLotti()) {
                farmaco.getChildren().add(new TreeItem<>(new Farmaco(" ", " ", l.getQuantitaLotto(), l.getCodiceLotto(), l.getDataScadenza())));
            }
        }

        nomeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNomeFarmaco()));
        principioCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getPrincipioAttivo()));
        quantitaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty((String.valueOf(param.getValue().getValue().getQuantitaFarmaco()))));
        lottoCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getLottoS()));
        scadenzaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDataScadenza()));

        root.setExpanded(true);
        listaFarmaciTable.setRoot(root);
        listaFarmaciTable.setShowRoot(false);
    }

    public void homeFarmacia(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomeFarmacia.fxml"));
        Parent root = loader.load();
        HomeFarmaciaControl homeFControl = loader.getController();
        homeFControl.setLabels();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home Farmacia");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void applicaFiltro(ActionEvent event) {
        String nomeFarmaco = nomeFarmacoField.getText();
        String principioAttivo = principioAttivoField.getValue();
        if(principioAttivo==null) principioAttivo="";
        String dataDiScadenza = null;
        if(dataDiScadenzaField.getValue() != null)
            dataDiScadenza = dataDiScadenzaField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        TreeItem root = new TreeItem(new Farmaco(" ", " ","",""," "));
        TreeItem farmaco;
        for(Farmaco f: listaFarmaci) {
            farmaco = new TreeItem(new Farmaco(f.getNomeFarmaco(), f.getPrincipioAttivo(), f.getQuantitaFarmaco(), " ", " "));

            if(nomeFarmaco.isEmpty() && principioAttivo=="") {
                root.getChildren().add(farmaco);
            } else if (!nomeFarmaco.isEmpty() && f.getNomeFarmaco().toLowerCase().startsWith(nomeFarmaco.toLowerCase()) && principioAttivo==""){
                root.getChildren().add(farmaco);
            } else if (nomeFarmaco.isEmpty() && f.getPrincipioAttivo().equals(principioAttivo)){
                root.getChildren().add(farmaco);
            } else if (!nomeFarmaco.isEmpty() && f.getNomeFarmaco().toLowerCase().startsWith(nomeFarmaco.toLowerCase()) && f.getPrincipioAttivo().equals(principioAttivo))
                root.getChildren().add(farmaco);


            for(Lotto l: f.getListaLotti()){
                if((dataDiScadenza != null && dataDiScadenza.equalsIgnoreCase(l.getDataScadenza())) || dataDiScadenza == null)
                    farmaco.getChildren().add(new TreeItem<>(new Farmaco(" ", " ", l.getQuantitaLotto(), l.getCodiceLotto(), l.getDataScadenza()))); }
        }

        nomeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNomeFarmaco()));
        principioCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getPrincipioAttivo()));
        quantitaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty((String.valueOf(param.getValue().getValue().getQuantitaFarmaco()))));
        lottoCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getLottoS()));
        scadenzaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDataScadenza()));

        root.setExpanded(true);
        listaFarmaciTable.setRoot(root);
        listaFarmaciTable.setShowRoot(false);
            }

    public void resetTable(ActionEvent event) {
    stampaTabella(listaFarmaci);
    }
}
