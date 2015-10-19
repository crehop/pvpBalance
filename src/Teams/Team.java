package Teams;

import java.util.ArrayList;
import org.bukkit.entity.Player;

public class Team {

	public static ArrayList<Player> red = new ArrayList<Player>();
	public static ArrayList<Player> blue = new ArrayList<Player>();
	
	public static void addPlayer(Player p) {
		if (blue.size() == 0 && red.size() == 0) {
			red.add(p);
			return;
		}
		if (blue.size() > red.size()) {
			red.add(p);
			return;
		}
		if (red.size() > blue.size()) {
			blue.add(p);
			return;
		}
	}
	
	public static void removePlayer(Player p) {
		if (blue.contains(p)) {
			blue.remove(p);
			return;
		}
		if (red.contains(p)) {
			red.remove(p);
			return;
		}
	}
	
	public static int getSize(String name) {
		if (name == "red") {
			return red.size();
		}
		if (name == "blue") {
			return blue.size();
		}
		return 0;
	}
	
	public static void removeAll() {
		red.clear();
		blue.clear();
	}
	
}
