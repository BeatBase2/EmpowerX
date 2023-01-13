import java.io.IOException;
import java.io.RandomAccessFile;

public class User {
    private String password;// Minimun 8 characters max 20 characters
    private String username;// Minimum 4 max 20 characters
    private int usernum;//

    private final int recLen = 80;
    public User(){
        this.setPassword("");
        this.setUsername("");
    }

    public User(String pass,String user){
        this.setUsername(user);
        this.setPassword(pass);
    }

    public int getUsernum() {
        return usernum;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsernum(int usernum) {
        this.usernum = usernum;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void writeUser (RandomAccessFile raf, int num)throws IOException{
        raf.seek(this.getUsernum() * recLen);
    }

}
