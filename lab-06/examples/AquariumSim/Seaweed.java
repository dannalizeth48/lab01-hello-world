import java.util.Random;

/**
 * Representa una planta de algas que limpia popó y ayuda a descomponer cadáveres.
 */
public class Seaweed {
    double x, y;          // posición en el fondo
    double h;             // altura de la planta
    double phase;         // fase para la animación de movimiento
    double cleanRadius = 42;  // radio de limpieza alrededor de la planta
    double eatRate   = 1.0;   // velocidad con la que ayuda a "comer" cadáveres

    Seaweed(double x, double y, double h) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.phase = new Random().nextDouble() * Math.PI * 2;
    }
}
