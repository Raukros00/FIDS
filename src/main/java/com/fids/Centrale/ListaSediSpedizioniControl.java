package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.Farmacia;
import Entity.GlobalData;
import Entity.Spedizione;
import com.fids.Corriere.FirmaControl;
import com.fids.Corriere.ListaSpedizioniControl;
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
import java.util.LinkedList;

public class ListaSediSpedizioniControl extends GlobalData {
    @FXML
    private Button indietroButton;
    @FXML
    private TableView<Farmacia> listaSediTable = new TableView<>();
    @FXML
    private TableColumn<Farmacia, String> nomeFarmaciaCol;
    @FXML
    private TableColumn<Farmacia, String> indirizzoCol;
    @FXML
    private TableColumn<Farmacia, Farmacia> visualizzaCol;
    DBMSBoundary dbms = new DBMSBoundary();
    LinkedList<Farmacia> listaSpedizioniSedi= new LinkedList<>();

    public void setDatiSediSpedizioni(){
        listaSpedizioniSedi=dbms.getListaSpedizioniSedi();
        stampaFarmacia(listaSpedizioniSedi);
    }

    private void stampaFarmacia(LinkedList<Farmacia> listaFarmacie){
        for(Farmacia f: listaFarmacie){
            f.setIndirizzoSede(f.getIndirizzoSede() + "\n" + f.getCitta());
        }
        nomeFarmaciaCol.setCellValueFactory(new PropertyValueFactory<Farmacia, String>("nomeSede"));
        indirizzoCol.setCellValueFactory(new PropertyValueFactory<Farmacia, String>("indirizzoSede"));
        visualizzaCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        visualizzaCol.setCellFactory(param -> new TableCell<Farmacia, Farmacia>() {
            private final Button visualizzaButton = new Button("Visualizza");

            @Override
            protected void updateItem(Farmacia f, boolean empty) {
                super.updateItem(f, empty);

                if (f == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(visualizzaButton);

                visualizzaButton.setOnAction(event -> {
                    Farmacia fa = getTableRow().getItem();
                    ID_FARMACIA=fa.getIDFarmacia();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(ListaSediSpedizioniControl.class.getResource("SpedizioniCompletate.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    SpedizioniFarmaciaControl spedizioniFarmaciaControl = loader.getController();
                    spedizioniFarmaciaControl.setDatiOrdini();
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Lista Spedizioni");
                    stage.setScene(new Scene(root));
                    stage.show();
                });
            }
        });
        listaSediTable.setItems(FXCollections.observableArrayList(listaFarmacie));


    }
    public void homepageCentrale(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomePageCentrale.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("HomePage Centrale");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
