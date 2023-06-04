package Projet.Gestion.Modele;

import Projet.Metier.Bureau;
import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;

import java.util.ArrayList;
import java.util.List;


public class ModeleBureau implements DAOBureau {

    private List<Bureau> mesBureaux = new ArrayList<>();

    @Override
    public Bureau create(Bureau newBur) {
        if(mesBureaux.contains(newBur)) return null;
        mesBureaux.add(newBur);
        return newBur;
    }

    @Override
    public boolean delete(Bureau BurRech) {
        Bureau bu = read(BurRech);
        if(bu != null){
            mesBureaux.remove(bu);
            return true;
        }
        else return false;
    }

    @Override
    public Bureau read(Bureau BurRech) {
        int idRech = BurRech.getIdBureau();
        for(Bureau bureau : mesBureaux){
            if(bureau.getIdBureau() == idRech){
                return bureau;
            }
        }
        return null;
    }

    @Override
    public Bureau update(Bureau BurRech) {
        Bureau bu = read(BurRech);
        if(bu == null){
            return null;
        }
        bu.setSigle(BurRech.getSigle());
        bu.setTel(BurRech.getTel());
        return bu;
    }

    @Override
    public List<Bureau> readAll() {
        return mesBureaux;
    }


}
