package org.simulador.es.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import org.simulador.es.model.TiroParabolicoModel;
@Data
public class TiroParabolicoDAO {
    private ObservableList<TiroParabolicoModel> list = FXCollections.observableArrayList();
    public void add(TiroParabolicoModel item){
        list.add(item);
    }
    public void remove(TiroParabolicoModel item){
        list.remove(item);
    }
    public void update(TiroParabolicoModel item){
        int count = 0;
        for (TiroParabolicoModel mruModel : list) {
            if(mruModel == item){
                list.set(count,item);
            }
            count++;
        }
    }
}
