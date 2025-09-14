// CreatureDemo.java
class Creature {
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
}

public class CreatureDemo {
    public static void main(String[] args) {
        // Create a Creature object
        Creature myCreature = new Creature("Fluffy", "Medium");

        // Show its actions
        System.out.println("Creature name: " + myCreature.name);
        System.out.println("Creature size: " + myCreature.size);

        myCreature.eat();
        myCreature.talk();
        myCreature.move();
    }
}
