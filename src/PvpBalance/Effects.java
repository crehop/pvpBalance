package PvpBalance;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

//Will house effect methods
public class Effects {
	Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers();
	public void effectCoordinator(){
	}
	public static void igniteFirePlayers(Player player){
		try {
			ParticleEffect.sendToLocation(ParticleEffect.FLAME, player.getLocation().add(0, 1, 0),0.2f,0.2f,0.2f, (float)0.02, 60);
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void effectPoison(Player player){	
		try {
			ParticleEffect.sendCrackToPlayer(true, 295, (byte) 0, player, player.getLocation().add(0, 1.3, 0), 0.2f, 0.2f, 0.2f, 100);
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect poisonPlayer!");
		}
	}//Particle.playParticle("iconcrack_295", player.getLocation().add(0, 1, 0), 0.35f, 0.05f, 30);	
	public static void effectBlind(Player player){
		try {
			ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, player.getLocation().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 200);
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect blindPlayer!");
		}
	}
	public static void effectWither(Player player){
		try {
			ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, player.getLocation().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 200);
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect witherPlayer!");
		}	
	}
	public static void effectSharpnessPlayers(Player player){
		float enchantsP = 0;
		float enchantsO = 0;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK)*2;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT)*2;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_INFINITE)*3;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_FIRE)*2;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
		float amount = enchantsO + enchantsP;
		try {
			ParticleEffect.sendToLocation(ParticleEffect.PORTAL, player.getLocation().add(0, 0.2, 0),0.2f,0.2f,0.2f, (float)0.15f, (int)amount*5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void effectSprintPlayers(Player player, float speed, int amount){
		try {
			ParticleEffect.sendToLocation(ParticleEffect.FOOTSTEP, player.getLocation().add(0, 0.02, 0),0.2f,0f,0.2f, (float)0.15, 1);
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect sprintPlayer!");
		}
	}
	public static void effectSpeedPlayers(Player player, float speed, int amount){
		try {
			ParticleEffect.sendToLocation(ParticleEffect.SMOKE, player.getLocation(),0.2f,0.1f,0.2f, (float)0.03, 30);
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect speedPlayer!");
		}
	}	
	public static void effectHealthPlayers(Player player, float speed, int amount){
		try {
			ParticleEffect.sendToLocation(ParticleEffect.HEART, player.getLocation(),0f,1f,0f, (float)0.02, 10);
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect healthPlayer!");
		}
	}	
	public static void effectConfuse(Player player){
		try {
			ParticleEffect.sendToLocation(ParticleEffect.CRIT, player.getLocation(),0f,1f,0f, (float)0.02, 6);
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect confusePlayer!");
		}
	}
	public static void bleed(Player player){
		try {
			ParticleEffect.sendToLocation(ParticleEffect.DRIP_LAVA, player.getLocation(),0.35f,0.35f,0.35f, (float)0.02, 40);
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect bleedPlayer!");
		}
	}
	public static void superSaien(Player player){
		try{
			ParticleEffect.sendToLocation(ParticleEffect.FLAME, player.getLocation(),0.3f,0.3f,0.3f, (float)0.025, 70);
		}catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("Effect supersaienPlayer!");
		}
	}
	public static void admin(Player player){
		if(player.hasPermission("particle.admin")){
			try {
				ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, player.getLocation(),0.35f,0.35f,0.35f, (float)0.02, 100);
			} catch (Exception e) {
				e.printStackTrace();
				Main.logger.info("Effect adminPlayer!");
			}
		}
	}
}
