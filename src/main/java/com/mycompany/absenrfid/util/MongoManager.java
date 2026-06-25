package com.mycompany.absenrfid.util;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * @author FAIZAL ISMAN
 */
public class MongoManager {
     private static MongoClient mongoClient;
    private static final String DATABASE_NAME = "absensi"; 

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            // 1. Buat aturan agar Java Class (POJO) bisa otomatis dibaca MongoDB
            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );

            // 2. Masukkan aturan registry ke pengaturan client secara permanen
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .codecRegistry(pojoCodecRegistry)
                .build();

            // 3. Buka koneksi menggunakan settings yang sudah pintar membaca POJO
            mongoClient = MongoClients.create(settings);
        }
        
        // Sekarang, setiap kali database dipanggil, ia otomatis sudah mewarisi aturan POJO
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}