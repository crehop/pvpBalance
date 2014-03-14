package PvpBalance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import DuelZone.Duel;
import Party.CommandParty;
import Party.PartyListener;
import SaveLoad.LoadSave;
import SaveLoad.Save;
import Util.ItemUtils;
import Util.MYSQLManager;

public class PvpBalance extends JavaPlugin
{
	public static MYSQLManager mysql;
	public static final Logger logger = Logger.getLogger("Minecraft");
    public static PvpBalance plugin;
	public LoadSave LoadSave;
	private final float SPEED = 0.2F;
	private final int AMOUNT = 300;
	private static int everyOther = 0;
	private boolean debug = false;
	private static List<Chicken> chickens = new ArrayList<Chicken>();
	public static List<Player> particulating = new ArrayList<Player>();
	public static List<Duel> duel = new ArrayList<Duel>();
	private boolean faction = false;
	public static Economy economy = null;
	
	private Save sDamage, protection;
	public boolean logDB = true;


 
	@Override
 	public void onDisable()
 	{
		for(PVPPlayer pp : PvpHandler.getPvpPlayers())
		{
			if(pp == null)
				continue;
			pp.setCombatCoolDown(0);
		}
		PvpHandler.clear();
	 	PluginDescriptionFile pdfFile = this.getDescription();
	 	logger.info(pdfFile.getName() + " Has Been Disabled!!");
	 	this.mysql.closeDB();
 	}
 
