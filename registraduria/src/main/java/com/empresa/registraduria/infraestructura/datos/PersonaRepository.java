package com.empresa.registraduria.infraestructura.datos;

import java.util.List;
import com.empresa.registraduria.dominio.exepciones.*;
import com.empresa.registraduria.dominio.modelo.Persona;

public interface PersonaRepository {

    public abstract boolean existe(long nid) throws AccesoDatosEx;

    public abstract void agregar(Persona persona) throws AccesoDatosEx;

    public abstract void actualizarClave(long nid, String nuevaClave) throws AccesoDatosEx;

    public abstract Persona buscar(long nid) throws AccesoDatosEx;

    public abstract List<Persona> listar() throws AccesoDatosEx;

    public abstract void crear(String nombreDB, String rutaScript) throws AccesoDatosEx;

    public abstract void borrar(long nid) throws AccesoDatosEx;
}
