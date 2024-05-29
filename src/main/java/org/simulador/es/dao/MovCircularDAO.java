package org.simulador.es.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.simulador.es.model.MovCircularModel;

public class MovCircularDAO  extends  SuperDAO<MovCircularModel>{
    private ObservableList<MovCircularModel> list = FXCollections.observableArrayList();
    public MovCircularDAO(){
        setList ( list );
    }
}
