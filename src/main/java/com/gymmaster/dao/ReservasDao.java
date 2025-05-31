package com.gymmaster.dao;

import com.gymmaster.models.Reservas;

import java.util.Date;
import java.util.List;

public interface ReservasDao {

    void registrar(Reservas reserva);

    void eliminar(Long id);

    List<Reservas> getReservasPorUsuario(Long idUsuario);

    boolean existeReserva(Long idUsuario, Date fecha, String hora);

}
