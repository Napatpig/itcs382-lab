package bikeshare;

import java.io.FileWriter;
import java.io.IOException;

public class CsvRideLog implements RideLog {
    private final String fileName;

    public CsvRideLog(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void append(Receipt receipt) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(receipt.bikeId() + "," + receipt.memberId() + ","
                    + receipt.minutes() + "," + receipt.fee() + "\n");
        }
    }
}
