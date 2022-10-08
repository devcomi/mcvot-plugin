package io.github.devcomi.mcvot_plugin.Commands;

import io.github.devcomi.mcvot_plugin.MCVot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Verify implements CommandExecutor {

    public MCVot plugin;

    public Verify(MCVot plugin) {
        this.plugin = plugin;

        plugin.getCommand("verification").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("플레이어가 아닙니다.");
            return false;
        }

        Player player = (Player) sender;

//        if (this.plugin.getVerifiedData().user_exists(player.getUniqueId())) {
//            player.sendMessage("이미 인증이 완료되셨습니다!");
//            return true;
//        }

        if (this.plugin.getUserData().user_exists(player.getUniqueId())) {
            player.sendMessage("이미 인증을 하고 계십니다!");
            return true;
        }

        Random random = new Random();

        String pinText = "";
        for (int i=0; i<7; i++) {
            pinText += random.nextInt(9) + 1;
        }

        this.plugin.getUserData().createUser(player, pinText, (int) (System.currentTimeMillis()/1000));
        player.sendMessage("디스코드 서버에서 /verify 명령어를 통해 인증하세요!\n핀 : " + pinText);

        return true;
    }
}
