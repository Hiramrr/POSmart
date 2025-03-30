package controllers;

import BaseDatos.BaseDatos;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    BaseDatos mBD = new BaseDatos();

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public Button iniciarBoton;

    @FXML
    private Hyperlink olvidarDatos;

    @FXML
    private CheckBox recordarDatos;

    @FXML
    private Text titulo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String username = txtUser.getText();
        String password = txtPassword.getText();

        validarCredenciales(username, password);
    }

    @FXML
    void handleOlvidarDatos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recuperarCuental.fxml"));
            VBox pane = FXMLLoader.load(getClass().getResource("/recuperarCuenta.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Cambio de datos");
            stage.setScene(new Scene(pane));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            Stage currentStage = (Stage) txtUser.getScene().getWindow();
            currentStage.close();
            stage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void validarCredenciales(String username, String password) {
        Boolean exito = mBD.validarUsuario(username, password);

        if (!exito) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Alerta de Login");
            alert.setHeaderText(null);
            alert.setContentText("usuario o contraseña, invalido");
            alert.showAndWait();
            return;
        }
        if(recordarDatos.isSelected()){
            guardarDatos();
        }
        cargarVentantaPrincipal();
    }

    public void cargarVentantaPrincipal(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/principal.fxml"));
            GridPane pane = FXMLLoader.load(getClass().getResource("/principal.fxml"));
            Stage stage = new Stage();
            stage.setTitle("POSmart");
            stage.setScene(new Scene(pane));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            Stage currentStage = (Stage) this.txtUser.getScene().getWindow();
            currentStage.close();
            stage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarDatos() {
        try (BufferedWriter datos = new BufferedWriter(new FileWriter(".ultimaSesion.txt"))) {
            datos.write(txtUser.getText() + "\n" + txtPassword.getText() );
        } catch (IOException e) {
            System.err.println("ojala no pase esto: " + e.getMessage());
        }
    }
}