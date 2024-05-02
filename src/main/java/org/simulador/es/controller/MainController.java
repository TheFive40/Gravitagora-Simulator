package org.simulador.es.controller;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;
import util.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import org.simulador.es.data.Gravedad;
import org.simulador.es.model.CaidaLibre;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
    private TextField textFieldVelocidadInicial;
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
        caidaLibre.setVelocidadTiempo(mapaVelocidadTiempo);
    }

    @FXML
    public void eventoEstablecerGravedad(ActionEvent event) {
        String gravedades = comboGravedades.getSelectionModel().getSelectedItem();
        switch (gravedades) {
            case "Tierra" -> {
                caidaLibre.setGravedad(Gravedad.TIERRA);
            }
            case "Luna" -> {
                caidaLibre.setGravedad(Gravedad.LUNA);
            }
            case "Marte" -> {
                caidaLibre.setGravedad(Gravedad.MARTE);
            }
            case "Jupiter" -> {
                caidaLibre.setGravedad(Gravedad.JUPITER);
            }
            case "Saturno" -> {
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
    public void eventoRadioButtonMru(ActionEvent event) throws IOException {
        General.agregarContenedorPadre("/view/MruFXML.fxml",contenedorPrincipal);
    }
    @FXML
    void eventoMovCircularRadioButton(ActionEvent event) throws IOException {
        General.agregarContenedorPadre("/view/MovCircularFXML.fxml",contenedorPrincipal);
    }
    @FXML
    void eventoTiroParabolicoRadioButton(ActionEvent event) throws IOException {
        General.agregarContenedorPadre("/view/TiroParabolicoFXML.fxml",contenedorPrincipal);
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
