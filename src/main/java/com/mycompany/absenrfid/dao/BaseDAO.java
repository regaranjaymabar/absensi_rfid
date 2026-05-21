package com.mycompany.absenrfid.dao;

import java.util.List;
import org.bson.conversions.Bson;

public interface BaseDAO<T> {
    // Operasi CRUD Dasar
    void save(T entity);
    void update(Bson filter, T entity);
    void delete(Bson filter);
    
    // Operasi Searching/Reading
    List<T> findAll();
    T findOne(Bson filter);
    List<T> findMany(Bson filter);
    
    // FIX: Tambah method countAll yang dibutuhkan LogAbsensiService
    long countAll();
}
