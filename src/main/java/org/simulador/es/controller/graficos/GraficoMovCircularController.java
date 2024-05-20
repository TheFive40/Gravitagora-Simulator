package org.simulador.es.controller.graficos;

import javafx.beans.property.MapProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import org.simulador.es.dao.MovCircularDAO;
import org.simulador.es.data.RegresionLineal;
import org.simulador.es.model.MovCircularModel;
import util.General;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.simulador.es.data.LocalStorage.*;

public class GraficoMovCircularController implements Initializable {
    @FXML
    private TableView<MovCircularModel> tablaMovimiento;

    @FXML
    private LineChart<Number, Number> lineChartVelocidadTiempo;

    @FXML
    private ChoiceBox<String> tipoInformacionChoiceBox;

    @FXML
    private TableColumn<MovCircularModel, Number> tableColumnTiempo;

    @FXML
    private TableColumn<MovCircularModel, Number> tableColumnVelocidad;
    @FXML
    private ScrollPane scrollPane;

    private MovCircularDAO movCircularDAO;
    void eventoGraficoSeleccionado(){
        tipoInformacionChoiceBox.setOnAction(e->{
            String tipo = tipoInformacionChoiceBox.getSelectionModel().getSelectedItem();
            switch (tipo){
                case "Velocidad vs Tiempo"->{
                    tableColumnVelocidad.setText("Velocidad (m/s)");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Velocidad (m/s)");
                    llenarGraficoMovCircular(velocidadTiempoMovCircular);
                }
                case "Posición vs Tiempo"->{
                    tableColumnVelocidad.setText("Posición (m)");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Posición (m)");
                    llenarGraficoMovCircular(posicionTiempoMovCircular);
                }
                case "Aceleración vs Tiempo"->{
                    tableColumnVelocidad.setText("Aceleración (m/s²)");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Aceleración (m/s²)");
                    llenarGraficoMovCircular(aceleracionTiempoMovCircular);
                }
                default -> throw new IllegalArgumentException();
            }
        });
    }
    public void llenarGraficoMovCircular(MapProperty<? extends Number, ? extends Number> mapProperty) {
        RegresionLineal regresionLineal = new RegresionLineal ();
        aceleracionTiempoMovCircular.get().entrySet().removeIf(k -> k.getKey() > General.tiempoAnimacion);
        posicionTiempoMovCircular.get().entrySet().removeIf(k -> k.getKey() > General.tiempoAnimacion);
        velocidadTiempoMovCircular.get().entrySet().removeIf(k -> k.getKey() > General.tiempoAnimacion);
        movCircularDAO.getList().clear();
        lineChartVelocidadTiempo.getData().clear();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        List<Double> x = new ArrayList<> (  );
        List<Integer> y = new ArrayList<> (  );
        mapProperty.forEach((k,v)->{
            x.add ( (Double) v );
            y.add ( (Integer) k );
            System.out.println("Tiempo: " + k + " Valor: " + v);
            dataSeries.getData().add(new XYChart.Data<>(k,v));
            movCircularDAO.add(new MovCircularModel((int) k, (double) v));
        });
        regresionLineal.setRegresionLineal ( x,y );
        Tooltip tooltip = new Tooltip ( regresionLineal.obtenerEcuacion ( ) );
        scrollPane.setTooltip ( tooltip );
        lineChartVelocidadTiempo.getData().addAll(dataSeries);
    }
    public void llenarTabla(){
        tableColumnTiempo.setCellValueFactory(e-> e.getValue().tiempoProperty());
        tableColumnVelocidad.setCellValueFactory(e->e.getValue().valorProperty());
        tablaMovimiento.setItems(movCircularDAO.getList());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movCircularDAO = new MovCircularDAO();
        eventoGraficoSeleccionado();
        llenarGraficoMovCircular(velocidadTiempoMovCircular);
        llenarTabla();
    }

}
