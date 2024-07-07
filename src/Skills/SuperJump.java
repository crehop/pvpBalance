package Skills;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import PvpBalance.Effects;
import PvpBalance.PvpHandler;

public class SuperJump {
	
	public static void Jump(Player player,double d){
	String doubleJump = "DOUBLE JUMP!";
        float hForce = 15 / 10.0F;
        float vForce = 12 / 10.0F;
        if (player.getGameMode() == GameMode.CREATIVE) {
            	return;
        }
        Vector direction = player.getLocation().getDirection();
        Vector forward = direction.multiply(3);
        if(PvpHandler.getPvpPlayer(player).getUsedSpeedSkill() == true) {
        	doubleJump = "SPRINTING DOUBLE JUMP!";
        	forward.multiply(2.5);
        }
        Vector v = player.getLocation().toVector().subtract(player.getLocation().add(0,3,0).toVector());
        v.add(forward);
        v.setY(5);
        v.normalize();
        v.multiply(hForce*d);
        v.setY(vForce*d);
        player.setVelocity(v);
		List<Player> list = SkillHandler.getPlayers(20, player);
		if (!list.isEmpty()) {
			for(Player lplayer:list){
				lplayer.playSound(lplayer.getLocation(), Sound.HORSE_JUMP, 3.0F, 0.533F);
			}
		}
		Effects.effectSuperJump(player);
		player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + doubleJump);
	}
}
