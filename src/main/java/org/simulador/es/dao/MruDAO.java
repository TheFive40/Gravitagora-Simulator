package org.simulador.es.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.simulador.es.model.MruModel;
public class MruDAO extends SuperDAO<MruModel> {
    private ObservableList<MruModel> list = FXCollections.observableArrayList();
    public MruDAO(){
        setList ( list );
    }
}
