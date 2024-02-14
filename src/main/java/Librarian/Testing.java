package Librarian;

import java.util.ArrayList;

public class Testing {
	
	private String query;
	private String expectedResult;
	
	Testing(String result,String query){
		this.expectedResult = result;
		this.query = query;
	}
	
	public void setExpectedResult(String value) {
		this.expectedResult = value;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public boolean testing_authors() {
		boolean success= true;
		
		if(testing_Jrequest()) {	
			try {
				if(!Request_Json.j_getBooks(query).isEmpty()) {	
					ArrayList<Book> book = Request_Json.j_getBooks(query);
					
					for(String author:book.get(0).getAuthors()) {
						if(author.equals(expectedResult)) {
							break;
						}
						else {
							success=false;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}
	
	public boolean testing_description() {
	 boolean success= true;
		if(testing_Jrequest()) {	
			try {
				if(!Request_Json.j_getBooks(query).isEmpty()) {	
					ArrayList<Book> book = Request_Json.j_getBooks(query);	
					if(!book.get(0).getDescription().equals(expectedResult)) {
						success=false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return success;
	}
	
	public boolean testing_rating() {
		 boolean success= true;
			if(testing_Jrequest()) {	
				try {
					if(!Request_Json.j_getBooks(query).isEmpty()) {	
						ArrayList<Book> book = Request_Json.j_getBooks(query);	
						if(!book.get(0).getRating().equals(Double.parseDouble(expectedResult))) {
							success=false;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return success;
		}

	public boolean testing_wordFrequency() {
		boolean success= true;
		if(testing_Jrequest()) {
			try {
				// Contains since there may be many books with the quote
				if(!Book.quoteSearch(query).contains(expectedResult)) {
					success=false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}
	
	public boolean testing_Jrequest() {
		boolean success = true;
		
		try {
			if(query.isEmpty() && Request_Json.j_req_JSON(query) != null) {
				success=false;
			}
			else {
				if(!query.isEmpty() && Request_Json.j_req_JSON(query)==null) {
					success= false;
				}		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
}
