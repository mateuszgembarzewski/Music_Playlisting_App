
/**
 * Write a description of class Artist here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Artist extends User
{
    // instance variables - replace the example below with your own
    private int artistID;
    /**
     * Constructor for objects of class Admin
     */
    public Artist(String email, String username, String password, int artistID)
    {
       super(email, username, password);
       this.artistID = artistID;
    }
    
    public int getArtistID() {
        return artistID;
    }
    
}