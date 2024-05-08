package org.simulador.es.controller;

import javafx.beans.property.MapProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.simulador.es.dao.MovCircularDAO;
import org.simulador.es.model.MovCircularModel;
import util.General;

import java.net.URL;
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

    private MovCircularDAO movCircularDAO;
    void eventoGraficoSeleccionado(){
        tipoInformacionChoiceBox.setOnAction(e->{
            String tipo = tipoInformacionChoiceBox.getSelectionModel().getSelectedItem();
            switch (tipo){
                case "Velocidad vs Tiempo"->{
                    tableColumnVelocidad.setText("Velocidad");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Velocidad");
                    llenarGraficoMovCircular(velocidadTiempoMovCircular);
                }
                case "Posición vs Tiempo"->{
                    tableColumnVelocidad.setText("Posición");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Posición");
                    llenarGraficoMovCircular(posicionTiempoMovCircular);
                }
                case "Aceleración vs Tiempo"->{
                    tableColumnVelocidad.setText("Aceleración");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Aceleración");
                    llenarGraficoMovCircular(aceleracionTiempoMovCircular);
                }
                default -> throw new IllegalArgumentException();
            }
        });
    }
    public void llenarGraficoMovCircular(MapProperty<? extends Number, ? extends Number> mapProperty) {
        aceleracionTiempoMovCircular.get().entrySet().removeIf(k -> k.getKey() > General.tiempoAnimacion);
        posicionTiempoMovCircular.get().entrySet().removeIf(k -> k.getKey() > General.tiempoAnimacion);
        velocidadTiempoMovCircular.get().entrySet().removeIf(k -> k.getKey() > General.tiempoAnimacion);
        movCircularDAO.getList().clear();
        lineChartVelocidadTiempo.getData().clear();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        mapProperty.forEach((k,v)->{
            System.out.println("Tiempo: " + k + " Valor: " + v);
            dataSeries.getData().add(new XYChart.Data<>(k,v));
            movCircularDAO.add(new MovCircularModel((int) k, (double) v));
        });
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
