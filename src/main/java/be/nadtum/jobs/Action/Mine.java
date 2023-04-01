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

public class Mine implements Listener {

    @EventHandler
    public void PlayerBreakBlock(BlockBreakEvent event) throws SQLException {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Integer expToDrop = event.getExpToDrop();

        Statement stmt = ConnectionBuilder.getConnection().createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM `PROFIL` WHERE `UUID` = '" + player.getUniqueId() + "'");
        if(!result.next()){
            stmt.executeUpdate("INSERT INTO `PROFIL`(`UUID`) " + "VALUES ('" + event.getPlayer().getUniqueId() + "')");
        }



        result.close();
        stmt.close();
    }

}
