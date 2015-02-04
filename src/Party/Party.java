package Party;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpBalance;

public class Party
{
	private List<PVPPlayer> members = new ArrayList<PVPPlayer>(5);
	private PVPPlayer leader;
	
	public Party(PVPPlayer dp)
	{
		leader = dp;
		members.add(dp);
	}
	
	public boolean isMember(PVPPlayer dp)
	{
		return members.contains(dp);
	}
	
	public boolean isLeader(PVPPlayer dp)
	{
		return leader.equals(dp);
	}
	
	public boolean addPlayer(PVPPlayer dp)
	{
		dp.setParty(this);
		if(members.add(dp))
		{
			for(PVPPlayer m : members)
			{
				if(m.equals(dp))
					continue;
				m.sendMessage(ChatColor.AQUA + dp.getName() + ChatColor.GOLD + " Joined The Party");
			}
			return true;
		}
		return false;
	}
	
	public boolean isFull()
	{
		return members.size() >= 5 ? true:false;
	}
	
	public void checkDisband()
	{
		if(members.size() <= 1)
		{
			leader.sendMessage(ChatColor.GOLD + "Your Party Got Disbanded, Friendly Fire Will Stay Off For 10 More Seconds");
			leader.setParty(null);
			leader.setGhostParty(this);
			if(leader.usesPartyChat())
			{
				leader.setPartyChat(false);
				leader.sendMessage(ChatColor.DARK_AQUA + "Party Chat Disabled");
			}
			//leader.setLeaveTime(new Date().getTime());
			Bukkit.getScheduler().scheduleSyncDelayedTask(PvpBalance.plugin, new Runnable()
			{
				@Override
				public void run()
				{
					leader.setGhostParty(null);
				}
			},20*10);
		}
	}
	
	public void leave(PVPPlayer dp)
	{
		
		members.remove(dp);
		dp.sendMessage(ChatColor.GOLD + "You Left The Party, Friendly Fire Will Stay Off For 10 More Seconds");
		for(PVPPlayer temp : members)
		{
			temp.sendMessage(ChatColor.AQUA + dp.getName() + ChatColor.GOLD + " Has Left The Party, Friendly Fire Will Stay Off For 10 More Seconds");
		}
		if(isLeader(dp))
		{
			Random object = new Random();
			leader = members.get(object.nextInt(members.size()));
			leader.sendMessage(ChatColor.GOLD + "You Are Now Leader Of The Party");
			for(PVPPlayer temp : members)
			{
				if(isLeader(temp))
					continue;
				temp.sendMessage(ChatColor.AQUA + leader.getName() + ChatColor.GOLD + " Is Now Leader Of The Party");
			}
		}
		checkDisband();
	}
	
	public void kick(PVPPlayer dp)
	{
		members.remove(dp);
		dp.sendMessage(ChatColor.GOLD + "You Got Kicked From The Party, Friendly Fire Will Stay Off For 10 More Seconds");
		for(PVPPlayer temp : members)
		{
			temp.sendMessage(ChatColor.AQUA + dp.getName() + ChatColor.GOLD + " Got Kicked From The Party, Friendly Fire Will Stay Off For 10 More Seconds");
		}
		checkDisband();
	}
	
	public void sendMessage(String sender, String msg)
	{
		for(PVPPlayer temp : members)
		{
			temp.sendMessage(ChatColor.DARK_AQUA + "[Party] " + ChatColor.GREEN + sender + ChatColor.DARK_AQUA + " " + msg);
		}
	}
}
