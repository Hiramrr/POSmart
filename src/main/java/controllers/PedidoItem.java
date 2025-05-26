package controllers;

import javafx.beans.property.*;

public class PedidoItem {
    private final IntegerProperty idProducto;
    private final StringProperty nombreProducto;
    private final IntegerProperty cantidad;
    private final StringProperty nombreProveedor;
    private final DoubleProperty total;

    public PedidoItem(int idProducto, String nombreProducto, int cantidad, String nombreProveedor, double total) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
        this.nombreProducto = new SimpleStringProperty(nombreProducto);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.nombreProveedor = new SimpleStringProperty(nombreProveedor);
        this.total = new SimpleDoubleProperty(total);
    }

    public int getIdProducto() { return idProducto.get(); }
    public void setIdProducto(int value) { idProducto.set(value); }
    public IntegerProperty idProductoProperty() { return idProducto; }

    public String getNombreProducto() { return nombreProducto.get(); }
    public void setNombreProducto(String value) { nombreProducto.set(value); }
    public StringProperty nombreProductoProperty() { return nombreProducto; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    public String getNombreProveedor() { return nombreProveedor.get(); }
    public void setNombreProveedor(String value) { nombreProveedor.set(value); }
    public StringProperty nombreProveedorProperty() { return nombreProveedor; }

    public double getTotal() { return total.get(); }
    public void setTotal(double value) { total.set(value); }
    public DoubleProperty totalProperty() { return total; }
} 