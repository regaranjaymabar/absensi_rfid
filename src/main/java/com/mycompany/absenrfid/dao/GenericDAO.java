package com.mycompany.absenrfid.dao;

import com.mongodb.client.MongoCollection;
import com.mycompany.absenrfid.util.MongoManager;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;

/**
 * GenericDAO - implementasi operasi CRUD ke MongoDB
 * Mengikuti pola yang sama dengan punya dosen (KaryawanService)
 * @author user
 */
public class GenericDAO<T> implements BaseDAO<T> {
    private final MongoCollection<T> collection;
    private final Class<T> clazz;

    public GenericDAO(String collectionName, Class<T> clazz) {
        this.clazz = clazz;
        this.collection = MongoManager.getDatabase().getCollection(collectionName, clazz);
    }

    @Override
    public void save(T entity) {
        collection.insertOne(entity);
    }

    @Override
    public void update(Bson filter, T entity) {
        collection.replaceOne(filter, entity);
    }

    @Override
    public void delete(Bson filter) {
        collection.deleteOne(filter);
    }

    @Override
    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public T findOne(Bson filter) {
        return collection.find(filter).first();
    }

    @Override
    public List<T> findMany(Bson filter) {
        return collection.find(filter).into(new ArrayList<>());
    }

    @Override
    public long countAll() {
        return collection.countDocuments();
    }
}
