package be.nadtum.jobs.Action;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Mine implements Listener {

    @EventHandler
    public void PlayerBreakBlock(BlockBreakEvent event){

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Integer expToDrop = event.getExpToDrop();

    }

}
