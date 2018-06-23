package com.rocket.rocketbot.localization;

import com.cyr1en.mcb.configuration.MCBConfigManager;
import com.cyr1en.mcutils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Locale {

    private static MCBConfigManager configsManager;

    public static void init(MCBConfigManager config) {
        Locale.configsManager = config;
    }

    private static YamlConfiguration getLocalization() {
        String loc = configsManager.getBotConfig().getLocale();
        File f = new File(Bukkit.getPluginManager().getPlugin("MineCordBot").getDataFolder() + "/localizations/" + loc + ".yml");
        if (!f.exists())
            f = new File(Bukkit.getPluginManager().getPlugin("MineCordBot").getDataFolder() + "localizations/en.yml");
        return YamlConfiguration.loadConfiguration(f);
    }

    private static String getTranslatedMessage(String messagePath) {
        String s = getLocalization().getString(messagePath);
        if (s == null) {
            Logger.warn("Can not get localization for " + messagePath + ". Returned path");
            return messagePath;
        }
        return getLocalization().getString(messagePath);
    }

    public static Formatter getEventMessage(String path) {
        return new Formatter(getTranslatedMessage("mcb.event." + path));
    }

    public static Formatter getCommandMessage(String path) {
        return new Formatter(getTranslatedMessage("mcb.command." + path));
    }

    public static Formatter getCommandsMessage(String path) {
        return new Formatter(getTranslatedMessage("mcb.commands." + path));
    }

    public static Formatter getResponsLvlMsg(String path) {
        return new Formatter(getTranslatedMessage("mcb.responselevel." + path));
    }

    public static class Formatter {
        private String message;

        public Formatter(String message) {
            this.message = message;
        }

        public String f(Object... objects) {
            return String.format(message, objects);
        }

        public String finish() {
            return message;
        }
    }
}