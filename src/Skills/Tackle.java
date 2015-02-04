package Skills;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.utils.CombatUtil;

import PvpBalance.Damage;
import PvpBalance.PVPPlayer;
import PvpBalance.PvpBalance;
import PvpBalance.PvpHandler;

public class Tackle {
	public static void tackle(Player player)
	{
		for(Entity near:player.getNearbyEntities(2, 2, 2)){
			if(near instanceof Player)
			{
				if(Damage.partyCanHit(near, player) == true && CombatUtil.preventDamageCall(PvpBalance.getTowny(), near,player) == false)
				near.setPassenger(player);
				PVPPlayer pvpDamagee = PvpHandler.getPvpPlayer((Player)near);
				Player tackled = (Player)near;
				pvpDamagee.setTackleTimer(10);
				pvpDamagee.damage(Damage.calcDamage(player) * 2);
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "" + "YOU TACKLE " + tackled.getDisplayName() + " CROUCH TO EXIT");
				((Player) near).sendMessage(ChatColor.RED  + "" + ChatColor.BOLD + "" + player.getDisplayName()+ " HAS TACKLED YOU!");
				break;
			}
		}
	}
}

