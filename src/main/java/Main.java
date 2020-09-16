import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        String token = "NzUzMzE2MDM3MzE4OTM0NjI5.X1kaOw.paAaQZcUggye32PliJVniCjGET4";
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new Main());
        builder.build();
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
    }
}