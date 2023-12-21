package com.eu.habbo.builders.room;

import com.eu.habbo.Emulator;
import com.eu.habbo.builders.commands.SetRotationCommand;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.furniture.FurnitureMovedEvent;
import com.eu.habbo.plugin.events.furniture.FurniturePlacedEvent;
import com.eu.habbo.plugin.events.users.UserExitRoomEvent;

public class SetRotation implements EventListener {
    @EventHandler
    public static void onUserExitRoomEvent(UserExitRoomEvent event) {
        (event.habbo.getHabboStats()).cache.remove(SetRotationCommand.SET_ROTATION_KEY);
    }

    @EventHandler
    public static void onFurniturePlaced(FurniturePlacedEvent event) {
        if (event.location != null) {
            Habbo habbo = event.habbo;
            HabboItem item = event.furniture;
            if (habbo != null && (habbo.getHabboStats()).cache.containsKey(SetRotationCommand.SET_ROTATION_KEY)) {
                Emulator.getThreading().run(() -> {
                    item.setRotation(((Integer)(habbo.getHabboStats()).cache.get(SetRotationCommand.SET_ROTATION_KEY)).intValue());
                    item.needsUpdate(true);
                    habbo.getHabboInfo().getCurrentRoom().updateItem(item);
                });
            }
        }
    }

    @EventHandler
    public static void onFurnitureMoved(FurnitureMovedEvent event) {
        if (event.newPosition != null) {
            Habbo habbo = event.habbo;
            HabboItem item = event.furniture;
            if (habbo != null && (habbo.getHabboStats()).cache.containsKey(SetRotationCommand.SET_ROTATION_KEY)) {
                Emulator.getThreading().run(() -> {
                    item.setRotation(((Integer)(habbo.getHabboStats()).cache.get(SetRotationCommand.SET_ROTATION_KEY)).intValue());
                    item.needsUpdate(true);
                    habbo.getHabboInfo().getCurrentRoom().updateItem(item);
                });
            }
        }
    }
}
