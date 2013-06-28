package PvpBalance;

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

import SaveLoad.LoadSave;


public class DBZListener implements Listener{

		public static Main plugin;
		public LoadSave LoadSave;
		public DBZListener(Main instance, LoadSave LoadSave){
			this.LoadSave = LoadSave;
			plugin = instance;
		}	
	@EventHandler	
	 public void playerQuit(PlayerQuitEvent quitevent){
		Player quitplayer = quitevent.getPlayer();
		if(Main.cooldown.contains(quitplayer)){
			Main.cooldown.remove(quitplayer);
		}
		if(Main.PVP.contains(Commands.getPVPPlayer(quitplayer))){
			PVPPlayer PVPPlayer = Commands.getPVPPlayer(quitplayer);
			Main.PVP.remove(PVPPlayer);
		}
		if(Main.pvpstats.contains(quitplayer)){
			Main.pvpstats.remove(quitplayer);
		}
		if(Commands.getPVPPlayer(quitplayer).isInCombat == true)
		{
			quitplayer.setHealth(0);
		}

	}
	
	@EventHandler
	public void foodChangeEvent(FoodLevelChangeEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			if(Commands.getPVPPlayer(player).god == true){
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
			PVPPlayer PVPdamager = Commands.getPVPPlayer(damager);
		}*/
		Entity e = event.getEntity();
		if (e instanceof Player)
		{
			int rawDamage = event.getDamage();
			Random rand = new Random();
			Player damagee = (Player) e;
			if(Commands.getPVPPlayer(damagee) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(damagee);
				Main.PVP.add(newPVP);
			}
			PVPPlayer PVPDamagee = Commands.getPVPPlayer(damagee);
			if(event.getCause() == DamageCause.PROJECTILE)
			{
				//ARROW CODE (PLAYER HIT BY PLAYER ARROW)
				if(CombatUtil.preventDamageCall(damagee, damagee) && DungeonAPI.canhit(event))
				{
					event.setCancelled(true);
					canhit = false;
				}
			}
			if(event.getDamager() instanceof Player && canhit && event.getDamage() > 0)
			{
				Player damager = (Player)event.getDamager();
				if(Commands.getPVPPlayer(damager) == null)
				{
					PVPPlayer newPVP = new PVPPlayer(damager);
					Main.PVP.add(newPVP);
				}
				Boolean faction = true;
				if(plugin.faction)
				{
					faction = plugin.factions.entityListener.canDamagerHurtDamagee(event, true);
				}
				
				
				if(!CombatUtil.preventDamageCall(damagee, damager) && DungeonAPI.canhit(event) && faction)
				{
					PVPPlayer PVPdamager = Commands.getPVPPlayer(damager);
					if(PVPdamager.getHitCooldown() < 1)
					{
						dealtDamage = Damage.calcDamage(damager) + rand.nextInt(Damage.calcDamage(damager) / 10);
						if(PVPdamager.getHitCooldown() < 1)
						{
							PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, dealtDamage, event.getCause());
							Bukkit.getPluginManager().callEvent(pbdEvent);
							if(!pbdEvent.isCancelled())
							{
								dealtDamage = pbdEvent.getDamage();
								PVPDamagee.Damage(dealtDamage);
								
								String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + PVPDamagee.gethealth();
								Bukkit.getMessenger().dispatchIncomingMessage(damager, "Scoreboard", message.getBytes());
							}
						}
						PVPdamager.setHitCoolDown(4);
						PVPdamager.setCombatCoolDown(40);
						PVPDamagee.setCombatCoolDown(40);
						if(Main.debug == true || Main.pvpstats.contains(damager))
						{
							damager.sendMessage(ChatColor.RED + "DAMAGE DEALT: " + dealtDamage);
						}
					}
					else if(PVPdamager.getHitCooldown() > 0){
						event.setCancelled(true);
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
				if(Main.debug == true)
				{
					Bukkit.broadcastMessage(ChatColor.RED + " DAMAGE DEALT: " + dealtDamage);
				}
			}
			if(PVPDamagee.god == true){
				event.setCancelled(true);
			}
			else{
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
	public void playerJoin(PlayerJoinEvent event){
		PVPPlayer newPVP = new PVPPlayer(event.getPlayer());
		Player player = event.getPlayer();
		if(player.getWorld().getName().contains("world")){
			if(player.hasPermission("particles.admin")){
				player.sendMessage("Welcome Administrator " + player);
				newPVP.setGod(true);
			}
			else{
				player.teleport(new Location(player.getWorld(), -730.50, 105, 319.50));
			}
		}
		Main.PVP.add(newPVP);
		Damage.calcArmor(event.getPlayer());
		newPVP.sethealth(newPVP.getMaxHealth());
	}
	@EventHandler
	public void respawn(PlayerRespawnEvent event){
		Player player = event.getPlayer();
		if(Commands.getPVPPlayer(player) == null){
			PVPPlayer newPVP = new PVPPlayer(player);
			Main.PVP.add(newPVP);
		}
		Damage.calcArmor(event.getPlayer());
		PVPPlayer PVPPlayer = Commands.getPVPPlayer(player);
		PVPPlayer.setIsDead(false);
		PVPPlayer.sethealth(500);
	}
	@EventHandler
	public void regenEvent(EntityRegainHealthEvent event){
		if (event.getEntity() instanceof Player){
			if(event.getRegainReason() == RegainReason.MAGIC){
				Player player = (Player)event.getEntity();
				if(Commands.getPVPPlayer(player) == null){
					PVPPlayer newPVP = new PVPPlayer(player);
					Main.PVP.add(newPVP);
				}
				PVPPlayer PVPPlayer = Commands.getPVPPlayer(player);
				PVPPlayer.sethealth(PVPPlayer.gethealth() + 1000);
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
					Commands.getPVPPlayer(player).setArmorEventLastTick(2);
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
			if(Commands.getPVPPlayer(player) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(player);
				Main.PVP.add(newPVP);
			}
			PVPPlayer pvp = Commands.getPVPPlayer(player);
			if(event.getCause().equals(DamageCause.STARVATION))
			{
				event.setDamage(0);
			}
			if(Commands.getPVPPlayer(player).god == true){
				event.setCancelled(true);
			}
			else if(event.getCause().equals(DamageCause.FIRE_TICK))
			{
				int damage = LoadSave.Firetick;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
			}
			else if(event.getCause().equals(DamageCause.VOID))
			{
				int damage = LoadSave.Voide;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.CONTACT))
			{
				int damage = LoadSave.Contact;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.DROWNING))
			{
				int damage = LoadSave.Drowning;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.POISON))
			{
				int damage = LoadSave.Poison;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.FALL))
			{
				int damage = LoadSave.Fall;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.WITHER))
			{
				int damage = LoadSave.Wither;
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
				if(pbdEvent.isCancelled())
					return;
				pvp.Damage(pbdEvent.getDamage());
				event.setDamage(0);
			}
			else if(event.getCause() != DamageCause.PROJECTILE && event.getCause() != DamageCause.ENTITY_ATTACK)
			{
				int damage = event.getDamage();
				PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
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
		if(Commands.getPVPPlayer(player) == null){
			PVPPlayer newPVP = new PVPPlayer(player);
			Main.PVP.add(newPVP);
		}
		PVPPlayer PVPPlayer = Commands.getPVPPlayer(event.getEntity().getPlayer());
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
    			if(Commands.getPVPPlayer(player) == null)
    			{
    				PVPPlayer newPVP = new PVPPlayer(player);
    				Main.PVP.add(newPVP);
    			}
    			Commands.getPVPPlayer(player).setArmorEventLastTick(2);
    		}
    	}
	    if(event.getSlotType() == SlotType.ARMOR)
	    {
	    	Player player = (Player) event.getWhoClicked();
			if(Commands.getPVPPlayer(player) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(player);
				Main.PVP.add(newPVP);
			}
	        Commands.getPVPPlayer(player).setArmorEventLastTick(2);
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
	
		
		