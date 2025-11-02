public class Creature {
    private String name;
    private String size;

    // Constructor
    public Creature(String name, String size) {
        this.name = name;
        this.size = size;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name (optional but useful)
    public void setName(String name) {
        this.name = name;
    }

    // Getter for size
    public String getSize() {
        return size;
    }

    // Setter for size (optional but useful)
    public void setSize(String size) {
        this.size = size;
    }

    // toString for debugging / printing
    public String toString() {
        return "Creature{name='" + name + "', size='" + size + "'}";
    }
}
