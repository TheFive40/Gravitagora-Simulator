package org.simulador.es.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import org.simulador.es.data.LocalStorage;
import org.simulador.es.data.Punto;
import util.General;
import util.animations.TiroParabolicoAnimation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.simulador.es.data.LocalStorage.*;

public class TiroParabolicoController implements Initializable {
    @FXML
    private RadioButton tiroParabolicoRadioButton;

    @FXML
    private TextField textFieldVelocidadInicial;

    @FXML
    private TextField textFieldTiempo;

    @FXML
    private RadioButton caidaLibreRadioButton;

    @FXML
    private RadioButton mruRadioButton;

    @FXML
    private ComboBox<?> comboGravedades;

    @FXML
    private AnchorPane contenedorAnimacion;

    @FXML
    private TextField textFieldVelocidadMru;

    @FXML
    private AnchorPane contenedorPrincipal;

    @FXML
    private RadioButton movCircularRadioButton;
    @FXML
    private TextField textFieldVelocidadProyectil;
    @FXML
    private TextField textFieldMasaProyectil;

    @FXML
    private Circle proyectil;
    @FXML
    private Slider sliderAnguloInicial;
    @FXML
    private TextField textFieldVelocidadObjetoX;
    @FXML
    private TextField textFieldVelocidadObjetoY;
    @FXML
    private Canvas canvaComponent;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private volatile HBox contenedorArena;
    private TiroParabolicoAnimation tiroParabolicoAnimation;


    public void iniciarSimulacion ( ActionEvent event ) {
        canvaComponent.setWidth ( 800 );
        canvaComponent.setHeight ( 410 );
        tiroParabolicoAnimation.setContenedorArena ( contenedorArena );
        tiroParabolicoAnimation.setCanvaProperty ( canvaComponent );
        tiroParabolicoAnimation.setMasa ( Double.parseDouble ( textFieldMasaProyectil.getText ( ) ) );
        tiroParabolicoAnimation.setVelocidadInicial ( Double.parseDouble ( textFieldVelocidadProyectil.getText ( ) ) );
        tiroParabolicoAnimation.setAngulo ( sliderAnguloInicial.getValue ( ) );
        tiroParabolicoAnimation.setTextFieldVelocidadX ( textFieldVelocidadObjetoX );
        tiroParabolicoAnimation.setTextFieldVelocidadY ( textFieldVelocidadObjetoY );
        tiroParabolicoAnimation.establecerAnimacion ( );
    }

    @FXML
    void eventoDevelopersMenuItem ( ActionEvent event ) throws IOException {
        Parent parent = General.obtenerContenedorPadre ( General.DEVELOPERS_RUTA );
        Scene scene = new Scene ( parent );
        scene.getStylesheets ( ).add ( getClass ( ).getResource ( "/css/Desarrolladores.css" ).toExternalForm ( ) );
        Stage stage = new Stage ( );
        stage.setTitle ( "Desarrolladores" );
        stage.setResizable ( false );
        stage.setScene ( scene );
        stage.show ( );
    }

    public void eventoMovCircularRadioButton ( ActionEvent event ) {
        General.agregarContenedorPadre ( General.RUTA_MOV_CIRCULAR, contenedorPrincipal );
    }

    public void eventoCaidaLibreRadioButton ( ActionEvent event ) {
        General.agregarContenedorPadre ( General.RUTA_CAIDA_LIBRE, contenedorPrincipal );
    }

    @FXML
    void eventoMruRadioButton ( ActionEvent event ) {
        General.agregarContenedorPadre ( General.RUTA_MRU, contenedorPrincipal );
    }

    public void reiniciarSimulacion ( ActionEvent event ) {
        //GraphicsContext graphicsContext = canvaComponent.getGraphicsContext2D ();
        //graphicsContext.clearRect ( 0,0,canvaComponent.getWidth (),canvaComponent.getHeight () );
        velocidadTiempoMovCircular.set ( FXCollections.observableHashMap ( ) );
        aceleracionTiempoMovCircular.set ( FXCollections.observableHashMap ( ) );
        posicionTiempoMovCircular.set ( FXCollections.observableHashMap ( ) );
        tiroParabolicoRadioButton.fire ( );
    }

