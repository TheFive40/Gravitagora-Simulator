package util.animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.simulador.es.data.LocalStorage;
import java.util.concurrent.atomic.AtomicInteger;

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

    public TiroParabolicoAnimation(Circle proyectil, AnchorPane contenedorAnimacion) {
        setProjectile(proyectil);
        setContenedorAnimacion(contenedorAnimacion);
    }

    public void establecerAnimacion() {
        // Inicializar posiciones y velocidades
        double angleRadians = Math.toRadians(angulo);
        xPosicion = 0;
        yPosicion = 0; // Altura inicial
        xVelocidad = velocidadInicial * Math.cos(angleRadians);
        yVelocidad = -velocidadInicial * Math.sin(angleRadians);
        AtomicInteger tiempo = new AtomicInteger();
        // Crear el bucle de animación
        timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            tiempo.getAndIncrement();

            // Calcular la posición en el siguiente paso de tiempo
            double xAcceleration = -RESISTENCIA_DEL_AIRE * xVelocidad * masa;
            double yAcceleration = gravedad - RESISTENCIA_DEL_AIRE * yVelocidad * masa;
            //Guardamos informacion en el LocalStorage
            LocalStorage.xVelocidadTiempoTiroParabolico.put(tiempo.get(), xVelocidad);
            LocalStorage.yVelocidadTiempoTiroParabolico.put(tiempo.get(), yVelocidad);
            LocalStorage.xAceleracionTiempoTiroParabolico.put(tiempo.get(), xAcceleration);
            LocalStorage.yAceleracionTiempoTiroParabolico.put(tiempo.get(), yAcceleration);
            xVelocidad += xAcceleration * tiempo.get() / 100;
            yVelocidad += yAcceleration * tiempo.get() / 100;
            xPosicion += xVelocidad * tiempo.get() / 100;
            yPosicion += yVelocidad * tiempo.get() / 100;
            // Actualizar la posición del proyectil
            projectile.setCenterX(xPosicion);
            projectile.setCenterY(yPosicion);
            // Si el proyectil toca el suelo, detener la animación
            if (projectile.getCenterY() > 5) {
                timeline.stop();
            }
            //Colocamos la informacion del mov en los textFields
            textFieldVelocidadX.setText(xVelocidad + " m/s²");
            textFieldVelocidadY.setText(yVelocidad + " m/s²");
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
