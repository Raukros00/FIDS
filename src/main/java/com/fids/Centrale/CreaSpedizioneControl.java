package com.fids.Centrale;

import DBMSB.DBMSBoundary;
import Entity.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public class CreaSpedizioneControl extends GlobalData {
    private String filePath="./src/main/resources/com/fids/Centrale/CreaSpedizione.txt";
    private File file = new File(filePath);

    LinkedList<Farmaco> farmaciDaBanco = new LinkedList<Farmaco>();

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
        if(!DAY.equals(data)){
            DBMSBoundary dbms = new DBMSBoundary();
            LinkedList<Contratto> listaContratti= dbms.getContrattiFarmacia();

            LocalDate toCompare=LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            LinkedList<Contratto> daSpedire= new LinkedList<>();
            for(Contratto c : listaContratti){
                LocalDate toCompare2=LocalDate.parse(c.getUltimaConsegna()).plusDays(c.getPerioditicita());
                if(toCompare.isAfter(toCompare2) || toCompare.isEqual(toCompare2)){
                    daSpedire.add(c);
                }
            }

            farmaciDaBanco = dbms.getFarmaciDaBanco();
            LinkedList<LottoSpedizione> ordine = new LinkedList<>();


            for(Contratto c : daSpedire){
                ordine.clear();

                for(FarmacoContratto fc : c.getListaFarmaciContratto()) {
                    if (c.getListaFarmaciContratto().size() > 0) {
                        for (Farmaco fb : farmaciDaBanco) {
                            if (fc.getIDFarmaco() == fb.getIDFarmaco()) {
                                for (Lotto l : fb.getListaLotti()) {

                                    if (l.getQuantitaLotto() <= fc.getQuantitaRichiesta()) {
                                        ordine.add(new LottoSpedizione(fb.getNomeFarmaco(), l.getCodiceLotto(), fc.getQuantitaRichiesta(), l.getDataScadenza(), l.getDataScadenza(), l.getFKFarmaco()));
                                        break;
                                    } else if (l.getQuantitaLotto() > 0) {
                                        ordine.add(new LottoSpedizione(fb.getNomeFarmaco(), l.getCodiceLotto(), fc.getQuantitaRichiesta(), l.getDataScadenza(), l.getDataScadenza(), l.getFKFarmaco()));
                                        break;
                                    }

                                }
                            }
                        }
                        int idSpedizione = dbms.insertNewSpedizione(c.getIDSede());
                        dbms.inserisciLottiInSpedizione(idSpedizione, ordine);
                    }
                }
            }



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
