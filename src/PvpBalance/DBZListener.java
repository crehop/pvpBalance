package PvpBalance;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.palmergames.bukkit.towny.utils.CombatUtil;

import Event.PBEntityDamageEntityEvent;
import Event.PBEntityDamageEvent;
import Event.PBEntityDeathEvent;
import Event.PBEntityRegainHealthEvent;
import SaveLoad.LoadSave;
import Skills.PileDrive;
import Skills.SuperSpeed;


public class DBZListener implements Listener
{
	static int tick = 0;
	public static PvpBalance plugin;
	public LoadSave LoadSave;
	
	public DBZListener(PvpBalance instance, LoadSave LoadSave)
	{
		this.LoadSave = LoadSave;
		plugin = instance;
	}	
		
	@EventHandler	
	public void playerQuit(PlayerQuitEvent quitevent)
	{
		Player quitPlayer = quitevent.getPlayer();
		PVPPlayer pp = PvpHandler.getPvpPlayer(quitPlayer);
		if(pp.isInCombat())
		{
			quitPlayer.setHealth(0f);
		}
		if(pp.isInPVP())
		{
			pp.sethealth(0);
			quitevent.getPlayer().setHealth(0D);
		}
		if(pp.isInParty())
		{
			pp.getParty().leave(pp);
		}
		PvpHandler.removePvpPlayer(pp);
	}
	
