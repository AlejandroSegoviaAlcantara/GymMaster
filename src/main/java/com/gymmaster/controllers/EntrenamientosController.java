package com.gymmaster.controllers;

import com.gymmaster.dao.EntrenamientosDao;
import com.gymmaster.models.Entrenamientos;
import com.gymmaster.models.Usuario;
import com.gymmaster.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/usuario/entrenamientos")
public class EntrenamientosController {

    @Autowired
    private EntrenamientosDao entrenamientosDao;

    @Autowired
    private JwtUtil jwtUtil;

    private Long extraerIdUsuario(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no válido");
        }
        String token = authHeader.substring(7);
        return jwtUtil.getIdUsuario(token);
    }

    @GetMapping
    public List<Entrenamientos> getEntrenamientosPorUsuario(@RequestHeader("Authorization") String token) {
        Long idUsuario = extraerIdUsuario(token);
        return entrenamientosDao.getEntrenamientosPorUsuario(idUsuario);
    }

    @PostMapping
    public ResponseEntity<?> crearEntrenamiento(@RequestHeader("Authorization") String token,
                                                @RequestBody Entrenamientos entrenamiento) {
        Long idUsuario = extraerIdUsuario(token);
        entrenamiento.setIdUsuario(idUsuario);
        entrenamientosDao.registrar(entrenamiento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEntrenamiento(@PathVariable Long id,
                                                     @RequestBody Entrenamientos entrenamiento,
                                                     @RequestHeader("Authorization") String token) {
        Long idUsuario = extraerIdUsuario(token);
        Entrenamientos actual = entrenamientosDao.obtenerPorId(id);
        if (actual == null || !actual.getIdUsuario().equals(idUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        actual.setDia(entrenamiento.getDia());
        actual.setEjercicio(entrenamiento.getEjercicio());
        actual.setRepeticiones(entrenamiento.getRepeticiones());
        actual.setPeso(entrenamiento.getPeso());

        entrenamientosDao.actualizar(actual);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id,
                                      @RequestHeader("Authorization") String token) {
        Long idUsuario = extraerIdUsuario(token);
        Entrenamientos entrenamiento = entrenamientosDao.obtenerPorId(id);
        if (entrenamiento == null || !entrenamiento.getIdUsuario().equals(idUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        entrenamientosDao.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
