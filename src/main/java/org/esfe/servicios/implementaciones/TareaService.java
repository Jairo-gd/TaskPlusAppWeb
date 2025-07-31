package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Tareas;
import org.esfe.servicios.interfaces.ITareaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class TareaService implements ITareaService {
    @Override
    public Page<Tareas> buscarTodosPaginados(Pageable pageable) {
        return null;
    }

    @Override
    public List<Tareas> obtenerTodos() {
        return List.of();
    }

    @Override
    public Optional<Tareas> buscarPorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public Tareas crearOEditar(Tareas tareas) {
        return null;
    }

    @Override
    public void eliminarPorId(Integer grupo) {

    }
}
