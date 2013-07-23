package PvpBalance;

import java.util.Random;

import me.ThaH3lper.com.DungeonAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.palmergames.bukkit.towny.utils.CombatUtil;

import Event.PBEntityDamageEntityEvent;
import Event.PBEntityDamageEvent;
import Event.PBEntityDeathEvent;
import Event.PBEntityRegainHealthEvent;
import SaveLoad.LoadSave;


public class DBZListener implements Listener
{
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
	
	@EventHandler
	public void onPlayerDamageEvent(EntityDamageByEntityEvent event)
	{
		boolean canhit = DungeonAPI.canhit(event);
		double dealtDamage = 0.0f;

		Entity e = event.getEntity();
		if (e instanceof Player)
		{
			double rawDamage = event.getDamage();
			Random rand = new Random();
			Player damagee = (Player) e;
			
			PVPPlayer pvpDamagee = PvpHandler.getPvpPlayer(damagee);
			if(event.getCause().equals(DamageCause.PROJECTILE))
			{
				if(CombatUtil.preventDamageCall(((Arrow)event.getDamager()).getShooter(), damagee) || !DungeonAPI.canhit(event))
				{
					event.setCancelled(true);
					return;
				}
				else if((event.getDamager().getType() == EntityType.SMALL_FIREBALL))
				{
					Entity damager = ((SmallFireball)event.getDamager()).getShooter();
					PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, (int)dealtDamage, event.getCause());
					Bukkit.getPluginManager().callEvent(pbdEvent);
					if(pbdEvent.isCancelled())
					{
						event.setCancelled(true);
						return;
					}
					dealtDamage = pbdEvent.getDamage();
					pvpDamagee.Damage((int)dealtDamage, event.getEntity());
				}
				else if(event.getDamager().getType() == EntityType.FIREBALL)
				{
					dealtDamage += 175;
					Entity damager = ((Fireball)event.getDamager()).getShooter();
					PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, (int)dealtDamage, event.getCause());
					Bukkit.getPluginManager().callEvent(pbdEvent);
					if(pbdEvent.isCancelled())
					{
						event.setCancelled(true);
						return;
					}
					dealtDamage = pbdEvent.getDamage();
					pvpDamagee.Damage((int)dealtDamage ,event.getEntity());
				}
				else if(event.getDamager().getType() == EntityType.ARROW)
				{
					Arrow arrow = (Arrow)event.getDamager();
					Entity damager = arrow.getShooter();
					if(damager instanceof Player && event.getEntity() instanceof Player){
						dealtDamage = SaveLoad.LoadSave.Bow;
					}
					if(!(arrow.getShooter() instanceof Player)){
						dealtDamage = event.getDamage() * SaveLoad.LoadSave.Multi;
					}
					PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, (int)dealtDamage, event.getCause());
					Bukkit.getPluginManager().callEvent(pbdEvent);
					if(pbdEvent.isCancelled())
					{
						event.setCancelled(true);
						return;
					}
					dealtDamage = pbdEvent.getDamage();
					if(arrow.getShooter() instanceof Player)
					{
						PVPPlayer pvpDamager = PvpHandler.getPvpPlayer((Player)arrow.getShooter());
						pvpDamager.setCombatCoolDown(80);
					}

					pvpDamagee.setCombatCoolDown(80);
					pvpDamagee.Damage((int)dealtDamage , damager);
				}
			}
			else if(event.getDamager() instanceof Player && canhit && event.getDamage() > 0)
			{
				Player damager = (Player)event.getDamager();
				PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
				boolean faction = true;
				if(plugin.hasFaction())
				{
					faction = plugin.getFactions().entityListener.canDamagerHurtDamagee(event, true);
				}
				if(!CombatUtil.preventDamageCall(damager, damagee) && DungeonAPI.canhit(event) && faction)
				{
					if(pvpDamager.canHit())
					{
						if(damager.getItemInHand().getType().equals(Material.BOW) && !event.getCause().equals(DamageCause.PROJECTILE)){
							dealtDamage = 20;
						}
						else{
							dealtDamage = Damage.calcDamage(damager) + rand.nextInt(Damage.calcDamage(damager) / 10);
						}
						PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, (int)dealtDamage, event.getCause());
						Bukkit.getPluginManager().callEvent(pbdEvent);
						if(pbdEvent.isCancelled())
						{
							event.setCancelled(true);
							return;
						}
						dealtDamage = pbdEvent.getDamage();
						pvpDamagee.Damage((int)dealtDamage ,event.getEntity());
							
						String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + pvpDamagee.gethealth();
						Bukkit.getMessenger().dispatchIncomingMessage(damager, "Scoreboard", message.getBytes());
						pvpDamager.setHitCoolDown(SaveLoad.LoadSave.HitCooldown);
						pvpDamager.setCombatCoolDown(80);
						pvpDamagee.setCombatCoolDown(80);
						if(PvpBalance.plugin.isDebug() || pvpDamager.isPvpstats())
						{
							damager.sendMessage(ChatColor.RED + "DAMAGE DEALT: " + dealtDamage);
						}
					}
					else
					{
						event.setCancelled(true);
					}
				}
				else
				{
					canhit = false;
				}
			}
			else if(canhit && event.getDamage() > 0 && !(event.getDamager() instanceof Player))
			{
				if(event.getDamager() instanceof Arrow && ((Arrow)event.getDamager()).getShooter() instanceof Player)
				{
					Player damager = (Player)((Arrow)event.getDamager()).getShooter();
					PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
					//dealtDamage = rawDamage * LoadSave.Diamond;
					dealtDamage = Damage.calcDamage(damager);
					PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, (int)dealtDamage, event.getCause());
					Bukkit.getPluginManager().callEvent(pbdEvent);
					if(pbdEvent.isCancelled())
					{
						event.setCancelled(true);
						return;
					}
					dealtDamage = pbdEvent.getDamage();
					pvpDamagee.Damage((int)dealtDamage ,event.getEntity());
					pvpDamager.setCombatCoolDown(80);
					pvpDamagee.setCombatCoolDown(80);
					event.setDamage(0D);
				}
				else
				{
					dealtDamage = rawDamage * SaveLoad.LoadSave.Multi;
					PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, event.getDamager(), (int)dealtDamage, event.getCause());
					Bukkit.getPluginManager().callEvent(pbdEvent);
					if(pbdEvent.isCancelled())
					{
						event.setCancelled(true);
						return;
					}
					dealtDamage = pbdEvent.getDamage();
					pvpDamagee.Damage((int)dealtDamage ,event.getEntity());
					if(PvpBalance.plugin.isDebug())
					{
					}
				}
			}
			if(pvpDamagee.isGod())
			{
				event.setCancelled(true);
			}
			else
			{
				event.setDamage(0f);
			}
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
			else{
				player.teleport(new Location(player.getWorld(), -730.50, 105, 319.50));
			}
		}
		PvpHandler.addPvpPlayer(newPVP);
		Damage.calcArmor(event.getPlayer());
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
					PvpHandler.getPvpPlayer(player).setArmorEventLastTick(2);
			}

		}
		//Bukkit.broadcastMessage("useiteminhand " +  event.useItemInHand());
		//Bukkit.broadcastMessage("isblockinhand " +  event.isBlockInHand());
		//Bukkit.broadcastMessage("getaction " + event.getAction());
		//Bukkit.broadcastMessage("issneaking " + player.isSneaking());
	}
	
	@EventHandler
	public void damageCause(EntityDamageEvent event)
	{
		int dealt = 0;
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			if(event.getCause().equals(DamageCause.STARVATION))
			{
				event.setDamage(0f);
			}
			else if(PvpHandler.getPvpPlayer(player).isGod()){
				event.setCancelled(true);
			}
			else if(event.getCause().equals(DamageCause.FIRE_TICK))
			{
				int damage = SaveLoad.LoadSave.Firetick;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage);
			}
			else if(event.getCause().equals(DamageCause.VOID))
			{
				int damage = SaveLoad.LoadSave.Voide;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage);
				event.setDamage(0f);
			}
			else if(event.getCause().equals(DamageCause.CONTACT))
			{
				int damage = SaveLoad.LoadSave.Contact;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled() || player.getNoDamageTicks() > 10)
				{
					event.setCancelled(true);
					return;
				}
				if(player.getNoDamageTicks() < 10){
					pvp.Damage(damage);
					event.setDamage(0f);
				}
			}
			else if(event.getCause().equals(DamageCause.DROWNING))
			{
				int damage = SaveLoad.LoadSave.Drowning;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage);
				event.setDamage(0f);
			}
			else if(event.getCause().equals(DamageCause.POISON))
			{
				int damage = SaveLoad.LoadSave.Poison;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage);
				event.setDamage(0f);
			}
			else if(event.getCause().equals(DamageCause.FALL))
			{
				int damage = SaveLoad.LoadSave.Fall;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage);
				event.setDamage(0f);
			}
			else if(event.getCause().equals(DamageCause.WITHER))
			{
				event.setDamage(0f);
				int damage = SaveLoad.LoadSave.Wither;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage ,event.getEntity());
			}

			else if(event.getCause().equals(DamageCause.ENTITY_EXPLOSION))
			{
				int damage = SaveLoad.LoadSave.Explosion_Mob;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage ,event.getEntity());
				event.setDamage(0f);
			}
			else if(event.getCause().equals(DamageCause.BLOCK_EXPLOSION))
			{
				int damage = SaveLoad.LoadSave.Explosion;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage ,event.getEntity());
				event.setDamage(0f);
			}
			else if(event.getCause().equals(DamageCause.LIGHTNING))
			{
				int damage = SaveLoad.LoadSave.Lightning;
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage ,event.getEntity());
				event.setDamage(0f);
			}
			else if(event.getCause().equals(DamageCause.SUFFOCATION))
			{
				int damage = 100;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage ,event.getEntity());
				event.setDamage(0f);
			}
			//THIS MUST BE LAST ==================================================================================
			else if(event.getCause() != DamageCause.PROJECTILE && event.getCause() != DamageCause.ENTITY_ATTACK)
			{
				int damage = (int)event.getDamage();
				dealt = dealt + damage;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				pvp.Damage(damage ,event.getEntity());
				event.setDamage(0f);
			}
			//THIS MUST BE LAST ================================================================================
		}
	}
	
	@EventHandler
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
	public void onEntityDeathEvent(EntityDeathEvent event)
	{
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
    			PvpHandler.getPvpPlayer(player).setArmorEventLastTick(2);
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
	        PvpHandler.getPvpPlayer(player).setArmorEventLastTick(2);
	    }
	}
	@EventHandler
	public void shotBow(EntityShootBowEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if(event.getForce() < 0.95){
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You must pull the bow all the way back to fire!");
			}
		}
	}
	
	private boolean isArmor(ItemStack is)
	{
		if(is == null)
			return false;
		switch(is.getTypeId())
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
	
		
		