package com.gymmaster.dao;

import com.gymmaster.models.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ProductoDaoImp implements ProductoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Producto> getTodos() {
        return entityManager.createQuery("FROM Producto", Producto.class).getResultList();
    }
}
