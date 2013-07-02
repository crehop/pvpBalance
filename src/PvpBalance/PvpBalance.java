package PvpBalance;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import com.massivecraft.factions.P;

import SaveLoad.LoadSave;
import SaveLoad.Save;

public class PvpBalance extends JavaPlugin
{
	public static final Logger logger = Logger.getLogger("Minecraft");
    public static PvpBalance plugin;
	private float speed = (float) 0.2;
	private int amount = 300;
	private static int everyOther = 0;
	private boolean debug = false;
	
	private P factions;
	private boolean faction = false;
	
	private Save sDamage, protection;


 
	@Override
 	public void onDisable()
 	{
	 	PluginDescriptionFile pdfFile=this.getDescription();
	 	logger.info(pdfFile.getName() + " Has Been Disabled!!");
 	}
 
 	public void onEnable()
 	{
	 	plugin = this;
	 	
	 	sDamage = new Save(this, "Damage.yml");
	 	protection = new Save(this, "Protection.yml");
	 	LoadSave loadSave  = new LoadSave(this);
	 	
	 	factions = (P) Bukkit.getPluginManager().getPlugin("Factions");
	 	if(factions != null)
	 	{
	 		faction = true;
	 	}
	 	
	 	Bukkit.getMessenger().registerOutgoingPluginChannel(this, "Scoreboard");
	 	
	 	Damage.LoadSave = loadSave;
	 	getServer().getPluginManager().registerEvents(new DBZListener(this, loadSave), this);
	 	
	 	PluginDescriptionFile pdfFile = this.getDescription();

		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
		    @Override  
		    public void run()
		    {
		    	if(everyOther == 0)
		    	{
		    		everyOther = 1;
			    	for(Player all : Bukkit.getServer().getOnlinePlayers())
			    	{
							try
							{
								PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(all);
								PVPPlayer.sethealth(PVPPlayer.gethealth());
					    		// BASIC EFFECT  APLICATIONS ==========================================================================================
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
					    			PVPPlayer.sethealth(PVPPlayer.gethealth() - 10);
					    		}	
					    		//POISONED
					    		if(all.getActivePotionEffects().toString().contains("POISON"))
					    		{
					    			Effects.effectPoison(all);
					    			PVPPlayer.sethealth(PVPPlayer.gethealth() - 10);
					    		}
					    		//BLIND
					    		if(all.getActivePotionEffects().toString().contains("Blindness"))
					    		{
					    			Effects.effectPoison(all);
					    		}
					    		//SPEED POT
					    		if(all.getActivePotionEffects().toString().contains("SPEED"))
					    		{
					    			Effects.effectSpeedPlayers(all, speed, amount);
					    		}
					    		//REGENERATING
					    		if(all.getActivePotionEffects().toString().contains("REGENERATION"))
					    		{
					    			PVPPlayer.sethealth(PVPPlayer.gethealth() + 20);
					    			Effects.effectHealthPlayers(all, (float) 0.3, 30);
					    		    for (PotionEffect effect : all.getActivePotionEffects())
					    		    {
					    		        all.removePotionEffect(effect.getType());
					    		    }
					    			all.sendMessage(ChatColor.GREEN + "ALL EFFECTS CLENSED!");
					    		}
					    		//SPRINTING
					    		if(all.isSprinting())
					    		{
					    			Effects.effectSprintPlayers(all, speed, (int)speed*5);
					    			PVPPlayer.setHunger(PVPPlayer.getHunger() - 1);
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
		    if(everyOther == 1)
		    {
		    	everyOther = 2;
	    		//ARMOR EFFECTS =================================================================================
		    	for(Player all : Bukkit.getServer().getOnlinePlayers())
		    	{
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
		    }
		    if(everyOther == 2)
		    {
		    	everyOther = 0;
		    	//TICK PVPPLAYERS TO ITERATE COOLDOWNS
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
		    }
		}
	}, 0L, 10L);
		
	 	logger.info(pdfFile.getName() + " Has Been Enabled!!");
 	}
 	
	/*private void copy(InputStream in, File file)
	{ 
		try
	     {
	         OutputStream out = new FileOutputStream(file);
	         byte[] buffer = new byte[1024];
	         int len;
	         while((len=in.read(buffer))>0)
	         {
	             out.write(buffer,0,len);
	         }
	         out.close();
	         in.close();
	     }
	     catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	}*/
	
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
	
	public P getFactions()
	{
		return factions;
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
		if(commandLabel.equalsIgnoreCase("pvpdebug") && player.hasPermission("particles.admin"))
		{
			if(debug == false)
			{
				debug = true;
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG MODE ENABLED");
				Bukkit.broadcastMessage("PVP PLAYER: " + PvpHandler.getPvpPlayer(player));
				Bukkit.broadcastMessage("PVP PLAYER NAME: " + PvpHandler.getPvpPlayer(player).getPlayer().getName());
			}
			else
				debug = false;
			if(PvpHandler.getPvpPlayer(player) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(player);
				PvpHandler.addPvpPlayer(newPVP);
			}
		}
		if(commandLabel.equalsIgnoreCase("pvpgod") && player.hasPermission("particles.admin"))
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
		if(commandLabel.equalsIgnoreCase("pvpver") && player.hasPermission("particles.admin"))
		{
			Bukkit.broadcastMessage("VERSION 1.5 BETA");
		}
		if(commandLabel.equalsIgnoreCase("polish"))
		{
			ArmorEffects.polish(player);
		}
		if(commandLabel.equalsIgnoreCase("pvpstats"))
		{
			//PVPPlayer pvpPlayer = Commands.getPVPPlayer(player); 
//			player.sendMessage(ChatColor.GREEN + "IF NULL HERE ERROR :" + pvpPlayer);
//			player.sendMessage(ChatColor.GREEN + "PLAYER NAME : " + pvpPlayer.getPlayer().getName());
//			player.sendMessage(ChatColor.GREEN + "MAXIMUM HEALTH  : " + pvpPlayer.getMaxHealth());
//			player.sendMessage(ChatColor.GREEN + "Health THIS TICK: " + pvpPlayer.gethealth());
//			player.sendMessage(ChatColor.GREEN + "Health LAST TICK: " + pvpPlayer.getHealthLastTick());
//			player.sendMessage(ChatColor.GREEN + "COOLDOWN (regen): " + pvpPlayer.getCooldown());
//			player.sendMessage(ChatColor.GREEN + "COMBAT COOLDOWN (FASTREGEN): " + pvpPlayer.getCombatCoolDown());
//			player.sendMessage(ChatColor.GREEN + "IS DEAD T/F : " + pvpPlayer.isDead);
//			player.sendMessage(ChatColor.GREEN + "IS IN COMBAT T/F : " + pvpPlayer.isInCombat);
//			player.sendMessage(ChatColor.GREEN + "HITCOOLDOWN : " + pvpPlayer.getHitCooldown());
//			player.sendMessage(ChatColor.GREEN + "CAN REGEN HEALTH T/F: " + pvpPlayer.canRegen);
			PVPPlayer pp = PvpHandler.getPvpPlayer(player);
			if(pp.isPvpstats())
			{
				pp.setPvpstats(false);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "DAMAGE CHECK MODE DISABLED SAY /PVPSTATS again to Enable");
			}
			else
			{
				pp.setPvpstats(true);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "DAMAGE CHECK MODE ENABLED SAY /PVPSTATS again to Disable");
			}
		}
        return true;
	}

}