package org.simulador.es.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
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

    @FXML
    void eventoCaidaLibreRadioButton(ActionEvent event) throws IOException {
        General.agregarContenedorPadre(General.RUTA_CAIDA_LIBRE, contenedorPrincipal);
    }

    @FXML
    void eventoMovCircularRadioButton(ActionEvent event) throws IOException {
        General.agregarContenedorPadre(General.RUTA_MOV_CIRCULAR,contenedorPrincipal);
    }
    @FXML
    void eventoTiroParabolicoRadioButton(ActionEvent event) throws IOException {
        General.agregarContenedorPadre(General.RUTA_TIRO_PARABOLICO,contenedorPrincipal);

    }
    @FXML
    void iniciarSimulacion(ActionEvent event) {
        mru.setTiempo(Integer.parseInt(textFieldTiempoMru.getText()));
        mru.setVelocidad(Double.parseDouble(textFieldVelocidadMru.getText()));
        mru.setTextFieldVelocidadObjeto(textFieldVelocidadObjeto);
        mru.establecerAnimacion();
    }

    @FXML
    void reiniciarSimulacion(ActionEvent event) {
        ruedaTrasera.setTranslateX(0.0);
        vehiculo.setTranslateX(0.0);
        ruedaDelantera.setTranslateX(4.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Inicializacion de objetos
        mru = new MruAnimation(ruedaTrasera, ruedaDelantera, vehiculo, contenedorAnimacion);

    }
}
