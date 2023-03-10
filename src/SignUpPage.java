import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


//Run if you have no accounts made or want to create a new one
public class SignUpPage extends JFrame {
    private JPanel contentPane;
    private JTextField UserTextField;
    private JLabel PFP;
    private JPasswordField PassTextField;
    private JTextField PassTextFieldVis;
    private String password = "";
    private String username = "";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignUpPage frame = new SignUpPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * @throws IOException
     */
    public SignUpPage() throws IOException {
        ReadWrite.readNewBinFile(User.UsersList);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, (int)size.getWidth(), (int)size.getHeight());
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        BufferedImage myPicture = ImageIO.read(new File("LargeDefaultPFP.png"));
        PFP = new JLabel(new ImageIcon(myPicture));
        PFP.setBounds((int)(size.getWidth()/2)-256, 0, 512, 512);
        contentPane.add(PFP);

        int labelwidth = (int) size.getWidth()/10;
        int labelHeight = (int) size.getHeight()/18;

        int buttonwidth = (int) size.getWidth()/15;
        int buttonHeight = (int) size.getHeight()/25;


        JLabel UsernameLabel = new JLabel("Username:");
        UsernameLabel.setBounds(PFP.getX()+labelwidth/2, PFP.getY()+PFP.getHeight(), labelwidth, labelHeight);
        UsernameLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(UsernameLabel)));
        contentPane.add(UsernameLabel);


        UserTextField = new JTextField();
        UserTextField.setBounds(UsernameLabel.getX()+UsernameLabel.getWidth(), UsernameLabel.getY(), labelwidth*2, labelHeight);
        UserTextField.setFont(new Font("Arial", Font.PLAIN, fontSize(UsernameLabel)));
        contentPane.add(UserTextField);
        UserTextField.setColumns(10);

        JLabel PasswordLabel = new JLabel("Password:");
        PasswordLabel.setBounds(PFP.getX()+labelwidth/2, PFP.getY()+PFP.getHeight()+UsernameLabel.getHeight(), labelwidth, labelHeight);
        PasswordLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(PasswordLabel)));
        contentPane.add(PasswordLabel);

        PassTextField = new JPasswordField();
        PassTextField.setBounds(PasswordLabel.getX()+PasswordLabel.getWidth(), PFP.getY()+PFP.getHeight()+UserTextField.getHeight()-6, labelwidth*2, labelHeight);
        PassTextField.setFont(new Font("Arial", Font.PLAIN, fontSize(PasswordLabel)));
        contentPane.add(PassTextField);

        PassTextFieldVis = new JTextField();
        PassTextFieldVis.setBounds(PassTextField.getX(), PassTextField.getY()+3, PassTextField.getWidth(), PassTextField.getHeight()-6);
        PassTextFieldVis.setFont(new Font("Arial", Font.PLAIN, fontSize(UsernameLabel)));
        contentPane.add(PassTextFieldVis);
        PassTextFieldVis.setVisible(false);

        JLabel ErrorMessage = new JLabel("Username already in use");
        ErrorMessage.setBounds(PasswordLabel.getX(), PasswordLabel.getY()+labelHeight/4, labelwidth*9, labelHeight+labelHeight);
        ErrorMessage.setFont(new Font("Arial", Font.PLAIN, fontSize(UsernameLabel)));
        ErrorMessage.setForeground(Color.RED);
        contentPane.add(ErrorMessage);
        ErrorMessage.setVisible(false);

        JLabel ErrorMessage2 = new JLabel("Username must be between 4-20 characters long");
        ErrorMessage2.setBounds(PasswordLabel.getX()-200, PasswordLabel.getY()+labelHeight/4, labelwidth*9, labelHeight+labelHeight);
        ErrorMessage2.setFont(new Font("Arial", Font.PLAIN, fontSize(UsernameLabel)));
        ErrorMessage2.setForeground(Color.RED);
        contentPane.add(ErrorMessage2);
        ErrorMessage2.setVisible(false);

        JLabel ErrorMessage3 = new JLabel("Password must be between 8-20 characters long");
        ErrorMessage3.setBounds(PasswordLabel.getX()-200, PasswordLabel.getY()+labelHeight/4, labelwidth*9, labelHeight+labelHeight);
        ErrorMessage3.setFont(new Font("Arial", Font.PLAIN, fontSize(UsernameLabel)));
        ErrorMessage3.setForeground(Color.RED);
        contentPane.add(ErrorMessage3);
        ErrorMessage3.setVisible(false);



        JButton LoginButton = new JButton("Sign Up");
        LoginButton.setBounds((int)size.getWidth()/2 - (buttonwidth/2), PassTextField.getY() + PassTextField.getHeight()+buttonHeight, buttonwidth, buttonHeight);
        LoginButton.setFont(new Font("Arial", Font.PLAIN, fontSize(LoginButton)));
        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ErrorMessage.setVisible(false);
                ErrorMessage2.setVisible(false);
                ErrorMessage3.setVisible(false);

                username = UserTextField.getText();
                if (PassTextField.isVisible())password = getText(PassTextField.getPassword());
                else password = PassTextFieldVis.getText();
                if(username.length() < 4 || username.length() > 20) {
                    ErrorMessage2.setVisible(true);
                } else if (password.length() < 8 || password.length() > 20) {
                    ErrorMessage2.setVisible(false);
                    ErrorMessage3.setVisible(true);
                } else if (User.Searchlist(username,User.UsersList) == -1){
                    String salt = User.Createsalt();
                    password = PBKDF2PasswdStorage.generatePasswdForStorage(password, salt);
                    String[][] data = new String[10][3];
                    data = fillArray(data);
                    User.UsersList.add(new User(salt,password,username,new User.Budget(),data));
                    try {
                        ReadWrite.writeNewBinFile(User.UsersList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    //Account created
                    contentPane.setVisible(false);
                    LoginPage j;
                    try {
                        j = new LoginPage();
                        j.setVisible(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else ErrorMessage.setVisible(true);//If user found Username already exists
            }
        });
        contentPane.add(LoginButton);

        JButton PasswordVisibleButton;
        BufferedImage eyepic = ImageIO.read(new File("Eye-Icon.png"));
        PasswordVisibleButton = new JButton(new ImageIcon(eyepic));
        PasswordVisibleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(PassTextField.isVisible()) {
                    PassTextField.setVisible(false);
                    PassTextFieldVis.setVisible(true);
                    String x = getText(PassTextField.getPassword());

                    PassTextFieldVis.setText(x);
                }
                else {
                    PassTextFieldVis.setVisible(false);
                    PassTextField.setVisible(true);
                    String x = PassTextFieldVis.getText();
                    PassTextField.setText(x);
                }
            }
        });
        PasswordVisibleButton.setBounds(PassTextField.getX()+PassTextField.getWidth(), PassTextField.getY(), eyepic.getWidth()+20, labelHeight);
        contentPane.add(PasswordVisibleButton);
    }
    //January 17
    public static String getText(char [] x) {
        String z ="";
        for(int i = 0; i < x.length; i++) {
            z = z+x[i];
        }
        for (int r = 0; r <x.length;r++){
            x[r] = ' ';
        }
        return z;
    }

    //January 18
    public int fontSize(JLabel label) {
        Font labelFont = label.getFont();
        String labelText = label.getText();

        int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = label.getWidth();
        double widthRatio = (double)componentWidth / (double)stringWidth;
        int newFontSize = (int)(labelFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();

        // Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);
        // Set the label's font size to the newly determined size.
        return fontSizeToUse;
    }

    //January 19
    public int fontSize(JButton button) {
        Font labelFont = button.getFont();
        String labelText = button.getText();

        int stringWidth = button.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = button.getWidth();
        double widthRatio = (double)componentWidth / (double)stringWidth;
        int newFontSize = (int)(labelFont.getSize() * widthRatio);
        int componentHeight = button.getHeight();

        // Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);
        // Set the label's font size to the newly determined size.
        return fontSizeToUse - ((int)fontSizeToUse/5*2);
    }
    public static String[][] fillArray(String [][] dat){
        for (int r = 0; r < dat.length; r++) {
            for (int c = 0; c < dat[0].length; c++) {
                if (dat[r][c] == null || dat[r][c].equalsIgnoreCase("null")) {
                    dat[r][c] = "";
                }
            }
        }
        return dat;
    }
}
