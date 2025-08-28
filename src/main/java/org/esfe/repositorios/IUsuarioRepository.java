package org.esfe.repositorios;

import org.esfe.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    // ✅ Ahora devuelve lista en lugar de un único usuario
    List<Usuario> findByEmail(String email);
}
