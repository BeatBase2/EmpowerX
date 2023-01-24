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
    private static final int recLen = 196;//length of one User
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
        RandomAccessFile raf = new RandomAccessFile("Users","rw");
        raf.setLength(UsersList.size()*recLen);
        System.out.println(UsersList.size());
        System.out.println(raf.length());
        for (int i=0; i < UsersList.size(); i++) {
            User.writeRec(raf,i,UsersList);
        }
    } // end writeNewBinFile
}
