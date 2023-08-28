package Projet.Gestion.Vue;

import Projet.Metier.Bureau;
import Projet.Metier.Employe;

import javax.swing.*;
import java.util.List;

public class VueBureauGraph extends VueCommuneGraph implements VueBureauInterface {


    @Override
    public Bureau create() {

        JTextField tfsigle = new JTextField();
        JTextField tftel = new JTextField();



        Object[] message = {
                "sigle: ", tfsigle,
                "tel: ", tftel,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "nouveau bureau", JOptionPane.DEFAULT_OPTION);
        String sigle = tfsigle.getText().toString();
        String tel = tftel.getText().toString();


        Bureau newBur = new Bureau(sigle,tel);
        return newBur;
    }

    @Override
    public void display(Bureau b) {
        displayMsg(b.toString());
    }

    @Override
    public Bureau update(Bureau b) {

        do {
            String ch = getMsg("1. Changement de tel\n2. Fin");
            switch (ch) {
                case "1":
                    String ntel = getMsg("Nouveau tel : ");
                    b.setTel(ntel);
                    break;
                case "2":
                    return b;
                default:
                    displayMsg("Veuillez entrer un choix valide");
            }
        } while (true);
    }

    @Override
    public String read() {
        String sigle = getMsg("Sigle du bureau : ");
        return sigle;
    }

    @Override
    public void affAll(List<Bureau> lbur) {
        int i = 0;
        StringBuffer sb = new StringBuffer(200);
        for(Bureau b : lbur) sb.append((++i)+") "+b+"\n");
        displayMsg(sb.toString());
    }

}