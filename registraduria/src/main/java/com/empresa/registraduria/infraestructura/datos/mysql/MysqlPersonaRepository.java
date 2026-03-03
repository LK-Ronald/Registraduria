package com.empresa.registraduria.infraestructura.datos.mysql;

import java.util.ArrayList;
import java.util.List;
import com.empresa.registraduria.dominio.exepciones.AccesoDatosEx;
import com.empresa.registraduria.dominio.exepciones.EscrituraDatosEx;
import com.empresa.registraduria.dominio.modelo.Persona;
import com.empresa.registraduria.infraestructura.datos.PersonaRepository;
import jakarta.persistence.*;

public class MysqlPersonaRepository implements PersonaRepository {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MiEmpresa");

    @Override
    public boolean existe(long nid) throws AccesoDatosEx {
        if (nid <= 0) {
            throw new AccesoDatosEx("El numero de identificacion no es valido");
        }
        EntityManager em = emf.createEntityManager();
        boolean existe = false;

        try {
            Persona p = em.find(Persona.class, nid);
            if (p != null && p.isActivo()) {
                existe = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return existe;
    }

    @Override
    public void agregar(Persona persona) throws EscrituraDatosEx {
        if (persona == null) {
            throw new EscrituraDatosEx("La persona no se puede agregar");
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(persona);
            em.getTransaction().commit();
            System.out.println("Persona agregada correctamente");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new EscrituraDatosEx("Error al agregar persona: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarClave(long nid, String nuevaClave) throws EscrituraDatosEx {
        if (nid <= 0) {
            throw new EscrituraDatosEx("El numero de identificacion no es valido");
        }
        if (nuevaClave == null || nuevaClave.isEmpty()) {
            throw new EscrituraDatosEx("La clave no puede ser null o vacia");
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Persona p = em.find(Persona.class, nid);
            if (p != null && p.isActivo()) {
                p.setClave(nuevaClave);
                em.merge(p);
                em.getTransaction().commit();
                System.out.println("Clave actualizada correctamente");
            } else {
                throw new EscrituraDatosEx("No se encontró la persona con NID: " + nid);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new EscrituraDatosEx("Error al actualizar clave: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Persona buscar(long nid) throws AccesoDatosEx {
        if (nid <= 0) {
            throw new AccesoDatosEx("El numero de identificacion no es valido");
        }
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Persona.class, nid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AccesoDatosEx("Error al buscar persona: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Persona> listar() throws AccesoDatosEx {
        List<Persona> personas = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        try {
            personas = em.createQuery("SELECT p FROM Persona p WHERE p.activo = true", Persona.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AccesoDatosEx("Error al listar personas: " + e.getMessage());
        } finally {
            em.close();
        }
        return personas;
    }

    @Override
    public void crear() throws AccesoDatosEx {
        System.out.println("La base de datos se creo exitosamente");
    }

    @Override
    public void borrar(long nid) throws AccesoDatosEx {
        if (nid <= 0) {
            throw new AccesoDatosEx("El numero de identificacion no es valido");
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Persona p = em.find(Persona.class, nid);
            if (p != null && p.isActivo()) {
                p.setActivo(false);
                em.merge(p);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new AccesoDatosEx("Error al borrar persona: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarCorreo(long nid, String correo) throws AccesoDatosEx {
        if (nid <= 0) {
            throw new AccesoDatosEx("El numero de identificacion no es valido");
        }

        if (correo.isEmpty()) {
            throw new AccesoDatosEx("El correo ingrsado no es valido");
        }
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Persona p = em.find(Persona.class, nid);

            if (p != null) {

                if (correo.equalsIgnoreCase(p.getCorreo())) {
                    em.getTransaction().commit();
                    throw new AccesoDatosEx("Ingresa un correo diferente");
                }

                p.setCorreo(correo);
                System.out.println("Se actualizo el correo correctamente");
                em.merge(p);
            }else{
                throw new AccesoDatosEx("No se encontro la persona");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
