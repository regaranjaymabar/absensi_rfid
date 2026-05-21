package com.mycompany.absenrfid.util;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Nur Laela Suci Safitri
 */
public class TesKoneksi {
    public static void main(String[] args) {
        try {
            System.out.println("Sedang mencoba menghubungkan ke database...");
            
            // 1. Memanggil koneksi melalui MongoManager di package yang sama
            MongoDatabase database = MongoManager.getDatabase();
            
            // 2. Melakukan perintah "ping" untuk verifikasi koneksi ke server
            Document ping = new Document("ping", 1);
            database.runCommand(ping);
            
            System.out.println("=========================================");
            System.out.println("STATUS: KONEKSI BERHASIL!");
            System.out.println("Terhubung ke Database: " + database.getName());
            System.out.println("=========================================");
            
            // 3. Menampilkan daftar koleksi (Admin, LogAbsensi, Visitors)
            System.out.println("Daftar Koleksi di " + database.getName() + ":");
            for (String name : database.listCollectionNames()) {
                System.out.println("- " + name);
            }

        } catch (Exception e) {
            System.err.println("=========================================");
            System.err.println("STATUS: KONEKSI GAGAL!");
            System.err.println("Pesan Error: " + e.getMessage());
            System.err.println("Cek apakah MongoDB Compass / Service sudah menyala.");
            System.err.println("=========================================");
        }
    }
}