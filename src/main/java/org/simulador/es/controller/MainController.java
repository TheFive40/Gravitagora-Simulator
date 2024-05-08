package org.simulador.es.controller;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import util.animations.CaidaLibre;

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
    private TextField textFieldTiempo;

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

    private CaidaLibre caidaLibre;

    @FXML
    public void iniciarSimulacion(ActionEvent event) {
        particula.setVisible(true);
        caidaLibre.setTiempo(Integer.parseInt(textFieldTiempo.getText()));
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
        switch (gravedades) {
            case "Tierra" -> {
                caidaLibre.setGravedad(Gravedad.TIERRA);
                caidaLibre.setGravedad(Gravedad.TIERRA);
            }
            case "Luna" -> {
                caidaLibre.setGravedad(Gravedad.LUNA);
                caidaLibre.setGravedad(Gravedad.LUNA);

            }
            case "Marte" -> {
                caidaLibre.setGravedad(Gravedad.MARTE);
                caidaLibre.setGravedad(Gravedad.MARTE);

            }
            case "Jupiter" -> {
                caidaLibre.setGravedad(Gravedad.JUPITER);
                caidaLibre.setGravedad(Gravedad.JUPITER);

            }
            case "Saturno" -> {
                caidaLibre.setGravedad(Gravedad.SATURNO);
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
    void eventoGpt4MenuItem(ActionEvent event) throws IOException {
        Parent parent = General.obtenerContenedorPadre(General.RUTA_GPT_4);
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Inicializacion de objetos
        caidaLibre = new CaidaLibre(particula, contenedorAnimacion);
        //Setteo de Atributos a los Objetos previamente Definidos
        comboGravedades.getItems().addAll("Tierra", "Luna", "Marte", "Jupiter"
                , "Saturno");
        caidaLibreRadioButton.setSelected(true);

    }
}
