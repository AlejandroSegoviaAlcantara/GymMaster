package com.gymmaster.controllers;

import com.gymmaster.dao.EntrenamientosDao;
import com.gymmaster.dao.UsuarioDao;
import com.gymmaster.models.Entrenamientos;
import com.gymmaster.models.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/api/usuario/entrenamientos")
    public void crearEntrenamiento(@RequestBody Entrenamientos entrenamiento, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        entrenamiento.setIdUsuario(usuario.getId());
        entrenamientosDao.registrar(entrenamiento);
    }

    @PutMapping("/api/usuario/entrenamientos/{id}")
    public void actualizarEntrenamiento(@PathVariable Long id, @RequestBody Entrenamientos entrenamiento, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // Asegúrate de que el entrenamiento pertenece al usuario
        Entrenamientos actual = entrenamientosDao.obtenerPorId(id);
        if (actual == null || !actual.getIdUsuario().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        actual.setDia(entrenamiento.getDia());
        actual.setEjercicio(entrenamiento.getEjercicio());
        actual.setRepeticiones(entrenamiento.getRepeticiones());
        actual.setPeso(entrenamiento.getPeso());

        entrenamientosDao.actualizar(actual);
    }

    @RequestMapping(value="api/entrenamientos/{id}", method = RequestMethod.DELETE)
    public void eliminar(@PathVariable Long id) {
        entrenamientosDao.eliminar(id);
    }

    @GetMapping("/api/usuario/entrenamientos")
    public List<Entrenamientos> getEntrenamientosPorUsuario(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return entrenamientosDao.getEntrenamientosPorUsuario(usuario.getId());
    }



}
