package io.github.grisstyl.potterplus.companion.command;

import io.github.grisstyl.potterplus.api.misc.FriendlyBooleans;
import io.github.grisstyl.potterplus.companion.PotterPlusController;
import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import io.github.grisstyl.potterplus.api.command.CommandBase;
import io.github.grisstyl.potterplus.api.command.CommandContext;
import io.github.grisstyl.potterplus.api.misc.BooleanFormat;
import io.github.grisstyl.potterplus.companion.PotterPlusPlugin;

import java.util.List;

import static io.github.grisstyl.potterplus.api.misc.StringUtilities.color;
import static io.github.grisstyl.potterplus.api.misc.StringUtilities.equalsAny;

/**
 * Copyright (c) 2013-2020 Tyler Grissom
 */
public class PotterPlusCommand extends CommandBase<PotterPlusPlugin> {

    public PotterPlusCommand(@NonNull PotterPlusPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getLabel() {
        return "io/github/grisstyl/potterplus";
    }

    public List<String> getHelp() {
        return color(
                "&d&l*** &7PotterPlus Companion Plugin &d&l***",
                "&8&m-------------------------",
                "  &8> &7/ppc feature <feature> [bool] &8- &6View or modify a feature of PPC",
                "  &8> &7/ppc instructions &8- &6View the initial installation instructions",
                "  &8> &7/ppc reload &8- &6Reload the plugin",
                "  &8> &7/ppc version &8- &6View the plugin and PotterPlus versions"
        );
    }

    public List<String> getAvailableFeatures() {
        return color(
                "&dAvailable features&8:",
                " &8- &eprogress_menu_click"
        );
    }

    @Override
    public void execute(CommandContext context) {
        PotterPlusController controller = getPlugin().getController();

        if (context.getArgs().length == 0) {
            context.sendMessage(getHelp());
        } else {
            String sub = context.getArgs()[0];

            if (equalsAny(sub, "feature")) {
                if (context.getArgs().length == 1) {
                    context.sendMessage("&cUsage&8: &7/ppc feature <feature> [bool]");
                } else {
                    String feature = context.getArgs()[1];

                    if (!equalsAny(feature, "progress_menu_click", "progressmenu", "progressmenuclick")) {
                        context.sendMessage(getAvailableFeatures());

                        return;
                    }

                    if (context.getArgs().length == 2) {
                        if (equalsAny(feature, "progress_menu_click", "progressmenu", "progressmenuclick")) {
                            context.sendMessage("&dPotterPlus&8> &7That feature is " + BooleanFormat.ENABLED_DISABLED.format(getPlugin().getController().getConfig().isProgressMenuClickEnabled()));
                        } else {
                            context.sendMessage(getAvailableFeatures());
                        }
                    } else {
                        String modify;

                        if (equalsAny("progress_menu_click", "progressmenu", "progressmenuclick")) {
                            modify = "progress_menu_click";
                        } else {
                            context.sendMessage(getAvailableFeatures());

                            return;
                        }

                        String bool = context.getArgs()[2];
                        FileConfiguration fc = getPlugin().getConfig();

                        if (FriendlyBooleans.isFriendlyBoolean(bool) || bool.equalsIgnoreCase("toggle")) {
                            boolean b;

                            if (bool.equalsIgnoreCase("toggle")) {
                                b = !fc.getBoolean("features." + modify, true);
                            } else {
                                b = FriendlyBooleans.getFriendlyBoolean(bool);
                            }

                            fc.set("features." + modify, b);

                            getPlugin().saveConfig();

                            context.sendMessage("&dPotterPlus&8> &7Feature &e" + modify + " &7has been " + BooleanFormat.ENABLED_DISABLED.format(b));

                            getPlugin().getController().load();
                        } else {
                            context.sendMessage("&cValid booleans&8: &etrue&8, &efalse&8, &etoggle");
                        }
                    }
                }
            } else if (equalsAny(sub, "features")) {
                context.sendMessage(getAvailableFeatures());
            } else if (equalsAny(sub, "instructions")) {
                context.sendMessage(controller.getFirstTimeInstructions());
            } else if (equalsAny(sub, "reload", "load", "refresh")) {
                context.sendMessage("&dPotterPlus&8> &7Reloading plugin&8...");

                try {
                    getPlugin().reloadConfig();
                } catch (Exception e) {
                    context.sendMessage("&dPotterPlus&8> &cFailed to reload! Check the console for the issue.");

                    e.printStackTrace();
                }
            } else if (equalsAny(sub, "version", "v")) {
                context.sendMessage("&7Plugin version&8: &e" + getPlugin().getDescription().getVersion());
                context.sendMessage("&7Config version&8: &cUnknown"); // TODO Read PotterPlus JSON file
            } else {
                context.sendMessage(getHelp());
            }
        }
    }

    @Override
    public List<String> tab(CommandContext context) {
        return null;
    }
}
