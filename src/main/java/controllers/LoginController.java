package controllers;

import BaseDatos.Login_DAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    Login_DAO mBD = new Login_DAO();

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

    AlertPOSmart alerta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!mBD.conexion()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Alerta de base de datos");
            alert.setHeaderText(null);
            alert.setContentText("No se encontro la conexion con la base de datos, contacta a un administrador");
            alert.showAndWait();
        }
        leerUltimaSesion();
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
            alerta = new AlertPOSmart(AlertType.ERROR, "Alerta de Login", "Usuario o contraseña inválidos");
            return;
        }

        String rol = mBD.saberRol(username, password);

        if (rol == null) {
            alerta = new AlertPOSmart(AlertType.ERROR, "Error", "No se pudo determinar el rol del usuario.");
            return;
        }

        if (recordarDatos.isSelected()) {
            guardarDatos(rol);
        }

        Sesion sesion = Sesion.getInstancia();
        sesion.setIdUsuario(mBD.obtenerIdUsuario(username));
        sesion.setRol(rol);
        sesion.setNombreUsuario(username);

        alerta = new AlertPOSmart(AlertType.INFORMATION, "Bienvenido", "Bienvenido de vuelta " + username + " (" + rol + ")!");

        cargarVistaPorRol(rol);
    }

    private void cargarVistaPorRol(String rol) {
        if (rol.equalsIgnoreCase("administrador")) {
            cargarVentantaPrincipal();
        } else {
            cargarVistaCajero();
        }
    }

    public void cargarVentantaPrincipal(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/principal.fxml"));
            GridPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("POSmart");
            stage.setScene(new Scene(pane));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            Stage currentStage = (Stage) this.txtUser.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarVistaCajero() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VistaCajero.fxml"));
            Parent pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cajero - POSmart");
            stage.setScene(new Scene(pane));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            Stage currentStage = (Stage) this.txtUser.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarDatos(String rol) {
        try (BufferedWriter datos = new BufferedWriter(new FileWriter(".ultimaSesion.txt"))) {
            datos.write(txtUser.getText() + "\n" + txtPassword.getText() + "\n" + rol);
        } catch (IOException e) {
            alerta = new AlertPOSmart(AlertType.ERROR,"???", "Realmente no se como podria ocurrir este error, pero si ves este mensajen wow");
        }
    }

    public void leerUltimaSesion() {
        File archivoSesion = new File(".ultimaSesion.txt");

        if (archivoSesion.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoSesion))) {
                String usuarioGuardado = br.readLine();
                String passwordGuardado = br.readLine();
                String rolGuardado = br.readLine();
                System.out.println("Rol guardado: " + rolGuardado);
                if (usuarioGuardado != null && passwordGuardado != null && rolGuardado != null) {
                    txtUser.setText(usuarioGuardado);
                    txtPassword.setText(passwordGuardado);
                    recordarDatos.setSelected(true);
                    Boolean exito = mBD.validarUsuario(usuarioGuardado, passwordGuardado);

                    if (exito) {
                        String rolActual = mBD.saberRol(usuarioGuardado, passwordGuardado);
                        if (rolActual != null && rolActual.equalsIgnoreCase(rolGuardado)) {
                            alerta = new AlertPOSmart(AlertType.INFORMATION, "Bienvenido", "Bienvenido de vuelta " + usuarioGuardado + " (" + rolGuardado + ")!");
                            cargarVistaPorRol(rolGuardado);
                        } else {
                            System.out.println("Rol actual: " + rolActual + ", Rol guardado: " + rolGuardado);
                            alerta = new AlertPOSmart(AlertType.ERROR, "Error",
                                    "El rol del usuario ha cambiado desde la última sesión.");
                            alerta.showAndWait();
                        }
                    } else {
                        alerta = new AlertPOSmart(AlertType.ERROR, "Alerta de Login",
                                "Usuario o contraseña guardados ya no son válidos.");
                        alerta.showAndWait();
                    }
                }
            } catch (IOException e) {
                alerta = new AlertPOSmart(AlertType.ERROR, "Error IO", e.toString());
            }
        }
    }
}