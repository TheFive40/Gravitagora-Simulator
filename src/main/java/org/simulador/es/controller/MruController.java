package org.simulador.es.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import org.simulador.es.model.Mru;
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

    private Mru mru;

    @FXML
    void eventoCaidaLibreRadioButton(ActionEvent event) throws IOException {
        General.agregarContenedorPadre("/view/MainFXML.fxml", contenedorPrincipal);
    }

    @FXML
    void eventoMovCircularRadioButton(ActionEvent event) throws IOException {
        General.agregarContenedorPadre("/view/MovCircularFXML.fxml",contenedorPrincipal);
    }
    @FXML
    void eventoTiroParabolicoRadioButton(ActionEvent event) throws IOException {
        General.agregarContenedorPadre("/view/TiroParabolicoFXML.fxml",contenedorPrincipal);

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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Inicializacion de objetos
        mru = new Mru(ruedaTrasera, ruedaDelantera, vehiculo);
        //Setteo de Atributos a los Objetos previamente Definidos
        mruRadioButton.setSelected(true);
        comboGravedades.setDisable(true);
        textFieldTiempo.setDisable(true);
        textFieldVelocidadInicial.setDisable(true);
    }
}
