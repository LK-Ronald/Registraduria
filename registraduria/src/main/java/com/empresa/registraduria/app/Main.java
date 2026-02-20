package com.empresa.registraduria.app;

import java.util.Scanner;

import com.empresa.registraduria.infraestructura.datos.mysql.MysqlPersonaRepository;
import com.empresa.registraduria.infraestructura.datos.postgresql.PostgresPersonaRepository;
import com.empresa.registraduria.util.FechaActu;
import com.empresa.registraduria.dominio.modelo.Persona;
import com.empresa.registraduria.dominio.servicio.RegistroPersonasImpl;

public class Main {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        RegistroPersonasImpl rp = new RegistroPersonasImpl(new PostgresPersonaRepository());
        //RegistroPersonasImpl rp = new RegistroPersonasImpl(new AccesoDatosMysqlImpl());
        int opcion = 0;

        while (opcion != 8) {
            System.out.println("--------Menu--------");
            System.out.println("1. Crear base de datos");
            System.out.println("2. Agregar persona");
            System.out.println("3. Buscar persona");
            System.out.println("4. Listar personas");
            System.out.println("5. Borrar persona");
            System.out.println("6. Actualizar clave");
            System.out.println("7. Existe persona");
            System.out.println("8. Salir");
            System.out.print("Ingrese una opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Creando base de datos...");
                    rp.crear("empresa", "registraduria/db_scripts/empresa.sql");
                    System.out.println("Base de datos creada exitosamente");
                    break;

                case 2:
                    System.out.println("Agregando persona...");
                    System.out.print("Ingrese su numero de identificacion: ");
                    long nid = sc.nextLong();
                    sc.nextLine();

                    System.out.print("Ingrese su nombre: ");
                    String nombre = sc.next();
                    sc.nextLine();

                    System.out.print("Ingrese el apellido: ");
                    String apellido = sc.next();
                    sc.nextLine();

                    System.out.print("Ingrese su correo: ");
                    String correo = sc.next();
                    sc.nextLine();

                    System.out.print("Ingrese la clave: ");
                    String clave = sc.next();
                    sc.nextLine();

                    System.out.print("Ingrese su fecha de nacimiento (YYYY-MM-DD): ");
                    String fechaNacimiento = sc.next();
                    sc.nextLine();

                    Persona persona = new Persona(nid, nombre, apellido, correo, clave,
                            FechaActu.parseFecha(fechaNacimiento));

                    rp.agregar(persona);
                    System.out.println("Persona agregada exitosamente");
                    break;

                case 3:
                    System.out.print("Ingrese el numero de identificacion: ");
                    long nidBus = sc.nextLong();
                    sc.nextLine();

                    System.out.println("Buscando persona...");
                    rp.buscar(nidBus);
                    break;

                case 4:
                    System.out.println("Listando personas...");
                    rp.listar();
                    break;

                case 5:
                    System.out.print("Ingrese el numero de identificacion: ");
                    long nidBor = sc.nextLong();
                    sc.nextLine();

                    System.out.println("Borrando persona...");
                    rp.borrar(nidBor);
                    break;

                case 6:
                    System.out.print("Ingrese el numero de identificacion: ");
                    long nidAct = sc.nextLong();
                    sc.nextLine();

                    System.out.print("Ingrese la nueva clave: ");
                    String nuevaClave = sc.next();
                    sc.nextLine();

                    rp.actualizarClave(nidAct, nuevaClave);
                    System.out.println("Clave actualizada exitosamente");
                    break;

                case 7:
                    System.out.print("Ingrese el numero de identificacion: ");
                    long nidEx = sc.nextLong();
                    sc.nextLine();

                    rp.existe(nidEx);
                    break;

                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }

        sc.close();
        
    }
}
