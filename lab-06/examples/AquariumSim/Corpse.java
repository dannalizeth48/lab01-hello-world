public class Corpse {
    double x, y;
    double vy = 0;
    double radius;
    boolean settled = false;
    double age = 0;
    double decay = 0;         // 0..1
    double decayRate = 0.02;  // per second baseline (plus seaweed bites)
    boolean consumed = false; // fully eaten by plants

    public Corpse(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
}
