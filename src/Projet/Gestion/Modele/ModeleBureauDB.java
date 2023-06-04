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

public class ModeleBureauDB implements  DAOBureau {

    protected Connection dbConnect;

    public ModeleBureauDB() {dbConnect = DBConnection.getConnection();}

    @Override
    public Bureau create(Bureau newBur) {
        String query1 = "insert into BUREAU (SIGLE, NUMERO_TELEPHONE) values (?,?)";
        String query2 = "select IDBUREAU from BUREAU where SIGLE=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1); PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm1.setString(1, newBur.getSigle());
            pstm1.setString(2, newBur.getTel());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }
            pstm2.setString(1, newBur.getSigle());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idb = rs.getInt(1);
                newBur.setIdBureau(idb);
                return newBur;
            } else {
                throw new Exception("Aucun bureau n'a été trouvé!");
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(Bureau bur) {
        String query = "delete from BUREAU where SIGLE =?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, bur.getSigle());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Bureau read(Bureau burRech) {
        String query = "select * from BUREAU where SIGLE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, burRech.getSigle());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int idbureau = rs.getInt("IDBUREAU");
                String tel = rs.getString("NUMERO_TELEPHONE");
                Bureau bu = new Bureau(idbureau, burRech.getSigle(), tel);
                return bu;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Bureau update(Bureau bur) {

        String query1 = "update BUREAU set sigle=?, numero_telephone=? WHERE sigle=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query1)) {
            pstm.setString(1, bur.getSigle());
            pstm.setString(2, bur.getTel());
            pstm.setString(3, bur.getSigle());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new Exception("Aucun bureau n'a été mis à jour");
            }
            return read(bur);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Bureau> readAll() {
        String query = "select * from BUREAU ORDER BY IDBUREAU";
        List<Bureau> lbur = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(query); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idBur = rs.getInt("IDBUREAU");
                String sigle = rs.getString("SIGLE");
                String tel = rs.getString("NUMERO_TELEPHONE");
                lbur.add(new Bureau(idBur, sigle, tel));
            }
            if (lbur.isEmpty()) {
                return null;
            }
            return lbur;
        } catch (Exception e) {
            return null;
        }
    }


}


