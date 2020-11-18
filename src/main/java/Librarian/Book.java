package Librarian;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Book {

private String title, description;
private List<String> authors = new ArrayList<>();
private double rating;

public Book() {

}

public Book(String title, ArrayList<String> authors, String description, double rating) {
    this.title = title;
    this.authors = authors;
    this.description = description;
    this.rating = rating;
}

	
	public Double getRating() {
		return rating;
	}
	
	public void setRating(Double rate) {
		this.rating = rate;
	}
	
	public String getTitle() {
	    return title;
	}
	
	public void setTitle(String title) {
	    this.title = title;
	}
	
	public List<String> getAuthors() {
	    return authors;
	}
	
	public void setAuthors(List<String> authors) {
	    this.authors = authors;
	}
	
	
	public String getDescription() {
	    return description;
	}
	
	public void setDescription(String description) {
	    this.description = description;
	}

	//Given a quote as an input, finds books containing a similar quote
	public static String quoteSearch(String quote) throws Exception {
		org.jsoup.nodes.Document doc = org.jsoup.Jsoup.connect("https://www.goodreads.com/quotes/search?utf8=%E2%9C%93&q=" + quote + "&commit=Search").get();
		org.jsoup.select.Elements els = doc.getElementsByClass("authorOrTitle");
		StringBuilder elList = new StringBuilder();
		int index = 0;
		for (org.jsoup.nodes.Element el : els) {
			if (!elList.toString().contains(el.text())) {
				elList.append(el.text());
				if (index++ % 2 == 0) {
					elList.append(" ");
				} else {
					elList.append("\n");
				}
			}
		}
		return elList.toString();
	}

	//Give a public domain book and a list of words, finds the frequency of each word within the book
	public static String wordFrequency(String book, String[] words, String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(path.substring(0, path.length()-1) + "GUTINDEX.ALL")));
		String booknum;
		while ((booknum = br.readLine()) != null) {
			if (booknum.contains(book) && booknum.length() > 73) {
				System.out.println(booknum);
				booknum = booknum.substring(73).replaceAll("[^0-9]", "");
				break;
			}
		}
		if (booknum == null) return "No book with the title " + book + " was found";
		StringBuilder url = new StringBuilder("https://mirrors.xmission.com/gutenberg/");
		if (booknum.length() == 1) url.append("0/");
		for (int i = 0; i < booknum.length()-1; i++) {
			url.append(booknum.charAt(i));
			url.append("/");
		}
		booknum += "/" + booknum + ".txt";
		url.append(booknum);
		int[] freqs = new int[words.length];
		String text = org.jsoup.Jsoup.connect(url.toString()).get().getElementsByTag("body").get(0).text();
		for (int i = 0; i < words.length; i++) {
			freqs[i] = countFreq(text, words[i]);
		}
		System.out.println(words[0] + freqs[0]);
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
			ret.append(words[i]);
			ret.append(": ");
			ret.append(freqs[i]);
			ret.append("\n");
		}
		ret.append("Total Word Count: ");
		ret.append(countWordsUsingSplit(text));
		return ret.toString();
	}

	// Function to count the frequency of
	// a given word in the given string
	static int countFreq(String str, String word) {

		int freq = 0;
		// Splitting to find the word
		String[] arr = str.split("[\\W]");

		// Loop to iterate over the words
		for(String w : arr)
		{
			if (w.equalsIgnoreCase(word)) freq++;
		}

		return freq;
	}

	//Counts words to find frequencies as percentages
	public static int countWordsUsingSplit(String input) {
		if (input == null || input.isEmpty()) {
			return 0;
		}

		String[] words = input.split("\\s+");
		return words.length;
	}
}
