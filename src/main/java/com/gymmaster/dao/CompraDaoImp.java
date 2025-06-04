package com.gymmaster.dao;

import com.gymmaster.models.Compra;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CompraDaoImp implements CompraDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void guardar(Compra compra) {
        entityManager.persist(compra);
    }

    @Override
    public List<Compra> getComprasPorUsuario(Long idUsuario) {
        String query = "SELECT c FROM Compra c LEFT JOIN FETCH c.productos WHERE c.idUsuario = :idUsuario ORDER BY c.fecha DESC";
        return entityManager.createQuery(query, Compra.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
    }

}
