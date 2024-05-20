package util;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class General {
    public static String RUTA_MRU = "/view/MruFXML.fxml";
    public static String RUTA_TIRO_PARABOLICO = "/view/TiroParabolicoFXML.fxml";
    public static String RUTA_CAIDA_LIBRE = "/view/MainFXML.fxml";
    public static String RUTA_MOV_CIRCULAR = "/view/MovCircularFXML.fxml";
    public static String RUTA_GPT_4 = "/view/vistasmenu/AsistenteVirtualFXML.fxml";
    public static String RUTA_MRU_GRAFICO = "/view/graficos/MruGraficoFXML.fxml";
    public static String RUTA_MOV_CIRCULAR_GRAFICO = "/view/graficos/MovCircularGraficoFXML.fxml";
    public static String RUTA_CAIDA_LIBRE_GRAFICO = "/view/graficos/CaidaLibreGraficoFXML.fxml";
    public static String RUTA_TIRO_PARABOLICO_GRAFICO = "/view/graficos/TiroParabolicoGraficoFXML.fxml";
    public static String DEVELOPERS_RUTA = "/view/vistasmenu/DesarrolladoresFXML.fxml";
    public static String RUTA_LUNA_GRAVEDAD = "/view/caidalibre/LunaFXML.fxml";
    public static String RUTA_MARTE_GRAVEDAD = "/view/caidalibre/MarteFXML.fxml";
    public static String RUTA_SATURNO_GRAVEDAD = "/view/caidalibre/SaturnoFXML.fxml";
    public static String RUTA_JUPITER_GRAVEDAD = "/view/caidalibre/JupiterFXML.fxml";
    public static String tablaValores;
    public static String conversacion;
    public static final String API_URL = "https://api.openai.com/v1/chat/completions";
    public static final String API_KEY2 = "sk-proj-LEWjfQbV58XdHOlkaJNuT3BlbkFJ2hSP5avfhOrZN1ncoY6r";
    public static final String API_KEY_3  = "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";
    public static final String API_KEY_4 = "sk-proj-gcuRlbWte9Gu8MpXq6UrT3BlbkFJnpliiAEpivVaAVCf4Bud";;
    private static String MODEL = "gpt-3.5-turbo";
    public static int tiempoAnimacion;

    public static void mostrarMensajeAlerta ( String titulo, String cabecera, String contenido,
                                              Alert.AlertType tipo ) {
        Alert alert = new Alert ( tipo );
        alert.setTitle ( titulo );
        alert.setHeaderText ( cabecera );
        alert.setContentText ( contenido );
        alert.show ( );
    }

    public static Parent obtenerContenedorPadre ( String url ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader ( General.class.getResource ( url ) );
        Parent parent = fxmlLoader.load ( );
        return parent;
    }

    public static void agregarContenedorPadre ( String url, AnchorPane contenedor ) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader ( General.class.getResource ( url ) );
            Parent parent = fxmlLoader.load ( );
            contenedor.getChildren ( ).clear ( );
            contenedor.getChildren ( ).addAll ( parent );
        } catch (Exception exception) {
            exception.printStackTrace ( );
        }

    }

    public static String askChatGPT ( String question ) {
        //OpenAiService service = new OpenAiService("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");
        String contexto = "Eres un Asistente Virtual llamado Richard Door AI creado por Jeanfranco Boom Bolaño tu área de especialización será la física mecánica\n"
                + "Estarás a cargo de resolver preguntas y problemas relacionados con el mundo físico en movimiento\n" +
                "Ademas de eso, si el usuario te hace alguna pregunta relacionada a algun movimiento en especifico \n" +
                "Toma en cuenta las siguientes tablas de valores: \n" + tablaValores + "\n Ahora te voy a dar el contexto de la conversacion \n" +
                "(En caso de estar vacio ignora esto ultimo): " + conversacion + "\n Algo mas cuando des la respuesta NO coloques en ella tu nombre\n " +
                "Pregunta del usuario: ";
        OpenAiService service = new OpenAiService ( "sk-tn58wyUba4DFLWHm1b96F5D13dD14f40Bd4bE36fB54a8358" );
        question.replaceAll ( "`", "" );
        CompletionRequest completionRequest = CompletionRequest.builder ( )
                .prompt ( contexto +
                        question + "`" )
                .model ( "gpt-3.5-turbo-instruct" )
                .echo ( true )
                .temperature ( 1.0 )
                .maxTokens ( 100 )
                .build ( );
        CompletionResult result = service.createCompletion ( completionRequest );
        return getText ( result.getChoices ( ).get ( 0 ).getText ( ) );
    }


    private static String getText ( String answer ) {
        int index = answer.indexOf ( "`" ) + 2;
        String textFinal = "";
        for (int i = index; i < answer.length ( ); i++) {
            textFinal += answer.charAt ( i );
        }
        return textFinal;
    }

    public static void saltarAlertasMenuItem () {
        Alert alert = new Alert ( Alert.AlertType.WARNING );
        alert.setTitle ( "¡No hay datos en la grafica!" );
        alert.setHeaderText ( "¡Error al Graficar!" );
        alert.setContentText ( "Debe iniciar la simulación antes de ir a este item" +
                "\nPor favor intente mas tarde" );
        alert.show ( );
    }
}
