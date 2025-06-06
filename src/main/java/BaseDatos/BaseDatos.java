package BaseDatos;

import controllers.Categoria;
import controllers.Producto;
import controllers.Proveedor;
import controllers.Ubicacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import controllers.Compra;
import controllers.DetalleCompra;

public class BaseDatos {
    private static Connection con;

    private static Statement consulta;
    private static ResultSet resultado;

    public BaseDatos() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/POSMart", "Hiram", "coco123");
            System.out.println("Si ves esto es que se conecto la base");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public boolean validarUsuario(String username, String password){
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL validacion_usuario('" + username + "', '" + password + "')");
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


    public List<Proveedor> obtenerProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT * FROM Proveedor");
            while (resultado.next()) {
                Proveedor proveedor = new Proveedor(
                        resultado.getInt("id_Proveedor"),
                        resultado.getString("Nombre"),
                        resultado.getString("Telefono"),
                        resultado.getString("Correo"),
                        resultado.getString("Direccion")
                );
                proveedores.add(proveedor);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return proveedores;
    }

    public ObservableList<Producto> obtenerProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();

        String query = """
            SELECT p.id_Producto, p.Nombre, p.Descripcion, p.Cantidad_stock,
                   p.Precio_compra, p.Precio_venta,
                   p.Imagen,
                   c.Nombre AS categoria, u.Nombre AS ubicacion
            FROM Productos p
            JOIN Categoria c ON p.id_categoria = c.id_categoria
            JOIN Ubicacion u ON p.id_ubicacion = u.id_ubicacion
        """;

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                byte[] imagenBytes = rs.getBytes("Imagen");
                Producto producto = new Producto(
                        rs.getInt("id_Producto"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getInt("Cantidad_stock"),
                        rs.getDouble("Precio_compra"),
                        rs.getDouble("Precio_venta"),
                        rs.getString("categoria"),
                        rs.getString("ubicacion"),
                        imagenBytes
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }


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

    public boolean agregarProducto(int idProducto, String nombre, String descripcion, int cantidadStock,
                                   double precioCompra, double precioVenta, String categoria, String ubicacion,
                                   byte[] imagenBytes) {
        String query = "{CALL agregar_producto(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, idProducto);
            pstmt.setString(2, nombre);
            pstmt.setString(3, descripcion);
            pstmt.setInt(4, cantidadStock);
            pstmt.setDouble(5, precioCompra);
            pstmt.setDouble(6, precioVenta);
            pstmt.setString(7, categoria);
            pstmt.setString(8, ubicacion);
            pstmt.setBytes(9, imagenBytes); // nueva línea para el parámetro de imagen

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarProductoEnBaseDeDatos(Producto producto) {
        String query = """
        UPDATE Productos
        SET Nombre = ?, 
            Descripcion = ?, 
            Cantidad_stock = ?, 
            Precio_compra = ?, 
            Precio_venta = ?, 
            id_categoria = (SELECT id_Categoria FROM Categoria WHERE Nombre = ?),
            id_ubicacion = (SELECT id_Ubicacion FROM Ubicacion WHERE Nombre = ?),
            Imagen = ?
        WHERE id_Producto = ?
    """;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getCantidad());
            stmt.setDouble(4, producto.getPrecioCompra());
            stmt.setDouble(5, producto.getPrecioVenta());
            stmt.setString(6, producto.getCategoria());
            stmt.setString(7, producto.getUbicacion());
            stmt.setBytes(8, producto.getImagen());
            stmt.setInt(9, producto.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //------Eliminar producto------------------------------------------
    public boolean darDeBajaProducto(int idProducto) {
        String query = "UPDATE Productos SET disponible = FALSE WHERE id_Producto = ?\n";
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

    public int obtenerIdUsuario(String username) {
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT id_Usuario FROM Usuario WHERE Nombre_usuario = '" + username + "'");
            if (resultado.next()) {
                return resultado.getInt("id_Usuario");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el id del usuario: " + e.getMessage());
        }
        return -1;  // Retorna -1 si no encuentra el usuario
    }

    public int obtenerIdProveedor(String nombreProveedor) {
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("SELECT id_Proveedor FROM Proveedor WHERE Nombre = '" + nombreProveedor + "'");
            if (resultado.next()) {
                return resultado.getInt("id_Proveedor");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el id del proveedor: " + e.getMessage());
        }
        return -1;  // Retorna -1 si no encuentra el proveedor
    }


    public List<Compra> obtenerCompras() {
        List<Compra> compras = new ArrayList<>();
        String query = "SELECT c.id_compra, c.fecha_compra, c.total, p.Nombre AS proveedor, u.Nombre_completo AS usuario " +
                "FROM Compra c " +
                "JOIN Proveedor p ON c.id_Proveedor = p.id_Proveedor " +
                "JOIN Usuario u ON c.id_Usuario = u.id_Usuario";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Compra compra = new Compra(
                        rs.getInt("id_compra"),
                        rs.getString("fecha_compra"),
                        rs.getDouble("total"),
                        rs.getString("proveedor"),
                        rs.getString("usuario")
                );
                compras.add(compra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compras;
    }

    public List<DetalleCompra> obtenerDetallesCompra(int idCompra) {
        List<DetalleCompra> detalles = new ArrayList<>();
        String query = "SELECT p.Nombre AS producto, dc.Cantidad, p.Precio_venta, dc.Monto_final " +
                "FROM Detalle_compra dc " +
                "JOIN Productos p ON dc.id_Producto = p.id_Producto " +
                "WHERE dc.id_Compra = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, idCompra);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetalleCompra detalle = new DetalleCompra(
                            rs.getString("producto"),
                            rs.getInt("Cantidad"),
                            rs.getDouble("Precio_venta"),
                            rs.getDouble("Monto_final")
                    );
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

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


    public Categoria obtenerCategoriaPorId(int id) {
        Categoria cat = null;
        String sql = "SELECT id_Categoria, Nombre FROM Categoria WHERE id_Categoria = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cat = new Categoria(rs.getInt("id_Categoria"), rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cat;
    }

    public Ubicacion obtenerUbicacionPorId(int id) {
        Ubicacion ubi = null;
        String sql = "SELECT id_Ubicacion, Nombre FROM Ubicacion WHERE id_Ubicacion = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);  // Asignar el ID a la consulta
            ResultSet rs = stmt.executeQuery();  // Ejecutar consulta
            if (rs.next()) {
                // Crear objeto Ubicacion con el resultado
                ubi = new Ubicacion(rs.getInt("id_Ubicacion"), rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ubi; // Devuelve la ubicación o null si no se encontró
    }

    public boolean agregarProductoProveedor(int idProducto, int idProveedor) {
        try {
            consulta = con.createStatement();
            String query = "INSERT INTO Producto_Proveedor (id_Proveedor, id_Producto) VALUES (" +
                         idProveedor + ", " + idProducto + ")";
            consulta.executeUpdate(query);
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar relación producto-proveedor: " + e.getMessage());
            return false;
        }
    }

}
