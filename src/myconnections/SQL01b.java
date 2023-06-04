
package myconnections;
import java.sql.*;


public class SQL01b {

    public void demo(){

        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");
        String query = "select * from BUREAU";
        try (Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                String idbureau = rs.getString("IDBUREAU");
                String sigle = rs.getString("SIGLE");
                String tel = "" + rs.getInt("NUMERO_TELEPHONE");
                System.out.println(sigle + " " + tel + " " + idbureau);
            }
        } catch (SQLException e) {
            System.out.println("erreur SQL " + e);
        }

        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        SQL01b pgm = new SQL01b();
        pgm.demo();
    }
}
