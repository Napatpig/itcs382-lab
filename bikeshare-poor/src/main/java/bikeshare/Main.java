package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final String[] STATIONS = {"Library", "Canteen", "Dorm"};
    public static String[] bikeId = {"B1", "B2", "B3", "B4", "B5", "B6"};
    public static int[] bikeAt = {0, 0, 1, 1, 2, 2};   // station index, -1 = rented out
    public static String[] rider = new String[6];
    public static int[] riderType = new int[6];        // 1 = student, 2 = staff

    public static void main(String[] args) {
        System.out.println("Commands: rent <memberId> <student|staff> <station>, "
                + "return <memberId> <station> <minutes>, quit");
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNextLine()) {
                String[] w = in.nextLine().trim().split("\\s+");
                if (w[0].equals("quit")) {
                    break;
                } else if (w[0].equals("rent") || w[0].equals("return")) {
                    handle(w, w[0].equals("rent"));
                } else {
                    System.out.println("Unknown command");
                }
            }
        }
    }

    public static int stationIndex(String name) {
        for (int i = 0; i < STATIONS.length; i++) {
            if (STATIONS[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public static void handle(String[] w, boolean rent) {
        try {
            if (rent) {
                int type = w[2].equals("student") ? 1 : w[2].equals("staff") ? 2 : 0;
                int station = stationIndex(w[3]);
                if (type == 0 || station < 0) {
                    System.out.println("Invalid member type or station");
                    return;
                }
                for (int i = 0; i < bikeId.length; i++) {
                    if (w[1].equals(rider[i])) {
                        System.out.println("Member already has a bike");
                        return;
                    }
                }
                for (int i = 0; i < bikeId.length; i++) {
                    if (bikeAt[i] == station) {
                        bikeAt[i] = -1;
                        rider[i] = w[1];
                        riderType[i] = type;
                        System.out.println("Bike " + bikeId[i] + " rented");
                        return;
                    }
                }
                System.out.println("No bike available at " + STATIONS[station]);
            } else {
                int station = stationIndex(w[2]);
                int minutes = Integer.parseInt(w[3]);
                if (station < 0 || minutes < 0) {
                    System.out.println("Invalid station or minutes");
                    return;
                }
                for (int i = 0; i < bikeId.length; i++) {
                    if (w[1].equals(rider[i])) {
                        int fee = minutes * (riderType[i] == 1 ? 2 : 3);
                        bikeAt[i] = station;
                        rider[i] = null;
                        System.out.println("Receipt: bike " + bikeId[i] + ", " + minutes
                                + " minutes, fee " + fee + " baht");
                        save(bikeId[i], w[1], minutes, fee);
                        return;
                    }
                }
                System.out.println("No active rental for " + w[1]);
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Invalid command");
        }
    }

    public static void save(String bike, String member, int minutes, int fee) {
        try (FileWriter writer = new FileWriter("rides.csv", true)) {
            writer.write(bike + "," + member + "," + minutes + "," + fee + "\n");
        } catch (IOException e) {
            System.out.println("Cannot save rides.csv: " + e.getMessage());
        }
    }
}
