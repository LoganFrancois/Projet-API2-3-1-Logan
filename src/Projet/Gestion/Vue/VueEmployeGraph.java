package Projet.Gestion.Vue;



import Projet.Metier.Employe;

import javax.swing.*;
import java.util.List;

public class VueEmployeGraph extends VueCommuneGraph implements VueEmployeInterface {


    @Override
    public Employe create() {

        JTextField tfmat = new JTextField();
        JTextField tfnom = new JTextField();
        JTextField tfprenom = new JTextField();
        JTextField tfmail = new JTextField();


        Object[] message = {
                "matricule: ", tfmat,
                "nom: ", tfnom,
                "prénom: ", tfprenom,
                "mail: ", tfmail,

        };

        int option = JOptionPane.showConfirmDialog(null, message, "nouvel employe", JOptionPane.DEFAULT_OPTION);
        String matricule = tfmat.getText().toString();
        String nom = tfnom.getText().toString();
        String prenom = tfprenom.getText().toString();

        Employe newEmp = new Employe(matricule, nom, prenom);
        return newEmp;
    }

    @Override
    public void display(Employe emp) {
        displayMsg(emp.toString());
    }

    @Override
    public Employe update(Employe emp) {

        do {
            String ch = getMsg("1. Changement de mail\n2. Fin");
            switch (ch) {
                case "1":
                    String nmail = getMsg("Nouveau mail : ");
                    emp.setMail(nmail);
                    break;
                case "2":
                    return emp;
                default:
                    displayMsg("Veuillez entrer un choix valide");
            }
        } while (true);
    }

    @Override
    public String read() {
        String mail = getMsg("mail de l'employe : ");
        return mail;
    }

    @Override
    public void affAll(List<Employe> lemp) {
        int i = 0;
        StringBuffer sb = new StringBuffer(200);
        for(Employe emp : lemp) sb.append((++i)+") "+emp+"\n");
        displayMsg(sb.toString());
    }

}
