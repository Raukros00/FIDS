package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Contratto;
import Entity.Farmaco;
import Entity.FarmacoContratto;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class ModificaContrattoControl {

    @FXML TableView<Farmaco> contrattoFarmaciTable = new TableView<>();
    @FXML TableColumn<Farmaco, String> nomeCol;
    @FXML TableColumn<Farmaco, Integer> quantitaPeriodicaCol;
    DBMSBoundary dbms = new DBMSBoundary();
    Contratto contratto;
    LinkedList<Farmaco> farmaciDisponibili;
    LinkedList<FarmacoContratto> farmaciContratto = new LinkedList<>();
    public void setDatiContratto(int ID_FARMACIA) {

        farmaciDisponibili = dbms.getInventarioCentrale();
        contratto = dbms.getContratto(ID_FARMACIA);
        farmaciContratto = contratto.getListaFarmaciContratto();
        System.err.println(farmaciDisponibili.size());
        for(Farmaco f : farmaciDisponibili){
            for(FarmacoContratto fc : farmaciContratto){
                if(f.getIDFarmaco()==fc.getIDFarmaco() && fc.getQuantitaRichiesta() > 0){
                    f.setQuantitaFarmacoInt(fc.getQuantitaRichiesta());
                }
            }
        }

        nomeCol.setCellValueFactory(new PropertyValueFactory<Farmaco, String>("nomeFarmaco"));
        quantitaPeriodicaCol.setCellValueFactory(new PropertyValueFactory<Farmaco, Integer>("quantitaFarmacoInt"));
        quantitaPeriodicaCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Farmaco, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Farmaco, Integer> event) {
                for(Farmaco f : farmaciDisponibili){
                    for(FarmacoContratto fc : farmaciContratto){
                        if(f.getIDFarmaco() == fc.getIDFarmaco())
                            fc.setQuantitaRichiesta(event.getNewValue());
                    }
                }
            }
        });


        contrattoFarmaciTable.setItems(FXCollections.observableArrayList(farmaciDisponibili));
        contrattoFarmaciTable.setEditable(true);

    }
    public void updateFarmaciContratto(ActionEvent actionEvent) {
        for(FarmacoContratto fc : farmaciContratto){
        }
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
