package kelola_produk;

import config.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class ProdukModel {

    // 1. Membaca data dari MySQL dan memasukkannya ke JTable
    public DefaultTableModel tampilkanData() {
        DefaultTableModel modelTabel = new DefaultTableModel();
        modelTabel.addColumn("ID");
        modelTabel.addColumn("Nama Barang");
        modelTabel.addColumn("Stok");
        modelTabel.addColumn("Harga");

        Connection conn = koneksi.getKoneksi();
        // PASTI SINKRON: Menggunakan nama tabel 'produk'
        String sql = "SELECT * FROM produk"; 

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelTabel.addRow(new Object[]{
                    rs.getInt("id_produk"), 
                    rs.getString("nama_produk"),
                    rs.getInt("stok"),
                    rs.getInt("harga")
                });
            }
        } catch (SQLException e) {
            // Memunculkan pop-up pesan eror detail jika nama kolom di database Anda ada yang salah
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal memuat database:\n" + e.getMessage());
            e.printStackTrace();
        }
        return modelTabel;
    }

    // 2. Tambah Data Produk (Create)
    public boolean tambahProduk(String nama, int stok, int harga) {
        Connection conn = koneksi.getKoneksi();
        // SINKRON: Menggunakan nama tabel 'produk'
        String sql = "INSERT INTO produk (nama_produk, stok, harga) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setInt(2, stok);
            ps.setInt(3, harga);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal Tambah Data:\n" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 3. Ubah Data Produk (Update)
    public boolean ubahProduk(int id, String nama, int stok, int harga) {
        Connection conn = koneksi.getKoneksi();
        // SINKRON: Menggunakan nama tabel 'produk'
        String sql = "UPDATE produk SET nama_produk = ?, stok = ?, harga = ? WHERE id_produk = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setInt(2, stok);
            ps.setInt(3, harga);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal Ubah Data:\n" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 4. Hapus Data Produk (Delete)
    public boolean hapusProduk(int id) {
        Connection conn = koneksi.getKoneksi();
        // SINKRON: Menggunakan nama tabel 'produk'
        String sql = "DELETE FROM produk WHERE id_produk = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal Hapus Data:\n" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
