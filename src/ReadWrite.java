import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ReadWrite {

    private String fname;

    public ReadWrite() {
        this.setFname("");
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public static void initialize() {
    {
        try {
            RandomAccessFile raf = new RandomAccessFile("Users", "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

    private static final int recLen = 80;//length of one character

    /* Method Name: ModifyRAF
     * Author: Cason Cook
     * Creation Date; October, 19, 2022
     * Modified Date: October, 20, 2022
     * Description: Modifys a select stat of a RAF file
     * @Parameters: File name of raf,index of who to modify,index of what to modify, Stat to put in that place
     * @Return Value: n/a
     * Dependencies: java.io.*;
     *Throws/Exceptions: throws IOException
     */
    private void AddUser(String Username,String Password,String Salt) throws IOException {
      //  raf.setLength(raf.length()+80);
        RandomAccessFile raf = new RandomAccessFile("Users","rw");
        User newuser = new User(Password,Username,Salt);
        raf.seek(raf.length());
        User.writeRec(raf,(int)raf.length()/80,newuser);
    }
    /*
    From Mr.McKay ICS4U
    Name:readNewBinFile
    Purpose: method to read from a RAF file
    Parameters: File name to read and Array to fill
    Return Value: n/a
    Dependencies: java.IO.*
    Throws/Exceptions: IOException
    */
    public static void readNewBinFile(ArrayList<User> UsersList) throws IOException{
        User temp = new User();
        RandomAccessFile raf = new RandomAccessFile("Users","rw");
        int numrecs = (int)raf.length()/recLen;
        for (int i=0; i < numrecs; i++) {
            UsersList.add(User.readRec(raf, i));
        } //end for
    } // end readNewBinFile
    /*
    From Mr.McKay ICS4U
    Name:writeNewBinFile
    Purpose: method to write a RAF
    Parameters: File name to write to and Array to look through
    Return Value: n/a
    Dependencies: java.IO.*
    Throws/Exceptions: IOException
    */
    public static void writeNewBinFile(ArrayList<User> UsersList) throws IOException{
       // raf.setLength(UsersList.size()*recLen);
        RandomAccessFile raf = new RandomAccessFile("Users","rw");
        for (int i=0; i < UsersList.size(); i++) {
            User.writeRec(raf,i,UsersList);
        }
    } // end writeNewBinFile
}
