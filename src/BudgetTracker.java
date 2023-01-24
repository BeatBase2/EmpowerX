import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class BudgetTracker extends JFrame{
    public static String temp;
    private JPanel contentPane;
    private JTextField AlllowancetextField;
    public static int min;
    public static int max;
    public static int value;
    public double allow;
    public int bal;
    public int spent;
    private JTable table_1;
    public static String[] columnNames = { "Name","Description","Amount" };
    public static String [][] data = new String[3][50];

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
        ReadArray();

        LoginPage.setIndex(0);
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

        JLabel lblNewLabel_2 = new JLabel("Allowance:" + allow);
        lblNewLabel_2.setBounds(750, 115, 100, 16);
        contentPane.add(lblNewLabel_2);

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
        if (spent > 0) {
            value = User.UsersList.get(LoginPage.getIndex()).getBudget().getBalance() / spent;
        }else value = 0;
        min = 0;
        max = 100;
        JProgressBar progressBar_1 = new JProgressBar(min,max);
        progressBar_1.setValue(0);
        progressBar_1.setBounds(491, 83, 320, 20);
        contentPane.add(progressBar_1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(485, 299, 320, 313);
        contentPane.add(scrollPane);
        table_1 = new JTable(data,columnNames);
        scrollPane.setViewportView(table_1);

        /*
        JButton DeleteButton = new JButton("Delete expense");
        DeleteButton.setBounds(485, 271, 126, 29);
        contentPane.add(DeleteButton);
        DeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
         */
        JButton ExpenseButton = new JButton("Add Expense");
        ExpenseButton.setBounds(688, 271, 117, 29);
        contentPane.add(ExpenseButton);
        ExpenseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (searchfornull() != -1) {
                    data[0][searchfornull()] = String.valueOf(table_1.getValueAt(0,searchfornull()));
                    data[1][searchfornull()] = String.valueOf(table_1.getValueAt(1,searchfornull()));
                    data[1][searchfornull()] = String.valueOf(table_1.getValueAt(2,searchfornull()));
                }
                spent = sumofspent();
                spentLabel.setText("Spent:" + spent + ".0");
                int tempint = 0;
                tempint = (int) (Double.parseDouble(String.valueOf(spent))/100*allow);
                progressBar_1.setValue(tempint);
                progressBar_1.updateUI();
                System.out.println(progressBar_1.getValue());
            }
        });
    }
    public static void ReadArray(){
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < User.UsersList.size(); c++) {
                if (c==0) {
                    data[r][c] = User.UsersList.get(c).getBudget().getName();
                } else if (c==1) {
                    data[r][c] = User.UsersList.get(c).getBudget().getDescription();
                } else if (c==2) {
                    temp = String.valueOf(User.UsersList.get(c).getBudget().getAmmount());
                }
            }
        }
    }
    public static void printArray(){
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < User.UsersList.size(); c++) {
                if (c==0) {
                    System.out.println(data[r][c]);
                } else if (c==1) {
                    System.out.println(data[r][c]);
                } else if (c==2) {
                    System.out.println(data[r][c]);
                }
            }
        }
    }
    public static int sumofspent(){
        int sum = 0;
        for (int r = 0; r < data.length; r++) {
            if (data[r][2] != null){
                sum += Integer.parseInt(data[r][2]);
            }
        }
        return sum;
            }
            public static int searchfornull(){
            int index = -1;
                for (int r = 0; r < data.length; r++) {
                    if (data[r][0] == null){
                        index = r;
                        return index;
                    }
                }
                return index;
            }
        }
