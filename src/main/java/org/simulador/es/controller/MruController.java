package org.simulador.es.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.simulador.es.data.LocalStorage;
import util.animations.MruAnimation;
import util.General;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class MruController implements Initializable {
    @FXML
    private RadioButton tiroParabolicoRadioButton;

    @FXML
    private TextField textFieldVelocidadInicial;
    @FXML
    private TextField textFieldTiempoMru;

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
    private AnchorPane contenedorPrincipal;

    @FXML
    private Rectangle vehiculo;

    @FXML
    private TextField textFieldVelocidadMru;
    @FXML
    private TextField textFieldVelocidadObjeto;

    @FXML
    private Circle ruedaDelantera;

    @FXML
    private RadioButton movCircularRadioButton;

    @FXML
    private Circle ruedaTrasera;

    private MruAnimation mru;
    public static boolean limiteDerecha = false;
    public static boolean limiteIzquierda = true;

    @FXML
    void eventoCaidaLibreRadioButton ( ActionEvent event ) throws IOException {
        General.agregarContenedorPadre ( General.RUTA_CAIDA_LIBRE, contenedorPrincipal );
    }

    @FXML
    void eventoMovCircularRadioButton ( ActionEvent event ) throws IOException {
        General.agregarContenedorPadre ( General.RUTA_MOV_CIRCULAR, contenedorPrincipal );
    }

    @FXML
    void eventoTiroParabolicoRadioButton ( ActionEvent event ) throws IOException {
        General.agregarContenedorPadre ( General.RUTA_TIRO_PARABOLICO, contenedorPrincipal );

    }
    @FXML
    void eventoAgradecimientos(ActionEvent event) throws IOException {
        Parent parent = General.obtenerContenedorPadre(General.RUTA_AGRADECIMIENTOS);
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/css/Agradecimientos.css").toExternalForm());
        Stage stage = new Stage();
        stage.setTitle("Agradecimientos");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void iniciarSimulacion ( ActionEvent event ) {
        double velocidad  = Double.parseDouble ( textFieldVelocidadMru.getText (  ) );
        if(!limiteDerecha && limiteIzquierda && velocidad>0){
            limiteIzquierda = false;
        }

        if (Double.parseDouble ( textFieldVelocidadMru.getText ( ) ) > 70 || (limiteDerecha
         && Double.parseDouble ( textFieldVelocidadMru.getText (  ) )>0) || limiteIzquierda &&
        Double.parseDouble ( textFieldVelocidadMru.getText (  ))<0) {
            General.mostrarMensajeAlerta ( "Has excedido el limite",
                    "¡No puedes colocar valores muy grandes!", "Has excedido el limite" +
                            " de velocidad permitido \nde la animación por favor intenta con" +
                            "\n valores mas pequeños y realistas", Alert.AlertType.WARNING );
            return;
        }
        mru.setTextFieldTiempo ( textFieldTiempoMru );
        mru.setVelocidad ( Double.parseDouble ( textFieldVelocidadMru.getText ( ) ) );
        mru.setTextFieldVelocidadObjeto ( textFieldVelocidadObjeto );
        mru.establecerAnimacion ( );
        General.condicionesIniciales = " Velocidad: : " + textFieldVelocidadMru.getText ( ) + "\n";
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
    void eventoGraficarMovCircular ( ActionEvent event ) throws IOException {
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
    void eventoGraficarTiroParabolico ( ActionEvent event ) throws IOException {
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
    void eventoGpt4MenuItem ( ActionEvent event ) throws IOException {
        Parent parent = General.obtenerContenedorPadre ( General.RUTA_GPT_4 );
        Stage stage = new Stage ( );
        Scene scene = new Scene ( parent );
        stage.setTitle ( "Asistente Virtual" );
        scene.getStylesheets ( ).add ( getClass ( ).getResource ( "/css/EstilosAsistenteVirtual.css" ).toExternalForm ( ) );
        stage.setScene ( scene );
        stage.show ( );
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

    @FXML
    void reiniciarSimulacion ( ActionEvent event ) {
        ruedaTrasera.setTranslateX ( 0.0 );
        vehiculo.setTranslateX ( 0.0 );
        ruedaDelantera.setTranslateX ( 4.0 );
        limiteDerecha = false;
    }

    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
        //Inicializacion de objetos
        mru = new MruAnimation ( ruedaTrasera, ruedaDelantera, vehiculo, contenedorAnimacion );

    }
}
