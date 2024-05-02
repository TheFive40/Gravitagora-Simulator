package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class General {
    public  static void mostrarMensajeAlerta(String titulo, String cabecera, String contenido,
                                     Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);
        alert.show();
    }
    public static Parent obtenerContenedorPadre(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(General.class.getResource(url));
        Parent parent = fxmlLoader.load();
        return parent;
    }
    public static void agregarContenedorPadre(String url, AnchorPane contenedor) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(General.class.getResource(url));
        Parent parent = fxmlLoader.load();
        contenedor.getChildren().clear();
        contenedor.getChildren().addAll(parent);
    }
}
