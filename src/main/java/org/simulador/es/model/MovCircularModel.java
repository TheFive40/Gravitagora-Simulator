package org.simulador.es.model;

import javafx.beans.property.DoubleProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MovCircularModel {
    private IntegerProperty tiempo = new SimpleIntegerProperty();
    private DoubleProperty valor = new SimpleDoubleProperty();

    public MovCircularModel(int tiempo, double valor) {
        setTiempo(tiempo);
        setValor(valor);
    }

    public double getTiempo() {
        return tiempo.get();
    }


    public double getValor() {
        return valor.get();
    }

    public IntegerProperty tiempoProperty() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo.set(tiempo);
    }

    public DoubleProperty valorProperty() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor.set(valor);
    }
}
