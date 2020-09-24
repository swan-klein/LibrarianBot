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
        if(event.getMessage().getContentRaw().equals("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
        }
        else if(event.getMessage().getContentRaw().substring(0,event.getMessage().getContentRaw().indexOf(' ')).equals("!booksBy")) {
            String author = event.getMessage().getContentRaw();
            author = author.substring(author.indexOf('"')+1);
            author = author.substring(0,author.indexOf('"'));
            try {
                event.getChannel().sendMessage(Author.booksBy(author, workingDirectory.toString())).queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}