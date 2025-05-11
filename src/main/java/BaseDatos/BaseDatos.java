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
import controllers.Empleado;


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

    public String saberRol(String username, String password) {
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL obtener_rol('" + username + "', '" + password + "')");
            if (resultado.next()) {
                return resultado.getString("Rol"); // Devuelve el valor de la columna Rol
            }
        } catch (Exception e) {
            System.out.println("Error al obtener rol: " + e.getMessage());
        }
        return null;
    }

    public boolean seConecto() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/POSMart", "Hiram", "coco123");
            System.out.println("Si ves esto es que se conecto la base");
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
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
        System.out.println("entra a obtener productos");
        ObservableList<Producto> productos = FXCollections.observableArrayList();

        String query = """
        SELECT p.id_Producto, p.Nombre, p.Descripcion, p.Cantidad_stock,
               p.Precio_compra, p.Precio_venta,
               c.Nombre AS categoria, u.Nombre AS ubicacion
        FROM Productos p
        JOIN Categoria c ON p.id_categoria = c.id_categoria
        JOIN Ubicacion u ON p.id_ubicacion = u.id_ubicacion
    """;

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id_Producto"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getInt("Cantidad_stock"),
                        rs.getDouble("Precio_compra"),
                        rs.getDouble("Precio_venta"),
                        rs.getString("categoria"),
                        rs.getString("ubicacion")
                );
                productos.add(producto);
            }

            System.out.println("termina de hacer en la obtener productos");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("obtiene los productos con éxito");
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
                                   double precioCompra, double precioVenta, String categoria, String ubicacion) {
        String query = "{CALL agregar_producto(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, idProducto);
            pstmt.setString(2, nombre);
            pstmt.setString(3, descripcion);
            pstmt.setInt(4, cantidadStock);
            pstmt.setDouble(5, precioCompra);
            pstmt.setDouble(6, precioVenta);
            pstmt.setString(7, categoria);
            pstmt.setString(8, ubicacion);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarProductoEnBaseDeDatos(Producto producto) {
        String query = """
        UPDATE Productos
        SET Nombre = ?, Descripcion = ?, Cantidad_stock = ?, Precio_compra = ?, Precio_venta = ?, 
            id_categoria = (SELECT id_Categoria FROM Categoria WHERE Nombre = ?),
            id_ubicacion = (SELECT id_Ubicacion FROM Ubicacion WHERE Nombre = ?)
        WHERE id_Producto = ?
    """;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            // Establecer los parámetros en la consulta
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getCantidad());
            stmt.setDouble(4, producto.getPrecioCompra());
            stmt.setDouble(5, producto.getPrecioVenta());
            stmt.setString(6, producto.getCategoria());  // Aquí usamos el nombre de la categoría
            stmt.setString(7, producto.getUbicacion()); // Aquí usamos el nombre de la ubicación
            stmt.setInt(8, producto.getId());

            // Ejecutar la actualización
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;  // Si se actualizó al menos un registro, retorna true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //------Eliminar producto------------------------------------------
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

}