package org.simulador.es;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TiroParabolico extends Application {
    // Constantes para la simulación
    private static final double GRAVEDAD = 9.81; // Aceleración de la gravedad (m/s^2)
    private static final double PASO_DE_TIEMPO = 0.02; // Paso de tiempo (s)
    private static final double RESISTENCIA_DEL_AIRE = 0.02; // Resistencia del aire
    private static final double MASA = 0.1; // Masa del objeto (kg)
    private static final double VELOCIDAD_INICIAL = 50.0; // Velocidad inicial (m/s)
    private static final double ANGULO = 45.0; // Ángulo de lanzamiento (grados)

    private double xPosicion;
    private double yPosicion;
    private double xVelocidad;
    private double yVelocidad;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        Circle projectile = new Circle(10, Color.RED); // Objeto que representa el proyectil
        root.getChildren().add(projectile);

        // Inicializar posiciones y velocidades
        double angleRadians = Math.toRadians(ANGULO);
        xPosicion = 0;
        yPosicion = scene.getHeight() - 20; // Altura inicial
        xVelocidad = VELOCIDAD_INICIAL * Math.cos(angleRadians);
        yVelocidad = -VELOCIDAD_INICIAL * Math.sin(angleRadians);

        // Crear el bucle de animación
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Calcular la posición en el siguiente paso de tiempo
                double xAcceleration = -RESISTENCIA_DEL_AIRE * xVelocidad * MASA;
                double yAcceleration = GRAVEDAD - RESISTENCIA_DEL_AIRE * yVelocidad * MASA;

                xVelocidad += xAcceleration * PASO_DE_TIEMPO;
                yVelocidad += yAcceleration * PASO_DE_TIEMPO;

                xPosicion += xVelocidad * PASO_DE_TIEMPO;
                yPosicion += yVelocidad * PASO_DE_TIEMPO;

                // Actualizar la posición del proyectil
                projectile.setCenterX(xPosicion);
                projectile.setCenterY(yPosicion);

                // Si el proyectil toca el suelo, detener la animación
                if (yPosicion >= scene.getHeight() - projectile.getRadius()) {
                    stop();
                }
            }
        }.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Parabolic Motion Animation");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