    @FXML
    void eventoTiroParabolico ( ActionEvent event ) {
        General.agregarContenedorPadre ( General.RUTA_TIRO_PARABOLICO, contenedorPrincipal );
    }

    @FXML
    void eventoGraficarCaidaLibre ( ActionEvent event ) throws IOException {
        if (LocalStorage.velocidadTiempoCaidaLibre != null && LocalStorage.velocidadTiempoCaidaLibre.isEmpty ( )) {
            General.saltarAlertasMenuItem ( );
            return;
        } else if (LocalStorage.velocidadTiempoCaidaLibre == null) {
            General.saltarAlertasMenuItem ( );
            return;
        }
        Parent parent = General.obtenerContenedorPadre ( General.RUTA_CAIDA_LIBRE_GRAFICO );
        Scene scene = new Scene ( parent );
        scene.getStylesheets ( ).add ( getClass ( ).getResource ( "/css/EstiloGraficos.css" ).toExternalForm ( ) );
        Stage stage = new Stage ( );
        stage.setScene ( scene );
        stage.show ( );
    }

    @FXML
    void eventoGraficarMru ( ActionEvent event ) throws IOException {
        //Este codigo verifica posibles errores antes de iniciar a graficar
        if (LocalStorage.velocidadTiempoMru != null && LocalStorage.velocidadTiempoMru.isEmpty ( )) {
            General.saltarAlertasMenuItem ( );
            return;
        } else if (LocalStorage.velocidadTiempoMru == null) {
            General.saltarAlertasMenuItem ( );
            return;
        }
        //Aqui termina el codigo de la verificacion
        Parent parent = General.obtenerContenedorPadre ( General.RUTA_MRU_GRAFICO );
        Scene scene = new Scene ( parent );
        scene.getStylesheets ( ).add ( getClass ( ).getResource ( "/css/EstiloGraficos.css" ).toExternalForm ( ) );
        Stage stage = new Stage ( );
        stage.setScene ( scene );
        stage.show ( );


    }

    @FXML
    void graficarMovCircularEvent ( ActionEvent event ) throws IOException {
        //Este codigo verifica posibles errores antes de iniciar a graficar
        if (LocalStorage.velocidadTiempoMovCircular != null && LocalStorage.velocidadTiempoMovCircular.isEmpty ( )) {
            General.saltarAlertasMenuItem ( );
            return;
        } else if (LocalStorage.velocidadTiempoMovCircular == null) {
            General.saltarAlertasMenuItem ( );
            return;
        }
        //Aqui termina el codigo de la verificacion
        Parent parent = General.obtenerContenedorPadre ( General.RUTA_MOV_CIRCULAR_GRAFICO );
        Scene scene = new Scene ( parent );
        scene.getStylesheets ( ).add ( getClass ( ).getResource ( "/css/EstiloGraficos.css" ).toExternalForm ( ) );
        Stage stage = new Stage ( );
        stage.setScene ( scene );
        stage.show ( );
    }

    @FXML
    void graficarTiroParabolicoEvent ( ActionEvent event ) throws IOException {
        //Este codigo verifica posibles errores antes de iniciar a graficar
        if (LocalStorage.xVelocidadTiempoTiroParabolico != null && LocalStorage.yVelocidadTiempoTiroParabolico.isEmpty ( )) {
            General.saltarAlertasMenuItem ( );
            return;
        } else if (LocalStorage.xVelocidadTiempoTiroParabolico == null) {
            General.saltarAlertasMenuItem ( );
            return;
        }
        //Aqui termina el codigo de la verificacion
        Parent parent = General.obtenerContenedorPadre ( General.RUTA_TIRO_PARABOLICO_GRAFICO );
        Scene scene = new Scene ( parent );
        scene.getStylesheets ( ).add ( getClass ( ).getResource ( "/css/EstiloGraficos.css" ).toExternalForm ( ) );
        Stage stage = new Stage ( );
        stage.setScene ( scene );
        stage.show ( );
    }

    @FXML
    void GptMenuItemEvent ( ActionEvent event ) throws IOException {
        Parent parent = General.obtenerContenedorPadre ( General.RUTA_GPT_4 );
        Stage stage = new Stage ( );
        Scene scene = new Scene ( parent );
        stage.setTitle ( "Asistente Virtual" );
        scene.getStylesheets ( ).add ( getClass ( ).getResource ( "/css/EstilosAsistenteVirtual.css" ).toExternalForm ( ) );
        stage.setScene ( scene );
        stage.show ( );
    }

