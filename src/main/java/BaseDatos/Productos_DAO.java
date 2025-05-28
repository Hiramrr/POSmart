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
        String sql = "SELECT * FROM vista_productos"; // Cambia si es necesario
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery(sql);
            while (resultado.next()) {
                byte[] imagenBytes = null;
                try {
                    imagenBytes = resultado.getBytes("Imagen");
                } catch (SQLException e) {
                    // Ignorar si no existe la columna imagen
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
                        null // Aquí probablemente debas ajustar si quieres el valor disponible
                );

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
        String eliminarRelaciones = "DELETE FROM producto_proveedor WHERE id_Producto = ?";
        String eliminarProducto = "DELETE FROM productos WHERE id_Producto = ?";

        try (PreparedStatement stmtRelaciones = con.prepareStatement(eliminarRelaciones);
             PreparedStatement stmtProducto = con.prepareStatement(eliminarProducto)) {

            // Eliminar relaciones en producto_proveedor
            stmtRelaciones.setInt(1, idProducto);
            stmtRelaciones.executeUpdate();

            // Eliminar el producto
            stmtProducto.setInt(1, idProducto);
            int filas = stmtProducto.executeUpdate();

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
        String query = "SELECT * FROM Productos WHERE disponible = TRUE";

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
                        rs.getBoolean("disponible")
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
        String query = "UPDATE Productos SET Cantidad_stock = Cantidad_stock + ?, disponible = CASE WHEN Cantidad_stock + ? > 0 THEN 1 ELSE 0 END WHERE id_Producto = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, cantidad);
            stmt.setInt(3, idProducto);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean agregarProducto(Producto producto) {
        // Primero obtener los ids de categoria y ubicacion por sus nombres
        Integer idCategoria = obtenerIdCategoriaPorNombre(producto.getCategoria());
        Integer idUbicacion = obtenerIdUbicacionPorNombre(producto.getUbicacion());

        if (idCategoria == null) {
            System.err.println("No se encontró la categoría: " + producto.getCategoria());
            return false;
        }
        if (idUbicacion == null) {
            System.err.println("No se encontró la ubicación: " + producto.getUbicacion());
            return false;
        }

        String sql = "INSERT INTO Productos (id_Producto, Nombre, Descripcion, Cantidad_stock, Precio_compra, Precio_venta, id_Categoria, id_Ubicacion, Imagen, disponible) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "Nombre = VALUES(Nombre), Descripcion = VALUES(Descripcion), Cantidad_stock = VALUES(Cantidad_stock), " +
                "Precio_compra = VALUES(Precio_compra), Precio_venta = VALUES(Precio_venta), id_Categoria = VALUES(id_Categoria), " +
                "id_Ubicacion = VALUES(id_Ubicacion), Imagen = VALUES(Imagen), disponible = VALUES(disponible)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, producto.getId());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setInt(4, producto.getCantidad());
            stmt.setDouble(5, producto.getPrecioCompra());
            stmt.setDouble(6, producto.getPrecioVenta());

            stmt.setInt(7, idCategoria);
            stmt.setInt(8, idUbicacion);

            byte[] imagen = producto.getImagen();
            if (imagen != null) {
                stmt.setBytes(9, imagen);
            } else {
                stmt.setNull(9, Types.BLOB);
            }

            Boolean disponible = producto.getdisponible();
            if (disponible != null) {
                stmt.setBoolean(10, disponible);
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


    public boolean actualizarCantidadYDisponibilidad(int idProducto, int nuevaCantidad) {
        String query = "UPDATE Productos SET Cantidad_stock = ?, disponible = ? WHERE id_Producto = ?";
        boolean nuevoEstado = nuevaCantidad > 0;
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, nuevaCantidad);
            stmt.setBoolean(2, nuevoEstado);
            stmt.setInt(3, idProducto);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cantidad y disponibilidad: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Métodos para obtener id por nombre
    public Integer obtenerIdCategoriaPorNombre(String nombreCategoria) {
        String sql = "SELECT id_Categoria FROM Categoria WHERE Nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_Categoria");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener id_Categoria: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Integer obtenerIdUbicacionPorNombre(String nombreUbicacion) {
        String sql = "SELECT id_Ubicacion FROM Ubicacion WHERE Nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUbicacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_Ubicacion");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener id_Ubicacion: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
