package Projet.Gestion.Modele;

import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DAOMessage extends DAO<Message> {

Message EnvoiMess (Employe emp, Message msg);


boolean add(Employe emp, Message msg);

List<Message> mail_non_lus(Employe emp);

List <Message> mails_envoyes(Employe emp);

List <Message> courrierRecu(Employe emp);

List <Message> entre2dates (Date a, Date b);
}
