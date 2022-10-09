package io.github.devcomi.mcvot_plugin;

import io.github.devcomi.mcvot_plugin.Commands.Verify;
import io.github.devcomi.mcvot_plugin.Database.MySQL;
import io.github.devcomi.mcvot_plugin.Database.SQLUser;
import io.github.devcomi.mcvot_plugin.Database.SQLVerified;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class MCVot extends JavaPlugin {

    private Logger logger;

    private MySQL SQL;
    private SQLUser UserData;
    private SQLVerified VerifiedData;

    @Override
    public void onEnable() {
        this.logger = getLogger();
        this.SQL = new MySQL(this);

        this.saveDefaultConfig();

        new Verify(this);

        try {
            this.SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            this.getServer().getPluginManager().disablePlugin(this);
            this.logger.info("데이터베이스에 연결 할 수 없습니다.");
        }

        if (SQL.isConnected()) {
            this.logger.info("데이터베이스에 연결 되었습니다.");

            try {
                Statement statement = this.SQL.getConnection().createStatement();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            statement.execute("SELECT 1");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }.runTaskTimer(this, 20L, 60*20L);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            UserData = new SQLUser(this);
            VerifiedData = new SQLVerified(this);
        }
    }

    @Override
    public void onDisable() {
        this.SQL.disconnect();
    }

    public MySQL getSQL() {
        return SQL;
    }

    public SQLUser getUserData() {
        return UserData;
    }

    public SQLVerified getVerifiedData() {
        return VerifiedData;
    }
}
