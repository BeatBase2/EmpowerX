import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ReadWrite {
    private static final int recLen = 756;//length of one User
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
        RandomAccessFile raf = new RandomAccessFile("Users","rw");
        int numrecs = (int)raf.length()/recLen;//number of users
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
        for (int i=0; i < UsersList.size(); i++) {
            User.writeRec(raf,i,UsersList);
        }
    } // end writeNewBinFile
}
