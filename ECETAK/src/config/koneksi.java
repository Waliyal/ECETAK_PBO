
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class koneksi {
    public static Connection getKoneksi(){
        try{
            String url = "jdbc:mysql://localhost:3306/db_epercetakan";
            String user = "root";
            String password = "";
            
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException se){
            System.out.println("Koneksi Gagal");
            se.printStackTrace();
            return null;
        }
    }
}
