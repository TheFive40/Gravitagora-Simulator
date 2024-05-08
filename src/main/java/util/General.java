package util;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import javafx.beans.property.MapProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import org.simulador.es.model.MruModel;

import java.io.IOException;

public class General {
    public static String RUTA_MRU = "/view/MruFXML.fxml";
    public static String RUTA_TIRO_PARABOLICO = "/view/TiroParabolicoFXML.fxml";
    public static String RUTA_CAIDA_LIBRE = "/view/MainFXML.fxml";
    public static String RUTA_MOV_CIRCULAR = "/view/MovCircularFXML.fxml";
    public static String RUTA_GPT_4 = "/view/Gpt4FXML.fxml";
    public static String RUTA_MRU_GRAFICO = "/view/MruGraficoFXML.fxml";

    public static String RUTA_CAIDA_LIBRE_GRAFICO = "/view/CaidaLibreGraficoFXML.fxml";
    public static final String API_URL = "https://api.openai.com/v1/chat/completions";
    public static final String API_KEY = "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";
    private static String MODEL = "gpt-3.5-turbo";
    public static int tiempoAnimacion;
    public static void mostrarMensajeAlerta(String titulo, String cabecera, String contenido,
                                            Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);
        alert.show();
    }

    public static Parent obtenerContenedorPadre(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(General.class.getResource(url));
        Parent parent = fxmlLoader.load();
        return parent;
    }

    public static void agregarContenedorPadre(String url, AnchorPane contenedor) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(General.class.getResource(url));
            Parent parent = fxmlLoader.load();
            contenedor.getChildren().clear();
            contenedor.getChildren().addAll(parent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public static String askChatGPT(String question) {
        //OpenAiService service = new OpenAiService("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");
        OpenAiService service = new OpenAiService("sk-proj-RXokl1R1wicgMUhUpyDOT3BlbkFJRpm2rRQDj7qQFFsyBxHK");
        question.replaceAll("`", "");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("Tu área de especialización será la física mecánica\n"
                        + "Estarás a cargo de resolver preguntas y problemas relacionados con el mundo físico en movimiento\n" +
                        question + "`")
                .model("gpt-3.5-turbo-instruct")
                .echo(true)
                .temperature(1.0)
                .maxTokens(100)
                .build();
        CompletionResult result = service.createCompletion(completionRequest);
        return getText(result.getChoices().get(0).getText());
    }


    private static String getText(String answer) {
        int index = answer.indexOf("`") + 2;
        String textFinal = "";
        for (int i = index; i < answer.length(); i++) {
            textFinal += answer.charAt(i);
        }
        return textFinal;
    }

    public static void saltarAlertasMenuItem() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("¡No hay datos en la grafica!");
        alert.setHeaderText("¡Error al Graficar!");
        alert.setContentText("Debe iniciar la simulación antes de ir a este item" +
                "\nPor favor intente mas tarde");
        alert.show();
    }
}
