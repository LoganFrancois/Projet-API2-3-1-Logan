package Projet.Gestion.Presenter;

import Projet.Gestion.Modele.DAOBureau;
import Projet.Gestion.Modele.DAOEmploye;
import Projet.Gestion.Modele.DAOMessage;
import Projet.Gestion.Vue.VueBureauInterface;
import Projet.Gestion.Vue.VueEmployeInterface;
import Projet.Gestion.Vue.VueMessageInterface;
import Projet.Metier.Bureau;
import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PresenterMessage {



    private DAOMessage mdm;
    private VueMessageInterface vueMsg;
    private DAOEmploye mde;

    private PresenterBureau pb;
    private PresenterEmploye pe;

    public void setPb(PresenterBureau pb) {
        this.pb = pb;
    }

    public void setPe(PresenterEmploye pe) {
        this.pe = pe;
    }

    public PresenterMessage(VueMessageInterface vueMsg, DAOMessage mdm) {
        this.vueMsg = vueMsg;
        this.mdm = mdm;
    }

    public void gestion() {
        System.out.println("\n       **** Gestion des messages ****");

        do {
            System.out.println("\n");
            int ch = vueMsg.menu(new String[]{" Ajout", " Recherche", " Modification", " Suppression", " Voir tout", " Menu utilisateur", " Fin"});
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    recherche();
                    break;
                case 3:
                    modification();
                    break;
                case 4:
                    suppression();
                    break;
                case 5:
                    all();
                    break;
                case 6:
                    menuUtilisateur();
                    break;
                case 7:
                    return;
            }
        } while (true);
    }




    private void menuUtilisateur() {
        do {
            List li=null;
            Employe emp = pe.recherche();
            if (emp == null) {
                System.out.println("Adresse mail non valide");
                break;
            }
            System.out.println("\n");
            int ch = vueMsg.menu(new String[]{" Envoyer un message", " Relever ses messages non lus ", " Afficher les messages envoyés ", " Afficher les messages reçus mais déjà lus", "Afficher le nombre d'employés par bureau"," Retour"});
            switch (ch) {
                case 1:
                    Message msg=envoiMail(emp);
                    add(msg);
                    return;
                case 2:
                    li=mdm.mail_non_lus(emp);
                    System.out.println(li);
                return;
                case 3:
                    li=mdm.mails_envoyes(emp);
                    return;
                case 4:
                    li=mdm.courrierRecu(emp);
                    System.out.println(li);
                    return;
                case 5:

                    return;

                case 6:
                    return;
            }

        } while (true);
    }


    private Message envoiMail(Employe emp) {
        Message msg = vueMsg.envoi(emp);
        msg = mdm.EnvoiMess(emp, msg);
        return msg;
    }

    protected void add(Message msg){
        Employe e= pe.affAll();
        if (e == null) return;
        boolean res =mdm.add(e,msg);
        if (res) vueMsg.displayMsg("ajout avec succès\n");
        else vueMsg.displayMsg("pas ajouté");
    }

    protected void countBureau(){
    }

    protected void ajout() {
        Message newMsg = vueMsg.create();
        newMsg = mdm.create(newMsg);
        if (newMsg == null) {
            vueMsg.displayMsg("erreur lors de la création du msg");
            return;
        }
        vueMsg.displayMsg("message créé");
        vueMsg.display(newMsg);
    }


    protected Message recherche(){
        int idmes = vueMsg.read();
        Message msg = new Message(idmes, "", "", null, null);
        msg = mdm.read(msg);
        if (msg == null) {
            vueMsg.displayMsg("message introuvable");
            return null;
        }
        vueMsg.display(msg);
        return msg;
    }

protected void modification(){
    Message message = recherche();
    if (message != null) {
        message = vueMsg.update(message);
        mdm.update(message);
    }
}
protected void suppression(){
    Message msg = recherche();
    if (msg != null) {
        String rep;
        do {
            rep = vueMsg.getMsg("confirmez-vous la suppression (o/n) ? ");
        } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));
        if (rep.equalsIgnoreCase("o")) {
            if (mdm.delete(msg)) vueMsg.displayMsg("message supprimé");
            else vueMsg.displayMsg("message non supprimé");
        }
    }
}

protected void all(){
    vueMsg.affAll(mdm.readAll());
}
}


