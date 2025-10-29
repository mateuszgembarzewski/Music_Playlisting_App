

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class EssayTestStuff.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class EssayTestStuff
{
    /**
     * Default constructor for test class EssayTestStuff
     */
    public EssayTestStuff()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    @Test
    public void songABCSucceed() {
        SearchService CATALOG = new SearchService();
        Artist artist = new Artist("artist@gmail.com", "artist", "artistpass", 2);
        
        Song song = new Song("testTitle", "testCreator", 1);
        boolean outcome = artist.addSongToCatalog(CATALOG, song);
        
        assertTrue(outcome);
    }
    
    @Test
    public void songAFails() {
        SearchService CATALOG = new SearchService();
        Artist artist = new Artist("artist@gmail.com", "artist", "artistpass", 2);
        
        Song song = new Song("", "testCreator", 1);
        boolean outcome = artist.addSongToCatalog(CATALOG, song);
        
        assertFalse(outcome);
    }
    
    @Test
    public void songBFails() {
        SearchService CATALOG = new SearchService();
        Artist artist = new Artist("artist@gmail.com", "artist", "artistpass", 2);
        
        Song song = new Song("testTitle", "", 1);
        boolean outcome = artist.addSongToCatalog(CATALOG, song);
        
        assertFalse(outcome);
    }
    
    @Test
    public void songCFails() {
        SearchService CATALOG = new SearchService();
        Artist artist = new Artist("artist@gmail.com", "artist", "artistpass", 2);
        
        Song song = new Song("testTitle", "testCreator", -1);
        boolean outcome = artist.addSongToCatalog(CATALOG, song);
        
        assertFalse(outcome);
    }
}
