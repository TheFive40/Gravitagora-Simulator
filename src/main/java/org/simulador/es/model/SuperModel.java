package org.simulador.es.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuperModel {
    private IntegerProperty tiempo = new SimpleIntegerProperty ( );
    private DoubleProperty value = new SimpleDoubleProperty ( );
    private DoubleProperty desplazamiento = new SimpleDoubleProperty (  );

    public SuperModel ( int tiempo, double value ) {
        setTiempo ( tiempo );
        setValue ( value );
    }

    public SuperModel(int tiempo, double velocidad, double desplazamiento){
        setTiempo ( tiempo );
        setValue ( velocidad );
        setDesplazamiento ( desplazamiento );
    }
    public int getTiempo () {
        return tiempo.get ( );
    }

    public IntegerProperty tiempoProperty () {
        return tiempo;
    }

    public void setTiempo ( int tiempo ) {
        this.tiempo.set ( tiempo );
    }

    public double getValue () {
        return value.get ( );
    }

    public DoubleProperty valueProperty () {
        return value;
    }

    public void setValue ( double value ) {
        this.value.set ( value );
    }

    public double getDesplazamiento () {
        return desplazamiento.get ( );
    }

    public DoubleProperty desplazamientoProperty () {
        return desplazamiento;
    }

    public void setDesplazamiento ( double desplazamiento ) {
        this.desplazamiento.set ( desplazamiento );
    }
}
