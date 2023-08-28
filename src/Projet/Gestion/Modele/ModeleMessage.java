package Projet.Gestion.Modele;

import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModeleMessage implements DAOMessage {

    private List<Message> mesMessages = new ArrayList<>();
    private List<Infos> mesInfos = new ArrayList<>();

    @Override
    public Message create(Message newMsg) {
        if (mesMessages.contains(newMsg)) return null;
        mesMessages.add(newMsg);
        return newMsg;
    }

    @Override
    public boolean delete(Message msgRech) {
        Message msg = read(msgRech);
        if (msg != null) {
            mesMessages.remove(msg);
            return true;
        }
        return false;
    }

    @Override
    public Message read(Message msgRech) {
            int idMessage = msgRech.getIdMessage();
            for (Message m : mesMessages) {
                if (m.getIdMessage() == idMessage)
                    System.out.println("ddddd");
                    return m;
            }
            return null;
    }

    @Override
    public Message update(Message msgRech) {
        Message msg = read(msgRech);
        if (msg == null) {
            return null;
        }
        msg.setObjet(msgRech.getObjet());
        msg.setContenu(msgRech.getContenu());
        msg.setDateEnvoi(msgRech.getDateEnvoi());
        return msg;
    }

    @Override
    public List<Message> readAll() {
        return mesMessages;
    }

    @Override
    public Message EnvoiMess(Employe emp, Message msg) {
        if (mesMessages.contains(msg)) return null;
        mesMessages.add(msg);
        return msg;
    }

    @Override
    public boolean add(Employe emp, Message msg) {
        return false;
    }

    @Override
    public List <Message> mail_non_lus(Employe emp) {
        return mesMessages;
    }

    @Override
    public List<Message> mails_envoyes(Employe emp) {
        return mesMessages;
    }

    @Override
    public List<Message> courrierRecu(Employe emp) {
        return mesMessages;
    }



    @Override
    public List<Message> entre2dates(LocalDate a, LocalDate b) {
        return mesMessages;
    }

    @Override
    public List <Message> verificationReponse (Employe emp) {return mesMessages;}


}
