import java.util.*;

public class Artist extends User {
    private List<Song> catalog;

    public Artist(String email, String username, String password, int id) {
        super(email, username, password, id);
        this.catalog = new ArrayList<>();
    }

    public boolean addSongToCatalog(Song song) {
        if (song == null) return false;
    
        // Validation: title and artist name cannot be empty, duration must be positive
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) return false;
        if (song.getCreator() == null || song.getCreator().trim().isEmpty()) return false;
        if (song.getDuration() <= 0) return false;
    
        if (catalog.contains(song)) return false; // Prevent duplicates
            catalog.add(song);
        return true;
    }

    public List<Song> getCatalog() {
        return catalog;
    }
}
