package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.GlobalData;
import Entity.LottoSpedizione;
import Entity.Spedizione;
import com.fids.PopUp.PopUpControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;

public class CaricaConsegnaControl extends GlobalData {
    @FXML private TextArea noteField;
    @FXML private Button indietroButton;
    @FXML private TableView<LottoSpedizione> consegnaTable = new TableView<>();
    @FXML private TableColumn<LottoSpedizione, String> nomeCol;
    @FXML private TableColumn<LottoSpedizione, String> codLottoCol;
    @FXML private TableColumn<LottoSpedizione, Integer> quantitaOrdCol;
    @FXML private TableColumn<LottoSpedizione, Integer> quantitaArrCol;

    DBMSBoundary dbms = new DBMSBoundary();

    LinkedList<Spedizione> listaSpdizioni = new LinkedList<>();
    LinkedList<LottoSpedizione> listaNuoviFarmaci = new LinkedList<>();
    public void setDatiConsegna(int ID_FARMACIA) {

        LocalDate oggi = LocalDate.now();
        listaSpdizioni = dbms.getConsegne(String.valueOf(oggi), ID_FARMACIA);

        for(LottoSpedizione ls : listaSpdizioni.getFirst().getListaLottiSpedizione())
            listaNuoviFarmaci.add(new LottoSpedizione(ls.getIDSpedizione(), ls.getNomeFarmaco(), ls.getPrincipioAttivo(), ls.getCodiceLotto(), ls.getDataProduzione(), ls.getDataScadenza(), ls.getQuantita()));


        nomeCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, String>("nomeFarmaco"));
        codLottoCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, String>("codiceLotto"));
        quantitaOrdCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, Integer>("quantita"));

        quantitaArrCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantitaArrCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LottoSpedizione, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<LottoSpedizione, Integer> event) {
                for(LottoSpedizione ls : listaNuoviFarmaci){
                    if(ls.getNomeFarmaco().equalsIgnoreCase(event.getRowValue().getNomeFarmaco()))
                        ls.setQuantita(event.getNewValue());
                }
            }
        });

        if(listaSpdizioni.size() > 0) {
            stampaSpedizione(listaSpdizioni.getFirst().getListaLottiSpedizione());
        }

    }

    public void stampaSpedizione(LinkedList<LottoSpedizione> spedizione){

        consegnaTable.setItems(FXCollections.observableArrayList(spedizione));
        consegnaTable.setEditable(true);

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

    public void caricaOrdine(ActionEvent event) {

        String note = "";
        boolean edit = false;
        boolean segnala = false;
        if(!noteField.getText().isEmpty()) note = noteField.getText();


        LinkedList<LottoSpedizione> listaLottiSpedizione = listaSpdizioni.getFirst().getListaLottiSpedizione();
        LinkedList<LottoSpedizione> listaFarmaciRimanenti = new LinkedList<>();
        Iterator<LottoSpedizione> lottiSIterator = listaLottiSpedizione.iterator();

        for(LottoSpedizione newLS : listaNuoviFarmaci){
            while (lottiSIterator.hasNext()){
                LottoSpedizione ls = lottiSIterator.next();
                if(newLS.getNomeFarmaco().equalsIgnoreCase(ls.getNomeFarmaco())){

                    if(newLS.getQuantita() == ls.getQuantita()){
                        lottiSIterator.remove();
                    }
                    else if(!edit){
                        ls.setQuantita(ls.getQuantita() - newLS.getQuantita());
                        listaFarmaciRimanenti.add(ls);
                        lottiSIterator.remove();
                        edit = true;
                    }
                }
            }
        }

        if(listaFarmaciRimanenti.size() > 0){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FarmaciNonArrivatiControl.class.getResource("FarmaciNonArrivati.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FarmaciNonArrivatiControl FNAControl = loader.getController();
            FNAControl.setFarmaciNonArrivati(listaFarmaciRimanenti);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avviso");
            stage.setScene(scene);
            stage.showAndWait();

            segnala = FNAControl.getSegnala();

            if(segnala) {
                LocalDateTime now = LocalDateTime.now();
                String segnalazione = "Segnalaziona da: " + NOME_FARMACIA + "\nFarmacista: " + NOMINATIVO + "\nData e ora: " + String.valueOf(now) + "\n\nLOTTI MANCANTI: ";
                for(LottoSpedizione ls : listaFarmaciRimanenti){
                    segnalazione += "\n" + ls.getCodiceLotto() + " " + ls.getQuantita();
                }
                segnalazione += "\n\nNOTE: " + note;

                dbms.inserisciSegnalazione(ID_UTENTE, segnalazione);

            }

            else {
                return;
            }
        }

        else {
            if(note.isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                String segnalazione = "Segnalaziona da: " + NOME_FARMACIA + "\nFarmacista: " + NOMINATIVO + "\nData e ora: " + String.valueOf(now) +  "\n\nNOTE: " + note;
           }
        }

        dbms.aggiornaInventarioFarmacia(ID_FARMACIA, listaNuoviFarmaci.getFirst().getIDSpedizione(),listaNuoviFarmaci);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PopUpControl popControl = loader.getController();
        popControl.setPopUp("Caricamento avvenuto!!");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Avviso");
        stage.setScene(scene);
        stage.showAndWait();

        listaSpdizioni.removeFirst();
        NUM_CONSEGNE--;

        if(listaSpdizioni.size() > 0){
            stampaSpedizione(listaSpdizioni.getFirst().getListaLottiSpedizione());
        }
        else{
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("HomeFarmacia.fxml"));
            root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            HomeFarmaciaControl homeFControl = loader.getController();
            homeFControl.setLabels();
            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Home Farmacia");
            stage.setScene(new Scene(root));
            stage.show();
        }





    }

}
