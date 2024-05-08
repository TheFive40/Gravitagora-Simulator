package org.simulador.es.model;

import javafx.beans.property.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TiroParabolicoModel {
    private IntegerProperty tiempo = new SimpleIntegerProperty();
    private DoubleProperty velocidad = new SimpleDoubleProperty();

    public TiroParabolicoModel(int tiempo, double velocidad) {
        setTiempo(tiempo);
        setVelocidad(velocidad);
    }

    public int getTiempo() {
        return tiempo.get();
    }

    public IntegerProperty tiempoProperty() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo.set(tiempo);
    }

    public double getVelocidad() {
        return velocidad.get();
    }

    public DoubleProperty velocidadProperty() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad.set(velocidad);
    }
}
