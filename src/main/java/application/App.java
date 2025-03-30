package application;

import BaseDatos.BaseDatos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.IOException;

public class App extends Application {
    BaseDatos bd = new BaseDatos();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        AnchorPane load = loader.load();

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

        Scene scene = new Scene(load);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("POSmart");


    }
}