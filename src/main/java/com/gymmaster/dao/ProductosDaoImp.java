package com.gymmaster.dao;

import com.gymmaster.models.Productos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductosDaoImp implements ProductosDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Productos> getProductos() {
        return entityManager.createQuery("FROM Productos", Productos.class).getResultList();
    }
}
