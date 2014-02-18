package PvpBalance;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerParticles {
	public static void nextParticle(Player player){
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(player.hasPermission("pvpbalance.particles2")){
			pvp.setParticleEffect(getValidParticleString(pvp));
		}
		if(!(PvpBalance.particulating.contains(player))){
			PvpBalance.particulating.add(player);
		}
	}
	public static void incrimentParticleCount(Player player){
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(player.hasPermission("pvpbalance.particles2")){
			pvp.setParticleCount(getValidParticleCount(pvp));
		}
		if(!(PvpBalance.particulating.contains(player))){
			PvpBalance.particulating.add(player);
		}
	}
	public static void incrimentParticleSpeed(Player player){
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(player.hasPermission("pvpbalance.particles2")){
			pvp.setParticleSpeed(getValidParticleSpeed(pvp));
		}
		if(!(PvpBalance.particulating.contains(player))){
			PvpBalance.particulating.add(player);
		}
	}
	private static double getValidParticleSpeed(PVPPlayer pvp) {
		double current = pvp.getParticleSpeed();
		if(pvp.getParticleSpeed() < 0.16){
			current+=0.01;
		}
		if(pvp.getParticleSpeed() > 0.15){
			current = 0;
		}
		return current;
	}
	private static int getValidParticleCount(PVPPlayer pvp) {
		int current = pvp.getParticleCount();
		if(pvp.getParticleCount() < 31){
			current+=10;
		}
		if(pvp.getParticleCount() > 30){
			current = 0;
		}
		return current;
	}
	private static String getValidParticleString(PVPPlayer pvp){
		String currentParticleString = pvp.getParticleEffect();
		if(currentParticleString == ""){
			currentParticleString = "FIREWORKS_SPARK";
			pvp.setEffect(ParticleEffect.FIREWORKS_SPARK);
			pvp.setParticleCount(10);
			pvp.setParticleSpeed(0.01);
		}
		else if(currentParticleString == "FIREWORKS_SPARK"){
			currentParticleString = "TOWN_AURA";
			pvp.setEffect(ParticleEffect.TOWN_AURA);
		}
		else if(currentParticleString == "TOWN_AURA"){
			currentParticleString = "CRIT";
			pvp.setEffect(ParticleEffect.CRIT);
		}
		else if(currentParticleString == "CRIT"){
			currentParticleString = "MAGIC_CRIT";
			pvp.setEffect(ParticleEffect.MAGIC_CRIT);
		} 
		else if(currentParticleString == "MAGIC_CRIT"){
			currentParticleString = "MOB_SPELL";
			pvp.setEffect(ParticleEffect.MOB_SPELL);
		} 
		else if(currentParticleString == "MOB_SPELL"){
			currentParticleString = "MOB_SPELL_AMBIENT";
			pvp.setEffect(ParticleEffect.MOB_SPELL_AMBIENT);
		} 
		else if(currentParticleString == "MOB_SPELL_AMBIENT"){
			currentParticleString = "SPELL";
			pvp.setEffect(ParticleEffect.SPELL);
		}
		else if(currentParticleString == "SPELL"){
			currentParticleString = "INSTANT_SPELL";
			pvp.setEffect(ParticleEffect.INSTANT_SPELL);
		}
		else if(currentParticleString == "INSTANT_SPELL"){
			currentParticleString = "WITCH_MAGIC";
			pvp.setEffect(ParticleEffect.WITCH_MAGIC);
		}
		else if(currentParticleString == "WITCH_MAGIC"){
			currentParticleString = "NOTE";
			pvp.setEffect(ParticleEffect.NOTE);
		}
		else if(currentParticleString == "NOTE"){
			currentParticleString = "PORTAL";
			pvp.setEffect(ParticleEffect.PORTAL);
		}
		else if(currentParticleString == "PORTAL"){
			pvp.setEffect(ParticleEffect.ENCHANTMENT_TABLE);	
			currentParticleString = "ENCHANTMENT_TABLE";
		}
		else if(currentParticleString == "ENCHANTMENT_TABLE"){
			currentParticleString = "FLAME";
			pvp.setEffect(ParticleEffect.FLAME);
		}
		else if(currentParticleString == "FLAME"){
			currentParticleString = "LAVA";
			pvp.setEffect(ParticleEffect.LAVA);
		}
		else if(currentParticleString == "LAVA"){
			currentParticleString = "SPLASH";
			pvp.setEffect(ParticleEffect.SPLASH);
		}
		else if(currentParticleString == "SPLASH"){
			currentParticleString = "LARGE_SMOKE";
			pvp.setEffect(ParticleEffect.LARGE_SMOKE);
		}
		else if(currentParticleString == "LARGE_SMOKE"){
			currentParticleString = "CLOUD";
			pvp.setEffect(ParticleEffect.CLOUD);
		}
		else if(currentParticleString == "CLOUD"){
			currentParticleString = "RED_DUST";
			pvp.setEffect(ParticleEffect.RED_DUST);
		}
		else if(currentParticleString == "RED_DUST"){
			currentParticleString = "DRIP_WATER";
			pvp.setEffect(ParticleEffect.DRIP_WATER);
		}
		else if(currentParticleString == "DRIP_WATER"){
			currentParticleString = "DRIP_LAVA";
			pvp.setEffect(ParticleEffect.DRIP_LAVA);
		}
		else if(currentParticleString == "DRIP_LAVA"){
			currentParticleString = "HEART";
			pvp.setEffect(ParticleEffect.HEART);
		}
		else if(currentParticleString == "HEART"){
			currentParticleString = "ANGRY_VILLAGER";
			pvp.setEffect(ParticleEffect.ANGRY_VILLAGER);
		}
		else if(currentParticleString == "ANGRY_VILLAGER"){
			currentParticleString = "HAPPY_VILLAGER";
			pvp.setEffect(ParticleEffect.HAPPY_VILLAGER);
		}
		else if(currentParticleString == "HAPPY_VILLAGER"){
			currentParticleString = "";
			PvpBalance.particulating.remove(pvp.getPlayer());
		}
		return currentParticleString;
	}
	public static void incrimentParticleHeight(Player player) {
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(player.hasPermission("pvpbalance.particles2")){
			pvp.setParticleHeight(getValidParticleHeight(pvp));
		}
		if(!(PvpBalance.particulating.contains(player))){
			PvpBalance.particulating.add(player);
		}
	}
	private static double getValidParticleHeight(PVPPlayer pvp) {
		double current = pvp.getParticleHeight();
		if(pvp.getParticleHeight() < 5.1){
			current+=0.2;
		}
		if(pvp.getParticleHeight() > 5.0){
			current = 0;
		}
		return current;
	}
}
