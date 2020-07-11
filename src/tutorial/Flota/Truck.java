package tutorial.Flota;

public class Truck extends Car {
    private final double loadCapacity;

    public Truck(double value, String brand, long mileage, String color, String segment, double loadCapacity, int id) {
        super(value, brand, mileage, color, segment, id);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity; //no setter bcs it's final
    }
}
