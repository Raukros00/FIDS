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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public Button filtraButton;
    private DBMSBoundary dbms = new DBMSBoundary();
    LinkedList<Farmaco> listaFarmaci = new LinkedList<>();

    public void setField() {

        listaFarmaci = dbms.getInventarioCentrale();

        ArrayList<String> principiAttivi = new ArrayList<>();
        principiAttivi.add("a");
        for(Farmaco f : listaFarmaci){
            boolean add=true;
            for(String s : principiAttivi){
                if(f.getPrincipioAttivo().equals(s))
                    add=false;
            }
            if(add){
                principiAttivi.add(f.getPrincipioAttivo());
                principioAttivoField.getItems().add(f.getPrincipioAttivo());
            }
        }
        stampaTabella(listaFarmaci);
    }

    public void stampaTabella(LinkedList<Farmaco> listaFarmaci) {
        TreeItem root = new TreeItem(new Farmaco(" ", " ", " ", " ", "", " "));
        TreeItem farmaco;

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
                            if(getTreeTableRow().getItem()!=null){
                                if (empty || getTreeTableRow().getItem().getNomeFarmaco()==" ") {
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
                        }
                    };
                    return cell;
                }
            };
            modificaCol.setCellFactory(cellFactory);

            for (Lotto l : f.getListaLotti()) {
                farmaco.getChildren().add(new TreeItem<>(new Farmaco(" ", " ", " ", String.valueOf(l.getQuantitaLotto()), l.getCodiceLotto(), l.getDataScadenza())));
            }
        }
        TreeItem last = new TreeItem(new Farmaco(" ", "", " ", "", "", ""));
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

    public void applicaFiltro(ActionEvent event) {
        String nomeFarmaco = nomeFarmacoField.getText();
        String principioAttivo = principioAttivoField.getValue();
        if(principioAttivo==null) principioAttivo="";

        dataDiScadenzaField.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        String dataDiScadenza;
        try{
            dataDiScadenza = String.valueOf(dataDiScadenzaField.getValue());
            if(dataDiScadenza != null)
                dataDiScadenza = dataDiScadenzaField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (NullPointerException e){
            dataDiScadenza = null;
            dataDiScadenzaField.setValue(null);
        }

        System.err.println(dataDiScadenza);

        TreeItem root = new TreeItem(new Farmaco(" ", " "," ","",""," "));
        TreeItem farmaco;
        for(Farmaco f: listaFarmaci) {
            farmaco = new TreeItem(new Farmaco(f.getNomeFarmaco(), f.getPrincipioAttivo(), f.getTipologia(), f.getQuantitaFarmaco(), " ", " "));

            System.err.println(f.getNomeFarmaco().toLowerCase() + " " + nomeFarmaco.toLowerCase() + " " + f.getNomeFarmaco().toLowerCase().contains(nomeFarmaco.toLowerCase()));
            if (nomeFarmaco.isEmpty() && principioAttivo == "") {
                root.getChildren().add(farmaco);
            } else if (!nomeFarmaco.isEmpty() && f.getNomeFarmaco().toLowerCase().contains(nomeFarmaco.toLowerCase()) && principioAttivo == "") {
                root.getChildren().add(farmaco);
            } else if (nomeFarmaco.isEmpty() && f.getPrincipioAttivo().equals(principioAttivo)) {
                root.getChildren().add(farmaco);
            } else if (!nomeFarmaco.isEmpty() && f.getNomeFarmaco().toLowerCase().contains(nomeFarmaco.toLowerCase()) && f.getPrincipioAttivo().equals(principioAttivo))
                root.getChildren().add(farmaco);


            for (Lotto l : f.getListaLotti()) {
                if ((dataDiScadenza != null && dataDiScadenza.equalsIgnoreCase(l.getDataScadenza()) || dataDiScadenza == null))
                    farmaco.getChildren().add(new TreeItem<>(new Farmaco(" ", " ", "", String.valueOf(l.getQuantitaLotto()), l.getCodiceLotto(), l.getDataScadenza())));
            }
            if (farmaco.getChildren().isEmpty()) {
                root.getChildren().remove(farmaco);
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
        dataDiScadenzaField.setValue(null);

        nomeFarmaco=null;
        principioAttivo=null;
        dataDiScadenza=null;
    }

    public void resetTable(ActionEvent event) {
        nomeFarmacoField.clear();
        principioAttivoField.setValue(null);
        dataDiScadenzaField.setValue(null);
        stampaTabella(listaFarmaci);
    }
}
