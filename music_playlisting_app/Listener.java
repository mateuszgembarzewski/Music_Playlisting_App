import java.util.ArrayList;
import java.util.List;

/**
 * Listener user. Has personal library (songs) and playlists.
 */
public class Listener extends User {
    private int listenerId;
    private List<Playlist> playlists;
    private List<Song> personalLibrary; // songs added directly to library

    public Listener(String email, String username, String password, int listenerId, ArrayList<Playlist> initialPlaylists) {
        super(email, username, password);
        this.listenerId = listenerId;
        this.playlists = (initialPlaylists != null) ? initialPlaylists : new ArrayList<>();
        this.personalLibrary = new ArrayList<>();
    }

    public int getListenerId() { return listenerId; }
    public List<Playlist> getPlaylists() { return playlists; }
    public List<Song> getPersonalLibrary() { return personalLibrary; }

    /** Create new empty playlist and return it */
    public Playlist createNewPlaylist(String playlistName) {
        Playlist p = new Playlist(playlistName, this.getUsername(), new ArrayList<>());
        playlists.add(p);
        return p;
    }

    /** Lists playlist summaries (returns as a list of strings for testability) */
    public List<String> listPlaylistsSummary() {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < playlists.size(); i++) {
            lines.add("[" + i + "] - " + playlists.get(i).toString());
        }
        return lines;
    }

    public Playlist getPlaylist(int index) {
        if (index < 0 || index >= playlists.size()) return null;
        return playlists.get(index);
    }

    /** Add song to personal library. Returns true if added, false if duplicate. */
    public boolean addSongToLibrary(Song song) {
        if (song == null) return false;
        for (Song s : personalLibrary) {
            if (s.equals(song)) return false;
        }
        personalLibrary.add(song);
        return true;
    }

    /** Clear all playlists (used by Admin) */
    public void clearPlaylists() {
        playlists.clear();
    }
}
