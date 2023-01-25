import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class BudgetSetter extends JFrame{

    private JPanel contentPane;
    private JTextField BalancetextField;
    public JLabel Allowancelbl;
    public double allow;
    public int bal;
    public int spent;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BudgetSetter frame = new BudgetSetter();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public BudgetSetter() {
        bal = User.UsersList.get(LoginPage.getIndex()).getBudget().getBalance();
        allow = Double.parseDouble(String.valueOf(User.UsersList.get(LoginPage.getIndex()).getBudget().getAllowance()));
        spent = User.UsersList.get(LoginPage.getIndex()).getBudget().getSpent();
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

        JButton CancelButton = new JButton("Cancel");
        CancelButton.setBounds(40, 40, buttonwidth, buttonHeight);
        CancelButton.setFont(new Font("Arial", Font.PLAIN, LoginPage.fontSize(CancelButton)));
        CancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ReadWrite.writeNewBinFile(User.UsersList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                contentPane.setVisible(false);
                BudgetTracker j;
                try {
                    j = new BudgetTracker();
                    j.setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(CancelButton);

        JButton btnSubmitBalance = new JButton("Submit Balance");
        btnSubmitBalance.setFont(new Font("Arial", Font.PLAIN, 19));
        btnSubmitBalance.setBounds(624, 80, 190, 36);

        JLabel lblNewLabel = new JLabel("Set Allowance:");
        lblNewLabel.setBounds(672, 262, 96, 37);
        contentPane.add(lblNewLabel);
        lblNewLabel.setVisible(false);

        Allowancelbl = new JLabel("Allowance:" + allow);
        Allowancelbl.setBounds(479, 336, 96, 16);
        contentPane.add(Allowancelbl);
        Allowancelbl.setVisible(false);


        JLabel Balancelbl = new JLabel("Balance: " + bal);
        Balancelbl.setBounds(792, 336, 81, 16);
        contentPane.add(Balancelbl);
        Balancelbl.setVisible(false);

        JLabel lblNewLabel_2_1 = new JLabel("Set Balance:");
        lblNewLabel_2_1.setBounds(642, 51, 96, 16);
        contentPane.add(lblNewLabel_2_1);

        JSlider slider = new JSlider();
        slider.setValue(0);
        slider.setBackground(Color.BLACK);
        slider.setBounds(573, 299, 300, 25);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                allow = bal*((Double.parseDouble(String.valueOf(slider.getValue()))/100));
                Allowancelbl.setText("Allowance:" + allow);
            }
        });
        contentPane.add(slider);
        slider.setVisible(false);


        BalancetextField = new JTextField();
        BalancetextField.setColumns(10);
        BalancetextField.setBounds(719, 46, 61, 26);
        contentPane.add(BalancetextField);

        btnSubmitBalance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bal = Integer.parseInt(BalancetextField.getText());
                Balancelbl.setText("Balance: " + bal);
                allow = 0;
                Balancelbl.setVisible(true);
                Allowancelbl.setVisible(true);
                lblNewLabel.setVisible(true);
                slider.setVisible(true);
            }
        });
        contentPane.add(btnSubmitBalance);

        JButton btnSubmitAllowance = new JButton("Submit Allowance");
        btnSubmitAllowance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User.UsersList.get(LoginPage.getIndex()).getBudget().setAllowance((int)allow);
                User.UsersList.get(LoginPage.getIndex()).getBudget().setBalance(bal);
                try {
                    ReadWrite.writeNewBinFile(User.UsersList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                contentPane.setVisible(false);
                BudgetTracker j;
                try {
                    j = new BudgetTracker();
                    j.setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnSubmitAllowance.setFont(new Font("Arial", Font.PLAIN, 19));
        btnSubmitAllowance.setBounds(624, 359, 190, 36);
        contentPane.add(btnSubmitAllowance);
    }
}

