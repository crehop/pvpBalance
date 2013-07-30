package PvpBalance;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import Event.PBEntityRegainHealthEvent;


public class PVPPlayer
{
	private Player player;
	private double health;
	private double healthLastTick;
	private double maxHealth;
	private double cooldown;
	private int hitCoolDown;
	private int combatCoolDown;
	private int hunger;
	private int lastDamage;
	private int armorEventLastTick;
	private boolean inCombat;
	private boolean isDead;
	private boolean canRegen;
	private boolean god;
	private boolean pvpstats;
	private boolean colorUp;
	//private final double INVULNERABILITY_TIMER = 1D;
	
	public PVPPlayer(Player player)
	{
		this.player = player;
		this.health = 500;
		this.canRegen = true;
		this.healthLastTick = 500;
		this.maxHealth = 500;
		this.cooldown = 0;
		this.isDead = false;
		this.hitCoolDown = 0;
		this.hunger = player.getFoodLevel();
		this.inCombat = false;
		this.combatCoolDown = 0;
		this.armorEventLastTick = 0;
		this.lastDamage = 0;
		colorUp = false;
	}

	public Player getPlayer()
	{
		return this.player;
	}
	
	public double getCombatCoolDown()
	{
		return combatCoolDown;
	}
	
	public double getCooldown()
	{
		return cooldown;
	}
	
	public boolean isDead()
	{
		return isDead;
	}
	
	public boolean isColorUp()
	{
		return colorUp;
	}
	
	public double getHealthLastTick()
	{
		return this.healthLastTick;
	}
	
	public int getMaxHealth()
	{
		return (int)this.maxHealth;
	}
	
	public int gethealth()
	{
		return (int)this.health;
	}
	
	public int getHunger()
	{
		return this.hunger;
	}
	
	public boolean isGod()
	{
		return god;
	}
	
	public double getHitCooldown()
	{
		return this.hitCoolDown;
	}
	
	public int getArmorEventLastTick()
	{
		return this.armorEventLastTick;
	}
	
	public void setGod(boolean god)
	{
		this.god = god;
	}
	
	public void setArmorEventLastTick(int armorEventLastTick)
	{
		this.armorEventLastTick = armorEventLastTick;
	}
	
	public void setCombatCoolDown(int combatCoolDown)
	{
		this.combatCoolDown = combatCoolDown;
	}
	
	public void setHunger(int hunger)
	{
		if(this.getHunger() - hunger < 1)
		{
			this.hunger = 1;
		}
		else
		{
			this.hunger = hunger;
		}
		
	}
	
	public void setHitCoolDown(int hitCoolDown)
	{
		this.hitCoolDown = hitCoolDown;
	}
	
	public void setCooldown(double cooldown)
	{
		this.cooldown = cooldown;
	}
	
	public void setIsDead(boolean isDead)
	{
		this.isDead = isDead;
	}
	
	public void setColorUp(boolean value)
	{
		colorUp = value;
	}
	
	public void setMaxHealth(double maxHealth)
	{
		if(this.health == this.maxHealth && this.combatCoolDown < 1)
		{
			this.maxHealth = maxHealth;
			this.sethealth(this.maxHealth);
			if(this.armorEventLastTick == 1)
			{
				player.sendMessage(ChatColor.GREEN + "[HEALTH]:" + ChatColor.YELLOW + " change in armor your new health is: " + ChatColor.GREEN + this.maxHealth);
			}
		}
		else
		{
			this.maxHealth = maxHealth;
			if(this.armorEventLastTick == 1)
			{
				player.sendMessage(ChatColor.GREEN + "[HEALTH]:" + ChatColor.YELLOW + " change in armor your new health is: " + ChatColor.GREEN + this.maxHealth);
				player.sendMessage(ChatColor.GREEN + "[HEALTH]:" + ChatColor.RED + "Due to recent combat you will gain life to your new max");
			}

		}
	}
	
	public void sethealth(double health)
	{
		if(pvpstats)
		{
			if(this.health > health)
			{
				double decreasedBy = this.health - health;
				if(decreasedBy > 10)
				{
					player.sendMessage(ChatColor.YELLOW + "[HEALTH]: " + ChatColor.RED + "- " + decreasedBy);
				}
			}
			else if(this.health < health)
			{
				double increasedBy = health - this.health;
				if(increasedBy > 10)
				{
					player.sendMessage(ChatColor.YELLOW + "[HEALTH]: " + ChatColor.GREEN + "+ " + increasedBy);
				}
			}
		}
		if(this.player.getGameMode() == GameMode.SURVIVAL)
		{
			if(health > this.maxHealth)
			{
				health = this.maxHealth;
			}
			if(health < this.health)
			{
				this.health = health;
			}
			if(health > this.health)
			{
				this.health = health;
			}
			if (this.isDead != true){
				this.setProperHealth();
			}
			
		}
		
	}
	
	public void damage(int dealtDamage)
	{
		if(player.getGameMode().equals(GameMode.SURVIVAL) && !this.god)
		{
			if(this.player.getNoDamageTicks() <= 0)
			{
				this.lastDamage = dealtDamage;
				this.sethealth(health - dealtDamage);
			}
			else if(this.player.getNoDamageTicks() <= 10)
			{
				if(this.lastDamage < dealtDamage)
				{
					this.sethealth(health - (dealtDamage - this.lastDamage));
					lastDamage = dealtDamage;
				}
			}
			if(healthLastTick > health)
			{
				if(this.combatCoolDown < 40)
				{
					this.combatCoolDown = this.combatCoolDown + 20;
				}
			}
		}
		else
		{
			this.sethealth(this.maxHealth);
			player.setFoodLevel(20);
		}
	}
	
