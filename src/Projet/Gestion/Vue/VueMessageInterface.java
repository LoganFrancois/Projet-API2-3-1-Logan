package Projet.Gestion.Vue;

import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;

public interface VueMessageInterface extends VueInterface <Message, Integer>{
    public Message envoi(Employe emp);


}
