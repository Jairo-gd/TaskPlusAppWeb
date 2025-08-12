package org.esfe.repositorios;

import org.esfe.modelos.Tareas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Tareas, Integer> {

}

