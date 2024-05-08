package org.simulador.es.data;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

public class LocalStorage {

    public static MapProperty<Integer, Double> velocidadTiempoMru = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer,Double> desplazamientoTiempoMru = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer, Double> velocidadTiempoCaidaLibre;
    public static MapProperty<Integer, Double> aceleracionCaidaLibre = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer, Double> posicionTiempoCaidaLibre = new SimpleMapProperty<>(FXCollections.observableHashMap());

    public static MapProperty<Integer, Double> velocidadTiempoMovCircular = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer, Double> aceleracionTiempoMovCircular = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer, Double> posicionTiempoMovCircular = new SimpleMapProperty<>(FXCollections.observableHashMap());

}
