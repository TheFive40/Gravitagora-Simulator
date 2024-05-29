package org.simulador.es.model;

import javafx.beans.property.DoubleProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MovCircularModel extends SuperModel{

    public MovCircularModel(int tiempo, double valor) {
        super (tiempo,valor );
        setTiempo(tiempo);
        setValue (valor);
    }


}