	@EventHandler
	public void foodChangeEvent(FoodLevelChangeEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			if(PvpHandler.getPvpPlayer(player).isGod())
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamageEvent(EntityDamageByEntityEvent event)
	{
		//if(event.isCancelled())
		//	return;
		if(!Damage.partyCanHit(event.getEntity(), event.getDamager()))
		{
			event.setCancelled(true);
			return;
		}
		if(event.getDamage() <= 0)
		{
			event.setCancelled(true);
			return;
		}
		double dealtDamage = 0.0f;
		Entity e = event.getEntity();
		if (e instanceof Player)
		{
			double rawDamage = event.getDamage();
			Player damagee = (Player) e;
			if(damagee.getNoDamageTicks() > 10)
			{
				event.setCancelled(true);
				return;
			}
			
			PVPPlayer pvpDamagee = PvpHandler.getPvpPlayer(damagee);
			if(pvpDamagee.isGod())
			{
				event.setCancelled(true);
				return;
			}
			if(event.getDamager() instanceof Arrow)
			{
				Arrow arrow = (Arrow)event.getDamager();
				Entity damager = arrow.getShooter();
				if(CombatUtil.preventDamageCall(((Arrow)event.getDamager()).getShooter(), damagee))
				{
					event.setCancelled(true);
					return;
				}
				if(damager instanceof Player && event.getEntity() instanceof Player)
				{
					PVPPlayer pvpDamager = PvpHandler.getPvpPlayer((Player)arrow.getShooter());
					if(pvpDamager.isUsingGrappleShot() == true)
					{
						pvpDamager.setGrapplePlayer(damagee);
					}
					dealtDamage = Damage.calcDamage((Player)damager);
					pvpDamagee.damage((int)dealtDamage);
					pvpDamager.setCombatCoolDown(20);
					pvpDamagee.setCombatCoolDown(20);
				}
				if(!(damager instanceof Player))
				{	
					dealtDamage = event.getDamage() * SaveLoad.LoadSave.Multi;
				}
				pvpDamagee.setCombatCoolDown(20);
			}
			else if(event.getCause().equals(DamageCause.PROJECTILE))
			{
				if(CombatUtil.preventDamageCall(((Projectile)event.getDamager()).getShooter(), damagee))
				{
					event.setCancelled(true);
					return;
				}
				else if((event.getDamager().getType() == EntityType.WITHER_SKULL))
				{
					event.setDamage(0f);
					dealtDamage += 85;
				}
				else if((event.getDamager().getType() == EntityType.SMALL_FIREBALL))
				{
					dealtDamage += 65;
				}
				else if(event.getDamager().getType() == EntityType.FIREBALL)
				{
					dealtDamage += 175;
				}
				PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, event.getDamager(), (int)dealtDamage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				dealtDamage = pbdEvent.getDamage();
				if(!pvpDamagee.damage((int)dealtDamage))
				{
					event.setCancelled(true);
					return;
				}
			}
			else if(event.getDamager() instanceof Player)
			{
				Player damager = (Player)event.getDamager();
				PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
				if(CombatUtil.preventDamageCall(damager, damagee))
				{
					event.setCancelled(true);
					return;
				}
				if(!pvpDamager.canHit())
				{
					event.setCancelled(true);
					return;
				}
				if(damager.getItemInHand().getType().equals(Material.BOW) && !(event.getDamager() instanceof Arrow))
				{
					dealtDamage = 20;
				}
				else
				{
					if(pvpDamager.canUsePileDrive()){
						PileDrive.pileDrive(damagee,damager);
					}
					else
					{
						dealtDamage = Damage.calcDamage(damager) + new Random().nextInt(Damage.calcDamage(damager) / 10);
					}
				}
				PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, (int)dealtDamage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				dealtDamage = pbdEvent.getDamage();
				if(!pvpDamagee.damage((int)dealtDamage))
				{
					event.setCancelled(true);
					return;
				}
				
				String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + pvpDamagee.gethealth();
				Bukkit.getMessenger().dispatchIncomingMessage(damager, "Scoreboard", message.getBytes());
				pvpDamager.setHitCoolDown(SaveLoad.LoadSave.HitCooldown);
				pvpDamager.setCombatCoolDown(20);
				pvpDamagee.setCombatCoolDown(20);
				if(PvpBalance.plugin.isDebug() || pvpDamager.isPvpstats())
				{
					damager.sendMessage(ChatColor.RED + "DAMAGE DEALT: " + dealtDamage);
				}
			}
			else
			{
				if(event.getDamager() instanceof Arrow && ((Arrow)event.getDamager()).getShooter() instanceof Player)
				{
					Player damager = (Player)((Arrow)event.getDamager()).getShooter();
					PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
					dealtDamage = Damage.calcDamage(damager);
					pvpDamager.setCombatCoolDown(20);
					pvpDamagee.setCombatCoolDown(20);
				}
				else
				{
					dealtDamage = rawDamage * SaveLoad.LoadSave.Multi;
				}
				PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, event.getDamager(), (int)dealtDamage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				dealtDamage = pbdEvent.getDamage();
				if(!pvpDamagee.damage((int)dealtDamage))
				{
					event.setCancelled(true);
					return;
				}
			}
			event.setDamage(0D);
			event.setCancelled(false);
		}
		else if(e instanceof LivingEntity)
		{
			if(!(event.getDamager() instanceof Player) && !(event.getDamager() instanceof Arrow))
				return;
			double health = (((LivingEntity)e).getHealth() - event.getDamage());
			if(health < 0)
				health = 0;
			String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + (int)health;
			if(event.getDamager() instanceof Player)
			{
				Bukkit.getMessenger().dispatchIncomingMessage((Player)event.getDamager(), "Scoreboard", message.getBytes());
			}
			else
			{
				if(!(((Arrow)event.getDamager()).getShooter() instanceof Player))
					return;
				Bukkit.getMessenger().dispatchIncomingMessage((Player)((Arrow)event.getDamager()).getShooter(), "Scoreboard", message.getBytes());
			}
		}
	}
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		PVPPlayer newPVP = new PVPPlayer(player);
		if(player.getWorld().getName().contains("world"))
		{
			if(player.hasPermission("particles.admin"))
			{
				player.sendMessage(ChatColor.RED + "Welcome Administrator :" + player.getName() + ChatColor.GREEN + " Please Enjoy your stay on the medieval lords server.. a personal Concierge will be with you shortly to handle your every whim");
				newPVP.setGod(true);
			}
			else
			{
				player.teleport(new Location(player.getWorld(), -730.50, 105, 319.50));
			}
		}
		PvpHandler.addPvpPlayer(newPVP);
		Damage.calcArmor(event.getPlayer());
		if(player.getHealth() > 0)
			newPVP.sethealth(newPVP.getMaxHealth());
	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		for(PotionEffect effect:player.getActivePotionEffects()){
			PotionEffectType potion = effect.getType();
			player.removePotionEffect(potion);
		}
		if(PvpHandler.getPvpPlayer(player) == null)
		{
			PVPPlayer newPVP = new PVPPlayer(player);
			PvpHandler.addPvpPlayer(newPVP);
		}
		Damage.calcArmor(player);
		PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
		PVPPlayer.setIsDead(false);
		PVPPlayer.sethealth(PVPPlayer.getMaxHealth());
	}
	@EventHandler
	public void projectileHitEvent(ProjectileHitEvent event)
	{
		if(event.getEntity().getShooter() instanceof Player){
			Player player = (Player)event.getEntity().getShooter();
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			if(pvp.isUsingGrappleShot() == true)
			{
				pvp.setGrappleEnd(event.getEntity().getLocation());
			}
		}
		
	}
	//JUMP SKILL!
	@EventHandler
	public void playerToggleFlightEvent(PlayerToggleFlightEvent event)
	{
		PVPPlayer pvp = PvpHandler.getPvpPlayer(event.getPlayer());
		if(pvp.getStamina() > 10 && pvp.canUseSkill() == true && event.getPlayer().getGameMode() == GameMode.SURVIVAL && event.getPlayer().getWorld().getName().contains("world")){
			Skills.SuperJump.Jump(event.getPlayer(), 0.9);
			pvp.setSkillCooldown(7);
			pvp.setCanUseSkill(false);
			pvp.setStamina((int) (pvp.getStamina() - 10));
			event.setCancelled(true);
			pvp.getPlayer().setAllowFlight(false);
			pvp.getPlayer().setFlying(false);
		}
		if(event.getPlayer().getGameMode() == GameMode.SURVIVAL && event.getPlayer().getWorld().getName().contains("world")){
			event.setCancelled(true);
			event.getPlayer().setAllowFlight(false);
			event.getPlayer().setFlying(false);
		}
	}
	@EventHandler
	public void playerToggleSprintEvent(PlayerToggleSprintEvent event)
	{
		Player player = event.getPlayer();
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		pvp.setWasSprinting(2);
		pvp.setCanUseSkill(true);
		if(pvp.getUsedSpeedSkill() == true)
		{
			pvp.setUsedSpeedSkill(false);
			pvp.setCanUseSkill(false);
			SuperSpeed.speedOff(player);
		}
	}
	@EventHandler
	public void playerToggleSneakEvent(PlayerToggleSneakEvent event)
	{
		Player player = event.getPlayer();
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(pvp.wasFirstToggle() == true)
		{
			pvp.setFirstToggle(false);
			return;
		}
		else if(pvp.getWasSprinting() > 0 && pvp.canUseSkill() == true && pvp.getStamina() > 5 && pvp.getUsedSpeedSkill() == false){
			event.setCancelled(true);
			player.setSneaking(false);
			SuperSpeed.speedOn(player);
			pvp.setUsedSpeedSkill(true);
			pvp.setFirstToggle(true);
			return;
		}
		else if(pvp.getUsedSpeedSkill() == true && pvp.wasFirstToggle() == false)
		{
			pvp.setUsedSpeedSkill(false);
			pvp.setCanUseSkill(false);
			SuperSpeed.speedOff(player);
		}
	}
	@EventHandler
	public void regenEvent(EntityRegainHealthEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			if(event.getRegainReason() == RegainReason.MAGIC)
			{
				Player player = (Player)event.getEntity();
				if(PvpHandler.getPvpPlayer(player) == null)
				{
					PVPPlayer newPVP = new PVPPlayer(player);
					PvpHandler.addPvpPlayer(newPVP);
				}
				PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
				int heal = SaveLoad.LoadSave.HealPot;
				PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(player, heal, event.getRegainReason());
				Bukkit.getPluginManager().callEvent(pberh);
				if(pberh.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				PVPPlayer.sethealth(PVPPlayer.gethealth() + pberh.getAmount());
			}
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void playerinteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if((event.useItemInHand() == Result.DEFAULT && !event.isBlockInHand()) && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			if(isArmor(player.getItemInHand()))
			{
					Damage.calcArmor(player);
					PvpHandler.getPvpPlayer(player).setArmorEventLastTick(1);
			}

		}
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(player.getItemInHand().getType() == Material.DIAMOND_SWORD || player.getItemInHand().getType()== Material.IRON_SWORD
				|| player.getItemInHand().getType() == Material.GOLD_SWORD || player.getItemInHand().getType() == Material.WOOD_SWORD
				|| player.getItemInHand().getType() == Material.IRON_AXE || player.getItemInHand().getType() == Material.GOLD_AXE
				|| player.getItemInHand().getType() == Material.WOOD_AXE || player.getItemInHand().getType() == Material.DIAMOND_AXE
				|| player.getItemInHand().getType() == Material.IRON_HOE || player.getItemInHand().getType() == Material.GOLD_HOE
				|| player.getItemInHand().getType() == Material.WOOD_HOE || player.getItemInHand().getType() == Material.DIAMOND_HOE){
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				if(pvp.getComboReady() < 7)
				{
					pvp.setComboReady(pvp.getComboReady() + 2);
					if(pvp.getComboReady() >= 6 && pvp.getStamina() >= 51 && pvp.canUsePileDrive() == false){
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "PILEDRIVE READY HIT PLAYER TO ACTIVATE!");
						pvp.setCanUsePileDrive(true);
						pvp.setCanUseGrappleShot(false);
					}
					if(pvp.getComboReady() >=6 && pvp.getStamina() < 51){
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "PILEDRIVE NEEDS 50 STAMINA!");
					}
				}
			}
			if(player.getItemInHand().getType() == Material.BOW){
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				if(pvp.getComboReady() < 7)
				{
					pvp.setComboReady(pvp.getComboReady() + 2);
					if(pvp.getComboReady() >= 7 && pvp.getStamina() >= 25){
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "GRAPLE READY SHOOT ARROW TO ACTIVATE!");
						pvp.setCanUsePileDrive(false);
						pvp.setCanUseGrappleShot(true);
					}
					if(pvp.getComboReady() >= 7 && pvp.getStamina() < 25){
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "GRAPPLE NEEDS 25 STAMINA!");
					}
				}
			}
		}
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageEvent(EntityDamageEvent event)
	{
		//if(event.isCancelled())
		//	return;
		if(event instanceof EntityDamageByEntityEvent)
			return;
		int damage = 0;
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			if(event.getCause().equals(DamageCause.STARVATION))
			{
				event.setDamage(0D);
				return;
			}
			else if(PvpHandler.getPvpPlayer(player).isGod())
			{
				event.setCancelled(true);
				return;
			}
			else if(event.getCause().equals(DamageCause.FIRE_TICK))
			{
				pvp.uncheckedDamage(SaveLoad.LoadSave.Firetick);
				int prevNoDamageTicks = player.getNoDamageTicks();
				player.damage(0D);
				player.setNoDamageTicks(prevNoDamageTicks);
				event.setCancelled(true);
				return;
			}
			else if(event.getCause().equals(DamageCause.VOID))
			{
				damage = SaveLoad.LoadSave.Voide;
			}
			else if(event.getCause().equals(DamageCause.CONTACT))
			{
				damage = SaveLoad.LoadSave.Contact;
			}
			else if(event.getCause().equals(DamageCause.DROWNING))
			{
				pvp.uncheckedDamage(SaveLoad.LoadSave.Drowning);
				int prevNoDamageTicks = player.getNoDamageTicks();
				player.damage(0D);
				player.setNoDamageTicks(prevNoDamageTicks);
				event.setCancelled(true);
				return;
			}
			else if(event.getCause().equals(DamageCause.POISON))
			{
				pvp.uncheckedDamage(SaveLoad.LoadSave.Poison);
				int prevNoDamageTicks = player.getNoDamageTicks();
				player.damage(0D);
				player.setNoDamageTicks(prevNoDamageTicks);
				event.setCancelled(true);
				return;
			}
			else if(event.getCause().equals(DamageCause.FALL))
			{
				damage = SaveLoad.LoadSave.Fall;
			}
			else if(event.getCause().equals(DamageCause.WITHER))
			{
				pvp.uncheckedDamage(SaveLoad.LoadSave.Wither);
				int prevNoDamageTicks = player.getNoDamageTicks();
				player.damage(0D);
				player.setNoDamageTicks(prevNoDamageTicks);
				event.setCancelled(true);
				return;
			}

			else if(event.getCause().equals(DamageCause.ENTITY_EXPLOSION))
			{
				damage = SaveLoad.LoadSave.Explosion_Mob;
			}
			else if(event.getCause().equals(DamageCause.BLOCK_EXPLOSION))
			{
				damage = SaveLoad.LoadSave.Explosion;
			}
			else if(event.getCause().equals(DamageCause.LIGHTNING))
			{
				damage = SaveLoad.LoadSave.Lightning;
			}
			else if(event.getCause().equals(DamageCause.SUFFOCATION))
			{
				damage = 100;
			}
			//THIS MUST BE LAST ==================================================================================
			else if(!(event.getCause().equals(DamageCause.PROJECTILE)) && !(event.getCause().equals(DamageCause.ENTITY_ATTACK)))
			{
				damage = (int)event.getDamage();
			}
			//THIS MUST BE LAST ================================================================================
			PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
			Bukkit.getPluginManager().callEvent(pbdEvent);
			if(pbdEvent.isCancelled())
			{
				event.setCancelled(true);
				return;
			}
			pvp.uncheckedDamage(damage);
			event.setCancelled(true);
			player.damage(0D);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(final PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		/*if(PvpHandler.getPvpPlayer(player) == null)
		{
			PVPPlayer newPVP = new PVPPlayer(player);
			PvpHandler.addPvpPlayer(newPVP);
		}*/
		PVPPlayer pvpPlayer = PvpHandler.getPvpPlayer(player);
		pvpPlayer.setIsDead(true);
		//player.teleport(player.getWorld().getSpawnLocation());
		Bukkit.getScheduler().scheduleSyncDelayedTask(PvpBalance.plugin, new Runnable()
		{
			@Override
			public void run()
			{
				final PBEntityDeathEvent pbede = new PBEntityDeathEvent(event.getEntity(), event.getDrops(), event.getDroppedExp());
				Bukkit.getPluginManager().callEvent(pbede);
				event.setDroppedExp(pbede.getDropExp());
			}
		},1L);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if(event.isShiftClick())
		{
			if(isArmor(event.getCurrentItem()))
			{
    			Player player = (Player) event.getWhoClicked();
    			if(PvpHandler.getPvpPlayer(player) == null)
    			{
    				PVPPlayer newPVP = new PVPPlayer(player);
    				PvpHandler.addPvpPlayer(newPVP);
    			}
    			PvpHandler.getPvpPlayer(player).setArmorEventLastTick(1);
    		}
    	}
	    if(event.getSlotType() == SlotType.ARMOR)
	    {
	    	Player player = (Player) event.getWhoClicked();
			if(PvpHandler.getPvpPlayer(player) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(player);
				PvpHandler.addPvpPlayer(newPVP);
			}
	        PvpHandler.getPvpPlayer(player).setArmorEventLastTick(1);
	    }
	}
	@EventHandler
	public void shotBow(EntityShootBowEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			pvp.setComboReady(pvp.getComboReady() - 2);
			if(event.getForce() < 0.95){
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU MUST PULL ALL THE WAY BACK TO FIRE BOW!");
				return;
			}
			if(pvp.canUseGrappleShot() == true)
			{
				if(pvp.getStamina() < 25)
				{
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "CANNOT USE GRAPPLE SHOT NOT ENOUGH STAMINA (25)!");
				}
				else if(pvp.getStamina() > 25 && pvp.getSkillCooldown() == 0){
					player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU USE GRAPPLE SHOT!");
					pvp.setGrappleArrow((Arrow)event.getProjectile());
					pvp.setSkillCooldown(5);
					pvp.setGrappleStart(pvp.getPlayer().getLocation());
					pvp.setGrappleEnd(null);
					pvp.setIsUsingGrappleShot(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerItemBreakEvent(PlayerItemBreakEvent event)
	{
		Damage.calcArmor(event.getPlayer());
	}
	
	private boolean isArmor(ItemStack is)
	{
		if(is == null)
			return false;
		int check = 0;
		if(is.getType() == Material.DIAMOND_HELMET){
			check = 310;
		}
		else if(is.getType() == Material.DIAMOND_CHESTPLATE){
			check = 311;
		}
		else if(is.getType() == Material.DIAMOND_LEGGINGS){
			check = 312;
		}
		else if(is.getType() == Material.DIAMOND_BOOTS){
			check = 313;
		}
		else if(is.getType() == Material.GOLD_HELMET){
			check = 314;
		}
		else if(is.getType() == Material.GOLD_CHESTPLATE){
			check = 315;
		}
		else if(is.getType() == Material.GOLD_LEGGINGS){
			check = 316;
		}
		else if(is.getType() == Material.GOLD_BOOTS){
			check = 317;
		}
		else if(is.getType() == Material.IRON_HELMET){
			check = 306;
		}
		else if(is.getType() == Material.IRON_CHESTPLATE){
			check = 307;
		}
		else if(is.getType() == Material.IRON_LEGGINGS){
			check = 308;
		}
		else if(is.getType() == Material.IRON_BOOTS){
			check = 309;
		}
		else if(is.getType() == Material.CHAINMAIL_HELMET){
			check = 302;
		}
		else if(is.getType() == Material.CHAINMAIL_CHESTPLATE){
			check = 303;
		}
		else if(is.getType() == Material.CHAINMAIL_LEGGINGS){
			check = 304;
		}
		else if(is.getType() == Material.CHAINMAIL_BOOTS){
			check = 305;
		}
		else if(is.getType() == Material.LEATHER_HELMET){
			check = 298;
		}
		else if(is.getType() == Material.LEATHER_CHESTPLATE){
			check = 299;
		}
		else if(is.getType() == Material.LEATHER_LEGGINGS){
			check = 300;
		}
		else if(is.getType() == Material.LEATHER_BOOTS){
			check = 301;
		}
		switch(check)
		{
		case 298:
		case 299:
		case 300:
		case 301:
		case 302:
		case 303:
		case 304:
		case 305:
		case 306:
		case 307:
		case 308:
		case 309:
		case 310:
		case 311:
		case 312:
		case 313:
		case 314:
		case 315:
		case 316:
		case 317:
			return true;
		default:
			return false;
		}
	}
}
	
		
		