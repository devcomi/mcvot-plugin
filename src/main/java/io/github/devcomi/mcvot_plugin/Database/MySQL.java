package io.github.devcomi.mcvot_plugin.Database;

import io.github.devcomi.mcvot_plugin.MCVot;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private MCVot main;

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    private Connection connection;

    public MySQL(MCVot main) {
        this.main = main;

        FileConfiguration config = main.getConfig();

        this.host = config.getString("host");
        this.port = config.getString("port");
        this.database = config.getString("database");
        this.username = config.getString("username");
        this.password = config.getString("password");
    }

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false" + "&autoReconnect=true",
                    username, password);
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
