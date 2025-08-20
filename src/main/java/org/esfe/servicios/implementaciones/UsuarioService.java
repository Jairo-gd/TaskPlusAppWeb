package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Usuario;
import org.esfe.repositorios.IUsuarioRepository;
import org.esfe.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Usuario registrar(Usuario usuario) {
        // Guardar el usuario en la BD
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario login(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}