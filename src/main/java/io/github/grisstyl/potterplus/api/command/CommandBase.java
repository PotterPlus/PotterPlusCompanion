package io.github.grisstyl.potterplus.api.command;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * An implementation of CommandExecutor and TabCompleter.
 * @param <T> The plugin class.
 */
public abstract class CommandBase<T extends JavaPlugin> implements CommandExecutor, TabCompleter {

    public static abstract class SubCommand {

        public abstract void execute(CommandContext context);
    }

    @Getter @NonNull
    private T plugin;

    public CommandBase(@NonNull T plugin) {
        this.plugin = plugin;

        PluginCommand command = plugin.getCommand(getLabel());

        if (command == null) {
            throw new RuntimeException(String.format("Command '%s' not registered.", getLabel()));
        } else {
            command.setExecutor(this);
            command.setTabCompleter(this);
        }
    }

    public abstract String getLabel();
    public abstract void execute(CommandContext context);
    public abstract List<String> tab(CommandContext context);

    public List<String> tab(CommandSender sender, Command command, String label, String[] args) {
        return tab(new CommandContext(sender, command, label, args));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandContext context = new CommandContext(sender, command, label, args);

        execute(context);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        CommandContext context = new CommandContext(sender, command, label, args);

        return tab(context);
    }
}

