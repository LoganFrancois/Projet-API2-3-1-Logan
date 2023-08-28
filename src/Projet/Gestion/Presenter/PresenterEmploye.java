package Projet.Gestion.Presenter;

import Projet.Gestion.Modele.DAOBureau;
import Projet.Gestion.Modele.DAOEmploye;
import Projet.Gestion.Modele.DAOMessage;
import Projet.Gestion.Vue.VueEmployeInterface;
import Projet.Gestion.Vue.VueMessageInterface;
import Projet.Metier.Bureau;
import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;

import java.util.List;

public class PresenterEmploye {

    private DAOEmploye mde;
    private VueEmployeInterface vueEmp;
    private DAOBureau mdb;
    private VueMessageInterface vueMsg;
    private DAOMessage mdm;
    private PresenterMessage pm;

    public PresenterEmploye(DAOEmploye mde, VueEmployeInterface vueEmp, DAOBureau mdb, VueMessageInterface vueMsg, DAOMessage mdm) {
        this.mde = mde;
        this.vueEmp = vueEmp;
        this.mdb = mdb;
        this.vueMsg = vueMsg;
        this.mdm = mdm;
    }


    public void gestion() {
        System.out.println("\n       **** Gestion des employés ****");
        List li=null;
        do {
            System.out.println("\n");
            int ch = vueEmp.menu(new String[]{" Ajout", " Recherche", " Modification", " Suppression", " Voir tout", " Menu Utilisateur"," Retour"});
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

    private void menuUtilisateur(){
        boolean flag= false;
        Employe emp;
        do {
             emp = recherche();
            if (emp == null) {
                System.out.println("Adresse mail non valide");

            }
            else {
                flag=true;
            }
        }while (!flag);

        do {
            List li=null;

            System.out.println("\n");
            int ch = vueMsg.menu(new String[]{" Envoyer un message", " Relever ses messages non lus ", " Afficher les messages envoyés ", " Afficher les messages reçus mais déjà lus", " Vérifier si mes messages envoyés ont été lus", " Retour"});
            switch (ch) {
                case 1:
                    Message msg=envoiMail(emp);
                    add(msg);
                    break;
                case 2:
                    li=mdm.mail_non_lus(emp);
                    System.out.println(li);
                    break;
                case 3:
                    li=mdm.mails_envoyes(emp);
                    System.out.println(li);
                    break;
                case 4:
                    li=mdm.courrierRecu(emp);
                    System.out.println(li);
                    break;
                case 5:
                    li=mdm.verificationReponse(emp);
                   break;

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
        Employe e= affAll();
        if (e == null) return;
        boolean res =mdm.add(e,msg);
        if (res) vueMsg.displayMsg("message envoyé\n");
        else vueMsg.displayMsg("échec de l'envoi");
    }

    protected Employe affAll() {
        List<Employe> le = mde.readAll();
        vueEmp.affAll(mde.readAll());
        do {
            String chs = vueEmp.getMsg("numéro de l'employe choisi (0 pour aucun) :");
            int ch = Integer.parseInt(chs);
            if (ch == 0) return null;
            if (ch >= 1 && ch <= le.size()) return le.get(ch - 1) ;
        } while (true);
    }

    private void affBureaux() {
        System.out.println("----------------------------------");
        System.out.println("Bureaux disponibles (choisir l'id) ");
        System.out.println("----------------------------------");
        int i = 0;
        for (Bureau b : mdb.readAll()) {
            System.out.printf( "---> Id du bureau n°%s, sigle :  %s \n", b.getIdBureau(), b.getSigle());
        }
    }

    protected void ajout() {
        affBureaux();
        Employe newEmp = vueEmp.create();
        newEmp = mde.create(newEmp);
        if (newEmp == null) {
            vueEmp.displayMsg("erreur lors de la création de l'employé : doublon");
            return;
        }
        vueEmp.displayMsg("employé créé");
        vueEmp.display(newEmp);
    }

    protected Employe recherche() {
        String mail = vueEmp.read();
        Employe emp = new Employe(mail, "", "", null);
        emp = mde.read(emp);
        if (emp == null) {
            vueEmp.displayMsg("employé introuvable");
            return null;
        }
        vueEmp.display(emp);
        return emp;
    }

    protected void modification() {
        Employe emp = recherche();
        if (emp != null) {
            emp = vueEmp.update(emp);
            mde.update(emp);
        }
    }

    protected void suppression() {
        Employe emp = recherche();
        if (emp != null) {
            String rep;
            do {
                rep = vueEmp.getMsg("Confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if (mde.delete(emp)) vueEmp.displayMsg("employé supprimé");
                else vueEmp.displayMsg("employé non supprimé");
            }
        }
    }

    protected void all() {
        vueEmp.affAll(mde.readAll());
    }

}
