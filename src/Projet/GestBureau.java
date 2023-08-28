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


    public void gestion(String modeVue, String modeData) {
        VueBureauInterface vueBureau;
        VueEmployeInterface vueEmploye;
        VueMessageInterface vueMessage;

        VueCommuneInterface vcm;

        DAOBureau mdBureau;
        DAOEmploye mdEmp;
        DAOMessage mdMsg;


        if (modeData.equals("db")) {
            mdBureau = new ModeleBureauDB();
            mdEmp = new ModeleEmployeDB();
            mdMsg = new ModeleMessageDB();

        }
       else{
            mdBureau = new ModeleBureau();
            mdEmp = new ModeleEmploye();
            mdMsg = new ModeleMessage();
        }

        if (modeVue.equals("console")) {
            vcm = new VueCommuneGraph();
            vueBureau = new VueBureauGraph();
            vueEmploye = new VueEmployeGraph();
            vueMessage = new VueMessageGraph();
        }
       else {
            vcm = new VueCommune();
           vueBureau = new VueBureau();
            vueEmploye = new VueEmploye();
            vueMessage = new VueMessage();
        }

        prbureau = new PresenterBureau(mdBureau, vueBureau);
        premploye = new PresenterEmploye(mdEmp, vueEmploye, mdBureau,vueMessage,mdMsg);
        prmessage = new PresenterMessage(vueMessage, mdMsg);


        prmessage.setPe(premploye);



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
                    if (mdEmp.readAll().isEmpty()) {
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
        String modeVue = args[0];
        String modeData =args[1];
        GestBureau g = new GestBureau();
        g.gestion(modeVue,modeData);
    }


}
