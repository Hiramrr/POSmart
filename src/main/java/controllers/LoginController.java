package controllers;

import BaseDatos.BaseDatos;
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

import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public Button iniciarBoton;

    @FXML
    private Hyperlink olvidarDatos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String username = txtUser.getText();
        String password = txtPassword.getText();

        System.out.println("Usuario: " + username);
        System.out.println("Contraseña: " + password);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/principal.fxml"));
            GridPane pane = FXMLLoader.load(getClass().getResource("/principal.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Alta de Plantas");
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

    @FXML
    void handleOlvidarDatos(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Olvidar Datos");
        alert.setHeaderText(null);
        alert.setContentText("Falta ver como agregar esto jaja");
        alert.showAndWait();
    }



}