import java.util.Objects;

/**
 * Represents a song with a title, creator (artist), and duration.
 * Songs are uniquely identified by their title and creator combination.
 */
public class Song {
    private String title;
    private String creator;
    private int duration; // Duration in seconds

    /**
     * Constructs a new Song.
     *
     * @param title    the title of the song
     * @param creator  the artist or creator of the song
     * @param duration the duration of the song in seconds
     */
    public Song(String title, String creator, int duration) {
        this.title = title;
        this.creator = creator;
        this.duration = duration;
    }

    /**
     * @return the title of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the creator (artist) of the song
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @return the duration of the song in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Checks equality between two Song objects.
     * Songs are considered equal if both their title and creator match,
     * ignoring case.
     *
     * @param obj the object to compare against
     * @return true if the songs are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return title.equalsIgnoreCase(song.title) && creator.equalsIgnoreCase(song.creator);
    }

    /**
     * Generates a case-insensitive hash code for the song,
     * based on its title and creator.
     *
     * @return the hash code of the song
     */
    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), creator.toLowerCase());
    }

    /**
     * @return a string representation of the song in the format "title – creator"
     */
    @Override
    public String toString() {
        return title + " – " + creator + " - " + durationToString();
    }
    
    public String durationToString() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
