public class Creature {
    String name;
    String size;

    // Constructor
    public Creature(String name, String size) {
        this.name = name;
        this.size = size;
    }

    // Methods
    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void talk() {
        System.out.println(name + " says hello!");
    }

    public void move() {
        System.out.println(name + " is moving around.");
    }

    @Override
    public String toString() {
        return "Creature{name='" + name + "', size='" + size + "'}";
    }
}
