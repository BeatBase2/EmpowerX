import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class User {
    private static final Random RANDOM = new SecureRandom();
    public static ArrayList<User> UsersList = new ArrayList<User>();

    private String password;// max 20 characters
    private String username;// max 20 characters
    private String salt; //12 characters
    private static final int recLen = 104;
    public User(){
        this.setSalt("");
        this.setUsername("");
        this.setPassword("");
    }

    public User(String salt,String hash,String user){
        this.setSalt(salt);
        this.setPassword(hash);
        this.setUsername(user);
    }

    public String getSalt() {return salt;}
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setSalt(String salt) {this.salt = salt;}

    /*
        public void writeUser (RandomAccessFile raf, int num)throws IOException{
            raf.seek(this.getUsernum() * recLen);
        }

     */
    public static String Createsalt() {
        String salt = new String(Base64.getEncoder().encode(getNextSalt()));
        return salt;
    }
    public static byte[] getNextSalt() {
        byte[] salt = new byte[8];
        RANDOM.nextBytes(salt);
        return salt;
    }
    public static int Searchlist(String name, ArrayList<User> UsersList){
        if (UsersList.size() > 0) {
            for (int i = 0; i < UsersList.size(); i++) {
                String next = UsersList.get(i).getUsername();

                if (next.equalsIgnoreCase(name)) {
                    return i;
                }
            }
        }
        return -1;
    }
    /*
From Mr.McKay ICS4U
Name:readRec
Purpose: method to read from a RAF file
Parameters: File name to read and amount of records
Return Value: n/a
Dependencies: java.IO.*
Throws/Exceptions: throws IOException
*/
    public static User readRec(RandomAccessFile raf, int recNum) throws IOException {
        User obj = new User();
        raf.seek(recNum * recLen);
        String temp = "";
        for (int i = 0; i < 20; i++) {
            temp = temp + raf.readChar();
            System.out.println(i + " temp:" + temp);
        }
        obj.setUsername(temp.trim());
        temp = "";
        for (int i = 0; i < 20; i++) {
            temp = temp + raf.readChar();
            System.out.println(i + " temp:" + temp);
        }
         obj.setPassword(temp.trim());
        temp = "";
        for (int i = 0; i < 12; i++) {
            temp = temp + raf.readChar();
            System.out.println(i + " temp:" + temp);
        }
        obj.setSalt(temp.trim());
        return obj;
    }  // end readRec
    /*
        From Mr.McKay ICS4U
        Name:writeRec
        Purpose: method to write a RAF file
        Parameters: File name to write and number of records
        Return Value: n/a
        Dependencies: java.IO.*,
        Throws/Exceptions: throws IOException
        */
    public static void writeRec(RandomAccessFile raf, int recordNumber, ArrayList<User> UsersList) throws IOException {
        raf.seek (recordNumber * recLen); // move pointer to position in file
        int nameLen = UsersList.get(recordNumber).getUsername().length();// determine if there are more than 20 characters
        int padLen = 0;						// calculate the number of blanks that need to be
        if (nameLen > 20)					//  added to maintain a length of 20
            nameLen = 20;
        else
            padLen = 20 - nameLen;
        for (int i = 0 ; i < UsersList.get(recordNumber).getUsername().length(); i++)	// write the name
            raf.writeChar (UsersList.get(recordNumber).getUsername().charAt(i));
        if (padLen > 0)	{					// write the extra blanks
            for (int i = 0 ; i < padLen ; i++)
                raf.writeChar (' ');
        }
        // repeat for Race
        nameLen = UsersList.get(recordNumber).getPassword().length ();
        padLen = 0;
        if (nameLen > 20)
            nameLen = 20;
        else
            padLen = 20 - nameLen;
        for (int i = 0 ; i < UsersList.get(recordNumber).getPassword().length () ; i++)
            raf.writeChar (UsersList.get(recordNumber).getPassword().charAt (i));
        if (padLen > 0)	{
            for (int i = 0 ; i < padLen ; i++)
                raf.writeChar (' ');
        }
        nameLen = UsersList.get(recordNumber).getSalt().length ();
        padLen = 0;
        if (nameLen > 12)
            nameLen = 12;
        else
            padLen = 12 - nameLen;
        for (int i = 0 ; i < UsersList.get(recordNumber).getSalt().length () ; i++) {
            raf.writeChar(UsersList.get(recordNumber).getSalt().charAt(i));
        }
        if (padLen > 0)	{
            for (int i = 0 ; i < padLen ; i++)
                raf.writeChar (' ');
        }
    }
}
