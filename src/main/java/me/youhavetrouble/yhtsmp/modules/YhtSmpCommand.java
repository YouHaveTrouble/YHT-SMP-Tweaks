package me.youhavetrouble.yhtsmp.modules;

import me.youhavetrouble.yhtsmp.YhtSmpTweaks;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class YhtSmpCommand extends Command {

    private final YhtSmpTweaks plugin;

    public YhtSmpCommand(YhtSmpTweaks plugin) {
        super("yhtsmp");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage(Component.text("YHT SMP Tweaks"));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("yhtsmp.reload")) {
                plugin.reloadSmpConfig();
                sender.sendMessage(Component.text("Reloaded YHT SMP Tweaks"));
                return true;
            }
            sender.sendMessage(Component.text("You Fool! You can't do that!"));
            return true;
        }
        sender.sendMessage(Component.text("You Fool! You can't do that!"));
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(
            @NotNull CommandSender sender,
            @NotNull String alias,
            @NotNull String[] args
    ) throws IllegalArgumentException {

        List<String> completes = new ArrayList<>();

        if (args.length == 1) {
            if ("reload".startsWith(args[0].toLowerCase()) && sender.hasPermission("yhtsmp.reload")) {
                completes.add("reload");
            }
        }

        return completes;
    }
}
