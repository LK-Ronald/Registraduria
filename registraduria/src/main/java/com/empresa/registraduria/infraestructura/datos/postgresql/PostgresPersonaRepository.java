package com.empresa.registraduria.infraestructura.datos.postgresql;

import java.util.ArrayList;
import java.util.List;
import com.empresa.registraduria.dominio.exepciones.*;
import com.empresa.registraduria.dominio.modelo.Persona;
import com.empresa.registraduria.infraestructura.datos.PersonaRepository;
import com.empresa.registraduria.util.*;
import java.sql.*;

public class PostgresPersonaRepository implements PersonaRepository {

    private final String nombreDB = "acceso.tb_usuarios";

    @Override
    public boolean existe(long nid) throws AccesoDatosEx {
        String url = "jdbc:postgresql://localhost:5432/empresa";
        CargarConfig cg = new CargarConfig("registraduria/config/demo.properties");
        boolean existe = false;

        String sql = "SELECT * FROM " + nombreDB + " WHERE nid = ? AND activo = true";

        try (Connection conn = DriverManager.getConnection(url, cg.cargarProperties());
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setLong(1, nid);
            try (ResultSet rs = pst.executeQuery()) {
                existe = rs.next();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return existe;
    }

    @Override
    public void agregar(Persona persona) throws EscrituraDatosEx {
        CargarConfig cg = new CargarConfig("registraduria/config/demo.properties");
        String url = "jdbc:postgresql://localhost:5432/empresa";

        String sql = "INSERT INTO " + nombreDB
                + " (nid, nombre, apellido, correo, clave, fecha_regis, fecha_naci) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, cg.cargarProperties());
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setLong(1, persona.getNid());
            pst.setString(2, persona.getNombre());
            pst.setString(3, persona.getApellido());
            pst.setString(4, persona.getCorreo());
            pst.setString(5, persona.getClave());
            pst.setTimestamp(6, FechaActu.getFechaTiempo());
            pst.setDate(7, FechaActu.valueOf(persona.getAnoNacimiento()));

            int insertRow = pst.executeUpdate();
            System.out.println("Se insertaron: " + insertRow + " filas");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actualizarClave(long nid, String nuevaClave) throws EscrituraDatosEx {
        CargarConfig cg = new CargarConfig("registraduria/config/demo.properties");
        String url = "jdbc:postgresql://localhost:5432/empresa";

        String sql = "UPDATE " + nombreDB + " SET clave = ? WHERE nid = ? AND activo = true";

        try (Connection conn = DriverManager.getConnection(url, cg.cargarProperties());
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, HashPassword.hashpw(nuevaClave));
            pst.setLong(2, nid);

            int updateRow = pst.executeUpdate();

            if (updateRow == 0) {
                System.out.println("El numero de identificacion no es valido");
            } else {
                System.out.println("Se actualizaron: " + updateRow + " filas");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Persona buscar(long nid) throws AccesoDatosEx {
        Persona persona = null;
        CargarConfig cg = new CargarConfig("registraduria/config/demo.properties");
        String url = "jdbc:postgresql://localhost:5432/empresa";

        String sql = "SELECT * FROM " + nombreDB + " WHERE nid = ? AND activo = true";

        try (Connection conn = DriverManager.getConnection(url, cg.cargarProperties());
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setLong(1, nid);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    persona = new Persona(rs.getLong("nid"), rs.getString("nombre"), rs.getString("apellido"),
                            rs.getString("correo"), rs.getString("clave"), FechaActu.valueOf(rs.getDate("fecha_naci")));
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return persona;
    }

    @Override
    public List<Persona> listar() throws AccesoDatosEx {
        List<Persona> personas = new ArrayList<>();
        CargarConfig cg = new CargarConfig("registraduria/config/demo.properties");
        String url = "jdbc:postgresql://localhost:5432/empresa";

        String sql = "SELECT * FROM " + nombreDB + " WHERE activo = true";

        try (Connection conn = DriverManager.getConnection(url, cg.cargarProperties());
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                personas.add(new Persona(rs.getLong("nid"), rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("correo"), rs.getString("clave"), FechaActu.valueOf(rs.getDate("fecha_naci"))));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return personas;
    }

    @Override
    public void crear(String nombreDB, String rutaScript) throws AccesoDatosEx {
        CargarConfig cg = new CargarConfig("registraduria/config/demo.properties");
        String url = "jdbc:postgresql://localhost:5432/empresa";

        String sql = CargarQuery.cargarQuery(rutaScript);

        try (Connection conn = DriverManager.getConnection(url, cg.cargarProperties());
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void borrar(long nid) throws AccesoDatosEx {

        CargarConfig cg = new CargarConfig("registraduria/config/demo.properties");
        String url = "jdbc:postgresql://localhost:5432/empresa";

        String sql = "UPDATE " + nombreDB + " SET activo = false WHERE nid = ?";

        try (Connection conn = DriverManager.getConnection(url, cg.cargarProperties());
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setLong(1, nid);
            pst.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
