import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class EmpowerX {
    private static void printlist(ArrayList<User> UsersList){
        for (int i = 0;i< UsersList.size();i++){
            String user = UsersList.get(i).getUsername();
            String pass = UsersList.get(i).getPassword();
            System.out.println("Username: " + user + "\tPassword: " + pass);
        }
    }
    private static boolean Searchlist(String name, ArrayList<User> UsersList){
        for (int i = 0;i< UsersList.size();i++){
            String next = UsersList.get(i).getUsername();

            if (next.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
    private static boolean Searchlist(String name,String word, ArrayList<User> UsersList){
        for (int i = 0;i< UsersList.size();i++){
            String user = UsersList.get(i).getUsername();
            String pass = UsersList.get(i).getPassword();
            if (user.equalsIgnoreCase(name)&&pass.equals(word)){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        boolean tempboolean;
        ArrayList<User> UsersList = new ArrayList<User>();
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
                        }
                    }while (tempboolean);
                    break;
                case 2:
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
                            UsersList.add(new User(pass,username));
                            System.out.println("Succes, Please login");
                        }
                    }while (tempboolean);
                    break;
                case 3:
                    printlist(UsersList);
                    break;
            }
        }while (login == false);

    }
}
