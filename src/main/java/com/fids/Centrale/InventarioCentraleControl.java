package com.fids.Centrale;

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
import javafx.util.Callback;

import java.io.IOException;
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
    private TreeTableColumn<Farmaco, Farmaco> modificaCol;
    @FXML
    private Button indietroButton;
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


    public void stampaTabella() {
        LinkedList<Farmaco> listaFarmaci = dbms.getInventarioCentrale();
        TreeItem root = new TreeItem(new Farmaco(" ", " ", " ", "", " "));
        TreeItem farmaco;

        principioAttivoField.getItems().add("");
        for (Farmaco f : listaFarmaci)
            principioAttivoField.getItems().add(f.getPrincipioAttivo());
        for (Farmaco f : listaFarmaci) {

            farmaco = new TreeItem(new Farmaco(f.getIDFarmaco(), f.getNomeFarmaco(), f.getPrincipioAttivo(), f.getQuantitaFarmaco(), " ", " ", f.getPeriodicitaProduzione(),f.getQuantitaProduzione()));
            root.getChildren().add(farmaco);


            Callback<TreeTableColumn<Farmaco, Farmaco>, TreeTableCell<Farmaco, Farmaco>> cellFactory = new Callback<>() {
                @Override
                public TreeTableCell call(final TreeTableColumn<Farmaco, Farmaco> param) {
                    final TreeTableCell<Farmaco, Farmaco> cell = new TreeTableCell<>() {

                        final Button modificaButton = new Button("Modifica Produzione");

                        @Override
                        public void updateItem(Farmaco item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                modificaButton.setOnAction(event -> {
                                    try {
                                        Farmaco f = getTreeTableRow().getItem();
                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation(getClass().getResource("ModificaProduzione.fxml"));
                                        Parent root = loader.load();
                                        ModificaProduzioneControl modificaProduzioneControl = loader.getController();
                                        modificaProduzioneControl.setPresets(f);
                                        Scene scene = new Scene(root);
                                        Stage stage = new Stage();
                                        stage.setTitle("Modifica Produzione");
                                        stage.setScene(scene);
                                        stage.show();
                                    }catch(Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                setGraphic(modificaButton);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };
            modificaCol.setCellFactory(cellFactory);

            for (Lotto l : f.getListaLotti()) {
                farmaco.getChildren().add(new TreeItem<>(new Farmaco(" ", " ", l.getQuantitaLotto(), l.getCodiceLotto(), l.getDataScadenza())));
            }
        }
        TreeItem last = new TreeItem(new Farmaco(" ", "", "", "", ""));
        root.getChildren().add(last);


        nomeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getNomeFarmaco()));
        principioCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getPrincipioAttivo()));
        quantitaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty((String.valueOf(param.getValue().getValue().getQuantitaFarmaco()))));
        lottoCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getLottoS()));
        scadenzaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDataScadenza()));

        root.setExpanded(true);
        listaFarmaciTable.setRoot(root);
        listaFarmaciTable.setShowRoot(false);
    }

    public void indietro(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomePageCentrale.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("HomePage Centrale");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void applicaFiltro(ActionEvent event) {/*
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
        lottoCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getLottoS()));
        scadenzaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Farmaco, String> param) -> new SimpleStringProperty(param.getValue().getValue().getDataScadenza()));

        root.setExpanded(true);
        listaFarmaciTable.setRoot(root);
        listaFarmaciTable.setShowRoot(false);

*/
    }
}
