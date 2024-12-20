package org.simulador.es.data;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LocalStorage {

    public static MapProperty<Integer, Double> velocidadTiempoMru = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer,Double> desplazamientoTiempoMru = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer, Double> velocidadTiempoCaidaLibre;
    public static MapProperty<Integer, Double> aceleracionCaidaLibre = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer, Double> posicionTiempoCaidaLibre = new SimpleMapProperty<>(FXCollections.observableHashMap());

    public static MapProperty<Integer, Double> velocidadTiempoMovCircular = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer, Double> aceleracionTiempoMovCircular = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer, Double> posicionTiempoMovCircular = new SimpleMapProperty<>(FXCollections.observableHashMap());

    public static MapProperty<Integer,Double> xVelocidadTiempoTiroParabolico = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer,Double> yVelocidadTiempoTiroParabolico = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer,Double> xAceleracionTiempoTiroParabolico = new SimpleMapProperty<>(FXCollections.observableHashMap());
    public static MapProperty<Integer,Double> yAceleracionTiempoTiroParabolico = new SimpleMapProperty<>(FXCollections.observableHashMap());

    public static ObservableList<Punto> puntoObservableList = FXCollections.observableArrayList ();

}
