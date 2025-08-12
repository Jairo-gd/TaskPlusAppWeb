package org.esfe.servicios.interfaces;


import org.esfe.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IUsuarioService {
    Usuario registrar(Usuario usuario);

    Usuario login(String email, String password);

    Usuario obtenerPorEmail(String email);
}

