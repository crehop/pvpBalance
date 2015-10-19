package Event;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class BattleRoyale {

	public static ArrayList<Player> players = new ArrayList<Player>();
	public static boolean active = false;
	public static int grace = 0;
	
	public void join() {
		if (players.size() == 0) grace = 200;
		
	}
	
}
