package be.nadtum.jobs;

import be.nadtum.jobs.Builder.ConnectionBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public final class Jobs extends JavaPlugin {

    private static Jobs INSTANCE;

    public static Jobs getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {

        //setup file config
        saveDefaultConfig();

        //setup connection to database
        ConnectionBuilder.setupConnection(getConfig());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
