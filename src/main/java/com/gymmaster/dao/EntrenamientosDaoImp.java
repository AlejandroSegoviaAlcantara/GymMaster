package com.gymmaster.dao;

import com.gymmaster.models.Entrenamientos;
import com.gymmaster.models.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional

public class EntrenamientosDaoImp implements EntrenamientosDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Entrenamientos> getEntrenamientos() {
        String query = "FROM Entrenamientos ";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Entrenamientos entrenamientos = entityManager.find(Entrenamientos.class, id);
        entityManager.remove(entrenamientos);
    }

    @Override
    public void registrar(Entrenamientos entrenamientos) {
        entityManager.merge(entrenamientos);
    }

    @Override
    public Entrenamientos obtenerPorId(Long id) {
        return entityManager.find(Entrenamientos.class, id);
    }

    @Override
    public void actualizar(Entrenamientos entrenamiento) {
        entityManager.merge(entrenamiento);
    }

    @Override
    public List<Entrenamientos> getEntrenamientosPorUsuario(Long idUsuario) {
        String query = "FROM Entrenamientos WHERE idUsuario = :idUsuario";
        return entityManager.createQuery(query, Entrenamientos.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
    }
}
