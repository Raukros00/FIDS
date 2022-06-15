package DBMSB;

import Entity.Farmacia;
import Entity.Farmaco;
import Entity.Lotto;
import Entity.Utente;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DBMSBoundary {
    private String DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Centrale?user=admin&password=Az-10694@";

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
            String sql = "SELECT IDSede, nomeSede, indirizzoSede, citta, IDSpedizione, COUNT(*) AS 'NUM_CONSEGNE' FROM Sede, Spedizione WHERE Sede.IDSede = Spedizione.FKFarmacia AND Sede.IDSede = ? AND Spedizione.dataConsegna=?;";
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
                System.out.println(farmacia.getNomeSede());
                return farmacia;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LinkedList<Farmaco> getInventarioFarmacia(int idFarmacia) {

        DB_URL = "jdbc:mysql://101.60.191.210:3306/FIDS_Farmacia_" + idFarmacia + "?user=admin&password=Az-10694@";
        LinkedList<Farmaco> listaFarmaci = new LinkedList<Farmaco>();
        Farmaco f;
        Lotto l;
        try {

            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM Farmaco";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet2;
            while (resultSet.next()) {

                f = new Farmaco();
                f.setIDFarmaco(resultSet.getInt("IDFarmaco"));
                f.setNomeFarmaco(resultSet.getString("nomeFarmaco"));
                f.setPrincipioAttivo(resultSet.getString("principioAttivo"));

                sql = "SELECT * FROM Lotto WHERE FKFarmaco =?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, f.getIDFarmaco());
                resultSet2 = preparedStatement.executeQuery();


                while (resultSet2.next()) {
                    l = new Lotto();

                    l.setIDLotto(resultSet2.getString("codiceLotto"));
                    l.setDataScadenza(resultSet2.getString("dataScadenza"));
                    l.setDataProduzione(resultSet2.getString("dataProduzione"));
                    l.setQuantitaLotto(resultSet2.getInt("quantitaLotto"));
                    f.setQuantitaFarmaco(f.getQuantitaFarmaco() + l.getQuantitaLotto());
                    f.inserisciLotto(l);
                }

                listaFarmaci.add(f);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaFarmaci;
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
        }

        return null;
    }
}


