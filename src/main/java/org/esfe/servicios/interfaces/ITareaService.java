package org.esfe.servicios.interfaces;

import org.esfe.modelos.Tareas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITareaService {

    Page<Tareas> buscarTodosPaginados(Pageable pageable);

    List<Tareas> obtenerTodos();

    Optional<Tareas> buscarPorId(Integer id);

    Tareas crearOEditar(Tareas tareas);

    void eliminarPorId(Integer id);

    List<Tareas> buscarTodos();

}