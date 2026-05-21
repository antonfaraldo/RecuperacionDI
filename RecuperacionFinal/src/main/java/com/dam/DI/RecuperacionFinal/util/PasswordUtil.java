package com.dam.DI.RecuperacionFinal.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
	public static String hashPassword(String password) {
		try {
			return BCrypt.hashpw(password, BCrypt.gensalt());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Error al encriptar la contraseña: " + e.getMessage());
		}
	}
	
	public static boolean checkPassword(String plainPassword, String hashedPassword) {
		try {
			return BCrypt.checkpw(plainPassword, hashedPassword);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Error al verificar la contraseña con BCrypt: " + e.getMessage());
			return false;
		}
	}

}
