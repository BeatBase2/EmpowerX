import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class User {
    public static class Budget {
        public int balance;//4
        public int allowance;//4
        public int spent;//4
        public Budget(){
            this.setAllowance(0);
            this.setBalance(0);
            this.setSpent(0);
        }
        //Budget setters and getters
        public int getBalance() {
            return balance;
        }
        public int getAllowance() {
            return allowance;
        }
        public int getSpent() {
            return spent;
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

    private String password;// max 20 characters 40
    private String username;// max 20 characters 40
    private String salt; //12 characters 24
    private Budget budget;// 12
    //116
    private static String [][] data = new String[10][3];
//20 40 4
    private static final int recLen = 756;
    public User(){
        this.setSalt("");
        this.setUsername("");
        this.setPassword("");
        this.setBudget(budget);
        this.setData(data);
    }
    public User(String salt,String hash,String user,Budget bud,String[][] dat){
        this.setSalt(salt);
        this.setPassword(hash);
        this.setUsername(user);
        this.setBudget(bud);
        this.setData(dat);
    }

    public String[][] getData() {
        return data;
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
    public void setData(String[][] data) {
        User.data = data;
    }

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
        obj.getBudget().setAllowance(raf.readInt());
        obj.getBudget().setBalance(raf.readInt());
        obj.getBudget().setSpent(raf.readInt());
        for (int r = 0; r < 10; r++) {
            for (int j = 0; j < 3; j++) {
                if (j==0) {
                    temp = "";
                    for (int i = 0; i < 10; i++) {//Name
                        temp = temp + raf.readChar();
                    }
                    if (temp.equals("          ")){
                        data[r][j] = "";
                    }else data[r][j] = temp;
                } else if (j==1) {
                    temp = "";
                    for (int i = 0; i < 20; i++) {//Description
                        temp = temp + raf.readChar();
                    }
                    if (temp.equals("                    ")){
                        data[r][j] = "";
                    }else
                    data[r][j] = temp;
                } else if (j==2) {//Amount
                    data[r][j] = String.valueOf(raf.readInt());
                }
            }
        }
        obj.setData(data);
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
        raf.writeInt(UsersList.get(recordNumber).getBudget().getAllowance());
        raf.writeInt(UsersList.get(recordNumber).getBudget().getBalance());
        raf.writeInt(UsersList.get(recordNumber).getBudget().getSpent());
        for (int r = 0; r < 10; r++) {
            for (int j = 0; j < 3; j++) {
                if (j==0){//Name
                    nameLen = data[r][j].length();
                    padLen = 0;
                    if (nameLen > 10)
                        nameLen = 10;
                    else
                        padLen = 10 - nameLen;
                    for (int i = 0 ; i < data[r][j].length();i++)
                        raf.writeChar (data[r][j].charAt(i));
                    if (padLen > 0)	{
                        for (int i = 0 ; i < padLen ; i++)
                            raf.writeChar (' ');
                    }
                } else if (j==1) {//Description
                    nameLen = data[r][j].length();
                    padLen = 0;
                    if (nameLen > 20)
                        nameLen = 20;
                    else
                        padLen = 20 - nameLen;
                    for (int i = 0 ; i < data[r][j].length();i++)
                        raf.writeChar (data[r][j].charAt(i));
                    if (padLen > 0)	{
                        for (int i = 0 ; i < padLen ; i++)
                            raf.writeChar (' ');
                    }
                } else if (j==2) {//Amount
                    if (data[r][2].equals("")){
                        raf.writeInt(0);
                    }else raf.writeInt(Integer.parseInt(data[r][2]));
                }
            }
        }
    }
}
