package org.simulador.es.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CaidaLibre {
    private HashMap<Integer, Double> velocidadTiempo = new HashMap<>();
    private Circle particula;
    private Timeline  timeline;
    private AnchorPane contenedorPrincipal;
    public CaidaLibre(Shape particula) {
        this.particula = (Circle) particula;
    }

    public CaidaLibre(Shape particula, AnchorPane pane) {
        this.particula = (Circle) particula;
        contenedorPrincipal = pane;
    }

    public void establecerAnimacion() {
        particula.setLayoutY(15);
        AtomicInteger tiempo = new AtomicInteger();
        timeline  = new Timeline(new KeyFrame(Duration.millis(250), e -> {
            tiempo.getAndIncrement();
            double velocidad = calcularVelocidad(tiempo.get());
            velocidadTiempo.put(tiempo.get(),velocidad);
            double nuevoDesplazamiento = particula.getTranslateY() + velocidad;
            if (nuevoDesplazamiento >= contenedorPrincipal.getHeight()) {
                velocidadTiempo.forEach((k,v)->{
                    System.out.println("Tiempo " + k + " Velocidad: " + v);
                });
                timeline.stop();
                return;
            }
            this.particula.setTranslateY(nuevoDesplazamiento);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private double calcularVelocidad(int tiempo) {
        double velocidadInicial = 0;
        double gravedad = 9.81;
        double velocidad = velocidadInicial + (gravedad * tiempo);
        return velocidad;
    }
}
