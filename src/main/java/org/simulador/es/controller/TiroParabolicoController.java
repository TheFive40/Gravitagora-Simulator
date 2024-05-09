package org.simulador.es.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import util.General;
import util.animations.TiroParabolicoAnimation;

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
    private TiroParabolicoAnimation tiroParabolicoAnimation;


    public void iniciarSimulacion(ActionEvent event) {
        tiroParabolicoAnimation.setMasa(Double.parseDouble(textFieldMasaProyectil.getText()));
        tiroParabolicoAnimation.setVelocidadInicial(Double.parseDouble(textFieldVelocidadProyectil.getText()));
        tiroParabolicoAnimation.setAngulo(sliderAnguloInicial.getValue());
        tiroParabolicoAnimation.establecerAnimacion();
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
        velocidadTiempoMovCircular.set(FXCollections.observableHashMap());
        aceleracionTiempoMovCircular.set(FXCollections.observableHashMap());
        posicionTiempoMovCircular.set(FXCollections.observableHashMap());
        General.mostrarMensajeAlerta("Datos limpiados",
                "La animación ha sido reestablecida", "Los datos anteriores de" +
                        " la animacion anterior han sido eliminado exitosamente ", Alert.AlertType.INFORMATION);
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
