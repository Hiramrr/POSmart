package BaseDatos;

import Interfaz_DAO.GestionarCompras_DAO_Interface;

import java.sql.*;

public class Compras_DAO implements GestionarCompras_DAO_Interface {
    private static Connection con;
    private static Statement consulta;
    private static ResultSet resultado;

    @Override
    public boolean conexion() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/POSMart", "Hiram", "coco123");
            return true;
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean agregarCompra(String fecha, double total, int idProveedor, int idUsuario) {
        try {
            consulta = con.createStatement();
            String query = "CALL agregar_Compra('" + fecha + "', " + total + ", " + idProveedor + ", " + idUsuario + ")";
            int filasAfectadas = consulta.executeUpdate(query);
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.out.println("Error al insertar compra: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean agregarDetalleCompra(int idProducto, int idCompra, int cantidad, double montoFinal) {
        try {
            consulta = con.createStatement();
            String query = "CALL agregar_DetalleCompra(" + idProducto + ", " + idCompra + ", " + cantidad + ", " + montoFinal + ")";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error al insertar detalle de compra: " + e.getMessage());
        }
        return false;
    }

    public int obtenerUltimoIdCompra() {
        int idCompra = -1;
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultado.next()) {
                idCompra = resultado.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el último id de compra: " + e.getMessage());
        }
        return idCompra;
    }

    public boolean actualizarDisponibilidad(int idProducto, boolean disponible) {
        try {
            String query = "UPDATE Productos SET disponible = ? WHERE id_Producto = ?";
            var ps = con.prepareStatement(query);
            ps.setBoolean(1, disponible);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al actualizar disponibilidad: " + e.getMessage());
        }
        return false;
    }

    public boolean obtenerDisponibilidad(int idProducto) {
        try {
            String query = "SELECT disponible FROM Productos WHERE id_Producto = ?";
            var ps = con.prepareStatement(query);
            ps.setInt(1, idProducto);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("disponible");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener disponibilidad: " + e.getMessage());
        }
        return false;
    }

    public boolean validarCantidadDisponible(int idProducto, int cantidadSolicitada) {
        String query = "SELECT Cantidad_stock FROM Productos WHERE id_Producto = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int stockDisponible = rs.getInt("Cantidad_stock");
                    return cantidadSolicitada <= stockDisponible;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean actualizarStockProducto(int idProducto, int cantidadVendida) {
        String query = "UPDATE Productos SET Cantidad_stock = Cantidad_stock - ? WHERE id_Producto = ? AND Cantidad_stock >= ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, cantidadVendida);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidadVendida);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
