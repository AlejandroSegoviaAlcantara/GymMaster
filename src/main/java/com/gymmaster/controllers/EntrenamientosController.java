package com.gymmaster.controllers;

import com.gymmaster.dao.EntrenamientosDao;
import com.gymmaster.dao.UsuarioDao;
import com.gymmaster.models.Entrenamientos;
import com.gymmaster.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class EntrenamientosController {

    @Autowired
    private EntrenamientosDao entrenamientosDao;

    @RequestMapping(value="api/entrenamientos", method = RequestMethod.GET)
    public List <Entrenamientos> getEntrenamientos() {
        return entrenamientosDao.getEntrenamientos();
    }

    @RequestMapping(value="api/entrenamientos", method = RequestMethod.POST)
    public void registrar(@RequestBody Entrenamientos entrenamientos) {
        entrenamientosDao.registrar(entrenamientos);
    }

    @RequestMapping(value="api/entrenamientos/{id}", method = RequestMethod.DELETE)
    public void eliminar(@PathVariable Long id) {
        entrenamientosDao.eliminar(id);
    }

}
