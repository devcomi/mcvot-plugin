package io.github.devcomi.mcvot_plugin.Database;

import io.github.devcomi.mcvot_plugin.MCVot;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLUser {

    private MCVot plugin;

    public SQLUser(MCVot plugin) {
        this.plugin = plugin;
    }

    public void createUser(Player player, String PIN, int date) {
        try {
            if (!user_exists(player.getUniqueId())) {
                PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("INSERT IGNORE INTO pending_verify" +
                        " (UUID,NAME,PIN,DATE) VALUES (?,?,?,?)");
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, player.getName());
                ps.setString(3, PIN);
                ps.setInt(4, date);
                ps.executeUpdate();

                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean user_exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM pending_verify WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveUserData(UUID uuid, int PIN, int date) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("UPDATE pending_verify SET UUID=?, PIN=?, DATE=?");
            ps.setString(1, uuid.toString());
            ps.setInt(2, PIN);
            ps.setInt(3, date);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object getData(UUID uuid, String name) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT " + name +  " FROM pending_verify WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            Object data;
            if (rs.next()) {
                data = rs.getObject(name);
                return data;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
