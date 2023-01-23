import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class User {

    public static class Budget {

        public int balance;
        public int allowance;

        public Budget(){
            this.setAllowance(0);
            this.setBalance(0);
        }

        public Budget(int all,int bal){
            this.setAllowance(all);
            this.setBalance(bal);
        }
        public int getBalance() {
            return balance;
        }
        //Budget setters and getters
        public int getAllowance() {
            return allowance;
        }

        public void setAllowance(int allowance) {
            this.allowance = allowance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }
    private static final Random RANDOM = new SecureRandom();//Used to generate a salt ot hash the passwords
    public static ArrayList<User> UsersList = new ArrayList<User>();//Stores User information

    private String password;// max 20 characters
    private String username;// max 20 characters
    private String salt; //12 characters
    private Budget budget;// 2 ints = 8 bytes
    private static final int recLen = 120;
    public User(){
        this.setSalt("");
        this.setUsername("");
        this.setPassword("");
        this.setBudget(budget);
    }
    public User(String salt,String hash,String user,Budget bud){
        this.setSalt(salt);
        this.setPassword(hash);
        this.setUsername(user);
        this.setBudget(bud);
    }
    public Budget getBudget() {
        return budget;
    }
    //User setters and getters
    public String getSalt() {return salt;}
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public void setBudget(Budget budget) {
        this.budget = budget;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setSalt(String salt) {this.salt = salt;}

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
        }
        obj.setUsername(temp.trim());
        temp = "";
        for (int i = 0; i < 20; i++) {
            temp = temp + raf.readChar();
        }
         obj.setPassword(temp.trim());
        temp = "";
        for (int i = 0; i < 12; i++) {
            temp = temp + raf.readChar();
        }
        obj.setSalt(temp.trim());
        obj.setBudget(new Budget(raf.readInt(), raf.readInt()));
        return obj;
    }  // end readRec
    /*
        From Mr.McKay ICS4U
        Name:writeRec
        Purpose: method to write a RAF file
        Parameters: Raf file,Amount of individual records,Array list of user
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
        raf.writeInt(UsersList.get(recordNumber).getBudget().getAllowance());
        raf.writeInt(UsersList.get(recordNumber).getBudget().getBalance());
    }
}
