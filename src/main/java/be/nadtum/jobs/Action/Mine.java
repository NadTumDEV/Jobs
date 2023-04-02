package be.nadtum.jobs.Action;

import be.nadtum.jobs.Builder.ConnectionBuilder;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Mine implements Listener {

    private Random random;

    @EventHandler
    public void PlayerBreakBlock(BlockBreakEvent event) throws SQLException {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Integer expToDrop = event.getExpToDrop();

        Statement stmt = ConnectionBuilder.getConnection().createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM `PROFIL` WHERE `UUID` = '" + player.getUniqueId() + "'");

        //create a profil player if not exist
        if(!result.next()){
            stmt.executeUpdate("INSERT INTO `PROFIL`(`UUID`) " + "VALUES ('" + event.getPlayer().getUniqueId() + "')");
        }

        //You must check that the block is present in the database
        result = stmt.executeQuery("SELECT * FROM `DEPOT` WHERE `ITEMSTACK` = '" + block.getType() + "'");
        if(!result.next()){
            result.close();
            stmt.close();
            return;
        }

        result.close();
        stmt.close();
    }

}
