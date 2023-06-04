package Projet.Gestion.Modele;
import Projet.Metier.Bureau;
import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;

import java.util.ArrayList;
import java.util.List;

public class ModeleEmploye implements DAOEmploye {

    private List <Employe> mesEmployes = new ArrayList<>();

    @Override
    public Employe create(Employe newEmp) {
        if(mesEmployes.contains(newEmp)) return null;
        mesEmployes.add(newEmp);
        return newEmp;
    }

    @Override
    public boolean delete(Employe empRech) {
        Employe emp = read(empRech);
        if(emp != null){
            mesEmployes.remove(emp);
            return true;
        }
        else return false;
    }

    @Override
    public Employe read(Employe empRech) {
        int idRech = empRech.getIdEmploye();
        for(Employe employe : mesEmployes){
            if(employe.getIdEmploye() == idRech){
                return employe;
            }
        }
        return null;
    }

    @Override
    public Employe update(Employe empRech) {
        Employe emp = read(empRech);
        if(emp == null){
            return null;
        }
        emp.setNom(empRech.getNom());
        emp.setPrenom(empRech.getPrenom());
        emp.setMail(empRech.getMail());
        return emp;
    }

    @Override
    public List<Employe> readAll() {
        return mesEmployes;
    }


}
