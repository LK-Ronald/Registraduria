package com.empresa.registraduria.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashPassword {
    
    /**
     * Metodo que permite generar un hash de una contraseña
     * @param password Contraseña a hashear
     * @return Hash de la contraseña
     */
    public static String hashpw(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Metodo que permite validar una contraseña
     * @param password Contraseña a validar
     * @param hash Hash de la contraseña
     * @return True si la contraseña es correcta, false en caso contrario
     */
    public static Boolean checkpw(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
