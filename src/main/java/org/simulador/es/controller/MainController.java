package org.simulador.es.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import org.simulador.es.model.CaidaLibre;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private RadioButton tiroParabolicoRadioButton;

    @FXML
    private RadioButton mruRadioButton;

    @FXML
    private TextField aceleracionTextField;

    @FXML
    private RadioButton mruaRadioButton;

    @FXML
    private TextField masaTextField;

    @FXML
    private Shape particula;

    @FXML
    private RadioButton movCircularRadioButton;
    @FXML
    private AnchorPane contenedorAnimacion;

    @FXML
    private TextField anguloTextField;
    private CaidaLibre caidaLibre;
    public void iniciarSimulacion(ActionEvent actionEvent) {
        caidaLibre.establecerAnimacion();
    }
    public void reiniciarSimulacion(ActionEvent actionEvent){
        particula.setTranslateY(0.0);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        caidaLibre = new CaidaLibre(particula, contenedorAnimacion);
    }
}
