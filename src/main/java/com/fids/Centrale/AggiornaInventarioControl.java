package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.Farmaco;
import Entity.GlobalData;
import Entity.Lotto;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
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
        String currentDay = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if(!DAY.equals(data)){
            DBMSBoundary dbms = new DBMSBoundary();
            ArrayList<Farmaco> daProdurre = dbms.checkProduzione(DAY);
            if(!daProdurre.isEmpty()) {
                ArrayList<Lotto> daInserire = buildLotti(daProdurre);
                dbms.aggiungiLotto(daInserire);
                System.err.println(currentDay != data);
            }
            if(!currentDay.equals(data)){
                try {
                    FileWriter myWriter = new FileWriter(filePath);
                    myWriter.write(currentDay);
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

    public void createFile(){
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public ArrayList<Lotto> buildLotti(ArrayList<Farmaco> daProdurre){
        ArrayList<Lotto> daInserire= new ArrayList<Lotto>();
        for(Farmaco f : daProdurre){
            Lotto l=new Lotto();
            String nome=f.getNomeFarmaco().trim().substring(0, 4)+String.valueOf(f.getIDFarmaco());
            String dataProduzione=String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            LocalTime now= LocalTime.now();
            String oraProduzione= now.format(DateTimeFormatter.ofPattern("HHmm"));
            String codiceLotto=nome+dataProduzione+oraProduzione;
            l.setCodiceLotto(codiceLotto);
            System.err.println(codiceLotto);
            l.setDataScadenza(String.valueOf(LocalDate.now().plusDays(f.getValidita())));
            System.err.println(String.valueOf(LocalDate.now().plusDays(f.getValidita())));
            l.setDataProduzione(String.valueOf(LocalDate.now()));
            l.setQuantitaLotto(f.getQuantitaProduzione());
            l.setFKFarmaco(f.getIDFarmaco());
            daInserire.add(l);
        }
        return daInserire;
    }
}
