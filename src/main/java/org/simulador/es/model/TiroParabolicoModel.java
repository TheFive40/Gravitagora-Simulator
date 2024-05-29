package org.simulador.es.model;

import javafx.beans.property.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TiroParabolicoModel  extends  SuperModel{


    public TiroParabolicoModel(int tiempo, double velocidad) {
        super (tiempo,velocidad );
        setTiempo(tiempo);
        setValue (velocidad);
    }

}
