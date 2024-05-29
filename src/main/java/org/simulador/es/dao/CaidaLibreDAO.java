package org.simulador.es.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.simulador.es.model.CaidaLibreModel;
import org.simulador.es.model.MruModel;

public class CaidaLibreDAO  extends  SuperDAO<CaidaLibreModel>{
    private ObservableList<CaidaLibreModel> list = FXCollections.observableArrayList();
   public CaidaLibreDAO(){
       setList ( list );
   }
}
