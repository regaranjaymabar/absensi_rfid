package com.mycompany.absenrfid.objects;

/**
 * Model untuk koleksi Visitors di MongoDB
 * Field disesuaikan dengan nama field di database
 * @author user
 */
public class Visitor {
    private String nama;
    private String nim;
    private String kelas;
    private String jurusan;
    private String uid_rfid;

    public Visitor() {}

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public String getJurusan() { return jurusan; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }

    public String getUid_rfid() { return uid_rfid; }
    public void setUid_rfid(String uid_rfid) { this.uid_rfid = uid_rfid; }
}