	/*public void Damage(int dealtDamage, EntityDamageEvent event)
	{
		if(event.getDamage() > 0)
		{
			event.setDamage(0D);
		}
		if(player.getNoDamageTicks() > 10)
		{
			event.setCancelled(true);
			return;
		}
		if(player.getGameMode().equals(GameMode.SURVIVAL) && !this.god)
		{
			this.sethealth(health - dealtDamage);
			if(healthLastTick > health)
			{
				if(this.combatCoolDown < 40)
				{
					this.combatCoolDown += 20;
				}
			}
		}
		else
		{
			this.sethealth(this.maxHealth);
			player.setFoodLevel(20);
		}
	}*/
	
	public void tick(){
		if(this.player.getFoodLevel() < 1 && this.health > 100)
		{
			this.sethealth(health - 10);
		}
		if(this.player.getFoodLevel() < 1 && this.health <= 100){
			this.combatCoolDown = 40;
		}
		if(this.combatCoolDown > 0){
			this.combatCoolDown--;
		}
		if(this.combatCoolDown < 0){
			this.combatCoolDown = 0;
		}
		if(this.cooldown > 0){
			this.cooldown--;
		}
		if(this.cooldown < 0){
			this.cooldown = 0;
		}
		if(this.hitCoolDown > 0){
			this.hitCoolDown--;
		}
		if(this.hitCoolDown < 0){
			this.hitCoolDown = 0;
		}
		String message = ("SIDEBAR,Health," + ChatColor.BLUE + "Health:" + ChatColor.RESET + "," + (int)this.health);
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message.getBytes());
		String message2 = ("SIDEBAR,Health," + ChatColor.GREEN + "Till Regen:" + ChatColor.RESET + "," + ((int)this.combatCoolDown/4));
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message2.getBytes());
		if(player.getGameMode() == GameMode.CREATIVE)
		{
			this.health = this.maxHealth;
			this.canRegen = true;
			this.healthLastTick = this.maxHealth;
			this.cooldown = 0;
			this.isDead = false;
			this.hitCoolDown = 0;
			this.inCombat = false;
			this.combatCoolDown = 0;
			this.armorEventLastTick = 0;
		}
		if(this.armorEventLastTick > 0)
		{
			this.armorEventLastTick--;
			Damage.calcArmor(player);
		}
		if(player.getHealth() <= 0)
		{
			this.health = 0;
		}
		if(this.health <= 0 && this.isDead == false)
		{

			if(player.getFireTicks() > 0){
				player.setFireTicks(0);
			}
			player.getActivePotionEffects().removeAll(player.getActivePotionEffects());
			this.player.setHealth(0f);
			this.isDead = true;
			this.combatCoolDown = 0;
			this.hitCoolDown = 0;
		}
		if(this.combatCoolDown > 0 || this.isDead == true)
		{
			this.canRegen = false;
		}
		else{
			canRegen = true;
		}
		if(combatCoolDown <= 1)
		{
			{
				inCombat = false;
//				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You are no longer in combat and may log off safely");
			}
		}
		else
		{
			if(!inCombat)
			{
				inCombat = true;
//				player.sendMessage(ChatColor.RED + "WARNING: you have entered combat if you log out within the next "
//						+ ChatColor.YELLOW + "= 20 Seconds =" + ChatColor.RED + " you will be automaticly killed and your loot will drop");
			}
		}
		if(PvpBalance.plugin.isDebug())
		{
		}
		if(health < maxHealth && this.canRegen)
		{
			if(inCombat)
			{
				int heal = 15;
				PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(player, heal, RegainReason.CUSTOM);
				Bukkit.getPluginManager().callEvent(pberh);
				if(pberh.isCancelled())
					return;
				this.sethealth(health + heal);
			}
			else
			{
				int heal = 25;
				PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(player, heal, RegainReason.CUSTOM);
				Bukkit.getPluginManager().callEvent(pberh);
				if(pberh.isCancelled())
					return;
				this.sethealth(health + heal);
			}
		}
		if(health > maxHealth)
		{
			health = maxHealth;
		}
		healthLastTick = health;
	}
	
	public void setProperHealth()
	{
		if(this.isDead == false)
		{
			double realHealth = health/(maxHealth/20);
			if(realHealth <= 1)
			{
				realHealth = 1;
			}
			if(realHealth > 20)
			{
				realHealth = 20;
			}
			player.setHealth(realHealth);
		}
	}
	
	public boolean isPvpstats()
	{
		return pvpstats;
	}

	public void setPvpstats(boolean value)
	{
		pvpstats = value;
	}

	public boolean isInCombat()
	{
		return inCombat;
	}

	public void setInCombat(boolean value)
	{
		inCombat = value;
	}

	public boolean canHit()
	{
		if(this.hitCoolDown > 0)
		{
				return false;
		}
		return true;
		//Date date = new Date();
		//boolean canhit = (date.getTime() / 1000) - lastDamage > INVULNERABILITY_TIMER ? true:false;
		//return canhit;
	}
}
