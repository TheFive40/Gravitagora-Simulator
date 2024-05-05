package org.simulador.es.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mru {

    private Circle ruedaTrasera;

    private Circle ruedaDelantera;

    private Rectangle vehiculo;

    private Timeline timeLine;

    private Integer tiempo;

    private Double velocidad;

    private TextField textFieldVelocidadObjeto;

    private AnchorPane contenedorPrincipal;
    public Mru(Circle ruedaTrasera, Circle ruedaDelantera, Rectangle vehiculo){
        this.ruedaTrasera = ruedaTrasera;
        this.ruedaDelantera = ruedaDelantera;
        this.vehiculo = vehiculo;
    }
    public void establecerAnimacion(){
        timeLine = new Timeline(new KeyFrame(Duration.seconds(1), e->{
            ruedaTrasera.setTranslateX(ruedaTrasera.getTranslateX() + getVelocidad());
            vehiculo.setTranslateX(vehiculo.getTranslateX() + getVelocidad());
            ruedaDelantera.setTranslateX(ruedaDelantera.getTranslateX() + getVelocidad());
            textFieldVelocidadObjeto.setText(getVelocidad() + " m/s");
        }));
        timeLine.setCycleCount(getTiempo());
        timeLine.play();
    }
}
