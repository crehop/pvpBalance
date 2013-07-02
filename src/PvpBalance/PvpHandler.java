package PvpBalance;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class PvpHandler
{
	private static List<PVPPlayer> players = new ArrayList<PVPPlayer>();
	
	public static PVPPlayer getPvpPlayer(Player player)
	{
		for(PVPPlayer pp : players)
		{
			if(pp == null)
				continue;
			if(pp.getPlayer().equals(player))
				return pp;
		}
		return null;
	}
	
	public static boolean addPvpPlayer(PVPPlayer pp)
	{
		return players.add(pp);
	}
	
	public static boolean removePvpPlayer(PVPPlayer pp)
	{
		return players.remove(pp);
	}
	
	public static boolean removePvpPlayer(Player player)
	{
		for(PVPPlayer pp : players)
		{
			if(pp == null)
				continue;
			if(pp.getPlayer().equals(player))
				return players.remove(pp);
		}
		return false;
	}
	
	public static List<PVPPlayer> getPvpPlayers()
	{
		return players;
	}
}
