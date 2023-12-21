package com.eu.habbo.builders.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.builders.BuildersEssentials;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;

public class SetStateCommand extends Command implements EventListener {
    public static String SET_STATE_KEY = "be.set_state";

    public SetStateCommand(String permission, String[] keys) {
        super(permission, keys);
        Emulator.getPluginManager().registerEvents((HabboPlugin)BuildersEssentials.INSTANCE, this);
    }

    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (strings.length == 2) {
            String extraData = "0";
            try {
                extraData = String.valueOf(strings[1]);
            } catch (Exception exception) {}
            int checkExtraData = Integer.parseInt(extraData);
            if (checkExtraData < 0 || checkExtraData > 100) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_setstate.invalid_state"));
                return true;
            }
            (gameClient.getHabbo().getHabboStats()).cache.put(SET_STATE_KEY, extraData);
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_setstate.changed").replace("%extra_data%", extraData));
        } else {
            if ((gameClient.getHabbo().getHabboStats()).cache.containsKey(SET_STATE_KEY)) {
                (gameClient.getHabbo().getHabboStats()).cache.remove(SET_STATE_KEY);
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_setstate.disabled"));
                return true;
            }
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("be.cmd_setstate.not_specified"));
        }
        return true;
    }
}
