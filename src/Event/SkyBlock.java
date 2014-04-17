package Event;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldedit.WorldEdit;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class SkyBlock {/*
	public static int grace = 0;
	public static int numberOfPlayers = 0;
	public static Player winner = null;
	public static boolean active = false;
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static void join(Player player){
		if(players.size() == 0){
			grace = 150;
		}
		players.add(player);
		SkyBlock.teleportToStart(player);
		numberOfPlayers++;
		ItemStack prize = new ItemStack(Material.CACTUS);
		ItemStack prize2 = new ItemStack(Material.FISHING_ROD);
		ItemStack prize3 = new ItemStack(Material.BREAD);
		ItemStack prize4 = new ItemStack(Material.IRON_CHESTPLATE);
		ItemStack prize5 = new ItemStack(Material.WOOD_SWORD);
		prize2.setAmount(1);
		prize3.setAmount(64);
		player.getInventory().addItem(prize);
		player.getInventory().addItem(prize2);
		player.getInventory().addItem(prize3);
		player.getInventory().addItem(prize4);
		player.getInventory().addItem(prize5);
	}
	private static void teleportToStart(Player player) {
		Location start = new Location(Bukkit.getWorld("world"),-956,151,-25);
		player.teleport(start);
	}
	public static void leave(Player player){
		for(ItemStack item:player.getInventory().getArmorContents()){
			item.setType(Material.AIR);
		}
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		pvp.setInEvent(false);
		pvp.setEventGrace(false);
		players.remove(player);
		EventRunner.leaveEvent(player);
	}
	public static void reset(){
		grace = 0;
		numberOfPlayers = 0;
		winner = null;
		setActive(false);
		for(Player player:players){
			SkyBlock.leave(player);
		}
		players.clear();
		EventRunner.endEvent();
	}
	public static void winner(Player player){
		ItemStack prize = new ItemStack(Material.DRAGON_EGG);
		ItemMeta meta = prize.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Event Prize Egg");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "/pb use" + ChatColor.AQUA + " to open!");
		meta.setLore(lore);
		prize.setItemMeta(meta);
		prize.setAmount(2);
		ItemStack prize2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta2 = prize2.getItemMeta();
		meta2.setDisplayName(ChatColor.YELLOW + "Armor Token");
		List<String> lore2 = new ArrayList<String>();
		lore2.add(ChatColor.GREEN + "/rules armor" + ChatColor.AQUA + " for more information!");
		meta2.setLore(lore2);
		prize2.setItemMeta(meta2);
		player.getInventory().addItem(prize2);
		Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW  + " has won " + getEventName());
		ItemStack prize3 = new ItemStack(Material.BOW);
		ItemMeta meta3 = prize3.getItemMeta();
		meta3.setDisplayName(ChatColor.YELLOW + "SkyBlock BOW");
		List<String> lore3 = new ArrayList<String>();
		lore3.add(ChatColor.RED + "Woot Woot");
		meta3.setLore(lore3);
		prize3.setItemMeta(meta3);
		prize3.setAmount(1);
		prize3.addEnchantment(Enchantment.ARROW_INFINITE,1);
		player.getInventory().addItem(prize);
		player.getInventory().addItem(prize2);
		player.getInventory().addItem(prize3);
	}
	public static void setActive(boolean state){
		active = state;
	}
	public static boolean isActive(){
		return active;
	}
	public static void setGrace(int graceTime){
		grace = graceTime;
	}
	public static int getGrace(){
		return grace;
	}
	public static void tick(){
		if(players.size() > 0){
			for (Iterator it = players.iterator(); it.hasNext();) {
				try{
					Player player = (Player) it.next();
					if(player == null){
						it.remove();
					}
					if(player.isOnline() == false){
						it.remove();
					}
					PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
					if(pvp.isInEvent() == false) pvp.setInEvent(true);
				}catch(ConcurrentModificationException e){
					e.printStackTrace();
					continue;
				}
			}
		}
		if(grace <= 5 && grace != 0){
			for(Player player:players){
				player.sendMessage(ChatColor.YELLOW + "Event will start in "+ ChatColor.RED + grace + "" + ChatColor.YELLOW + " seconds.");
			}
		}
		if(grace%10 == 0 && grace != 0){
			for(Player player:players){
				player.sendMessage(ChatColor.GREEN + "Joining grace period, Event will start in "+ ChatColor.RED + grace + "" + ChatColor.GREEN + " seconds.");
			}
		}
		if(grace == 1){
			if(players.size() == 0){
				SkyBlock.reset();
			}
			if(players.size() >= 5){
				setActive(true);
				grace = 0;
				for(Player player:players){
					PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
					pvp.setEventGrace(false);
				}
				Bukkit.broadcastMessage(getEventName() + "" + ChatColor.GREEN + " Has Started with " + ChatColor.RED + players.size() + "" + ChatColor.GREEN + " players GOOD LUCK");
			}
			else{
				for(Player player:players){
					player.sendMessage(ChatColor.RED + "Not enough players to start " + getEventName() + ", Need 5. Extending grace period 30 Seconds!");
				}
				grace = 30;
			}
		}
		if(grace > 0){
			for(Player player:players){
				if(player != null){
					PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
					if(pvp != null)	pvp.setEventGrace(true);
				}
			}
			grace--;
		}
		if(grace <= 0){
			for(Player player:players){
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				if(pvp.flyZone == false){
					SkyBlock.leave(player);
					player.sendMessage("You have Exited the arena!");
				}
			}
		}
		if(players.size() == 0 && active == true){
			SkyBlock.reset();
		}
	}
	public static boolean checkParticipant(String playername) {
		for(Player check : players){
			if(check.getName().toString().equalsIgnoreCase(playername)){
				return true;
			}
		}
		return false;
	}
	public static void setWinner(Player player) {
		winner = player;
	}
	public static void simulateDeath(Player player){
		PVPPlayer pvpDeath = PvpHandler.getPvpPlayer(player);{
		PVPPlayer pvpKiller = PvpHandler.getPvpPlayer(pvpDeath.getLastHitBy());
		pvpDeath.sethealth(pvpDeath.getMaxHealth());
		pvpKiller.sethealth(pvpKiller.getMaxHealth());
		pvpKiller .getPlayer().sendMessage(SkyBlock.getEventName() + ChatColor.GREEN + ": You have killed " + player.getDisplayName() + " and gained full health!");
		SkyBlock.leave(player);
		return;
		}
	}
	public static String getEventName(){
		return ChatColor.AQUA + "Sky" + ChatColor.RED + "Arrow";
	}
	public static void listPlayers(Player playerToTell){
		playerToTell.sendMessage(getEventName() + ChatColor.YELLOW + " Remaining Players :" + players.size());
		int counter = 0;
		for(Player player:players){
			counter++;
			playerToTell.sendMessage(counter + ":" + player.getName());
		}
	}*/
}