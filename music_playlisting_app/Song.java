import java.util.Objects;

public class Song {
    private String title;
    private String creator;
    private int duration; // Duration in seconds

    public Song(String title, String creator, int duration) {
        this.title = title;
        this.creator = creator;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getCreator() {
        return creator;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return title.equalsIgnoreCase(song.title) && creator.equalsIgnoreCase(song.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), creator.toLowerCase());
    }

    @Override
    public String toString() {
        return title + " – " + creator;
    }
}
