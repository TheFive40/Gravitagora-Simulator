package org.simulador.es.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import util.animations.MovCircular;
import util.General;

import java.net.URL;
import java.util.ResourceBundle;

public class MovCircularController implements Initializable {

    @FXML
    private RadioButton tiroParabolicoRadioButton;

    @FXML
    private TextField textFieldVelocidadInicial;

    @FXML
    private Slider sliderAnguloInicial;

    @FXML
    private TextField textFieldRadio;
    @FXML
    private TextField textFieldVelocidadAngular;
    @FXML
    private TextField textFieldMovCircular;
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
    private TextField textFieldVelocidadMru;

    @FXML
    private Circle particula;

    @FXML
    private AnchorPane contenedorPrincipal;

    @FXML
    private RadioButton movCircularRadioButton;

    private MovCircular movCircular;



    public void reiniciarSimulacion(ActionEvent event) {

    }

    public void iniciarSimulacion(ActionEvent event) {
        double frecuenciaAngular = Double.parseDouble(textFieldVelocidadAngular.getText());
        movCircular.setTiempo(Integer.parseInt(textFieldMovCircular.getText()));
        movCircular.setRadio(Double.parseDouble(textFieldRadio.getText()));
        //movCircular.setAngulo(Integer.parseInt(String.valueOf(sliderAnguloInicial.getValue())));
        movCircular.establecerAnimacion(Math.PI * frecuenciaAngular);
    }

    public void eventoTiroParabolicoRadioButton(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_TIRO_PARABOLICO, contenedorPrincipal);

    }
    public void eventoMruRadioButton(ActionEvent event){
        General.agregarContenedorPadre(General.RUTA_MRU, contenedorPrincipal);

    }
    public void eventoCaidaLibreRadioButton(ActionEvent event){
        General.agregarContenedorPadre(General.RUTA_CAIDA_LIBRE, contenedorPrincipal);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movCircular = new MovCircular(particula,contenedorAnimacion);
        movCircularRadioButton.setSelected(true);
    }
}
