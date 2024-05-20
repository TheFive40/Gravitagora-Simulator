package org.simulador.es;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainFXML.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Gravitagora Simulator");
        stage.getIcons ().add ( new Image ( getClass ().getResource ( "/images/logo.jpeg" ).toExternalForm () ) );
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}