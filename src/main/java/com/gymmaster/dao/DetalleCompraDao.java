package com.gymmaster.dao;

import com.gymmaster.models.Compras;

import java.util.List;

public interface DetalleCompraDao {

    List<Compras> getCompras();

    void eliminar(Long id);

    void carrito(Compras compras);

}
