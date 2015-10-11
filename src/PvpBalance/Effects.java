package PvpBalance;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

//Will house effect methods
public class Effects
{
	Collection<? extends Player> onlinePlayerList = Bukkit.getServer().getOnlinePlayers();
	public static void healEffect(Player player)
	{
		try
		{
			ParticleEffect.HEART.display(0.3f, 0.62f, 0.3f, 0.65f, 10, player.getLocation().add(0, 1, 0), 150);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void teleportGreen(Player player)
	{
		try
		{
			ParticleEffect.VILLAGER_HAPPY.display(0.3f, 0.62f, 0.3f, 0.65f, 10, player.getLocation().add(0, 1, 0), 150);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void magicWhiteSwirls(Player player)
	{
		try
		{
			ParticleEffect.SPELL.display(0.2f, 0.2f, 0.2f, 0.02f, 10, player.getLocation(), 160);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	
	public static void igniteFirePlayers(Player player)
	{
		try
		{
			ParticleEffect.FLAME.display(0.2f, 0.2f, 0.2f, 0.02f, 10, player.getLocation().add(0, 1, 0), 60);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void igniteFireball(Entity object)
	{
		try
		{
			ParticleEffect.FLAME.display(0.1f, 0.1f, 0.1f, 0.04f, 10, object.getLocation().add(0.1f, 0.1f, 0.1f), 160);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFireball!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void blockedPlayer(Player player)
	{
		try
		{
			ParticleEffect.FLAME.display(0.1f, 0.1f, 0.1f, 0.04f, 10, player.getLocation().add(0.1f, 0.1f, 0.1f), 160);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void effectPoison(Player player)
	{	
		try
		{
			ParticleEffect.SPELL_WITCH.display(0.2f, 0.2f, 0.2f, 0.2f, 10, player.getLocation().add(0.1f, 0.1f, 0.1f), 100);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect poisonPlayer!");
		}
	}//Particle.playParticle("iconcrack_295", player.getLocation().add(0, 1, 0), 0.35f, 0.05f, 30);	
	
	public static void effectBlind(Player player)
	{
		try
		{
			ParticleEffect.TOWN_AURA.display(0.1f, 0.1f, 0.1f, 0.01f, 10, player.getLocation().add(0, 2f, 0f), 100);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect blindPlayer!");
		}
	}
	
	public static void effectSlow(Player player)
	{
		try
		{
			//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
			ParticleEffect.CLOUD.display(0.2f, -0.2f, 0.2f, 0.0001f, 10, player.getLocation().subtract(0, 0.1, 0), 100);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect SlowPlayer!");
		}
	}
	public static void effectSuperJump(Player player)
	{
		try
		{
			//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
			ParticleEffect.CLOUD.display(0.4f, -0.4f, 0.4f, 0.004f, 10, player.getLocation().subtract(0, 0.1, 0), 100);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect SlowPlayer!");
		}
	}
	public static void effectWither(Player player)
	{
		try
		{
			ParticleEffect.TOWN_AURA.display(0.2f, 0.5f, 0.2f, 0.02f, 10, player.getLocation().add(0, 1, 0), 200);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect witherPlayer!");
		}	
	}
	
	public static void effectSharpnessPlayers(Player player)
	{
		float enchantsO = 0;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK)*2;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT)*2;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_INFINITE)*3;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_FIRE)*2;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
		float amount = enchantsO;
		try
		{
			ParticleEffect.PORTAL.display(0.2f, 0.2f, 0.2f, 0.15f, (int) (amount*5), player.getLocation().add(0, 0.2, 0), 60);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void effectSprintPlayers(Player player, float speed, int amount)
	{
		try
		{
			ParticleEffect.FOOTSTEP.display(0.2f, 0f, 0.2f, 0.15f, 1, player.getLocation().add(0, 0.05f, 0), 50);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect sprintPlayer!");
		}
	}
	
	public static void effectSpeedPlayers(Player player, float speed, int amount)
	{
		try
		{
			ParticleEffect.SMOKE_NORMAL.display(0.3f, 0.2f, 0.3f, 0.03f, 10, player.getLocation(), 70);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect speedPlayer!");
		}
	}	
	
	public static void effectHealthPlayers(Player player, float speed, int amount)
	{
		try
		{
			ParticleEffect.HEART.display(0f, 0f, 0f, 0.01f, 10, player.getLocation().add(0, 2.2, 0), 3);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect healthPlayer!");
		}
	}	
	
	public static void effectConfuse(Player player)
	{
		try
		{
			ParticleEffect.CRIT.display(0f, 1f, 0.2f, 0.02f, 10, player.getLocation().add(0, 1, 0), 6);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect confusePlayer!");
		}
	}
	
	public static void bleed(Player player)
	{
		try
		{
			ParticleEffect.DRIP_LAVA.display(0.35f, 0.35f, 0.35f, 0.02f, 10, player.getLocation().add(0, 1, 0), 40);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect bleedPlayer!");
		}
	}
	
	public static void superSaien(Player player)
	{
		try
		{
			ParticleEffect.FLAME.display(0.3f, 0.3f, 0.3f, 0.025f, 10, player.getLocation().add(0, 1, 0), 70);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect supersaienPlayer!");
		}
	}
	
	public static void admin(Player player)
	{
		if(player.hasPermission("particle.admin"))
		{
			try
			{
				ParticleEffect.SPELL_WITCH.display(0.35f, 0.35f, 0.35f, 0.02f, 10, player.getLocation(), 100);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				PvpBalance.logger.info("Effect adminPlayer!");
			}
		}
	}
	public static void impactEffect(Location location){
		try
		{
			ParticleEffect.FLAME.display(0.3f, 0.3f, 0.3f, 0.3f, 10, location, 300);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
	}
}
