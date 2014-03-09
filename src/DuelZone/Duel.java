package DuelZone;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class Duel {
	public static int EjectTimer;
	private static Player cont1;
	private static Player cont2;
	public static Location ejectLoc;
	public static int bet = 0;
	private static int winnings = 0;
	public static PVPPlayer cont1pvp;
	public static PVPPlayer cont2pvp;
	public static boolean has2players = false;
	public static boolean betAccepted = false;
	public static boolean betAccepted1 = false;
	public static boolean betAccepted2 = false;
	public static boolean duelAccepted = false;
	public static boolean duelAccepted1 = false;
	public static boolean duelAccepted2 = false;
	private static Player winner;
	public static boolean inprogress = false;
	public static int winState = -1;
	public static int bidTimer = -1;
	public static int acceptTimer = -1;
	private static boolean cancelled;

	public static void payout(Player cont) {
		if (cont.getName() == cont1.getName()) {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "["
					+ ChatColor.LIGHT_PURPLE + "DUEL" + ChatColor.YELLOW + "]"
					+ ChatColor.LIGHT_PURPLE + ":" + ChatColor.RED
					+ cont1.getName() + ChatColor.YELLOW
					+ " WON IN A DUEL AGAINST " + ChatColor.RED
					+ cont2.getName() + ChatColor.YELLOW + " AND WON "
					+ ChatColor.GREEN + "$" + winnings + ChatColor.YELLOW
					+ " to duel enter the " + ChatColor.GOLD + "GOLDEN"
					+ ChatColor.YELLOW + " gate at spawn");
			
		} else {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "["
					+ ChatColor.LIGHT_PURPLE + "DUEL" + ChatColor.YELLOW + "]"
					+ ChatColor.LIGHT_PURPLE + ":" + ChatColor.RED
					+ cont2.getName() + ChatColor.YELLOW
					+ " WON IN A DUEL AGAINST " + ChatColor.RED
					+ cont1.getName() + ChatColor.YELLOW + " AND WON "
					+ ChatColor.GREEN + "$" + winnings + ChatColor.YELLOW
					+ " to duel enter the " + ChatColor.GOLD + "GOLDEN"
					+ ChatColor.YELLOW + " gate at spawn");

		}
		PvpBalance.PvpBalance.economy.depositPlayer(cont.getName(), winnings);
		PvpBalance.PvpBalance.economy.depositPlayer("realmtaxs",
				(winnings / 10));
		cont.sendMessage(ChatColor.GREEN + "$" + winnings
				+ " Has been deposited in your bank account!");
		winnings = 0;
	}

	public static boolean checkContestant(Player player) {
		if (cont1 != null) {
			if (player.getName() == cont1.getName()) {
				return true;
			}
		}
		if(cont2 != null){
			if (player.getName() == cont2.getName()) {
				return true;
			}
		}
		return false;
	}

	public static void setBet(int bet) {
		if (checkBet(bet) == true && Duel.bet < 2499) {
			Duel.bet = bet;
			Duel.bidTimer = -1;
			cont1.sendMessage(ChatColor.BLUE + "BET ENTERED" + ChatColor.GREEN
					+ " $" + bet + ChatColor.BLUE + " please say "
					+ ChatColor.YELLOW + "/acceptbet" + ChatColor.BLUE
					+ " TO CONFIRM");
			cont2.sendMessage(ChatColor.BLUE + "BET ENTERED" + ChatColor.GREEN
					+ " $" + bet + ChatColor.BLUE + " please say "
					+ ChatColor.YELLOW + "/acceptbet" + ChatColor.BLUE
					+ " TO CONFIRM");
		} else if (bet > 2499) {
			if(cont1 != null){
				cont1.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "BET ALREADY ENTERED $" + bet + " PLEASE SAY"
						+ ChatColor.GREEN + " /acceptbet" + ChatColor.RED
						+ " TO ACCEPT");
			}
			if(cont2 != null){
				cont2.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
						+ "BET ALREADY ENTERED $" + bet + " PLEASE SAY"
						+ ChatColor.GREEN + " /acceptbet" + ChatColor.RED
						+ " TO ACCEPT");
			}
		}
	}

	public static boolean checkBet(int bet) {
		if(bet < 2500){
			cont1.sendMessage(ChatColor.RED + "Bet not high enough try again! must be at least $2500");
			cont2.sendMessage(ChatColor.RED + "Bet not high enough try again! must be at least $2500");
			return false;
		}
		if (PvpBalance.PvpBalance.economy.getBalance(cont1.getName()) < bet) {
			cont1.sendMessage(ChatColor.RED + cont1.getName()
					+ " Does not have enough funds for this bet! try again!");
			cont2.sendMessage(ChatColor.RED + cont1.getName()
					+ " Does not have enough funds for this bet! try again!");
			cancelDuel();
			return false;
		} else if (PvpBalance.PvpBalance.economy.getBalance(cont2.getName()) < bet) {
			cont1.sendMessage(ChatColor.RED + cont2.getName()
					+ " Does not have enough funds for this bet! try again!");
			cont2.sendMessage(ChatColor.RED + cont2.getName()
					+ " Does not have enough funds for this bet! try again!");
			cancelDuel();
			return false;
		}
		return true;
	}

	public static void cancelDuel() {
		if (cont1 != null){
			cont1.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
					+ "DUEL ENDED");
			if(cont1pvp != null)
			cont1pvp.setCombatCoolDown(0);
		}
		if (cont2 != null){
			cont2.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
					+ "DUEL ENDED");
			if(cont2pvp != null)
			cont2pvp.setCombatCooldown(0);
		}
		PVPPlayer cont1pvp = PvpHandler.getPvpPlayer(cont1);
		PVPPlayer cont2pvp = PvpHandler.getPvpPlayer(cont2);
		eject(cont1);
		eject(cont2);
		winState = -1;
		if (cont1pvp != null)
			cont1pvp.setDuelContestant(false);
		if (cont2pvp != null)
			cont2pvp.setDuelContestant(false);
		has2players = false;
		cont1 = null;
		cont2 = null;
		bet = 0;
		winnings = 0;
		cont1pvp = null;
		cont2pvp = null;
		betAccepted = false;
		betAccepted1 = false;
		betAccepted2 = false;
		duelAccepted = false;
		duelAccepted1 = false;
		duelAccepted2 = false;
		winner = null;
		inprogress = false;
		EjectTimer = 0;
		winState = -1;
		bidTimer = -1;
		acceptTimer = -1;
		has2players = false;
	}

	public static void acceptBet(Player player) {
		if (player.getName() == cont1.getName()) {
			betAccepted1 = true;
			cont1.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD
					+ "BET ACCEPTED! $" + bet);
		}
		if (player.getName() == cont2.getName()) {
			betAccepted2 = true;
			cont2.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD
					+ "BET ACCEPTED! $" + bet);
		} else if (player.getName() != cont1.getName()
				&& player.getName() != cont2.getName()) {
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
					+ "YOU ARE NOT CURRENTLY DUELING");
		}
	}

	public static void addContestant(Player player) {
		if (checkContestant(player) == false) {
			if (cont1 == null) {
				cont1 = player;
				Duel.EjectTimer = 10;
			} else if (cont2 == null && player != cont1) {
				cont2 = player;
				Duel.EjectTimer = 10;
			} else if (cont1 != null && cont2 != null) {
				player.sendMessage(ChatColor.RED
						+ "DUEL IN PROGRESS PLEASE WAIT " + EjectTimer + " SECONDS");
				eject(player);
			}
			if (cont1 != null && cont2 != null && Duel.inprogress == false) {
				beginBid();
			}
		}
	}

	private static void beginBid() {
		EjectTimer = 20;
		bidTimer = 20;
		cont1.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD
				+ "DUEL INITIATED, PLEASE SAY " + ChatColor.YELLOW
				+ "/bet <AMOUNT>");
		cont2.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD
				+ "DUEL INITIATED, PLEASE SAY " + ChatColor.YELLOW
				+ "/bet <AMOUNT>");
	}

	public static void playerDeath(Player player) {
		if (cont1.getName() == player.getName() && winState < 1) {
			try {
				PvpBalance.PvpBalance.mysql.storeUserData(cont2, "DuelsWon", PvpBalance.PvpBalance.mysql.getUserData(cont2, "DuelsWon") + 1);
				PvpBalance.PvpBalance.mysql.storeUserData(cont1, "DuelsLost", PvpBalance.PvpBalance.mysql.getUserData(cont1, "DuelsLost") + 1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			payout(cont2);
			winner = cont2;
			winState = 20;
		}
		if (cont2.getName() == player.getName() && winState < 1) {
			try {
				PvpBalance.PvpBalance.mysql.storeUserData(cont1, "DuelsWon", PvpBalance.PvpBalance.mysql.getUserData(cont1, "DuelsWon") + 1);
				PvpBalance.PvpBalance.mysql.storeUserData(cont2, "DuelsLost", PvpBalance.PvpBalance.mysql.getUserData(cont2, "DuelsLost") + 1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			payout(cont1);
			winner = cont1;
			winState = 20;
		} else {
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			pvp.setDuelContestant(false);
		}
	}

	public static void tick() {
		if (cont1 != null && cont2 != null) {
			has2players = true;
		} else {
			has2players = false;
		}
		if(cont1 != null && Duel.duelAccepted == true){
			if(cont1.isOnline() == false && winState == -1 && duelAccepted == true){
				if(cont2.isOnline()){
					payout(cont2);
				}
				else{
					cancelDuel();
				}
			}
		}
		if(cont2 != null && Duel.duelAccepted == true){
			if(cont2.isOnline() == false && winState == -1){
				if(cont1.isOnline()){
					payout(cont1);
				}
				else{
					cancelDuel();
				}
			}
		}
		
		if( has2players == true && cont1.isOnline() == false && cont2.isOnline() == false){
			cancelDuel();
		}
		if (Duel.duelAccepted == false && (Duel.EjectTimer % 5) == 0) {
			if (Duel.has2players = true) {
				if(cont1 != null){
					cont1.sendMessage(ChatColor.RED + "Duel will cancel in "
						+ EjectTimer + " if bet/duel not accepted");
					if (cont2 == null ) {
						cont1.sendMessage(ChatColor.RED
								+ "Waiting for another player");
					}
				}
				if(cont2 != null){
					cont2.sendMessage(ChatColor.RED + "Duel will cancel in "
						+ EjectTimer + " if bet/duel not accepted");
					if (cont1 == null ) {
						cont2.sendMessage(ChatColor.RED
								+ "Waiting for another player");
					}
				}
			}
		}
		if (winState > 0) {
			winState--;
			if(winState%5 == 0)
			winner.sendMessage("You will be ejected from the duel area in "
					+ winState + " seconds " + ChatColor.RED
					+ " DO NOT LEAVE BEFORE THIS RUNS OUT!");
		} else if (winState == 0) {
			winState = -1;
			Duel.cancelDuel();
		}
		if (duelAccepted == true) {
			Duel.inprogress = true;
			cont1pvp.setCombatCoolDown(20);
			cont2pvp.setCombatCooldown(20);
			if (cont1pvp.isDuelZone() == false && winState == -1) {
				cont1.teleport(cont2);
				cont1pvp.sethealth(cont1pvp.gethealth() - 500);
				cont1.sendMessage("You have been penalized for leaving the arena! - 500 health");
			}
			if (cont2pvp.isDuelZone() == false && winState == -1) {
				cont2.teleport(cont1);
				cont2pvp.sethealth(cont1pvp.gethealth() - 500);
				cont2.sendMessage("You have been penalized for leaving the arena! - 500 health");
			}
		}
		if (ejectLoc == null && cont1 != null) {
			ejectLoc = new Location(cont1.getWorld(), -645, 82, 346);
		}
		if (betAccepted1 == true && betAccepted2 == true
				&& betAccepted == false) {
			EjectTimer = 20;
			betAccepted = true;
			cont1.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD
					+ "BET ACCEPTED TO BEGIN SAY " + ChatColor.YELLOW
					+ "/acceptduel");
			cont2.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD
					+ "BET ACCEPTED TO BEGIN SAY " + ChatColor.YELLOW
					+ "/acceptduel");
		}
		if (duelAccepted1 == true && duelAccepted2 == true
				&& duelAccepted == false) {
			duelAccepted = true;
			EjectTimer = 20;
			cont1.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD
					+ "DUEL ACCEPTED!");
			cont1.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD
					+ "DUEL ACCEPTED!");
			startDuel();
		}
		if (EjectTimer == 1) {
				if(cont1 != null)
					eject(cont1);
				if(cont2 != null)
					eject(cont2);
			cancelDuel();
			EjectTimer = 0;
		}
		if (EjectTimer > 0) {
			Duel.EjectTimer--;
		}
		if (bidTimer > 0) {
			bidTimer--;
			if (bidTimer == 0) {
				cont1.sendMessage(ChatColor.YELLOW
						+ "["
						+ ChatColor.LIGHT_PURPLE
						+ "DUEL"
						+ ChatColor.YELLOW
						+ "]"
						+ ChatColor.LIGHT_PURPLE
						+ ":"
						+ ChatColor.RED
						+ cont1.getName()
						+ ChatColor.GREEN
						+ " Duel canceled, no bid amount entered please try again!");
				cont2.sendMessage(ChatColor.YELLOW
						+ "["
						+ ChatColor.LIGHT_PURPLE
						+ "DUEL"
						+ ChatColor.YELLOW
						+ "]"
						+ ChatColor.LIGHT_PURPLE
						+ ":"
						+ ChatColor.RED
						+ cont2.getName()
						+ ChatColor.GREEN
						+ " Duel canceled, no bid amount entered please try again!");
				cancelDuel();
			}
		}
		if (acceptTimer > 1) {
			acceptTimer--;
		}
	}
	private static void win(Player winner) {

	}

	private static void eject(Player player) {
		if (player != null)
			player.teleport(ejectLoc);
	}

	private static void startDuel() {
		cancelled = false;
		cont1pvp = PvpHandler.getPvpPlayer(cont1);
		cont2pvp = PvpHandler.getPvpPlayer(cont2);
		cont1pvp.setDuelZone(true);
		cont2pvp.setDuelZone(true);
		if (readyCheck() == true) {
			Duel.inprogress = true;
			Bukkit.broadcastMessage(ChatColor.YELLOW + "["
					+ ChatColor.LIGHT_PURPLE + "DUEL" + ChatColor.YELLOW + "] "
					+ ChatColor.LIGHT_PURPLE + ":" + ChatColor.RED
					+ cont1.getName() + ChatColor.YELLOW + " AND "
					+ ChatColor.RED + cont2.getName() + ChatColor.YELLOW
					+ " have started a duel! Go in the portal at "
					+ ChatColor.GREEN + "/spawn" + ChatColor.YELLOW
					+ " to watch enter the " + ChatColor.GOLD + "GOLDEN"
					+ ChatColor.YELLOW + " gate at spawn");

			PvpBalance.PvpBalance.economy.withdrawPlayer(cont1.getName(), bet);
			PvpBalance.PvpBalance.economy.withdrawPlayer(cont2.getName(), bet);
			winnings = (bet * 2);
			winnings = winnings - (winnings / 10);
			EjectTimer = 500;
			cont1.teleport(new Location(cont1.getWorld(), -633, 77, 334));
			cont2.teleport(new Location(cont2.getWorld(), -592, 77, 334));
			cont1pvp.setDuelContestant(true);
			cont2pvp.setDuelContestant(true);
		}
	}

	private static boolean readyCheck() {
		if (cont1 == null || cont2 == null) {
			return false;
		}
		if (Duel.betAccepted == false) {
			return false;
		}
		if (checkBet(bet) == false) {
			return false;
		}
		return true;
	}

	public static void acceptDuel(Player player) {
		if (player.getName() == cont1.getName()) {
			duelAccepted1 = true;
		}
		if (player.getName() == cont2.getName()) {
			duelAccepted2 = true;
		}
	}
}
