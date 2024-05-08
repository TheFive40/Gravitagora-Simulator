package org.simulador.es.model;

import javafx.beans.property.DoubleProperty;

import javafx.beans.property.SimpleDoubleProperty;

public class MovCircularModel {
    private DoubleProperty velocidadAngular = new SimpleDoubleProperty();
    private DoubleProperty aceleracionAngular = new SimpleDoubleProperty();
    private DoubleProperty posicionAngular = new SimpleDoubleProperty();

    public MovCircularModel(double velocidad, double aceleracion, double posicion){
        setVelocidadAngular(velocidad);
        setAceleracionAngular(aceleracion);
        setPosicionAngular(posicion);
    }
    public double getVelocidadAngular() {
        return velocidadAngular.get();
    }

    public DoubleProperty velocidadAngularProperty() {
        return velocidadAngular;
    }

    public void setVelocidadAngular(double velocidadAngular) {
        this.velocidadAngular.set(velocidadAngular);
    }

    public double getAceleracionAngular() {
        return aceleracionAngular.get();
    }

    public DoubleProperty aceleracionAngularProperty() {
        return aceleracionAngular;
    }

    public void setAceleracionAngular(double aceleracionAngular) {
        this.aceleracionAngular.set(aceleracionAngular);
    }

    public double getPosicionAngular() {
        return posicionAngular.get();
    }

    public DoubleProperty posicionAngularProperty() {
        return posicionAngular;
    }

    public void setPosicionAngular(double posicionAngular) {
        this.posicionAngular.set(posicionAngular);
    }
}
