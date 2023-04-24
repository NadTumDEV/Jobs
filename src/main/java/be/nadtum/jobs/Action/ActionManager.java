package be.nadtum.jobs.Action;

import be.nadtum.jobs.Builder.ConnectionBuilder;
import be.nadtum.jobs.Jobs;
import be.nadtum.jobs.Passif.Passif;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

public class ActionManager implements Listener {

    private final HashMap<UUID, BossBar> bossBarPlayer = new HashMap<>();

    //setup event
    @EventHandler
    public void BreakBlock(@NotNull BlockBreakEvent event) throws SQLException {
        scalingXp(event.getPlayer(), event.getBlock().getType().toString().toLowerCase(), "JOBS_BLOCKS_DATA", "BLOCK_NAME");
    }

    @EventHandler
    public void KillEntity(@NotNull EntityDeathEvent event) throws SQLException {
        scalingXp(event.getEntity().getKiller(), event.getEntity().toString().toLowerCase(), "JOBS_ENTITY_DATA", "ENTITY_NAME");
    }

    @EventHandler
    public void FishEntity(@NotNull PlayerFishEvent event) throws SQLException {
        scalingXp(event.getPlayer(), event.getCaught().getName().toLowerCase(), "JOBS_ENTITY_DATA", "ENTITY_NAME");
    }

    private void scalingXp(Player player, String target_ressource, String tabTarget, String typeTarget) throws SQLException {

        Statement stmt;
        try {
            stmt = ConnectionBuilder.getConnection().createStatement();
        } catch (SQLException e) {
            ConnectionBuilder.setupConnection(Jobs.getINSTANCE().getConfig());
            Bukkit.getLogger().severe("La connection a échoué, le lien vers la base de données est en cours");
            return;
        }

        //You must check that the block is present in the database
        ResultSet data_target = stmt.executeQuery("SELECT * FROM `"+ tabTarget +"` WHERE `" + typeTarget + "` = '" + target_ressource + "'");

        if(!data_target.next()){
            data_target.close();
            stmt.close();
            return;
        }
        //le métier qu'il devra avoir pour obtenir l'xp
        String job_name = data_target.getString("JOB_TARGET");
        //l'xp qu'il faudra ajouter au métier du joueur
        int gain_xp = data_target.getInt("GAIN_XP");

        ResultSet jobs_player_result = stmt.executeQuery("SELECT * FROM `JOBS_PLAYER_DATA` " +
                "WHERE `UUID` = '" + player.getUniqueId() + "' AND `JOB_NAME` = '" + job_name + "'");
        if(!jobs_player_result.next()){
            data_target.close();
            jobs_player_result.close();
            stmt.close();
            return;
        }

        //the xp that the player has before having the xp gain
        int current_xp = jobs_player_result.getInt("XP");
        int current_level = jobs_player_result.getInt("LEVEL");

        //n = k * (n^2) + b * n
        int xp_need_for_next_level = (int) (1.5 * 100 * (current_level + 1));
        if(xp_need_for_next_level <= current_xp + gain_xp){
            current_level++;
            current_xp = 0;
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1, 1);
        }else{
            current_xp = current_xp + gain_xp;
        }

        //on met à jour les données d'xp du joueur
        stmt.executeUpdate("UPDATE `JOBS_PLAYER_DATA` SET `LEVEL`='" + current_level + "',`XP`='" + current_xp + "' WHERE `UUID` = '" + player.getUniqueId() + "' AND `JOB_NAME` = '" + job_name + "'");


        //on affiche l'avancement du joueur via le bossBar
        if(bossBarPlayer.containsKey(player.getUniqueId())){
            bossBarPlayer.get(player.getUniqueId()).removePlayer(player);
        }
        BossBar bossBar = Bukkit.createBossBar("§6Niveau §e" + current_level + " " + job_name + " §8: §7" + current_xp + "§8/§7" + xp_need_for_next_level + " §e[§6XP§e]", BarColor.BLUE, BarStyle.SOLID);
        bossBar.setProgress((double) current_xp / xp_need_for_next_level);

        bossBar.addPlayer(player);

        bossBarPlayer.put(player.getUniqueId(), bossBar);

        Bukkit.getScheduler ().runTaskLater (Jobs.getINSTANCE(), () -> bossBar.removePlayer(player), 60);

        //nous allons vérifier quel métier le joueur a via un switch et ainsi donner les différents passifs


        jobs_player_result.close();
        data_target.close();
        stmt.close();
    }

}
