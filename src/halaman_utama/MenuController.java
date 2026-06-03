
package halaman_utama;

public class MenuController {
    private MenuView view;
    private String namaAdmin;

    // Konstruktor ini wajib menerima parameter MenuView dan Nama Admin
    public MenuController(MenuView view, String namaAdmin) {
        this.view = view;
        
        // Tampilkan halaman utama ke tengah layar
        this.view.setVisible(true);
        this.view.setLocationRelativeTo(null);
    }
}
