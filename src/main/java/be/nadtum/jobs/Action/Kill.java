package be.nadtum.jobs.Action;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Kill implements Listener {


    @EventHandler
    public void PlayerKillEntity(EntityDeathEvent event){

        Player killer = event.getEntity().getKiller();
        Entity entity = event.getEntity();


    }

}
