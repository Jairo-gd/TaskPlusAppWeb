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
        return tareaRepository.findAll();
    }

    @Override
    public Optional<Tareas> buscarPorId(Integer id) {
        return tareaRepository.findById(id);
    }

    @Override
    public Tareas crearOEditar(Tareas tareas) {
        return tareaRepository.save(tareas);
    }

    @Override
    public void eliminarPorId(Integer id) {
        tareaRepository.deleteById(id);
    }

    @Override
    public List<Tareas> buscarTodos() {
        return tareaRepository.findAll();
    }
}