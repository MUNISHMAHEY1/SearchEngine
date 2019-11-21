package se;

import java.util.List;

import textprocessing.TST;

public class Test {

	public static void main(String[] args) {
		
		String[] input = {"allows"};
		
		long start = System.currentTimeMillis();
		SearchEngine se1 = new SearchEngine(true);
		System.out.println("Build data structure time from scratch: " + (System.currentTimeMillis() - start) + " miliseconds ");
		
		start = System.currentTimeMillis();
		se1 = new SearchEngine(false);
		System.out.println("Load data structure time: " + (System.currentTimeMillis() - start) + " miliseconds ");
		
		List<String> results;
		start = System.currentTimeMillis();
		results = se1.search(input);
		System.out.println("Search time: " + (System.currentTimeMillis() - start) + " miliseconds ");
		/*
		for (String s : results) {
			System.out.println(s);
		}
		*/

	}
}
