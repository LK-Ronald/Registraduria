package com.empresa.registraduria.dominio.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import com.empresa.registraduria.util.HashPassword;

public class Persona implements Serializable {

    private long nid;
    private String nombre;
    private String apellido;
    private String correo;
    private String clave;
    private LocalDate anoNacimiento;

    public Persona(long nid, String nombre, String apellido, String correo, String clave, LocalDate anoNacimiento) {
        this.nid = nid;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.clave = HashPassword.hashpw(clave);
        this.anoNacimiento = anoNacimiento;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getAnoNacimiento() {
        return this.anoNacimiento;
    }

    public void setAnoNacimiento(LocalDate anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public long getNid() {
        return nid;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = HashPassword.hashpw(clave);
    }

    @Override
    public String toString() {
        return "NID: " + nid + " | Nombre: " + nombre + " | Apellido: " + apellido + " | Correo: " + correo
                + " | Fecha de Nacimiento: " + anoNacimiento;
    }

}
