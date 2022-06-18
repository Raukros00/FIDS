package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Farmaco;
import Entity.GlobalData;
import Entity.Lotto;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.LinkedList;

public class VenditaControl extends GlobalData {

    @FXML public Button aggiungiFarmacoButton;
    @FXML private Spinner<Integer> quantitaLottoField;
    @FXML private TextField codLottoField;
    @FXML private TableView<Farmaco> venditaTable = new TableView<>();
    @FXML private TableColumn<Farmaco, String> nomeCol;
    @FXML private TableColumn<Farmaco, String> lottoCol;
    @FXML private TableColumn<Farmaco, Integer> quantitaCol;
    @FXML private TableColumn<Farmaco, Farmaco> rimuoviCol;
    @FXML private Label errorLabel;

    SpinnerValueFactory<Integer> valoriQuantita = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);

    int IDFarmacia;
    Farmaco temp = new Farmaco();
    String codLotto;
    int quantita;
    DBMSBoundary dbms = new DBMSBoundary();
    LinkedList<Farmaco> listaFarmaci;
    public LinkedList<Farmaco> venditaFarmaci = new LinkedList<>();

    public void setDatiVendita(int IDFarmacia) {
        this.IDFarmacia = IDFarmacia;
        valoriQuantita.setValue(1);
        quantitaLottoField.setValueFactory(valoriQuantita);
        listaFarmaci = dbms.getInventarioFarmacia(IDFarmacia);


        nomeCol.setCellValueFactory(new PropertyValueFactory<Farmaco, String>("nomeFarmaco"));
        lottoCol.setCellValueFactory(new PropertyValueFactory<Farmaco, String>("lottoS"));
        quantitaCol.setCellValueFactory(new PropertyValueFactory<Farmaco, Integer>("quantitaFarmacoInt"));
        quantitaCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantitaCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Farmaco, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Farmaco, Integer> event) {

                Farmaco newF = event.getRowValue();
                for(Farmaco f : listaFarmaci){
                    for(Lotto l : f.getListaLotti()){
                        if(l.getCodiceLotto().equalsIgnoreCase(newF.getLottoS())){
                            if(Integer.parseInt(l.getQuantitaLotto()) >= event.getNewValue()) {
                                newF.setQuantitaFarmacoInt(event.getNewValue());
                                errorLabel.setText("");
                            }
                            else
                                errorLabel.setText("La nuova quantità è superiore a quella disponibile!");
                        }
                    }
                }
            }
        });

        rimuoviCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        rimuoviCol.setCellFactory(param -> new TableCell<Farmaco, Farmaco>() {
            private final Button deleteButton = new Button("Rimuovi");

            @Override
            protected void updateItem(Farmaco f, boolean empty) {
                super.updateItem(f, empty);

                if (f == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> getTableView().getItems().remove(f)

                );
                venditaFarmaci.remove(f);
            }
        });


        venditaTable.setEditable(true);
    }


    public void aggiungiVendita(ActionEvent actionEvent) {

        codLotto = codLottoField.getText();
        quantita = quantitaLottoField.getValue();
        Boolean findLotto = false;

        for(Farmaco f : listaFarmaci){

            for(Lotto l : f.getListaLotti()) {

                if(l.getCodiceLotto().equalsIgnoreCase(codLotto)){
                    findLotto = true;
                    if(Integer.valueOf(l.getQuantitaLotto()) >= quantita) {
                        temp = new Farmaco(f.getNomeFarmaco(), codLotto, quantita);
                        addFarmaco(temp);
                        System.err.println(this.venditaFarmaci.size());
                        //venditaTable.setItems(FXCollections.observableArrayList(venditaFarmaci));
                        venditaTable.getItems().add(temp);
                        errorLabel.setText("");
                    }
                    else
                        errorLabel.setText("La quantità inserita non è presente in magazzino!");
                }
            }
        }

        if(!findLotto)
            errorLabel.setText("Il codice lotto non esiste!");





    }

    public void addFarmaco(Farmaco tmp){
        this.venditaFarmaci.add(tmp);
    }

    public void vendita(ActionEvent actionEvent) {
        System.err.println(this.venditaFarmaci.size());
        for(Farmaco f : venditaFarmaci){
            System.out.println(f.getNomeFarmaco() + " : "  + f.getLottoS() + " : " + f.getQuantitaFarmacoInt());
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

    @FXML
    public void initialize() {



    }

}
