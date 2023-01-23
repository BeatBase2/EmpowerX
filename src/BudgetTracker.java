import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class BudgetTracker extends JFrame {
    private JPanel contentPane;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BudgetTracker frame = new BudgetTracker();
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
    public BudgetTracker() throws IOException {
        ReadWrite.readNewBinFile(User.UsersList);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, (int)size.getWidth(), (int)size.getHeight());
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        int labelwidth = (int) size.getWidth()/10;
        int labelHeight = (int) size.getHeight()/18;

        int buttonwidth = (int) size.getWidth()/15;
        int buttonHeight = (int) size.getHeight()/25;
    }

}
