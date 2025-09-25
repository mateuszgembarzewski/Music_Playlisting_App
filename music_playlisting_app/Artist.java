import java.util.ArrayList;
import java.util.List;

/**
 * Artist/Creator user. Can add songs to the global catalog and create public playlists.
 */
public class Artist extends User {
    private List<Song> personalCatalog;

    public Artist(String email, String username, String password) {
        super(email, username, password);
        this.personalCatalog = new ArrayList<>();
    }

    public List<Song> getPersonalCatalog() {
        return personalCatalog;
    }

    /**
     * Add a Song object to the global catalog and artist's personal catalog.
     * Returns true on success, false on invalid input or duplicate in artist's catalog.
     */
    public boolean addSong(List<Song> globalCatalog, String title, int durationInSecs) {
        // Validate title and duration
        if (title == null || title.trim().isEmpty()) return false;
        if (durationInSecs <= 0 || durationInSecs > 600) return false;

        Song newSong = new Song(title.trim(), this.getUsername(), durationInSecs);

        // Prevent duplicate title by this artist in personal catalog
        for (Song s : personalCatalog) {
            if (s.equals(newSong)) return false;
        }

        // Add to both global and personal catalogs
        personalCatalog.add(newSong);
        globalCatalog.add(newSong);
        return true;
    }

    /**
     * Create a public playlist (returns the created Playlist)
     */
    public Playlist createPublicPlaylist(String name) {
        return new Playlist(name, this.getUsername(), new ArrayList<>());
    }
}
