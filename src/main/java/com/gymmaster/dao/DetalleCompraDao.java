package com.gymmaster.dao;

import com.gymmaster.models.Compra;

import java.util.List;

public interface DetalleCompraDao {

    List<Compra> getCompras();

    void eliminar(Long id);

    void carrito(Compra compras);

}
