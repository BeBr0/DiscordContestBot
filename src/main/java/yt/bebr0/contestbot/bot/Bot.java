package yt.bebr0.contestbot.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import yt.bebr0.contestbot.bot.events.CodeReceivedEvent;

/**
 * File is part of BeBrAPI. Thank you for using it! Also check out my YouTube channel where you can also leave your suggestions! https://www.youtube.com/c/BeBr0
 *
 * @author BeBr0
 */
public class Bot {

    public static final Bot instance = new Bot();

    private JDA jda;

    private Bot() {}

    public void initialize() {
        jda = JDABuilder.createDefault("MTAzMDc3Mzk2NTAwMzY5MDA1NA.GXxciM.7NgCxfrpnrdRTvDuEXfaqkrk9HgAdiTv3_6dTU")
                .addEventListeners(new CodeReceivedEvent())
                .build();

        try {
            jda.awaitReady();

            System.out.println("Bot is ready!");
        }
        catch (InterruptedException ignored) {}
    }


    public void textToSystem(String recipientId, String text) {
        jda.getUserById(recipientId).openPrivateChannel().queue(
                privateChannel -> {
                    privateChannel.sendMessage(text).queue();
                }
        );
    }
}
