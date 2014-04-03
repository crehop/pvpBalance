package Event;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class EventRunner {
	public static ArrayList<Player> participants = new ArrayList<>();
	public static HashMap<String, Integer> deaths = new HashMap<String, Integer>();
	public static HashMap<String, Location> previousLOC = new HashMap<String, Location>();
	public static String eventName = "";
	private static int tick = 0;
	private static boolean eventActive = false;
	private static int totalPlayers = 0;
	public static void tick(){
		tick++;
		if(tick > 3600){
			if(eventActive == false){
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "NEW PRIZED EVENT STARTING TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " TO JOIN");
				eventActive = true;
				eventName = SkyArrow.getEventName;
			}
			if(tick == 3630){
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "NEW PRIZED EVENT STARTING TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " TO JOIN");
			}
			if(tick == 3660){
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "NEW PRIZED EVENT STARTING TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " TO JOIN");
			}		
			if(tick == 3690){
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "NEW PRIZED EVENT STARTING TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " TO JOIN");
			}			
			SkyArrow.tick();
		}
		else{
			eventActive = false;
			SkyArrow.setActive(false);
			SkyArrow.players.clear();
			participants.clear();
		}
	}
	public static void joinEvent(Player player){
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(pvp.isInCombat() == false){
			if(SkyArrow.active == true){
				player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, EVENT IN PROGRESS. PLEASE WAIT TILL THE NEXT EVENT!");
				return;
			}
			if(eventActive == false){
				player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, NO EVENTS RUNNING. NEXT EVENT IN " + ChatColor.GREEN + (3600 - tick)/60 + " MINUTES");
				return;
			}
			if(Util.InventoryManager.storeInventory(player) == true){
				storeLocation(player);
				participants.add(player);
				if(eventActive == false)totalPlayers++;
				player.sendMessage(ChatColor.AQUA + "Welcome to " + ChatColor.GREEN + eventName + ChatColor.AQUA + " event will begin shortly!");
				pvp.setEventGrace(true);
				pvp.setInEvent(true);
				SkyArrow.join(player);
				return;
			}
			else{
				player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, PLEASE PUT YOUR ARMOR IN YOUR INVENTORY AND TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " AGAIN!");
				return;
			}
		}
		else{
			player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, YOU ARE IN COMBAT! LEAVE COMBAT THEN TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " AGAIN!");
		}
	}
	public static void endEvent(){
		participants.clear();
		deaths.clear();
		eventName = "";
		tick = 0;
		eventActive = false;
		totalPlayers = 0;
	}
	public static void leaveEvent(Player player){
		participants.remove(player);
		if(eventActive == false)totalPlayers--;
		if(deaths.containsKey(player.getName().toString())) deaths.remove(player.getName().toString());
		player.sendMessage(ChatColor.AQUA + "You have left " + ChatColor.GREEN + eventName + ChatColor.AQUA + " thank you for playing!");
		returnToPreviousLocation(player);
		Util.InventoryManager.getInventory(player);
		if(SkyArrow.players.size() == 1 && SkyArrow.isActive()){
			for(Player player2:SkyArrow.players){
				SkyArrow.setWinner(player2);
				SkyArrow.leave(player2);
			}
		}
		if(SkyArrow.winner != null){
			if(SkyArrow.winner.getName().toString() == player.getName().toString()){
				SkyArrow.winner(player);
			}
		}
	}
	public static void death(Player player){
		if(deaths.containsKey(player.getName().toString())){
			deaths.put(player.getName().toString(), deaths.get(player.getName().toString() + 1));
		}
		else{
			deaths.put(player.getName().toString(), 1);
		}
	}
	public static void storeLocation(Player player){
		previousLOC.put(player.getName().toString(), player.getLocation());
	}
	public static void returnToPreviousLocation(Player player){
		if(previousLOC.containsKey(player.getName().toString())){
			player.teleport(previousLOC.get(player.getName().toString()));
			previousLOC.remove(player.getName().toString());
		}
	}
	public static void removeFromEvent(Player player){
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(participants.contains(player)){
			participants.remove(player);
			pvp.setEventGrace(false);
			pvp.setInEvent(false);
			return;
		}
	}
	public static boolean isActive(){
		return eventActive;
	}
	public static String getActiveEvent() {
		return eventName;
	}
	public static void setTick(int newTick){
		tick = newTick;
	}
	public static int getTick(){
		return tick;
	}
}
