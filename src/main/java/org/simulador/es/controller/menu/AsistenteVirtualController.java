package org.simulador.es.controller.menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import util.General;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class AsistenteVirtualController implements Initializable {
    @FXML
    private TextField textFieldMensaje;
    @FXML
    private Label lblTitulo;
    @FXML
    private Button buttonEnviar;

    @FXML
    private TextArea textAreaRespuesta;
    @FXML
    private ListView<String> listViewModels;

    private String modelo = "QuantumQ AI";

    @FXML
    void eventoEnviarMensajeButton ( ActionEvent event ) throws IOException {
        AtomicReference<String> respuesta = new AtomicReference<> ( "" );
        switch (modelo) {
            case "QuantumQ AI" -> {
                textAreaRespuesta.appendText ( "QuantumQ AI \n" );
                respuesta.set ( General.askChatGPT ( textFieldMensaje.getText ( ) ) );
            }
            case "GalileoGPT AI" -> {
                textAreaRespuesta.appendText ( "GalileoGPT AI \n" );
                respuesta.set ( General.askGalileoGPT ( textFieldMensaje.getText ( ) ) );
            }
            case "Tesla AI" -> {
                textAreaRespuesta.appendText ( "Tesla AI \n" );
                respuesta.set ( General.askTeslaAI ( textFieldMensaje.getText ( ) ) );
            }
            case "Newton AI" -> {
                textAreaRespuesta.appendText ( "Newton AI \n" );
                respuesta.set ( General.askNewtonAI ( textFieldMensaje.getText ( ) ) );
            }
            default -> System.out.println ( );
        }
        textAreaRespuesta.appendText ( respuesta + "\n" );
        textAreaRespuesta.appendText ( "\n \n" );
        General.conversacion = textAreaRespuesta.getText ( );
    }

    @FXML
    void eventoClickModelo ( MouseEvent event ) {
        String select = listViewModels.getSelectionModel ( ).getSelectedItem ( );
        switch (select) {
            case "QuantumQ AI" -> {
                lblTitulo.setText ( "Asistente Virtual - " + "QuantumQ AI" );
                textAreaRespuesta.setText ( "" );
                modelo = "QuantumQ AI";
                General.conversacion  ="";
            }
            case "GalileoGPT AI" -> {
                lblTitulo.setText ( "Asistente Virtual - " + "GalileoGPT AI" );
                textAreaRespuesta.setText ( "" );
                modelo = "GalileoGPT AI";
                General.conversacion  ="";

            }
            case "Tesla AI" -> {
                lblTitulo.setText ( "Asistente Virtual - " + "Tesla AI" );
                textAreaRespuesta.setText ( "" );
                modelo = "Tesla AI";
                General.conversacion  ="";
            }
            case "Newton AI" -> {
                lblTitulo.setText ( "Asistente Virtual - " + "Newton AI" );
                textAreaRespuesta.setText ( "" );
                modelo = "Newton AI";
                General.conversacion  ="";
            }
        }

    }

    @Override
    public void initialize ( URL url, ResourceBundle resourceBundle ) {
        //Llenamos los items del ListView
        listViewModels.getItems ( ).addAll ( "QuantumQ AI", "GalileoGPT AI", "Tesla AI"
                , "Newton AI" );
    }
}
