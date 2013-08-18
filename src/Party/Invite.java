package Party;

import org.bukkit.ChatColor;

import PvpBalance.PVPPlayer;

public class Invite
{
	private PVPPlayer sender;
	private Party party;
	
	public Invite(PVPPlayer dp)
	{
		sender = dp;
		party = dp.getParty();
	}
	
	public PVPPlayer getSender()
	{
		return sender;
	}
	
	public void accept(PVPPlayer dp)
	{
		if(party == null)
		{
			party = new Party(sender);
			sender.setParty(party);
		}
		party.addPlayer(dp);
	}
	
	public void decline(PVPPlayer dp)
	{
		sender.sendMessage(ChatColor.AQUA + dp.getName() + ChatColor.RED + " Declined Your Invitation!");
		//party.checkDisband();
	}
	
	public boolean isPartyFull()
	{
		return party.isFull();
	}
	
	public Party getParty()
	{
		return party;
	}
}
