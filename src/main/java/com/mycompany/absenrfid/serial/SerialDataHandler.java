package com.mycompany.absenrfid.serial;

/**
 * Interface untuk menangani data yang diterima dari port serial (RFID)
 * Mengikuti pola dosen: generic <T> agar fleksibel
 * @param <T>
 */
public interface SerialDataHandler<T> {
    void onDataReceived(T data);
}
