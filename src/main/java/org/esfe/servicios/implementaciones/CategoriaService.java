package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Categorias;
import org.esfe.modelos.Usuario;
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
        return categoriaRepository.findAll(); // ✅ Ahora trae todas las categorías
    }

    @Override
    public List<Categorias> obtenerPorUsuario(Usuario usuario) {
        return categoriaRepository.findByUsuario(usuario); // ✅ Método filtrado por usuario
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
        return categoriaRepository.findAll();
    }
}
