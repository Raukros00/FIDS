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
import java.time.format.DateTimeFormatter;

public class CaricaConsegnaControl extends GlobalData {
    @FXML private TextArea noteField;
    @FXML private Button indietroButton;
    @FXML private TableView<LottoSpedizione> consegnaTable = new TableView<>();
    @FXML private TableColumn<LottoSpedizione, String> nomeCol;
    @FXML private TableColumn<LottoSpedizione, String> codLottoCol;
    @FXML private TableColumn<LottoSpedizione, Integer> quantitaOrdCol;
    @FXML private TableColumn<LottoSpedizione, Integer> quantitaArrCol;

    DBMSBoundary dbms = new DBMSBoundary();

    LinkedList<Spedizione> listaSpedizioni = new LinkedList<>();
    LinkedList<LottoSpedizione> listaNuoviFarmaci = new LinkedList<>();
    LinkedList<LottoSpedizione> listaLottiSpedizione;
    public void setDatiConsegna(int ID_FARMACIA) {
        LocalDate oggi = LocalDate.now();
        listaSpedizioni = dbms.getConsegne(String.valueOf(oggi), ID_FARMACIA);
        listaLottiSpedizione = listaSpedizioni.getFirst().getListaLottiSpedizione();

        for(LottoSpedizione ls : listaLottiSpedizione)

        nomeCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, String>("nomeFarmaco"));
        codLottoCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, String>("codiceLotto"));
        quantitaOrdCol.setCellValueFactory(new PropertyValueFactory<LottoSpedizione, Integer>("quantita"));

        quantitaArrCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantitaArrCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LottoSpedizione, Integer>>() {
            boolean exist = false;
            int newQ = 0;
            @Override
            public void handle(TableColumn.CellEditEvent<LottoSpedizione, Integer> event) {
                LottoSpedizione newLS = event.getRowValue();

                if (event.getNewValue() > event.getRowValue().getQuantita()) {
                    newQ = event.getRowValue().getQuantita();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PopUpControl.class.getResource("error.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    PopUpControl popControl = loader.getController();
                    popControl.setPopUp("Ha inserito una quantità maggiore!");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Avviso");
                    stage.setScene(scene);
                    stage.show();

                } else {
                    newQ = event.getNewValue();

                    for (LottoSpedizione ls : listaNuoviFarmaci) {
                        if (ls.getCodiceLotto() == newLS.getCodiceLotto())
                            exist = true;
                    }
                    if (!exist)
                        listaNuoviFarmaci.add(new LottoSpedizione(newLS.getIDSpedizione(), newLS.getNomeFarmaco(), newLS.getPrincipioAttivo(), newLS.getCodiceLotto(), newLS.getDataProduzione(), newLS.getDataScadenza(), newQ, newLS.getIDFarmaco()));

                }
            }
        });

        if(listaSpedizioni.size() > 0) {
            stampaSpedizione(listaSpedizioni.getFirst().getListaLottiSpedizione());
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

        LinkedList<LottoSpedizione> listaFarmaciRimanenti = new LinkedList<>();
        Iterator<LottoSpedizione> lottiSIterator = listaLottiSpedizione.iterator();

        for(LottoSpedizione newLS : listaNuoviFarmaci){

            while (lottiSIterator.hasNext()){
                LottoSpedizione ls = lottiSIterator.next();
                if(newLS.getNomeFarmaco().equalsIgnoreCase(ls.getNomeFarmaco())){

                    if(newLS.getQuantita() == ls.getQuantita()){
                        lottiSIterator.remove();
                        break;
                    }
                    else {
                        newLS.setQuantita(ls.getQuantita() - newLS.getQuantita());
                        listaFarmaciRimanenti.add(newLS);
                        lottiSIterator.remove();
                        break;
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

                String segnalazione = "Segnalaziona da: " + NOME_FARMACIA + "\nFarmacista: " + NOMINATIVO + "\nData e ora: " + String.valueOf(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))) + "\n\nLOTTI MANCANTI: ";
                for(LottoSpedizione ls : listaFarmaciRimanenti){
                    segnalazione += "\n" + ls.getNomeFarmaco() + " " + ls.getCodiceLotto() + " x" + ls.getQuantita();
                }
                segnalazione += "\n\nNOTE: " + note;

                dbms.inserisciSegnalazione(listaNuoviFarmaci.getFirst().getIDSpedizione(),ID_UTENTE, segnalazione);

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

        dbms.aggiornaInventarioFarmacia(ID_FARMACIA, listaNuoviFarmaci.getFirst().getIDSpedizione(), listaNuoviFarmaci);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PopUpControl.class.getResource("succesful.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PopUpControl popControl = loader.getController();
        popControl.setPopUp("Caricamento avvenuto!");
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Avviso");
        stage.setScene(scene);
        stage.showAndWait();

        NUM_CONSEGNE--;
        if(NUM_CONSEGNE > 0){
            listaSpedizioni.removeFirst();
            listaNuoviFarmaci.clear();
            setDatiConsegna(ID_FARMACIA);
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