package com.gymmaster.controllers;

import com.gymmaster.dao.PistaDao;
import com.gymmaster.models.Pista;
import com.gymmaster.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pistas")
public class PistaController {

    @Autowired
    private PistaDao pistaDao;

    @GetMapping
    public List<Pista> getTodas() {
        return pistaDao.getTodas();
    }

    @PostMapping
    public ResponseEntity<String> guardar(@RequestBody Pista pista) {
        pistaDao.guardar(pista);
        return ResponseEntity.ok("Clase/pista guardada correctamente");
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pistaDao.eliminar(id);
    }
}
