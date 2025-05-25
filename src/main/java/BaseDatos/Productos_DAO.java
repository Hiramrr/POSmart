package BaseDatos;

import Interfaz_DAO.Productos_DAO_Interface;
import controllers.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Productos_DAO implements Productos_DAO_Interface {

    private static Connection con;

    private static Statement consulta;
    private static ResultSet resultado;

    @Override
    public boolean conexion() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/POSMart", "Hiram", "coco123");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ObservableList<Producto> obtenerProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT * FROM vista_productos");
            while(resultado.next()){
                Producto producto = new Producto(
                        resultado.getInt("id_Producto"),
                        resultado.getString("Nombre"),
                        resultado.getString("Descripcion"),
                        resultado.getInt("Cantidad_stock"),
                        resultado.getDouble("Precio_compra"),
                        resultado.getDouble("Precio_venta"),
                        resultado.getString("categoria"),
                        resultado.getString("ubicacion")
                );
                productos.add(producto);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return productos;
    }

    @Override
    public List<String> obtenerNombresCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT Nombre FROM Categoria";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categorias.add(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    @Override
    public List<String> obtenerNombresUbicaciones() {
        List<String> ubicaciones = new ArrayList<>();
        String sql = "SELECT Nombre FROM Ubicacion";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ubicaciones.add(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ubicaciones;
    }

    @Override
    public boolean eliminarProductoDeBaseDeDatos(int idProducto) {
        String query = "DELETE FROM Productos WHERE id_Producto = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, idProducto);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ObservableList<Producto> obtenerProductosActivos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        String query = "SELECT * FROM productos WHERE estado = TRUE";

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id_Producto"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getInt("Cantidad_stock"),
                        rs.getDouble("Precio_compra"),
                        rs.getDouble("Precio_venta"),
                        rs.getString("id_categoria"),
                        rs.getString("id_ubicacion"),
                        rs.getBoolean("estado")
                );
                productos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public boolean agregarStock(int idProducto, int cantidad) {
        String query = "UPDATE Productos SET Cantidad_stock = ? WHERE id_Producto = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, idProducto);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
