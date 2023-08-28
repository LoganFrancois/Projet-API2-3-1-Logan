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
import java.util.Date;
import java.util.List;

public class PresenterMessage {



    private DAOMessage mdm;
    private VueMessageInterface vueMsg;
    private PresenterEmploye pe;



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
            int ch = vueMsg.menu(new String[]{" Ajout", " Recherche", " Modification", " Suppression", " Voir tout", "Afficher les mails entre 2 dates"," Fin"});
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
                    return;
            }
        } while (true);
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


