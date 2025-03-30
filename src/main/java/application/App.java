package application;

import BaseDatos.BaseDatos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App extends Application {
    BaseDatos bd = new BaseDatos();

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
           reader.close();

           if (validarCredenciales(user, contraseña)) {
               cargarVentanaPrincipal();
               mostrarLogin = false;
           }
       } catch (Exception e) {
           //es una excepcion que debe de ocurrir si no hay nada, asi que no lleva un manejo
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

   public void cargarVentanaPrincipal() {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/principal.fxml"));
           GridPane pane = loader.load();
           Stage stage = new Stage();
           stage.setTitle("POSmart");
           stage.setScene(new Scene(pane));
           stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
           stage.show();
       } catch (IOException e) {
           System.err.println("ojala nunca pase esto");
           e.printStackTrace();
       }
   }
}