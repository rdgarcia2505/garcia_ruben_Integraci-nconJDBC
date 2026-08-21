package poo_s8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Se realiza conexion a BD sin contraseña
    private static final String URL = "jdbc:mysql://localhost:3306/kd_electronics";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection obtenerConexion() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error de conexión a la BD: " + e.getErrorCode() + " - " + e.getMessage());
            throw e;
        }
    }
}