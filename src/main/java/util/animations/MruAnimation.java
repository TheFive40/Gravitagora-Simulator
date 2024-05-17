package util.animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import util.General;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.simulador.es.data.LocalStorage.*;

@Getter
@Setter
public class MruAnimation {

    private Circle ruedaTrasera;

    private Circle ruedaDelantera;

    private Rectangle vehiculo;

    private Timeline timeLine;

    private Double velocidad;

    private TextField textFieldVelocidadObjeto;
    private TextField textFieldTiempo;
    private AnchorPane contenedorPrincipal;

    public MruAnimation ( Circle ruedaTrasera, Circle ruedaDelantera, Rectangle vehiculo, AnchorPane contenedorPrincipal ) {
        this.ruedaTrasera = ruedaTrasera;
        this.ruedaDelantera = ruedaDelantera;
        this.vehiculo = vehiculo;
        this.contenedorPrincipal = contenedorPrincipal;
        velocidadTiempoMru = new SimpleMapProperty<> ( FXCollections.observableHashMap ( ) );
    }

    public void establecerAnimacion () {
        if (getVelocidad ( ) <= 50) {
            AtomicInteger tiempo = new AtomicInteger ( );
            timeLine = new Timeline ( new KeyFrame ( Duration.millis ( 16 ), e -> {
                ruedaTrasera.setTranslateX ( ruedaTrasera.getTranslateX ( ) + getVelocidad ( ) );
                vehiculo.setTranslateX ( vehiculo.getTranslateX ( ) + getVelocidad ( ) );
                ruedaDelantera.setTranslateX ( ruedaDelantera.getTranslateX ( ) + getVelocidad ( ) );
                //Calculamos el desplazamiento de la particula en un intervalo de seg
                double desplazamiento = getVelocidad ( ) * tiempo.getAndIncrement ( );
                textFieldVelocidadObjeto.setText ( desplazamiento + " m" );
                //Guardamos en el LocalStorage
                desplazamientoTiempoMru.put ( tiempo.get ( ), BigDecimal.valueOf ( desplazamiento ).
                        setScale ( 2, RoundingMode.DOWN ).doubleValue ( ) );
                velocidadTiempoMru.put ( tiempo.get ( ), BigDecimal.valueOf ( velocidad ).
                        setScale ( 2, RoundingMode.DOWN ).doubleValue ( ) );
                if ((velocidad >= 0 && vehiculo.getTranslateX ( ) >= (contenedorPrincipal.getWidth ( ) - 100)) ||
                        (velocidad < 0 && vehiculo.getTranslateX ( ) <= 0)) {
                    General.tiempoAnimacion = tiempo.get ( );
                    textFieldTiempo.setText ( tiempo.get ( ) + " s" );
                    timeLine.stop ( );
                    formatoTabla ( );
                }
            } ) );
            //General.tiempoAnimacion = getTiempo();
            timeLine.setCycleCount ( Timeline.INDEFINITE );
            timeLine.play ( );
        } else {
            General.mostrarMensajeAlerta ( "Condiciones Iniciales ",
                    "¡Haz excedido el limite permitido!",
                    "¡Por favor asegurate de dar valores realistas \n" +
                            "Para una mejor experiencia del simulador!",
                    Alert.AlertType.WARNING );
        }
    }

    void formatoTabla () {
        AtomicReference<String> tablaCaidaLibre = new AtomicReference<> ( "\nMovimiento Rectilineo Uniforme \n" );
        tablaCaidaLibre.set ( "Velocidad \t\t\t\tTiempo\n" );
        velocidadTiempoMru.forEach ( ( k, v ) -> {
            tablaCaidaLibre.set ( tablaCaidaLibre.get ( ) + k + "\t\t\t\t\t\t" + v + "\n" );
        } );
        tablaCaidaLibre.set ( tablaCaidaLibre.get ( ) + "Desplazamiento \t\t\tTiempo\n" );
        desplazamientoTiempoMru.forEach ( ( k, v ) -> {
            tablaCaidaLibre.set ( tablaCaidaLibre.get ( ) + k + "\t\t\t\t\t\t" + v + "\n" );
        } );

        General.tablaValores = tablaCaidaLibre.get ( );
    }
}
