package org.esfe.repositorios;

import org.esfe.modelos.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Categorias, Integer> {
}