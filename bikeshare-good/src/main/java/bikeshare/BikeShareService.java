package bikeshare;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BikeShareService {
    private final Map<String, Station> stations = new LinkedHashMap<>();
    private final Map<String, Rental> rentals = new HashMap<>();
    private final RideLog log;

    public BikeShareService(RideLog log) {
        this.log = log;
        int number = 1;
        for (String name : new String[] {"Library", "Canteen", "Dorm"}) {
            Station station = new Station(name);
            station.addBike(new Bike("B" + number++));
            station.addBike(new Bike("B" + number++));
            stations.put(name.toLowerCase(), station);
        }
    }

    public String rent(String memberId, MemberType type, String stationName) {
        if (rentals.containsKey(memberId)) {
            throw new IllegalStateException("Member already has a bike");
        }
        Bike bike = findStation(stationName).takeBike();
        rentals.put(memberId, new Rental(memberId, type, bike));
        return bike.getId();
    }

    public Receipt giveBack(String memberId, String stationName, int minutes) throws IOException {
        Rental rental = rentals.get(memberId);
        if (rental == null) {
            throw new IllegalStateException("No active rental for " + memberId);
        }
        Station station = findStation(stationName);
        rentals.remove(memberId);
        station.addBike(rental.bike());
        Receipt receipt = new Receipt(memberId, rental.bike().getId(), minutes,
                rental.type().feeFor(minutes));
        log.append(receipt);
        return receipt;
    }

    private Station findStation(String name) {
        Station station = stations.get(name.toLowerCase());
        if (station == null) {
            throw new IllegalArgumentException("Unknown station " + name);
        }
        return station;
    }
}
