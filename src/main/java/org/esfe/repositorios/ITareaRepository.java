package org.esfe.repositorios;

import org.esfe.modelos.Tareas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITareaRepository extends JpaRepository<Tareas, Integer> {
}
