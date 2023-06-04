package Projet.Gestion.Vue;

import Projet.Metier.Bureau;
import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;

import java.time.LocalDate;
import java.util.List;

public class VueEmploye extends VueCommune implements VueEmployeInterface {


    @Override
    public Employe create() {
        Integer idbureau = Integer.parseInt(getMsg("Id du bureau: "));
        Bureau idb = new Bureau(idbureau);
        String mail = getMsg("Email de l'employé : ");
        String nom = getMsg("Nom : ");
        String prenom = getMsg("Prenom : ");
        Employe newEmploye = new Employe(mail, nom, prenom, idb);
        return newEmploye;
    }

    @Override
    public void display(Employe emp) {
        displayMsg(emp.toString());
    }

    @Override
    public Employe update(Employe emp) {
        do {
            String choix = getMsg("1. Changement de nom\n2. Changement de prénom\n3. Fin");
            switch (choix) {
                case "1":
                    String nouvNom = getMsg("Nouveau nom: ");
                    emp.setNom(nouvNom);
                    displayMsg("Nom mis à jour" + emp.toString());
                    break;
                case "2":
                    String nouvPrenom = getMsg("Nouveau prenom: ");
                    emp.setPrenom(nouvPrenom);
                    displayMsg("Prénom mis à jour" + emp.toString());
                    break;
                case "3":
                    return emp;
                default:
                    displayMsg("Entrez un choix valide");
            }
        } while (true);
    }

    @Override
    public String read() {
        String mail = getMsg("Mail de l'employe : ");
        return mail;
    }

    @Override
    public void affAll(List<Employe> lemp) {
        int i = 0;
        for (Employe emp : lemp) {
            displayMsg((++i) + ") " + emp.toString());
        }
    }


}
