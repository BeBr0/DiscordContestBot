package yt.bebr0.contestbot.bot.events;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import yt.bebr0.contestbot.Database;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

public class SlashCommandEvent extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "add_task" -> {
                if (Database.instance.isAdmin(event.getUser().getId())) {
                    if (event.getOption("имя") != null && event.getOption("задание") != null) {
                        Task.addTask(
                                new Task(
                                        event.getOption("имя").getAsString(),
                                        event.getOption("задание").getAsString()
                                )
                        );

                        event.reply("Добавлено!").setEphemeral(true).queue();
                    }
                    else {
                        event.reply("Недостаточно аргументов!").setEphemeral(true).queue();
                    }
                }
                else
                    event.reply("Недостаточно прав!").setEphemeral(true).queue();
            }
            case "remove_task" -> {
                if (Database.instance.isAdmin(event.getUser().getId())) {
                    if (event.getOption("имя") != null) {
                        Task.removeTask(
                                Task.getTask(event.getOption("имя").getAsString())
                        );

                        event.reply("Удалено!").setEphemeral(true).queue();
                    }
                    else {
                        event.reply("Недостаточно аргументов!").setEphemeral(true).queue();
                    }

                }
                else
                    event.reply("Недостаточно прав!").setEphemeral(true).queue();
            }
            case "add_test_case" -> {
                if (Database.instance.isAdmin(event.getUser().getId())) {
                    if (event.getOption("ввод") != null && event.getOption("вывод") != null
                            && event.getOption("задание") != null) {

                        Task task = Task.getTask(event.getOption("задание").getAsString());
                        task.addTestCase(new TestCase(
                                        task,
                                        event.getOption("ввод").getAsString(),
                                        event.getOption("вывод").getAsString()
                                ));


                        event.reply("Добавлено!").setEphemeral(true).queue();
                    }
                    else {
                        event.reply("Недостаточно аргументов!").setEphemeral(true).queue();
                    }
                }
                else
                    event.reply("Недостаточно прав!").setEphemeral(true).queue();
            }
            case "remove_test_case" -> {
                if (Database.instance.isAdmin(event.getUser().getId())) {
                    if (event.getOption("задание") != null && event.getOption("номер") != null) {
                        Task.getTask(event.getOption("задание").getAsString()).getTestCases()
                                .remove(event.getOption("номер").getAsInt());


                        event.reply("Удалено!").setEphemeral(true).queue();
                    }
                    else {
                        event.reply("Недостаточно аргументов!").setEphemeral(true).queue();
                    }

                }
                else
                    event.reply("Недостаточно прав!").setEphemeral(true).queue();
            }
            case "add_admin" -> {
                if (Database.instance.isAdmin(event.getUser().getId())) {
                    Database.instance.addAdmin(event.getOption("id").getAsString());
                    event.reply("Готово!").setEphemeral(true).queue();
                }
                else
                    event.reply("Недостаточно прав!").setEphemeral(true).queue();
            }
            case "info" -> {
                StringBuilder info = new StringBuilder().append("```==========Информация===========\n");

                for (Task task: Task.getTasksCopy()) {
                    info.append(task.getName()).append(":\n").append(task.getTask()).append("\n");
                }

                info.append("```");
                event.reply(info.toString()).setEphemeral(true).queue();
            }
            case "admin_info" -> {
                if (Database.instance.isAdmin(event.getUser().getId())) {
                    StringBuilder info = new StringBuilder().append("```==========Информация===========\n");

                    for (Task task: Task.getTasksCopy()) {
                        info.append(task.getName()).append(":\n").append(task.getTask()).append("\n");

                        for (TestCase testCase: task.getTestCases()) {
                            info
                                    .append("\tВвод: ")
                                    .append(testCase.getInput())
                                    .append("\tВывод")
                                    .append(testCase.getExpectedOutput())
                                    .append("\n");
                        }
                    }

                    info.append("```");
                    event.reply(info.toString()).setEphemeral(true).queue();
                }
                else
                    event.reply("Недостаточно прав!").setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(
                Commands.slash("add_task", "Добавляет задание")
                        .addOption(OptionType.STRING, "имя", "Имя задания")
                        .addOption(
                                OptionType.STRING,
                                "задание",
                                "формулировка задания, которую увидит пользователь"
                        ),
                Commands.slash("remove_task", "Удаляет задание")
                        .addOption(OptionType.STRING, "имя", "Имя задания"),
                Commands.slash("add_test_case", "Добавляет тест-кейс к заданию")
                        .addOptions(
                                new OptionData(OptionType.STRING, "задание", "Имя задания"),
                                new OptionData(OptionType.STRING, "ввод", "Вводные данные для теста"),
                                new OptionData(OptionType.STRING, "вывод", "Ожидаемый результат выполнения программы при заданных входных данных")
                        ),
                Commands.slash("remove_test_case", "Удаляет тест-кейс")
                        .addOption(OptionType.STRING, "задание", "Имя задания")
                        .addOption(OptionType.INTEGER, "номер", "номер тест кейса, который необходимо удалить"),
                Commands.slash("admin_info", "Отправляет информацию о заданиях и существующих тест-кейсах к ним в лс"),
                Commands.slash("info", "Отправляет в личные сообщения пользовательскую информацию о заданиях")

        ).queue();
    }
}
