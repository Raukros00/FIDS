package com.fids.Centrale;

import Entity.Farmacia;
import Entity.LottoSpedizione;
import Entity.Spedizione;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
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
import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;

public class SpedizioniFarmaciaControl extends ListaSediSpedizioniControl{
    @FXML
    private TableView<Spedizione> listaSpedizioniInArrivoTable = new TableView<>();

    @FXML private TableColumn<Spedizione, Integer> IDspedizioneCol;
    @FXML private TableColumn<Spedizione, String> nomeFarmaciaCol;
    @FXML private TableColumn<Spedizione, String> dataConsegnaCol;
    @FXML private TableColumn<Spedizione, Spedizione> visualizzaCol;

    @FXML private TableView<Spedizione> listaSpedizioniConsegnateTable = new TableView<>();

    @FXML private TableColumn<Spedizione, Integer> IDspedizioneCol1;
    @FXML private TableColumn<Spedizione, String> nomeFarmaciaCol1;
    @FXML private TableColumn<Spedizione, String> dataConsegnaCol1;
    @FXML private TableColumn<Spedizione, Spedizione> visualizzaCol1;
    @FXML public Button indietroButton;
    LinkedList<Spedizione> listaSpedizioni = new LinkedList<>();

    public void setDatiOrdini(){
        System.out.println(listaSpedizioniSedi.size());
        for(Farmacia f: listaSpedizioniSedi){
            if(f.getIDFarmacia()==ID_FARMACIA)
                listaSpedizioni=f.getListaSpedizioni();
        }

        stampaSpedizioni(listaSpedizioni);
    }

    private void stampaSpedizioni(LinkedList<Spedizione> listaSpedizioni){
        IDspedizioneCol.setCellValueFactory(new PropertyValueFactory<Spedizione, Integer>("IDSpedizione"));
        nomeFarmaciaCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("nomeFarmacia"));
        dataConsegnaCol.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("dataConsegna"));

        visualizzaCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        visualizzaCol.setCellFactory(param -> new TableCell<Spedizione, Spedizione>() {
            private final Button visualizzaButton = new Button("Visualizza");

            @Override
            protected void updateItem(Spedizione s, boolean empty) {
                super.updateItem(s, empty);

                if (s == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(visualizzaButton);

                /*visualizzaButton.setOnAction(event -> {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(VisualizzaOrdineControl.class.getResource("visualizzaSpedizione.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    VisualizzaOrdineControl visualizzaOrdineFControl = loader.getController();

                    LinkedList<LottoSpedizione> fs = s.getListaLottiSpedizione();
                    System.err.println("Size: " + fs.size());
                    visualizzaOrdineFControl.setDatiSpedizione(fs);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Visualizza Spedizione");
                    stage.setScene(scene);
                    stage.show();
                });*/
            }
        });

        /*************************************
         *
         *         Tabella Archiviati
         *
         **************************************/

        IDspedizioneCol1.setCellValueFactory(new PropertyValueFactory<Spedizione, Integer>("IDSpedizione"));
        nomeFarmaciaCol1.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("nomeFarmacia"));
        dataConsegnaCol1.setCellValueFactory(new PropertyValueFactory<Spedizione, String>("dataConsegna"));

        visualizzaCol1.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        visualizzaCol1.setCellFactory(param -> new TableCell<Spedizione, Spedizione>() {
            private final Button visualizzaButton1 = new Button("Visualizza");

            @Override
            protected void updateItem(Spedizione s, boolean empty) {
                super.updateItem(s, empty);

                if (s == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(visualizzaButton1);

                /*visualizzaButton.setOnAction(event -> {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(VisualizzaOrdineControl.class.getResource("visualizzaSpedizione.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    VisualizzaOrdineControl visualizzaOrdineFControl = loader.getController();

                    LinkedList<LottoSpedizione> fs = s.getListaLottiSpedizione();
                    System.err.println("Size: " + fs.size());
                    visualizzaOrdineFControl.setDatiSpedizione(fs);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Visualizza Spedizione");
                    stage.setScene(scene);
                    stage.show();
                });*/
            }
        });

        LinkedList<Spedizione> inAttesa = new LinkedList<>();
        LinkedList<Spedizione> listaConsegnati = new LinkedList<>();

        for(Spedizione ls : listaSpedizioni){
            if(ls.getStatoConsegna() == 0)
                inAttesa.add(ls);
            else listaConsegnati.add(ls);
        }

        listaSpedizioniInArrivoTable.setItems(FXCollections.observableArrayList(inAttesa));
        listaSpedizioniConsegnateTable.setItems(FXCollections.observableArrayList(listaConsegnati));
    }


    public void listaSedi(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ListaSediSpedizioni.fxml"));
        Parent root = loader.load();
        ListaSediSpedizioniControl listaSediSpedizioniControl= loader.getController();
        listaSediSpedizioniControl.setDatiSediSpedizioni();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Lista delle farmacie");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
