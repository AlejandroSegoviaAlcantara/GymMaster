package com.gymmaster.controllers;

import com.gymmaster.dao.ProductoDao;
import com.gymmaster.models.Producto;
import com.gymmaster.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<Producto>> getProductos(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.replace("Bearer ", "");

        if (!jwtUtil.esTokenValido(token)) {
            return ResponseEntity.status(401).build();
        }

        List<Producto> productos = productoDao.getTodos();
        return ResponseEntity.ok(productos);
    }
}
