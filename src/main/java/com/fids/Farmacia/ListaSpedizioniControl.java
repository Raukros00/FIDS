package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Spedizione;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class ListaSpedizioniControl {

    @FXML private TableView<Spedizione> ordiniInArrivoTable = new TableView<>();

    @FXML private TableColumn<Spedizione, Integer> spedizioneCol;
    @FXML private TableColumn<Spedizione, String> dataConsegnaCol;
    @FXML private TableColumn<Spedizione, Spedizione> visualizzaCol;
    @FXML private TableColumn<Spedizione, Spedizione> modificaCol;

    @FXML private TableView<Spedizione> ordiniArchiviatiTable = new TableView<>();

    @FXML private TableColumn<Spedizione, Integer> spedizioneArcCol;
    @FXML private TableColumn<Spedizione, String> consegnaArcCol;
    @FXML private TableColumn<Spedizione, Spedizione> visualizzaArcCol;

    DBMSBoundary dbms = new DBMSBoundary();
    LinkedList<Spedizione> listaSpedizioni = new LinkedList<>();

    public void setDatiOrdini(int ID_FARMACIA){
        listaSpedizioni = dbms.getListaSpedizioni(ID_FARMACIA);
        stampaSpedizioni(listaSpedizioni);
    }

    public void stampaSpedizioni(LinkedList<Spedizione> listaSpedizioni){

        spedizioneCol.setCellValueFactory(new PropertyValueFactory<Spedizione, Integer>("IDSpedizione"));
        dataConsegnaCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("dataConsegna"));

        visualizzaCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        visualizzaCol.setCellFactory(param -> new TableCell<Spedizione, Spedizione>() {
            private final Button visualizzaButton = new Button("Visualizza");

            @Override
            protected void updateItem(Spedizione f, boolean empty) {
                super.updateItem(f, empty);

                if (f == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(visualizzaButton);

                visualizzaButton.setOnAction(event -> {

                });
            }
        });

        modificaCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        modificaCol.setCellFactory(param -> new TableCell<Spedizione, Spedizione>() {
            private final Button modificaButton = new Button("Modifica");

            @Override
            protected void updateItem(Spedizione f, boolean empty) {
                super.updateItem(f, empty);

                if (f == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(modificaButton);

                modificaButton.setOnAction(event -> {

                });
            }
        });


        /*************************************
         *
         *         Tabella Archiviati
         *
         **************************************/

        spedizioneArcCol.setCellValueFactory(new PropertyValueFactory<Spedizione, Integer>("IDSpedizione"));
        consegnaArcCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("dataConsegna"));

        visualizzaArcCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        visualizzaArcCol.setCellFactory(param -> new TableCell<Spedizione, Spedizione>() {
            private final Button visualizzaArcButton = new Button("Visualizza");

            @Override
            protected void updateItem(Spedizione f, boolean empty) {
                super.updateItem(f, empty);

                if (f == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(visualizzaArcButton);

                visualizzaArcButton.setOnAction(event -> {

                });
            }
        });

        for(Spedizione s : listaSpedizioni){
            if(s.getStatoConsegna() == 0){
                ordiniInArrivoTable.getItems().add(new Spedizione(s.getIDSpedizione(), s.getDataConsegna(), s.getStatoConsegna()));
            }
            else{
                ordiniArchiviatiTable.getItems().add(new Spedizione(s.getIDSpedizione(), s.getDataConsegna(), s.getStatoConsegna()));
            }
        }
       // ordiniInArrivoTable.setItems(FXCollections.observableArrayList(listaSpedizioni));
    }

    public void creaOrdine(ActionEvent actionEvent) {
    }

    public void homeFarmacia(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomeFarmacia.fxml"));
        Parent root = loader.load();
        HomeFarmaciaControl homeFControl = loader.getController();
        homeFControl.setLabels();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home Farmacia");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
