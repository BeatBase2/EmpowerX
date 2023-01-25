import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;

public class TaskList extends JFrame {

	private JScrollPane scrollPane;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskList frame = new TaskList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public TaskList() throws IOException, ParseException {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		RandomAccessFile raf = new RandomAccessFile(/*username +*/"File", "rw");
		int length = (int)raf.length()/1156;		
		int labelWidth = (int)size.getWidth()/100;//*by # of chars
		int labelHeight = (int)size.getHeight()/30;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, (int)size.getWidth(), (int)size.getHeight());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		/***/
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(6, 6, (int)size.getWidth(),(int) size.getHeight()/15);
		contentPane.add(toolBar);
		
		//Open task management tab
		JButton TaskManagementButton = new JButton("Task Management");
		toolBar.add(TaskManagementButton);
		TaskManagementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				TaskManager manager;
				try {
					manager = new TaskManager();
					manager.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
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

		/***/
		
		Task[] taskList = new Task[length];
		
		
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
		btnNewButton.setBounds(toolBar.getX(), toolBar.getY()+toolBar.getHeight(), 117, 29);
		contentPane.add(btnNewButton);

		JLabel title = new JLabel("Tasks ("+length+")");
		title.setBounds(btnNewButton.getX()+btnNewButton.getWidth(), toolBar.getY()+toolBar.getHeight(), labelWidth*(title.getText().length())*2,(int)(labelHeight));
		title.setFont(new Font("Arial", Font.PLAIN, fontSize(title)));
		contentPane.add(title);

						
		int xpos = title.getX();
		int ypos = title.getY()+title.getHeight()+title.getHeight();

		for(int i = 0; i < taskList.length; i++) {
			if(i<12) {
				taskList[i] = new Task();
				taskList[i].readRec(raf, i);
				
				Date deadline = new Date();
				String pattern = "MM-dd-yyyy:HH:mm";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				deadline = simpleDateFormat.parse(taskList[i].getDeadline());
			
				
				//Graphics			
				JCheckBox TaskCheckBox = new JCheckBox(taskList[i].getName());
				TaskCheckBox.setBounds(xpos, ypos, labelWidth*(TaskCheckBox.getText().length()*2), labelHeight);
				TaskCheckBox.setFont(new Font("Arial", Font.PLAIN, fontSize(TaskCheckBox)));
				contentPane.add(TaskCheckBox);
				
				JLabel TaskDeadlineLabel = new JLabel(deadline.toString());
				TaskDeadlineLabel.setBounds(xpos, TaskCheckBox.getY()+TaskCheckBox.getHeight(), labelWidth*(deadline.toString().length()), labelHeight);
				TaskCheckBox.setFont(new Font("Arial", Font.PLAIN, fontSize(TaskDeadlineLabel)));
				contentPane.add(TaskDeadlineLabel);
	
				scrollPane = new JScrollPane();
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);			
				
				JTextPane LabelTextPane = new JTextPane();
				LabelTextPane.setText(taskList[i].getDescription());
				LabelTextPane.setBounds(xpos, TaskDeadlineLabel.getY()+TaskDeadlineLabel.getHeight(), 362, 85);
				LabelTextPane.setFont(new Font("Arial", Font.PLAIN, fontSize(TaskDeadlineLabel)/2));
	
				scrollPane.setBounds(LabelTextPane.getX(), LabelTextPane.getY(), LabelTextPane.getWidth(), LabelTextPane.getHeight());
				scrollPane.getViewport().setBackground(Color.WHITE);
				scrollPane.setViewportView(LabelTextPane);
				
				contentPane.add(scrollPane);
	
				
				
				ypos = LabelTextPane.getY()+LabelTextPane.getHeight();
				if(i==3||i==7) {
					xpos = (int)(TaskCheckBox.getX()+LabelTextPane.getWidth() *1.1);
					ypos = title.getY()+title.getHeight()+title.getHeight();
				}
			}
		}
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
	
	public int fontSize(JCheckBox box) {
		 Font labelFont = box.getFont();
			String labelText = box.getText();

			int stringWidth = box.getFontMetrics(labelFont).stringWidth(labelText);
			int componentWidth = box.getWidth();
			double widthRatio = (double)componentWidth / (double)stringWidth;
			int newFontSize = (int)(labelFont.getSize() * widthRatio);
			int componentHeight = box.getHeight();
						
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
