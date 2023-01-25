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
    public static int min;
    public static int max;
    public static int value;
    public double allow;
    public int bal;
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
        data = new String[50][3];
        ReadWrite.readNewBinFile(User.UsersList);
        ReadArray();
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
                User.UsersList.get(LoginPage.getIndex()).getBudget().setAllowance((int)allow);
                User.UsersList.get(LoginPage.getIndex()).getBudget().setBalance(bal);
                User.UsersList.get(LoginPage.getIndex()).getBudget().setAmmount(spent);
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

        JLabel Instructionlabel = new JLabel("Inset data into fields, pess when done:");
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
        if (spent > 0) {
            value = User.UsersList.get(LoginPage.getIndex()).getBudget().getBalance() / spent;
        }else value = 0;
        min = 0;
        max = 100;
        JProgressBar progressBar_1 = new JProgressBar(min,max);
        progressBar_1.setValue(0);
        progressBar_1.setBounds(491, 83, 320, 20);
        contentPane.add(progressBar_1);

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
                            User.UsersList.get(LoginPage.getIndex()).getBudget().setName(String.valueOf(table_1.getValueAt(r, 0)));
                        } else {
                            System.out.println("b:1");
                            ErrorMessage.setVisible(true);
                            con = false;
                        }
                    }
                    for (int r = 0;r<table_1.getRowCount();r++) {
                        System.out.println(r+":"+(table_1.getValueAt(r, 1)));
                        if ((String.valueOf(table_1.getValueAt(r, 1)).trim()).length() < 21) {
                            data[r][1] = String.valueOf(table_1.getValueAt(r, 1));
                            User.UsersList.get(LoginPage.getIndex()).getBudget().setDescription(String.valueOf(table_1.getValueAt(r, 1)));
                        } else {
                            System.out.println("b:2");
                            ErrorMessage.setVisible(false);
                            ErrorMessage1.setVisible(true);
                            con = false;
                        }
                    }
                    for (int r = 0;r<table_1.getRowCount();r++) {
                        if ((String.valueOf(table_1.getValueAt(r, 2)).trim()) != ""){
                            if (Integer.parseInt(String.valueOf(table_1.getValueAt(r, 2)).trim()) > 0){
                                data[2][r] = String.valueOf(table_1.getValueAt(r, 2));
                            }else {
                                System.out.println("b:3:" + r);
                                ErrorMessage.setVisible(false);
                                ErrorMessage1.setVisible(false);
                                ErrorMessage2.setVisible(true);
                                con = false;
                            }
                        }
                    }
                    if (con){
                        spent = sumofspent();
                        User.UsersList.get(LoginPage.getIndex()).getBudget().setAmmount(spent);
                        spentLabel.setText("Spent:" + spent + ".0");
                        int tempint = 0;
                        tempint = (int) (Double.parseDouble(String.valueOf(spent))/100*allow);
                        System.out.println(tempint);
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
    public static void ReadArray(){
        for (int r = 0; r < User.UsersList.size(); r++) {
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
        for (int r = 0; r < 50; r++) {
            for (int c = 0; c < 3; c++) {
                if (data[r][c] == null || data[r][c].equalsIgnoreCase("null")) {
                    data[r][c] = "";
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
        for (int r = 0; r < 50; r++) {
            if ((String.valueOf(data[r][2])) != ""){
                sum += Integer.parseInt(data[r][2]);
            }
        }
        return sum;
            }
            public static int searchforempty(){
            int index = -1;
                for (int r = 0; r < data.length; r++) {
                    if (data[r][0] == ""){
                        index = r;
                        return index;
                    }
                }
                return index;
            }
        }
