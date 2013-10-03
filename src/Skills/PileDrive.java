package Skills;

import me.frodenkvist.armoreditor.FireworkEffectPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import PvpBalance.Damage;
import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class PileDrive {
	public static void pileDrive(Player damagee, Player damager){

		PVPPlayer pvp = PvpHandler.getPvpPlayer(damagee);
		PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
		if(pvpDamager.getStamina() >= 51)
		{
			Block aboveStanding = damagee.getLocation().getBlock();
			int x = aboveStanding.getX()/1;
			int y = (aboveStanding.getY()/1);
			int z = aboveStanding.getZ()/1;
			Location spot = new Location(damagee.getWorld(),x,y,z);
			PotionEffect potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 140, 2);
			PotionEffect potionEffect2 = new PotionEffect(PotionEffectType.BLINDNESS, 80, 2);
			PotionEffect potionEffect3 = new PotionEffect(PotionEffectType.SLOW, 100, 2);
			if(damagee.getEyeLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR)
			{
				damagee.teleport(spot);
			}
			damagee.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " " + damager.getName().toUpperCase() + " USED PILEDRIVE ON YOU!" );
			damagee.addPotionEffect(potionEffect);
			damagee.addPotionEffect(potionEffect2);
			damagee.addPotionEffect(potionEffect3);
			pvp.damage(Damage.calcDamage(damager) * 2);
			pvpDamager.setComboReady(0);
			pvpDamager.setCanUsePileDrive(false);
			pvpDamager.setStamina((int)pvpDamager.getStamina() - 51);
			damager.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU PILEDRIVE "+ damagee.getDisplayName().toUpperCase() + "!!!");
			FireworkEffectPlayer fireworkPlayer = new FireworkEffectPlayer();
			FireworkEffect bonk = FireworkEffect.builder().with(Type.BURST).withColor(Color.YELLOW).withColor(Color.BLACK).flicker(true).withFade(Color.YELLOW).build();
			try {
				fireworkPlayer.playFirework(damagee.getWorld(), damagee.getEyeLocation(), bonk);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}

	}
}