package util.animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.simulador.es.data.LocalStorage;
import util.General;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.simulador.es.data.LocalStorage.*;

@Setter
@Getter
public class TiroParabolicoAnimation {

    private double gravedad = 9.81;

    private final double PASO_DE_TIEMPO = 0.02;

    private final double RESISTENCIA_DEL_AIRE = 0.02;

    private double masa = 0.1;

    private double velocidadInicial = 60.0;

    private double angulo = 45.0;

    private double xPosicion;

    private double yPosicion;

    private double xVelocidad;

    private double yVelocidad;

    private Timeline timeline;

    private TextField textFieldVelocidadX, textFieldVelocidadY;

    private Circle projectile;

    private AnchorPane contenedorAnimacion;

    private Canvas canvaProperty;
    private volatile HBox contenedorArena;

    public TiroParabolicoAnimation ( Circle proyectil, AnchorPane contenedorAnimacion ) {
        setProjectile ( proyectil );
        setContenedorAnimacion ( contenedorAnimacion );
    }

    public void establecerAnimacion () {
        // Inicializar posiciones y velocidades
        double angleRadians = Math.toRadians ( angulo );
        xPosicion = 0;
        yPosicion = 0; // Altura inicial
        xVelocidad = velocidadInicial * Math.cos ( angleRadians );
        yVelocidad = -velocidadInicial * Math.sin ( angleRadians );
        AtomicInteger tiempo = new AtomicInteger ( );
        // Crear el bucle de la  animación
        timeline = new Timeline ( new KeyFrame ( Duration.millis ( 16 ), e -> {
            tiempo.getAndIncrement ( );
            // Calcular la posición en el siguiente paso de tiempo
            double xAcceleration = -RESISTENCIA_DEL_AIRE * xVelocidad * masa;
            double yAcceleration = gravedad - RESISTENCIA_DEL_AIRE * yVelocidad * masa;
            //Aplicamos formato a los valores numericos
            BigDecimal formatoVelocidadX = new BigDecimal ( xVelocidad );
            BigDecimal formatoVelocidadY = new BigDecimal ( yVelocidad );
            BigDecimal formatoAceleracionX = new BigDecimal ( xAcceleration );
            BigDecimal formatoAceleracionY = new BigDecimal ( yAcceleration );
            formatoVelocidadX = formatoVelocidadX.setScale ( 2, RoundingMode.DOWN );
            formatoVelocidadY = formatoVelocidadY.setScale ( 2,RoundingMode.DOWN );
            formatoAceleracionX = formatoAceleracionX.setScale ( 2,RoundingMode.DOWN );
            formatoAceleracionY = formatoVelocidadY.setScale ( 2,RoundingMode.DOWN );
            //Guardamos informacion en el LocalStorage
            LocalStorage.xVelocidadTiempoTiroParabolico.put ( tiempo.get ( ), formatoVelocidadX.doubleValue () );
            LocalStorage.yVelocidadTiempoTiroParabolico.put ( tiempo.get ( ), formatoVelocidadY.doubleValue () );
            LocalStorage.xAceleracionTiempoTiroParabolico.put ( tiempo.get ( ), formatoAceleracionX.doubleValue () );
            LocalStorage.yAceleracionTiempoTiroParabolico.put ( tiempo.get ( ), formatoAceleracionY.doubleValue () );
            xVelocidad += xAcceleration * tiempo.get ( ) / 100;
            yVelocidad += yAcceleration * tiempo.get ( ) / 100;
            xPosicion += xVelocidad * tiempo.get ( ) / 100;
            yPosicion += yVelocidad * tiempo.get ( ) / 100;
            // Actualizar la posición del proyectil
            projectile.setCenterX ( xPosicion );
            projectile.setCenterY ( yPosicion );
            //Accedemos al canvas y dibujamos
            canvaProperty.setWidth ( contenedorAnimacion.getWidth ( ) );
            GraphicsContext gc = canvaProperty.getGraphicsContext2D ( );
            gc.setFill ( Color.BLACK );
            gc.fillOval ( xPosicion + 124, (yPosicion + 270), 2, 2 );
            // Si el proyectil toca el suelo, detener la animación
            if (projectile.getCenterY ( ) > 80) {
                timeline.stop ( );

                Task<Void> task = new Task<> ( ) {
                    @Override
                    protected Void call () throws Exception {
                        renderizarSuelo ( );
                        formatoTabla();
                        return null;
                    }
                };
                new Thread ( task ).start ( );
            }

            //Colocamos la informacion del mov en los textFields
            textFieldVelocidadX.setText ( xVelocidad + " m/s²" );
            textFieldVelocidadY.setText ( yVelocidad + " m/s²" );
        } ) );

        timeline.setCycleCount ( Timeline.INDEFINITE );
        timeline.play ( );
    }

    public void renderizarSuelo () {
        int horizontales = (int) (contenedorAnimacion.getWidth ( ) / 349);
        for (int i = 0; i < horizontales; i++) {
            ImageView imageView = new ImageView (new Image ( "/images/sand_PNG30.png" ) );
            imageView.setPreserveRatio(false);
            imageView.setFitHeight ( 130 );
            imageView.setFitWidth ( 352 );
            Platform.runLater(() -> contenedorArena.getChildren().add(imageView));

        }
    }
    void formatoTabla () {
        AtomicReference<String> tablaCaidaLibre = new AtomicReference<> ( "" );
        tablaCaidaLibre.set ( "Velocidad X\t\t\t\tTiempo\n" );
        xVelocidadTiempoTiroParabolico.forEach ( ( k, v ) -> {
            tablaCaidaLibre.set ( tablaCaidaLibre.get ( ) + k + "\t\t\t\t\t\t" + v + "\n" );
        } );
        tablaCaidaLibre.set (tablaCaidaLibre.get ()  +  "Velocidad Y\t\t\tTiempo\n" );
        yVelocidadTiempoTiroParabolico.forEach ( ( k, v ) -> {
            tablaCaidaLibre.set ( tablaCaidaLibre.get ( ) + k + "\t\t\t\t\t\t" + v + "\n" );
        } );
        tablaCaidaLibre.set (tablaCaidaLibre.get ()  +  "Aceleracion X\t\t\t\tTiempo\n" );
        xAceleracionTiempoTiroParabolico.forEach ( (k,v)->{
            tablaCaidaLibre.set ( tablaCaidaLibre.get ( ) + k + "\t\t\t\t\t\t" + v + "\n" );
        } );
        tablaCaidaLibre.set (tablaCaidaLibre.get ()  +  "Aceleracion Y\t\t\t\tTiempo\n" );
        yAceleracionTiempoTiroParabolico.forEach ( (k,v)->{
            tablaCaidaLibre.set ( tablaCaidaLibre.get ( ) + k + "\t\t\t\t\t\t" + v + "\n" );
        } );
        General.tablaValores = tablaCaidaLibre.get ();
    }
}
