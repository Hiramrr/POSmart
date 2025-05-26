package BaseDatos;

import Interfaz_DAO.RealizarPedido_DAO_Interface;
import controllers.Producto;
import controllers.Proveedor;
import controllers.PedidoItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RealizarPedido_DAO implements RealizarPedido_DAO_Interface {
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
            resultado = consulta.executeQuery("SELECT * FROM Productos");
            while(resultado.next()){
                Producto producto = new Producto(
                        resultado.getInt("id_Producto"),
                        resultado.getString("Nombre"),
                        resultado.getString("Descripcion"),
                        resultado.getInt("Cantidad_stock"),
                        resultado.getDouble("Precio_compra"),
                        resultado.getDouble("Precio_venta"),
                        String.valueOf(resultado.getInt("id_categoria")),
                        String.valueOf(resultado.getInt("id_ubicacion"))
                );
                productos.add(producto);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return productos;
    }

    @Override
    public List<Proveedor> obtenerProveedoresPorProducto(int idProducto) {
        List<Proveedor> proveedores = new ArrayList<>();
        try {
            consulta = con.createStatement();
            String query = "SELECT p.* FROM Proveedor p " +
                         "INNER JOIN Producto_Proveedor pp ON p.id_Proveedor = pp.id_Proveedor " +
                         "WHERE pp.id_Producto = " + idProducto;
            resultado = consulta.executeQuery(query);
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

    @Override
    public boolean realizarPedido(int idProducto, int idProveedor, int cantidad, double total, String nombreProducto, String nombreProveedor) {
        try {
            consulta = con.createStatement();
            String insertPedido = "INSERT INTO PEDIDO (id_Producto, nombre_Producto, cantidad, id_proveedor, nombre_Proveedor, total) " +
                                "VALUES (" + idProducto + ", '" + nombreProducto + "', " + cantidad + ", " + 
                                idProveedor + ", '" + nombreProveedor + "', " + total + ")";
                                //ya no me importa si hacen una inyeccion de sql, es mas rapido hacerlo asi jaja
            consulta.executeUpdate(insertPedido);
            return true;
        } catch (Exception e) {
            System.out.println("Error al realizar el pedido: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<PedidoItem> obtenerPedidos() {
        ObservableList<PedidoItem> pedidos = FXCollections.observableArrayList();
        try {
            consulta = con.createStatement();
            String query = "SELECT * FROM PEDIDO";
            resultado = consulta.executeQuery(query);
            while (resultado.next()) {
                PedidoItem pedido = new PedidoItem(
                    resultado.getInt("id_Producto"),
                    resultado.getString("nombre_Producto"),
                    resultado.getInt("cantidad"),
                    resultado.getString("nombre_Proveedor"),
                    resultado.getDouble("total")
                );
                pedidos.add(pedido);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener pedidos: " + e.getMessage());
        }
        return pedidos;
    }
}
