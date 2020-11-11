package Librarian;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
	static Path workingDirectory;
	
	public enum Comms 
	{
		AUTHOR("!author"),
		BOOK("!book"),
		RATING("!rating"),
		PRICE("!price"),
		DESCRIPTION("!description"),
		HELP("!help");
		
	    private String comms;
	 
	    Comms(String comms) {
	        this.comms = comms;
	    }
	 
	    public String getComms() {
	        return comms;
	    }
	}

    public static void main(String[] args) throws LoginException {
        String token = "NzUzMzE2MDM3MzE4OTM0NjI5.X1kaOw.paAaQZcUggye32PliJVniCjGET4";
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new Main());
        builder.build();
		workingDirectory=Paths.get(".").toAbsolutePath();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
		//first line ignores input from bots, including itself
		if(event.getAuthor().isBot()) {
			return;
		}
        String[] compliment = {"Awesome","Legendary","Crazy","Insane","Godly","Professional","Prominent","Prodigy","Intellectual"};

    	String command = event.getMessage().getContentRaw();
    	int index = command.indexOf(' ');
    	String help = " ";
    	
    	if(index > 0) 
    	  {
	    	help = command.substring(0,index);
	    	command = command.substring(index);
    	  }
    	else 
    	  {
    		if(command.equals(Comms.HELP.getComms())) {
    			help = Comms.HELP.getComms();
    		}
    		
    		command = " ";
    	  }

		//processes input to produce desired output
		System.out.println("We received a message from " +
				event.getAuthor().getName() + ": " +
				event.getMessage().getContentDisplay());
    	try {
    		
    	    ArrayList<Book> books;  
    		
    		if(help.equals(Comms.AUTHOR.getComms()))
    		 {
    			books = Request_Json.j_getBooks(command.substring(1));
    			 // System.out.println("InAuthor\n");
				  List<String> authors = books.get(0).getAuthors();
				  event.getChannel().sendMessage(">>> Author(s):").queue();
				  for(String author : authors) {
					  event.getChannel().sendMessage(">>> " +compliment[(int) (Math.random()*(compliment.length-1))] + " " + author).queue();
				  }
    		 }
    		
    		else if(help.equals(Comms.DESCRIPTION.getComms())) {
    			books = Request_Json.j_getBooks(command.substring(1));
    			//System.out.println("InDesc\n");
    			event.getChannel().sendMessage(">>> " + 
    					books.get(0).getDescription()).queue();
    		}
    		
    		else if(help.equals(Comms.RATING.getComms())) {
    			books = Request_Json.j_getBooks(command.substring(1));
    			event.getChannel().sendMessage(">>> The rating is: "+books.get(0).getRating().toString()).queue();
    		}
    		else if(help.equals(Comms.HELP.getComms())){
    			
   		     EmbedBuilder bd = new EmbedBuilder();
   		     bd.setAuthor("Commands:");
   		     bd.addField("```!author [book name]```", "Prints the author(s) of the book.\n", false);
   		     bd.addField("```!description [book name]```", "Prints a short description of a book.", true);
   		     bd.addField("```!rating [book name]```", "Prints the review rating of a book.", false);
		     bd.addField("```!quoteSearch [quote]```", "Finds books with matching or similar quotes.", false);
   		     bd.addField("```!booksBy [author]```", "Returns a list of all books by the author.", false);
   		     bd.setColor(new Color(0x7ED321));
   			 event.getChannel().sendMessage(bd.build()).queue();
    		}
    		else if (event.getMessage().getContentRaw().substring(0, event.getMessage().getContentRaw().indexOf(' ')).equals("!booksBy")) {
				String author = event.getMessage().getContentRaw();
				author = author.substring(author.indexOf(' ') + 1);
				try {
					event.getChannel().sendMessage(Author.booksBy(author, workingDirectory.toString())).queue();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (event.getMessage().getContentRaw().substring(0, event.getMessage().getContentRaw().indexOf(' ')).equals("!quoteSearch")) {
				String quote = event.getMessage().getContentRaw();
				quote = quote.substring(quote.indexOf(' ') + 1);
				try {
					event.getChannel().sendMessage(Book.quoteSearch(quote)).queue();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (event.getMessage().getContentRaw().substring(0, event.getMessage().getContentRaw().indexOf(' ')).equals("!wordFrequency")) {
				String book = event.getMessage().getContentRaw();
				book = book.substring(book.indexOf(' ') + 1);
				String[] words = book.substring(book.indexOf(';') + 1).trim().split("[\\W]");
				try {
					event.getChannel().sendMessage(Book.wordFrequency(book.substring(0,book.indexOf(";")), words, workingDirectory.toString())).queue();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
    	  
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
