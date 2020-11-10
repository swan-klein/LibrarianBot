package Librarian;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Request_Json {
private static final String URL = "https://www.googleapis.com/books/v1/volumes?q=";

public static JSONObject j_req_JSON(String query) throws Exception {

    String inputLine;
    StringBuilder response = new StringBuilder();
    InputStreamReader bufReader;
    BufferedReader in; 
    
    URL searchQueue = new URL(URL+query.replaceAll(" ", "+"));
    HttpURLConnection connect = (HttpURLConnection) searchQueue.openConnection();
 
    connect.setRequestMethod("GET");
    connect.setRequestProperty("User-Agent", "Mozilla/5.0");
    
    if(connect.getResponseCode() == HttpURLConnection.HTTP_OK) {
		
    	bufReader = new InputStreamReader(connect.getInputStream());
		in = new BufferedReader(bufReader);
		
		    while ((inputLine=in.readLine()) != null)
		        {
		        	response.append(inputLine);
		        }
		    
		    in.close();  
    }
    else
    {
      System.out.println("GET Request Failed.\n");	
    }

    return new JSONObject(response.toString());
}



public static Book j_convJSON(JSONObject jsonObject) throws JSONException {
    Gson gson = new Gson();
    String title,description,rating = "";
    Book book = new Book();
    List<String> authors = new ArrayList<>();
    double aveRating;
    
    try {
    	if(jsonObject.getJSONObject("volumeInfo").has("averageRating")) 
    	   rating = jsonObject.getJSONObject("volumeInfo").get("averageRating").toString();
    	  
    	else 
    	   rating = null;
    	
    } catch (JSONException e) {
        e.printStackTrace();
    }
    
    if(rating != null) {
    	aveRating = Double.parseDouble(rating);
    	book.setRating(aveRating);
    }
    
    title = jsonObject.getJSONObject("volumeInfo").get("title").toString();
    
    book.setTitle(title);
    
    try {
        if(jsonObject.getJSONObject("volumeInfo").has("authors")) {
             	
          JSONArray temp = jsonObject.getJSONObject("volumeInfo").getJSONArray("authors");
        
          for(int i =0;i<temp.length();i++) {
        	authors.add(temp.get(i).toString());  
          }
          
          book.setAuthors(authors);
        }
        
    } catch (JSONException e) {
        e.printStackTrace();
    }
    
    
    if (jsonObject.getJSONObject("volumeInfo").has("description")) {
    	description = jsonObject.getJSONObject("volumeInfo").get("description").toString();
    	book.setDescription(description);
    }
    
     return book;
    }

public static ArrayList<Book> j_getBooks(String query) throws Exception {
	   
    ArrayList<Book> returnedList = new ArrayList<>();
    JSONArray allItems = new JSONObject(j_req_JSON(query).toString()).getJSONArray("items");
    
    for (int i = 0; i < allItems.length(); i++) {  	
        returnedList.add(j_convJSON(allItems.getJSONObject(i)));
    }
    return returnedList;
}
}



