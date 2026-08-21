package poo_s8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Registrar Producto
    public boolean registrarProducto(ProductoKD producto) {
        String sql = "INSERT INTO productos (codigo, nombre, descripcion, precio_base, precio_venta, categoria, cantidad, estado) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecioBase());
            ps.setDouble(5, producto.getPrecioVenta());
            ps.setString(6, producto.getCategoria());
            ps.setInt(7, producto.getCantidad());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(" Error al registrar el producto en la BD.");
            System.err.println("Codigo SQL Error: " + e.getErrorCode());
            System.err.println("Mensaje: " + e.getMessage());

            if (e.getErrorCode() == 1062) {
                System.err.println("Detalle: El codigo '" + producto.getCodigo() + "' ya existe en MySQL.");
            }
            return false;
        }
    }

    // Listado solo productos activos (estado = 1)
    public List<ProductoKD> listarProductos() {
        List<ProductoKD> listaProductos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE estado = 1";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductoKD prod = ProductoKD.desdeBaseDeDatos(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_base"),
                        rs.getDouble("precio_venta"),
                        rs.getString("categoria"),
                        rs.getInt("cantidad"),
                        rs.getInt("estado")
                );

                listaProductos.add(prod);
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar productos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Se encontro un registro con datos inválidos en la base de datos: " + e.getMessage());
        }

        return listaProductos;
    }

    // Buscar por código activo
    public ProductoKD buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM productos WHERE codigo = ? AND estado = 1";
        ProductoKD producto = null;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = ProductoKD.desdeBaseDeDatos(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio_base"),
                            rs.getDouble("precio_venta"),
                            rs.getString("categoria"),
                            rs.getInt("cantidad"),
                            rs.getInt("estado")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar el producto con código '" + codigo + "'.");
            System.err.println("Código SQL Error: " + e.getErrorCode());
            System.err.println("Mensaje: " + e.getMessage());
        }

        return producto;
    }

    // Actualizar datos de producto activo
    public boolean actualizarProducto(ProductoKD producto) {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio_base = ?, precio_venta = ?, categoria = ?, cantidad = ? WHERE codigo = ? AND estado = 1";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecioBase());
            ps.setDouble(4, producto.getPrecioVenta());
            ps.setString(5, producto.getCategoria());
            ps.setInt(6, producto.getCantidad());
            ps.setString(7, producto.getCodigo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    // Borrado lógico (Cambia el estado a 0)
    public boolean eliminarLogico(String codigo) {
        String sql = "UPDATE productos SET estado = 0 WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al realizar borrado lógico: " + e.getMessage());
            return false;
        }
    }

    // Reactiva un producto borrado lógicamente (cambia el estado a 1)
    public boolean rehabilitarProducto(String codigo) {
        String sql = "UPDATE productos SET estado = 1 WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al reactivar el producto: " + e.getMessage());
            return false;
        }
    }

    // BUSCAR GENERAL: Busca un producto sin filtrar por estado (para validar si existe inactivo)
    public ProductoKD buscarPorCodigoGeneral(String codigo) {
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        ProductoKD producto = null;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = ProductoKD.desdeBaseDeDatos(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio_base"),
                            rs.getDouble("precio_venta"),
                            rs.getString("categoria"),
                            rs.getInt("cantidad"),
                            rs.getInt("estado")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar el producto general: " + e.getMessage());
        }

        return producto;
    }
}