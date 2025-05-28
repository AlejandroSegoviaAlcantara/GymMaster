package com.gymmaster.controllers;

import com.gymmaster.dao.ComprasDao;
import com.gymmaster.dao.UsuarioDao;
import com.gymmaster.models.Compras;
import com.gymmaster.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class ComprasController {

    @Autowired
    private ComprasDao comprasDao;

    @RequestMapping(value="api/compras", method = RequestMethod.GET)
    public List <Compras> getCompras() {
        return comprasDao.getCompras();
    }

    @RequestMapping(value="api/compras", method = RequestMethod.POST)
    public void carrito(@RequestBody Compras compras) {
        comprasDao.carrito(compras);
    }

    @RequestMapping(value="api/compras/{id}", method = RequestMethod.DELETE)
    public void eliminar(@PathVariable Long id) {
        comprasDao.eliminar(id);
    }

}
