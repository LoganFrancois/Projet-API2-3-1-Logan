package Projet;

import Projet.Gestion.Modele.*;
import Projet.Gestion.Presenter.PresenterBureau;
import Projet.Gestion.Presenter.PresenterEmploye;
import Projet.Gestion.Presenter.PresenterMessage;
import Projet.Gestion.Vue.*;

import java.sql.*;
import java.util.Scanner;

public class GestBureau {


    private Scanner sc = new Scanner(System.in);
    private Connection dbConnect;
    private PresenterBureau prbureau;
    private PresenterEmploye premploye;
    private PresenterMessage prmessage;


    public void gestion() {
        VueBureauInterface vueBureau;
        VueEmployeInterface vueEmploye;
        VueMessageInterface vueMessage;

        VueCommuneInterface vcm;

        DAOBureau mdBureau;
        DAOEmploye mdEmp;
        DAOMessage mdMsg;

        vcm = new VueCommune();


        mdBureau = new ModeleBureauDB();
        mdEmp = new ModeleEmployeDB();
        mdMsg = new ModeleMessageDB();

        vueBureau = new VueBureau();
        vueEmploye = new VueEmploye();
        vueMessage = new VueMessage();

        prbureau = new PresenterBureau(mdBureau, vueBureau);
        premploye = new PresenterEmploye(mdEmp, vueEmploye, mdBureau,vueMessage,mdMsg);
        prmessage = new PresenterMessage(vueMessage, mdMsg);


        prmessage.setPe(premploye);
        prmessage.setPb(prbureau);


        do {

            System.out.println("         --- Menu Principal ---");
            int choix = vcm.menu(new String[]{"Bureaux", "Employés", "Messages", "Fin"});


            switch (choix) {
                case 1:
                    prbureau.gestion();
                    break;
                case 2:
                    if (mdBureau.readAll().isEmpty()){
                        System.out.println("Vous ne pouvez pas avoir accès à cette interface tant que vous n'avez pas encodé de bureau");
                        break;
                    }
                    premploye.gestion();
                    break;
                case 3:
                    if (mdMsg.readAll().isEmpty()) {
                        System.out.println("Vous ne pouvez pas avoir accès à cette interface tant que vous n'avez pas encodé d'employé");
                        break;
                    }
                    prmessage.gestion();
                    break;
                case 4:
                    return;


                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }


    public static void main(String[] args) {

        GestBureau g = new GestBureau();
        g.gestion();
    }


}
