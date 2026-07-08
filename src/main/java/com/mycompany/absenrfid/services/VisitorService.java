package com.mycompany.absenrfid.services;

import com.mycompany.absenrfid.dao.GenericDAO;
import com.mycompany.absenrfid.objects.Visitor;
import com.mongodb.client.model.Filters;
import java.util.List;
import org.bson.conversions.Bson;

/**
 * 
 * @author User
 */
public class VisitorService {
    private final GenericDAO<Visitor> dao = new GenericDAO<>("Visitors", Visitor.class);

    public void tambah(Visitor v) {
    String hashedUID = com.mycompany.absenrfid.util.SecurityUtils
        .getHash(v.getUid_rfid(), com.mycompany.absenrfid.util.SecurityUtils.SHA_256);
    v.setUid_rfid(hashedUID);
    dao.save(v);
    }

    public void update(String nim, Visitor v) {
    String hashedUID = com.mycompany.absenrfid.util.SecurityUtils
        .getHash(v.getUid_rfid(), com.mycompany.absenrfid.util.SecurityUtils.SHA_256);
    v.setUid_rfid(hashedUID);
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
    
    /** Cari visitor berdasarkan UID yang sudah di-hash SHA-256 */
    public Visitor cariByUidHash(String hashedUid) {
        return dao.findOne(Filters.eq("uid_rfid", hashedUid));
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
