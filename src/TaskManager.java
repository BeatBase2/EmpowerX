import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JToolBar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class TaskManager extends JFrame {

	private JPanel contentPane;
	private static JTextArea TaskDescField;
	private static JScrollPane scrollPane;
	private JTextField TaskNameField;
	private int month = getMonth();
	private int day = getDay();
	private int year = getYear();
	private int hour = getHour();
	private int minute = getMinute();
	RandomAccessFile raf;

	
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
	 * @throws IOException 
	 */
	public TaskManager() throws IOException {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, (int)size.getWidth(), (int)size.getHeight());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RandomAccessFile raf = new RandomAccessFile(/*username+*/"File", "rw");
		int length = (int)raf.length()/1156;

		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(6, 6, (int)size.getWidth(),(int) size.getHeight()/15);
		contentPane.add(toolBar);
		
		//Open task management tab
		JButton TaskManagementButton = new JButton("Task Management");
		toolBar.add(TaskManagementButton);

		TaskManagementButton.setBackground(new Color(102, 51, 255));
		TaskManagementButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
			
		//Open calendar
		JButton CalendarButton = new JButton("Calendar");
		CalendarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		CalendarButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
		toolBar.add(CalendarButton);
		
		//Open Budget Tracker
		JButton BudgetButton = new JButton("Budget Tracker");
		BudgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		BudgetButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
		toolBar.add(BudgetButton);
		
		//Open work reporting
		JButton ReportButton = new JButton("Work Reporting");
		ReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ReportButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
		toolBar.add(ReportButton);
		
		JButton TaskListButton = new JButton("Task List");
		TaskListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				try {
					TaskList List = new TaskList();
					List.setVisible(true);
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		TaskListButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
		toolBar.add(TaskListButton);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				try {
					LoginPage login = new LoginPage();
					login.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		LoginButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
		toolBar.add(LoginButton);		
		
		int labelWidth = (int)size.getWidth()/100;//*by # of chars
		int labelHeight = (int)size.getHeight()/30;
				
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
		TaskNameLabel.setBounds((int)((int)size.getWidth()*0.2), (int)(toolBar.getHeight()*1.5)+toolBar.getY(), labelWidth*TaskNameLabel.getText().length(), labelHeight);
		TaskNameLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(TaskNameLabel)));
		contentPane.add(TaskNameLabel);
		
		JLabel TaskDeadlineLabel = new JLabel("Task Deadline:");
		TaskDeadlineLabel.setBounds((int)((int)size.getWidth()*0.2), TaskNameLabel.getY()+(int)(TaskNameLabel.getHeight()*1.5), labelWidth*TaskDeadlineLabel.getText().length(), labelHeight);
		TaskDeadlineLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(TaskNameLabel)));
		contentPane.add(TaskDeadlineLabel);
		
		JLabel TaskDescLabel = new JLabel("Task Description:");
		TaskDescLabel.setBounds((int)((int)size.getWidth()*0.2),TaskDeadlineLabel.getY()+(int)(TaskDeadlineLabel.getHeight()*1.5), labelWidth*TaskDescLabel.getText().length(), labelHeight);
		TaskDescLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(TaskNameLabel)));
		contentPane.add(TaskDescLabel);
		
		int textBoxWidth = (int)size.getWidth()-(TaskNameLabel.getX()*2)-TaskNameLabel.getWidth();

		
		TaskNameField = new JTextField();
		TaskNameField.setForeground(Color.BLACK);
		TaskNameField.setBounds(TaskDescLabel.getX()+(int)(TaskDescLabel.getWidth()), TaskNameLabel.getY(), textBoxWidth, labelHeight);
		contentPane.add(TaskNameField);
		TaskNameField.setColumns(10);
				
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Task description empty text field
		TaskDescField = new JTextArea("");
		TaskDescField.setLineWrap(true);
		TaskDescField.setWrapStyleWord(true);		
		TaskDescField.setBounds(TaskNameField.getX(), TaskDescLabel.getY(), textBoxWidth, (int) size.getHeight()-TaskDescLabel.getY()-((int)size.getHeight()/15*3)-toolBar.getY());
				
		scrollPane.setBounds(TaskDescField.getX(), TaskDescField.getY(), TaskDescField.getWidth(), TaskDescField.getHeight());
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setViewportView(TaskDescField);
		
		contentPane.add(scrollPane);
		
				
		JLabel monthLabel = new JLabel("MM/DD/YYYY:");
		monthLabel.setBounds(TaskDescField.getX(),TaskDeadlineLabel.getY(), labelWidth*monthLabel.getText().length(), labelHeight);
		monthLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(monthLabel)));
		contentPane.add(monthLabel);	
				
		String[] MonthArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		JComboBox<String> monthList = new JComboBox<String>(MonthArray);
		monthList.setFont(new Font("Arial", Font.PLAIN, 13));
		monthList.setBounds(monthLabel.getX()+monthLabel.getWidth(), TaskDeadlineLabel.getY(), (int)size.getWidth()/18, 27);
		monthList.setSelectedIndex(month-1);
		contentPane.add(monthList);
		
		JLabel dayLabel = new JLabel("/");
		dayLabel.setBounds(monthList.getX()+monthList.getWidth(), monthList.getY(), labelWidth*dayLabel.getText().length(), labelHeight);
		dayLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(monthLabel)));
		contentPane.add(dayLabel);
		
		String[] dayArray = new String[31];
		for(int i = 0; i < dayArray.length; i++) {
			dayArray[i] = Integer.toString(i+1);
		}
		
		
		JComboBox<String> dayList = new JComboBox<String>(dayArray);
		dayList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				day = Integer.parseInt(monthList.getSelectedItem().toString());
			}
		});
		dayList.setFont(new Font("Arial", Font.PLAIN, 13));
		dayList.setBounds(dayLabel.getX()+dayLabel.getWidth(), dayLabel.getY(), (int)size.getWidth()/18, 27);
		dayList.setSelectedIndex((day-1));
		contentPane.add(dayList);
		
		JLabel yearLabel = new JLabel("/");
		yearLabel.setBounds(dayList.getX()+dayList.getWidth(), dayList.getY(), labelWidth*yearLabel.getText().length(), labelHeight);
		yearLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(monthLabel)));
		contentPane.add(yearLabel);
		
		//Year comboBox
		int currentYear = getYear();
		String[] yearArray = new String[100];
		for(int i = 0; i < yearArray.length; i++) {
			yearArray[i] = Integer.toString(currentYear);
			currentYear += 1;
		}

		JComboBox<String> yearList = new JComboBox<String>(yearArray);
		yearList.setFont(new Font("Arial", Font.PLAIN, 13));
		yearList.setBounds(yearLabel.getX() + yearLabel.getWidth(), dayList.getY(), (int)size.getWidth()/15, 27);
		contentPane.add(yearList);
		
		//hour and minute label
		JLabel timeLabel = new JLabel("Time:");
		timeLabel.setBounds(yearList.getX()+yearList.getWidth(), yearList.getY(), labelWidth*timeLabel.getText().length(), labelHeight);
		timeLabel.setFont(new Font("Arial", Font.PLAIN, fontSize(monthLabel)));
		contentPane.add(timeLabel);

		//Hour comboBox
		String[] hourArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		JComboBox<String> hourList = new JComboBox<String>(hourArray);
		hourList.setFont(new Font("Arial", Font.PLAIN, 13));
		hourList.setBounds(timeLabel.getX() + timeLabel.getWidth(), timeLabel.getY(), (int)size.getWidth()/18, 27);
		if(hour > 12) hour -= 12;
		if(hour == 0) hour = 12;
		hourList.setSelectedIndex(hour-1);
		contentPane.add(hourList);
		
		//Minute comboBox
		String[] minuteArray = new String[59];
		for(int i = 0; i < minuteArray.length; i++) 
			minuteArray[i] = Integer.toString((i+1));
		
		JComboBox<String> minuteList = new JComboBox<String>(minuteArray);
		minuteList.setFont(new Font("Arial", Font.PLAIN, 13));
		minuteList.setBounds(hourList.getX() + hourList.getWidth(), hourList.getY(), (int)size.getWidth()/18, 27);
		minuteList.setSelectedIndex(minute-1);
		contentPane.add(minuteList);
		
		JButton ampmButton = new JButton();
		if(getHour() >= 12) ampmButton.setText("p.m.");
		if(getHour() < 12) ampmButton.setText("a.m.");
		ampmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ampmButton.getText().equals("a.m."))
					ampmButton.setText("p.m.");
				else if(ampmButton.getText().equals("p.m."))
					ampmButton.setText("a.m.");
			}
		});
		ampmButton.setBounds(minuteList.getX()+minuteList.getWidth(), minuteList.getY(), (int)size.getWidth()/18, 29);
		contentPane.add(ampmButton);

		//Button to create task
		JButton CreateTaskButton = new JButton("Create Task");
		CreateTaskButton.setBounds(TaskDescField.getX(), TaskDescField.getY()+TaskDescField.getHeight(), 117, 29);
		contentPane.add(CreateTaskButton);
		
		JLabel ErrorMessage = new JLabel("Incomplete text field");
		ErrorMessage.setForeground(Color.RED);
		ErrorMessage.setBounds(CreateTaskButton.getX()+CreateTaskButton.getWidth(), CreateTaskButton.getY(), labelWidth*ErrorMessage.getText().length(), labelHeight);
		contentPane.add(ErrorMessage);
		ErrorMessage.setVisible(false);
		
		JLabel ErrorMessage2 = new JLabel("Task Name Has a maximum of 50 characters");
		ErrorMessage2.setForeground(Color.RED);
		ErrorMessage2.setBounds(CreateTaskButton.getX()+CreateTaskButton.getWidth(), CreateTaskButton.getY(), labelWidth*ErrorMessage2.getText().length(), labelHeight);
		contentPane.add(ErrorMessage2);
		ErrorMessage2.setVisible(false);

		//create task button action listener
		CreateTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				day = Integer.parseInt(dayList.getSelectedItem().toString());
				String taskName = TaskNameField.getText().trim();
				String taskDesc = TaskDescField.getText().trim();
				if((!(taskName.equals("")|| taskDesc.equals(""))) && taskName.length()<=50){
					contentPane.setVisible(false);
					Task newTask = new Task();
					newTask.setName(taskName);
					newTask.setDeadline(month, day, year, hour, minute);
					newTask.setDescription(taskDesc);					
					try {
						newTask.writeRec(raf, length);
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					
					ICSFinalProject j;
					try {
						j = new ICSFinalProject();
						j.setVisible(true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if ((taskName.equals("")|| taskDesc.equals(""))) {
					ErrorMessage2.setVisible(false);
					ErrorMessage.setVisible(true);
				}
				if(taskName.length() > 50) {
					ErrorMessage.setVisible(false);
					ErrorMessage2.setVisible(true);
					System.out.println(taskName.length());
				}
			}
		});
		

		
		//Month list action listener
		monthList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				month = Integer.parseInt(monthList.getSelectedItem().toString());
				/** If the month is February **/
				if(month == 2) {
					int remains;
					if(year % 4 == 0)remains = dayList.getItemCount()-29; //if it is a Leap Year
					else remains = dayList.getItemCount()-28;
					for(int i = 0; i < remains; i++)
						dayList.removeItemAt(dayList.getItemCount()-1);
				}
				/** If the month has 31 days **/
				else if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
					int remains = dayList.getItemCount()-31;
					remains *= -1;
					for(int i = 0; i < remains; i++)
						dayList.addItem(Integer.toString(dayList.getItemCount()+1));
				}
				/** If the month has 30 days **/
				else if(month == 4 || month == 6 || month == 9 || month == 11) {
					int remains = dayList.getItemCount()-30;
					remains *= -1;
					for(int i = 0; i < remains; i++)
						dayList.addItem(Integer.toString(dayList.getItemCount()+1));
				}
			}
		});

		//Year list action listener
		yearList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				year = Integer.parseInt(yearList.getSelectedItem().toString());
				/** If it is a Leap Year **/
				if(year % 4 == 0 && month == 2) {
					int remains = dayList.getItemCount()-29;
					if(remains > 0) {
						for(int i = 0; i < remains; i++)
							dayList.removeItemAt(dayList.getItemCount()-1);
					}
					else if(remains < 0) {
						remains *= -1;
						for(int i = 0; i < remains; i++)
							dayList.addItem(Integer.toString(dayList.getItemCount()+1));
					}
				}
				/** If it isn't a Leap Year **/
				if(year % 4 != 0 && month == 2) {
					int remains = dayList.getItemCount()-28;
					for(int i = 0; i < remains; i++)
						dayList.removeItemAt(dayList.getItemCount()-1);
				}
			}
		});
		
		//Hour list action listener
		hourList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hour = Integer.parseInt(hourList.getSelectedItem().toString());
			}
		});
		
		//Hour list action listener
		minuteList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				minute = Integer.parseInt(hourList.getSelectedItem().toString());
			}
		});
		
		//System.out.println(getDay());
	}
	//January 19
	public int getYear() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
		LocalDateTime now = LocalDateTime.now();
		return Integer.parseInt(dtf.format(now).toString());
	}
	//January 22
	public int getMonth() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM");
		LocalDateTime now = LocalDateTime.now();
		return Integer.parseInt(dtf.format(now).toString());
	}
	//January 22
	public int getDay() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
		LocalDateTime now = LocalDateTime.now();
		return Integer.parseInt(dtf.format(now).toString());
	}
		
	//January 23
	public int getHour() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
		LocalDateTime now = LocalDateTime.now();
		return Integer.parseInt(dtf.format(now).toString());
	}
	
	//January 22
	public int getMinute() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm");
		LocalDateTime now = LocalDateTime.now();
		return Integer.parseInt(dtf.format(now).toString());
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
	
	//January 19
	public int fontSize(JToolBar bar, JButton button) {
		 Font labelFont = bar.getFont();
			String labelText = button.getText();

			int stringWidth = bar.getFontMetrics(labelFont).stringWidth(labelText);
			int componentWidth = bar.getWidth();
			double widthRatio = (double)componentWidth / (double)stringWidth;
			int newFontSize = (int)(labelFont.getSize() * widthRatio);
			int componentHeight = bar.getHeight();
						
			// Pick a new font size so it will not be larger than the height of label.
			int fontSizeToUse = Math.min(newFontSize, componentHeight);
			// Set the label's font size to the newly determined size.
			return fontSizeToUse - ((int)fontSizeToUse/5*2);
	}
}
