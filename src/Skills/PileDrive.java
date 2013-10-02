package Skills;

import me.frodenkvist.armoreditor.FireworkEffectPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class PileDrive {
	public static void pileDrive(Player damager, Player damagee){
		Block aboveStanding = damagee.getLocation().getBlock();
		BlockFace standingOn = aboveStanding.getFace(aboveStanding).DOWN;
		int x = standingOn.getModX();
		int y = standingOn.getModY();
		int z = standingOn.getModZ();
		Location spot = new Location(damagee.getWorld(),x,y,z);
		PotionEffect potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 100, 2);
		PotionEffect potionEffect2 = new PotionEffect(PotionEffectType.BLINDNESS, 60, 2);
		PotionEffect potionEffect3 = new PotionEffect(PotionEffectType.SLOW, 60, 2);
		if(damagee.getEyeLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR)
		{
			damagee.teleport(spot);
		}
		damagee.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU HAVE BEEN DRIVEN INTO THE GROUND BY " + damager.getName() + "'s PILEDRIVE!" );
		damagee.addPotionEffect(potionEffect);
		damagee.addPotionEffect(potionEffect2);
		damagee.addPotionEffect(potionEffect3);
		PVPPlayer pvp = PvpHandler.getPvpPlayer(damagee);
		PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
		pvp.damage(250);
		pvpDamager.setComboReady(0);
		pvpDamager.setStamina((int)pvpDamager.getStamina() - 50);
		damager.sendMessage(ChatColor.GREEN + "YOU PILEDRIVE "+ damagee + "!!!");
		FireworkEffectPlayer fireworkPlayer = new FireworkEffectPlayer();
		FireworkEffect bonk = FireworkEffect.builder().with(Type.BURST).withColor(Color.YELLOW).withColor(Color.BLACK).flicker(true).withFade(Color.YELLOW).build();
		try {
			fireworkPlayer.playFirework(damagee.getWorld(), damagee.getEyeLocation(), bonk);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}