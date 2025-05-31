package com.gymmaster.controllers;

import com.gymmaster.dao.ProductosDao;
import com.gymmaster.models.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {

    @Autowired
    private ProductosDao productosDao;

    @GetMapping
    public List<Productos> listarProductos() {
        return productosDao.getProductos();
    }
}
