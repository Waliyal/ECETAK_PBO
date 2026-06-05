
package halaman_utama;

import kelola_produk.ProdukModel;
import kelola_produk.ProdukViewAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController {
    private MenuViewAdmin view;
    private String namaAdmin;

    public MenuController(MenuViewAdmin view, String namaAdmin) {
        this.view = view;
        this.namaAdmin = namaAdmin;

        this.view.setVisible(true);
        this.view.setLocationRelativeTo(null);

        // ====================================================================
        // LOGIKA KETIKA MENU ITEM "PRODUK" DIKLIK
        // ====================================================================
        this.view.getMenuProduk().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose(); // Tutup Menu Utama Admin
                
                // Hubungkan paket MVC Kelola Produk
                ProdukViewAdmin pView = new ProdukViewAdmin();
                ProdukModel pModel = new ProdukModel();
                
                
                pView.setVisible(true);
                pView.setLocationRelativeTo(null);
            }
        });
        // ====================================================================
    }
}
