package com.fids.Farmacia;

import Entity.LottoSpedizione;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.LinkedList;

public class FarmaciNonArrivatiControl {

    @FXML private Button segnalaButton;
    @FXML private Button annullaButton;

    @FXML private TableView<LottoSpedizione> farmaciMancantiTable = new TableView<>();
    @FXML private TableColumn<LottoSpedizione, String> nomeCol;
    @FXML private TableColumn<LottoSpedizione, Integer> quantitaCol;

    boolean segnala;
    Stage stage;


    public void setFarmaciNonArrivati(LinkedList<LottoSpedizione> listaFarmaci){

        nomeCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, String>("nomeFarmaco"));
        quantitaCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, Integer>("quantita"));

        farmaciMancantiTable.setItems(FXCollections.observableArrayList(listaFarmaci));

        segnalaButton.setOnAction(e -> {
            this.segnala = true;
            stage = (Stage) segnalaButton.getScene().getWindow();
            stage.close();
        });

        annullaButton.setOnAction(e -> {
            this.segnala = false;
            stage = (Stage) annullaButton.getScene().getWindow();
            stage.close();
        });

    }

    public boolean getSegnala() { return this.segnala; }
}
