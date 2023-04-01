package be.nadtum.jobs.Builder;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBuilder {

    private static Connection connection;

    //methode
    public static void setupConnection(FileConfiguration configuration){

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + configuration.getString("database_data.address") + "/" + configuration.getString("database_data.nameBD"),
                    configuration.getString("database_data.user"),
                    configuration.getString("database_data.password"));
            Bukkit.getLogger().info("data connection is enable");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //commit
    }

    public static Connection getConnection() {
        return connection;
    }



}
