package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import controllers.Proveedor;

public class DetallesProveedorController {

    @FXML
    private Label idLabel;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label telefonoLabel;

    @FXML
    private Label correoLabel;

    @FXML
    private Label direccionLabel;

    public void setProveedor(Proveedor proveedor) {
        idLabel.setText("ID: " + proveedor.getId());
        nombreLabel.setText("Nombre: " + proveedor.getNombre());
        telefonoLabel.setText("Teléfono: " + proveedor.getTelefono());
        correoLabel.setText("Correo: " + proveedor.getCorreo());
        direccionLabel.setText("Dirección: " + proveedor.getDireccion());
    }
}