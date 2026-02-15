package com.empresa.registraduria.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FechaActu {

    /**
     * Metodo que permite obtener la fecha actual
     * @return Fecha actual
     */
    public static Date getFechaActual() {
        return Date.valueOf(LocalDate.now());
    }

    /**
     * Metodo que permite convertir una fecha de LocalDate a Date
     * @param date Fecha a convertir
     * @return Fecha convertida
     */
    public static Date valueOf(LocalDate date){
        return Date.valueOf(date);
    }

    /**
     * Metodo que permite convertir una fecha de Date a LocalDate
     * @param date Fecha a convertir
     * @return Fecha convertida
     */
    public static LocalDate valueOf(Date date){
        return date.toLocalDate();
    }

    /**
     * Metodo que permite obtener la fecha y hora actual
     * @return Fecha y hora actual
     */
    public static Timestamp getFechaTiempo(){
        return Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * Metodo que permite convertir una fecha de String a LocalDate
     * @param fechaStr Fecha a convertir
     * @param formato Formato de la fecha
     * @return Fecha convertida
     */
    public static LocalDate parseFecha(String fechaStr, DateTimeFormatter formato){
        return LocalDate.parse(fechaStr, formato);
    }

    /**
     * Metodo que permite convertir una fecha en String a LocalDate
     * @param fechaStr Fecha a convertir
     * @return Fecha convertida
     */
    public static LocalDate parseFecha(String fechaStr){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fechaStr, formato);
    }

}
