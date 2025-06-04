package com.gymmaster.controllers;

import com.gymmaster.dao.UsuarioDao;
import com.gymmaster.models.Usuario;
import com.gymmaster.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/api/usuarios")
    public List<Usuario> getUsuarios() {
        return usuarioDao.getUsuarios();
    }

    @PostMapping("/api/usuarios")
    public void registrarUsuario(@RequestBody Usuario usuario) {
        usuarioDao.registrar(usuario);
    }

    @DeleteMapping("/api/usuarios/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioDao.eliminar(id);
    }

    @GetMapping("/api/usuarios/{id}")
    public Usuario getUsuario(@PathVariable Long id) {
        return usuarioDao.getUsuario(id);
    }

    // ✅ Obtener rol del usuario desde el token
    @GetMapping("/api/usuario/rol")
    public ResponseEntity<?> obtenerRolDesdeToken(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token no proporcionado o inválido");
        }

        String jwt = token.substring(7);
        Long idUsuario = jwtUtil.getIdUsuario(jwt);

        Usuario usuario = usuarioDao.getUsuario(idUsuario);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        return ResponseEntity.ok(usuario.getRol());
    }
}
