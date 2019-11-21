package se;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Dictionary {
	
	private String dict_file = System.getProperty("user.dir") + "/database/dictionary/dictionary.txt";
	private PrintWriter writer;

	
	public Dictionary() {
		try {
			writer = new PrintWriter(dict_file, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeln(String s) {
		writer.println(s);
	}
	
	@SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
	     try {
	         this.close();        // close open files
	     } finally {
	         super.finalize();
	     }
	 }
	
	public void close() {
		writer.close();
	}
	

}
