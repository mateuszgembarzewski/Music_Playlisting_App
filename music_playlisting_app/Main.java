public class Main { // Replace 'Main' with your chosen class name
     public static void main(String[] args) {
         
        Song song1 = new Song("SongName", "ArtistCreator", 300);
        
        Song song2 = new Song("IllegalSong", "ArtistCreator", 601);
        
        System.out.println("Song1's duration in seconds is: " + song1.getDurationInSecs());
        
        //System.out.println("Testing to see if song2's length limit works: " + song1.getDurationInSecs());

        Admin Admin1 = new Admin("user@gmail.com","Bob","Qwerty123", 1);
 
        Listener listener1 = new Listener("listener@gmail.com","Matt","GoodPass1!", 1);
        
        Artist artist1 = new Artist("artist@gmail.com","Artie","Pastel4u?", 1);
         
        System.out.println(Admin1.getUsername());
         
        System.out.println(Admin1.getPassword());
         
        System.out.println(listener1.getUsername());
        
        System.out.println(listener1.getPassword());
        
        System.out.println(artist1.getUsername());
        
        System.out.println(artist1.getPassword());
        
        System.out.println(authenticate("Bob","Qwerty123", Admin1));
        
        System.out.println(authenticate("Matt","GoodPass1!", listener1));
        
        System.out.println(authenticate("Artie","Pastel4u?", artist1));
        
     }
     
     private static boolean authenticate(String username, String password, User user) {         
         if ( user.getUsername().equals(username) && user.getPassword().equals(password) ) {
            return true;
         } else {
            return false;             
         }
     }
}