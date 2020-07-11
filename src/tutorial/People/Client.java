package tutorial.People;

import java.util.List;

public class Client {
    int id;
    String name;
    String[] flota;
    String[] brands;
    boolean willBuyBroken;
    String[] segment;
    double cash;

    public Client(int id, String name, String[] flota, String[] brands,
                  boolean willBuyBroken, String[] segment, double cash) {
        this.id = id;
        this.name = name;
        this.flota = flota;
        this.brands = brands;
        this.willBuyBroken = willBuyBroken;
        this.segment = segment;
        this.cash = cash;
    }

    public Client() {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getFlota() {
        return flota;
    }

    public void setFlota(String[] flota) {
        this.flota = flota;
    }

    public String[] getBrands() {
        return brands;
    }

    public List<String> getBrandsList() {
        return List.of(brands);
    }

    public void setBrands(String[] brands) {
        this.brands = brands;
    }

    public boolean isWillBuyBroken() {
        return willBuyBroken;
    }

    public void setWillBuyBroken(boolean willBuyBroken) {
        this.willBuyBroken = willBuyBroken;
    }

    public String[] getSegment() {
        return segment;
    }

    public List<String> getSegmentsList() {
        return List.of(segment);
    }

    public void setSegment(String[] segment) {
        this.segment = segment;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public void decreaseCash(double price) {
        if (price >= 0)
            cash -= price;
    }
}