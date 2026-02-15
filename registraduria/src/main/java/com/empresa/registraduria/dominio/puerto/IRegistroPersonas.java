package com.empresa.registraduria.dominio.puerto;

import com.empresa.registraduria.dominio.modelo.Persona;

public interface IRegistroPersonas {
    
    final String RUTASCRIPT = "registraduria/db_scripts/empresa.sql";

    public abstract void existe(long nid);

    public abstract void agregar(Persona persona);

    public abstract void actualizarClave(long nid, String nuevaClave);

    public abstract void buscar(long nid);

    public abstract void listar();

    public abstract void crear(String nombreDB, String rutaScript);

    public abstract void borrar(long nid);
}