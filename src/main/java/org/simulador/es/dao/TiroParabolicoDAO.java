package org.simulador.es.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import org.simulador.es.model.TiroParabolicoModel;
public class TiroParabolicoDAO extends SuperDAO<TiroParabolicoModel> {
    private ObservableList<TiroParabolicoModel> list = FXCollections.observableArrayList();
    public TiroParabolicoDAO(){
        setList ( list );
    }
}
