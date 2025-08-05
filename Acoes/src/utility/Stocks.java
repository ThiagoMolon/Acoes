package utility;

public class Stocks {
    private int id;
    private String name;
    private double yield;
    private double value;

    public Stocks() {}

    public Stocks(int id, String name, double yield, double value) {
        this.id = id;
        this.name = name;
        this.yield = yield;
        this.value = value;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getYield() { return yield; }
    public void setYield(double yield) { this.yield = yield; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    @Override
    public String toString() {
        return name;
    }
}