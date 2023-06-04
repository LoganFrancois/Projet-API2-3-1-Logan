package Projet.Gestion.Vue;

import Projet.Metier.Bureau;
import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class VueMessage extends VueCommune implements  VueMessageInterface{


    @Override
    public Message create() {
        Integer idemp = Integer.parseInt(getMsg("Id de l'emetteur : "));
        Employe ide = new Employe(idemp);
        String objet = getMsg("Objet : ");
        String contenu = getMsg("Contenu : ");
        LocalDate date_envoi = LocalDate.now();
        Message m = new Message(objet, contenu, date_envoi,ide);
        return m;
    }

    @Override
    public void display(Message msg) {
        displayMsg(msg.toString());
    }

    @Override
    public Message update(Message msg) {
        do {
            String ch = getMsg("\n1. Changement d'objet\n2. Changement de contenu\n3. fin");
            switch (ch) {
                case "1":
                    String nouvObj = getMsg("Nouvel objet: ");
                    msg.setObjet(nouvObj);
                    displayMsg("Objet mis à jour" + msg.toString());
                    break;
                case "2":
                    String nouvContenu = getMsg("Nouveau contenu: ");
                    msg.setObjet(nouvContenu);
                    displayMsg("Contenu mis à jour" + msg.toString());
                    break;
                case "3":
              return msg;
                default:
                    displayMsg("Entrez un choix valide");
            }
        } while (true);
    }

    @Override
    public Integer read() {
        String id = getMsg("Identifiant du message : ");
        int idmsg = Integer.parseInt(id);
        return idmsg;
    }

    @Override
    public void affAll(List<Message> lmsg) {
        int i = 0;
        for (Message m : lmsg) {
            displayMsg((++i) + ") " + m.toString());
        }
    }

    @Override
    public Message envoi(Employe emp) {
        String objet = getMsg("Objet : ");
        String contenu = getMsg("Contenu : ");
        LocalDate date_envoi = LocalDate.now();
        Message m = new Message(objet, contenu, date_envoi,emp);
        return m;
    }


}
