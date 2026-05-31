/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absenrfid.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mycompany.absenrfid.util.MongoManager;
import com.mycompany.absenrfid.util.SecurityUtils;
import com.mycompany.absenrfid.view.Login;
import com.mycompany.absenrfid.view.Monitoring;
import javax.swing.JOptionPane;
import org.bson.Document;

/**
 *
 * @author user
 */
public class AuthService {
    public void login(String username, String plainPassword, Login loginPage) {
 
        // 1. Hash password mentah menggunakan SHA-256 sebelum dicek ke database
        //    Sehingga password "123" → "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"
        String hashedInput = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);
 
        // 2. Konek ke koleksi "Admin" di database "absensi"
        MongoCollection<Document> col = MongoManager.getDatabase().getCollection("Admin");
 
        // 3. Cari dokumen yang cocok: username DAN password hash harus sama
        Document user = col.find(
            Filters.and(
                Filters.eq("username", username),
                Filters.eq("password", hashedInput)
            )
        ).first();
 
        // 4. Validasi hasil pencarian
        if (user != null) {
            String namaAdmin = user.getString("nama_lengkap");
            JOptionPane.showMessageDialog(null, "Selamat Datang, " + namaAdmin + "!");
 
            // Buka halaman Monitoring
            new Monitoring().setVisible(true);
            loginPage.dispose();
        } else {
            JOptionPane.showMessageDialog(null,
                "Username atau Password Salah!",
                "Login Gagal",
                JOptionPane.ERROR_MESSAGE);
        }
    }
 
    /**
     * Mendaftarkan admin baru ke koleksi "Admin" dengan password yang sudah di-hash.
     * Berguna untuk setup awal atau tambah admin baru.
     *
     * @param namaLengkap nama admin
     * @param username    username untuk login
     * @param plainPassword password mentah (akan di-hash otomatis)
     */
    public void registerAdmin(String namaLengkap, String username, String plainPassword) {
        // Hash password sebelum disimpan
        String hashedPassword = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);
 
        Document adminBaru = new Document("username", username)
                .append("password", hashedPassword)
                .append("nama_lengkap", namaLengkap)
                .append("bahasa_sistem", "Bahasa Indonesia");
 
        try {
            MongoManager.getDatabase().getCollection("Admin").insertOne(adminBaru);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mendaftarkan admin: " + e.getMessage());
        }
    }
}
