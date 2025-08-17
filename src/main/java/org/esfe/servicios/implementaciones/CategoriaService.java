package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Categorias;
import org.esfe.repositorios.ICategoriaRepository;
import org.esfe.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Override
    public Page<Categorias> buscarTodosPaginados(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    public List<Categorias> obtenerTodos() {
        // Único método para obtener todas las categorías
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categorias> buscarPorId(Integer id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categorias crearOEditar(Categorias categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void eliminarPorId(Integer id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public List<Categorias> buscarTodos() {
        // Puedes decidir eliminarlo si no lo usas,
        // o simplemente devolver todas las categorías
        return categoriaRepository.findAll();
    }
}
