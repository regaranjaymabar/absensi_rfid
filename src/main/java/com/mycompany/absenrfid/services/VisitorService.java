package com.mycompany.absenrfid.services;

import com.mycompany.absenrfid.dao.GenericDAO;
import com.mycompany.absenrfid.objects.Visitor;
import com.mongodb.client.model.Filters;
import java.util.List;
import org.bson.conversions.Bson;
import com.mycompany.absenrfid.util.EncryptionUtils;

/**
 * VisitorService - logika bisnis untuk data Visitor
 * Mengikuti pola KaryawanService milik dosen
 * @author NEXA
 */
public class VisitorService {
    private final GenericDAO<Visitor> dao = new GenericDAO<>("Visitors", Visitor.class);

    public void tambah(Visitor v) {
    String encryptedUID = EncryptionUtils.encrypt(v.getUid_rfid());
    v.setUid_rfid(encryptedUID);
    dao.save(v);
    }

    public void update(String nim, Visitor v) {
    // Enkripsi UID baru sebelum disimpan
    String encryptedUID = EncryptionUtils.encrypt(v.getUid_rfid());
    v.setUid_rfid(encryptedUID);
    dao.update(Filters.eq("nim", nim), v);
    }

    public void hapus(String nim) {
        dao.delete(Filters.eq("nim", nim));
    }

    public List<Visitor> getAll() {
        return dao.findAll();
    }

    public Visitor cariByNim(String nim) {
        return dao.findOne(Filters.eq("nim", nim));
    }
    
    public Visitor cariByUidEncrypted(String encryptedUid) {
    return dao.findOne(Filters.eq("uid_rfid", encryptedUid));
    }

    public List<Visitor> cari(String keyword) {
        Bson filter = Filters.or(
            Filters.regex("nama",  keyword, "i"),
            Filters.regex("nim",   keyword, "i"),
            Filters.regex("kelas", keyword, "i")
        );
        return dao.findMany(filter);
    }
}