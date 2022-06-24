package DBMSB;

import Entity.*;
import com.fids.PopUp.PopUpControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

public class DBMSBoundary {
    private String DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";

    private void cadutaConnessione(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PopUpControl.class.getResource("error.fxml"));
            Parent root = loader.load();
            PopUpControl popControl = loader.getController();
            popControl.setPopUp("Connessione scaduta!");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Avviso");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Utente verificaCredenziali(String username, String password) {

        Utente user = new Utente();

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM Utente WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                sql = "SELECT * FROM Utente WHERE username =? AND password =?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {

                    user.setIDUtente(Integer.parseInt(resultSet.getString("IDUtente")));
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setNome(resultSet.getString("nome"));
                    user.setCognome(resultSet.getString("cognome"));
                    user.setEmail(resultSet.getString("email"));
                    user.setRuolo(Integer.parseInt(resultSet.getString("ruolo")));
                    user.setIDSede(Integer.parseInt(resultSet.getString("FKSede")));
                    return user;

                } else {
                    user.setIDUtente(-1);
                    return user;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }
        user.setIDUtente(-2);
        return user;
    }

    public Farmacia richiediInfoHome(int idFarmacia) {

        Farmacia farmacia = new Farmacia();
        LocalDate data = LocalDate.now();

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "SELECT IDSede, nomeSede, indirizzoSede, citta, IDSpedizione, distanza, COUNT(*) AS 'NUM_CONSEGNE' FROM Sede, Spedizione WHERE Sede.IDSede = Spedizione.FKFarmacia AND Sede.IDSede = ? AND Spedizione.dataConsegna=? AND Spedizione.statoSpedizione=1;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idFarmacia);
            preparedStatement.setString(2, String.valueOf(data));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                farmacia.setIDFarmacia(resultSet.getInt("IDSede"));
                farmacia.setNomeSede(resultSet.getString("nomeSede"));
                farmacia.setIndirizzoSede(resultSet.getString("indirizzoSede"));
                farmacia.setCitta(resultSet.getString("citta"));
                farmacia.setNumConsegne(Integer.parseInt(resultSet.getString("NUM_CONSEGNE")));
                farmacia.setDistanza(resultSet.getDouble("distanza"));
                return farmacia;
            }
        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }

        return null;
    }

    public LinkedList<Farmaco> getInventarioFarmacia(int idFarmacia) {

        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Farmacia_" + idFarmacia + "?user=admin&password=Az-10694@";
        LinkedList<Farmaco> listaFarmaci = new LinkedList<Farmaco>();
        Farmaco f = new Farmaco();
        Lotto l = new Lotto();
        try {

            Connection conn = DriverManager.getConnection(DB_URL);

            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM Farmaco, Lotto WHERE Farmaco.IDFarmaco = Lotto.FKFarmaco ORDER BY IDFarmaco";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            int IDFarmaco = -1;

            while (resultSet.next()) {

                if(resultSet.getInt("IDFarmaco") != IDFarmaco && IDFarmaco != -1) {
                    listaFarmaci.add(f);
                    f = new Farmaco();
                }

                if(resultSet.getInt("IDFarmaco") != IDFarmaco) {

                    f.setIDFarmaco(resultSet.getInt("IDFarmaco"));
                    f.setNomeFarmaco(resultSet.getString("nomeFarmaco"));
                    f.setPrincipioAttivo(resultSet.getString("principioAttivo"));

                    IDFarmaco = resultSet.getInt("IDFarmaco");
                    l = new Lotto();
                    l.setCodiceLotto(resultSet.getString("codiceLotto"));
                    l.setDataScadenza(resultSet.getString("dataScadenza"));
                    l.setDataProduzione(resultSet.getString("dataProduzione"));
                    l.setQuantitaLotto(resultSet.getInt("quantitaLotto"));
                    f.setQuantitaFarmaco(String.valueOf(Integer.valueOf(resultSet.getInt("quantitaLotto")) + Integer.valueOf(f.getQuantitaFarmaco())));
                    f.inserisciLotto(l);

                }

                else {
                    l = new Lotto();
                    l.setCodiceLotto(resultSet.getString("codiceLotto"));
                    l.setDataScadenza(resultSet.getString("dataScadenza"));
                    l.setDataProduzione(resultSet.getString("dataProduzione"));
                    l.setQuantitaLotto(resultSet.getInt("quantitaLotto"));
                    f.setQuantitaFarmaco(String.valueOf(Integer.valueOf(resultSet.getInt("quantitaLotto")) + Integer.valueOf(f.getQuantitaFarmaco())));
                    f.inserisciLotto(l);

                }
            }
            listaFarmaci.add(f);

        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }

        return listaFarmaci;
    }

    public void updateInventario(LinkedList<Farmaco> venditaFarmaci, LinkedList<Farmaco> listaFarmaci, int idFarmacia) {
        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Farmacia_" + idFarmacia + "?user=admin&password=Az-10694@";

        try {

            for(Farmaco f: venditaFarmaci){
                for(Farmaco lf: listaFarmaci){
                    for(Lotto ll : lf.getListaLotti()){
                        if(ll.getCodiceLotto().equalsIgnoreCase(f.getLottoS())){
                                f.setQuantitaFarmacoInt(ll.getQuantitaLotto() - f.getQuantitaFarmacoInt());
                                Connection conn = DriverManager.getConnection(DB_URL);
                                Statement stat = conn.createStatement();
                                String sql = "UPDATE Lotto SET quantitaLotto =? WHERE codiceLotto =?";
                                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setInt(1, f.getQuantitaFarmacoInt());
                                preparedStatement.setString(2, f.getLottoS());
                                int row = preparedStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            cadutaConnessione();
        }
    }

    public LinkedList<Spedizione> getListaSpedizioni(int idFarmacia) {

        LinkedList<Spedizione> listaSpedizioni = new LinkedList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM Spedizione, Lotto_Spedizione WHERE Spedizione.IDSpedizione = Lotto_Spedizione.FKSpedizione AND Spedizione.FKFarmacia=? ORDER BY IDSpedizione";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idFarmacia);
            ResultSet resultSet = preparedStatement.executeQuery();

            Spedizione s = new Spedizione();
            LottoSpedizione ls = new LottoSpedizione();
            int IDSpedizione = -1;
            boolean flag = false;

            while (resultSet.next()) {
                flag = true;
                if(resultSet.getInt("IDSpedizione") != IDSpedizione && IDSpedizione != -1 ){
                    listaSpedizioni.add(s);
                    s = new Spedizione();
                }

                if(resultSet.getInt("IDSpedizione") != IDSpedizione){

                    s.setIDSpedizione(resultSet.getInt("IDSpedizione"));
                    s.setDataConsegna(resultSet.getString("dataConsegna"));
                    s.setStatoConsegna(resultSet.getInt("statoSpedizione"));

                    IDSpedizione = resultSet.getInt("IDSpedizione");

                    ls = new LottoSpedizione();
                    ls.setIDSpedizione(IDSpedizione);
                    ls.setNomeFarmaco(resultSet.getString("nomeFarmaco"));
                    ls.setCodiceLotto(resultSet.getString("FKLotto"));
                    ls.setQuantita(resultSet.getInt("quantita"));
                    s.addLotto(ls);
                }

                else{

                    ls = new LottoSpedizione();
                    ls.setIDSpedizione(IDSpedizione);
                    ls.setNomeFarmaco(resultSet.getString("nomeFarmaco"));
                    ls.setCodiceLotto(resultSet.getString("FKLotto"));
                    ls.setQuantita(resultSet.getInt("quantita"));
                    s.addLotto(ls);

                }
            }
            if(flag) listaSpedizioni.add(s);

        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }

        return listaSpedizioni;

    }

    public int creaSpedizione(int ID_FARMACIA, Double DISTANZA){

        String dataConsegna = LocalDate.now().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();

        if(0.0 <= DISTANZA && DISTANZA < 5.0) c.add(Calendar.DATE, 3);
        if(6.0 <= DISTANZA && DISTANZA < 9.0) c.add(Calendar.DATE, 4);
        if(9.0 <= DISTANZA && DISTANZA < 50.0) c.add(Calendar.DATE, 5);

        if(50 < DISTANZA) c.add(Calendar.DATE, 7);

        dataConsegna = sdf.format(c.getTime());

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "INSERT INTO Spedizione (dataConsegna, FKFarmacia) VALUES (?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(dataConsegna));
            preparedStatement.setInt(2, ID_FARMACIA);
            int row = preparedStatement.executeUpdate();


            sql = "SELECT MAX(IDSpedizione) AS 'IDSPEDIZIONE' FROM Spedizione";
            preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                return resultSet.getInt("IDSPEDIZIONE");
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return -1;
    }

    public void inserisciLottiInSpedizione(int idSpedizione, LinkedList<LottoSpedizione> ordine) throws SQLException {

        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stat = conn.createStatement();
        String sql;

        for(LottoSpedizione o : ordine){

            sql = "INSERT INTO Lotto_Spedizione (FKLotto, FKSpedizione, quantita, nomeFarmaco) VALUES(?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, o.getCodiceLotto());
            preparedStatement.setInt(2, idSpedizione);
            preparedStatement.setInt(3, o.getQuantita());
            preparedStatement.setString(4, o.getNomeFarmaco());
            int row = preparedStatement.executeUpdate();

            sql = "UPDATE Lotto SET quantitaLotto = quantitaLotto - ? WHERE codiceLotto = ?";
             preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, o.getQuantita());
            preparedStatement.setString(2, o.getCodiceLotto());
            row = preparedStatement.executeUpdate();
        }

    }

    public void modificaSpedizione(int IDSpedizione, LinkedList<LottoSpedizione> ordine ) throws  SQLException {

        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stat = conn.createStatement();
        String sql;

        sql = "DELETE FROM Lotto_Spedizione WHERE FKSpedizione=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, IDSpedizione);
        int row = preparedStatement.executeUpdate();
        inserisciLottiInSpedizione(IDSpedizione,ordine);

    }

    public LinkedList<Spedizione> getConsegne(String currentDate, int ID_FARMACIA) {

        LinkedList<Spedizione> listaSpedizioni = new LinkedList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "SELECT IDSpedizione, dataConsegna, dataScadenza, dataProduzione, Farmaco.nomeFarmaco, principioAttivo, FKLotto, quantita FROM Lotto_Spedizione, Lotto, Farmaco, Spedizione WHERE Spedizione.IDSpedizione = Lotto_Spedizione.FKSpedizione AND Lotto_Spedizione.FKLotto = Lotto.codiceLotto AND Lotto.FKFarmaco = Farmaco.IDFarmaco AND FKFarmacia =? AND dataConsegna=? AND Spedizione.statoSpedizione=1";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, ID_FARMACIA);
            preparedStatement.setString(2, currentDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            Spedizione s = new Spedizione();
            LottoSpedizione ls = new LottoSpedizione();
            boolean flag = false;
            int IDSpedizione = -1;


            while (resultSet.next()) {
                flag = true;
                if(resultSet.getInt("IDSpedizione") != IDSpedizione && IDSpedizione != -1){
                    listaSpedizioni.add(s);
                    s = new Spedizione();
                }

                if(resultSet.getInt("IDSpedizione") != IDSpedizione){

                    s.setIDSpedizione(resultSet.getInt("IDSpedizione"));
                    s.setDataConsegna(resultSet.getString("dataConsegna"));

                    IDSpedizione = resultSet.getInt("IDSpedizione");

                    ls = new LottoSpedizione();
                    ls.setIDSpedizione(IDSpedizione);
                    ls.setCodiceLotto(resultSet.getString("FKLotto"));
                    ls.setNomeFarmaco(resultSet.getString("nomeFarmaco"));
                    ls.setPrincipioAttivo(resultSet.getString("principioAttivo"));
                    ls.setQuantita(resultSet.getInt("quantita"));
                    ls.setDataProduzione(resultSet.getString("dataProduzione"));
                    ls.setDataScadenza(resultSet.getString("dataScadenza"));
                    s.addLotto(ls);

                }

                else {

                    ls = new LottoSpedizione();
                    ls.setIDSpedizione(IDSpedizione);
                    ls.setNomeFarmaco(resultSet.getString("nomeFarmaco"));
                    ls.setPrincipioAttivo(resultSet.getString("principioAttivo"));
                    ls.setCodiceLotto(resultSet.getString("FKLotto"));
                    ls.setQuantita(resultSet.getInt("quantita"));
                    ls.setDataProduzione(resultSet.getString("dataProduzione"));
                    ls.setDataScadenza(resultSet.getString("dataScadenza"));
                    s.addLotto(ls);

                }
            }
            if(flag) listaSpedizioni.add(s);
        }

        catch(Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }

        return listaSpedizioni;
    }

    public void inserisciSegnalazione(int IDUtente, String segnalazione) {

        try {

            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql;
            sql = "INSERT INTO Segnalazione (FKUtente, Descrizione) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            System.out.println(IDUtente + " ha scritto " + segnalazione);
            preparedStatement.setInt(1, IDUtente);
            preparedStatement.setString(2, segnalazione);
            int row = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }
    }

    public void aggiornaInventarioFarmacia(int ID_FARMACIA, int IDSpedizione,LinkedList<LottoSpedizione> listaCarico) {

        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Farmacia_" + ID_FARMACIA + "?user=admin&password=Az-10694@";

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql;
            int row;
            int FKFarmaco = -1;

            for(LottoSpedizione ls : listaCarico) {

                sql = "SELECT nomeFarmaco FROM Farmaco WHERE nomeFarmaco=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, ls.getNomeFarmaco());
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    System.err.println("Exist");
                }
                else{
                    sql = "INSERT INTO Farmaco (nomeFarmaco, principioAttivo) VALUES (?,?)";
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, ls.getNomeFarmaco());
                    preparedStatement.setString(2, ls.getPrincipioAttivo());
                    row = preparedStatement.executeUpdate();
                }

                sql = "SELECT IDFarmaco FROM Farmaco WHERE nomeFarmaco=?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, ls.getNomeFarmaco());
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                    FKFarmaco = resultSet.getInt("IDFarmaco");

                sql = "INSERT INTO Lotto (codiceLotto, dataScadenza, dataProduzione, quantitaLotto, FKFarmaco) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, ls.getCodiceLotto());
                preparedStatement.setString(2, ls.getDataScadenza());
                preparedStatement.setString(3, ls.getDataProduzione());
                preparedStatement.setInt(4, ls.getQuantita());
                preparedStatement.setInt(5, FKFarmaco);
                row = preparedStatement.executeUpdate();

            }


            DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
            sql = "UPDATE Spedizione SET statoSpedizione=2 WHERE IDSpedizione=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, IDSpedizione);
            row = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }

    }

    public Contratto getContratto(int ID_FARMACIA) {

        Contratto contratto = new Contratto();

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "SELECT perioditicita, FKFarmaco, quantitaRichiesta FROM Contratto, Farmaco_Contratto WHERE Contratto.IDContratto = Farmaco_Contratto.FKContratto AND Contratto.FKFarmacia=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, ID_FARMACIA);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean done = false;
            while(resultSet.next()){

                if(!done) {
                    contratto.setPerioditicita(resultSet.getInt("periodicita"));
                    done = true;
                }
                contratto.addListaFarmaciContratto(new FarmacoContratto(resultSet.getInt("FKFarmaco"), resultSet.getInt("quantitaRichiesta")));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }
        return contratto;
    }



    /*
    -------------------------------------------------------
    |                                                     |
    |                   QUERY CENTRALE                    |
    |                                                     |
    -------------------------------------------------------
    */
    public String richiediNumSegnalazioni() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "SELECT COUNT(IDSegnalazione) AS 'NUM_SEGNALAZIONI' FROM Segnalazione;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("NUM_SEGNALAZIONI");
            }
        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }

        return null;
    }

    public LinkedList<Farmaco> getInventarioCentrale() {

        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";
        LinkedList<Farmaco> listaFarmaci = new LinkedList<Farmaco>();
        Farmaco f = new Farmaco();
        Lotto l = new Lotto();
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "SELECT *, DATE_FORMAT(dataScadenza, '%d/%m/%Y') AS Data_Scadenza FROM Farmaco, Lotto WHERE Farmaco.IDFarmaco = Lotto.FKFarmaco ORDER BY nomeFarmaco";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            int IDFarmaco = -1;

            while (resultSet.next()) {

                if(resultSet.getInt("IDFarmaco") != IDFarmaco && IDFarmaco != -1) {
                    listaFarmaci.add(f);
                    f = new Farmaco();
                }

                if(resultSet.getInt("IDFarmaco") != IDFarmaco) {

                    f.setIDFarmaco(resultSet.getInt("IDFarmaco"));
                    f.setNomeFarmaco(resultSet.getString("nomeFarmaco"));
                    f.setPrincipioAttivo(resultSet.getString("principioAttivo"));
                    f.setPeriodicitaProduzione(resultSet.getInt("periodicitaProduzione"));
                    f.setQuantitaProduzione(resultSet.getInt("quantitaProduzione"));

                    IDFarmaco = resultSet.getInt("IDFarmaco");
                    l = new Lotto();
                    l.setCodiceLotto(resultSet.getString("codiceLotto"));
                    l.setDataScadenza(resultSet.getString("dataScadenza"));
                    l.setDataProduzione(resultSet.getString("dataProduzione"));
                    l.setQuantitaLotto(resultSet.getInt("quantitaLotto"));
                    f.setQuantitaFarmaco(String.valueOf(resultSet.getInt("quantitaLotto") + Integer.valueOf(f.getQuantitaFarmaco())));
                    f.inserisciLotto(l);

                } else {
                    l = new Lotto();
                    l.setCodiceLotto(resultSet.getString("codiceLotto"));
                    l.setDataScadenza(resultSet.getString("dataScadenza"));
                    l.setDataProduzione(resultSet.getString("dataProduzione"));
                    l.setQuantitaLotto(resultSet.getInt("quantitaLotto"));
                    f.setQuantitaFarmaco(String.valueOf(resultSet.getInt("quantitaLotto") + Integer.valueOf(f.getQuantitaFarmaco())));
                    f.inserisciLotto(l);

                }
            }
            listaFarmaci.add(f);

        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }

        return listaFarmaci;
    }

    public ResultSet getUtenti(){
        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "select IDSede, nomeSede from Sede;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }
        return null;
    }

    public boolean verificaEmail(String email){
        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "select email from Utente";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if(email.equalsIgnoreCase(resultSet.getString("email"))) {
                    return true;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }
        return false;
    }

    public ArrayList getUsernames(){
        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";
        ArrayList<String> usernames = new ArrayList<String>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "select username from Utente";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                usernames.add(resultSet.getString("username"));
            }
        }catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }
        return(usernames);
    }
    public void insertUtente(String nome, String cognome, String dataNascita, String email, String username, String ruolo, String IDsede){
        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";
        int ruoloVero=0;
        if(ruolo=="Farmacista"){
            ruoloVero=1;
        } else if (ruolo=="Impiegato") {
            ruoloVero=2;
        } else if (ruolo=="Corriere") {
            ruoloVero=3;
        }
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            if(ruoloVero==1) {
                String sql = "INSERT INTO Utente(nome, cognome, dataNascita, email, username, password, ruolo, FKSede) VALUES( ? , ? , ? , ? , ? ,\"0000\",\"1\", ? );";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, cognome);
                preparedStatement.setString(3, dataNascita);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, username);
                preparedStatement.setString(6, IDsede);
                int row = preparedStatement.executeUpdate();
                // rows affected
            }else{
                String sql = "INSERT INTO Utente(nome, cognome, dataNascita, email, username, password, ruolo, FKSede) VALUES( ? , ? , ? , ? , ? , \"0000\", ? , null);";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, cognome);
                preparedStatement.setString(3, dataNascita);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, username);
                preparedStatement.setString(6, String.valueOf(ruoloVero));
                int row = preparedStatement.executeUpdate();
                // rows affected
            }

        }catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }
    }

    public boolean modificaProduzione(int periodo,int quantita, int id){
        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";
        int row;
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "UPDATE Farmaco SET periodicitaProduzione=?, quantitaProduzione=? WHERE IDFarmaco=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt   (1, periodo);
            preparedStatement.setInt   (2, quantita);
            preparedStatement.setInt   (3, id);
            row =preparedStatement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
            return false;
        }
        if(row==1){
            return true;
        }else{
            return false;
        }
    }

    public ResultSet getFarmaco(int IDFarmaco){
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "select periodicitaProduzione, quantitaProduzione from Farmaco where IDFarmaco=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt   (1, IDFarmaco);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }
        return null;
    }

    public ResultSet getPassword(){
        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";
        try{

            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;

        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }
        return null;
    }

    /*
    -------------------------------------------------------
    |                                                     |
    |                   QUERY CORRIERE                    |
    |                                                     |
    -------------------------------------------------------
    */
    public String richiediNumSpedizioni() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "SELECT COUNT(IDSpedizione) AS 'NUM_SPEDIZIONI' FROM Spedizione;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("NUM_SPEDIZIONI");
            }
        } catch (Exception e) {
            e.printStackTrace();
            cadutaConnessione();
        }

        return null;
    }





}