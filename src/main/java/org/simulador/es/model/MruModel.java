package org.simulador.es.model;
public class MruModel extends  SuperModel{

    public MruModel(int tiempo, double velocidad) {
        super (tiempo,velocidad,(tiempo*velocidad) );
    }


}
