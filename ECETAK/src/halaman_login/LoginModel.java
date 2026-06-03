
package halaman_login;

import config.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    private String username;
    private String role;

    // Fungsi utama untuk memeriksa akun ke database phpMyAdmin
    public boolean validasiLogin(String username, String password, String role) {
        Connection conn = koneksi.getKoneksi();
        // Memeriksa tabel 'users' berdasarkan input username, password, dan role
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                this.username = rs.getString("username");
                this.role = rs.getString("role");
                return true; // Data cocok, login berhasil
            }
        } catch (SQLException e) {
            System.err.println("Error Query Login: " + e.getMessage());
        }
        return false; // Data tidak cocok, login gagal
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
