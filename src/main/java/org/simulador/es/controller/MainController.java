package org.simulador.es.controller;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.simulador.es.data.LocalStorage;
import util.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import org.simulador.es.data.Gravedad;
import util.animations.CaidaLibreAnimation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class MainController implements Initializable {
    @FXML
    private RadioButton tiroParabolicoRadioButton;

    @FXML
    private RadioButton mruRadioButton;

    @FXML
    private ComboBox<String> comboGravedades;

    @FXML
    private RadioButton caidaLibreRadioButton;

    @FXML
    private TextField textFieldAltura;

    @FXML
    private TextField textFieldTiempoObjeto;
    @FXML
    private TextField textFieldVelocidadObjeto;

    @FXML
    private Shape particula;

    @FXML
    private RadioButton movCircularRadioButton;
    @FXML
    private AnchorPane contenedorAnimacion;
    @FXML
    private AnchorPane contenedorPrincipal;

    @FXML
    private TextField textFieldVelocidadMru;
    @FXML
    private TextField textFieldRadio;
    @FXML
    private TextField textFieldMovCircular;
    @FXML
    private TextField textFieldVelocidadAngular;
    @FXML
    private TextField textFieldTiempoMru;
    @FXML
    private TextField textFieldVelocidadInicial;

    @FXML
    private MenuItem menuItemMru;

    @FXML
    private MenuItem menuItemDev;

    @FXML
    private MenuItem menuItemTiroParabolico;

    @FXML
    private MenuItem menuItemGpt4;
    @FXML
    private MenuItem menuItemCaidaLibre;

    @FXML
    private MenuItem menuItemMovCircular;
    @FXML
    private MenuItem menuItemAgradecimientos;

    @FXML
    private Slider sliderAnguloInicial;

    private static CaidaLibreAnimation caidaLibre;
    private static String item = "Tierra";
    @FXML
    public void iniciarSimulacion(ActionEvent event) {
        particula.setVisible(true);
        //caidaLibre.setTiempo(Integer.parseInt(textFieldTiempo.getText()));
        caidaLibre.setAlturaInicial ( Double.parseDouble ( textFieldAltura.getText () ) );
        caidaLibre.setVelocidadInicial(Double.parseDouble(textFieldVelocidadInicial.getText()));
        caidaLibre.setTextFieldTiempoObjeto(textFieldTiempoObjeto);
        caidaLibre.setTextFieldVelocidadObjeto(textFieldVelocidadObjeto);
        caidaLibre.establecerAnimacion();
    }

    @FXML
    public void reiniciarSimulacion(ActionEvent event) {
        particula.setTranslateY(0.0);
        //Reseteamos los datos anteriores que puedan haber en la tabla de Velocidad Contra Tiempo
        MapProperty<Integer, Double> mapaVelocidadTiempo = new SimpleMapProperty<>();
        mapaVelocidadTiempo.set(FXCollections.observableHashMap());
    }

    @FXML
    public void eventoEstablecerGravedad(ActionEvent event) {
        String gravedades = comboGravedades.getSelectionModel().getSelectedItem();
        item = gravedades;
        switch (gravedades) {
            case "Tierra" -> {
                General.agregarContenedorPadre ( General.RUTA_CAIDA_LIBRE,contenedorPrincipal );
                caidaLibre.setGravedad(Gravedad.TIERRA);
            }
            case "Luna" -> {
                General.agregarContenedorPadre ( General.RUTA_LUNA_GRAVEDAD,contenedorPrincipal );
                caidaLibre.setGravedad(Gravedad.LUNA);

            }
            case "Marte" -> {
                General.agregarContenedorPadre ( General.RUTA_MARTE_GRAVEDAD,contenedorPrincipal );
                caidaLibre.setGravedad(Gravedad.MARTE);

            }
            case "Jupiter" -> {
                General.agregarContenedorPadre ( General.RUTA_JUPITER_GRAVEDAD, contenedorPrincipal );
                caidaLibre.setGravedad(Gravedad.JUPITER);

            }
            case "Saturno" -> {
                General.agregarContenedorPadre ( General.RUTA_SATURNO_GRAVEDAD,contenedorPrincipal );
                caidaLibre.setGravedad(Gravedad.SATURNO);

            }
            default -> {
                General.mostrarMensajeAlerta("Opcion invalida",
                        "¡Verifique sus opciones de respuesta!",
                        "Opcion seleccionada no existe. Intente denuevo",
                        Alert.AlertType.WARNING);
            }
        }
    }
    @FXML
    public void eventoAlturaInicialTextField( KeyEvent event ){
        if(event.getCode () == KeyCode.ENTER){
            double alturaInicial = Double.parseDouble ( textFieldAltura.getText () );
            Circle particula1 = (Circle) particula;
            if(alturaInicial < particula1.getRadius ()){
                particula1.setLayoutY ( particula1.getRadius () );
            }else{
                particula1.setLayoutY(alturaInicial);
            }
        }
    }
    @FXML
    public void eventoRadioButtonMru(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_MRU, contenedorPrincipal);
    }

    @FXML
    void eventoMovCircularRadioButton(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_MOV_CIRCULAR, contenedorPrincipal);
    }

    @FXML
    void eventoTiroParabolicoRadioButton(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_TIRO_PARABOLICO, contenedorPrincipal);
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
        //Inicializacion de objetos
        caidaLibre = new CaidaLibreAnimation(particula, contenedorAnimacion);
        caidaLibre.setGravedad ( Gravedad.TIERRA );
        //Setteo de Atributos a los Objetos previamente Definidos
        comboGravedades.getItems().addAll("Tierra", "Luna", "Marte", "Jupiter"
                , "Saturno");
        comboGravedades.getSelectionModel ().select ( item );
        caidaLibreRadioButton.setSelected(true);

    }
}
