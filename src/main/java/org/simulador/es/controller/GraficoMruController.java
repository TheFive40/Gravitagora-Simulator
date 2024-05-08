package org.simulador.es.controller;

import javafx.beans.property.MapProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.simulador.es.dao.MruDAO;
import org.simulador.es.data.LocalStorage;
import org.simulador.es.model.MruModel;
import util.General;
import util.animations.Mru;

import java.net.URL;
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

    private MruDAO mruDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mruDAO = new MruDAO();
        llenarTabla();
        llenarGraficoLineal(LocalStorage.velocidadTiempoMru);
        eventoGraficoSeleccionado();
    }
    void llenarTabla(){
        desplazamientoTiempoMru.get().entrySet().removeIf(x->x.getKey()> General.tiempoAnimacion);
        velocidadTiempoMru.get().entrySet().removeIf(x->x.getKey()>General.tiempoAnimacion);
        tableColumnTiempo.setCellValueFactory(e-> e.getValue().tiempoProperty());
        tableColumnVelocidad.setCellValueFactory(e-> e.getValue().velocidadProperty());
        tablaMovimiento.setItems(mruDAO.getList());
    }
    void eventoGraficoSeleccionado(){
        tipoInformacionChoiceBox.setOnAction(e->{
            String tipo = tipoInformacionChoiceBox.getSelectionModel().getSelectedItem();
            switch (tipo){
                case "Velocidad vs Tiempo"->{
                    tableColumnVelocidad.setText("Velocidad");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Velocidad");
                    llenarGraficoLineal(LocalStorage.velocidadTiempoMru);
                }
                case "Desplazamiento vs Tiempo"->{
                    tableColumnVelocidad.setText("Desplazamiento");
                    lineChartVelocidadTiempo.getYAxis().setLabel("Desplazamiento");

                    llenarGraficoLineal(LocalStorage.desplazamientoTiempoMru);
                }
                default -> throw new IllegalArgumentException();
            }
        });
    }
    public void llenarGraficoLineal(MapProperty<?extends Number,?extends Number> mapProperty) {
        lineChartVelocidadTiempo.getData().clear();
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();

        mapProperty.forEach((k, v) -> {
            dataSeries.getData().add(new XYChart.Data<>(k, v));
            mruDAO.add(new MruModel((int) k, (double) v));
        });
        lineChartVelocidadTiempo.getData().addAll(dataSeries);
    }
}
