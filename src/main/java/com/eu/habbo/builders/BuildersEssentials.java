package com.eu.habbo.builders;

import com.eu.habbo.Emulator;
import com.eu.habbo.builders.events.EmulatorLoad;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;

public class BuildersEssentials extends HabboPlugin implements EventListener {
    public static BuildersEssentials INSTANCE = null;

    public void onEnable() {
        INSTANCE = this;
        Emulator.getPluginManager().registerEvents(this, (EventListener)new EmulatorLoad());
    }

    public void onDisable() {}

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Don't run this separately");
    }
}
