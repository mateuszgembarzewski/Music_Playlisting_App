import java.util.Objects;

/**
 *  Song represents a single song with a title, creator, and duration.
 */
public class Song {
    // Represents the title for the song
    private String title;

    // Represents the creator for a song
    private String creator;

    // Represents the duration of a song
    private int duration;

    /**
     * Constructor for Song objects 
     * 
     * @param title   name of the song
     * @param creator Artist who uploaded the Song's username
     */
    public Song(String title, String creator, int duration) {
        this.title = title;
        this.creator = creator;
        this.duration = duration;
    }

    /**
     * Getter for the Song's 'title'
     *
     * @return String storing the Song's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the Song's 'creator'
     *
     * @return String storing the Song's creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Getter for the Song's 'duration'
     *
     * @return int storing the Song's duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Override of the toString() method; prints a description of the Song
     *
     * @return String descriptor of a Song object
     */
    @Override
    public String toString() {
        return title + " – " + creator + " - " + durationToString();
    }

    /**
     * Converts the duration integer value of a song into a formatted string with colon separated values.
     *
     * @return String formatted time string
     */
    public String durationToString() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
