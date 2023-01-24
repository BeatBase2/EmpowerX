import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ReportingGUI extends JFrame implements ActionListener {

    private static final String LOG_DIR = "work_logs"; // directory to store log files
    private JTextField userIdField;
    private JTextField projectField;
    private JTextField workField;
    private JButton submitButton;
    private JButton printButton;

    public ReportingGUI() {
        super("Work Reporting System");
        setLayout(new FlowLayout());

        add(new JLabel("User ID:"));
        userIdField = new JTextField(20);
        add(userIdField);

        add(new JLabel("Project:"));
        projectField = new JTextField(20);
        add(projectField);

        add(new JLabel("Work:"));
        workField = new JTextField(20);
        add(workField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        add(submitButton);

        printButton = new JButton("Print Log");
        printButton.addActionListener(this);
        add(printButton);

        setSize(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String userId = userIdField.getText();
            String project = projectField.getText();
            String work = workField.getText();
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            logWork(userId, project, work, date);
            JOptionPane.showMessageDialog(this, "Work logged successfully!");
        } else if (e.getSource() == printButton) {
            String project = projectField.getText();
            printLog(project);
        }
    }

    private void logWork(String userId, String project, String work, String date) {
        try {
            // create log directory if it doesn't exist
            File logDir = new File(LOG_DIR);
            if (!logDir.exists()) {
                logDir.mkdir();
            }
            // create log file for the project if it doesn't exist
            File logFile = new File(LOG_DIR + File.separator + project + ".txt");
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(logFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(date + " [" + userId + "]: " + work);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error logging work: " + e.getMessage());
        }
    }

    private void printLog(String project) {
        try {
            File logFile = new File(LOG_DIR + File.separator + project + ".txt");
            if (!logFile.exists()) {
                JOptionPane.showMessageDialog(this, "Log file for project " + project + " does not exist.");
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile));
            String line;
            StringBuilder log = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line).append("\n");
            }
            bufferedReader.close();
            JOptionPane.showMessageDialog(this, log.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error printing log: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ReportingGUI gui = new ReportingGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}