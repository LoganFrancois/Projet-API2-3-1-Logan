package Projet.Gestion.Presenter;

import Projet.Gestion.Modele.DAOBureau;
import Projet.Gestion.Vue.VueBureauInterface;
import Projet.Metier.Bureau;

public class PresenterBureau {


    private DAOBureau mdb;
    private VueBureauInterface vueb;

    public PresenterBureau(DAOBureau mdb, VueBureauInterface vueb) {
        this.mdb = mdb;
        this.vueb = vueb;
    }

    public void gestion() {
        System.out.println("\n       **** Gestion des bureaux ****");
        do {
            System.out.println("\n");
            int ch = vueb.menu(new String[]{" Ajout", " Recherche", " Modification", " Suppression", " Voir tout", " Retour"});
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

    protected void ajout() {

        Bureau newBur = vueb.create();
        newBur = mdb.create(newBur);
        if (newBur == null) {
            vueb.displayMsg("erreur lors de la création du nouveau bureau, c'est un doublon");
            return;
        }
        vueb.displayMsg("bureau créé");
        vueb.display(newBur);

    }

    protected Bureau recherche() {
        String siglerech = vueb.read();
        Bureau bu = new Bureau(siglerech, "");
        bu = mdb.read(bu);
        if (bu == null) {
            vueb.displayMsg("bureau introuvable");
            return null;
        }
        vueb.display(bu);
        return bu;
    }

    protected void modification() {
        Bureau bu = recherche();
        if (bu != null) {
            bu = vueb.update(bu);
            mdb.update(bu);
        }
    }

    protected void suppression() {
        Bureau bu = recherche();
        if (bu != null) {
            String rep;
            do {
                rep = vueb.getMsg("Confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if (mdb.delete(bu)) vueb.displayMsg("bureau supprimé");
                else vueb.displayMsg("bureau non supprimé");
            }
        }
    }

    protected void all(){
        vueb.affAll(mdb.readAll());
    }


}
