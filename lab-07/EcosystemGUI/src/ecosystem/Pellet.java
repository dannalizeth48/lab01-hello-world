package ecosystem;
public class Pellet {
    double x, y;
    double vy = 0;
    double radius = 4;
    boolean settled = false;
    boolean eaten = false;
    double age = 0;

    public Pellet(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
