/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absenrfid.services;

import com.mycompany.absenrfid.dao.GenericDAO;
import com.mycompany.absenrfid.objects.LogAbsensi;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author FAIZAL ISMAN
 */
public class LogAbsensiService {

private final GenericDAO<LogAbsensi> logDAO = new GenericDAO<>("LogAbsensi", LogAbsensi.class);

    public void simpanLog(String uid, String nama, String nim, String kelas, String status) {
        LogAbsensi log = new LogAbsensi();
        log.setWaktu(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        log.setUid_rfid(uid);
        log.setNama(nama);
        log.setNim(nim);
        log.setKelas(kelas);
        log.setStatus(status);
        logDAO.save(log);
    }
        
}