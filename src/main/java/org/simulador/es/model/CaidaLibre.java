package org.simulador.es.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class CaidaLibre {
    private MapProperty<Integer, Double> velocidadTiempo;

    private Circle particula;

    private Timeline timeline;

    private AnchorPane contenedorPrincipal;

    private Double gravedad;

    private Double velocidadInicial;

    private Integer tiempo;

    private TextField textFieldVelocidadObjeto;

    private TextField textFieldTiempoObjeto;

    public CaidaLibre(Shape particula, AnchorPane pane) {
        this.particula = (Circle) particula;
        velocidadTiempo = new SimpleMapProperty<>();
        velocidadTiempo.set(FXCollections.observableHashMap());
        tiempo = 0;
        gravedad = 0.0;
        velocidadInicial = 0.0;
        contenedorPrincipal = pane;
    }

    public void establecerAnimacion() {
        particula.setLayoutY(particula.getRadius());
        particula.setLayoutX(contenedorPrincipal.getWidth() / 2);
        AtomicInteger tiempo = new AtomicInteger();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            //Incrementamos el tiempo cada vez que se invoque la linea de tiempo
            tiempo.getAndIncrement();
            double velocidad = calcularVelocidad(tiempo.get());
            velocidadTiempo.put(tiempo.get(), velocidad);
            //Actualizamos en los TextFields la Velocidad y el tiempo, en real
            textFieldVelocidadObjeto.setText(velocidad + " m/s²");
            textFieldTiempoObjeto.setText(tiempo.get() + "s");
            //Calculamos el nuevo desplazamiento de la particula
            double nuevoDesplazamiento = particula.getTranslateY() + velocidad;
            //Evaluamos si la particula ya llego al borde del contenedor principal
            if (nuevoDesplazamiento >= (contenedorPrincipal.getHeight() +
                    (particula.getRadius() / 2))) {
                velocidadTiempo.forEach((k, v) -> System.out.println("Tiempo " + k + " Velocidad: " + v));
                timeline.stop();
                return;
            }
            //Establecemos el desplazamiento de la particula en el eje Y
            this.particula.setTranslateY(nuevoDesplazamiento);
        }));
        timeline.setCycleCount((getTiempo()));
        timeline.play();
    }


    private double calcularVelocidad(int tiempo) {
        return getVelocidadInicial() + (getGravedad() * tiempo);
    }


}
