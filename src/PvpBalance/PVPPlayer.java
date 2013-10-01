package PvpBalance;

import java.util.Date;

import me.frodenkvist.scoreboardmanager.SMHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import Event.PBEntityRegainHealthEvent;
import Party.Invite;
import Party.Party;


public class PVPPlayer
{
	private Player player;
	private double health;
	private double stamina;
	private double healthLastTick;
	private double maxHealth;
	private double maxStamina;
	private double cooldown;
	private int hitCoolDown;
	private int skillCoolDown;
	private int combatCoolDown;
	private int hunger;
	private int lastDamage;
	private int armorEventLastTick;
	private int healthPercent;
	private boolean inCombat;
	private boolean isDead;
	private boolean canRegen;
	private boolean god;
	private boolean pvpstats;
	private boolean colorUp;
	private boolean canUseSkill;
	private long defencePotionTimer = 0L;
	private long offencePotionTimer = 0L;
	private long pvpTimer = 0L;
	private long portalTimer = 0L;
	private Party party;
	private Invite invite;
	private Party ghostParty;
	private boolean partyChat;
	
	public PVPPlayer(Player player)
	{
		this.player = player;
		this.health = 500;
		this.canRegen = true;
		this.healthLastTick = 500;
		this.maxHealth = 500;
		this.maxStamina = 100;
		this.stamina = 100;
		this.skillCoolDown = 0;
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
	public double getStamina()
	{
		return this.stamina;
	}
	public double getMaxStamina()
	{
		return this.maxStamina;
	}
	public void setStamina(int stamina)
	{
		this.stamina = stamina;
	}
	public void setMaxStamina(int maxStamina)
	{
		this.maxStamina = maxStamina;
	}
	public boolean canUseSkill()
	{
		return this.canUseSkill;
	}
	public int getSkillCooldown()
	{
		return this.skillCoolDown;
	}
	public void setSkillCooldown(int skillCoolDown)
	{
		this.skillCoolDown = skillCoolDown;
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
	
	public int getLastDamage()
	{
		return lastDamage;
	}
	
	public void setLastDamage(int lastDamage)
	{
		this.lastDamage = lastDamage;
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
	public void setCanUseSkill(boolean canUseSkill)
	{
		this.canUseSkill = canUseSkill;
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
				this.health = this.maxHealth;
			}
			else
			{
				this.health = health;
			}
			if(!this.isDead)
			{
				this.setProperHealth();
			}
		}
		
	}
	
	public void uncheckedDamage(int dealtDamage)
	{
		if(player.getGameMode().equals(GameMode.SURVIVAL) && !this.god)
		{
			this.sethealth(health - dealtDamage);
			if(healthLastTick > health)
			{
				if(this.combatCoolDown < 10)
				{
					this.combatCoolDown = this.combatCoolDown + 5;
				}
			}
		}
		else
		{
			this.sethealth(this.maxHealth);
			player.setFoodLevel(20);
		}
	}
	
	public boolean damage(int dealtDamage)
	{
		if(this.player.getNoDamageTicks() <= 0)
		{
			this.lastDamage = dealtDamage;
			this.sethealth(health - dealtDamage);
		}
		else if(this.player.getNoDamageTicks() <= 10 && this.player.getNoDamageTicks() >= 1)
		{
			if(this.lastDamage < dealtDamage)
			{
				this.lastDamage = dealtDamage;
				this.sethealth(health - (dealtDamage - this.lastDamage));
			}
			else
			{
				return false;
			}
		}
		if(player.getGameMode().equals(GameMode.SURVIVAL) && !this.god)
		{
			//this.sethealth(health - dealtDamage);
			if(healthLastTick > health)
			{
				if(this.combatCoolDown < 10)
				{
					this.combatCoolDown += 5;
				}
			}
		}
		else
		{
			this.sethealth(this.maxHealth);
			player.setFoodLevel(20);
		}
		return true;
	}
	
	public void tick()
	{
		if(player.getPlayer().getAllowFlight() == false && player.getWorld().getName().contains("world") && this.skillCoolDown == 0){
			player.setAllowFlight(true);
		}
		if(player.isFlying() == true && player.getGameMode() == GameMode.SURVIVAL && player.getWorld().getName().contains("world")){
			player.setAllowFlight(false);
			player.setFlying(false);
		}
		if(this.player.getFoodLevel() < 1 && this.health > 100)
		{
			this.sethealth(health - 10);
		}
		else if(this.player.getFoodLevel() < 1 && this.health <= 100)
		{
			this.combatCoolDown = 20;
		}
		if(this.combatCoolDown > 0)
		{
			--this.combatCoolDown;
		}
		else if(this.combatCoolDown < 0)
		{
			this.combatCoolDown = 0;
		}
		if(this.cooldown > 0)
		{
			--this.cooldown;
		}
		else if(this.cooldown < 0)
		{
			this.cooldown = 0;
		}
		if(this.hitCoolDown > 0)
		{
			--this.hitCoolDown;
		}
		else if(this.hitCoolDown < 0)
		{
			this.hitCoolDown = 0;
		}
		if(this.skillCoolDown > 0)
		{
			--this.skillCoolDown;
			if(this.canUseSkill == true){
				this.setCanUseSkill(false);
			}
		}
		else if(this.skillCoolDown == 0){
			if(this.canUseSkill == false)
			{
				this.setCanUseSkill(true);
			}
		}
		else if(this.skillCoolDown < 0)
		{
			this.skillCoolDown = 0;
		}
		
		String message = ("SIDEBAR,Health," + ChatColor.BLUE + "Health:" + ChatColor.RESET + "," + (int)this.health);
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message.getBytes());
		String message2 = ("SIDEBAR,Health," + ChatColor.YELLOW + "Stamina:" + ChatColor.RESET + "," + (int)this.stamina);
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message2.getBytes());
		String message3 = ("SIDEBAR,Health," + ChatColor.GREEN + "Till Regen:" + ChatColor.RESET + "," + ((int)this.combatCoolDown));
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message3.getBytes());
		String message4 = ("SIDEBAR,Health," + ChatColor.RED + "Till Skill:" + ChatColor.RESET + "," + ((int)this.skillCoolDown));
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message4.getBytes());
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
			return;
		}
		if(this.armorEventLastTick > 0)
		{
			this.armorEventLastTick = 0;
			Damage.calcArmor(player);
		}
		if(player.getHealth() <= 0)
		{
			this.health = 0;
		}
		if(this.health <= 0 && this.isDead == false)
		{

			if(player.getFireTicks() > 0)
			{
				player.setFireTicks(0);
			}
			player.getActivePotionEffects().removeAll(player.getActivePotionEffects());
			this.player.setHealth(0f);
			this.isDead = true;
			this.combatCoolDown = 0;
			this.hitCoolDown = 0;
		}
		if(this.combatCoolDown > 0 || this.isDead == true || this.player.getFoodLevel() < 1)
		{
			this.canRegen = false;
		}
		else
		{
			canRegen = true;
		}
		if(combatCoolDown < 1 && this.inCombat == true)
		{
			{
				inCombat = false;
			}
		}
		else if(combatCoolDown >= 1 && !inCombat)
		{
			inCombat = true;
		}
		if(health < maxHealth && this.canRegen)
		{
			if(this.inCombat == true)
			{
				int heal = 45;
				PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(player, heal, RegainReason.CUSTOM);
				Bukkit.getPluginManager().callEvent(pberh);
				if(pberh.isCancelled())
					return;
				this.sethealth(health + heal);
			}
			else
			{
				int heal = 64;
				PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(player, heal, RegainReason.CUSTOM);
				Bukkit.getPluginManager().callEvent(pberh);
				if(pberh.isCancelled())
					return;
				this.sethealth(health + heal);
			}
		}
		if(this.canRegen == true && this.getStamina() < this.getMaxStamina()){
			this.setStamina((int)this.getStamina() + 1);
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
			SMHandler.setHealthBar((health / maxHealth), player);
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
	}
	
	public void update()
	{
		String message = ("SIDEBAR,Health," + ChatColor.BLUE + "Health:" + ChatColor.RESET + "," + (int)this.health);
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message.getBytes());
		String message2 = ("SIDEBAR,Health," + ChatColor.GREEN + "Till Regen:" + ChatColor.RESET + "," + ((int)this.combatCoolDown/4));
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message2.getBytes());
		SMHandler.setHealthBar((health / maxHealth), player);
	}
	
	public void setDefencePotionTimer(long time)
	{
		this.defencePotionTimer = time;
	}
	 
	public void setOffencePotionTimer(long time)
	{
		this.offencePotionTimer = time;
	}
	   
	public void setPortalTimer(long time)
	{
		this.portalTimer= time;
	}
	   
	public long getPortalTimer()
	{
		return this.portalTimer;
	}
	 
	public long getOffencePotionTimer()
	{
		return this.offencePotionTimer;
	}
	 
	public long getDefencePotionTimer()
	{
		return this.defencePotionTimer;
	}
	 
	public String getName()
	{
		return this.player.getName();
	}
	 
	public long getPvpTimer()
	{
		return this.pvpTimer;
	}
	 
	public void setPvpTimer(long timer)
	{
		this.pvpTimer = timer;
	}
	 
	public boolean isInPVP()
	{
		Date d = new Date();
		if((d.getTime() - this.pvpTimer) / 1000L <= PvpHandler.getPvpTimer())
		{
			return true;
		}
		return false;
	}
	 
	public boolean isInParty()
	{
		if(this.party == null)
		{
			return false;
		}
		return true;
	}
	 
	public boolean isLeader()
	{
		if (this.party == null)
			return false;
		return this.party.isLeader(this);
	}
	 
	public void sendMessage(String s)
	{
		this.player.sendMessage(s);
	}
	 
	public Party getParty()
	{
		return this.party;
	}
	 
	public void setInvite(Invite invite)
	{
		this.invite = invite;
	}
	 
	public void setParty(Party p)
	{
		this.party = p;
	}
	 
	public boolean hasInvite()
	{
		return this.invite != null;
	}
	 
	public void sendInvite(PVPPlayer target)
	{
		Invite invite = new Invite(this);
		target.setInvite(invite);
		target.sendMessage(ChatColor.AQUA + this.player.getName() + ChatColor.GOLD + " Has Invited You To His Party");
		target.sendMessage(ChatColor.GOLD + "Type " + ChatColor.AQUA + "/party accept " + ChatColor.GOLD + " To Accept Or " + ChatColor.AQUA + "/party decline" + ChatColor.GOLD + " To Decline");
	}
	 
	public void accept()
	{
		if (this.invite != null)
		{
			this.invite.accept(this);
			this.invite = null;
		}
	}
	 
	public void decline()
	{
		if (this.invite != null)
		{
			this.invite.decline(this);
			this.invite = null;
		}
	}
	 
	public boolean canAccept()
	{
		if (this.invite.getParty() == null)
			return true;
		if (this.invite.isPartyFull())
			return false;
		return true;
	}
	 
	public boolean hasGhostParty()
	{
		return this.ghostParty != null;
	}
	 
	public void setGhostParty(Party p)
	{
		this.ghostParty = p;
	}
	 
	public boolean justLeft()
	{
		return this.ghostParty != null;
	}
	 
	public Party getGhostParty()
	{
		return this.ghostParty;
	}
	 
	public void setPartyChat(boolean value)
	{
		this.partyChat = value;
	}
	 
	public boolean usesPartyChat()
	{
		return this.partyChat;
	}
}
