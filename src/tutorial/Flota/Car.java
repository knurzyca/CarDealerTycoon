package tutorial.Flota;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private double value;
    private String brand; //final cant change
    private long mileage;
    private String color;
    private String segment;
    private final List<Parts> carParts; // can change items, but not remove them
    private int id;
    private List<String> historyOfRepair;
    private double maintenanceCosts;

    public Car(double value, String brand, long mileage, String color, String segment, int id) {
        this();
        this.value = value;
        this.brand = brand;
        this.mileage = mileage;
        this.color = color;
        this.segment = segment;
        this.id = id;
    }

    public Car() {
        carParts = new ArrayList<>(List.of(new Parts("Brakes"),
                new Parts("Dampers"),
                new Parts("Engine"),
                new Parts("Body"),
                new Parts("Gearbox")));
        historyOfRepair = new ArrayList<>();
    }

    public double getValue() {
        return value;
    }

    public void increaseValue(double value) {
        if (value > 0)
            this.value += value;
    }

    public void increaseValue(List<Parts> parts) {
        for (Parts part : parts) {
            switch (part.getName()) {
                case "Brakes":
                    increaseValue(value * 0.1);
                    break;
                case "Dampers":
                    increaseValue(value * 0.2);
                    break;
                case "Engine":
                    increaseValue(value);
                    break;
                case "Car body":
                case "Gearbox":
                    increaseValue(value * 0.5);
            }
        }
    }

    public void decreaseValue(double value) {
        if (value > 0)
            this.value -= value;
    }

    public String getBrand() {
        return brand;
    }

    public long getMileage() {
        return mileage;
    }

    public void increaseMileage(int kilometer) {
        if (kilometer <= 0)
            System.out.println("You can't go lower in kilometers!!");
        else this.mileage += kilometer;

    }

    public String getColor() {
        System.out.println("The color of your car is: " + color);
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public List<Parts> getCarParts() {
        return carParts;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getHistoryOfRepair() {
        return historyOfRepair;
    }

    public double getMaintenanceCosts() {
        return maintenanceCosts;
    }

    public void increaseMaintenanceCosts(double maintenanceCosts) {
        if (maintenanceCosts >= 0)
            this.maintenanceCosts = maintenanceCosts;
    }

    @Override
    public String toString() {
        return color + " " + segment + " " + brand +
                ", costing: " + value + ", with " + mileage + "km.";


    }

    public boolean isDamaged() {
        for(Parts p : carParts){
            if(p.isDamaged())
                return true;
        }
        return false;
    }
}
