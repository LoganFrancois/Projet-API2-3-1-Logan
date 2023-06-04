package myconnections;

import java.sql.*;

public class SQLOracle {

    public static void main(String args[]) {
        String userid = "ora44";
        String password = "oracle";
        String server = "mons-oracle12.condorcet.be";
        //String server = "localhost";
        String port = "1521";
        String database = "orcl.condorcet.be";
        String url = "jdbc:oracle:thin:@//" + server + ":" + port + "/" + database;//construit l'URL de la base de données
        Connection dbConnect = null;

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            dbConnect = DriverManager.getConnection(url, userid, password);
            System.out.println("connexion établie");

            // Requête SQL pour afficher tous les éléments de la table
            String selectAllQuery = "SELECT * FROM bureau ";
            System.out.println("Exécute la requête SQL : " + selectAllQuery);

            // Exécution de la requête SQL pour afficher tous les éléments de la table
            Statement selectAllStmt = dbConnect.createStatement();
            ResultSet rs = selectAllStmt.executeQuery(selectAllQuery);

            // Parcours des résultats de la requête et affichage de chaque ligne
            while (rs.next()) {
                String sigle = rs.getString("SIGLE");
                String numero_telephone = rs.getString("NUMERO_TELEPHONE");
                int n = rs.getInt("IDBUREAU");
                System.out.println(sigle + " " + numero_telephone + " " + n);
            }

            // Requête SQL pour ajouter un élément dans la table
            String insertQuery = "INSERT INTO bureau ( SIGLE, NUMERO_TELEPHONE) VALUES ( 'FRA', '0102030405')";
            System.out.println("Exécute la requête SQL : " + insertQuery);

            // Exécution de la requête SQL pour ajouter un élément dans la table
            Statement insertStmt = dbConnect.createStatement();
            int rowsInserted = insertStmt.executeUpdate(insertQuery);
            System.out.println(rowsInserted + " ligne(s) insérée(s) dans la table Bureau");

            // Requête SQL pour afficher tous les éléments de la table Bureau
            String selectAllQuery2 = "SELECT * FROM bureau ";
            System.out.println("Exécute la requête SQL : " + selectAllQuery2);

            // Exécution de la requête SQL pour afficher tous les éléments de la table
            Statement selectAllStmt2 = dbConnect.createStatement();
            ResultSet rs2 = selectAllStmt2.executeQuery(selectAllQuery2);

            // Parcours des résultats de la requête et affichage de chaque ligne
            while (rs2.next()) {
                String sigle = rs2.getString("SIGLE");
                String numero_telephone = rs2.getString("NUMERO_TELEPHONE");
                int n = rs2.getInt("IDBUREAU");
                System.out.println(sigle + " " + numero_telephone + " " + n);
            }


            // Exécution de la requête SQL pour supprimer l'élément ajout
            String deleteQuery = "DELETE FROM bureau WHERE SIGLE = 'FRA'";
            System.out.println("Exécute la requête SQL : " + deleteQuery);
            // Exécution de la requête SQL pour supprimer l'élément ajout
            Statement deleteStmt = dbConnect.createStatement();
            int deleteResult = deleteStmt.executeUpdate(deleteQuery);
            System.out.println(deleteResult + " élément(s) supprimé(s) de la table");

            // Requête SQL pour afficher tous les éléments de la table Bureau
            System.out.println("\nAffichage de tous les éléments de la table bureau après suppression : ");
            String selectAllStmt3 = "SELECT * FROM bureau";
            // Exécution de la requête SQL pour afficher tous les éléments de la table
            Statement stmt2 = dbConnect.createStatement();
            ResultSet rs3 = stmt2.executeQuery(selectAllStmt3);

            // Parcours des résultats de la requête et affichage de chaque ligne

            while (rs3.next()) {
                String sigle = rs3.getString("SIGLE");
                String numero_telephone = rs3.getString("NUMERO_TELEPHONE");
                int n = rs3.getInt("IDBUREAU");
                System.out.println(sigle + " " + numero_telephone + " " + n);
            }
        } catch (SQLException e) {
            System.out.println("erreur SQL : " + e);
        } catch (Exception e) {
            System.out.println("erreur : " + e);
        } finally {
            try {
                dbConnect.close();
            } catch (SQLException e) {
                System.out.println("erreur de fermeture de connexion : " + e);
            }
        }
    }
}