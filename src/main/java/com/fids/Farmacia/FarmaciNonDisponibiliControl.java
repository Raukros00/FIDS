package com.fids.Farmacia;

import Entity.Farmaco;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.LinkedList;

public class FarmaciNonDisponibiliControl {
    @FXML private TableView<Farmaco> farmaciTable = new TableView<>();
    @FXML private TableColumn<Farmaco, String> nomeCol;
    @FXML private TableColumn<Farmaco, Integer> quantitaCol;
    @FXML private TableColumn<Farmaco, String> dataCol;
    @FXML private Button okayButton;

    public void setFarmaciNonDisponibili(LinkedList<Farmaco> listaFarmaci){

        nomeCol.setCellValueFactory(new PropertyValueFactory<Farmaco, String>("nomeFarmaco"));
        quantitaCol.setCellValueFactory(new PropertyValueFactory<Farmaco, Integer>("quantitaFarmacoInt"));
        farmaciTable.setItems(FXCollections.observableArrayList(listaFarmaci));

    }
    public void closePopUp(ActionEvent actionEvent) {
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }
}
