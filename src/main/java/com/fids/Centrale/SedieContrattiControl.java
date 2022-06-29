package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.Farmacia;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
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

public class SedieContrattiControl {
    DBMSBoundary dbms = new DBMSBoundary();
    @FXML
    private Button indietroButton;
    @FXML
    private TableView<Farmacia> listaSediTable = new TableView<>();
    @FXML
    private TableColumn<Farmacia, String> nomeFarmaciaCol;
    @FXML
    private TableColumn<Farmacia, String> cittaCol;
    @FXML
    private TableColumn<Farmacia, String> indirizzoCol;
    @FXML
    private TableColumn<Farmacia, Farmacia> modificaCol;

    public void setDatiFarmacia(){
        LinkedList<Farmacia> listaFarmacia = dbms.getFarmacie();
        stampaFarmacia(listaFarmacia);
    }

    private void stampaFarmacia(LinkedList<Farmacia> listaFarmacia){
        nomeFarmaciaCol.setCellValueFactory(new PropertyValueFactory<Farmacia, String>("nomeSede"));
        cittaCol.setCellValueFactory(new PropertyValueFactory<Farmacia, String>("citta"));
        indirizzoCol.setCellValueFactory(new PropertyValueFactory<Farmacia, String>("indirizzoSede"));
        modificaCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        modificaCol.setCellFactory(param -> new TableCell<Farmacia, Farmacia>() {
            private final Button modificaButton = new Button("Modifica Contratto");
            @Override
            protected void updateItem(Farmacia f, boolean empty) {
                super.updateItem(f, empty);

                if (f == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(modificaButton);

                /*modificaButton.setOnAction(event -> {
                    Farmacia fa = getTableRow().getItem();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SpedizioniFarmaciaControl.class.getResource("SpedizioniCompletate.fxml"));
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
        });*/


            }
        });
        listaSediTable.setItems(FXCollections.observableArrayList(listaFarmacia));
    }

    public void homePageCentrale(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomePageCentrale.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("HomePage Centrale");
        stage.setScene(new Scene(root));
        stage.show();
    }
}