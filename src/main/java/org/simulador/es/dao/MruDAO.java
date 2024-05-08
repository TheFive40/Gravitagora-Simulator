package org.simulador.es.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.simulador.es.model.MruModel;

@Getter
@Setter
public class MruDAO {
    private ObservableList<MruModel> list = FXCollections.observableArrayList();
    public void add(MruModel item){
        list.add(item);
    }
    public void remove(MruModel item){
        list.remove(item);
    }
    public void update(MruModel item){
        int count = 0;
        for (MruModel mruModel : list) {
            if(mruModel == item){
                list.set(count,item);
            }
            count++;
        }
    }
}
