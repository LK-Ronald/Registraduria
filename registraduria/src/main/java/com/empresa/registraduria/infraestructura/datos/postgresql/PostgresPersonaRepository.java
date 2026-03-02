package com.empresa.registraduria.infraestructura.datos.postgresql;

import java.util.ArrayList;
import java.util.List;
import com.empresa.registraduria.dominio.exepciones.*;
import com.empresa.registraduria.dominio.modelo.Persona;
import com.empresa.registraduria.infraestructura.datos.PersonaRepository;
import com.empresa.registraduria.util.*;
import jakarta.persistence.*;

public class PostgresPersonaRepository implements PersonaRepository {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MiEmpresa");

    @Override
    public boolean existe(long nid) throws AccesoDatosEx {
        if (nid < 0) {
            throw new AccesoDatosEx("El numero de identificacion no es valido");
        }

        EntityManager em = emf.createEntityManager();
        boolean existe = false;

        try {

            em.getTransaction().begin();
            Persona p = em.find(Persona.class, nid);
            em.getTransaction().commit();

            if (p != null && p.isActivo()) {
                existe = true;
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            em.close();
        }

        return existe;
    }

    @Override
    public void agregar(Persona persona) throws EscrituraDatosEx {
        if (persona == null) {
            throw new EscrituraDatosEx("La persona no puede ser nulla");
        }

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(persona);
            em.getTransaction().commit();
            System.out.println("Persona agregada correctamente");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarClave(long nid, String nuevaClave) throws EscrituraDatosEx {
        if (nid < 0) {
            throw new EscrituraDatosEx("El numero de identificacion no es valido");
        }

        if (nuevaClave == null || nuevaClave.isEmpty()) {
            throw new EscrituraDatosEx("La nueva clave no puede ser null o vacia");
        }

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Persona p = em.find(Persona.class, nid);
            if (p != null && p.isActivo()) {
                p.setClave(HashPassword.hashpw(nuevaClave));
                em.merge(p);
            } else {
                throw new EscrituraDatosEx("No se encontro la persona con el numero de identificacion " + nid);
            }
            em.getTransaction().commit();
            System.out.println("Clave actualizada correctamente");

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Persona buscar(long nid) throws AccesoDatosEx {
        if (nid < 0) {
            throw new AccesoDatosEx("El numero de identificacion no es valido");
        }
        Persona persona = null;
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            persona = em.find(Persona.class, nid);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return persona;
    }

    @Override
    public List<Persona> listar() throws AccesoDatosEx {
        List<Persona> personas = new ArrayList<>();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            personas = em.createQuery("SELECT p FROM Persona p WHERE p.activo = true", Persona.class)
                    .getResultList();
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return personas;
    }

    @Override
    public void crear() throws AccesoDatosEx {

        System.out.println("Base de datos creada correctamente");
    }

    @Override
    public void borrar(long nid) throws AccesoDatosEx {
        if (nid < 0) {
            throw new EscrituraDatosEx("El numero de identificacion no es valido");
        }

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Persona p = em.find(Persona.class, nid);

            if (p != null && p.isActivo()) {
                p.setActivo(false);
                em.merge(p);
            } else {
                throw new EscrituraDatosEx("No se encontro la persona con el numero de identificacion " + nid);
            }

            em.getTransaction().commit();

            System.out.println("Persona eliminada correctamente");

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
