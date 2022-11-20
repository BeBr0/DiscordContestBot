package yt.bebr0.contestbot.bot.events;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import yt.bebr0.contestbot.Database;
import yt.bebr0.contestbot.bot.Bot;
import yt.bebr0.contestbot.testing.languages.Language;
import yt.bebr0.contestbot.testing.languages.impls.JavaTester;
import yt.bebr0.contestbot.testing.languages.impls.PythonTester;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Code written by BeBr0. Check out my YouTube - https://www.youtube.com/c/BeBr0
 *
 * @author BeBr0
 */

public class CodeReceivedEvent extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel() instanceof PrivateChannel) {
            if (event.getMessage().getContentRaw().startsWith("```")) {
                String code = event.getMessage().getContentRaw().replaceAll("```", "");

                for (Language language: Language.values()) {
                    if (code.startsWith(language.getName().toLowerCase())) {
                        code = code.replaceFirst(language.getName().toLowerCase(), "");

                        String taskName = code.substring(
                                code.indexOf(language.getCommentMarker()) + language.getCommentMarker().length(),
                                code.indexOf("\n", code.indexOf(language.getCommentMarker()) + language.getCommentMarker().length())
                        );

                        Task task = Database.instance.getTask(taskName);

                        if (task == null) {
                            Bot.instance.textTo(event.getAuthor().getId(), "Неверное имя задачи!");
                            return;
                        }

                        List<Boolean> result = language.getTester().test(task, code);

                        StringBuilder message = new StringBuilder("```");
                        for (int i = 0; i < result.size(); i++) {
                            if (result.get(i))
                                message.append(i).append(": ✅\n");
                            else
                                message.append(i).append(": ❌\n");
                        }

                        message.append("```");

                        Bot.instance.textTo(event.getAuthor().getId(), message.toString());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
    }
}
