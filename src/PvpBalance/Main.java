package PvpBalance;
import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import com.massivecraft.factions.P;

import SaveLoad.LoadSave;
import SaveLoad.Save;


public class Main extends JavaPlugin
{
	public static List<Player> cooldown=new ArrayList<Player>();
	public static List<Player> pvpstats=new ArrayList<Player>();
	public static List<PVPPlayer> PVP=new ArrayList<PVPPlayer>();
	public static List<Entity> projectile=new ArrayList<Entity>();
	public final Logger logger=Logger.getLogger("Minecraft");
    public static Main plugin;
    public File configFile;
	private static String name = "witchMagic";
	private static float offset = (float) 0.1;
	private static float speed = (float) 0.2;
	private static int amount = 300;
	public static boolean particulating = false;
	public static int everyOther = 0;
	public static boolean debug = false;
	
	public P factions;
	public boolean faction = false;
	
	public Save sDamage, Protection;


 
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
	 	Protection = new Save(this, "Protection.yml");
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
								PVPPlayer PVPPlayer = Commands.getPVPPlayer(all);
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
					    			Effects.effectSpeedPlayers(all, speed, getAmount());
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
						catch (IllegalArgumentException e1)
					    {
							e1.printStackTrace();
						} 
						catch (NullPointerException e1)
						{
							e1.printStackTrace();
						}
						catch (ConcurrentModificationException e1)
						{
							e1.printStackTrace();
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
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
		    		catch (IllegalArgumentException e1)
		    		{
							e1.printStackTrace();
					}
		    		catch (NullPointerException e1)
		    		{
							e1.printStackTrace();
		    		}
		    		catch (ConcurrentModificationException e1)
		    		{
							e1.printStackTrace();
					}
		    		catch (Exception e1)
		    		{
							e1.printStackTrace();
					}
				}
		    }
		    if(everyOther == 2)
		    {
		    	everyOther = 0;
		    	//TICK PVPPLAYERS TO ITERATE COOLDOWNS
		    	for(PVPPlayer all: PVP)
		    	{
					try
					{
						
						all.tick();
						
					}
					catch (IllegalArgumentException e1)
					{
						e1.printStackTrace();
					}
					catch (NullPointerException e1)
					{
						e1.printStackTrace();
					}
					catch (ConcurrentModificationException e1)
					{
						e1.printStackTrace();
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
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
 	
	public static String getParticleName()
	{
		return name;
	}
	
	public static float getOffset()
	{
		return offset;
	}
	
	public static float getSpeed()
	{
		return speed;
	}
	
	public static int getAmount()
	{
		return amount;
	}
	
	public boolean isParticulating()
	{
		return particulating;
	}
	
	public boolean onCommand(CommandSender sender,Command cmd,String commandLabel, String[] args)
	{
		Player player = (Player) sender;
		//Location location = player.getLocation();
		if(commandLabel.equalsIgnoreCase("pvpgod") && player.hasPermission("particles.admin"))
		{
			PVPPlayer PVPPlayer = Commands.getPVPPlayer(player);
			if(PVPPlayer.isGod() == true){
				PVPPlayer.setGod(false);
			}
			else{
				PVPPlayer.setGod(true);
			}
		}
		if(commandLabel.equalsIgnoreCase("pvpdebug") && player.hasPermission("particles.admin"))
		{
			if(debug == false)
			{
				debug = true;
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG MODE ENABLED");
				Bukkit.broadcastMessage("PVP PLAYER: " + Commands.getPVPPlayer(player));
				Bukkit.broadcastMessage("PVP PLAYER NAME: " + Commands.getPVPPlayer(player).getPlayer().getName());
			}
			else
				debug = false;
			if(Commands.getPVPPlayer(player) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(player);
				Main.PVP.add(newPVP);
			}

		}
		if(commandLabel.equalsIgnoreCase("pvpver") && player.hasPermission("particles.admin"))
		{
			Bukkit.broadcastMessage("VERSION .05 BETA");
		}
		if(commandLabel.equalsIgnoreCase("polish"))
		{
			ArmorEffects.polish(player);
		}
		if(commandLabel.equalsIgnoreCase("pvpinfo"))
		{
			PVPPlayer pvpPlayer = Commands.getPVPPlayer(player);
			player.sendMessage(ChatColor.GREEN + "IF NULL HERE ERROR :" + pvpPlayer);
			player.sendMessage(ChatColor.GREEN + "PLAYER NAME : " + pvpPlayer.getPlayer().getName());
			player.sendMessage(ChatColor.GREEN + "MAXIMUM HEALTH  : " + pvpPlayer.getMaxHealth());
			player.sendMessage(ChatColor.GREEN + "Health THIS TICK: " + pvpPlayer.gethealth());
			player.sendMessage(ChatColor.GREEN + "Health LAST TICK: " + pvpPlayer.getHealthLastTick());
			player.sendMessage(ChatColor.GREEN + "COOLDOWN (regen): " + pvpPlayer.getCooldown());
			player.sendMessage(ChatColor.GREEN + "COMBAT COOLDOWN (FASTREGEN): " + pvpPlayer.getCombatCoolDown());
			player.sendMessage(ChatColor.GREEN + "IS DEAD T/F : " + pvpPlayer.isDead);
			player.sendMessage(ChatColor.GREEN + "IS IN COMBAT T/F : " + pvpPlayer.isInCombat);
			player.sendMessage(ChatColor.GREEN + "CAN REGEN HEALTH T/F: " + pvpPlayer.canRegen);
			if(pvpstats.contains(player))
			{
				pvpstats.remove(player);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "DAMAGE CHECK MODE DISABLED SAY /PVPINFO again to Enable");
			}
			else
			{
				pvpstats.add(player);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "DAMAGE CHECK MODE ENABLED SAY /PVPINFO again to Disable");
			}
		}
        return true;
	}

}