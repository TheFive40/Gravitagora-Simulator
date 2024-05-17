package org.simulador.es.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.simulador.es.data.LocalStorage;
import util.General;
import util.animations.TiroParabolicoAnimation;

import javax.swing.*;
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
    private volatile HBox contenedorArena;
    private TiroParabolicoAnimation tiroParabolicoAnimation;


    public void iniciarSimulacion(ActionEvent event) {
        tiroParabolicoAnimation.setContenedorArena ( contenedorArena );
        tiroParabolicoAnimation.setCanvaProperty ( canvaComponent );
        tiroParabolicoAnimation.setMasa(Double.parseDouble(textFieldMasaProyectil.getText()));
        tiroParabolicoAnimation.setVelocidadInicial(Double.parseDouble(textFieldVelocidadProyectil.getText()));
        tiroParabolicoAnimation.setAngulo(sliderAnguloInicial.getValue());
        tiroParabolicoAnimation.setTextFieldVelocidadX(textFieldVelocidadObjetoX);
        tiroParabolicoAnimation.setTextFieldVelocidadY(textFieldVelocidadObjetoY);
        tiroParabolicoAnimation.establecerAnimacion();
    }
    @FXML
    void eventoDevelopersMenuItem(ActionEvent event) throws IOException {
        Parent parent = General.obtenerContenedorPadre(General.DEVELOPERS_RUTA);
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/css/Desarrolladores.css").toExternalForm());
        Stage stage = new Stage();
        stage.setTitle("Desarrolladores");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public void eventoMovCircularRadioButton(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_MOV_CIRCULAR, contenedorPrincipal);
    }

    public void eventoCaidaLibreRadioButton(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_CAIDA_LIBRE, contenedorPrincipal);
    }

    @FXML
    void eventoMruRadioButton(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_MRU, contenedorPrincipal);
    }

    public void reiniciarSimulacion(ActionEvent event) {
        //GraphicsContext graphicsContext = canvaComponent.getGraphicsContext2D ();
        //graphicsContext.clearRect ( 0,0,canvaComponent.getWidth (),canvaComponent.getHeight () );
        velocidadTiempoMovCircular.set(FXCollections.observableHashMap());
        aceleracionTiempoMovCircular.set(FXCollections.observableHashMap());
        posicionTiempoMovCircular.set(FXCollections.observableHashMap());
        tiroParabolicoRadioButton.fire ();
    }
    @FXML
    void eventoTiroParabolico( ActionEvent event){
        General.agregarContenedorPadre ( General.RUTA_TIRO_PARABOLICO,contenedorPrincipal );
    }
    @FXML
    void eventoGraficarCaidaLibre(ActionEvent event) throws IOException {
        if (LocalStorage.velocidadTiempoCaidaLibre != null && LocalStorage.velocidadTiempoCaidaLibre.isEmpty()) {
            General.saltarAlertasMenuItem();
            return;
        } else if (LocalStorage.velocidadTiempoCaidaLibre == null) {
            General.saltarAlertasMenuItem();
            return;
        }
        Parent parent = General.obtenerContenedorPadre(General.RUTA_CAIDA_LIBRE_GRAFICO);
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/css/EstiloGraficos.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void eventoGraficarMru(ActionEvent event) throws IOException {
        //Este codigo verifica posibles errores antes de iniciar a graficar
        if (LocalStorage.velocidadTiempoMru != null && LocalStorage.velocidadTiempoMru.isEmpty()) {
            General.saltarAlertasMenuItem();
            return;
        } else if (LocalStorage.velocidadTiempoMru == null) {
            General.saltarAlertasMenuItem();
            return;
        }
        //Aqui termina el codigo de la verificacion
        Parent parent = General.obtenerContenedorPadre(General.RUTA_MRU_GRAFICO);
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/css/EstiloGraficos.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    void eventoGraficarMovCircular (ActionEvent event) throws IOException {
        //Este codigo verifica posibles errores antes de iniciar a graficar
        if (LocalStorage.velocidadTiempoMovCircular != null && LocalStorage.velocidadTiempoMovCircular.isEmpty()) {
            General.saltarAlertasMenuItem();
            return;
        } else if (LocalStorage.velocidadTiempoMovCircular == null) {
            General.saltarAlertasMenuItem();
            return;
        }
        //Aqui termina el codigo de la verificacion
        Parent parent = General.obtenerContenedorPadre(General.RUTA_MOV_CIRCULAR_GRAFICO);
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/css/EstiloGraficos.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void eventoGraficarTiroParabolico(ActionEvent event) throws IOException {
        //Este codigo verifica posibles errores antes de iniciar a graficar
        if (LocalStorage.xVelocidadTiempoTiroParabolico != null && LocalStorage.yVelocidadTiempoTiroParabolico.isEmpty()) {
            General.saltarAlertasMenuItem();
            return;
        } else if (LocalStorage.xVelocidadTiempoTiroParabolico == null) {
            General.saltarAlertasMenuItem();
            return;
        }
        //Aqui termina el codigo de la verificacion
        Parent parent = General.obtenerContenedorPadre(General.RUTA_TIRO_PARABOLICO_GRAFICO);
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/css/EstiloGraficos.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void eventoGpt4MenuItem(ActionEvent event) throws IOException {
        Parent parent = General.obtenerContenedorPadre(General.RUTA_GPT_4);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Asistente Virtual");
        scene.getStylesheets().add(getClass().getResource("/css/EstilosAsistenteVirtual.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tiroParabolicoRadioButton.setSelected(true);
        tiroParabolicoAnimation = new TiroParabolicoAnimation(proyectil, contenedorAnimacion);
        sliderAnguloInicial.setMin(0);
        sliderAnguloInicial.setMax(360);
        sliderAnguloInicial.setMajorTickUnit(45);
        sliderAnguloInicial.setMinorTickCount(0);
    }
}
