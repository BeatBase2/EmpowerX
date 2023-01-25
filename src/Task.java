import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

	private String name; //max 50 chars (100)
	private String deadline; //28 chars (56)
	private String description;//500 chars (1000)
	private int recLen = 1156;
	
	public Task() {
		name = "";
		deadline = null;
		description = "";
	}
	
	public Task(String n, String d, String D) {
		name = n;
		deadline = d;
		description = D;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName(){
		return name;
	}
	
	public void setDeadline(int month, int day, int year, int hour, int minute) {
		String monthpad = Integer.toString(month);
		String daypad = Integer.toString(day);
		String hourpad = Integer.toString(hour);
		String minutepad = Integer.toString(minute);
		
		if(month < 10) monthpad = "0" + monthpad;
		if(day < 10) daypad = "0" + daypad;
		if(hour < 10) hourpad = "0" + hourpad;
		if(minute < 10) hourpad = "0" + minutepad;
		
		deadline = monthpad + "-" + daypad + "-" + year + ":" + hourpad + ":" + minutepad;
	}
	
	public void setDeadline(String d) {
		deadline = d;
	}

	
	public String getDeadline(){
		return deadline;
	}
	
	public void setDescription(String d) {
		description = d;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void writeRec(RandomAccessFile raf, int recNum) throws IOException {
		raf.seek(recLen * recNum);
		int strLength = name.length(), padLength = 0;
		if(strLength > 50)
			strLength = 50;
		else
			padLength = 50 - strLength;
		
		for(int i = 0; i < strLength; i++)
			raf.writeChar(name.charAt(i));
		
		if(padLength > 0) {
			for(int i = 0; i < padLength; i++)
				raf.writeChar(' ');
		}
		
		//Repeat for deadline
		strLength = deadline.toString().length();
		padLength = 0;
		if(strLength > 28)
			strLength = 28;
		else
			padLength = 28 - strLength;
		
		for(int i = 0; i < strLength; i++)
			raf.writeChar(deadline.toString().charAt(i));
		
		if(padLength > 0) {
			for(int i = 0; i < padLength; i++)
				raf.writeChar(' ');
		}
		
		//Repeat for description
		strLength = description.length();
		padLength = 0;
		
		if(strLength > 500)
			strLength = 500;
		else
			padLength = 500 - strLength;
		
		for(int i = 0; i < strLength; i++)
			raf.writeChar(description.charAt(i));
		
		if(padLength > 0) {
			for(int i = 0; i < padLength; i++)
				raf.writeChar(' ');
		}
	}

	
	public void readRec(RandomAccessFile raf, int recNum) throws IOException {
		raf.seek(recLen * recNum);
		String temp = "";
		for(int i = 0; i < 50; i++)
			temp = temp + raf.readChar();
		name = temp.trim();
		
		temp = "";
		for(int i = 0; i < 28; i++)
			temp = temp + raf.readChar();
		deadline = temp.trim();
		
		temp = "";
		for(int i = 0; i < 500; i++)
			temp = temp + raf.readChar();
		description = temp.trim();
	}
}
