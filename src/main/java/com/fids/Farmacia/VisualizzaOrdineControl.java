package com.fids.Farmacia;

import Entity.LottoSpedizione;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.LinkedList;

public class VisualizzaOrdineControl {

    @FXML private TableView<LottoSpedizione> farmaciTable = new TableView<>();
    @FXML private TableColumn<LottoSpedizione, String> nomeCol;
    @FXML private TableColumn<LottoSpedizione, String> codLottoCol;
    @FXML private TableColumn<LottoSpedizione, Integer> quantitaCol;
    @FXML private Button okayButton;

    public void closePopUp(ActionEvent actionEvent) {
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }
    public void setDatiSpedizione(LinkedList<LottoSpedizione> listaLotti) {

        nomeCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, String>("nomeFarmaco"));
        codLottoCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, String>("codiceLotto"));
        quantitaCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, Integer>("quantita"));

        farmaciTable.setItems(FXCollections.observableArrayList(listaLotti));

    }
}
