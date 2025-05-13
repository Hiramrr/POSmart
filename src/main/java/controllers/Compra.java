package controllers;

import javafx.beans.property.*;

public class Compra {
    private final IntegerProperty idCompra;
    private final StringProperty fechaCompra;
    private final DoubleProperty totalCompra;
    private final StringProperty proveedor;
    private StringProperty usuario;

    public Compra(int idCompra, String fechaCompra, double totalCompra, String proveedor, String usuario) {
        this.idCompra = new SimpleIntegerProperty(idCompra);
        this.fechaCompra = new SimpleStringProperty(fechaCompra);
        this.totalCompra = new SimpleDoubleProperty(totalCompra);
        this.proveedor = new SimpleStringProperty(proveedor);
        this.usuario = new SimpleStringProperty(usuario);
    }

    public int getIdCompra() { return idCompra.get(); }
    public void setIdCompra(int value) { idCompra.set(value); }
    public IntegerProperty idCompraProperty() { return idCompra; }

    public String getFechaCompra() { return fechaCompra.get(); }
    public void setFechaCompra(String value) { fechaCompra.set(value); }
    public StringProperty fechaCompraProperty() { return fechaCompra; }

    public double getTotalCompra() { return totalCompra.get(); }
    public void setTotalCompra(double value) { totalCompra.set(value); }
    public DoubleProperty totalCompraProperty() { return totalCompra; }

    public String getProveedor() { return proveedor.get(); }
    public void setProveedor(String value) { proveedor.set(value); }
    public StringProperty proveedorProperty() { return proveedor; }

    public String getUsuario() {return usuario.get();}
    public void setUsuario(String value) { usuario.set(value); }

    public StringProperty usuarioProperty() {return usuario;}


}
