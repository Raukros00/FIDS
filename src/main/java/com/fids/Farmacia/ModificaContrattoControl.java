package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Contratto;
import Entity.Farmaco;
import Entity.FarmacoContratto;
import com.fids.PopUp.PopUpControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.LinkedList;

public class ModificaContrattoControl {

    @FXML TableView<Farmaco> contrattoFarmaciTable = new TableView<>();
    @FXML TableColumn<Farmaco, String> nomeCol;
    @FXML TableColumn<Farmaco, Integer> quantitaPeriodicaCol;

    @FXML private Spinner<Integer> periodoConsegnaField;
    SpinnerValueFactory<Integer> valoriQuantita = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);

    DBMSBoundary dbms = new DBMSBoundary();
    Contratto contratto;
    LinkedList<Farmaco> farmaciDisponibili;
    LinkedList<FarmacoContratto> farmaciContratto;
    public void setDatiContratto(int ID_FARMACIA) {

        periodoConsegnaField.setValueFactory(valoriQuantita);
        farmaciDisponibili = dbms.getInventarioCentrale();
        contratto = dbms.getContratto(ID_FARMACIA);
        periodoConsegnaField.getValueFactory().setValue(contratto.getPerioditicita());
        farmaciContratto = contratto.getListaFarmaciContratto();
        for(Farmaco f : farmaciDisponibili){
            for(FarmacoContratto fc : farmaciContratto){
                if(f.getIDFarmaco()==fc.getIDFarmaco() && fc.getQuantitaRichiesta() > 0){
                    f.setQuantitaFarmacoInt(fc.getQuantitaRichiesta());
                }
            }
        }

        nomeCol.setCellValueFactory(new PropertyValueFactory<Farmaco, String>("nomeFarmaco"));
        quantitaPeriodicaCol.setCellValueFactory(new PropertyValueFactory<Farmaco, Integer>("quantitaFarmacoInt"));
        quantitaPeriodicaCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantitaPeriodicaCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Farmaco, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Farmaco, Integer> event) {
                if(event.getNewValue() < 0){
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PopUpControl.class.getResource("error.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    PopUpControl popControl = loader.getController();
                    popControl.setPopUp("La quantità inserita \nnon può essere negativa!");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Avviso");
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    for (Farmaco f : farmaciDisponibili) {
                        if (event.getRowValue().getIDFarmaco() == f.getIDFarmaco())
                            farmaciContratto.add(new FarmacoContratto(f.getIDFarmaco(), event.getNewValue()));
                    }
                }
            }
        });


        contrattoFarmaciTable.setItems(FXCollections.observableArrayList(farmaciDisponibili));
        contrattoFarmaciTable.setEditable(true);

    }
    public void updateFarmaciContratto(ActionEvent actionEvent) throws IOException {
        int periodo = periodoConsegnaField.getValue();
        if(periodo == contratto.getPerioditicita()) periodo = 0;

        dbms.updateContratto(farmaciContratto, periodo, contratto.getIDContratto());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
        Parent root = loader.load();
        PopUpControl popControl = loader.getController();
        popControl.setPopUp("Contratto aggiornato!");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Avviso");
        stage.setScene(scene);
        stage.showAndWait();

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomeFarmacia.fxml"));
        root = loader.load();
        HomeFarmaciaControl homeFControl = loader.getController();
        homeFControl.setLabels();
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Home Farmacia");
        stage.setScene(new Scene(root));
        stage.show();

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
