package be.nadtum.jobs;

import be.nadtum.jobs.Action.ActionManager;
import org.bukkit.plugin.PluginManager;

public class Listeners {
    public Listeners(Jobs jobs) {

        PluginManager pm = jobs.getServer().getPluginManager();

        pm.registerEvents(new ActionManager(), jobs);
    }
}
