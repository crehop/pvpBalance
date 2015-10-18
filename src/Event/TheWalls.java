package Event;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;
import Teams.Team;

public class TheWalls {

	Team redTeam = new Team("Red");
	Team blueTeam = new Team("Blue");
	Team yellowTeam = new Team("Yellow");
	Team greenTeam = new Team("Green");
	
	public static boolean active = false;
	public static int numberOfPlayers = 0;
	public static int timeToJoin = 0;
	public static ArrayList<Player> players3 = new ArrayList<Player>();
	
	public static void join(Player p) {
		if (timeToJoin == 0) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "GAME IS ALREADY IN PROGRESS");
			return;
		}
		players3.add(p);
		numberOfPlayers++;
		teleportToStart(p);
	}
	
	public static void leave(Player p) {
		for(ItemStack item:p.getInventory().getArmorContents()){
			item.setType(Material.AIR);
		}
		players3.remove(p);
		numberOfPlayers--;
		PVPPlayer pvp = PvpHandler.getPvpPlayer(p);
		pvp.setInEvent(false);
		pvp.setEventGrace(false);
		EventRunner.leaveEvent(p);
	}
	
	public static void teleportToStart(Player p) {
		
	}
	
	public static void reset() {
		numberOfPlayers = 0;
		for(Player player:players3){
			TheWalls.leave(player);
		}
		players3.clear();
		EventRunner.endEvent();
	}
	
	public static void tick() {
		if (active == false) {
			if(timeToJoin % 10 == 0 && timeToJoin != 0) {
				for(Player player:players3) {
					player.sendMessage(ChatColor.GREEN + "Joining grace period, Event will start in "+ ChatColor.RED + timeToJoin + "" + ChatColor.GREEN + " seconds.");
				}
			}
			if (timeToJoin == 0) {
				if (players3.size() >= 4) {
				setActive(true);
				Bukkit.broadcastMessage(getEventName() + "" + ChatColor.GREEN + " Has Started with " + ChatColor.RED + players3.size() + "" + ChatColor.GREEN + " players GOOD LUCK");
				} else {
					reset();
				}
			}
		}
		if (active == true) {
			
		}
	}

	public Team getRedTeam() {
		return redTeam;
	}

	public void setRedTeam(Team redTeam) {
		this.redTeam = redTeam;
	}

	public Team getBlueTeam() {
		return blueTeam;
	}

	public void setBlueTeam(Team blueTeam) {
		this.blueTeam = blueTeam;
	}

	public Team getYellowTeam() {
		return yellowTeam;
	}

	public void setYellowTeam(Team yellowTeam) {
		this.yellowTeam = yellowTeam;
	}

	public Team getGreenTeam() {
		return greenTeam;
	}

	public void setGreenTeam(Team greenTeam) {
		this.greenTeam = greenTeam;
	}

	public static boolean isActive() {
		return active;
	}

	public static void setActive(boolean active) {
		TheWalls.active = active;
	}

	public static int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public static void setNumberOfPlayers(int numberOfPlayers) {
		TheWalls.numberOfPlayers = numberOfPlayers;
	}

	public static int getTimeToJoin() {
		return timeToJoin;
	}

	public static void setTimeToJoin(int timeToJoin) {
		TheWalls.timeToJoin = timeToJoin;
	}

	public static ArrayList<Player> getPlayers3() {
		return players3;
	}

	public static void setPlayers3(ArrayList<Player> players3) {
		TheWalls.players3 = players3;
	}
	
	public static String getEventName(){
		return ChatColor.GOLD + "The" + ChatColor.LIGHT_PURPLE + "Walls";
	}
}
