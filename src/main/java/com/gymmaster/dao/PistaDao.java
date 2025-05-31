package com.gymmaster.dao;

import com.gymmaster.models.Pista;

import java.util.List;

public interface PistaDao {

    List<Pista> getTodas();

    void guardar(Pista pista);

    void eliminar(Long id);
}
