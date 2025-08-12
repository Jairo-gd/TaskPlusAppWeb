package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Tareas;
import org.esfe.repositorios.ITareaRepository;
import org.esfe.servicios.interfaces.ITareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService implements ITareaService {
    @Autowired
    private ITareaRepository tareaRepository;

    @Override
    public Page<Tareas> buscarTodosPaginados(Pageable pageable) {
        return tareaRepository.findAll(pageable);
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
