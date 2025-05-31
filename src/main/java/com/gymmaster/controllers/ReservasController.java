package com.gymmaster.controllers;

import com.gymmaster.dao.ReservasDao;
import com.gymmaster.models.Reservas;
import com.gymmaster.models.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/usuario/reservas")
public class ReservasController {

    @Autowired
    private ReservasDao reservasDao;

    @GetMapping
    public List<Reservas> obtenerReservasUsuario(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return reservasDao.getReservasPorUsuario(usuario.getId());
    }

    @PostMapping("/api/usuario/reservas")
    public ResponseEntity<?> crearReserva(@RequestBody Reservas reserva, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Combinar fecha y hora a un DateTime
        LocalDate fecha = reserva.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime hora = LocalTime.parse(reserva.getHora());
        LocalDateTime fechaHoraReserva = LocalDateTime.of(fecha, hora);

        if (fechaHoraReserva.isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No puedes reservar en una fecha pasada.");
        }

        // Validación de conflicto
        if (reservasDao.existeReserva(usuario.getId(), reserva.getFecha(), reserva.getHora())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya tienes una reserva en ese horario.");
        }

        reserva.setIdUsuario(usuario.getId());
        reservasDao.registrar(reserva);
        return ResponseEntity.ok("Reserva registrada");
    }


    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable Long id) {
        reservasDao.eliminar(id);
    }
}
