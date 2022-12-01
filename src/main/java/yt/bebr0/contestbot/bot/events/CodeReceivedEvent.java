package yt.bebr0.contestbot.bot.events;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import yt.bebr0.contestbot.Database;
import yt.bebr0.contestbot.bot.Bot;
import yt.bebr0.contestbot.testing.TestResult;
import yt.bebr0.contestbot.testing.languages.Language;
import yt.bebr0.contestbot.testing.languages.Tester;
import yt.bebr0.contestbot.testing.task.Task;

import java.util.List;

/**
 * Code written by BeBr0. Check out my YouTube - <a href="https://www.youtube.com/c/BeBr0">...</a>
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

                        List<TestResult> result = language.getTester().test(task, code);

                        StringBuilder message = new StringBuilder("```");
                        StringBuilder logMessage = new StringBuilder("[LOG]: User " + event.getAuthor().getName() + "("+
                                event.getAuthor().getId() + ") is running test of task '" + task.getName() + "': \n");

                        for (int i = 0; i < result.size(); i++) {
                            logMessage.append("\tTest № ").append(i + 1).append("\n")
                                    .append("\tTime: ").append(result.get(i).timeMilliseconds()).append(" mills\n")
                                    .append("\tResult: ").append(result.get(i).answer()).append("\n")
                                    .append("\tInput: ").append(Database.instance.getTestCases(task).get(i).input()).append("\n")
                                    .append("\tOutput: ").append(result.get(i).answerText());

                            if (result.get(i).answer() && result.get(i).timeMilliseconds() <= task.getMaxTimeMills())
                                message.append("Тест ").append(i + 1).append(": ✅\n");
                            else if (result.get(i).timeMilliseconds() > task.getMaxTimeMills())
                                message.append("Тест ").append(i + 1).append(": ❌ - Лимит времени исчерпан\n");
                            else if (result.get(i).answerText().equals(Tester.noOutText) || result.get(i).answerText().equals(""))
                                message.append("Тест ").append(i + 1).append(": ❌ - Ошибка выполнения программы\n");
                            else
                                message.append("Тест ").append(i + 1).append(": ❌ - Ответ не верный\n");
                        }

                        message.append("```");
                        System.out.println(logMessage.append("\n-------------------------"));

                        Bot.instance.textTo(event.getAuthor().getId(), message.toString());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
    }
}
