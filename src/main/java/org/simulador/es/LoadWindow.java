package org.simulador.es;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class LoadWindow extends Application {

    public static Stage primaryStage;

    @Override
    public void start ( Stage stage ) throws Exception {
        Parent parent = new FXMLLoader ( getClass ().getResource ( "/view/LoadWindow.fxml" ) ).load ();
        stage.setScene ( new Scene ( parent ) );
        stage.show ();
        stage.setTitle ( "Cargando..." );
        stage.getIcons ().add ( new Image ( getClass ().getResource ( "/images/logo2-nofondo.png" ).toExternalForm () ) );
        primaryStage = stage;
    }
}
