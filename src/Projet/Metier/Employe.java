package Projet.Metier;

public class Employe {

    private int idEmploye;
    private String mail;
    private String nom;
    private String prenom;
    private Bureau bureau;


    public Employe(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public Employe(int idEmploye, String mail, String nom, String prenom, Bureau bureau) {
        this.idEmploye = idEmploye;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.bureau = bureau;
    }

    public Employe(String mail, String nom, String prenom, Bureau bureau) {
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.bureau = bureau;
    }

    public Employe(String mail) {
        this.mail = mail;
    }


    public Employe(String mail, String nom, String prenom) {
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Bureau getBureau() {
        return bureau;
    }

    public void setBureau(Bureau bureau) {
        this.bureau = bureau;
    }

    @Override
    public String toString() {
        return "Employé numero: " + idEmploye+
                " - " + mail  +
                " - nom: " + nom +
                " - prenom: " + prenom;
    }
}
