package org.simulador.es.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Punto {
    private int x,y,z,tiempo;
    private Double velocidadX, velocidadY;
    private List<Integer> x1, y1;
    public Punto ( int x, int y ) {
        x1 = new ArrayList<> (  );
        y1 = new ArrayList<> (  );
        x1.add ( x );
        y1.add ( y );
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (!(i == 0 && j == 0)) {
                    x1.add(x + i);
                    y1.add(y + j);
                }
            }
        }
    }

    public Punto ( int x, int y, int z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
