package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Farmaco;
import Entity.Lotto;
import Entity.LottoSpedizione;
import com.fids.PopUp.PopUpControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public class ModificaOrdineControl {

    @FXML private TableView<Farmaco> ordineTable = new TableView<>();
    @FXML private Button ordinaButton;
    @FXML private TableColumn<Farmaco, String> nomeCol;
    @FXML private TableColumn<Farmaco, Integer> quantitaCol;

    LinkedList<Farmaco> listaFarmaciInVendita = new LinkedList<>();
    ListIterator<Farmaco> farmaciIterator;
    LinkedList<LottoSpedizione> listaFarmaciOrdine;
    LinkedList<LottoSpedizione> ordine = new LinkedList<>();
    LinkedList<Farmaco> nuovaSpedizione = new LinkedList<>();
    DBMSBoundary dbms = new DBMSBoundary();
    LottoSpedizione ls;

    int IDOrdine;
    boolean mantieni;
    boolean add = true;
    public void setModificaOrdine(LinkedList<LottoSpedizione> listaFarmaci, int IDOrdine) {

        listaFarmaciInVendita = dbms.getInventarioCentrale();
        farmaciIterator = listaFarmaciInVendita.listIterator();
        this.listaFarmaciOrdine = listaFarmaci;
        this.IDOrdine = IDOrdine;

        nomeCol.setCellValueFactory(new PropertyValueFactory<Farmaco, String>("nomeFarmaco"));
        quantitaCol.setCellValueFactory(new PropertyValueFactory<Farmaco, Integer>("quantitaFarmacoInt"));
        quantitaCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        quantitaCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Farmaco, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Farmaco, Integer> event) {
                Farmaco newF= event.getRowValue();
                newF.setQuantitaFarmacoInt(event.getNewValue());
            }
        });

        for(Farmaco f : listaFarmaciInVendita){
            for(LottoSpedizione l : listaFarmaci){
                if(f.getNomeFarmaco().equalsIgnoreCase(l.getNomeFarmaco()))
                    f.setQuantitaFarmacoInt(f.getQuantitaFarmacoInt() + l.getQuantita());
            }
        }

        ordineTable.setItems(FXCollections.observableArrayList(listaFarmaciInVendita));
        ordineTable.setEditable(true);
    }

    public void updateOrdine(ActionEvent actionEvent) throws IOException, SQLException {

        LocalDate oggi = LocalDate.now();
        LocalDate scadenzaLotto;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate currentDataPlusTwo = oggi.plusMonths(2);
        currentDataPlusTwo = LocalDate.parse(String.valueOf(currentDataPlusTwo), formatter);
        boolean empty = true;
        boolean exit = false;

        dbms.modificaSpedizione(IDOrdine, listaFarmaciOrdine);

        Iterator<Farmaco> farmaciIterator = listaFarmaciInVendita.iterator();

        while(farmaciIterator.hasNext()) {
            Farmaco f = farmaciIterator.next();
            if (f.getQuantitaFarmacoInt() > 0) {
                empty = false;
                for (Lotto l : f.getListaLotti()) {
                    if(l.getCodiceLotto() != null) {
                        scadenzaLotto = LocalDate.parse(l.getDataScadenza(), formatter);

                        if (scadenzaLotto.isBefore(currentDataPlusTwo)) {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(FarmacoScadenzaControl.class.getResource("FarmacoInScadenza.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            FarmacoScadenzaControl FSControl = loader.getController();
                            FSControl.setPopUp("Il seguente farmaco scade prima dei due mesi! \n\n" + f.getNomeFarmaco() + "\n" + l.getDataScadenza());
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setTitle("Avviso");
                            stage.setScene(scene);
                            stage.showAndWait();

                            mantieni = FSControl.returnMantieni();
                            if (mantieni) {

                                if (l.getQuantitaLotto() >= f.getQuantitaFarmacoInt()) {
                                    ls = new LottoSpedizione(f.getNomeFarmaco(), l.getCodiceLotto(), f.getQuantitaFarmacoInt(), l.getDataProduzione(), l.getDataScadenza(), l.getFKFarmaco());
                                    ordine.add(ls);
                                    farmaciIterator.remove();
                                } else if (l.getQuantitaLotto() > 0) {
                                    ls = new LottoSpedizione(f.getNomeFarmaco(), l.getCodiceLotto(), l.getQuantitaLotto(), l.getDataProduzione(), l.getDataScadenza(), l.getFKFarmaco());
                                    ordine.add(ls);
                                    f.setQuantitaFarmacoInt(f.getQuantitaFarmacoInt() - l.getQuantitaLotto());
                                }
                            }
                        } else {
                            if (l.getQuantitaLotto() >= f.getQuantitaFarmacoInt()) {
                                ls = new LottoSpedizione(f.getNomeFarmaco(), l.getCodiceLotto(), f.getQuantitaFarmacoInt(), l.getDataProduzione(), l.getDataScadenza(), l.getFKFarmaco());
                                ordine.add(ls);
                                farmaciIterator.remove();
                                break;
                            } else if (l.getQuantitaLotto() > 0) {
                                ls = new LottoSpedizione(f.getNomeFarmaco(), l.getCodiceLotto(), l.getQuantitaLotto(), l.getDataProduzione(), l.getDataScadenza(), l.getFKFarmaco());
                                ordine.add(ls);
                                f.setQuantitaFarmacoInt(f.getQuantitaFarmacoInt() - l.getQuantitaLotto());
                            }
                        }
                    }
                }
            }
            else {
                farmaciIterator.remove();
            }
        }

        if(listaFarmaciInVendita.size() > 0){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FarmaciNonDisponibiliControl.class.getResource("FarmaciNonDisponibili.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FarmaciNonDisponibiliControl FNDControl = loader.getController();
            FNDControl.setFarmaciNonDisponibili(listaFarmaciInVendita);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Lista Farmaci non disponibili");
            stage.setScene(scene);
            stage.showAndWait();
        }

        if(ordine.size() > 0){

            dbms.inserisciLottiInSpedizione(IDOrdine, ordine);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
            Parent root = loader.load();
            PopUpControl popControl = loader.getController();
            popControl.setPopUp("Modifica eseguita!");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avviso");
            stage.setScene(scene);
            stage.showAndWait();

            stage = (Stage) ordinaButton.getScene().getWindow();
            stage.close();


        }

        if(empty) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PopUpControl.class.getResource("error.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PopUpControl popControl = loader.getController();
            popControl.setPopUp("Non hai inserito farmaci!");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avviso");
            stage.setScene(scene);
            stage.show();
        }
    }
}
