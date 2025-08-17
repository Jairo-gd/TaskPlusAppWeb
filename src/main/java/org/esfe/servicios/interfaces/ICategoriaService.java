package org.esfe.servicios.interfaces;

import org.esfe.modelos.Categorias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {

    /**
     * Obtiene todas las categorías paginadas.
     *
     * @param pageable Objeto de paginación
     * @return Página de categorías
     */
    Page<Categorias> buscarTodosPaginados(Pageable pageable);

    /**
     * Obtiene todas las categorías sin paginación.
     *
     * @return Lista de categorías
     */
    List<Categorias> obtenerTodos();

    /**
     * Busca una categoría por su ID.
     *
     * @param id Identificador de la categoría
     * @return Optional con la categoría si existe
     */
    Optional<Categorias> buscarPorId(Integer id);

    /**
     * Crea o edita una categoría.
     *
     * @param categoria Objeto Categorias a guardar
     * @return Categorias guardada
     */
    Categorias crearOEditar(Categorias categoria);

    /**
     * Elimina una categoría por su ID.
     *
     * @param id Identificador de la categoría a eliminar
     */
    void eliminarPorId(Integer id);

    /**
     * Busca todas las categorías (alias de obtenerTodos, útil para flexibilidad).
     *
     * @return Lista de categorías
     */
    List<Categorias> buscarTodos();
}
