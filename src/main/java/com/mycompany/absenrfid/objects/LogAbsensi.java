package com.mycompany.absenrfid.objects;

/**
 * Model untuk koleksi LogAbsensi di MongoDB
 * Field disesuaikan dengan nama field di database
 * @author user
 */
public class LogAbsensi {
    private String waktu;
    private String kelas;
    private String nama;
    private String nim;
    private String status;
    private String uid_rfid;

    public LogAbsensi() {}

    public String getWaktu() { return waktu; }
    public void setWaktu(String waktu) { this.waktu = waktu; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUid_rfid() { return uid_rfid; }
    public void setUid_rfid(String uid_rfid) { this.uid_rfid = uid_rfid; }
}