package com.rocket.rocketbot;

import com.rocket.rocketbot.accountSync.Authentication.AuthManager;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class RocketBot extends Plugin {

    private static RocketBot instance;

    @Getter private AuthManager authManager;

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static RocketBot getInstance() {
        if(instance == null)
            return new RocketBot();
        return instance;
    }
}
