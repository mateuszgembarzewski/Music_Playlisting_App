import java.util.Objects;

/**
 * Represents a song with a title, creator (artist), and duration.
 * <p>
 * Songs are uniquely identified by their title and creator combination.
 * The {@code equals} and {@code hashCode} methods are case-insensitive
 * with respect to these fields.
 * </p>
 */
public class Song {

    /** The title of the song. */
    private String title;

    /** The artist or creator of the song. */
    private String creator;

    /** The duration of the song, in seconds. */
    private int duration;

    /**
     * Constructs a new {@code Song} instance.
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
     * Returns the title of the song.
     *
     * @return the song title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the artist or creator of the song.
     *
     * @return the creator (artist) of the song
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Returns the duration of the song in seconds.
     *
     * @return the song duration, in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Compares this song to another object for equality.
     * <p>
     * Two songs are considered equal if both their title and creator
     * are the same, ignoring case.
     * </p>
     *
     * @param obj the object to compare to
     * @return {@code true} if the songs are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return title.equalsIgnoreCase(song.title) &&
               creator.equalsIgnoreCase(song.creator);
    }

    /**
     * Returns a hash code value for this song.
     * <p>
     * The hash code is computed in a case-insensitive manner based on
     * the title and creator.
     * </p>
     *
     * @return the hash code of this song
     */
    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), creator.toLowerCase());
    }

    /**
     * Returns a string representation of this song, including its title,
     * creator, and formatted duration.
     * <p>
     * Example output: {@code "Shape of You – Ed Sheeran - 03:45"}
     * </p>
     *
     * @return a human-readable string representation of the song
     */
    @Override
    public String toString() {
        return title + " – " + creator + " - " + durationToString();
    }

    /**
     * Converts the song duration from seconds to a formatted string
     * in {@code mm:ss} format.
     *
     * @return the formatted duration string
     */
    public String durationToString() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
