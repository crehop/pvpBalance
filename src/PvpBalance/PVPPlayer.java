package PvpBalance;

import java.util.Date;

import me.frodenkvist.scoreboardmanager.SMHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Event.PBEntityRegainHealthEvent;
import Party.Invite;
import Party.Party;
import Skills.SuperSpeed;


public class PVPPlayer
{
	private Player player;
	private double health;
	private double stamina;
	private double healthLastTick;
	private double maxHealth;
	private double maxStamina;
	private double cooldown;
	private double healthPercent;
	private Arrow grappleArrow;
	private Location grappleStart;
	private Location grappleEnd;
	private Player hitByGrapple;
	private int comboReady;
	private int hitCoolDown;
	private int skillCoolDown;
	private int combatCoolDown;
	private int hunger;
	private int stun;
	private int lastDamage;
	private int armorEventLastTick;
	private int wasSprinting;
	private int tackleTimer;
	private boolean canUseGrappleShot;
	private boolean isUsingGrappleShot;
	private boolean canUsePileDrive;
	private boolean inCombat;
	private boolean isDead;
	private boolean canRegen;
	private boolean god;
	private boolean pvpstats;
	private boolean colorUp;
	private boolean canUseSkill; 
	private boolean firstToggle;
	private boolean newbZone;
	private long defencePotionTimer = 0L;
	private long offencePotionTimer = 0L;
	private long pvpTimer = 0L;
	private long portalTimer = 0L;
	private Party party;
	private Invite invite;
	private Party ghostParty;
	private boolean partyChat;
	private boolean isStunned;
	private boolean usedSpeedSkill;
	public boolean flyZone;
	
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
		this.wasSprinting = 0;
		this.usedSpeedSkill = false;
		this.isStunned = false;
		this.healthPercent = 100.0;
		this.comboReady = 0;
		colorUp = false;
		this.flyZone = false;
	}
	public int tackleTimer()
	{
		return this.tackleTimer;
	}
	public void setTackleTimer(int tackleTimer)
	{
		if(tackleTimer < 0)
		{
			this.tackleTimer = 0;
		}
		else
		{
			this.tackleTimer = tackleTimer;
		}
	}
	public boolean isUsingGrappleShot()
	{
		return this.isUsingGrappleShot;
	}
	public void setIsUsingGrappleShot(boolean isUsingGrappleShot)
	{
		this.isUsingGrappleShot = isUsingGrappleShot;
	}
	public Player getPlayerHitByGrapple()
	{
		return this.hitByGrapple;
	}
	public void setGrapplePlayer(Player player)
	{
		this.hitByGrapple = player;
	}
	public Arrow getGrappleArrow()
	{
		return this.grappleArrow;
	}
	public void setGrappleArrow(Arrow grappleArrow)
	{
		this.grappleArrow = grappleArrow;
	}
	public void setCanUseGrappleShot(boolean canUseGrappleShot)
	{
		this.canUseGrappleShot = canUseGrappleShot;
	}
	public boolean canUseGrappleShot()
	{
		return this.canUseGrappleShot;
	}
	public void setIsNewbZone(boolean newbZone)
	{
		this.newbZone = newbZone;
	}
	public boolean isNewbZone()
	{
		return this.newbZone;
	}
	public int getCombatCooldown(){
		return this.combatCoolDown;
	}
	public void setCombatCooldown(int combatCooldown){
		this.combatCoolDown = combatCooldown;
	}
	public Location getGrappleStart()
	{
		return this.grappleStart;
	}
	public Location getGrappleEnd()
	{
		return this.grappleEnd;
	}
	public void setGrappleStart(Location grappleStart)
	{
		this.grappleStart = grappleStart;
	}
	public void setGrappleEnd(Location grappleEnd)
	{
		this.grappleEnd = grappleEnd;
	}
	public int getComboReady()
	{
		return this.comboReady;
	}
	public void setComboReady(int comboReady)
	{
		int modifiedComboReady = comboReady;
		if(modifiedComboReady < 0){
			modifiedComboReady = 0;
		}
		this.comboReady = modifiedComboReady;
	}
	public double getHealthPercent()
	{
		return this.healthPercent;
	}
	public void setHealthPercent(double percent)
	{
		this.health = this.health / percent;
		this.healthPercent = percent;
	}
	public boolean getUsedSpeedSkill()
	{
		return this.usedSpeedSkill;
	}
	public void setUsedSpeedSkill(boolean usedSpeedSkill)
	{
		this.usedSpeedSkill = usedSpeedSkill;
	}
	public boolean wasFirstToggle()
	{
		return this.firstToggle;
	}
	public void setFirstToggle(boolean firstToggle)
	{
		this.firstToggle = firstToggle;
	}
	public int getWasSprinting()
	{
		return this.wasSprinting;
	}
	public void setWasSprinting(int wasSprinting)
	{
		this.wasSprinting = wasSprinting;
	}
	public int getStun()
	{
		return this.stun;
	}
	public void setStun(int stun)
	{
		this.stun = stun;
	}
	public boolean isStunned()
	{
		return this.isStunned;
	}
	public void setIsStunned(boolean isStunned)
	{
		this.isStunned = isStunned;
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
		if(stamina < 0)
		{
			this.stamina = 0;
		}
		else if(this.getMaxStamina() < stamina)
		{
			this.stamina = this.getMaxStamina();
		}
		else
		{
			this.stamina = stamina;
		}
		
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
		}
		else
		{
			this.sethealth(this.maxHealth);
			player.setFoodLevel(20);
		}
	}
	
	public boolean damage(int dealtDamage)
	{
		for(PotionEffect effect:this.getPlayer().getActivePotionEffects())
		{
			if(effect.getType() == PotionEffectType.DAMAGE_RESISTANCE)
			{
				int level = effect.getAmplifier();
				dealtDamage = dealtDamage - level * 12;
				if(dealtDamage < 0)
				{
					dealtDamage = 0;
				}
			}
		}
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
		if(this.player.getActivePotionEffects().contains(PotionEffectType.FAST_DIGGING)){
			this.player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		}
		if(this.player.getLocation().subtract(0,player.getLocation().getY(),0).add(0,1,0).getBlock().getType() == Material.GLASS){
			if(this.isNewbZone() == false){
				player.sendMessage(ChatColor.RED + "YOU ARE ENTERING A NEWBIE ZONE GEAR/DAMAGE RESTRICTED!");
				this.setIsNewbZone(true);
			}
		}
		else{
			if(this.isNewbZone() == true){
				this.setIsNewbZone(false);
				player.sendMessage(ChatColor.RED + "YOU ARE LEAVING THE NEWBIE ZONE GEAR/DAMAGE UNLIMITED!");
			}
			
		}
		if(this.player.getLocation().subtract(0,player.getLocation().getY(),0).add(0,1,0).getBlock().getType() == Material.ENDER_STONE){
			if(this.flyZone == false){
				this.flyZone = true;
				player.sendMessage(ChatColor.RED + "YOU FEEL WEIGHTLESS!");
				player.setAllowFlight(true);
				player.setFlying(true);
			}
		}
		else{
			if(this.flyZone == true){
				this.flyZone = false;
				player.sendMessage(ChatColor.RED + "YOU NO LONGER FEEL WEIGHTLESS AND COME CRASHING DOWN!");
			}
			
		}

		if(this.player.getLocation().subtract(0,1,0).getBlock().getType() == Material.BEDROCK){
			Skills.SuperSpeed.pathspeedOn(player);
		}
		if(this.tackleTimer > 0)
		{
			if(this.getPlayer().getPassenger() != null && this.getPlayer().getPassenger() instanceof Player)
			{
				PVPPlayer rider = PvpHandler.getPvpPlayer((Player)this.getPlayer().getPassenger());
				rider.setStamina((int)rider.getStamina() - 10);
				if(rider.getStamina() < 7)
				{
					this.getPlayer().getPassenger().eject();
				}
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 500, 3));
			}
			else
			{
				this.setTackleTimer(0);
			}
		}
		if(this.getComboReady() == 0 && this.canUsePileDrive() == true || this.getComboReady() == 0 && this.canUseGrappleShot == true)
		{
			if(this.canUseGrappleShot==true)
			{
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU NO LONGER FEEL READY TO GRAPPLE-SHOT!");
				this.setCanUseGrappleShot(false);
				this.setCanUsePileDrive(false);
			}
			if(this.canUsePileDrive==true)
			{
				this.setCanUsePileDrive(false);
				this.setCanUseGrappleShot(false);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU NO LONGER FEEL READY TO PILEDRIVE!");
			}
			
			
		}
		if(this.getComboReady() > 0 && this.comboReady < 9){
			this.comboReady--;
			if(this.getComboReady() >= 6){
				this.setCanUsePileDrive(true);
			}
		}
		this.checkForGrapple();
		if(this.getComboReady() >= 10 && this.comboReady < 99)
		{
			this.setComboReady(this.getComboReady() - 10);
		}
		if(this.getComboReady() >= 100 && this.comboReady < 999)
		{
			this.setComboReady(this.getComboReady()- 100);
		}
		this.healthPercent = this.health / this.maxHealth;
		if(player.getPlayer().getAllowFlight() == false && player.getWorld().getName().contains("world") && this.skillCoolDown == 0){
			player.setAllowFlight(true);
		}
		if(this.flyZone == true){
			player.setAllowFlight(true);
		}
		if(this.flyZone == false && player.isFlying() == true && player.getGameMode() == GameMode.SURVIVAL && player.getWorld().getName().contains("world")){
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
		if(this.wasSprinting > 0)
		{
			--this.wasSprinting;
		}
		else if(this.wasSprinting < 0)
		{
			this.wasSprinting = 0;
		}
		if(player.isSprinting() == true){
			this.wasSprinting = 2;
		}
		if(this.usedSpeedSkill == true){
			this.setStamina((int)stamina - 5);
			if(this.stamina <= 4)
			{
				player.sendMessage(ChatColor.RED + "" + ChatColor.RED + "YOUR OUT OF STAMINA AND STOP SPRINTING!");
				SuperSpeed.speedOff(player);
			}
		}
		String message = ("SIDEBAR,Health," + ChatColor.BLUE + "Health:" + ChatColor.RESET + "," + (int)this.health);
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message.getBytes());
		String message2 = ("SIDEBAR,Health," + ChatColor.YELLOW + "Stamina:" + ChatColor.RESET + "," + (int)this.stamina);
		Bukkit.getMessenger().dispatchIncomingMessage(player, "Scoreboard", message2.getBytes());
		String message3 = ("SIDEBAR,Health," + ChatColor.RED + "Attackable:" + ChatColor.RESET + "," + ((int)this.combatCoolDown));
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
		if(this.getStamina() < this.getMaxStamina()){
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
	public boolean canUsePileDrive() 
	{
		return canUsePileDrive;
	}
	public void setCanUsePileDrive(boolean canUsePileDrive)
	{
		this.canUsePileDrive = canUsePileDrive;
	}
	public void checkForGrapple()
	{
		if(this.isUsingGrappleShot == true)
		{
			if(this.getGrappleStart() != null && this.getGrappleEnd() != null && this.hitByGrapple == null)
			{
				Skills.GrappleShot.grappleShotBlockHit(getPlayer(), this.getGrappleEnd().getBlock(), this);
				this.setCanUsePileDrive(false);
			}
			if(this.getGrappleStart() != null && this.hitByGrapple != null)
			{
				Skills.GrappleShot.grappleShotPlayerHit(this.hitByGrapple, this.getPlayer(), this);
				this.setCanUsePileDrive(false);
			}
		}
	}
}
