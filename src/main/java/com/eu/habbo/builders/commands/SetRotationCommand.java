package com.eu.habbo.builders.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.builders.BuildersEssentials;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;

public class SetRotationCommand extends Command implements EventListener {
    public static String SET_ROTATION_KEY = "be.set_rotation";

    public SetRotationCommand(String permission, String[] keys) {
        super(permission, keys);
        Emulator.getPluginManager().registerEvents((HabboPlugin)BuildersEssentials.INSTANCE, this);
    }

    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (strings.length == 2) {
            int rotation = 0;
            try {
                rotation = Integer.parseInt(strings[1]);
            } catch (Exception exception) {}
            if (rotation > 7 || rotation < 0) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_setrotation.invalid_state"));
                return true;
            }
            (gameClient.getHabbo().getHabboStats()).cache.put(SET_ROTATION_KEY, Integer.valueOf(rotation));
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_setrotation.changed").replace("%rot%", rotation + ""));
        } else {
            if ((gameClient.getHabbo().getHabboStats()).cache.containsKey(SET_ROTATION_KEY)) {
                (gameClient.getHabbo().getHabboStats()).cache.remove(SET_ROTATION_KEY);
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_setrotation.disabled"));
                return true;
            }
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_setrotation.not_specified"));
        }
        return true;
    }
}
