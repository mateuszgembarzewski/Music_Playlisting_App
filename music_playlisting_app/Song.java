/**
 * Song model class.
 */
public class Song {
    private String name;
    private String creator;
    private int durationInSecs;

    /**
     * Constructor. Duration must be >0 and <=600 (10 minutes).
     */
    public Song(String name, String creator, int durationInSecs) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Song title must not be empty.");
        }
        if (creator == null || creator.trim().isEmpty()) {
            throw new IllegalArgumentException("Creator must not be empty.");
        }
        if (durationInSecs <= 0 || durationInSecs > 600) {
            throw new IllegalArgumentException("Duration must be 1..600 seconds.");
        }
        this.name = name.trim();
        this.creator = creator.trim();
        this.durationInSecs = durationInSecs;
    }

    public String getSongName() { return name; }
    public String getCreator() { return creator; }
    public int getDurationInSecs() { return durationInSecs; }

    public String durationFormatted() {
        int minutes = durationInSecs / 60;
        int seconds = durationInSecs % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return name + " - " + creator + " - " + durationFormatted();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Song)) return false;
        Song other = (Song) obj;
        // songs considered equal if same title and same creator
        return this.name.equalsIgnoreCase(other.name) &&
               this.creator.equalsIgnoreCase(other.creator);
    }

    @Override
    public int hashCode() {
        return (name.toLowerCase() + "::" + creator.toLowerCase()).hashCode();
    }
}
