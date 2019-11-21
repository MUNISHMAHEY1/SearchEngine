package se;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sorting.Sort;
import textprocessing.TST;

public class SearchEngine {
	
	public TST<WordMetaData[]> searchDB;
	
	public SearchEngine(boolean rebuildDatabase) {
		String txt_folder_path = System.getProperty("user.dir") + "/database/txt/";
		String tst_folder_path = System.getProperty("user.dir") + "/database/tst/";
		if (rebuildDatabase) {
			System.out.println("Cleaning folders...");
			this.cleanFolder(txt_folder_path);
			this.cleanFolder(tst_folder_path);
			System.out.println("Converting html to txt...");
			new HtmlToTxt().run();
		}
		this.searchDB = new TxtToTST(rebuildDatabase).getTst();
	}
	
	public SearchEngine() {
		this(false);
	}
	
	private void cleanFolder(String folder) {
		File directory = new File(folder);
		File[] files = directory.listFiles();
		for (File file : files) {
			if (!file.delete()) {
				System.out.println("Failed to delete " + file);
			}
		}
	}
	
	
	/**
	 * Returns a list of Page names where the words were found most frequently. 
	 *
	 * @param words 	A list of words.
	 * @return      	A list of pages where the words occurs.
	 */
	public List<String> search(String[] words) {
		List<String> result = new ArrayList<String>();
		List<WordMetaData> finalResult = new ArrayList<WordMetaData>();
		WordMetaData[] finalResultArray;
		WordMetaData[] searchResult;
		boolean found = false;
		
		for (String w : words) {
			if (this.searchDB.contains(w)) {
				searchResult = this.searchDB.get(w);
				for (WordMetaData wmd1 : searchResult) {
					found = false;
					for (WordMetaData wmd2: finalResult) {
						if (wmd1.file.equals(wmd2.file)) {
							found = true;
							wmd2.frequence = wmd2.frequence + wmd1.frequence;
						}
					}
					if (!found) {
						finalResult.add(wmd1);
					}
				}
				
			}
		}
		
		finalResultArray = new WordMetaData[finalResult.size()];
		finalResultArray = finalResult.toArray(finalResultArray);
		Sort.mergeSort(finalResultArray);
		if (finalResultArray.length > 10) {
			finalResultArray = Arrays.copyOfRange(finalResultArray, 0, 9);
		}
		
		for (WordMetaData wmd : finalResultArray) {
			result.add(wmd.file);
		}
		
		return result;
	}

}
