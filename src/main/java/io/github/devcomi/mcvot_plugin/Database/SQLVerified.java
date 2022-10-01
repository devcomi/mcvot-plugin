package io.github.devcomi.mcvot_plugin.Database;

import io.github.devcomi.mcvot_plugin.MCVot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLVerified {

    private MCVot plugin;

    public SQLVerified(MCVot plugin) {
        this.plugin = plugin;
    }

    public void createUser(UUID uuid, int PIN, int date) {
        try {
            if (!user_exists(uuid)) {
                PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("INSERT IGNORE INTO users" +
                        " (UUID,PIN,DATE) VALUES (?,?,?)");
                ps.setString(1, uuid.toString());
                ps.setInt(2, PIN);
                ps.setInt(3, date);
                ps.executeUpdate();

                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean user_exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM users WHERE UUID=?");
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
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("UPDATE users SET UUID=?, PIN=?, DATE=?");
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
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT " + name +  " FROM users WHERE UUID=?");
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
