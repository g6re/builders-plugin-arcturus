package com.eu.habbo.builders.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.builders.BuildersEssentials;
import com.eu.habbo.builders.room.SetBuildHeight;
import com.eu.habbo.builders.room.SetRotation;
import com.eu.habbo.builders.room.SetState;
import com.eu.habbo.builders.utils.Extras;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;

import java.io.IOException;

public class EmulatorLoad implements EventListener {
  @EventHandler
  public static void onEmulatorLoaded(EmulatorLoadedEvent event) throws IOException {
    Extras.loadPlayerCommands();
    Extras.loadTexts();
    Extras.checkDatabase();
    Emulator.getPluginManager().registerEvents((HabboPlugin)BuildersEssentials.INSTANCE, (EventListener)new SetState());
    Emulator.getPluginManager().registerEvents((HabboPlugin)BuildersEssentials.INSTANCE, (EventListener)new SetBuildHeight());
    Emulator.getPluginManager().registerEvents((HabboPlugin)BuildersEssentials.INSTANCE, (EventListener)new SetRotation());
    System.out.println("[OFFICIAL PLUGIN] Builders Essentials (1.0.0) has official loaded!");
  }
}
