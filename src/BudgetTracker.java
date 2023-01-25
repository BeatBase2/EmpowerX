import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.Color;

public class BudgetTracker extends JFrame{
    private JPanel contentPane;
    public double allow;
    public int bal;
    public int tempint;
    public int spent;
    private JTable table_1;
    public static String[] columnNames = { "Name","Description","Amount" };
    public static String [][] data;
    /**
     * Launch the application.
     */
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
     * Create the application.
     */
    public BudgetTracker() throws IOException {
        ReadWrite.readNewBinFile(User.UsersList);
        data = User.UsersList.get(LoginPage.getIndex()).getData();
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

        int buttonwidth = (int) size.getWidth()/15;
        int buttonHeight = (int) size.getHeight()/25;

        JButton CancelButton = new JButton("Cancel");
        CancelButton.setBounds(40, 40, buttonwidth, buttonHeight);
        CancelButton.setFont(new Font("Arial", Font.PLAIN, LoginPage.fontSize(CancelButton)));
        CancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User.UsersList.get(LoginPage.getIndex()).getBudget().setAllowance((int)allow);
                User.UsersList.get(LoginPage.getIndex()).getBudget().setBalance(bal);
                User.UsersList.get(LoginPage.getIndex()).setData(data);
                try {
                    ReadWrite.writeNewBinFile(User.UsersList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                contentPane.setVisible(false);
                ICSFinalProject j;
                try {
                    j = new ICSFinalProject();
                    j.setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(CancelButton);

        JLabel spentLabel = new JLabel("Spent:" + spent + ".0");
        spentLabel.setBounds(485, 115, 75, 16);
        contentPane.add(spentLabel);

        JLabel allowancelabel = new JLabel("Allowance:" + allow);
        allowancelabel.setBounds(750, 115, 100, 16);
        contentPane.add(allowancelabel);

        JLabel Instructionlabel = new JLabel("Inset data into fields, press when done:");
        Instructionlabel.setBounds(450, 275, 400, 16);
        contentPane.add(Instructionlabel);

        JButton btnSetBalance = new JButton("Change Values");
        btnSetBalance.setFont(new Font("Arial", Font.PLAIN, LoginPage.fontSize(CancelButton)));
        btnSetBalance.setBounds(148, 40, 146, 36);
        btnSetBalance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentPane.setVisible(false);
                BudgetSetter j;
                j = new BudgetSetter();
                j.setVisible(true);
            }
        });
        contentPane.add(btnSetBalance);
        JProgressBar progressBar_1 = new JProgressBar(0,100);
        progressBar_1.setValue(0);
        progressBar_1.setBounds(491, 83, 320, 20);
        contentPane.add(progressBar_1);
        tempint = (int) (Double.parseDouble(String.valueOf(spent))/100*allow);
        progressBar_1.setValue(tempint);
        progressBar_1.updateUI();

        JLabel ErrorMessage = new JLabel("Name length must be less than 10");
        ErrorMessage.setBounds(450, 255, 400, 16);
        ErrorMessage.setFont(new Font("Arial", Font.PLAIN, LoginPage.fontSize(CancelButton)));
        ErrorMessage.setForeground(Color.RED);
        contentPane.add(ErrorMessage);
        ErrorMessage.setVisible(false);

        JLabel ErrorMessage1 = new JLabel("Description length must be less than 20");
        ErrorMessage1.setBounds(450, 255, 400, 16);
        ErrorMessage1.setFont(new Font("Arial", Font.PLAIN, LoginPage.fontSize(CancelButton)));
        ErrorMessage1.setForeground(Color.RED);
        contentPane.add(ErrorMessage1);
        ErrorMessage1.setVisible(false);

        JLabel ErrorMessage2 = new JLabel("Amount must be positive");
        ErrorMessage2.setBounds(450, 255, 400, 16);
        ErrorMessage2.setFont(new Font("Arial", Font.PLAIN, LoginPage.fontSize(CancelButton)));
        ErrorMessage2.setForeground(Color.RED);
        contentPane.add(ErrorMessage2);
        ErrorMessage2.setVisible(false);

        JButton ExpenseButton = new JButton("Add Expense");
        ExpenseButton.setBounds(688, 271, 117, 29);
        contentPane.add(ExpenseButton);

        Clear();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(485, 299, 320, 313);
        contentPane.add(scrollPane);
        table_1 = new JTable(data,columnNames);
        scrollPane.setViewportView(table_1);
        ExpenseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ErrorMessage.setVisible(false);
                ErrorMessage1.setVisible(false);
                ErrorMessage2.setVisible(false);

                boolean con = true;
                    for (int r = 0;r<table_1.getRowCount();r++) {
                        if ((String.valueOf(table_1.getValueAt(r, 0)).trim()).length() < 11) {
                            data[r][0] = String.valueOf(table_1.getValueAt(r, 0));
                        } else {
                            ErrorMessage.setVisible(true);
                            con = false;
                        }
                    }
                    for (int r = 0;r<table_1.getRowCount();r++) {
                        if ((String.valueOf(table_1.getValueAt(r, 1)).trim()).length() < 21) {
                            data[r][1] = String.valueOf(table_1.getValueAt(r, 1));
                        } else {
                            ErrorMessage.setVisible(false);
                            ErrorMessage1.setVisible(true);
                            con = false;
                        }
                    }
                    for (int r = 0;r<table_1.getRowCount();r++) {
                        System.out.println("r:" +r + " d:" + (String.valueOf(table_1.getValueAt(r, 2)).trim()));
                        if (!(String.valueOf(table_1.getValueAt(r, 2)).trim()).equals("")){
                            if (Integer.parseInt(String.valueOf(table_1.getValueAt(r, 2)).trim()) > 0){
                                data[r][2] = String.valueOf(table_1.getValueAt(r, 2));
                            }else {
                                ErrorMessage.setVisible(false);
                                ErrorMessage1.setVisible(false);
                                ErrorMessage2.setVisible(true);
                                con = false;
                            }
                        }
                    }
                    if (con){
                        spent = sumofspent();
                        User.UsersList.get(LoginPage.getIndex()).getBudget().setSpent(spent);
                        User.UsersList.get(LoginPage.getIndex()).setData(data);
                        spentLabel.setText("Spent:" + spent + ".0");
                        tempint = (int) ((Double.parseDouble(String.valueOf(spent))/allow)*100);
                       // System.out.println("?:" ((Double.parseDouble(String.valueOf(spent)))/allow));
                        progressBar_1.setValue(tempint);
                        progressBar_1.updateUI();
                        try {
                            ReadWrite.writeNewBinFile(User.UsersList);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
            }
        });
    }
    public static int sumofspent(){
        int sum = 0;
        for (int r = 0; r < 10; r++) {
            if (!(String.valueOf(data[r][2])).equals("")){
                sum += Integer.parseInt(data[r][2]);
            }
        }
        return sum;
            }
            public static void Clear(){
                for (int i = 0; i < 10; i++) {
                    data[i][0] = data[i][0].trim();
                    data[i][1] = data[i][1].trim();
                    if (Integer.parseInt(data[i][2].trim()) == 0){
                        data[i][2] = "";
                    }
                }
            }
        }
