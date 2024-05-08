package org.simulador.es.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.simulador.es.model.MovCircularModel;

public class MovCircularDAO {
    private ObservableList<MovCircularModel> list = FXCollections.observableArrayList();
    public void add(MovCircularModel item){
        list.add(item);
    }
    public void remove(MovCircularModel item){
        list.remove(item);
    }
    public void update(MovCircularModel item){
        int count = 0;
        for (MovCircularModel mruModel : list) {
            if(mruModel == item){
                list.set(count,item);
            }
            count++;
        }
    }

    public ObservableList<MovCircularModel> getList() {
        return list;
    }

    public void setList(ObservableList<MovCircularModel> list) {
        this.list = list;
    }
}
