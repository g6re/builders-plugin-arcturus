package com.eu.habbo.builders.utils;

import com.eu.habbo.Emulator;
import com.eu.habbo.builders.commands.BuildHeightCommand;
import com.eu.habbo.builders.commands.SetRotationCommand;
import com.eu.habbo.builders.commands.SetStateCommand;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Extras {
    public static void loadTexts() {
        Emulator.getTexts().register("commands.description.cmd_setstate", ":ss");
        Emulator.getTexts().register("be.cmd_setstate.keys", "ss;setstate");
        Emulator.getTexts().register("be.cmd_setstate.changed", "Changed state to %extra_data%");
        Emulator.getTexts().register("be.cmd_setstate.disabled", "state removed.");
        Emulator.getTexts().register("be.cmd_setstate.not_specified", "No state set. state must be between 0 - 100.");
        Emulator.getTexts().register("commands.description.cmd_buildheight", ":buildheight [height]");
        Emulator.getTexts().register("be.cmd_buildheight.keys", "buildheight;bh;setz");
        Emulator.getTexts().register("be.cmd_buildheight.invalid_height", "Invalid height! Build height must be between 0 - 40!");
        Emulator.getTexts().register("be.cmd_buildheight.changed", "Changed build height to %height%");
        Emulator.getTexts().register("be.cmd_buildheight.disabled", "Build height removed.");
        Emulator.getTexts().register("be.cmd_buildheight.not_specified", "No buildheight set. Height must be between 0 - 40.");
        Emulator.getTexts().register("commands.description.cmd_setrotation", ":rot;rotation");
        Emulator.getTexts().register("be.cmd_setrotation.keys", "rot;setrotation");
        Emulator.getTexts().register("be.cmd_setrotation.changed", "Changed Furni Rotation to %rot%");
        Emulator.getTexts().register("be.cmd_setrotation.disabled", "Furni Rotation removed.");
        Emulator.getTexts().register("be.cmd_setrotation.not_specified", "No rotation set. state must be between 0 - 6.");
    }

    public static void loadPlayerCommands() {
        try {
            CommandHandler.addCommand((Command)new BuildHeightCommand("cmd_buildheight", Emulator.getTexts().getValue("cmd_buildheight.keys").split(";")));
            CommandHandler.addCommand((Command)new SetStateCommand("cmd_setstate", Emulator.getTexts().getValue("be.cmd_setstate.keys").split(";")));
            CommandHandler.addCommand((Command)new SetRotationCommand("cmd_setrotation", Emulator.getTexts().getValue("be.cmd_setrotation.keys").split(";")));
        } catch (Exception ex) {
            Emulator.getLogging().logErrorLine(ex);
        }
    }

    private static boolean registerPermission(String name, boolean defaultReturn) {
        try {
            Connection connection = Emulator.getDatabase().getDataSource().getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT `column_name` FROM INFORMATION_SCHEMA.COLUMNS WHERE `table_name` = 'permissions' AND `column_name` = '" + name + "'");
                try {
                    if (!statement.executeQuery().next()) {
                        PreparedStatement statement_ = connection.prepareStatement("ALTER TABLE `permissions` ADD `" + name + "` ENUM('0', '1', '2') NOT NULL DEFAULT '1'");
                        try {
                            statement_.execute();
                            boolean bool1 = true;
                            if (statement_ != null)
                                statement_.close();
                            if (statement != null)
                                statement.close();
                            if (connection != null)
                                connection.close();
                            return bool1;
                        } catch (Throwable throwable) {
                            if (statement_ != null)
                                try {
                                    statement_.close();
                                } catch (Throwable throwable1) {
                                    throwable.addSuppressed(throwable1);
                                }
                            throw throwable;
                        }
                    }
                    boolean bool = true;
                    if (statement != null)
                        statement.close();
                    if (connection != null)
                        connection.close();
                    return bool;
                } catch (Throwable throwable) {
                    if (statement != null)
                        try {
                            statement.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    throw throwable;
                }
            } catch (Throwable throwable) {
                if (connection != null)
                    try {
                        connection.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                throw throwable;
            }
        } catch (SQLException sql) {
            Emulator.getLogging().logErrorLine(sql);
            return defaultReturn;
        }
    }

    public static void checkDatabase() {
        boolean reloadPermissions = false;
        reloadPermissions = registerPermission("cmd_setstate", reloadPermissions);
        reloadPermissions = registerPermission("cmd_buildheight", reloadPermissions);
        reloadPermissions = registerPermission("cmd_setrotation", reloadPermissions);
        if (reloadPermissions)
            Emulator.getGameEnvironment().getPermissionsManager().reload();
    }
}
