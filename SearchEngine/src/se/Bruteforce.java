package se;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import sorting.Sort;

public class Bruteforce {

	public static void main(String[] args) throws IOException {

		String input = "allows";

		File folder = new File(System.getProperty("user.dir") + "/database/txt/");
		File[] listOfFiles = folder.listFiles();

		String[] result;
		List<WordMetaData> finalResult = new ArrayList<WordMetaData>();
		WordMetaData[] sortedResult;
		int wordcount = 0;
		boolean found = false;
		long start = System.currentTimeMillis();

		for (int i = 0; i < listOfFiles.length; i++) {
			org.jsoup.nodes.Document doc = Jsoup.parse(listOfFiles[i], "UTF-8");
			String text = doc.text();
			result = text.split("\\s");

			for (int j = 0; j < result.length; j++) {
				if (result[j].equals(input)) {
					found = true;
					wordcount++;
				}
			}
			if (found) {
				finalResult.add(new WordMetaData(wordcount, listOfFiles[i].getName()));
			}
			found = false;
			wordcount = 0;
		}
		sortedResult = finalResult.toArray(new WordMetaData[finalResult.size()]);
		Sort.quicksort(sortedResult);
		System.out.println("Brute force time search: " + (System.currentTimeMillis() - start) + " miliseconds ");
	}

}
