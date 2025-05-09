package controllers;

    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.image.Image;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.Pane;
    import javafx.stage.Stage;

    import java.io.File;

    public class VistaCajeroController {

        @FXML
        private Pane contenedor;

        public void initialize() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentaVista.fxml"));
                Parent root = loader.load();
                contenedor.getChildren().clear();
                contenedor.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }







}

