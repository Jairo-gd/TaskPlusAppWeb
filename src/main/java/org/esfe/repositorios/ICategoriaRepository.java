package org.esfe.repositorios;

import org.esfe.modelos.Categorias;
import org.esfe.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoriaRepository extends JpaRepository<Categorias, Integer> {
    List<Categorias> findByUsuario(Usuario usuario);
}
