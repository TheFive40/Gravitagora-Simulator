package org.simulador.es.controller.graficos;

import javafx.beans.property.MapProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import org.simulador.es.dao.MruDAO;
import org.simulador.es.data.LocalStorage;
import org.simulador.es.data.RegresionLineal;
import org.simulador.es.model.MruModel;
import util.General;
import util.TableViewToCsv;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.simulador.es.data.LocalStorage.desplazamientoTiempoMru;
import static org.simulador.es.data.LocalStorage.velocidadTiempoMru;

public class GraficoMruController implements Initializable {
    @FXML
    private LineChart<Number, Number> lineChartVelocidadTiempo;
    @FXML
    private ChoiceBox<String> tipoInformacionChoiceBox;
    @FXML
    private TableColumn<MruModel, Number> tableColumnTiempo;

    @FXML
    private TableColumn<MruModel, Number> tableColumnVelocidad;

    @FXML
    private TableView<MruModel> tablaMovimiento;
    @FXML
    private ScrollPane scrollPane;
    private MruDAO mruDAO;


    void llenarTabla(){
       /* desplazamientoTiempoMru.get().entrySet().removeIf(x->x.getKey()> General.tiempoAnimacion);
        velocidadTiempoMru.get().entrySet().removeIf(x->x.getKey()>General.tiempoAnimacion);*/
        tableColumnTiempo.setCellValueFactory(e-> e.getValue().tiempoProperty());
        tableColumnVelocidad.setCellValueFactory(e-> e.getValue().valueProperty ());
        tablaMovimiento.setItems(mruDAO.getList());
    }
    void eventoGraficoSeleccionado(){
        tipoInformacionChoiceBox.setOnAction(e->{
            String tipo = tipoInformacionChoiceBox.getSelectionModel().getSelectedItem();
            switch (tipo){
                case "Velocidad vs Tiempo"->{
                    tableColumnVelocidad.setText("Velocidad (m/s)");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Velocidad (m/s)");
                    llenarGraficoLineal(LocalStorage.velocidadTiempoMru);
                }
                case "Desplazamiento vs Tiempo"->{
                    tableColumnVelocidad.setText("Desplazamiento (m)");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Desplazamiento (m)");
                    llenarGraficoLineal(LocalStorage.desplazamientoTiempoMru);
                }
                default -> throw new IllegalArgumentException();
            }
        });
    }
    @FXML
    void eventoGuardarDatos( ActionEvent event ){
        TableViewToCsv<MruDAO> tableViewToCsv = new TableViewToCsv<> ( mruDAO );
        tableViewToCsv.guardarExcel ();
    }
    public void llenarGraficoLineal(MapProperty<?extends Number,?extends Number> mapProperty) {
        RegresionLineal regresionLineal = new RegresionLineal ();
        lineChartVelocidadTiempo.getData().clear();
        mruDAO.getList().clear();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        List<Double> x = new ArrayList<> (  );
        List<Integer> y = new ArrayList<> (  );
        mapProperty.forEach((k, v) -> {
            x.add ( (Double) v );
            y.add ( (Integer) k );
            dataSeries.getData().add(new XYChart.Data<>(k, v));
            mruDAO.add(new MruModel((int) k, (double) v));
        });
        regresionLineal.setRegresionLineal ( x,y );
        Tooltip tooltip = new Tooltip ( regresionLineal.obtenerEcuacion ( ) );
        scrollPane.setTooltip ( tooltip );
        lineChartVelocidadTiempo.getData().addAll(dataSeries);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mruDAO = new MruDAO();
        llenarTabla();
        llenarGraficoLineal(LocalStorage.velocidadTiempoMru);
        eventoGraficoSeleccionado();
    }
}
