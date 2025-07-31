package org.esfe.servicios.interfaces;

import org.esfe.modelos.Categorias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    Page<Categorias> buscarTodosPaginados(Pageable pageable);

    List<Categorias> obtenerTodos();

    Optional<Categorias> buscarPorId(Integer id);

    Categorias crearOEditar(Categorias categorias);

    void eliminarPorId(Integer grupo);
}
