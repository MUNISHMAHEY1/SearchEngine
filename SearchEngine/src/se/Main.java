package se;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.EditDistance;

public class Main {

	public static void main(String[] args) throws IOException {

		SearchEngine se = new SearchEngine(false);
		List<String> results;

		String input = "";
		while (input != "exit") {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter a word to be searched:");
			input = br.readLine();
			input = input.toLowerCase();

			/** Splitting the input */
			String[] inputdiv = input.split("\\s");
			
			results = se.search(inputdiv);
			System.out.println("");
			System.out.println("Results:");
			for (String page : results) {
				System.out.println(page);
			}
			
			System.out.println("");
			System.out.println("Suggestions:");
			EditDistance ed = new EditDistance();
			List<String> matchedWords = new ArrayList<String>();

			for (int i = 0; i < inputdiv.length; i++) {
				matchedWords = ed.findingLeastEditDistance(inputdiv[i]);
				for (int j=0; j<matchedWords.size() && j<10; j++) {
					System.out.println(matchedWords.get(j));
				}
			}
		}
	}

}
