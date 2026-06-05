
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class koneksi {
    // 1. Siapkan variabel penampung koneksi tunggal
    private static Connection koneksiTunggal;

    public static Connection getKoneksi(){
        // 2. Cek, jika koneksi belum pernah dibuat atau terputus, baru buat yang baru
        if (koneksiTunggal == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/db_epercetakan";
                String user = "root";
                String password = "";
                
                // Registrasi driver modern (Sangat direkomendasikan untuk NetBeans baru)
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                
                koneksiTunggal = DriverManager.getConnection(url, user, password);
                System.out.println("-> INFO: Koneksi ke MySQL Sukses Terbuka!");
            } catch (SQLException se) {
                System.out.println("-> ERROR: Koneksi ke MySQL Gagal Total!");
                se.printStackTrace();
                return null;
            }
        }
        // 3. Jika sudah ada, langsung kembalikan koneksi yang sudah siap pakai
        return koneksiTunggal;
    }
}

