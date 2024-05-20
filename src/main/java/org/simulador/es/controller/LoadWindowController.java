package org.simulador.es.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.simulador.es.LoadWindow;
import util.General;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class LoadWindowController implements Initializable {
    @FXML
    private ProgressBar progressIndicator;
    @FXML
    private VBox loadingWindow;

    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
        AtomicReference<Double> progress = new AtomicReference<> (  0.0);
        Timeline timeline = new Timeline ( new KeyFrame ( Duration.millis ( 50 ),e-> {
            progress.set ( progress.get () + 0.01 );
            progressIndicator.setProgress ( progress.get () );
        }));
        timeline.setCycleCount ( 100 );
        timeline.play ();
        timeline.setOnFinished ( (x)->{
            LoadWindow.primaryStage.hide ();
            try {
                Stage stage = new Stage ( );
                stage.setScene (  new Scene(
                        new FXMLLoader ( getClass ().getResource ( General.RUTA_CAIDA_LIBRE ) ).load () ));
                stage.setTitle("Gravitagora Simulator");
                stage.setResizable(false);
                stage.getIcons ().add ( new Image ( getClass ().getResource ( "/images/logo2-nofondo.png" ).toExternalForm () ) );

                stage.show ();
            } catch (IOException e) {
                throw new RuntimeException ( e );
            }
        } );
    }
}
