package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Usuario;
import org.esfe.repositorios.IUsuarioRepository;
import org.esfe.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<Usuario> usuarios = usuarioRepository.findByEmail(email);

        if (usuarios != null && !usuarios.isEmpty()) {
            // ✅ Busca el primero que coincida con el password
            return usuarios.stream()
                    .filter(u -> u.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public Usuario obtenerPorEmail(String email) {
        List<Usuario> usuarios = usuarioRepository.findByEmail(email);
        // ✅ Devuelvo el primero encontrado, si hay varios
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }
}
