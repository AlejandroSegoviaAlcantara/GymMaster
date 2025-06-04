package com.gymmaster.dao;

import com.gymmaster.models.Compra;
import com.gymmaster.models.DetalleCompra;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DetalleCompraDaoImp implements DetalleCompraDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Compra> getCompras() {
        String query = "FROM DetalleCompra";
        return entityManager.createQuery(query).getResultList();
    }
    @Override
    public void carrito(Compra compra) {
        entityManager.persist(compra);
    }
    @Override
    public void eliminar(Long id){
        DetalleCompra detalleCompra = entityManager.find(DetalleCompra.class, id);
        entityManager.remove(detalleCompra);
    }

}
