package kelola_pesanan;

import config.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class PesananModel {

    // 1. QUERY TAMPIL: Menampilkan data riwayat pesanan ke JTable
    public DefaultTableModel tampilkanDataPesanan() {
        DefaultTableModel modelTabel = new DefaultTableModel();
        modelTabel.addColumn("ID Pesanan");
        modelTabel.addColumn("Nama Pelanggan");
        modelTabel.addColumn("Produk Terpilih");
        modelTabel.addColumn("Jumlah");
        modelTabel.addColumn("Status");

        Connection conn = koneksi.getKoneksi();
        // SINKRON: orders.nm_pelanggan sesuai kolom terbaru Anda di MySQL
        String sql = "SELECT pesanan.id_pesanan, pesanan.nm_pelanggan, produk.nama_produk, pesanan.jumlah, pesanan.status " +
                     "FROM pesanan " +
                     "INNER JOIN produk ON pesanan.id_produk = produk.id_produk";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelTabel.addRow(new Object[]{
                    rs.getInt("id_pesanan"),
                    rs.getString("nm_pelanggan"), // Menarik data dari kolom nm_pelanggan
                    rs.getString("nama_produk"),
                    rs.getInt("jumlah"),
                    rs.getString("status")
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal memuat data pesanan:\n" + e.getMessage());
            e.printStackTrace();
        }
        return modelTabel;
    }

    // 2. QUERY TAMBAH / BELI: Langsung memasukkan ketikan namaUser ke kolom nm_pelanggan
    public boolean tambahPesanan(String namaUser, String namaProduk, int jumlah) {
        Connection conn = koneksi.getKoneksi();
        try {
            // Ambil id_produk berdasarkan nama produk yang dipilih di ComboBox
            int idProduk = 0;
            String sqlProduk = "SELECT id_produk FROM produk WHERE nama_produk = ?";
            PreparedStatement psProd = conn.prepareStatement(sqlProduk);
            psProd.setString(1, namaProduk);
            ResultSet rsProd = psProd.executeQuery();
            if (rsProd.next()) idProduk = rsProd.getInt("id_produk");
            rsProd.close();
            psProd.close();

            // SINKRON: Menggunakan kolom nm_pelanggan untuk menyimpan teks nama bebas
            String sqlOrder = "INSERT INTO pesanan (nm_pelanggan, id_produk, jumlah, status) VALUES (?, ?, ?, 'pending')";
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder);
            psOrder.setString(1, namaUser); 
            psOrder.setInt(2, idProduk);
            psOrder.setInt(3, jumlah);
            
            boolean hasil = psOrder.executeUpdate() > 0;
            psOrder.close();
            return hasil;
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal menambah data pesanan:\n" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 3. QUERY UPDATE STATUS: Untuk memproses transaksi (pending -> diproses -> selesai)
    public boolean updateStatusPesanan(int idPesanan, String statusBaru) {
        Connection conn = koneksi.getKoneksi();
        String sql = "UPDATE pesanan SET status = ? WHERE id_pesanan = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, statusBaru);
            ps.setInt(2, idPesanan);
            
            boolean hasil = ps.executeUpdate() > 0;
            ps.close();
            return hasil;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. QUERY HAPUS / BATAL: Untuk membatalkan/menghapus transaksi pesanan
    public boolean hapusPesanan(int idPesanan) {
        Connection conn = koneksi.getKoneksi();
        String sql = "DELETE FROM pesanan WHERE id_pesanan = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPesanan);
            
            boolean hasil = ps.executeUpdate() > 0;
            ps.close();
            return hasil;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
