package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.GlobalData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.util.Duration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Scanner;

public class AggiornaInventarioControl extends GlobalData {
    private String filePath="./src/main/resources/com/fids/Centrale/AggiornaInventario.txt";
    private File file = new File(filePath);
    public void aggiornaInventario() {
        createFile();
        String data="";
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        String currentDay = LocalDate.now().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        currentDay = sdf.format(c.getTime());
        System.err.println(DAY);
        System.err.println(data);
        if(DAY.equals(data)){
            DBMSBoundary dbms = new DBMSBoundary();
            dbms.checkProduzione(DAY);
            System.err.println("Weeeeee");
        }
    }

    public void createFile(){
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
