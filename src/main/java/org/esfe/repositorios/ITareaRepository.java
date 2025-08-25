package org.esfe.repositorios;

import org.esfe.modelos.Tareas;
import org.esfe.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITareaRepository extends JpaRepository<Tareas, Integer> {

    // Buscar por ID
    @Override
    Optional<Tareas> findById(Integer id);

    // Eliminar por ID
    @Override
    void deleteById(Integer id);

    // Obtener el ID máximo para asignar el siguiente ID (opcional)
    @Query("SELECT COALESCE(MAX(t.id), 0) FROM Tareas t")
    Integer findMaxId();

    // Reiniciar AUTO_INCREMENT de la tabla (para MySQL/MariaDB)
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE tareas AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    // 🔥 Nuevo: obtener tareas por usuario
    List<Tareas> findByUsuario(Usuario usuario);
}
