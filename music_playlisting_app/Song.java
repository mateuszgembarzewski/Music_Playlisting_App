
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
        
        if( duration > 600 ) {
             throw new IllegalArgumentException("Song cannot be longer than 10 mins.");
        }
        
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
    
    public String durationFormatted() {
        int minutes = durationInSecs / 60;
        int seconds = durationInSecs % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    public String toString() {
        return name + " - " + creator + " - " + durationFormatted();
    }
}