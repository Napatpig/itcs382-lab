package bikeshare;

public record Receipt(String memberId, String bikeId, int minutes, int fee) {
    @Override
    public String toString() {
        return "Receipt: bike " + bikeId + ", " + minutes + " minutes, fee " + fee + " baht";
    }
}
