package halaman_login; // Sesuaikan dengan nama folder baru Anda tanpa angka

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import halaman_utama.MenuView;
import halaman_utama.MenuController;

public class LoginController {
    private LoginModel model;
    private LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;

        // Logika Tombol LOGIN
        this.view.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesLogin();
            }
        });

        // Logika Tombol BACK
        this.view.getBtnBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                System.out.println("Kembali ke menu awal.");
            }
        });
    }

    private void prosesLogin() {
        String uName = view.getUsernameInput();
        String pWord = view.getPasswordInput();
        String rUser = view.getRoleInput();

        if (uName.trim().isEmpty() || pWord.trim().isEmpty()) {
            view.tampilPesan("Username dan Password tidak boleh kosong!");
            return;
        }

        if (model.validasiLogin(uName, pWord, rUser)) {
            view.tampilPesan("Login Berhasil! Selamat datang " + uName);
            view.dispose(); 
            
            if (rUser.equals("admin")) {
                // Membuka halaman utama admin tanpa hambatan eror angka
                halaman_utama.MenuView menuView = new halaman_utama.MenuView();
                new halaman_utama.MenuController(menuView, uName);
            } else {
                view.tampilPesan("Akses khusus User disarankan menggunakan platform Website.");
            }
        } else {
            view.tampilPesan("Login Gagal! Username, Password, atau Role salah.");
        }
    }
}
