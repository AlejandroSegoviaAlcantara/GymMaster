package com.gymmaster.dao;

import com.gymmaster.models.Entrenamientos;

import java.util.List;

public interface EntrenamientosDao {

    List<Entrenamientos> getEntrenamientos();

    void eliminar(Long id);

    void registrar(Entrenamientos entrenamientos);

    List<Entrenamientos> getEntrenamientosPorUsuario(Long idUsuario);

    Entrenamientos obtenerPorId(Long id);

    void actualizar(Entrenamientos entrenamiento);

}
