package com.gymmaster.dao;

import com.gymmaster.models.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> getUsuarios();

    void eliminar(Long id);
}
