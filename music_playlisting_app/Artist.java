import java.util.*;

public class Artist extends User {
    private List<Song> catalog;

    public Artist(String email, String username, String password, int id) {
        super(email, username, password, id);
        this.catalog = new ArrayList<>();
    }

    public boolean addSongToCatalog(Song song) {
        if (song == null || catalog.contains(song)) return false;
        catalog.add(song);
        return true;
    }

    public List<Song> getCatalog() {
        return catalog;
    }
}
