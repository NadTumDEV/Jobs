package be.nadtum.jobs.Passif;

import org.bukkit.entity.Player;

public class Passif {

    public static void selectJob(String job, String ability_1, String ability_2, String ability_3, Player player){

        switch (job) {
            case "MINER":
                Miner.strength(player);
                break;
            case "HUNTER":
                System.out.println("2");
                break;
            case "WOODHAMMER":
                System.out.println("3");
                break;
        }
    }

}
