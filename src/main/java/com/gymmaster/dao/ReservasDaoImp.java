package com.gymmaster.dao;

import com.gymmaster.models.Reservas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ReservasDaoImp implements ReservasDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void registrar(Reservas reserva) {
        entityManager.persist(reserva);
    }

    @Override
    public void eliminar(Long id) {
        Reservas reserva = entityManager.find(Reservas.class, id);
        if (reserva != null) {
            entityManager.remove(reserva);
        }
    }

    @Override
    public List<Reservas> getReservasPorUsuario(Long idUsuario) {
        String query = "FROM Reservas WHERE idUsuario = :idUsuario";
        return entityManager.createQuery(query, Reservas.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
    }

    @Override
    public boolean existeReserva(Long idUsuario, Date fecha, String hora) {
        String query = "SELECT COUNT(r) FROM Reservas r WHERE r.idUsuario = :idUsuario AND r.fecha = :fecha AND r.hora = :hora";
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("idUsuario", idUsuario)
                .setParameter("fecha", fecha)
                .setParameter("hora", hora)
                .getSingleResult();
        return count > 0;
    }
}
