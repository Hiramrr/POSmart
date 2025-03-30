package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PrincipalController {
    @FXML
    private Text dia;

    @FXML
    private Text hora;

    @FXML
    private Button cerrarSesion;

    @FXML
    public void initialize() {
        obtenerDia();
    }

    public void obtenerDia(){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dia.setText(localDate.format(formatter));
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
            //no hay que hacer nada jaja
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
}
