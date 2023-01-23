import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;

public class TaskManager extends JFrame {

	private JPanel contentPane;
	private JTextField TaskNameField;
	int month = 0;
	int day = 0;
	int year = 2023;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskManager frame = new TaskManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TaskManager() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, (int)size.getWidth(), (int)size.getHeight());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Go back to main page
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
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
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel TaskNameLabel = new JLabel("Task Name:");
		TaskNameLabel.setBounds(16, 47, 71, 16);
		TaskNameLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(TaskNameLabel)));
		contentPane.add(TaskNameLabel);
		
		JLabel TaskDeadlineLabel = new JLabel("Task Deadline:");
		TaskDeadlineLabel.setBounds(16, 75, 87, 16);
		TaskDeadlineLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(TaskDeadlineLabel)));
		contentPane.add(TaskDeadlineLabel);
		
		JLabel TaskDescLabel = new JLabel("Task Description:");
		TaskDescLabel.setBounds(16, 103, 107, 16);
		TaskDescLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(TaskDescLabel)));
		contentPane.add(TaskDescLabel);
		
		TaskNameField = new JTextField();
		TaskNameField.setForeground(Color.BLACK);
		TaskNameField.setBounds(128, 41, 302, 26);
		contentPane.add(TaskNameField);
		TaskNameField.setColumns(10);
		
		JLabel ErrorMessage = new JLabel("Incomplete text field");
		ErrorMessage.setForeground(Color.RED);
		ErrorMessage.setBounds(278, 248, 152, 16);
		contentPane.add(ErrorMessage);
		ErrorMessage.setVisible(false);
		
		//Task description empty text field
		JTextArea TaskDescField = new JTextArea();
		TaskDescField.setBounds(135, 102, 295, 134);
		contentPane.add(TaskDescField);

		
		//Button to create task
		JButton CreateTaskButton = new JButton("Create Task");
		CreateTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String taskName = TaskNameField.getText().trim();
				//String taskDeadline = TaskDeadlineField.getText().trim();
				String taskDesc = TaskDescField.getText().trim();
				if(!(taskName.equals("") /*|| taskDeadline.equals("")*/ || taskDesc.equals(""))){
					contentPane.setVisible(false);
					ICSFinalProject j;
					try {
						j = new ICSFinalProject();
						j.setVisible(true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					ErrorMessage.setVisible(true);
				}
			}
		});
		CreateTaskButton.setBounds(128, 243, 117, 29);
		contentPane.add(CreateTaskButton);
		
		JButton ampmButton = new JButton("a.m.");
		ampmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ampmButton.getText().equals("a.m."))
					ampmButton.setText("p.m.");
				else if(ampmButton.getText().equals("p.m."))
					ampmButton.setText("a.m.");
			}
		});
		ampmButton.setBounds(351, 69, 75, 29);
		contentPane.add(ampmButton);
		
		String[] MonthArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		JComboBox monthList = new JComboBox(MonthArray);
		monthList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				month = Integer.parseInt(monthList.getSelectedItem().toString());
			}
		});
		monthList.setFont(new Font("Arial", Font.PLAIN, 13));
		monthList.setBounds(138, 70, 71, 27);
		contentPane.add(monthList);
		
		String[] dayArray = new String[31];
		for(int i = 0; i < dayArray.length; i++) {
			dayArray[i] = Integer.toString(i+1);
		}
		
		
		JComboBox dayComboBox = new JComboBox(dayArray);
		dayComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				day = Integer.parseInt(monthList.getSelectedItem().toString());
			}
		});
		dayComboBox.setFont(new Font("Arial", Font.PLAIN, 13));
		dayComboBox.setBounds(210, 70, 71, 27);
		contentPane.add(dayComboBox);
		
		String[] yearArray = new String[100];
		
		
		
	}
	//January 19
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
	
	
}