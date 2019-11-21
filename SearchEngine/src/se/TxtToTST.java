package se;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import sorting.Sort;
import textprocessing.TST;


public class TxtToTST {

	String txt_folder_path = System.getProperty("user.dir") + "/database/txt/";

	private StringTokenizer tkob;
	// private TST<WordMetaData[]> tst = new TST<WordMetaData[]>();
	private TSTSerializable<WordMetaData[]> tst = new TSTSerializable<WordMetaData[]>();
	private int pageResults = 11;

	public TxtToTST() {
		this(true);
	}

	public TxtToTST(boolean rebuildTst) {
		if (rebuildTst) {
			// Read all .txt files and build TST
			System.out.println("Building TST...");
			this.buildTST();
		} else {
			// Load TST from .dat file. Fast
			System.out.println("Loading TST...");
			this.loadTST();
		}
	}

	public TST<WordMetaData[]> getTst() {
		return tst;
	}

	private WordMetaData[] getEmptyList(File[] listOfFiles) {
		WordMetaData[] emptyWMDArray = new WordMetaData[pageResults];
		for (int i = 0; i < emptyWMDArray.length; i++) {
			WordMetaData wmd = new WordMetaData();
			emptyWMDArray[i] = wmd;
		}
		return emptyWMDArray;
	}

	private boolean validToken(String s) {
		if (s.matches("[0-9](.*)")) {
			return false;
		}
		if (s.length() <= 3) {
			return false;
		}
		return true;
	}

	public void buildTST() {

		WordMetaData[] wmdArray;

		File folder = new File(txt_folder_path);
		File[] listOfFiles = folder.listFiles();

		String content = "";
		String key;

		for (File f : listOfFiles) {
			if (f.isFile()) {
				content = readFile(f.getAbsolutePath());
				content = removeSpecialChars(content);
				content = content.toLowerCase();

				// Get tokens using delimiters " .;,\n\r\t()"
				tkob = new StringTokenizer(content, " .;,\n\r\t()\"\'");
				// Build a TST structure with the frequency of each word
				while (tkob.hasMoreTokens()) {
					key = tkob.nextToken();
					if (validToken(key)) {
						if (this.tst.contains(key)) {
							wmdArray = this.tst.get(key);
							if (wmdArray[10].file.equals(f.getName())) {
								wmdArray[10].frequence++;
							} else {
								Sort.mergeSort(wmdArray);
								wmdArray[10].file = f.getName();
								wmdArray[10].frequence = 1;
							}
							this.tst.put(key, wmdArray);
						} else {
							// Create an empty array of WordMetaData
							wmdArray = getEmptyList(listOfFiles);
							wmdArray[10].file = f.getName();
							wmdArray[10].frequence = 1;
							this.tst.put(key, wmdArray);
						}
					}
				}
			}
		} // end for

		// Open dictionary file
		Dictionary dict = new Dictionary();

		// Order the array for each key and add the key in the dictionary
		for (String word : this.tst.keys()) {
			dict.writeln(word);
			wmdArray = this.tst.get(word);
			Sort.mergeSort(wmdArray);
			this.tst.put(word, wmdArray);
		}

		// Close dictionary file
		dict.close();

		this.saveTST();
	}

	private String removeSpecialChars(String content) {
		return content.replaceAll("[^a-zA-Z0-9]", " ");
	}

	public void saveTST() {
		
		WordMetaData[] wmdArray;
		String filepath;

		for (String k : this.tst.keys()) {
			try {
				filepath = System.getProperty("user.dir") + "/database/tst/" + k;
				wmdArray = this.tst.get(k);
				File fout = new File(filepath);
				FileOutputStream fos = new FileOutputStream(fout);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				for (int i  = 0; i < wmdArray.length; i++) {
					bw.write(wmdArray[i].frequence + ";" + wmdArray[i].file);
					bw.newLine();
				}
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	
	public void loadTST() {
		
		File folder = new File(System.getProperty("user.dir") + "/database/tst/");
		File[] listOfFiles = folder.listFiles();
		for (File f : listOfFiles) {
			try {
				InputStream is = new FileInputStream(f.getAbsolutePath());
		        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		        
		        
		        WordMetaData[] wmdArray = new WordMetaData[pageResults];
		        for(int i=0; i<wmdArray.length; i++) {
		        	String line = buf.readLine();
		        	String[] ls = line.split(";");
		        	if (ls.length >= 2) {
		        		wmdArray[i] =new WordMetaData(Integer.parseInt(ls[0]), ls[1]);
		        	} else {
		        		wmdArray[i] =new WordMetaData(Integer.parseInt(ls[0]), "");
		        	}
		        }
				this.tst.put(f.getName(), wmdArray);
				buf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private String readFile(String path) {

		String content = "";

		try {
			content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		} catch (NoSuchFileException e) {
			try {

				content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_16);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
}
