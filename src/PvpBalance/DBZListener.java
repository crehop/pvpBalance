package PvpBalance;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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
		if(Commands.getPVPPlayer(quitplayer).isInCombat == true){
			quitplayer.setHealth(0);
		}
	}
	
	@EventHandler
	public void onPlayerDamageEvent(EntityDamageByEntityEvent event){
		boolean canhit = DungeonAPI.canhit(event);
		int dealtDamage = 0;
		/*if (event.getDamager() instanceof Player){
			//Player damager = (Player)event.getDamager();
			PVPPlayer PVPdamager = Commands.getPVPPlayer(damager);
		}*/
		Entity e = event.getEntity();
		if (e instanceof Player){
			int rawDamage = event.getDamage();
			Random rand = new Random();
			Player damagee = (Player) e;
			if(Commands.getPVPPlayer(damagee) == null){
				PVPPlayer newPVP = new PVPPlayer(damagee);
				Main.PVP.add(newPVP);
			}
			PVPPlayer PVPDamagee = Commands.getPVPPlayer(damagee);
			if(event.getCause() == DamageCause.PROJECTILE){
				if(CombatUtil.preventDamageCall(damagee, damagee) == true && DungeonAPI.canhit(event) == true){
					event.setCancelled(true);
					canhit = false;
				}
			}
			if(event.getDamager() instanceof Player && canhit != false && event.getDamage() > 0){
				Player damager = (Player)event.getDamager();
				if(Commands.getPVPPlayer(damager) == null){
					PVPPlayer newPVP = new PVPPlayer(damager);
					Main.PVP.add(newPVP);
				}
				Boolean faction = true;
				if(plugin.faction)
				{
					faction = plugin.factions.entityListener.canDamagerHurtDamagee(event, true);
				}
				
				
				if(CombatUtil.preventDamageCall(damagee, damager) == false && DungeonAPI.canhit(event) == true && faction){
					PVPPlayer PVPdamager = Commands.getPVPPlayer(damager);
					if(PVPdamager.getHitCooldown() < 1){
						dealtDamage = Damage.calcDamage(damager) + rand.nextInt(Damage.calcDamage(damager) / 10);
						if(PVPdamager.getHitCooldown() < 1){
							PVPDamagee.Damage(dealtDamage);
						}
						PVPdamager.setHitCoolDown(5);
						PVPdamager.setCombatCoolDown(40);
						PVPDamagee.setCombatCoolDown(40);
						if(Main.debug == true || Main.pvpstats.contains(damager)){
							damager.sendMessage(ChatColor.RED + "DAMAGE DEALT: " + dealtDamage);
						}
					}
				}
				else{
					canhit = false;
				}
			}
			else if(canhit != false && event.getDamage() > 0 && event.getDamager().getClass() != Player.class){
				dealtDamage = rawDamage * LoadSave.Multi;
				PVPDamagee.Damage(dealtDamage);
				if(Main.debug == true){
					Bukkit.broadcastMessage(ChatColor.RED + " DAMAGE DEALT: " + dealtDamage);
				}
			}
			event.setDamage(0);
		}
	}
	@EventHandler
	public void playerJoin(PlayerJoinEvent event){
		PVPPlayer newPVP = new PVPPlayer(event.getPlayer());
		Player player = event.getPlayer();
		if(player.getWorld().getName().contains("world")){
			if(player.hasPermission("particles.admin")){
				player.sendMessage("Welcome Administrator " + player);
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
				PVPPlayer.sethealth(PVPPlayer.gethealth() + 700);
			}
		}
		event.setCancelled(true);
	}
	@EventHandler
	public void playerinteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.useItemInHand() == Result.DEFAULT && event.isBlockInHand() == false && event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(player.getItemInHand().getType() == Material.DIAMOND_BOOTS ||
				player.getItemInHand().getType() == Material.DIAMOND_CHESTPLATE ||
				player.getItemInHand().getType() == Material.DIAMOND_LEGGINGS ||
				player.getItemInHand().getType() == Material.DIAMOND_HELMET ||
				player.getItemInHand().getType() == Material.IRON_CHESTPLATE ||
				player.getItemInHand().getType() == Material.IRON_LEGGINGS ||
				player.getItemInHand().getType() == Material.IRON_HELMET ||
				player.getItemInHand().getType() == Material.IRON_BOOTS ||
				player.getItemInHand().getType() == Material.LEATHER_CHESTPLATE ||
				player.getItemInHand().getType() == Material.LEATHER_LEGGINGS ||
				player.getItemInHand().getType() == Material.LEATHER_HELMET ||
				player.getItemInHand().getType() == Material.LEATHER_BOOTS ||
				player.getItemInHand().getType() == Material.CHAINMAIL_CHESTPLATE ||
				player.getItemInHand().getType() == Material.CHAINMAIL_LEGGINGS ||
				player.getItemInHand().getType() == Material.CHAINMAIL_HELMET ||
				player.getItemInHand().getType() == Material.CHAINMAIL_BOOTS ||
				player.getItemInHand().getType() == Material.GOLD_CHESTPLATE ||
				player.getItemInHand().getType() == Material.GOLD_LEGGINGS ||
				player.getItemInHand().getType() == Material.GOLD_HELMET ||
				player.getItemInHand().getType() == Material.GOLD_BOOTS){
				
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
		if (event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			if(Commands.getPVPPlayer(player) == null){
				PVPPlayer newPVP = new PVPPlayer(player);
				Main.PVP.add(newPVP);
			}
			PVPPlayer pvp = Commands.getPVPPlayer(player);
			if(event.getCause().equals(DamageCause.STARVATION)){
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.FIRE_TICK)){
				pvp.Damage(LoadSave.Firetick);
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.VOID)){
				pvp.Damage(LoadSave.Voide);
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.CONTACT)){
				pvp.Damage(LoadSave.Contact);
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.DROWNING)){
				pvp.Damage(LoadSave.Drowning);
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.POISON)){
				pvp.Damage(LoadSave.Poison);
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.FALL)){
				pvp.Damage(LoadSave.Fall);
				event.setDamage(0);
			}
			else if(event.getCause().equals(DamageCause.WITHER)){
				pvp.Damage(LoadSave.Wither);
				event.setDamage(0);
			}
			else if(event.getCause() != DamageCause.PROJECTILE && event.getCause() != DamageCause.ENTITY_ATTACK)
			{
				pvp.Damage(event.getDamage());
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
	public void onInventoryClick(InventoryClickEvent event){
    	if(event.isShiftClick()){
    		if(event.getCurrentItem().getType() == Material.DIAMOND_BOOTS ||
    			event.getCurrentItem().getType() == Material.DIAMOND_CHESTPLATE ||
				event.getCurrentItem().getType() == Material.DIAMOND_LEGGINGS ||
				event.getCurrentItem().getType() == Material.DIAMOND_HELMET ||
				event.getCurrentItem().getType() == Material.IRON_CHESTPLATE ||
				event.getCurrentItem().getType() == Material.IRON_LEGGINGS ||
				event.getCurrentItem().getType() == Material.IRON_HELMET ||
				event.getCurrentItem().getType() == Material.IRON_BOOTS ||
				event.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE ||
				event.getCurrentItem().getType() == Material.LEATHER_LEGGINGS ||
				event.getCurrentItem().getType() == Material.LEATHER_HELMET ||
				event.getCurrentItem().getType() == Material.LEATHER_BOOTS ||
				event.getCurrentItem().getType() == Material.CHAINMAIL_CHESTPLATE ||
				event.getCurrentItem().getType() == Material.CHAINMAIL_LEGGINGS ||
				event.getCurrentItem().getType() == Material.CHAINMAIL_HELMET ||
				event.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS ||
				event.getCurrentItem().getType() == Material.GOLD_CHESTPLATE ||
				event.getCurrentItem().getType() == Material.GOLD_LEGGINGS ||
				event.getCurrentItem().getType() == Material.GOLD_HELMET ||
				event.getCurrentItem().getType() == Material.GOLD_BOOTS){
    				Player player = (Player) event.getWhoClicked();
    					if(Commands.getPVPPlayer(player) == null){
    						PVPPlayer newPVP = new PVPPlayer(player);
    						Main.PVP.add(newPVP);
    					}
    					Commands.getPVPPlayer(player).setArmorEventLastTick(2);
    		}
    	}
	    if(event.getSlotType() == SlotType.ARMOR){
	    	Player player = (Player) event.getWhoClicked();
			if(Commands.getPVPPlayer(player) == null){
				PVPPlayer newPVP = new PVPPlayer(player);
				Main.PVP.add(newPVP);
			}
	        Commands.getPVPPlayer(player).setArmorEventLastTick(2);
	    }
	}
}
	
		
		