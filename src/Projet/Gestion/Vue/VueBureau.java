package Projet.Gestion.Vue;

import Projet.Metier.Bureau;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class VueBureau extends VueCommune implements VueBureauInterface {

    private static Scanner sc = new Scanner(System.in);

    @Override
    public Bureau create() {
        boolean flag = false;

        String sigle = getMsg("Sigle du bureau? :");
        String tel= readPhoneNumber("N° de téléphone?");
        Bureau bu = new Bureau(sigle, tel);
        return bu;
    }

    @Override
    public void display(Bureau bu) {
        displayMsg(bu.toString());
    }

    @Override
    public Bureau update(Bureau bu) {
        do {
            String choix = getMsg("1.Changement de tel  \n2. Fin");
            switch (choix) {
                case "1":
                    String nouvTel = getMsg("Nouveau tel : ");
                    bu.setSigle(bu.getSigle());
                    bu.setTel(nouvTel);
                    displayMsg("Le tel a été mis a jour. " + bu.toString());
                    break;
                case "2":
                    return bu;
                default:
                    displayMsg("Entrez un choix valide");
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
        for (Bureau bu : lbur) {
            displayMsg((++i) + ") " + bu.toString());
        }
    }
}
