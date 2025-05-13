package controllers;

import javafx.beans.property.*;

public class DetalleCompra {
    private final StringProperty producto;
    private final IntegerProperty cantidad;
    private final DoubleProperty precioUnitario;
    private final DoubleProperty montoFinal;

    public DetalleCompra(String producto, int cantidad, double precioUnitario, double montoFinal) {
        this.producto = new SimpleStringProperty(producto);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.precioUnitario = new SimpleDoubleProperty(precioUnitario);
        this.montoFinal = new SimpleDoubleProperty(montoFinal);
    }

    public String getProducto() { return producto.get(); }
    public void setProducto(String value) { producto.set(value); }
    public StringProperty productoProperty() { return producto; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    public double getPrecioUnitario() { return precioUnitario.get(); }
    public void setPrecioUnitario(double value) { precioUnitario.set(value); }
    public DoubleProperty precioUnitarioProperty() { return precioUnitario; }

    public double getMontoFinal() { return montoFinal.get(); }
    public void setMontoFinal(double value) { montoFinal.set(value); }
    public DoubleProperty montoFinalProperty() { return montoFinal; }
}
