package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.LottoSpedizione;
import Entity.Spedizione;
import com.fids.PopUp.PopUpControl;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;

import static Entity.GlobalData.ID_FARMACIA;

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
    LinkedList<Spedizione> listaSpedizioni;

    public void setDatiOrdini(int ID_FARMACIA){
        listaSpedizioni = dbms.getListaSpedizioniFarmacia(ID_FARMACIA);
        stampaSpedizioni(listaSpedizioni);
    }

    public void stampaSpedizioni(LinkedList<Spedizione> listaSpedizioni){

        spedizioneCol.setCellValueFactory(new PropertyValueFactory<Spedizione, Integer>("IDSpedizione"));
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

                visualizzaButton.setOnAction(event -> {
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
                });
            }
        });

        modificaCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        modificaCol.setCellFactory(param -> new TableCell<Spedizione, Spedizione>() {
            private final Button modificaButton = new Button("Modifica");

            @Override
            protected void updateItem(Spedizione s, boolean empty) {
                super.updateItem(s, empty);

                if (s == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(modificaButton);

                modificaButton.setOnAction(event -> {
                    LocalDate oggi = LocalDate.now();
                    LocalDate dataConsegna;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                    LocalDate currentDataPlusTwo = oggi.plusDays(2);
                    dataConsegna = LocalDate.parse(s.getDataConsegna(), formatter);

                    if(!currentDataPlusTwo.isAfter(dataConsegna)){

                        LinkedList<LottoSpedizione> fs = s.getListaLottiSpedizione();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(ModificaOrdineControl.class.getResource("ModificaOrdine.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ModificaOrdineControl MOControl = loader.getController();
                        MOControl.setModificaOrdine(fs, s.getIDSpedizione());
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Modifica Ordine");
                        stage.setScene(scene);
                        stage.showAndWait();

                        setDatiOrdini(ID_FARMACIA);
                    }
                    else{
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(PopUpControl.class.getResource("error.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        PopUpControl popControl = loader.getController();
                        popControl.setPopUp("Questo ordine è stato già spedito!");
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Avviso");
                        stage.setScene(scene);
                        stage.show();
                    }

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
            protected void updateItem(Spedizione s, boolean empty) {
                super.updateItem(s, empty);

                if (s == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(visualizzaArcButton);

                visualizzaArcButton.setOnAction(event -> {
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
                });
            }
        });

        LinkedList<Spedizione> inAttesa = new LinkedList<>();
        LinkedList<Spedizione> listaArchiviati = new LinkedList<>();

        for(Spedizione ls : listaSpedizioni){
            if(ls.getStatoConsegna() == 0)
                inAttesa.add(ls);
            else if(ls.getStatoConsegna() == 1)
                listaArchiviati.add(ls);
        }

        ordiniInArrivoTable.setItems(FXCollections.observableArrayList(inAttesa));
        ordiniArchiviatiTable.setItems(FXCollections.observableArrayList(listaArchiviati));



    }

    public void creaOrdine(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AggiungiOrdineControl.class.getResource("AggiungiOrdine.fxml"));
        Parent root = loader.load();
        AggiungiOrdineControl addOrdfControl = loader.getController();
        addOrdfControl.setDatiOrdine();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Crea ordine");
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
