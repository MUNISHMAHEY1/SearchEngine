package se;

public class WordMetaData implements Comparable<WordMetaData> {
	
	public int frequence;
	public String file;
	
	public WordMetaData(int frequence, String file) {
		this.frequence = frequence;
		this.file = file;
	}
	
	public WordMetaData() {
		this.frequence=0;
		this.file = "";
	}

	@Override
	public int compareTo(WordMetaData o) {
		return (o.frequence - this.frequence);
	}
	
	@Override
	public String toString() {
		return this.frequence + " " + this.file;
	}


	

}
