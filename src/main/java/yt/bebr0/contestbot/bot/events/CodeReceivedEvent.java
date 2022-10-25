package yt.bebr0.contestbot.bot.events;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import yt.bebr0.contestbot.bot.Bot;
import yt.bebr0.contestbot.testing.languages.Language;
import yt.bebr0.contestbot.testing.languages.impls.JavaTester;
import yt.bebr0.contestbot.testing.languages.impls.PythonTester;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

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
                System.out.println("Code detected");
                String code = event.getMessage().getContentRaw().replaceAll("```", "");

                for (Language language: Language.values()) {
                    if (code.startsWith(language.getName().toLowerCase())) {
                        System.out.println("Language " + language.getName());
                        code = code.replace(language.getName().toLowerCase(), "");
                        Task task = Task.getTask(code.substring(code.indexOf(language.getCommentMarker()) + 1, code.indexOf("\n")));

                        if (task == null) {
                            Bot.instance.textTo(event.getAuthor().getId(), "Неверное имя задачи!");
                            return;
                        }

                        List<Boolean> result = language.getTester().test(task, code);

                        StringBuilder message = new StringBuilder("```");
                        for (int i = 0; i < result.size(); i++) {
                            if (result.get(i))
                                message.append(i).append(1).append(": ✅");
                        }

                        message.append("```");

                        Bot.instance.textTo(event.getAuthor().getId(), message.toString());
                        break;
                    }
                }
            }
        }
    }
}
