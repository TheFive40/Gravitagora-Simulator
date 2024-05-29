package org.simulador.es.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SuperDAO<T> {
    private ObservableList<T> list = FXCollections.observableArrayList ( );

    public void add ( T item ) {
        list.add ( item );
    }

    public void remove ( T item ) {
        list.remove ( item );
    }

    public void update ( T item ) {
        int count = 0;
        for (T mruModel : list) {
            if (mruModel == item) {
                list.set ( count, item );
            }
            count++;
        }
    }

    public ObservableList<T> getList () {
        return list;
    }

    public void setList ( ObservableList<T> list ) {
        this.list = list;
    }

    public T getItem ( int index ) {
        return list.get ( index );
    }
}
