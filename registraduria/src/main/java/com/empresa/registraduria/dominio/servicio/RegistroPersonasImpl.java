package com.empresa.registraduria.dominio.servicio;

import java.util.List;

import com.empresa.registraduria.dominio.exepciones.AccesoDatosEx;
import com.empresa.registraduria.dominio.modelo.Persona;
import com.empresa.registraduria.dominio.puerto.IRegistroPersonas;
import com.empresa.registraduria.infraestructura.datos.IAccesoDatos;

public class RegistroPersonasImpl implements IRegistroPersonas {

    private final IAccesoDatos accesoDatos;

    public RegistroPersonasImpl(IAccesoDatos dataBase) {
        this.accesoDatos = dataBase;
    }

    @Override
    public void actualizarClave(long nid, String nuevaClave) {
        try {
            accesoDatos.actualizarClave(nid, nuevaClave);
        } catch (AccesoDatosEx e) {
            System.out.println("Error al actualizar la clave");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void agregar(Persona persona) {
        try {
            accesoDatos.agregar(persona);
        } catch (AccesoDatosEx e) {
            System.out.println("Error al agregar la persona");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void borrar(long nid) {
        try {
            accesoDatos.borrar(nid);
        } catch (AccesoDatosEx e) {
            System.out.println("Error al borrar la persona");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void buscar(long nid) {
        try {
            Persona persona = accesoDatos.buscar(nid);
            if (persona != null) {
                System.out.println(persona);
            } else {
                System.out.println("Persona no encontrada");
            }
        } catch (AccesoDatosEx e) {
            System.out.println("Error al buscar la persona");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void crear(String nombreDB, String rutaScript) {
        try {
            accesoDatos.crear(nombreDB, RUTASCRIPT);
        } catch (AccesoDatosEx e) {
            System.out.println("Error al crear la base de datos");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void existe(long nid) {
        try {
            boolean existe = accesoDatos.existe(nid);
            if (existe) {
                System.out.println("La persona existe");
            } else {
                System.out.println("La persona no existe");
            }
        } catch (AccesoDatosEx e) {
            System.out.println("Error al verificar la existencia de la persona");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void listar() {
        try {
            List<Persona> personas = accesoDatos.listar();
            for (Persona persona : personas) {
                System.out.println(persona);
            }
        } catch (AccesoDatosEx e) {
            System.out.println("Error al listar las personas");
            System.out.println(e.getMessage());
        }
    }

}
