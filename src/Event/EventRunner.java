package Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
				Random rand = new Random();
				int check = rand.nextInt(3) + 1;
				if(check == 1){
					eventName = SkyArrow.getEventName();
				}
				if(check == 2){
					eventName = CrazyRace.getEventName();
					CrazyRace.setGrace(150);
				}
				eventActive = true;
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "EVENT STARTING " + eventName + ChatColor.RED + "" + ChatColor.BOLD + " TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " TO JOIN");				
			}
			if(tick == 3630){
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "EVENT STARTING " + eventName + ChatColor.RED + "" + ChatColor.BOLD + " TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " TO JOIN");
			}
			if(tick == 3660){
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "EVENT STARTING " + eventName + ChatColor.RED + "" + ChatColor.BOLD + " TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " TO JOIN");
			}		
			if(tick == 3690){
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "EVENT STARTING " + eventName + ChatColor.RED + "" + ChatColor.BOLD + " TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " TO JOIN");
			}		
			if(tick == 3720){
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "EVENT STARTING " + eventName + ChatColor.RED + "" + ChatColor.BOLD + " TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " TO JOIN");			}			
			if(eventName.equalsIgnoreCase(SkyArrow.getEventName())) SkyArrow.tick();
			if(eventName.equalsIgnoreCase(CrazyRace.getEventName())) CrazyRace.tick();

		}
		else{
			eventActive = false;
			SkyArrow.setActive(false);
			CrazyRace.setActive(false);
			eventName = "";
			participants.clear();
		}
	}
	public static void joinEvent(Player player){
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(pvp.isInCombat() == false){
			if(eventActive == false){
				player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, NO EVENTS RUNNING. NEXT EVENT IN " + ChatColor.GREEN + (3600 - tick)/60 + " MINUTES");
				return;
			}
			if(eventName.equalsIgnoreCase(SkyArrow.getEventName())){
				if(SkyArrow.active == true){
					player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, EVENT IN PROGRESS. PLEASE WAIT TILL THE NEXT EVENT!");
					SkyArrow.listPlayers(player);
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
			if(eventName.equalsIgnoreCase(CrazyRace.getEventName())){
				if(eventName.equalsIgnoreCase(CrazyRace.getEventName())){
					if(CrazyRace.active == true){
						player.sendMessage(ChatColor.RED + "Joining event already in progress");
						CrazyRace.listPlayers(player);
					}
					if(Util.InventoryManager.storeInventory(player) == true){
						storeLocation(player);
						participants.add(player);
						if(eventActive == false)totalPlayers++;
						player.sendMessage(ChatColor.AQUA + "Welcome to " + ChatColor.GREEN + eventName + ChatColor.AQUA + " event will begin shortly!");
						pvp.setEventGrace(true);
						pvp.setInEvent(true);
						CrazyRace.join(player);
						return;
					}
					else{
						player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, PLEASE PUT YOUR ARMOR IN YOUR INVENTORY AND TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " AGAIN!");
						return;
					}
			}
		}
		else{
			player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, YOU ARE IN COMBAT! LEAVE COMBAT THEN TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " AGAIN!");
			}
		}
	}
	public static void endEvent(){
		participants.clear();
		deaths.clear();
		eventName = "";
		setTick(1);
		eventActive = false;
		totalPlayers = 0;
	}
	public static void leaveEvent(Player player){
		if(participants.contains(player)){
			participants.remove(player);
			if(eventActive == false)totalPlayers--;
			if(deaths.containsKey(player.getName().toString())) deaths.remove(player.getName().toString());
			player.sendMessage(ChatColor.AQUA + "You have left " + ChatColor.GREEN + eventName + ChatColor.AQUA + " thank you for playing!");
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			pvp.sethealth(pvp.getMaxHealth());
			returnToPreviousLocation(player);
			Util.InventoryManager.getInventory(player);
			if(EventRunner.getActiveEvent().equalsIgnoreCase(SkyArrow.getEventName())){
				if(SkyArrow.players.size() == 1 && SkyArrow.isActive()){
					for(Player player2:SkyArrow.players){
						SkyArrow.setWinner(player2);
						SkyArrow.leave(player2);
					}
				}
				if(SkyArrow.winner != null){
					if(SkyArrow.winner.getName().toString().equalsIgnoreCase(player.getName().toString())){
						SkyArrow.winner(player);
					}
				}
			}
			if(EventRunner.getActiveEvent().equalsIgnoreCase(CrazyRace.getEventName())){
				if(CrazyRace.winner != null){
					if(CrazyRace.winner.getName().toString().equalsIgnoreCase(player.getName().toString())){
						CrazyRace.winner(player);
					}
				}
			}
		}
		else{
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU ARE NOT IN AN EVENT! ");
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
