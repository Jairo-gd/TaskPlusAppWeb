package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Usuario;
import org.esfe.repositorios.IUsuarioRepository;
import org.esfe.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

public abstract class UsuarioService extends Usuario implements IUsuarioService {
    private Map<String, UsuarioService> usuariosRegistrados = new HashMap<>();
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Usuario registrar(Usuario usuario) {
        return null;
    }

    @Override
    public Usuario login(String email, String password) {
        Usuario u = usuariosRegistrados.get(email);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    @Override
    public Usuario obtenerPorCorreo(String correo) {
        return usuariosRegistrados.get(correo);
    }
}


