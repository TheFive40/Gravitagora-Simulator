package org.simulador.es.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.General;
import java.net.URL;
import java.util.ResourceBundle;

public class AsistenteVirtualController implements Initializable {
    @FXML
    private TextField textFieldMensaje;

    @FXML
    private Button buttonEnviar;

    @FXML
    private TextArea textAreaRespuesta;

    @FXML
    void eventoEnviarMensajeButton ( ActionEvent event ) {
        String respuesta = General.askChatGPT ( textFieldMensaje.getText ( ) );
        textAreaRespuesta.appendText ( "Richard Door AI \n" );
        textAreaRespuesta.appendText ( respuesta + "\n" );
        textAreaRespuesta.appendText ( "\n \n" );

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
