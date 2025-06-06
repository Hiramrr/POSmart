package controllers;

import BaseDatos.Recuperar_DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jdk.jfr.Event;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecuperarController implements Initializable {
    Recuperar_DAO mBD = new Recuperar_DAO();

    @FXML
    private TextField nombre_completo;

    @FXML
    private PasswordField nueva_contraseña;

    @FXML
    private PasswordField repetir_contraseña;

    @FXML
    private Button cambiar;

    AlertPOSmart alerta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!mBD.conexion()){
            alerta = new AlertPOSmart(Alert.AlertType.ERROR,"Alerta de base de datos", "No se encontro la conexion con la base de datos, contacta a un administrador");
        }
    }

    @FXML
    void handleCambiar(ActionEvent event){
        if(seLleno()) {
            if(validarUsuario() && validarContraseña()){
                cambiarContraseña();
            }
        }
    }

    public boolean validarUsuario(){
        String nombre_com = nombre_completo.getText();

        boolean esValido = mBD.validarNombreCompleto(nombre_com);
        if(!esValido){
            error("El nombre no es valido", "El nombre introducido no es valido!");
            return false;
        }
        return true;
    }

    public boolean validarContraseña(){
        String nuevaPass = nueva_contraseña.getText();
        String repetirPass = repetir_contraseña.getText();

        if(!nuevaPass.equals(repetirPass)){
            error("Las contraseñas no coinciden", "Las contraseñas no coinciden!");
            return false;
        }
        return true;
    }

    public void cambiarContraseña(){
        String nombre = nombre_completo.getText();
        String nuevaPass = nueva_contraseña.getText();
        boolean seCambio= mBD.cambiarContraseña(nombre,nuevaPass);
        if(!seCambio){
            error("Ocurrio un error", "Si este error persiste comunicate con un administrador");
            return;
        }

        alerta = new AlertPOSmart(Alert.AlertType.INFORMATION, "Se ha cambiado la contraseña", "Se cambio la contraseña con exito!");

        regresarLogin();
        ((Stage) cambiar.getScene().getWindow()).close();
    }

    public boolean seLleno() {
        String nombre = nombre_completo.getText();
        String nuevaPass = nueva_contraseña.getText();
        String repetirPass = repetir_contraseña.getText();

        if (!nombre.isEmpty() && !nuevaPass.isEmpty() && !repetirPass.isEmpty()) {
            return true;
        }
        if (nombre.isEmpty() && nuevaPass.isEmpty() && repetirPass.isEmpty()) {
            error("Llena los campos", "Por favor llena los campos requeridos");
        } else if (nombre.isEmpty()) {
            error("Introduce un nombre", "Por favor introduce el nombre");
        } else if (nuevaPass.isEmpty()) {
            error("Introduce una contraseña", "Por favor introduce una nueva contraseña");
        } else if (repetirPass.isEmpty()) {
            error("Repite la contraseña", "Por favor repite la contraseña");
        }
        return false;
    }

    public void error(String titulo, String mensaje){
       alerta = new AlertPOSmart(Alert.AlertType.ERROR, titulo, mensaje);
    }

    public void regresarLogin(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("POSmart");
            stage.setScene(new Scene(pane));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
