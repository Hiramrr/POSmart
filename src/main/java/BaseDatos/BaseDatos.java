package BaseDatos;

import controllers.Producto;
import controllers.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean validarNombreCompleto(String nombre_completo){
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL existe_usuario('" + nombre_completo + "')");
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean cambiarContraseña(String nombre_completo, String contraseña_nueva){
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL cambiar_contraseña('" + nombre_completo + "', '" + contraseña_nueva + "')");
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean agregarEmpleado(int id, String nombre, String contraseña, String nombre_completo, String rol, String Telefono, String ciudad, String direccion){
        try {
            consulta = con.createStatement();
            resultado = consulta.executeQuery("CALL agregar_usuario('" + id + "', '" + nombre + "', '" + contraseña + "', '" + nombre_completo + "', '" + rol + "', '" + Telefono + "', '" + ciudad + "', '" + direccion + "')");
            if (resultado.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


    public boolean agregarProveedor(int id, String nombre, String telefono, String correo, String direccion) {
        try {
            consulta = con.createStatement();
            String query = "CALL agregar_proveedor('" + id + "', '" + nombre + "', '" + telefono + "', '" + correo + "', '" + direccion + "')";
            resultado = consulta.executeQuery(query);
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


    public boolean modificarProveedor(int id, String nombre, String telefono, String correo, String direccion) {
        try {
            consulta = con.createStatement();
            String query = "CALL modificar_proveedor('" + id + "', '" + nombre + "', '" + telefono + "', '" + correo + "', '" + direccion + "')";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean eliminarProveedor(int id) {
        try {
            consulta = con.createStatement();
            String query = "CALL eliminar_proveedor('" + id + "')";
            int rowsAffected = consulta.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean agregarProducto(int id, String nombre, String descripcion, int cantidad, double precio, String categoria, String ubicacion) {
        try {
            consulta = con.createStatement();
            String query = "CALL agregar_producto('" + id + "', '" + nombre + "', '" + descripcion + "', '" + cantidad + "', '" + precio + "', '" + categoria + "', '" + ubicacion + "')";

            boolean result = consulta.execute(query);

            return true;
        } catch (Exception e) {
            System.out.println("error base");
            e.printStackTrace();
        }
        return false;
    }

    /** public ObservableList<Producto> cargarProductos() {
        System.out.println("cargar productos");
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id_producto, nombre, descripcion, cantidad, precio FROM productos";  // Ajusta según tu tabla y columnas
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("cargar productos");
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getString("ubicacion")
                );
                listaProductos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaProductos;
    }*/

//////////////////////////////////////////////////////////////////
    public ObservableList<Producto> obtenerProductos() {
        System.out.println("entra a obtener productos");
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        String query = "SELECT * FROM productosPD"; // Cambia "productos" si tu tabla tiene otro nombre

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id_producto"),          // Nombre de las columnas en tu tabla
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getString("ubicacion")
                );
                productos.add(producto);
            }
            System.out.println("termina de hacer en la obtener productos");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("obtiene los productos con exito");
        return productos;
    }

    public boolean actualizarProductoEnBaseDeDatos(Producto producto) {
        String query = "UPDATE productosPD SET nombre = ?, descripcion = ?, cantidad = ?, precio = ?, categoria = ?, ubicacion = ? WHERE id_producto = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            // Establecer los parámetros en la consulta
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getCantidad());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setString(5, producto.getCategoria());
            stmt.setString(6, producto.getUbicacion());
            stmt.setInt(7, producto.getId());

            // Ejecutar la actualización
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;  // Si se actualizó al menos un registro, retorna true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}