import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadWrite {
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
    private static void ModifyRAF(String fname, int charnum, int stattochange,int stat) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fname, "rw");
        raf.seek(charnum*recLen+78);
        raf.skipBytes(4*stattochange);
        raf.writeInt(stat);
    }
    /*
    From Mr.McKay ICS4U
    Name:readNewBinFile
    Purpose: method to read from a RAF file
    Parameters: File name to read and Array to look through
    Return Value: n/a
    Dependencies: java.IO.*
    Throws/Exceptions: IOException
    */
    public static void readNewBinFile(String filename, Character[] Characters) throws IOException{
        RandomAccessFile raf = new RandomAccessFile(filename,"rw");
        int numrecs = (int)raf.length()/recLen;
        for (int i=0; i < numrecs; i++) {
            Characters[i].readRec(raf, i);
        } //end for
        raf.close();
        System.out.println("File closed");
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
    public static void writeNewBinFile(String filename, Character Characters[], int numrecs) throws IOException{
        RandomAccessFile raf = new RandomAccessFile(filename,"rw");
        raf.setLength(numrecs*recLen);
        for (int i=0; i < numrecs; i++) {
            Characters[i].writeRec(raf, i);
        }
        raf.close();
    } // end writeNewBinFile
    /*
  From Mr.McKay ICS4U
  Name:readRec
  Purpose: method to read from a RAF file
  Parameters: File name to read and amount of records
  Return Value: n/a
  Dependencies: java.IO.*
  Throws/Exceptions: throws IOException
  */
    public void readRec (RandomAccessFile raf, int recNum) throws IOException   {
        raf.seek (recNum * recLen);
        String temp = "";
        for (int i = 0 ; i < 20 ; i++)
            temp = temp + raf.readChar();
        Charactername = temp.trim();
        temp = "";
        for (int i = 0 ; i < 8 ; i++)
            temp = temp + raf.readChar();
        Race = temp.trim();
        temp = "";
        for (int i = 0 ; i < 7 ; i++)
            temp = temp + raf.readChar();
        CClass = temp.trim();
        // read the Int from the file
        Level = raf.readInt();
        Hitpoints = raf.readInt();
        Strength = raf.readInt();
        Constitution = raf.readInt();
        Intelligence = raf.readInt();
        Wisdom = raf.readInt();
        Dexterity = raf.readInt();
        Charisma = raf.readInt();
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
    public void writeRec (RandomAccessFile raf, int recordNumber) throws IOException {
        raf.seek (recordNumber * recLen); // move pointer to position in file
        int nameLen = Charactername.length ();// determine if there are more than 20 characters
        int padLen = 0;						// calculate the number of blanks that need to be
        if (nameLen > 20)					//  added to maintain a length of 20
            nameLen = 20;
        else
            padLen = 20 - nameLen;
        for (int i = 0 ; i < Charactername.length () ; i++)	// write the name
            raf.writeChar (Charactername.charAt (i));
        if (padLen > 0)	{					// write the extra blanks
            for (int i = 0 ; i < padLen ; i++)
                raf.writeChar (' ');
        }
        // repeat for Race
        nameLen = Race.length ();
        padLen = 0;
        if (nameLen > 8)
            nameLen = 8;
        else
            padLen = 8 - nameLen;
        for (int i = 0 ; i < Race.length () ; i++)
            raf.writeChar (Race.charAt (i));
        if (padLen > 0)	{
            for (int i = 0 ; i < padLen ; i++)
                raf.writeChar (' ');
        }
        // repeat for Class
        nameLen = CClass.length ();
        padLen = 0;
        if (nameLen > 7)
            nameLen = 7;
        else
            padLen = 7 - nameLen;
        for (int i = 0 ; i < CClass.length () ; i++)
            raf.writeChar (CClass.charAt (i));
        if (padLen > 0)	{
            for (int i = 0 ; i < padLen ; i++)
                raf.writeChar (' ');
        }
        // write the int to the file
        raf.writeInt (Level);
        raf.writeInt (Hitpoints);
        raf.writeInt (Strength);
        raf.writeInt (Constitution);
        raf.writeInt (Intelligence);
        raf.writeInt (Wisdom);
        raf.writeInt (Dexterity);
        raf.writeInt (Charisma);
    }
}
