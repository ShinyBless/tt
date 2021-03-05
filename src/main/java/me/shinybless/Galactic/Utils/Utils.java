package me.shinybless.Galactic.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String chat(String s) {
        if (Bukkit.getVersion().contains("1.16")) {
            s = s.replace("&#", "#").replace("&k", "").replace("%", " ");
            Matcher match = pattern.matcher(s);
            while (match.find()) {
                String color = s.substring(match.start(), match.end());
                s = s.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                match = pattern.matcher(s);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
