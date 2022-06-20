package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Farmaco;
import Entity.GlobalData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.LinkedList;

public class AggiungiOrdineControl extends GlobalData {

    DBMSBoundary dbms = new DBMSBoundary();
    LinkedList<Farmaco> listaFarmaci;

    @FXML TableView<Farmaco> ordineTable = new TableView<>();
    @FXML TableColumn<Farmaco, String> nomeCol;
    @FXML private TableColumn<Farmaco, Integer> quantitaCol;


    public void setDatiOrdine() {

        listaFarmaci = dbms.getInventarioCentrale();

        nomeCol.setCellValueFactory(new PropertyValueFactory<Farmaco, String>("nomeFarmaco"));
        quantitaCol.setCellValueFactory(new PropertyValueFactory<Farmaco, Integer>("quantitaFarmacoInt"));
        quantitaCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantitaCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Farmaco, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Farmaco, Integer> event) {
                Farmaco newF = event.getRowValue();
                newF.setQuantitaFarmacoInt(event.getNewValue());
            }
        });

        ordineTable.setItems(FXCollections.observableArrayList(listaFarmaci));
        ordineTable.setEditable(true);


    }
    public void listaSpedizioni(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ListaSpedizioniControl.class.getResource("ListaSpedizioniFarmacia.fxml"));
        Parent root = loader.load();
        ListaSpedizioniControl homeFControl = loader.getController();
        homeFControl.setDatiOrdini(ID_FARMACIA);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Lista Ordini");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void creaOrdine(ActionEvent actionEvent) {
    }
}
