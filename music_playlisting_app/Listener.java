
/**
 * Write a description of class Listener here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Listener extends User
{
    // instance variables - replace the example below with your own
   //  private String[] library; -- It can also be a Song Array
    private int listenerID;
    /**
     * Constructor for objects of class Admin
     */
    public Listener(String email, String username, String password, int listenerID)
    {
       super(email, username, password);
       this.listenerID = listenerID;
    }
    
    public int getListenerID() {
        return listenerID;
    }
    
}