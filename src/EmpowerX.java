import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.Random;
public class EmpowerX {
    private static final Random RANDOM = new SecureRandom();
    public static byte[] getNextSalt() {
        byte[] salt = new byte[8];
        RANDOM.nextBytes(salt);
        return salt;
    }
    public static void printlist(ArrayList<User> UsersList){
        if (UsersList.get(0) != null) {
            for (int i = 0; i < UsersList.size(); i++) {
                String user = UsersList.get(i).getUsername();
                String pass = UsersList.get(i).getPassword();
                String salt = UsersList.get(i).getSalt();
                System.out.println("Username: " + user + "\tPassword: " + pass + "\tSalt: " + salt);
            }
        }else System.out.println("No users to print");
    }
    private static boolean Searchlist(String name, ArrayList<User> UsersList){
        if (UsersList.size() > 0) {
            if (UsersList.get(0) != null) {
                for (int i = 0; i < UsersList.size(); i++) {
                    String next = UsersList.get(i).getUsername();

                    if (next.equalsIgnoreCase(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static boolean Searchlist(String name, String word, ArrayList<User> UsersList){
        for (int i = 0;i< UsersList.size();i++){
            String user = UsersList.get(i).getUsername();
            String pass = UsersList.get(i).getPassword();
            if (user.equalsIgnoreCase(name)&&pass.equals(word)){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) throws IOException {
        User tempuser;
        boolean tempboolean;
        ArrayList<User> UsersList = new ArrayList<User>();
        ReadWrite.readNewBinFile(UsersList);
        Boolean login = false;
        String pass;
        String username;
        Scanner ui = new Scanner(System.in);
        do {
            System.out.println("1: Login 2:Create account 3: Print users");
            int x = Integer.parseInt(ui.nextLine());
            switch (x) {
                case 1:
                    tempboolean = true;
                    do {
                        System.out.println("Enter Username");
                        username = ui.nextLine();
                        System.out.println("Enter password");
                        pass = ui.nextLine();
                        if (Searchlist(username,pass,UsersList)) {
                            System.out.println("Success");
                            login = true;
                            tempboolean = false;
                        }else System.out.println("Invalid credentials, please try again");
                    }while (tempboolean);
                    break;
                case 2:
                    String salt = new String(Base64.getEncoder().encode(getNextSalt()));
                    tempboolean = true;
                    do {
                        do {
                            System.out.println("Enter a Username (20 Characters max)");
                            username = ui.nextLine();
                            if (username.length()>20) System.out.println("Username is too long please use only 20 characters");
                        }while (username.length()>20);
                        if (Searchlist(username,UsersList)){
                            System.out.println("Error, please use a username that is not in use");
                        }else {
                            do {
                                System.out.println("Enter a password (20 Characters max,Case sensitive)");
                                pass = ui.nextLine();
                                if (pass.length()>20) System.out.println("Password is too long please use only 20 characters");
                            }while (pass.length()>20);
                            tempboolean = false;
                            tempuser = new User(pass,username,salt);
                            UsersList.add(UsersList.size(),tempuser);
                            printlist(UsersList);
                            ReadWrite.writeNewBinFile(UsersList);
                            System.out.println("Succes, Please login");
                        }
                    }while (tempboolean);
                    break;
                case 3:
                    if (UsersList.size() == 0){
                        System.out.println("No users to print");
                    }else
                    printlist(UsersList);
                    break;
            }
        }while (login == false);

    }
}
