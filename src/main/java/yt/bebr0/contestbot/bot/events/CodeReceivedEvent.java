package yt.bebr0.contestbot.bot.events;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import yt.bebr0.contestbot.testing.Tester;

/**
 * Code written by BeBr0. Check out my YouTube - https://www.youtube.com/c/BeBr0
 *
 * @author BeBr0
 */

public class CodeReceivedEvent implements EventListener {


    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if (genericEvent instanceof MessageReceivedEvent) {
            MessageReceivedEvent event = (MessageReceivedEvent) genericEvent;

            if (event.getChannel() instanceof PrivateChannel) {
                if (event.getMessage().getContentRaw().startsWith("```")) {
                    Tester.instance.run(event.getAuthor().getId(), event.getMessage().getContentRaw().replaceAll("```", ""));
                }
            }
        }
    }
}
