package com.eu.habbo.builders.room;

import com.eu.habbo.builders.commands.BuildHeightCommand;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.furniture.FurnitureBuildheightEvent;
import com.eu.habbo.plugin.events.users.UserExitRoomEvent;

public class SetBuildHeight implements EventListener {
    @EventHandler
    public static void onUserExitRoomEvent(UserExitRoomEvent event) {
        (event.habbo.getHabboStats()).cache.remove(BuildHeightCommand.BUILD_HEIGHT_KEY);
    }

    @EventHandler
    public static void onFurniturePlaced(FurnitureBuildheightEvent event) {
        Habbo habbo = event.habbo;
        HabboItem item = event.furniture;
        if (habbo != null && item != null &&
                (habbo.getHabboStats()).cache.containsKey(BuildHeightCommand.BUILD_HEIGHT_KEY))
            event.setNewHeight(((Double)(habbo.getHabboStats()).cache.get(BuildHeightCommand.BUILD_HEIGHT_KEY)).doubleValue());
    }
}
