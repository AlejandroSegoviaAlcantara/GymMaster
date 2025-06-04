package com.gymmaster.dao;

import com.gymmaster.models.Pista;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class PistaDaoImp implements PistaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pista> getTodas() {
        String query = "FROM Pista";
        return entityManager.createQuery(query, Pista.class).getResultList();
    }

    @Override
    public void guardar(Pista pista) {
        entityManager.persist(pista);
    }

    @Override
    public void eliminar(Long id) {
        Pista pista = entityManager.find(Pista.class, id);
        if (pista != null) entityManager.remove(pista);
    }
}
