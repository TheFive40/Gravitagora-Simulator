package org.simulador.es.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MruModel {
    private IntegerProperty tiempo = new SimpleIntegerProperty();
    private DoubleProperty velocidad = new SimpleDoubleProperty();

    private DoubleProperty desplazamiento = new SimpleDoubleProperty();


    public MruModel(int tiempo, double velocidad) {
        setTiempo(tiempo);
        setVelocidad(velocidad);
        setDesplazamiento((tiempo*velocidad));
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

    public double getDesplazamiento() {
        return desplazamiento.get();
    }

    public DoubleProperty desplazamientoProperty() {
        return desplazamiento;
    }

    public void setDesplazamiento(double desplazamiento) {
        this.desplazamiento.set(desplazamiento);
    }
}