 	public void onEnable()
 	{
 		plugin = this;
	 	for(Player p : Bukkit.getOnlinePlayers())
	 	{
	 		if(p == null)
	 			continue;
	 		createNewPvPPlayer(p);
	 	}
	 	setupEconomy();
	 	PvpHandler.load();
 		mysql = new MYSQLManager(this);
	 	sDamage = new Save(this, "Damage.yml");
	 	protection = new Save(this, "Protection.yml");
	 	LoadSave  = new LoadSave(this);
	    getCommand("party").setExecutor(new CommandParty(this));
	 	
	 	Bukkit.getMessenger().registerOutgoingPluginChannel(this, "Scoreboard");
	 	
	 	Damage.LoadSave = LoadSave;
	 	
	 	getServer().getPluginManager().registerEvents(new DBZListener(this, LoadSave), this);
	 	getServer().getPluginManager().registerEvents(new PartyListener(), this);
	 	getServer().getPluginManager().registerEvents(new PvpListener(), this);
	 	
	 	PluginDescriptionFile pdfFile = this.getDescription();
	 	try {
			this.mysql.setupDb();
		} catch (SQLException e) {
			this.logDB  = false;
			e.printStackTrace();
		}
	 	

		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
		    @Override  
		    public void run()
		    {
		    	if(everyOther == 0)
		    	{
		    		everyOther = 1;
		    		particulating();
		    		fireballEffects();
			    	for(Player all : Bukkit.getServer().getOnlinePlayers())
			    	{
							try
							{
					    		// BASIC EFFECT APLICATIONS ==========================================================================================
					    		//ON FIRE
					    		if(all.getFireTicks() > 1){
					    			Effects.igniteFirePlayers(all);
					    		}	    
					    		//ENCHANTED SWORD
					    		if(all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) || all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE))
					    		{
					    			Effects.effectSharpnessPlayers(all);
					    		}
					    		//CONFUSED
					    		if(all.getActivePotionEffects().toString().contains("CONFUSION"))
					    		{
					    			Effects.effectConfuse(all);
					    		}
					    		//WITHERED
					    		if(all.getActivePotionEffects().toString().contains("WITHER"))
					    		{
					    			Effects.effectWither(all);
					    		}	
					    		//POISONED
					    		if(all.getActivePotionEffects().toString().contains("POISON"))
					    		{
					    			Effects.effectPoison(all);
					    		}
					    		//BLIND
					    		if(all.getActivePotionEffects().toString().contains("Blindness"))
					    		{
					    			Effects.effectBlind(all);
					    		}
					    		//SPEED POT
					    		if(all.getActivePotionEffects().toString().contains("SPEED"))
					    		{
					    			Effects.effectSpeedPlayers(all, SPEED, AMOUNT);
					    		}
					    		if(all.getActivePotionEffects().toString().contains("SLOW"))
					    		{
					    			Effects.effectSlow(all);
					    		}
					    		//REGENERATING
					    		if(all.getActivePotionEffects().toString().contains("REGENERATION"))
					    		{
					    			Effects.effectHealthPlayers(all, (float) 0.3, 30);
					    			PVPPlayer pvp = PvpHandler.getPvpPlayer(all);
					    			pvp.sethealth(pvp.gethealth() + SaveLoad.LoadSave.Regen);
					    		}
					    		//LOW HEALTH
					    		if(all.getHealth() < 9)
					    		{
					    			Effects.bleed(all);
					    		}
					    		//PVP ABILITIES ====================================================================================
							
					    }
						catch (Exception e1)
						{
							e1.printStackTrace();
							logger.info("Main Effect!");
						}
				}
		    }
		    else if(everyOther == 1)
		    {
		    	everyOther = 2;
	    		particulating();
	    		fireballEffects();
	    		//ARMOR EFFECTS =================================================================================
		    	for(Player all : Bukkit.getServer().getOnlinePlayers())
		    	{
		    		if(all == null)
		    			continue;
		    		try
		    		{
		    			ArmorEffects.checkForGlowTick(all);
		
		    		}
		    		catch (Exception e1)
		    		{
							e1.printStackTrace();
							logger.info("Main ArmorEffect!");
					}
		    		
				}
		    	for(Player all : Bukkit.getServer().getOnlinePlayers())
		    	{
						try
						{
				    		// BASIC EFFECT APLICATIONS ==========================================================================================
				    		//ON FIRE
				    		if(all.getFireTicks() > 1){
				    			Effects.igniteFirePlayers(all);
				    		}	    
				    		//ENCHANTED SWORD
				    		if(all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) || all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE))
				    		{
				    			Effects.effectSharpnessPlayers(all);
				    		}
				    		//CONFUSED
				    		if(all.getActivePotionEffects().toString().contains("CONFUSION"))
				    		{
				    			Effects.effectConfuse(all);
				    		}
				    		//WITHERED
				    		if(all.getActivePotionEffects().toString().contains("WITHER"))
				    		{
				    			Effects.effectWither(all);
				    		}	
				    		//POISONED
				    		if(all.getActivePotionEffects().toString().contains("POISON"))
				    		{
				    			Effects.effectPoison(all);
				    		}
				    		//BLIND
				    		if(all.getActivePotionEffects().toString().contains("Blindness"))
				    		{
				    			Effects.effectBlind(all);
				    		}
				    		//SPEED POT
				    		if(all.getActivePotionEffects().toString().contains("SPEED"))
				    		{
				    			Effects.effectSpeedPlayers(all, SPEED, AMOUNT);
				    		}
				    		if(all.getActivePotionEffects().toString().contains("SLOW"))
				    		{
				    			Effects.effectSlow(all);
				    		}
				    		//REGENERATING
				    		//SPRINTING
				    		//LOW HEALTH
				    		if(all.getHealth() < 9)
				    		{
				    			Effects.bleed(all);
				    		}
				    		//PVP ABILITIES ====================================================================================
						
				    }
					catch (Exception e1)
					{
						e1.printStackTrace();
						logger.info("Main Effect!");
					}
		    	}
		    }
		    //CLEANUP
		    else if(everyOther == 2)
		    {
		    	for(Chicken chick:chickens)
		    	{
		    		if(chick.getPassenger() == null)
		    		{
		    			chick.remove();
		    		    PvpBalance.chickenList().remove(chick);
		    		}
		    	}
		    	everyOther = 3;
	    		particulating();
	    		fireballEffects();
		    	for(Player all : Bukkit.getServer().getOnlinePlayers())
		    	{
						try
						{
				    		// BASIC EFFECT APLICATIONS ==========================================================================================
				    		//ON FIRE
				    		if(all.getFireTicks() > 1){
				    			Effects.igniteFirePlayers(all);
				    		}	    
				    		//ENCHANTED SWORD
				    		if(all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) || all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE))
				    		{
				    			Effects.effectSharpnessPlayers(all);
				    		}
				    		//CONFUSED
				    		if(all.getActivePotionEffects().toString().contains("CONFUSION"))
				    		{
				    			Effects.effectConfuse(all);
				    		}
				    		//WITHERED
				    		if(all.getActivePotionEffects().toString().contains("WITHER"))
				    		{
				    			Effects.effectWither(all);
				    		}	
				    		//POISONED
				    		if(all.getActivePotionEffects().toString().contains("POISON"))
				    		{
				    			Effects.effectPoison(all);
				    		}
				    		//BLIND
				    		if(all.getActivePotionEffects().toString().contains("Blindness"))
				    		{
				    			Effects.effectBlind(all);
				    		}
				    		//SPEED POT
				    		if(all.getActivePotionEffects().toString().contains("SPEED"))
				    		{
				    			Effects.effectSpeedPlayers(all, SPEED, AMOUNT);
				    		}
				    		if(all.getActivePotionEffects().toString().contains("SLOW"))
				    		{
				    			Effects.effectSlow(all);
				    		}
				    		//REGENERATING
				    		//SPRINTING
				    		//LOW HEALTH
				    		if(all.getHealth() < 9)
				    		{
				    			Effects.bleed(all);
				    		}
				    		//PVP ABILITIES ====================================================================================
						
				    }
					catch (Exception e1)
					{
						e1.printStackTrace();
						logger.info("Main Effect!");
					}
		    	}
		    }
		    else if(everyOther == 3)
		    {
		    	everyOther = 0;
	    		particulating();
	    		fireballEffects();
		    	Duel.tick();
		    	for(PVPPlayer all: PvpHandler.getPvpPlayers())
		    	{
					try
					{
						all.tick();
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
						logger.info("Main Tick!");
					}
				}
		    	for(Player all : Bukkit.getServer().getOnlinePlayers())
		    	{
						try
						{
				    		// BASIC EFFECT APLICATIONS ==========================================================================================
				    		//ON FIRE
				    		if(all.getFireTicks() > 1){
				    			Effects.igniteFirePlayers(all);
				    		}	    
				    		//ENCHANTED SWORD
				    		if(all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) || all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE))
				    		{
				    			Effects.effectSharpnessPlayers(all);
				    		}
				    		//CONFUSED
				    		if(all.getActivePotionEffects().toString().contains("CONFUSION"))
				    		{
				    			Effects.effectConfuse(all);
				    		}
				    		//WITHERED
				    		if(all.getActivePotionEffects().toString().contains("WITHER"))
				    		{
				    			Effects.effectWither(all);
				    		}	
				    		//POISONED
				    		if(all.getActivePotionEffects().toString().contains("POISON"))
				    		{
				    			Effects.effectPoison(all);
				    		}
				    		//BLIND
				    		if(all.getActivePotionEffects().toString().contains("Blindness"))
				    		{
				    			Effects.effectBlind(all);
				    		}
				    		//SPEED POT
				    		if(all.getActivePotionEffects().toString().contains("SPEED"))
				    		{
				    			Effects.effectSpeedPlayers(all, SPEED, AMOUNT);
				    		}
				    		if(all.getActivePotionEffects().toString().contains("SLOW"))
				    		{
				    			Effects.effectSlow(all);
				    		}
				    		//REGENERATING
				    		//SPRINTING
				    		if(all.isSprinting())
				    		{
				    			Effects.effectSprintPlayers(all, SPEED, (int)SPEED*5);
				    		}
				    		//LOW HEALTH
				    		if(all.getHealth() < 9)
				    		{
				    			Effects.bleed(all);
				    		}
				    		//PVP ABILITIES ====================================================================================
						
				    }
					catch (Exception e1)
					{
						e1.printStackTrace();
						logger.info("Main Effect!");
					}
		    	}
		    }
		}


	}, 0L, 5L);
		
	 	logger.info(pdfFile.getName() + " Has Been Enabled!!");
 	}
	private void particulating() {
		for(Player player:particulating){
			createParticleEffect(player);
			if(player.isOnline() == false){
				particulating.remove(player);
			}
		}
		
	}
	public boolean isDebug()
	{
		return debug;
	}
	
	public void setDebug(boolean value)
	{
		debug = value;
	}
	
	public boolean hasFaction()
	{
		return faction;
	}
	
	
	public Save getSDamage()
	{
		return sDamage;
	}
	
	public Save getProtection()
	{
		return protection;
	}
	
	public boolean onCommand(CommandSender sender,Command cmd,String commandLabel, String[] args)
	{
		Player player = (Player) sender;
		//Location location = player.getLocation();
		if(commandLabel.equalsIgnoreCase("bet")){
			if(args[0] != null){
				if(Duel.bet < 2500){
					if(Duel.checkContestant(player) == true && Duel.has2players == true && Duel.betAccepted == false){
						int bet = Integer.parseInt(args[0]);
						Duel.setBet(bet);
					}
				}
			}
		}
		if(commandLabel.equalsIgnoreCase("acceptbet")){
			Duel.acceptBet(player);
		}
		if(commandLabel.equalsIgnoreCase("acceptduel")){
			Duel.acceptDuel(player);
		}
		if(commandLabel.equalsIgnoreCase("pvpdebug") && player.hasPermission("particles.admin"))
		{
			if(debug == false)
			{
				debug = true;
			}
			else
				debug = false;
			if(PvpHandler.getPvpPlayer(player) == null)
			{
				createNewPvPPlayer(player);
			}
		}
		else if(commandLabel.equalsIgnoreCase("pvpgod") && player.hasPermission("particles.admin"))
		{
			PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
			if(PVPPlayer.isGod())
			{
				PVPPlayer.setGod(false);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "GOD MODE DISABLED");
			}
			else
			{
				PVPPlayer.setGod(true);
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "GOD MODE ENABLED");
				player.setFoodLevel(20);
				PVPPlayer.sethealth(PVPPlayer.getMaxHealth());
			}
		}
		else if(commandLabel.equalsIgnoreCase("pvpver") && player.hasPermission("particles.admin"))
		{
			ItemStack paper = new ItemStack(Material.PAPER);
			List<String> lore = new ArrayList<String>();
			lore.add("Polishing Cloth " + ChatColor.MAGIC + " " + ArmorEffects.CODE_PAPER);
			lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			ItemUtils.setLore(paper, lore);
			ItemUtils.setName(paper, ChatColor.GOLD + "Polishing Cloth");
			player.getInventory().addItem(paper);
			player.sendMessage(ChatColor.GREEN + "[Armor Polish]: Greetings Administrator " + player.getDisplayName() + " a cloth has been provided");
			player.getInventory().addItem(paper);
			player.sendMessage("VERSION 1.6");
			SaveLoad.LoadSave.reloadValues(this, player);
			player.setFoodLevel(0);
		}
		else if(commandLabel.equalsIgnoreCase("polish"))
		{
			ArmorEffects.polish(player);
		}		
		else if(commandLabel.equalsIgnoreCase("effect") && player.hasPermission("pvpbalance.particles2")){
			player.sendMessage(ChatColor.GREEN + "============= EFFECTS MENU ============");
			player.sendMessage(ChatColor.GREEN + "say /effecttype to change type");
			player.sendMessage(ChatColor.GREEN + "say /effectspeed to change speed");
			player.sendMessage(ChatColor.GREEN + "say /effectcount to change count");
			player.sendMessage(ChatColor.GREEN + "say /effectheight to change height");
			player.sendMessage(ChatColor.GREEN + "say /effectreset to set to none");
		}
		else if(commandLabel.equalsIgnoreCase("effectreset") && player.hasPermission("pvpbalance.particles2")){
			PvpBalance.particulating.remove(player);
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			pvp.setParticleCount(0);
			pvp.setParticleEffect("");
			pvp.setParticleSpeed(0);
		}
		else if(commandLabel.equalsIgnoreCase("effecttype") && player.hasPermission("pvpbalance.particles2")){
			PlayerParticles.nextParticle(player);
			player.sendMessage(ChatColor.GREEN + "type changed to " + ChatColor.DARK_PURPLE + "" + PvpHandler.getPvpPlayer(player).getParticleEffect());
		}
		else if(commandLabel.equalsIgnoreCase("endduel") && player.hasPermission("pvpbalance.op")){
			Duel.cancelDuel();
			player.sendMessage(ChatColor.YELLOW + "INFO =======================");
			if(Duel.cont1pvp != null)
			player.sendMessage(ChatColor.GREEN + "Contestant1 = " + Duel.cont1pvp.getPlayer().getName());
			if(Duel.cont2pvp != null)
			player.sendMessage(ChatColor.GREEN + "Contestant2 = " + Duel.cont2pvp.getPlayer().getName());
			player.sendMessage(ChatColor.GREEN + "EjectTimer = " + Duel.EjectTimer);
			player.sendMessage(ChatColor.GREEN + "Bet ammount = " + Duel.bet);
			player.sendMessage(ChatColor.GREEN + "DUEL CANNCELED");
			player.sendMessage(ChatColor.YELLOW + "============================");
			Duel.cancelDuel();
		}
		else if(commandLabel.equalsIgnoreCase("effectheight") && player.hasPermission("pvpbalance.particles2")){
			PlayerParticles.incrimentParticleHeight(player);
			player.sendMessage(ChatColor.GREEN + "height changed to " + ChatColor.DARK_PURPLE + "" + PvpHandler.getPvpPlayer(player).getParticleHeight());
		}
		else if(commandLabel.equalsIgnoreCase("effectspeed") && player.hasPermission("pvpbalance.particles2")){
			PlayerParticles.incrimentParticleSpeed(player);
			player.sendMessage(ChatColor.GREEN + "speed changed to " + ChatColor.DARK_PURPLE + "" + PvpHandler.getPvpPlayer(player).getParticleSpeed());
		}
		else if(commandLabel.equalsIgnoreCase("effectcount") && player.hasPermission("pvpbalance.particles2")){
			PlayerParticles.incrimentParticleCount(player);
			player.sendMessage(ChatColor.GREEN + "speed changed to " + ChatColor.DARK_PURPLE + "" + PvpHandler.getPvpPlayer(player).getParticleCount());
		}
		else if(args.length > 0){
			if(commandLabel.equalsIgnoreCase("pvpstats") && args[0] != null){
				try {
					if(mysql.getUserData(args[0].toString(), "Kills") != -1){
						int epicKills = 0;
						int epicDeaths = 0;
						int duelsWon = 0;
						int duelsLost = 0;
						int kills = 0;
						int deaths = 0;
						try {
							epicKills = mysql.getUserData(args[0].toString(), "EpicKills");
						} catch (SQLException e5) {
							// TODO Auto-generated catch block
							e5.printStackTrace();
						}
						try {
							epicDeaths = mysql.getUserData(args[0].toString(), "EpicDeaths");
						} catch (SQLException e4) {
							// TODO Auto-generated catch block
							e4.printStackTrace();
						}
						try {
							duelsWon = mysql.getUserData(args[0].toString(), "DuelsWon");
						} catch (SQLException e3) {
							e3.printStackTrace();
						}
						try {
							duelsLost = mysql.getUserData(args[0].toString(), "DuelsLost");
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							kills = mysql.getUserData(args[0].toString(), "Kills");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							deaths = mysql.getUserData(args[0].toString(), "Deaths");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						player.sendMessage(ChatColor.BLUE + "======= PVP STATS =======");
						player.sendMessage(ChatColor.GREEN + "Name" + ChatColor.YELLOW + ":" + ChatColor.WHITE + args[0].toString());
						player.sendMessage(ChatColor.GREEN + "Kills" + ChatColor.YELLOW + ":" + ChatColor.WHITE + kills);
						player.sendMessage(ChatColor.GREEN + "Deaths" + ChatColor.YELLOW + ":" + ChatColor.WHITE + deaths);
						player.sendMessage(ChatColor.GREEN + "Epic Kills" + ChatColor.YELLOW + ":" + ChatColor.WHITE + epicKills);
						player.sendMessage(ChatColor.GREEN + "Epic Deaths" + ChatColor.YELLOW + ":" + ChatColor.WHITE + epicDeaths);
						player.sendMessage(ChatColor.GREEN + "Duels Won" + ChatColor.YELLOW + ":" + ChatColor.WHITE + duelsWon);
						player.sendMessage(ChatColor.GREEN + "Duels Lost" + ChatColor.YELLOW + ":" + ChatColor.WHITE + duelsLost);
						player.sendMessage(ChatColor.BLUE + "=========================");
						return true;
					}
				} catch (SQLException e) {
				}

			player.sendMessage(ChatColor.BLUE + "======= PVP STATS =======");
			player.sendMessage(ChatColor.RED  + "Error: " + args[0].toString() + " not in database!");
			player.sendMessage(ChatColor.RED  + "PLEASE TRY AGAIN");
			player.sendMessage(ChatColor.BLUE + "=========================");
	        return true;
		}
		}
		else if(commandLabel.equalsIgnoreCase("pvpstats"))
		{
			int epicKills = 0;
			int epicDeaths = 0;
			int duelsWon = 0;
			int duelsLost = 0;
			int kills = 0;
			int deaths = 0;
			try {
				epicKills = mysql.getUserData(player, "EpicKills");
			} catch (SQLException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			try {
				epicDeaths = mysql.getUserData(player, "EpicDeaths");
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			try {
				duelsWon = mysql.getUserData(player, "DuelsWon");
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
			try {
				duelsLost = mysql.getUserData(player, "DuelsLost");
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				kills = mysql.getUserData(player, "Kills");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				deaths = mysql.getUserData(player, "Deaths");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			player.sendMessage(ChatColor.BLUE + "======= PVP STATS =======");
			player.sendMessage(ChatColor.GREEN + "Name" + ChatColor.YELLOW + ":" + ChatColor.WHITE + player.getDisplayName());
			player.sendMessage(ChatColor.GREEN + "Kills" + ChatColor.YELLOW + ":" + ChatColor.WHITE + kills);
			player.sendMessage(ChatColor.GREEN + "Deaths" + ChatColor.YELLOW + ":" + ChatColor.WHITE + deaths);
			player.sendMessage(ChatColor.GREEN + "Epic Kills" + ChatColor.YELLOW + ":" + ChatColor.WHITE + epicKills);
			player.sendMessage(ChatColor.GREEN + "Epic Deaths" + ChatColor.YELLOW + ":" + ChatColor.WHITE + epicDeaths);
			player.sendMessage(ChatColor.GREEN + "Duels Won" + ChatColor.YELLOW + ":" + ChatColor.WHITE + duelsWon);
			player.sendMessage(ChatColor.GREEN + "Duels Lost" + ChatColor.YELLOW + ":" + ChatColor.WHITE + duelsLost);
			player.sendMessage(ChatColor.BLUE + "=========================");
		}
		else if(commandLabel.equalsIgnoreCase("pvptop")){
			try {
				mysql.top10(player);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return true;
	}

	public static List<Chicken> chickenList()
	{
		return PvpBalance.chickens;
	}
	public void fireballEffects(){
    	for(Entity e:Bukkit.getWorld("world").getEntities()){
    		if(e instanceof Fireball){
    			Fireball shoot = (Fireball)e;
    			Effects.igniteFireball(e);
    			for(Entity near:shoot.getNearbyEntities(3, 3, 3)){
    				if(near instanceof Player && near != shoot.getShooter()){
    					shoot.teleport(near.getLocation());
    				}
    			}
    		}
    		if(e instanceof Arrow){
    			Arrow arrow = (Arrow)e;
    			if(arrow.isOnGround() == false){
    				try {
						ParticleEffect.sendToLocation(ParticleEffect.FIREWORKS_SPARK, arrow.getLocation(),0.01f,0.01f,0.01f, (float)0.0001, 1);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}
    			if(arrow.getTicksLived() > 1200){
    				arrow.remove();
    			}
    		}
        }
	}
	public void createParticleEffect(Player player){
		if(player.isOnline() == false){
			particulating.remove(player);
			return;
		}
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(pvp.getParticleCount() != 0){
			if(pvp.getParticleEffect() != ""){
				if(pvp.getEffect() != null){
					if(pvp.getParticleSpeed() != 0){
						try {
							ParticleEffect.sendToLocation(pvp.getEffect(), player.getLocation().add(0, pvp.getParticleHeight(), 0),0.2f,0.2f,0.2f, (float)pvp.getParticleSpeed(), pvp.getParticleCount());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	private boolean setupEconomy()
	{
		RegisteredServiceProvider economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null)
		{
			economy = (Economy)economyProvider.getProvider();
		}

		return economy != null;
	}
	public static Plugin getPlugin(){
		return plugin;
	}
	public static MYSQLManager getMYSQL(){
		return mysql;
	}
	public static void createNewPvPPlayer(Player player){
		PVPPlayer newPVP = new PVPPlayer(player);
		PvpHandler.addPvpPlayer(newPVP);
		try {
			mysql.storeUserData(player, "EpicKills", mysql.getUserData(player, "EpicKills"));
			mysql.storeUserData(player, "EpicDeaths", mysql.getUserData(player, "EpicDeaths"));
			mysql.storeUserData(player, "DuelsWon", mysql.getUserData(player, "DuelsWon"));
			mysql.storeUserData(player, "DuelsLost", mysql.getUserData(player, "DuelsLost"));
			mysql.storeUserData(player, "Kills", mysql.getUserData(player, "Kills"));
			mysql.storeUserData(player, "Deaths", mysql.getUserData(player, "Deaths"));

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	public static PVPPlayer createNewPvPPlayerObject(Player player){
		PVPPlayer newPVP = new PVPPlayer(player);
		PvpHandler.addPvpPlayer(newPVP);
		try {
			mysql.storeUserData(player, "EpicKills", mysql.getUserData(player, "EpicKills"));
			mysql.storeUserData(player, "EpicDeaths", mysql.getUserData(player, "EpicDeaths"));
			mysql.storeUserData(player, "DuelsWon", mysql.getUserData(player, "DuelsWon"));
			mysql.storeUserData(player, "DuelsLost", mysql.getUserData(player, "DuelsLost"));
			mysql.storeUserData(player, "Kills", mysql.getUserData(player, "Kills"));
			mysql.storeUserData(player, "Deaths", mysql.getUserData(player, "Deaths"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return newPVP;
	}
}