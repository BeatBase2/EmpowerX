import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class User {

    public static class Budget {

        public String Name;//10
        public String Description;//20
        public int balance;//4
        public int allowance;//4
        public int spent;//4
        public int ammount;//4
        public Budget(){
            this.setName("");
            this.setDescription("");
            this.setAllowance(0);
            this.setBalance(0);
            this.setSpent(0);
            this.setAmmount(0);
        }
        public Budget(String name,String desc,int all,int bal,int spent,int amt){
            this.setName(name);
            this.setDescription(desc);
            this.setAllowance(all);
            this.setBalance(bal);
            this.setSpent(spent);
            this.setAmmount(amt);
        }
        public String getDescription() {
            return Description;
        }
        public String getName() {
            return Name;
        }
        public int getBalance() {
            return balance;
        }
        //Budget setters and getters
        public int getAllowance() {
            return allowance;
        }

        public int getSpent() {
            return spent;
        }

        public int getAmmount() {
            return ammount;
        }

        public void setName(String name) {
            Name = name;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public void setAmmount(int ammount) {
            this.ammount = ammount;
        }

        public void setSpent(int spent) {
            this.spent = spent;
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
    private Budget budget;// 4 ints 2 strings = 76bytes
    //104
    private static final int recLen = 180;
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
        obj.setBudget(new Budget());
        raf.seek(recNum * recLen);
        String temp = "";
        String temp1 = "";
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
        temp = "";
        for (int i = 0; i < 10; i++) {
            temp = temp + raf.readChar();
        }
        obj.getBudget().setName(temp.trim());
        temp = "";
        for (int i = 0; i < 20; i++) {
            temp = temp + raf.readChar();
        }
        obj.getBudget().setDescription(temp.trim());
        obj.getBudget().setAllowance(raf.readInt());
        obj.getBudget().setBalance(raf.readInt());
        obj.getBudget().setSpent(raf.readInt());
        obj.getBudget().setAmmount(raf.readInt());
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
        }//repeat...
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
        nameLen = UsersList.get(recordNumber).getBudget().getName().length();
        padLen = 0;
        if (nameLen > 10)
            nameLen = 10;
        else
            padLen = 10 - nameLen;
        for (int i = 0 ; i < UsersList.get(recordNumber).getBudget().getName().length();i++)
            raf.writeChar (UsersList.get(recordNumber).getBudget().getName().charAt(i));
        if (padLen > 0)	{
            for (int i = 0 ; i < padLen ; i++)
                raf.writeChar (' ');
        }
        nameLen = UsersList.get(recordNumber).getBudget().getDescription().length();
        padLen = 0;
        if (nameLen > 20)
            nameLen = 20;
        else
            padLen = 20 - nameLen;
        for (int i = 0 ; i < UsersList.get(recordNumber).getBudget().getDescription().length(); i++)
            raf.writeChar (UsersList.get(recordNumber).getBudget().getDescription().charAt (i));
        if (padLen > 0)	{
            for (int i = 0 ; i < padLen ; i++)
                raf.writeChar (' ');
        }

        raf.writeInt(UsersList.get(recordNumber).getBudget().getAllowance());
        raf.writeInt(UsersList.get(recordNumber).getBudget().getBalance());
        raf.writeInt(UsersList.get(recordNumber).getBudget().getSpent());
        raf.writeInt(UsersList.get(recordNumber).getBudget().getAmmount());
    }
}
