package org.simulador.es.controller;

import javafx.beans.property.MapProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.simulador.es.dao.TiroParabolicoDAO;
import org.simulador.es.data.LocalStorage;
import org.simulador.es.model.TiroParabolicoModel;
import util.General;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import static org.simulador.es.data.LocalStorage.*;

public class GraficoTiroParabolicoController implements Initializable {
    @FXML
    private TableView<TiroParabolicoModel> tablaMovimiento;

    @FXML
    private LineChart<Number, Number> lineChartVelocidadTiempo;

    @FXML
    private ChoiceBox<String> tipoInformacionChoiceBox;

    @FXML
    private TableColumn<TiroParabolicoModel, Number> tableColumnTiempo;

    @FXML
    private TableColumn<TiroParabolicoModel, Number> tableColumnVelocidad;

    private TiroParabolicoDAO tiroParabolicoDAO;

    void eventoGraficoSeleccionado() {
        tipoInformacionChoiceBox.setOnAction(e -> {
            String tipo = tipoInformacionChoiceBox.getSelectionModel().getSelectedItem();
            switch (tipo) {
                case "Velocidad en X vs Tiempo" -> {
                    tableColumnVelocidad.setText("Velocidad X (m/s)");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Velocidad (m/s)");
                    llenarGraficoTiroParabolico(xVelocidadTiempoTiroParabolico);
                }
                case "Velocidad en Y vs Tiempo"->{
                    tableColumnVelocidad.setText("Velocidad Y (m/s)");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Velocidad (m/s)");
                    llenarGraficoTiroParabolico(yVelocidadTiempoTiroParabolico);

                }
                case "Aceleración X vs Tiempo" -> {
                    tableColumnVelocidad.setText("Aceleracion X (m/s²)");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Aceleracion (m/s²)");
                    llenarGraficoTiroParabolico(xAceleracionTiempoTiroParabolico);
                }
                case "Aceleración Y vs Tiempo" -> {
                    tableColumnVelocidad.setText("Aceleración Y (m/s²)");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Aceleración (m/s²)" );
                    llenarGraficoTiroParabolico(yAceleracionTiempoTiroParabolico);
                }
                default -> throw new IllegalArgumentException();
            }
        });
    }

    public void llenarGraficoTiroParabolico(MapProperty<? extends Number, ? extends Number> mapProperty) {
        tiroParabolicoDAO.getList().clear();
        lineChartVelocidadTiempo.getData().clear();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        mapProperty.forEach((k, v) -> {
            dataSeries.getData().add(new XYChart.Data<>(k, v));
            tiroParabolicoDAO.add(new TiroParabolicoModel((int) k, (double) v));
        });

        lineChartVelocidadTiempo.getData().addAll(dataSeries);
    }

  /*  public void llenarGraficoVelocidad(MapProperty<Integer, Double> yVelocidad) {
        tiroParabolicoDAO.getList().clear();
        lineChartVelocidadTiempo.getData().clear();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        xVelocidad.forEach((k,v)->{
            dataSeries.getData().add(new XYChart.Data<>(k,v));
            tiroParabolicoDAO.add(new TiroParabolicoModel(k, v));
        });
        yVelocidad.forEach((k, v) -> {
            dataSeries.getData().add(new XYChart.Data<>(k, v));
            tiroParabolicoDAO.add(new TiroParabolicoModel(k, v));
        });
        lineChartVelocidadTiempo.getData().addAll(dataSeries);
    }*/

    public void llenarTabla() {
        tableColumnTiempo.setCellValueFactory(e -> e.getValue().tiempoProperty());
        tableColumnVelocidad.setCellValueFactory(e -> e.getValue().velocidadProperty());
        tablaMovimiento.setItems(tiroParabolicoDAO.getList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tiroParabolicoDAO = new TiroParabolicoDAO();
        tipoInformacionChoiceBox.getSelectionModel().select(0);
        eventoGraficoSeleccionado();
        llenarGraficoTiroParabolico(xVelocidadTiempoTiroParabolico);
        llenarTabla();
    }
}
