package be.nadtum.jobs.Action;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Objects;

public class Fish implements Listener {

    @EventHandler
    public void PlayerFishEntity(PlayerFishEvent event){

        Player player = event.getPlayer();
        String caught = Objects.requireNonNull(event.getCaught()).getName();
        Integer expToDrop =  event.getExpToDrop();

    }

}
