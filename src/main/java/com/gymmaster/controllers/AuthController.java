package com.gymmaster.controllers;

import com.gymmaster.dao.UsuarioDao;
import com.gymmaster.models.Usuario;
import com.gymmaster.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario usuarioAutenticado = usuarioDao.verificarCredenciales(usuario);

        if (usuarioAutenticado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        // Genera token usando ID y Rol
        String token = JwtUtil.generarToken(usuarioAutenticado.getId(), usuarioAutenticado.getRol());

        // Devuelve el token y datos necesarios
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("nombre", usuarioAutenticado.getNombre());
        response.put("apellido", usuarioAutenticado.getApellido());
        response.put("rol", usuarioAutenticado.getRol());

        return ResponseEntity.ok(response);
    }

    // Obtener usuario autenticado desde el token
    @GetMapping("/usuario/sesion")
    public ResponseEntity<Usuario> getUsuarioDesdeToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.replace("Bearer ", "");

        if (!JwtUtil.esTokenValido(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Long userId = JwtUtil.getIdUsuario(token);

        Usuario usuario = usuarioDao.getUsuario(userId);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(usuario);
    }
}
