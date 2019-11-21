package se;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.*;

public class HtmlToTxt {

	public void run() {

		/*
		 * System.out.println("=====================================");
		 * System.out.println("          HTML to TXT                ");
		 * System.out.println("=====================================");
		 */

		org.jsoup.nodes.Document doc;
		String textContent;

		String html_folder_path = System.getProperty("user.dir") + "/database/html/";
		String txt_folder_path = System.getProperty("user.dir") + "/database/txt/";
		String output_filename;

		File dir = new File(html_folder_path);
		File[] files = dir.listFiles();
		PrintWriter writer;

		for (File f : files) {
			if (f.isFile()) {
				try {
					doc = Jsoup.parse(f, "UTF-8");
					textContent = doc.text();
					output_filename = txt_folder_path + f.getName() + ".txt";
					//System.out.println("Writing " + output_filename);
					writer = new PrintWriter(output_filename, "UTF-8");
					writer.write(textContent);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	

}
