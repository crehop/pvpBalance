package PvpBalance;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;


public class PVPPlayer {
	private Player player;
	private int health;
	private int healthLastTick;
	private int maxHealth;
	private int cooldown;
	private int hitCoolDown;
	private int combatCoolDown;
	private int hunger;
	//private int hungerMax = 20;
	private int armorEventLastTick;
	boolean isInCombat;
	boolean isDead;
	boolean canRegen;
	boolean colorUpHelmet;
	boolean colorUpChest;
	boolean colorUpLeggings;
	boolean colorUpBoots;
	public PVPPlayer(Player player){
		this.player = player;
		this.health = 500;
		this.canRegen = true;
		this.healthLastTick = 500;
		this.maxHealth = 500;
		this.cooldown = 0;
		this.isDead = false;
		this.hitCoolDown = 0;
		this.hunger = player.getFoodLevel();
		this.isInCombat = false;
		this.combatCoolDown = 0;
		this.armorEventLastTick = 0;
		this.colorUpHelmet = true;
		this.colorUpChest = true;
		this.colorUpLeggings = true;
		this.colorUpBoots = true;
	}

	public Player getPlayer(){
		return this.player;
	}
	public int getCombatCoolDown(){
		return combatCoolDown;
	}
	public int getCooldown(){
		return cooldown;
	}
	public boolean isDead(){
		return isDead;
	}
	public int getHealthLastTick(){
		return this.healthLastTick;
	}
	public int getMaxHealth(){
		return this.maxHealth;
	}
	public int gethealth(){
		return this.health;
	}
	public int getHunger(){
		return this.hunger;
	}
	public int getHitCooldown(){
		return this.hitCoolDown;
	}
	public int getArmorEventLastTick(){
		return this.armorEventLastTick;
	}
	public void setArmorEventLastTick(int armorEventLastTick){
		this.armorEventLastTick = armorEventLastTick;
	}
	public void setCombatCoolDown(int combatCoolDown){
		this.combatCoolDown = combatCoolDown;
	}
	public void setHunger(int hunger){
		if(this.getHunger() - hunger < 1){
			this.hunger = 1;
		}
		else{
			this.hunger = hunger;
		}
		
	}
	public void setHitCoolDown(int hitCoolDown){
		this.hitCoolDown = hitCoolDown;
	}
	public void setCooldown(int cooldown){
		
		this.cooldown = cooldown;
	}
	public void setIsDead(boolean isDead){
		this.isDead = isDead;
	}
	public void setMaxHealth(int maxHealth){
		if(this.health == this.maxHealth){
			this.maxHealth = maxHealth;
			this.sethealth(this.maxHealth);
			if(this.armorEventLastTick == 1){
				player.sendMessage(ChatColor.GREEN + "[HEALTH]:" + ChatColor.YELLOW + " change in armor your new health is: " + ChatColor.GREEN + this.maxHealth);
			}
		}
		else{
			this.maxHealth = maxHealth;
			if(this.armorEventLastTick == 1){
				player.sendMessage(ChatColor.GREEN + "[HEALTH]:" + ChatColor.YELLOW + " change in armor your new health is: " + ChatColor.GREEN + this.maxHealth);
				player.sendMessage(ChatColor.GREEN + "[HEALTH]:" + ChatColor.RED + "Due to recent combat you will gain life to your new max");
			}

		}
	}
	public void sethealth(int health){
		if(player.getGameMode() == GameMode.SURVIVAL){
			if(health > this.maxHealth){
				health = this.maxHealth;
			}
			this.health = health;
			DBZScoreBoard.setScore(health, player);
			if(this.health <= 0){
				this.health = 0;
			}
			this.setProperHealth();
			}
	}
	
	/*public PVPPlayer getPVPPlayer()
	{
		return this;
	}*/
	
	public void Damage(int dmg)
	{
		if(player.getGameMode() == GameMode.SURVIVAL){
			sethealth(gethealth() - dmg);
			if(this.health <= 0 && this.isDead != true){
				this.player.setHealth(0);
				this.isDead = true;
			}
			if(healthLastTick > health){
				if(cooldown < 20){
					cooldown = 20;
				}
			}
		}
		else{
			this.health = this.maxHealth;
		}

	}
	public void tick(){
		if(player.getGameMode() == GameMode.CREATIVE){
			this.health = this.maxHealth;
			this.canRegen = true;
			this.healthLastTick = this.maxHealth;
			this.cooldown = 0;
			this.isDead = false;
			this.hitCoolDown = 10;
			this.isInCombat = false;
			this.combatCoolDown = 0;
			this.armorEventLastTick = 0;
		}
		if(this.armorEventLastTick > 0){
			this.armorEventLastTick--;
			Damage.calcArmor(player);
		}
		if(player.getHealth() <= 0){
			this.health = 0;
		}
		if(this.health <= 0 && this.isDead != true){
			this.player.setHealth(0);
			this.isDead = true;
		}
		setProperHealth();
		this.canRegen = true;
		if(cooldown > 0){
			this.canRegen = false;
			cooldown--;
		}
		if(cooldown > 0){
			this.canRegen = false;
			cooldown--;
		}
		if(player.getFoodLevel() == 0){
			this.canRegen = false;
		}
		this.hunger = this.player.getFoodLevel();
		if(this.hunger < 2 && this.health > 100){
			this.canRegen = false;
			this.sethealth(this.gethealth() - 10);
		}

		if(combatCoolDown > 0){
			if(isInCombat == false){
				isInCombat = true;
				player.sendMessage(ChatColor.RED + "WARNING: you have entered combat if you log out within the next "
						+ ChatColor.YELLOW + "= 20 Seconds =" + ChatColor.RED + " you will be automaticly killed and your loot will drop");
			
			}
			combatCoolDown--;
		}
		if(combatCoolDown <= 0){
			this.combatCoolDown = 0;
			if(isInCombat == true){
				isInCombat = false;
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You are no longer in combat and may log off safely");
			}
		}
		
		if(this.hitCoolDown > 0){
			this.hitCoolDown--;
		}
		if(Main.debug == true){
			Bukkit.broadcastMessage("Cooldown: " + cooldown + " this.canRegen : " + this.canRegen + " MaxHealth: " + maxHealth + " Health: " + health );
			Bukkit.broadcastMessage("HUNGER LEVEL" + hunger);
			Bukkit.broadcastMessage(""+player.getExhaustion());
			Bukkit.broadcastMessage("HITCoolDown:" + hitCoolDown);
			
		}
		if(health < maxHealth && this.canRegen == true){
			this.sethealth(health + 5);
			if(this.isInCombat == false){
				this.sethealth(health + 10);
			}
		}
		if(health > maxHealth){
			health = maxHealth;
		}
		healthLastTick = health;
	}
	
	public void setProperHealth()
	{
		if(this.isDead == false)
		{
			int realHealth = health/(maxHealth/20);
			if(realHealth <= 1){
				realHealth = 1;
			}
			if(realHealth > 20){
				realHealth = 20;
			}
			player.setHealth(realHealth);
		}
	}
}
