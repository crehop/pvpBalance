package Event;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class CrazyRace {
	
	public static Location starter = new Location(Bukkit.getWorld("event"), -52 , 38 , 9);
	public static Location end1 = new Location(Bukkit.getWorld("event"), -30 , 39, -5);
	public static Location end2 = new Location(Bukkit.getWorld("event"), -32 , 39, -3);
	public static int grace = 0;
	public static int numberOfPlayers = 0;
	public static Player winner = null;
	public static boolean active = false;
	public static ArrayList<Player> players2 = new ArrayList<Player>();
	public static int timer = 0;
	public static World world = Bukkit.getWorld("event");
	
	public static void join(Player player){
		if (CrazyRace.timer > 0) {
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "GAME IS ALREADY IN PROGRESS");
		}
		if(players2.size() == 0){
			starter.getBlock().setType(Material.COAL);
		}
		players2.add(player);
		CrazyRace.teleportToStart(player);
		numberOfPlayers++;
		ItemStack prize = new ItemStack(Material.BREAD);
		prize.setAmount(64);
		player.getInventory().addItem(prize);
	}
	private static void teleportToStart(Player player) {
		Location start = new Location(Bukkit.getWorld("event"),-44,40,-4);
		player.teleport(start);
	}
	public static void leave(Player player){
		for(ItemStack item:player.getInventory().getArmorContents()){
			item.setType(Material.AIR);
		}
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		pvp.setInEvent(false);
		pvp.setEventGrace(false);
		for(Iterator<Player> it = players2.iterator(); it.hasNext();){
			Player player2 = (Player)it.next();
			if(player2.getName().equalsIgnoreCase(player.getName())){
				it.remove();
			}
		}
		EventRunner.leaveEvent(player);
	}
	public static void reset(){
		setActive(false);
		timer = 0;
		grace = 0;
		numberOfPlayers = 0;
		winner = null;
		starter.getBlock().setType(Material.COAL_BLOCK);
		for(Player player:players2){
			CrazyRace.leave(player);
		}
		players2.clear();
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
		ItemStack prize2 = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta2 = prize2.getItemMeta();
		meta2.setDisplayName(ChatColor.YELLOW + "Armor Token");
		List<String> lore2 = new ArrayList<String>();
		lore2.add(ChatColor.GREEN + "/rules armor" + ChatColor.AQUA + " for more information!");
		meta2.setLore(lore2);
		prize2.setItemMeta(meta2);
		player.getInventory().addItem(prize2);
		Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW  + " has won " + getEventName());
		ItemStack prize3 = new ItemStack(Material.DIAMOND_BOOTS);
		prize3.addEnchantment(Enchantment.DURABILITY, 3);
		prize3.addEnchantment(Enchantment.THORNS, 3);
		ItemMeta meta3 = prize3.getItemMeta();
		meta3.setDisplayName(getEventName() + ChatColor.RED +" Boots");
		List<String> lore3 = new ArrayList<String>();
		lore3.add(ChatColor.RED + "Zoom Zoom");
		meta3.setLore(lore3);
		prize3.setItemMeta(meta3);
		prize3.setAmount(1);
		prize.setAmount(2);
		player.getInventory().addItem(prize);
		player.getInventory().addItem(prize2);
		player.getInventory().addItem(prize3);
		reset();
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
		if(winner != null){
			evacuate();
			for(Player player:players2){
				if (player.getLocation().getX() >= -30 && player.getLocation().getX() <= -32) {
					if (player.getLocation().getBlockZ() >= -3 && player.getLocation().getBlockZ() <= -5)
					setWinner(player);
				}
			}
			}
		if( players2.size() < 1 && grace < 1){
			reset();
		}
		if(players2.size() > 0){
			for(Iterator<Player> it = players2.iterator(); it.hasNext();) {
				try{
					Player player = (Player) it.next();
					if(player.getName() == null){
						it.remove();
					}
					if(player.isOnline() == false){
						it.remove();
					}
					if(!(player.getLocation().getWorld().toString().equalsIgnoreCase(starter.getWorld().toString()))){
						CrazyRace.simulateDeath(player);
					}
					if(player.getLocation().getY() < 22){
						CrazyRace.simulateDeath(player);
					}
					PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
					if(pvp.isInEvent() == false) pvp.setInEvent(true);
				}catch(ConcurrentModificationException e){
					e.printStackTrace();
				}
			}
		}
		if(CrazyRace.isActive()){
			if(timer > 0){
				timer--;
			}
			if(timer == 0){
				CrazyRace.evacuate();
				Bukkit.broadcastMessage(getEventName() + ChatColor.RED +" : Noone has won, 20 Minutes has passed. Ending Event!");
				CrazyRace.reset();
			}
		}
		if(grace <= 5 && grace != 0){
			for(Player player:players2){
				player.sendMessage(ChatColor.YELLOW + "Event will start in "+ ChatColor.RED + grace + "" + ChatColor.YELLOW + " seconds.");
			}
		}
		if(grace%10 == 0 && grace != 0){
			for(Player player:players2){
				player.sendMessage(ChatColor.GREEN + "Joining grace period, Event will start in "+ ChatColor.RED + grace + "" + ChatColor.GREEN + " seconds.");
			}
		}
		if(grace == 1){
			if(players2.size() >= 1){
				timer = 1200;
				setActive(true);
				starter.getBlock().setType(Material.REDSTONE_BLOCK);
				
				Bukkit.broadcastMessage(getEventName() + "" + ChatColor.GREEN + " Has Started with " + ChatColor.RED + players2.size() + "" + ChatColor.GREEN + " players GOOD LUCK");
			}
			else{
				for(Player player:players2){
					player.sendMessage(ChatColor.RED + "Not enough players to start " + getEventName() + ", Need 5. Extending grace period 30 Seconds!");
				}
				grace = 30;
			}
		}
		if(grace > 0){
			grace--;
		}
		if(grace <= 0){
			//TODO add player leave arena detection code here.
		}
		if(players2.size() == 0 && active == true){
			CrazyRace.reset();
		}
	}
	private static void evacuate() {
		for(Iterator<Player> it = players2.iterator(); it.hasNext();){
			Player player = (Player)it.next();
			it.remove();
			CrazyRace.leave(player);
		}
	}
	public static boolean checkParticipant(String playername) {
		for(Player check : players2){
			if(check.getName().toString().equalsIgnoreCase(playername)){
				return true;
			}
		}
		return false;
	}
	public static boolean checkParticipant(Player player) {
		for(Player check : players2){
			if(check.getName().toString().equalsIgnoreCase(player.getName().toString())){
				return true;
			}
		}
		return false;
	}
	public static void setWinner(Player player) {
		winner = player;
		evacuate();
	}
	public static void simulateDeath(Player player){
		PVPPlayer pvpDeath = PvpHandler.getPvpPlayer(player);
		if(pvpDeath != null)pvpDeath.sethealth(pvpDeath.getMaxHealth());
		player.sendMessage(ChatColor.RED + "You can type " + ChatColor.GREEN + "/leave" + ChatColor.RED + " to quit " + getEventName());
		CrazyRace.teleportToStart(player);
	}
	public static String getEventName(){
		return ChatColor.AQUA + "C" + ChatColor.GOLD + "r" + ChatColor.DARK_RED + "a" + ChatColor.GREEN + "z" + ChatColor.LIGHT_PURPLE + "y" + ChatColor.YELLOW + " Race";
	}
	public static void listPlayers(Player playerToTell){
		playerToTell.sendMessage(getEventName() + ChatColor.YELLOW + " Remaining Players : " + ChatColor.RED + "" + ChatColor.BOLD + players2.size());
		int counter = 0;
		for(Player player:players2){
			counter++;
			playerToTell.sendMessage(ChatColor.GREEN + "" + counter + ":" + ChatColor.AQUA + player.getName());
		}
	}
}