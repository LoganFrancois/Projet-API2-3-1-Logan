package Projet.Gestion.Modele;

import Projet.Metier.Bureau;
import Projet.Metier.Employe;
import Projet.Metier.Infos;
import Projet.Metier.Message;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModeleEmployeDB implements DAOEmploye {

    protected Connection dbConnect;

    public ModeleEmployeDB() {
        dbConnect = DBConnection.getConnection();
    }


    @Override
    public Employe create(Employe emp) {
        String query1 = "insert into EMPLOYE(mail, nom, prenom, idbureau) values (?,?,?,?)";
        String query2 = "select IDEMPLOYE from EMPLOYE where MAIL=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1); PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm1.setString(1, emp.getMail());
            pstm1.setString(2, emp.getNom());
            pstm1.setString(3, emp.getPrenom());
            pstm1.setInt(4, emp.getBureau().getIdBureau());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }
            pstm2.setString(1, emp.getMail());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idemp = rs.getInt(1);
                emp.setIdEmploye(idemp);
                return emp;
            } else {
                throw new Exception("aucun employé de trouvé");
            }
        } catch (Exception e) {
            System.out.println("erreur sql :" + e);
            return null;
        }
    }

    @Override
    public boolean delete(Employe empRech) {
        String req = "delete from EMPLOYE where mail=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, empRech.getMail());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Employe read(Employe empRech) {
        String req = "select * from EMPLOYE where MAIL=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, empRech.getMail());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int idemploye = rs.getInt("IDEMPLOYE");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                int idbureau = rs.getInt("IDBUREAU");
                Bureau idb = new Bureau(idbureau);
                Employe emp = new Employe(idemploye, empRech.getMail(), nom, prenom, idb);
                return emp;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Employe update(Employe empRech) {
        String req = "update EMPLOYE set nom=?, prenom=? where mail=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(3, empRech.getMail());
            pstm.setString(1, empRech.getNom());
            pstm.setString(2, empRech.getPrenom());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new Exception("aucun employé n'a été mis à jour");
            }
            return read(empRech);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Employe> readAll() {
        String req = "select * from EMPLOYE order by IDEMPLOYE";
        List<Employe> lemp = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idemploye = rs.getInt("IDEMPLOYE");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                int idbureau = rs.getInt("IDBUREAU");
                Bureau idb = new Bureau(idbureau);
                lemp.add(new Employe(idemploye, mail, nom, prenom, idb));
            }
            if (lemp.isEmpty()) {
                return null;
            }
            return lemp;
        } catch (Exception e) {
            return null;
        }
    }


}