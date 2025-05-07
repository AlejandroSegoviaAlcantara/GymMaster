package com.gymmaster.controllers;

import com.gymmaster.dao.UsuarioDao;
import com.gymmaster.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;


    @RequestMapping(value="api/usuarios", method = RequestMethod.GET)
    public List <Usuario> getUsuarios() {
        return usuarioDao.getUsuarios();

    }

    @RequestMapping(value="usuario5")
    public Usuario editar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Alejandro");
        usuario.setApellido("Segovia");
        usuario.setEmail("alejandrosegovialcantara@gmail.com");
        usuario.setTipo("5555555555");
        return usuario;
    }


    @RequestMapping(value="api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@PathVariable Long id) {
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value="usuario2")
    public Usuario buscar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Alejandro");
        usuario.setApellido("Segovia");
        usuario.setEmail("alejandrosegovialcantara@gmail.com");
        usuario.setTipo("5555555555");
        return usuario;
    }

}
