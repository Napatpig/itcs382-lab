package bikeshare;

import java.io.IOException;

public interface RideLog {
    void append(Receipt receipt) throws IOException;
}
