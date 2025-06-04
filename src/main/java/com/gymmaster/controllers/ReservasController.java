package com.gymmaster.controllers;

import com.gymmaster.dao.ReservasDao;
import com.gymmaster.models.Reservas;
import com.gymmaster.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/usuario/reservas")
public class ReservasController {

    @Autowired
    private ReservasDao reservasDao;

    @Autowired
    private JwtUtil jwtUtil;

    // Obtener reservas del usuario autenticado
    @GetMapping
    public List<Reservas> obtenerReservasUsuario(@RequestHeader("Authorization") String authHeader) {
        Long idUsuario = obtenerIdUsuarioDesdeToken(authHeader);
        return reservasDao.getReservasPorUsuario(idUsuario);
    }

    // Crear una nueva reserva
    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestHeader("Authorization") String authHeader,
                                          @RequestBody Reservas reserva) {
        Long idUsuario = obtenerIdUsuarioDesdeToken(authHeader);

        if (reservasDao.existeReserva(idUsuario, reserva.getFecha(), reserva.getHora())) {
            return ResponseEntity.badRequest().body("Ya tienes una reserva en ese horario.");
        }

        reserva.setIdUsuario(idUsuario);
        reservasDao.registrar(reserva);

        return ResponseEntity.ok("Reserva creada con éxito");
    }

    // Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id,
                                             @RequestHeader("Authorization") String authHeader) {
        Long idUsuario = obtenerIdUsuarioDesdeToken(authHeader);

        List<Reservas> reservas = reservasDao.getReservasPorUsuario(idUsuario);
        boolean pertenece = reservas.stream().anyMatch(r -> r.getIdReserva().equals(id));

        if (!pertenece) {
            return ResponseEntity.status(403).body("No tienes permiso para eliminar esta reserva.");
        }

        reservasDao.eliminar(id);
        return ResponseEntity.ok("Reserva eliminada");
    }

    // 🔐 Método auxiliar
    private Long obtenerIdUsuarioDesdeToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido o ausente");
        }
        String token = authHeader.replace("Bearer ", "");
        return jwtUtil.getIdUsuario(token);
    }
}
