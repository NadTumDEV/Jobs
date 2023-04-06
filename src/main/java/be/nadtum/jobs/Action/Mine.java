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

        Statement stmt = ConnectionBuilder.getConnection().createStatement();

        //create a profil player if not exist
        ResultSet result = stmt.executeQuery("SELECT * FROM `PROFIL` WHERE `UUID` = '" + player.getUniqueId() + "'");
        if(!result.next()){
            stmt.executeUpdate("INSERT INTO `PROFIL`(`UUID`) " + "VALUES ('" + player.getUniqueId() + "')");
        }

        //You must check that the block is present in the database
        result = stmt.executeQuery("SELECT * FROM `JOBS_BLOCKS_DATA` WHERE `BLOCK_NAME` = '" + block.getType().toString().toLowerCase() + "'");
        if(!result.next()){
            result.close();
            stmt.close();
            return;
        }
        //le métier qu'il devra avoir pour obtenir l'xp
        String job_name = result.getString("JOB_TARGET");
        //l'xp qu'il faudra ajouter au métier du joueur
        int gain_xp = result.getInt("GAIN_XP");

        ResultSet jobs_player_result = stmt.executeQuery("SELECT * FROM `JOBS_PLAYER_DATA` " +
                "WHERE `UUID` = '" + player.getUniqueId() + "' AND `JOB_NAME` = '" + job_name + "'");
        if(!jobs_player_result.next()){
            result.close();
            jobs_player_result.close();
            stmt.close();
            return;
        }

        //the xp that the player has before having the xp gain
        int current_xp = jobs_player_result.getInt("XP");
        int current_level = jobs_player_result.getInt("LEVEL");

        //n = k * (n^2) + b * n
        int xp_need_for_next_level = (int) (1.5 * 250 * (current_level + 1));
        if(xp_need_for_next_level <= current_xp + gain_xp){
            current_level++;
            current_xp = 0;
        }else{
            current_xp = current_xp + gain_xp;
        }

        stmt.executeUpdate("UPDATE `JOBS_PLAYER_DATA` SET `LEVEL`='" + current_level + "',`XP`='" + current_xp + "' WHERE `UUID` = '" + player.getUniqueId() + "' AND `JOB_NAME` = '" + job_name + "'");



        jobs_player_result.close();
        result.close();
        stmt.close();
    }

}
