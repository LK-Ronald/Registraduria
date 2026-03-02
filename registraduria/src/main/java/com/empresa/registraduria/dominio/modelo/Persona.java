package com.empresa.registraduria.dominio.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_usuarios", schema = "acceso")
public class Persona implements Serializable {

    @Id
    @Column(nullable = false)
    private long nid;

    @Column(unique = true, nullable = false, insertable = false, updatable = false)
    private int codigo;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 30, nullable = false)
    private String apellido;

    @Column(length = 50, unique = true, nullable = false)
    private String correo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String clave;

    @Column(name = "fecha_naci", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_regis", nullable = false)
    @CreationTimestamp
    private LocalDateTime fechaRegistro;

    public Persona() {
    }

    public Persona(long nid, String nombre, String apellido, String correo, String clave, LocalDate fechaNacimiento) {
        this.nid = nid;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.clave = clave;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public long getNid() {
        return nid;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Persona [getNid()=" + getNid() + ", getNombre()=" + getNombre() + ", getApellido()=" + getApellido()
                + ", getCorreo()=" + getCorreo() + ", getFechaNacimiento()=" + getFechaNacimiento() + "]";
    }

}
