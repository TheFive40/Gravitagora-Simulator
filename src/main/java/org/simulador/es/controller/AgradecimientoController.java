package org.simulador.es.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AgradecimientoController implements Initializable {

    @FXML
    private Button agradecimientoButton;
    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {

    }

    public void handleClose ( ActionEvent event ) {
      Stage stage = (Stage) agradecimientoButton.getScene ().getWindow ();
      stage.close ();
    }
}
