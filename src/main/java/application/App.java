package application;

import BaseDatos.*;
import controllers.Sesion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App extends Application {
    BaseDatos bd = new BaseDatos();
    Login_DAO DAO = new Login_DAO();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        boolean mostrarLogin = true;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(".ultimaSesion.txt"));
            String user = reader.readLine();
            String contraseña = reader.readLine();
            String rol = reader.readLine();
            reader.close();

            if (validarCredenciales(user, contraseña)) {
                String rolActual = DAO.saberRol(user, contraseña);
                if (rolActual != null && rolActual.equalsIgnoreCase(rol)) {
                    Sesion sesion = Sesion.getInstancia();
                    sesion.setIdUsuario(bd.obtenerIdUsuario(user));
                    sesion.setRol(rolActual);
                    sesion.setNombreUsuario(user);
                    System.out.println("Bienvenido de vuelta " + user + " (" + rolActual + ")!");
                    cargarVistaPorRol(rolActual);
                    mostrarLogin = false;
                } else {
                    System.out.println("Rol cambiado o inválido.");
                }
            }
        } catch (Exception e) {
        }

        if (mostrarLogin) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            AnchorPane load = loader.load();

            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            Scene scene = new Scene(load);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("POSmart");
        }
    }

    private boolean validarCredenciales(String usuario, String contraseña) {
        return bd.validarUsuario(usuario, contraseña);
    }

    public void cargarVistaPorRol(String rol) {
        try {
            FXMLLoader loader;
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

            if (rol.equalsIgnoreCase("administrador")) {
                loader = new FXMLLoader(getClass().getResource("/principal.fxml"));
                GridPane pane = loader.load();
                stage.setTitle("POSmart - Administrador");
                stage.setScene(new Scene(pane));
            } else {
                loader = new FXMLLoader(getClass().getResource("/VistaCajero.fxml"));
                AnchorPane pane = loader.load();
                stage.setTitle("POSmart - Cajero");
                stage.setScene(new Scene(pane));

            }

            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista según el rol.");
            e.printStackTrace();
        }
    }
}
