package controllers;

    import javafx.animation.Animation;
    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.image.Image;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.Pane;
    import javafx.scene.text.Text;
    import javafx.stage.Stage;
    import javafx.util.Duration;

    import java.io.File;
    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.util.Date;

public class VistaCajeroController {

        @FXML
        private Pane contenedor;
        @FXML
        private Text dia;
        @FXML
        private Text hora;


    public void initialize() {
        obtenerDia();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentaVista.fxml"));
                Parent root = loader.load();
                contenedor.getChildren().clear();
                contenedor.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void obtenerDia(){
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dia.setText(localDate.format(formatter));
            actualizarHora();

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> actualizarHora())
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }

        private void actualizarHora() {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            hora.setText(dateFormat.format(date));
        }

        public void handleCerrarSesion(ActionEvent event) {
            try {
                File sessionFile = new File(".ultimaSesion.txt");
                if (sessionFile.exists()) {
                    if (sessionFile.delete()) {
                    } else {
                        System.out.println("Ojala no pase esto");
                    }
                }
            } catch (Exception e) {
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/login.fxml"));
                Stage stage = new Stage();
                stage.setTitle("POSmart");
                stage.setScene(new Scene(pane));
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
                Stage currentStage = (Stage) dia.getScene().getWindow();
                currentStage.close();
                stage.close();
                stage.show();
            }catch (Exception e){
                System.out.println(e);
            }


        }

        public void handleHacerUnaVenta(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentaVista.fxml"));
                Parent root = loader.load();
                contenedor.getChildren().clear();
                contenedor.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handlerVerProductos(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Productos.fxml"));
                Parent root = loader.load();

                ProductosController controller = loader.getController();
                controller.ocultarBotonesParaCajero();

                contenedor.getChildren().clear();
                contenedor.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handlerMostrarUbicaciones(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mostrarUbicaciones.fxml"));
                Parent root = loader.load();
                contenedor.getChildren().clear();
                contenedor.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void handlerMostrarCategorias(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mostrarCategorias.fxml"));
                Parent root = loader.load();
                contenedor.getChildren().clear();
                contenedor.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handlerHistorialVentas(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/HistorialVentas.fxml"));
                Parent root = loader.load();
                contenedor.getChildren().clear();
                contenedor.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}

