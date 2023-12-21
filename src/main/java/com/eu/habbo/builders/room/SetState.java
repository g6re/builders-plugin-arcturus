package com.eu.habbo.builders.room;

import com.eu.habbo.Emulator;
import com.eu.habbo.builders.commands.SetStateCommand;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.furniture.FurnitureMovedEvent;
import com.eu.habbo.plugin.events.furniture.FurniturePlacedEvent;
import com.eu.habbo.plugin.events.users.UserExitRoomEvent;

public class SetState implements EventListener {

    @EventHandler
    public static void onUserExitRoomEvent(UserExitRoomEvent event) {
        (event.habbo.getHabboStats()).cache.remove(SetStateCommand.SET_STATE_KEY);
    }

    @EventHandler
    public static void onFurniturePlaced(FurniturePlacedEvent event) {
        if (event.location != null) {
            Habbo habbo = event.habbo;
            HabboItem item = event.furniture;
            if (item != null && habbo != null &&
                    (habbo.getHabboStats()).cache.containsKey(SetStateCommand.SET_STATE_KEY)) {
                if (!item.getBaseItem().getInteractionType().getName().equals("dice") &&
                        !item.getBaseItem().getInteractionType().getName().equals("background_toner") &&
                        !item.getBaseItem().getInteractionType().getName().equals("dimmer") &&
                        !item.getBaseItem().getInteractionType().getName().equals("trophy") &&
                        !item.getBaseItem().getInteractionType().getName().equals("badge_display") &&
                        !item.getBaseItem().getInteractionType().getName().equals("vendingmachine")) {
                    Emulator.getThreading().run(() -> {
                        item.setExtradata((String)(habbo.getHabboStats()).cache.get(SetStateCommand.SET_STATE_KEY));
                        item.needsUpdate(true);
                        habbo.getHabboInfo().getCurrentRoom().updateItem(item);
                    });
                } else {
                    (habbo.getHabboStats()).cache.remove(SetStateCommand.SET_STATE_KEY);
                }
            }
        }
    }

    @EventHandler
    public static void onFurnitureMoved(FurnitureMovedEvent event) {
        if (event.newPosition != null) {
            Habbo habbo = event.habbo;
            HabboItem item = event.furniture;
            if (item != null && habbo != null &&
                    (habbo.getHabboStats()).cache.containsKey(SetStateCommand.SET_STATE_KEY)) {
                if (!item.getBaseItem().getInteractionType().getName().equals("dice") &&
                        !item.getBaseItem().getInteractionType().getName().equals("background_toner") &&
                        !item.getBaseItem().getInteractionType().getName().equals("dimmer") &&
                        !item.getBaseItem().getInteractionType().getName().equals("trophy") &&
                        !item.getBaseItem().getInteractionType().getName().equals("badge_display") &&
                        !item.getBaseItem().getInteractionType().getName().equals("vendingmachine")) {
                    Emulator.getThreading().run(() -> {
                        item.setExtradata((String)(habbo.getHabboStats()).cache.get(SetStateCommand.SET_STATE_KEY));
                        item.needsUpdate(true);
                        habbo.getHabboInfo().getCurrentRoom().updateItem(item);
                    });
                } else {
                    (habbo.getHabboStats()).cache.remove(SetStateCommand.SET_STATE_KEY);
                }
            }
        }
    }
}
