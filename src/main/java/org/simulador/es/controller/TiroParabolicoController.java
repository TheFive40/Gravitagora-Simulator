package org.simulador.es.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import util.General;
import util.animations.TiroParabolicoAnimation;

import java.net.URL;
import java.util.ResourceBundle;

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
    private TiroParabolicoAnimation tiroParabolicoAnimation;



    public void iniciarSimulacion(ActionEvent event) {
        tiroParabolicoAnimation.setMasa(Double.parseDouble(textFieldMasaProyectil.getText()));
        tiroParabolicoAnimation.setVelocidadInicial(Double.parseDouble(textFieldVelocidadProyectil.getText()));
        tiroParabolicoAnimation.establecerAnimacion();
    }

    public void eventoMovCircularRadioButton(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_MOV_CIRCULAR,contenedorPrincipal);
    }
    public void eventoCaidaLibreRadioButton(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_CAIDA_LIBRE, contenedorPrincipal);
    }
    @FXML
    void eventoMruRadioButton(ActionEvent event) {
        General.agregarContenedorPadre(General.RUTA_MRU, contenedorPrincipal);
    }

    public void reiniciarSimulacion(ActionEvent event) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tiroParabolicoRadioButton.setSelected(true);
        tiroParabolicoAnimation = new TiroParabolicoAnimation(proyectil, contenedorAnimacion);
    }
}
