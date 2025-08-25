package org.esfe.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    // Relación con Usuario (MUCHAS categorías pertenecen a UN usuario)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // columna en la tabla categorias
    private Usuario usuario;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tareas> tareas = new ArrayList<>();

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<Tareas> getTareas() { return tareas; }
    public void setTareas(List<Tareas> tareas) { this.tareas = tareas; }

    @Override
    public String toString() {
        return nombre;
    }
}
