package util.animations;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import util.General;

import java.util.concurrent.atomic.AtomicInteger;

import static org.simulador.es.data.LocalStorage.*;

@Getter
@Setter
public class MruAnimation {


    private Circle ruedaTrasera;

    private Circle ruedaDelantera;

    private Rectangle vehiculo;

    private Timeline timeLine;

    private Integer tiempo;

    private Double velocidad;

    private TextField textFieldVelocidadObjeto;

    private AnchorPane contenedorPrincipal;

    public MruAnimation(Circle ruedaTrasera, Circle ruedaDelantera, Rectangle vehiculo, AnchorPane contenedorPrincipal) {
        this.ruedaTrasera = ruedaTrasera;
        this.ruedaDelantera = ruedaDelantera;
        this.vehiculo = vehiculo;
        this.contenedorPrincipal = contenedorPrincipal;
        velocidadTiempoMru = new SimpleMapProperty<>(FXCollections.observableHashMap());
    }

    public void establecerAnimacion() {
        AtomicInteger tiempo = new AtomicInteger();
        timeLine = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            ruedaTrasera.setTranslateX(ruedaTrasera.getTranslateX() + getVelocidad());
            vehiculo.setTranslateX(vehiculo.getTranslateX() + getVelocidad());
            ruedaDelantera.setTranslateX(ruedaDelantera.getTranslateX() + getVelocidad());
            //Calculamos el desplazamiento de la particula en un intervalo de seg
            double desplazamiento = getVelocidad() * tiempo.getAndIncrement();
            textFieldVelocidadObjeto.setText(desplazamiento + " m/s");
            if (vehiculo.getTranslateX() >= (contenedorPrincipal.getHeight() - 50)) {
                ruedaTrasera.setTranslateX(0.0);
                vehiculo.setTranslateX(0.0);
                ruedaDelantera.setTranslateX(4.0);
            }
            //Guardamos en el LocalStorage
            desplazamientoTiempoMru.put(tiempo.get(),desplazamiento);
            velocidadTiempoMru.put(tiempo.get(),velocidad);
            if ((velocidad >= 0 && vehiculo.getTranslateX() >= (contenedorPrincipal.getWidth() - 50)) ||
                    (velocidad < 0 && vehiculo.getTranslateX() <= 0)) {
                ruedaDelantera.setTranslateX(350);
                ruedaTrasera.setTranslateX(346);
                vehiculo.setTranslateX(346);
            }
        }));
        General.tiempoAnimacion = getTiempo();
        timeLine.setCycleCount(getTiempo() * 1000 / 16);
        timeLine.play();

    }
}
