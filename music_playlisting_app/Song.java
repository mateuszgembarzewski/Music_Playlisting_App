
/**
 * Write a description of class Song here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Song
{
    // instance variables - replace the example below with your own
    private String name;
    private String creator; 
    private int durationInSecs;

    /**
     * Constructor for objects of class Song
     */
    public Song(String name, String creator, int duration)
    {
        this.name = name;
        this.creator = creator;
        durationInSecs = duration; 
    }

    public String getSongName() {
        return name;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public int getDurationInSecs() {
        return durationInSecs;
    }
}