package Skills;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import PvpBalance.PvpHandler;

public class Incapacitate {
	public static void Incapacitate(Player damagee, Player damager){
		double d = 3.5;
		PotionEffect potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 400, 3);
		PotionEffect potionEffect2 = new PotionEffect(PotionEffectType.BLINDNESS, 50, 4);
		PotionEffect potionEffect3 = new PotionEffect(PotionEffectType.SLOW, 300, 3);
		damagee.addPotionEffect(potionEffect);
		damagee.addPotionEffect(potionEffect2);
		damagee.addPotionEffect(potionEffect3);
		damager.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOUR INCAPACITATED " + damagee.getDisplayName());
		damagee.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU HAVE BEEN INCAPACITATED BY " + damager.getDisplayName());
        float hForce = 15 / 10.0F;
        float vForce = 12 / 10.0F;
	}

}
