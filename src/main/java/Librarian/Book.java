package Librarian;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

}
