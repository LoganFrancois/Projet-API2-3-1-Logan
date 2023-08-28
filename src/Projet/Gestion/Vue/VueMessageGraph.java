package Projet.Gestion.Vue;

import Projet.Metier.Employe;
import Projet.Metier.Message;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class VueMessageGraph extends VueCommuneGraph implements VueMessageInterface {




    @Override
    public Message create() {

        JTextField tfobj = new JTextField();
        JTextField tfcont = new JTextField();
        JTextField tfjour = new JTextField();
        JTextField tfmois = new JTextField();
        JTextField tfannee = new JTextField();
        JTextField tfidEmploye = new JTextField();


        Object[] message = {
                "Objet: ", tfobj,
                "Contenu: ", tfcont,
                "Jour de l'envoi ", tfjour,
                "Mois de l'envoi", tfmois,
                "Année de l'envoi",tfannee,
                "idemploye: ", tfidEmploye,

        };

        int option = JOptionPane.showConfirmDialog(null, message, "nouveau message", JOptionPane.DEFAULT_OPTION);
        String objet = tfobj.getText().toString();
        String contenu = tfcont.getText().toString();
        Integer jour = Integer.parseInt(tfjour.getText().toString());
        Integer mois = Integer.parseInt(tfmois.getText().toString());
        Integer annee = Integer.parseInt(tfannee.getText().toString());
        LocalDate date_envoi = LocalDate.of(jour, mois, annee);
        Integer idemp = Integer.parseInt(tfidEmploye.getText().toString());
     Employe emp = new Employe(idemp);
        Message newMess = new Message(objet, contenu, date_envoi,emp);
        return newMess;
    }

    @Override
    public void display(Message emp) {
        displayMsg(emp.toString());
    }

    @Override
    public Message update(Message m) {

        do {
            String ch = getMsg("1. Changement d'objet'\n2. Fin");
            switch (ch) {
                case "1":
                    String nobj = getMsg("Nouvel objet : ");
                    m.setObjet(nobj);
                    break;
                case "2":
                    return m;
                default:
                    displayMsg("Veuillez entrer un choix valide");
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
    public void affAll(List<Message> lmes) {
        int i = 0;
        StringBuffer sb = new StringBuffer(200);
        for(Message m : lmes) sb.append((++i)+") "+m+"\n");
        displayMsg(sb.toString());
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
