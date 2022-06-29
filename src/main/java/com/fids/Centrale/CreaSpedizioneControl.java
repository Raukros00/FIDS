package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.Contratto;
import Entity.Farmaco;
import Entity.GlobalData;
import Entity.Lotto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class CreaSpedizioneControl extends GlobalData {
    private String filePath="./src/main/resources/com/fids/Centrale/CreaSpedizione.txt";
    private File file = new File(filePath);

    public void creaSpedizione(){
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
        if(DAY.equals(data)){
            DBMSBoundary dbms = new DBMSBoundary();
            ArrayList<Contratto> listaContratti= dbms.getContrattiFarmacia();
            String toCompare=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            ArrayList<Contratto> daSpedire= new ArrayList<>();
            for(Contratto c : listaContratti){
                String toCompare2=String.valueOf(LocalDate.parse(c.getUltimaConsegna()).plusDays(c.getPerioditicita()));
                if(toCompare.equals(toCompare2)){
                    daSpedire.add(c);
                }
            }
            for(Contratto c : daSpedire)
                System.out.println(c.getIDContratto());

            //daSpedire è un array con tutti i contratti che in giornata deve uscire, crea la spedizione tra questo commento


            //e tra questo
            String currentDay = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
}
