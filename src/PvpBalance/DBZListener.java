package PvpBalance;

import java.util.Date;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import me.ThaH3lper.com.DungeonAPI;

import com.palmergames.bukkit.towny.utils.CombatUtil;

import Event.PBEntityDamageEntityEvent;
import Event.PBEntityDamageEvent;
import Event.PBEntityRegainHealthEvent;
import SaveLoad.LoadSave;


public class DBZListener implements Listener
{
	public static PvpBalance plugin;
	public LoadSave LoadSave;
	private final int HITCOOLDOWN = 2;
	
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
			quitPlayer.setHealth(0);
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
		int dealtDamage = 0;
		/*if (event.getDamager() instanceof Player){
			//Player damager = (Player)event.getDamager();
			PVPPlayer PVPdamager = PvpHandler.getPvpPlayer(damager);
		}*/
		Entity e = event.getEntity();
		if (e instanceof Player)
		{
			int rawDamage = event.getDamage();
			Random rand = new Random();
			Player damagee = (Player) e;
			if(PvpHandler.getPvpPlayer(damagee) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(damagee);
				PvpHandler.addPvpPlayer(newPVP);
			}
			PVPPlayer PVPDamagee = PvpHandler.getPvpPlayer(damagee);
			if(event.getCause() == DamageCause.PROJECTILE)
			{
				if(CombatUtil.preventDamageCall(damagee, damagee) && DungeonAPI.canhit(event))
				{
					event.setCancelled(true);
					canhit = false;
				}
			}
			if(event.getDamager() instanceof Player && canhit && event.getDamage() > 0)
			{
				Player damager = (Player)event.getDamager();
				if(PvpHandler.getPvpPlayer(damager) == null)
				{
					PVPPlayer newPVP = new PVPPlayer(damager);
					PvpHandler.addPvpPlayer(newPVP);
				}
				Boolean faction = true;
				if(plugin.hasFaction())
				{
					faction = plugin.getFactions().entityListener.canDamagerHurtDamagee(event, true);
				}
				
				
				if(!CombatUtil.preventDamageCall(damagee, damager) && DungeonAPI.canhit(event) && faction)
				{
					Date date = new Date();
					PVPPlayer PVPdamager = PvpHandler.getPvpPlayer(damager);
					if(((date.getTime() / 1000) - PVPdamager.getHitCooldown()) >= HITCOOLDOWN)
					{
						dealtDamage = Damage.calcDamage(damager) + rand.nextInt(Damage.calcDamage(damager) / 10);
						
						PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, dealtDamage, event.getCause());
						Bukkit.getPluginManager().callEvent(pbdEvent);
						if(!pbdEvent.isCancelled())
						{
							dealtDamage = pbdEvent.getDamage();
							PVPDamagee.Damage(dealtDamage);
								
							String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + PVPDamagee.gethealth();
							Bukkit.getMessenger().dispatchIncomingMessage(damager, "Scoreboard", message.getBytes());
						}
						PVPdamager.setHitCoolDown((int) (date.getTime() / 1000));
						PVPdamager.setCombatCoolDown((int) (date.getTime() / 1000));
						if(PvpBalance.plugin.isDebug() || PVPdamager.isPvpstats())
						{
							damager.sendMessage(ChatColor.RED + "DAMAGE DEALT: " + dealtDamage);
						}
					}
				}
				else
				{
					canhit = false;
				}
			}
			else if(canhit && event.getDamage() > 0 && event.getDamager().getClass() != Player.class)
			{
				dealtDamage = rawDamage * LoadSave.Multi;
				PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, event.getDamager(), dealtDamage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(!pbdEvent.isCancelled())
				{
					dealtDamage = pbdEvent.getDamage();
					PVPDamagee.Damage(dealtDamage);
				}
				if(PvpBalance.plugin.isDebug())
				{
					Bukkit.broadcastMessage(ChatColor.RED + " DAMAGE DEALT: " + dealtDamage);
				}
			}
			if(PVPDamagee.isGod())
			{
				event.setCancelled(true);
			}
			else
			{
				event.setDamage(0);
			}
		}
		else if(e instanceof LivingEntity)
		{
			if(!(event.getDamager() instanceof Player) && !(event.getDamager() instanceof Arrow))
				return;
			int health = ((LivingEntity)e).getHealth() - event.getDamage();
			if(health < 0)
				health = 0;
			String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + health;
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
				player.sendMessage("Welcome Administrator " + player);
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
		if(PvpHandler.getPvpPlayer(player) == null)
		{
			PVPPlayer newPVP = new PVPPlayer(player);
			PvpHandler.addPvpPlayer(newPVP);
		}
		Damage.calcArmor(player);
		PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
		PVPPlayer.setIsDead(false);
		PVPPlayer.sethealth(500);
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
				int heal = 700;
				PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(player, heal, event.getRegainReason());
				Bukkit.getPluginManager().callEvent(pberh);
				if(pberh.isCancelled())
					return;
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
	public void damageCause(EntityDamageEvent event){
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			if(PvpHandler.getPvpPlayer(player) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(player);
				PvpHandler.addPvpPlayer(newPVP);
			}
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			if(event.getCause().equals(DamageCause.STARVATION))
			{
				event.setDamage(0);
			}
			if(PvpHandler.getPvpPlayer(player).isGod()){
				event.setCancelled(true);
			}
			else if(event.getCause().equals(DamageCause.FIRE_TICK))
			{
				int damage = LoadSave.Firetick;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
			}
			else if(event.getCause().equals(DamageCause.VOID))
			{
				int damage = LoadSave.Voide;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.CONTACT))
			{
				int damage = LoadSave.Contact;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.DROWNING))
			{
				int damage = LoadSave.Drowning;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.POISON))
			{
				int damage = LoadSave.Poison;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.FALL))
			{
				int damage = LoadSave.Fall;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.WITHER))
			{
				int damage = LoadSave.Wither;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause() != DamageCause.PROJECTILE && event.getCause() != DamageCause.ENTITY_ATTACK)
			{
				int damage = event.getDamage();
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player = event.getEntity();
		if(PvpHandler.getPvpPlayer(player) == null){
			PVPPlayer newPVP = new PVPPlayer(player);
			PvpHandler.addPvpPlayer(newPVP);
		}
		PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(event.getEntity().getPlayer());
		PVPPlayer.setIsDead(true);
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
	
		
		