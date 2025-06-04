package com.gymmaster.dao;

import com.gymmaster.models.Compra;

import java.util.List;

public interface CompraDao {

    void guardar(Compra compra);

    List<Compra> getComprasPorUsuario(Long idUsuario);

}
