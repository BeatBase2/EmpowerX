import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ScheduleCalendarGUI extends JFrame implements ActionListener {

    private JTextField taskField;
    private JTextField formatField;
    private JTextField yearField;
    private JTextField monthField;
    private JTextField dayField;
    private JButton addButton;
    private JButton viewButton;
    private ScheduleCalendar scheduleCalendar;

    public ScheduleCalendarGUI() {
        super("Schedule Calendar");
        scheduleCalendar = new ScheduleCalendar();
        setLayout(new FlowLayout());

        add(new JLabel("Task:"));
        taskField = new JTextField(20);
        add(taskField);

        add(new JLabel("Date format: yyyy/mm/dd"));


        add(new JLabel("Year:"));
        yearField = new JTextField(20);
        add(yearField);

        add(new JLabel("Month:"));
        monthField = new JTextField(20);
        add(monthField);

        add(new JLabel("Day:"));
        dayField = new JTextField(20);
        add(dayField);

        addButton = new JButton("Add Deadline");
        addButton.addActionListener(this);
        add(addButton);

        viewButton = new JButton("View Deadline");
        viewButton.addActionListener(this);
        add(viewButton);

        setSize(300, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String task = taskField.getText();
            int year = Integer.parseInt(yearField.getText());
            int month = Integer.parseInt(monthField.getText());
            int day = Integer.parseInt(dayField.getText());
            scheduleCalendar.addDeadline(task, year, month, day);
            JOptionPane.showMessageDialog(this, "Deadline added successfully!");
        } else if (e.getSource() == viewButton) {
            String task = taskField.getText();
            Calendar deadline = scheduleCalendar.getDeadline(task);
            if (deadline != null) {
                JOptionPane.showMessageDialog(this, "Deadline for " + task + ": " + deadline.getTime());
            } else {
                JOptionPane.showMessageDialog(this, "No deadline found for " + task);
            }
        }
    }

    public static void main(String[] args) {
        ScheduleCalendarGUI gui = new ScheduleCalendarGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}