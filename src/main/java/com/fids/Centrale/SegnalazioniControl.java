package com.fids.Centrale;

import Entity.Segnalazione;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.paint.*;
import javafx.scene.text.*;

import javafx.scene.layout.*;
import javafx.scene.shape.*;
import DBMSB.DBMSBoundary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SegnalazioniControl {

    @FXML
    private Button avantiButton;
    @FXML
    private TextFlow segnalazioniField;
    private ArrayList<Segnalazione> listaSegnalazioni;
    DBMSBoundary dbms=new DBMSBoundary();
        // launch the application
        public void setSegnalazioni()
        {
            listaSegnalazioni=dbms.getSegnalazioni();
            String segnalazione=listaSegnalazioni.get(0).getDescrizione();


            try {

                // create TextFlow


                // create text
                Text text_1 = new Text(segnalazione);

                // set the text color
                text_1.setFill(Color.RED);

                // set font of the text
                text_1.setFont(Font.font("Verdana", 25));

                // create text
                Text text_2 = new Text("The computer science portal for geeks");

                // set the text color
                text_2.setFill(Color.BLUE);

                // set font of the text
                text_2.setFont(Font.font("Helvetica", FontPosture.ITALIC, 15));

                // add text to textflow
                segnalazioniField.getChildren().add(text_1);
                segnalazioniField.getChildren().add(text_2);

            }

            catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }







