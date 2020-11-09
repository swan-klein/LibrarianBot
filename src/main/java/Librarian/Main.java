package Librarian;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    static Path workingDirectory;

    public static void main(String[] args) throws LoginException {
        String token = "NzUzMzE2MDM3MzE4OTM0NjI5.X1kaOw.paAaQZcUggye32PliJVniCjGET4";
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new Main());
        builder.build();
        workingDirectory=Paths.get(".").toAbsolutePath();
        String filepath = workingDirectory.toString();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //first line ignores input from bots, including itself
        if(event.getAuthor().isBot()) {
            return;
        }
        //processes input to produce desired output
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay());
        try {
            if (event.getMessage().getContentRaw().substring(0, event.getMessage().getContentRaw().indexOf(' ')).equals("!booksBy")) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}