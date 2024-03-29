package Projet.Gestion.Modele;

import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ModeleMessageDB implements DAOMessage {

    protected Connection dbConnect;

    public ModeleMessageDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Message create(Message newMsg) {
        String query1 = "insert into MESSAGE (OBJET, CONTENU, DATE_ENVOI, IDEMPLOYE) values (?,?,?,?)";
        String query2 = "select IDMESSAGE from MESSAGE where OBJET=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm1.setString(1, newMsg.getObjet());
            pstm1.setString(2, newMsg.getContenu());
            pstm1.setDate(3, Date.valueOf(newMsg.getDateEnvoi()));
            pstm1.setInt(4, newMsg.getEmetteur().getIdEmploye());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }
            pstm2.setString(1, newMsg.getObjet());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idmsg = rs.getInt(1);
                newMsg.setIdMessage(idmsg);
                return newMsg;
            } else {
                throw new Exception("Aucun message n'a été trouvé");
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(Message msgRech) {
        String req = "delete from MESSAGE where IDMESSAGE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, msgRech.getIdMessage());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Message read(Message msgRech) {
        String req = "select * from MESSAGE where IDMESSAGE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, msgRech.getIdMessage());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                Message m = new Message(msgRech.getIdMessage(), objet, contenu, date_envoi.toLocalDate());
                return m;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Message update(Message msgRech) {
        String query = "update MESSAGE set OBJET=?, CONTENU=? where IDMESSAGE=? ";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(3, msgRech.getIdMessage());
            pstm.setString(1, msgRech.getObjet());
            pstm.setString(2, msgRech.getContenu());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new Exception("Aucun message n'a été mis à jour");
            }
            return read(msgRech);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Message> readAll() {
        String query = "select * from MESSAGE order by IDMESSAGE";
        List<Message> lmsg = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(query); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idMessage = rs.getInt("IDMESSAGE");
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                lmsg.add(new Message(idMessage, objet, contenu, date_envoi.toLocalDate()));
            }
            if (lmsg.isEmpty()) {
                return null;
            }
            return lmsg;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Message EnvoiMess(Employe emp, Message msg) {
        String query1 = "insert into MESSAGE (OBJET, CONTENU, DATE_ENVOI, IDEMPLOYE) values (?,?,?,?)";
        String query2 = "select IDMESSAGE from MESSAGE where OBJET=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm1.setString(1, msg.getObjet());
            pstm1.setString(2, msg.getContenu());
            pstm1.setDate(3, Date.valueOf(msg.getDateEnvoi()));
            pstm1.setInt(4, emp.getIdEmploye());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }
            pstm2.setString(1, msg.getObjet());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idmsg = rs.getInt(1);
                msg.setIdMessage(idmsg);
                return msg;
            } else {
                throw new Exception("Aucun message n'a été trouvé");
            }

        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public boolean add(Employe emp, Message msg) {
        String query = "select * from MESSAGE where IDMESSAGE=?";
        String query2 = "insert into INFOS( IDEMPLOYE, IDMESSAGE) values (?,?)";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {

            pstm.setInt(1, msg.getIdMessage());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                System.out.println("ok");
                pstm2.setInt(1, emp.getIdEmploye());
                pstm2.setInt(2, msg.getIdMessage());
                pstm2.executeQuery();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    @Override
    public List<Message> mail_non_lus(Employe emp) {

        List<Message> lmess = new ArrayList<>();
        String query = "SELECT * FROM messages_non_lus WHERE IDEMPLOYE = ?";
        String query2 = "UPDATE INFOS SET DATELECTURE =? WHERE IDEMPLOYE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query); PreparedStatement pstm2 = dbConnect.prepareStatement(query2)
        ) {
            pstm.setInt(1, emp.getIdEmploye());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idemploye = rs.getInt("EMETTEUR");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM2");
                String prenom = rs.getString("PRENOM2");
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                Employe lemp = new Employe(idemploye, mail, nom, prenom, null);
                Message msg=new Message(objet,contenu,date_envoi.toLocalDate(),lemp);
                lmess.add(msg);
            }
            if (lmess.isEmpty()) {
                System.out.println("Il n'y a pas de messages non lus");
                return null;

            } else {
                LocalDate ajd = LocalDate.now();
                pstm2.setInt(2, emp.getIdEmploye());
                pstm2.setDate(1, Date.valueOf(ajd));
                int n = pstm2.executeUpdate();
                if (n == 0) {
                    throw new Exception("Aucun message n'a été mis à jour");
                }
                return lmess;
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;

        }
    }

    @Override
    public List<Message> mails_envoyes(Employe emp) {
        List<Message> lmess = new ArrayList<>();
        String query = "SELECT * FROM vue_messages_par_employe WHERE IDEMPLOYE = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
        ) {
            pstm.setInt(1, emp.getIdEmploye());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM2");
                String prenom = rs.getString("PRENOM2");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                Employe lemp = new Employe(mail, nom, prenom);
                lmess.add(new Message(objet,contenu,date_envoi.toLocalDate(),lemp));
            }
            if (lmess.isEmpty()) {
                System.out.println("Cet employé n'a envoyé aucun mail");
                return null;

            } else {
                return lmess;
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;

        }

    }

    @Override
    public List<Message> courrierRecu(Employe emp) {
        List<Message> lmess = new ArrayList<>();
        String query = "SELECT * FROM messages_lus WHERE IDEMPLOYE = ?";
// VOICI LA VUE EN QUESTION::
        //  CREATE OR REPLACE VIEW messages_lus AS
        //  SELECT e.idemploye, m.objet, m.contenu, m.date_envoi, e2.nom AS nom2, e2.idemploye AS emetteur, e2.prenom AS prenom2, e2.mail
        //  FROM employe e
        //  JOIN infos i ON e.idemploye = i.idemploye
        //  JOIN message m ON i.idmessage = m.idmessage
        //  JOIN employe e2 ON m.idemploye = e2.idemploye
        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
        ) {
            pstm.setInt(1, emp.getIdEmploye());

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idemploye = rs.getInt("EMETTEUR");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM2");
                String prenom = rs.getString("PRENOM2");
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                Employe lemp = new Employe(idemploye, mail, nom, prenom, null);
                lmess.add(new Message(1, objet, contenu, date_envoi.toLocalDate(), lemp));

            }
            if (lmess.isEmpty()) {
                System.out.println("Il n'y a pas de messages déjà lus pour cet utilisateur");
                return null;

            } else {
                return lmess;
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;

        }
    }

    @Override
    public List<Message> entre2dates(LocalDate a, LocalDate b) {
        List<Message> lmess = new ArrayList<>();
        String query = "SELECT objet, contenu, date_envoi FROM message WHERE date_envoi BETWEEN ? AND ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
        ) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                LocalDate localDate = date_envoi.toLocalDate();
                lmess.add(new Message(1, objet, contenu, localDate));
            }
            if (lmess.isEmpty()) {
                System.out.println("Il n'y a pas de messages entre ces dates");
                return null;

            } else {
                return lmess;
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

@Override
public List<Message> verificationReponse(Employe emp){
    List<Message> lmess = new ArrayList<>();
    String query = "select * from verif_reponse WHERE IDEMPLOYE=?";



   // SELECT e.idemploye, i.idemploye as Recepteur, i.datelecture, i.idmessage, m.date_envoi ,e2.nom AS nom2, e2.prenom AS prenom2, e2.mail
    //FROM employe e
    //JOIN message m ON e.idemploye = m.idemploye
    //JOIN infos i ON m.idmessage = i.idmessage
    //JOIN employe e2 ON i.idemploye = e2.idemploye;



    try (PreparedStatement pstm = dbConnect.prepareStatement(query);
    ) {
        pstm.setInt(1, emp.getIdEmploye());

        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            int idemploye = rs.getInt("RECEPTEUR");
            String mail = rs.getString("MAIL");
            String nom = rs.getString("NOM2");
            String prenom = rs.getString("PRENOM2");
            String objet = rs.getString("OBJET");
            String contenu = rs.getString("CONTENU");
            Date datelecture = rs.getDate("DATELECTURE");
            Date date_envoi = rs.getDate("DATE_ENVOI");
            Employe lemp = new Employe(idemploye, mail, nom, prenom, null);
            lmess.add(new Message(1, objet, contenu, date_envoi.toLocalDate(), lemp));


            String redText = "\u001B[31m";
            String resetText = "\u001B[0m";

            if (datelecture != null) {
                System.out.println("\n\nObjet: " + objet + "\n Contenu du message: " + contenu + " \n Message envoyé le: " + date_envoi + "`\n à " + nom + " " + prenom + "\n Il a été lu le " + datelecture );
            } else {
                System.out.println(redText + "\n\nObjet: " + objet + "\n Contenu du message: " + contenu + " \n Message envoyé le: " + date_envoi + "`\n à " + nom + " " + prenom +  "("+mail+ ")"+"\n Il n'a pas encore été lu" + resetText);
            }
        }
        if (lmess.isEmpty()) {
            System.out.println("Il n'y a pas de messages pour cet utilisateur");
            return null;
        } else {
            return lmess;
        }
    } catch (Exception e) {
        System.out.println(e);
        return null;
    }


}
}