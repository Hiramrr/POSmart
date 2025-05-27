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
            System.err.println("Error en la conexión: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ObservableList<Producto> obtenerProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM vista_productos"; // si vista_productos no incluye imagen, cambia a tabla Productos
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery(sql);
            while (resultado.next()) {
                // Agrego lectura del campo imagen como bytes
                byte[] imagenBytes = null;
                try {
                    imagenBytes = resultado.getBytes("Imagen"); // puede ser null si no tiene imagen
                } catch (SQLException e) {
                    // Si no existe la columna imagen, ignorar
                }

                Producto producto = new Producto(
                        resultado.getInt("id_Producto"),
                        resultado.getString("Nombre"),
                        resultado.getString("Descripcion"),
                        resultado.getInt("Cantidad_stock"),
                        resultado.getDouble("Precio_compra"),
                        resultado.getDouble("Precio_venta"),
                        resultado.getString("categoria"),
                        resultado.getString("ubicacion"),
                        null // aquí si usas estado en tu tabla puedes ajustarlo
                );

                // Setear imagen (si tu clase Producto tiene un setter para imagen)
                producto.setImagen(imagenBytes);

                productos.add(producto);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
            e.printStackTrace();
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
        String query = "SELECT * FROM Productos WHERE estado = TRUE";

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                byte[] imagenBytes = null;
                try {
                    imagenBytes = rs.getBytes("Imagen");
                } catch (SQLException e) {
                    // Ignorar si no existe
                }

                Producto p = new Producto(
                        rs.getInt("id_Producto"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getInt("Cantidad_stock"),
                        rs.getDouble("Precio_compra"),
                        rs.getDouble("Precio_venta"),
                        rs.getString("categoria"),
                        rs.getString("ubicacion"),
                        rs.getBoolean("estado")
                );
                p.setImagen(imagenBytes);
                productos.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener productos activos: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public boolean agregarStock(int idProducto, int cantidad) {
        String query = "UPDATE Productos SET Cantidad_stock = Cantidad_stock + ? WHERE id_Producto = ?";
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

    // Método para agregar o actualizar producto con imagen
    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO Productos (id_Producto, Nombre, Descripcion, Cantidad_stock, Precio_compra, Precio_venta, id_Categoria, id_Ubicacion, Imagen, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "Nombre = VALUES(Nombre), Descripcion = VALUES(Descripcion), Cantidad_stock = VALUES(Cantidad_stock), " +
                "Precio_compra = VALUES(Precio_compra), Precio_venta = VALUES(Precio_venta), id_Categoria = VALUES(id_Categoria), " +
                "id_Ubicacion = VALUES(id_Ubicacion), Imagen = VALUES(Imagen), estado = VALUES(estado)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, producto.getId());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setInt(4, producto.getCantidad());
            stmt.setDouble(5, producto.getPrecioCompra());
            stmt.setDouble(6, producto.getPrecioVenta());

            // Aquí asumo que tienes los métodos para obtener id_categoria e id_ubicacion como enteros en Producto
            stmt.setInt(7, Integer.parseInt(producto.getCategoria()));  // Si tu clase Producto usa String, ajusta o agrega un método para traer id
            stmt.setInt(8, Integer.parseInt(producto.getUbicacion()));

            byte[] imagen = producto.getImagen();
            if (imagen != null) {
                stmt.setBytes(9, imagen);
            } else {
                stmt.setNull(9, Types.BLOB);
            }

            // Estado: si es null, por defecto true
            Boolean estado = producto.getEstado();
            if (estado != null) {
                stmt.setBoolean(10, estado);
            } else {
                stmt.setBoolean(10, true);
            }

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar o actualizar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
