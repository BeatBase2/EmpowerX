import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JLabel;


//Run this file if you have made an account
//Otherwise run sign up

public class ICSFinalProject {
	private JFrame frame;
	TaskManager manager = new TaskManager();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ICSFinalProject window = new ICSFinalProject();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public ICSFinalProject() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
		
	private void initialize() throws IOException {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.setBounds(0, 0, (int)size.getWidth(), (int)size.getHeight());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(6, 6, frame.getWidth(), frame.getHeight()/15);
		frame.getContentPane().add(toolBar);

		//Open task management tab
		JButton TaskManagementButton = new JButton("Task Management");
		toolBar.add(TaskManagementButton);
		TaskManagementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				manager.setVisible(true);
			}
		});
		TaskManagementButton.setBackground(new Color(102, 51, 255));
		TaskManagementButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
			
		//Open calendar
		JButton CalendarButton = new JButton("Calendar");
		CalendarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				ScheduleCalendarGUI cal = new ScheduleCalendarGUI();
				cal.setVisible(true);
			}
		});
		
		CalendarButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
		toolBar.add(CalendarButton);
		
		//Open Budget Tracker
		JButton BudgetButton = new JButton("Budget Tracker");
		BudgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				BudgetTracker budget = null;
				try {
					budget = new BudgetTracker();
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
				budget.setVisible(true);
			}
		});
		BudgetButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
		toolBar.add(BudgetButton);
		
		//Open work reporting
		JButton ReportButton = new JButton("Work Reporting");
		ReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					ReportingGUI report = new ReportingGUI();
					report.setVisible(true);
			}
		});
		ReportButton.setFont(new Font("Arial", Font.PLAIN, fontSize(toolBar, TaskManagementButton)));
		toolBar.add(ReportButton);
		
		JButton TaskListButton = new JButton("Task List");
		TaskListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
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
		//Openlogin
		JButton LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
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
		
		JLabel EmpowerXImage;
		
		BufferedImage myPicture = ImageIO.read(new File("EmpowerXLogo.png"));
		EmpowerXImage = new JLabel(new ImageIcon(myPicture));
		EmpowerXImage.setBounds(16, 59, 831, 170);
		frame.getContentPane().add(EmpowerXImage);
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
	
	public int fontSize(JLabel label) {
		 Font labelFont = label.getFont();
			String labelText = label.getText();

			int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
			int componentWidth = label.getWidth();
			System.out.println("Font metrics:" + label.getFontMetrics(labelFont).stringWidth(labelText));
			double widthRatio = (double)componentWidth / (double)stringWidth;
			int newFontSize = (int)(labelFont.getSize() * widthRatio);
			int componentHeight = label.getHeight();
			
			
			// Pick a new font size so it will not be larger than the height of label.
			int fontSizeToUse = Math.min(newFontSize, componentHeight);

			// Set the label's font size to the newly determined size.
			label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
			return fontSizeToUse;
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
