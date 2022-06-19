package com.fids.Farmacia;

import DBMSB.DBMSBoundary;
import Entity.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.sql.ResultSet;

public class ModificaPasswordControl {

    @FXML
    public PasswordField vecchiaPasswordField;

    @FXML
    public PasswordField nuovaPasswordField;

    @FXML
    public PasswordField confermaPasswordField;

    @FXML
    private Button aggiornaPasswordButton;

    private DBMSBoundary dbms = new DBMSBoundary();

    private Utente utente;


    public void AggiornaPasswordButton(){

        String vecchiaPassword = utente.getPassword();
        String nuovaPassword = "";
        String confermaPassword = "";

        if (vecchiaPasswordField.getText() == vecchiaPassword) {
            System.err.println("Ciao king");
        } else {
            System.err.println("Password vecchia errata!");
        }

    }
}
