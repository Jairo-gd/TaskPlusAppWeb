package org.esfe.servicios.interfaces;

import org.esfe.modelos.Categorias;
import org.esfe.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {

    Page<Categorias> buscarTodosPaginados(Pageable pageable);

    List<Categorias> obtenerTodos(); // 👈 Este queda

    Optional<Categorias> buscarPorId(Integer id);

    Categorias crearOEditar(Categorias categoria);

    void eliminarPorId(Integer id);

    // 🔹 Método clave para filtrar por usuario
    List<Categorias> obtenerPorUsuario(Usuario usuario);
}
