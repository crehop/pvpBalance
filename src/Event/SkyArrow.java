package Event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class SkyArrow {
	public static int grace = 0;
	public static int numberOfPlayers = 0;
	public static Player winner = null;
	public static boolean active = false;
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static String getEventName = ChatColor.AQUA + "Sky" + ChatColor.RED + "Arrow";
	public static void join(Player player){
		if(players.size() == 0){
			grace = 150;
		}
		players.add(player);
		numberOfPlayers++;
		ItemStack prize = new ItemStack(Material.BOW);
		ItemStack prize2 = new ItemStack(Material.ARROW);
		ItemMeta meta = prize.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "SkyArrow BOW");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "Woot Woot");
		meta.setLore(lore);
		prize.setItemMeta(meta);
		prize.setAmount(1);
		prize.addEnchantment(Enchantment.ARROW_INFINITE,1);
		prize.addEnchantment(Enchantment.DURABILITY,2);
		prize2.setAmount(1);
		player.getInventory().addItem(prize);
		player.getInventory().addItem(prize2);
	}
	public static void leave(Player player){
		players.remove(player);
	}
	public static void reset(){
		grace = 0;
		numberOfPlayers = 0;
		winner = null;
		active = false;
		players.clear();
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
		prize.setAmount(2);
		player.getInventory().addItem(prize);
		player.getInventory().addItem(prize2);
		Bukkit.broadcastMessage(ChatColor.GREEN + player.getCustomName() + ChatColor.YELLOW  + " has won " + getEventName);
		EventRunner.endEvent();
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
		Bukkit.broadcastMessage("GRACE = " + grace + " PLAYER COUNT =" + players.size());
		if(grace == 1){
			if(players.size() >= 5){
				setActive(true);
				grace = 0;
				for(Player player:players){
					PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
					pvp.setEventGrace(false);
				}
			}
			else{
				Bukkit.broadcastMessage(ChatColor.RED + "Not enough players to start " + getEventName + ", Need 5. Extending grace period 5 minutes "+ChatColor.GREEN + "/play");
				grace = 45;
			}
		}
		if(grace > 0){
			for(Player player:players){
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				pvp.setEventGrace(true);
			}
			grace--;
			setActive(false);
		}
		if(grace == 0){
			if(players.size() == 1 && grace <= 0){
				winner = players.get(0);
				winner(players.get(0));
			}
		}
	}
	public static boolean checkParticipant(String playername) {
		for(Player check : players){
			if(check.getName().toString() == playername){
				return true;
			}
		}
		return false;
	}

}
