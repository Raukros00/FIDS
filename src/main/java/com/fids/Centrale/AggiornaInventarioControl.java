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
        String currentDay = LocalDate.now().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        currentDay = sdf.format(c.getTime());
        System.err.println(DAY);
        System.err.println(data);
        if(DAY.equals(data)){
            DBMSBoundary dbms = new DBMSBoundary();
            ArrayList<Farmaco> daProdurre = dbms.checkProduzione(DAY);
            ArrayList<Lotto> daInserire = buildLotti(daProdurre);
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

    public ArrayList<Lotto> buildLotti(ArrayList<Farmaco> daProdurre){
        for(Farmaco f : daProdurre){
            Lotto l=new Lotto();
            String nome=f.getNomeFarmaco().trim().substring(0, 4);
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
            System.out.println(l);
        }
        return null;
    }
}
