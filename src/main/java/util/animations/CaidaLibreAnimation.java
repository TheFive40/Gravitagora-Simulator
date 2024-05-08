package util.animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import util.General;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.simulador.es.data.LocalStorage.*;

@Getter
@Setter
public class CaidaLibreAnimation {

    private Circle particula;

    private Timeline timeline;

    private AnchorPane contenedorPrincipal;

    private Double gravedad;

    private Double velocidadInicial;

    private Integer tiempo;

    private TextField textFieldVelocidadObjeto;

    private TextField textFieldTiempoObjeto;

    public CaidaLibreAnimation(Shape particula, AnchorPane pane) {
        this.particula = (Circle) particula;
        velocidadTiempoCaidaLibre = new SimpleMapProperty<>();
        velocidadTiempoCaidaLibre.set(FXCollections.observableHashMap());
        tiempo = 0;
        contenedorPrincipal = pane;
    }

    public void establecerAnimacion() {
        particula.setLayoutY(particula.getRadius());
        particula.setLayoutX(contenedorPrincipal.getWidth() / 2);
        AtomicInteger tiempo = new AtomicInteger();
        AtomicLong tiempoAnterior = new AtomicLong(System.currentTimeMillis());
        timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            //Incrementamos el tiempo cada vez que se invoque la linea de tiempo
            //Y calculamos la velocidad con el valor del tiempo
            tiempo.getAndIncrement();
            double velocidad = calcularVelocidad(tiempo.get());
            //Actualizamos en los TextFields la Velocidad y el tiempo, en real
            textFieldVelocidadObjeto.setText(velocidad + " m/s²");
            textFieldTiempoObjeto.setText(tiempo.get() + "s");
            //Calculamos el nuevo desplazamiento de la particula
            double desplazamientoAnimacion = particula.getTranslateY() + velocidad;

            double desplazamiento = getVelocidadInicial() *tiempo.get() +
            (double) 1 /2 * getGravedad()*Math.pow(tiempo.get(),2);
            //Establecemos el desplazamiento de la particula en el eje Y
            this.particula.setTranslateY(desplazamientoAnimacion);
            //Evaluamos si la particula ya llego al borde del contenedor principal
            if (desplazamientoAnimacion >= (contenedorPrincipal.getHeight() +
                    (particula.getRadius() / 2))) {
                particula.setTranslateY(15.0);
            }
            //Guardamos la informacion en el LocalStorage
            velocidadTiempoCaidaLibre.put(tiempo.get(), velocidad*100);
            aceleracionCaidaLibre.put(tiempo.get(), getGravedad());
            posicionTiempoCaidaLibre.put(tiempo.get(),desplazamiento);
        }));
        General.tiempoAnimacion = getTiempo();
        timeline.setCycleCount((getTiempo() * 1000 / 50));
        timeline.play();
    }


    private double calcularVelocidad(int tiempo) {
        return getVelocidadInicial() + (getGravedad() * tiempo/100);
    }


}
