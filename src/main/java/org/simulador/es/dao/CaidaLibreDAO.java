package org.simulador.es.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.simulador.es.model.CaidaLibreModel;
import org.simulador.es.model.MruModel;

public class CaidaLibreDAO {
    private ObservableList<CaidaLibreModel> list = FXCollections.observableArrayList();
    public void add(CaidaLibreModel item){
        list.add(item);
    }
    public void remove(CaidaLibreModel item){
        list.remove(item);
    }
    public void update(CaidaLibreModel item){
        int count = 0;
        for (CaidaLibreModel mruModel : list) {
            if(mruModel == item){
                list.set(count,item);
            }
            count++;
        }
    }

    public ObservableList<CaidaLibreModel> getList() {
        return list;
    }

    public void setList(ObservableList<CaidaLibreModel> list) {
        this.list = list;
    }
}
