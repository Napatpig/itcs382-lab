package bikeshare;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BikeShareService service = new BikeShareService(new CsvRideLog("rides.csv"));
        System.out.println("Commands: rent <memberId> <student|staff> <station>, "
                + "return <memberId> <station> <minutes>, quit");
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNextLine()) {
                String[] w = in.nextLine().trim().split("\\s+");
                if (w[0].equals("quit")) {
                    break;
                }
                System.out.println(run(service, w));
            }
        }
    }

    private static String run(BikeShareService service, String[] w) {
        try {
            if (w[0].equals("rent")) {
                MemberType type = MemberType.valueOf(w[2].toUpperCase());
                return "Bike " + service.rent(w[1], type, w[3]) + " rented";
            } else if (w[0].equals("return")) {
                int minutes = Integer.parseInt(w[3]);
                if (minutes < 0) {
                    return "Invalid minutes";
                }
                return service.giveBack(w[1], w[2], minutes).toString();
            }
            return "Unknown command";
        } catch (IOException e) {
            return "Cannot save rides.csv: " + e.getMessage();
        } catch (IllegalArgumentException | IllegalStateException
                 | ArrayIndexOutOfBoundsException e) {
            return "Invalid command: " + e.getMessage();
        }
    }
}
