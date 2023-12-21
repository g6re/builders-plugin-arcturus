package com.eu.habbo.builders.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.builders.BuildersEssentials;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;

public class BuildHeightCommand extends Command implements EventListener {
    public static String BUILD_HEIGHT_KEY = "be.build_height";

    public BuildHeightCommand(String permission, String[] keys) {
        super(permission, keys);
        Emulator.getPluginManager().registerEvents((HabboPlugin)BuildersEssentials.INSTANCE, this);
    }

    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (strings.length == 2) {
            double height = 0.0D;
            try {
                height = Double.parseDouble(strings[1]);
            } catch (Exception exception) {}
            if (height < -100.0D || height > 100.0D) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_buildheight.invalid_height"));
                return true;
            }
            (gameClient.getHabbo().getHabboStats()).cache.put(BUILD_HEIGHT_KEY, Double.valueOf(height));
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_buildheight.changed").replace("%height%", height + ""));
        } else {
            if ((gameClient.getHabbo().getHabboStats()).cache.containsKey(BUILD_HEIGHT_KEY)) {
                (gameClient.getHabbo().getHabboStats()).cache.remove(BUILD_HEIGHT_KEY);
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_buildheight.disabled"));
                return true;
            }
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_buildheight.not_specified"));
        }
        return true;
    }
}
