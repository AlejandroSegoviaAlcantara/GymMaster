package com.gymmaster.controllers;

import com.gymmaster.dao.CompraDao;
import com.gymmaster.dao.UsuarioDao;
import com.gymmaster.models.Compra;
import com.gymmaster.models.CompraProducto;
import com.gymmaster.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraDao compraDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> guardarCompra(@RequestBody Compra compra, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token no válido");
        }

        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtUtil.getIdUsuario(token);

        compra.setIdUsuario(userId);
        compra.setFecha(new Date());

        // Calcular total y vincular productos
        double total = 0;
        for (CompraProducto cp : compra.getProductos()) {
            cp.setCompra(compra); // vinculación bidireccional
            total += cp.getPrecio() * cp.getCantidad();
        }

        compra.setTotal(total);

        compraDao.guardar(compra);

        return ResponseEntity.ok("Compra guardada correctamente");
    }
    @GetMapping("/api/compras")
    public ResponseEntity<?> obtenerComprasUsuario(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token no proporcionado");
        }

        String token = authHeader.replace("Bearer ", "");
        if (!JwtUtil.esTokenValido(token)) {
            return ResponseEntity.status(401).body("Token inválido");
        }

        Long idUsuario = JwtUtil.getIdUsuario(token);
        List<Compra> compras = compraDao.getComprasPorUsuario(idUsuario);

        return ResponseEntity.ok(compras);
    }

    @GetMapping
    public ResponseEntity<List<Compra>> obtenerMisCompras(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtUtil.getIdUsuario(token);

        List<Compra> compras = compraDao.getComprasPorUsuario(userId);
        return ResponseEntity.ok(compras);
    }
}
