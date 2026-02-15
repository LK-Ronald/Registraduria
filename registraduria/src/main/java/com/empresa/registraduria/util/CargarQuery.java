package com.empresa.registraduria.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CargarQuery {

    /**
     * Metodo que permite cargar un archivo de texto y retornar su contenido como
     * String
     * @param rutaArchivo ruta del archivo
     * @return String contenido del archivo
     */
    public static String cargarQuery(String rutaArchivo) {
        String sql = "";
        File file = new File(rutaArchivo);

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String linea = null;
            while ((linea = br.readLine()) != null) {
                sql += linea;
                // System.out.println(linea);
            }
            br.close();
            fr.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return sql;
    }
}