    private void zoomEvent () {
        contenedorAnimacion.addEventFilter ( ScrollEvent.ANY, event -> {
            if (event.isControlDown ( )) {
                double zoomFactor = 1.05;
                double deltaY = event.getDeltaY ( );
                zoomFactor = (deltaY < 0) ? 2.0 - zoomFactor : zoomFactor;
                contenedorAnimacion.setScaleX ( contenedorAnimacion.getScaleX ( ) * zoomFactor );
                contenedorAnimacion.setScaleY ( contenedorAnimacion.getScaleY ( ) * zoomFactor );
                event.consume ( );
            }
        } );
    }

    private void clicarMouseEvent () {
        canvaComponent.setOnMouseClicked ( e -> {
            double x = e.getX ( );
            double y = e.getY ( );
            double endY = 0;
            for (Punto punto : puntoObservableList) {
                int contador = 0;
                for (Integer i : punto.getX1 ( )) {
                    int y0 = punto.getY1 ( ).get ( contador );
                    if (i == x && y == y0) {
                        //Establecemos la velocidad de las componentes del x,y en el TextField
                        textFieldVelocidadObjetoX.setText ( punto.getVelocidadX ( ) + " m/s²" );
                        textFieldVelocidadObjetoY.setText ( punto.getVelocidadY ( ) + " m/s²" );
                        //Calculos necesarios para dibujar el vector en el canvas
                        double arrowHeadSize = 5;
                        double endX = x + 20;
                        GraphicsContext gc = canvaComponent.getGraphicsContext2D ( );
                        if (yVelocidadTiempoTiroParabolico.get ( punto.getTiempo ( ) ) > 0) {
                            y = y + 4;
                            endY = y + 20;
                            gc.strokeLine ( x, y, x, endY );
                            gc.strokeLine ( x, y, x + 20, y );
                            gc.fillPolygon ( new double[]{x, (x - arrowHeadSize / 2), (x + arrowHeadSize / 2)},
                                    new double[]{endY, (endY - arrowHeadSize / 2), (endY - arrowHeadSize / 2)},
                                    3 );
                            gc.fillPolygon (
                                    new double[]{endX, (endX - arrowHeadSize), (endX - arrowHeadSize)},
                                    new double[]{y, (y - arrowHeadSize / 2), (y + arrowHeadSize / 2)},
                                    3
                            );
                            return;
                        }
                        y = y - 1;
                        gc.strokeLine ( x, y, x + 20, y );
                        gc.setFill ( Color.BLACK );
                        gc.fillPolygon (
                                new double[]{endX, (endX - arrowHeadSize), (endX - arrowHeadSize)},
                                new double[]{y, (y - arrowHeadSize / 2), (y + arrowHeadSize / 2)},
                                3
                        );
                        endY = y - 20;
                        gc.strokeLine ( x, y, x, endY );
                        gc.fillPolygon (
                                new double[]{x, (x - arrowHeadSize / 2), (x + arrowHeadSize / 2)},
                                new double[]{endY, endY + arrowHeadSize, endY + arrowHeadSize},
                                3
                        );
                        return;
                    }
                    contador++;
                }
            }
        } );
    }

    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
        tiroParabolicoRadioButton.setSelected ( true );
        tiroParabolicoAnimation = new TiroParabolicoAnimation ( proyectil, contenedorAnimacion );
        sliderAnguloInicial.setMin ( 0 );
        sliderAnguloInicial.setMax ( 360 );
        sliderAnguloInicial.setMajorTickUnit ( 45 );
        sliderAnguloInicial.setMinorTickCount ( 0 );
        //Metodos
        zoomEvent ( );
        clicarMouseEvent ( );
        sliderAnguloInicial.setOnMouseDragged ( e-> {
            double value = sliderAnguloInicial.getValue ( );
            for(int i = 0;i<360;i+=45){
                if(value>i && value<(i+45)){
                    sliderAnguloInicial.setValue ( (i+45) );
                    break;
                }
            }
        } );
    }
}
