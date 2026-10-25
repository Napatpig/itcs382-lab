package bikeshare;

import java.util.ArrayDeque;
import java.util.Deque;

public class Station {
    private final String name;
    private final Deque<Bike> bikes = new ArrayDeque<>();

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addBike(Bike bike) {
        bikes.addLast(bike);
    }

    public Bike takeBike() {
        Bike bike = bikes.pollFirst();
        if (bike == null) {
            throw new IllegalStateException("No bike available at " + name);
        }
        return bike;
    }
}
