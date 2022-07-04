package com.fids;

import DBMSB.DBMSBoundary;
import Entity.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class SystemControl extends GlobalData{
    private String filePathInventario="./AggiornaInventario.txt";
    private File fileInventario = new File(filePathInventario);
    private String filePathSpedizione="./CreaSpedizione.txt";
    private File fileSpedizione = new File(filePathSpedizione);
    LinkedList<Farmaco> farmaciDaBanco = new LinkedList<Farmaco>();
    public void aggiornaInventario() {
        createFileInventario();
        String data="";
        try {
            Scanner myReader = new Scanner(fileInventario);
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
                    FileWriter myWriter = new FileWriter(fileInventario);
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

    public void createFileInventario(){
        try {
            if (fileInventario.createNewFile()) {
                System.out.println("File created: " + fileInventario.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void createFileSpedizione(){
        try {
            if (fileSpedizione.createNewFile()) {
                System.out.println("File created: " + fileSpedizione.getName());
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

    public void creaSpedizione(){
        createFileSpedizione();
        String data="";
        try {
            Scanner myReader = new Scanner(fileSpedizione);
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
                if (c.getListaFarmaciContratto().size() > 0) {
                    for(FarmacoContratto fc : c.getListaFarmaciContratto()) {
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
                    }
                int idSpedizione = dbms.insertNewSpedizione(c.getIDSede());
                dbms.inserisciLottiInSpedizione(idSpedizione, ordine);
                }
            }



            String currentDay = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if(!currentDay.equals(data)){
                try {
                    FileWriter myWriter = new FileWriter(filePathSpedizione);
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
}
