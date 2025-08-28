package org.esfe.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "tareas")
public class Tareas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "categoriaid")
    private Categorias categoria;

    private Byte status; // 1 = Completada, 2 = Pendiente

    // ✅ Nuevo campo para evitar el error
    @Column(nullable = false)
    private boolean completada = false;

    // 🔑 Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Categorias getCategoria() { return categoria; }
    public void setCategoria(Categorias categoria) { this.categoria = categoria; }

    public Byte getStatus() { return status; }
    public void setStatus(Byte status) { this.status = status; }

    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    // Getter adicional para mostrar el estado como texto
    public String getStrStatus() {
        if (status == null) return "Desconocido";
        return status == 1 ? "Completada" : "Pendiente";
    }
}
