package Entity;

public class Utente {

    private int IDUtente;
    private String username;
    private String password;
    private String nome;
    private String cognome;

    private String dataNascita;
    private String email;
    private int ruolo;
    private int IDSede;
    
    public Utente(int IDUtente, String username, String password, String nome, String cognome, String dataNascita, String email, int ruolo) {
        setIDUtente(IDUtente);
        setUsername(username);
        setPassword(password);
        setNome(nome);
        setCognome(cognome);
        setDataNascita(dataNascita);
        setEmail(email);
        setRuolo(ruolo);
    }

    public Utente(int IDUtente, String username, String password, String nome, String cognome, int ruolo) {
        setIDUtente(IDUtente);
        setUsername(username);
        setPassword(password);
        setNome(nome);
        setCognome(cognome);
        setRuolo(ruolo);
    }

    public Utente(String nome, String cognome, int IDUtente, String email, String password, int ruolo, int IDSede){
        setNome(nome);
        setCognome(cognome);
        setIDUtente(IDUtente);
        setEmail(email);
        setPassword(password);
        setRuolo(ruolo);
        setIDSede(IDSede);
    }


    public Utente() {}
    public void setIDUtente(int IDUtente) { this.IDUtente = IDUtente; }
    public int getIDUtente() { return IDUtente; }
    
    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }
    
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
    
    public void setNome(String nome) { this.nome = nome; }
    public String getNome() { return nome; }
    
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getCognome() { return cognome; }

    public void setDataNascita(String dataNascita) { this.dataNascita = dataNascita; }
    public String getDataNascita() { return dataNascita; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }
    
    public void setRuolo(int ruolo) { this.ruolo = ruolo; }
    public int getRuolo() { return ruolo; }

    public void setIDSede(int IDSede) { this.IDSede = IDSede; }
    public int getIDSede() { return IDSede; }
}
