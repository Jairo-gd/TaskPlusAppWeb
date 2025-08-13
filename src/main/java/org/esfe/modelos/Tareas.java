package org.esfe.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tareas")
public class Tareas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "La descripción es requerida")
    private String descripcion;
    private String categoria;
    private byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String gategoria) {
        this.categoria = categoria;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
    public String getStrEstatus(){
        String str="";
        switch (status) {
            case 1:
                str = "COMPLETADA";
                break;
            case 2:
                str = "PENDIENTE";
                break;
            default:
                str = "";
        }
        return str;
    }
}
