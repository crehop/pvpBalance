package PvpBalance;

<<<<<<< HEAD
=======
import me.ThaH3lper.com.DungeonAPI;

>>>>>>> parent of 45fd277... Added Party System, Added Check On ItemBreak Update Health
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.palmergames.bukkit.towny.utils.CombatUtil;


//Handles damage control
public class Damage
{

	public static SaveLoad.LoadSave LoadSave;

	//ADDED++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static int calcDamage(Player player)
	{
		//Random rand = new Random();
		int damage = 25;
		if(player.getItemInHand().getType() == Material.DIAMOND_SWORD  || player.getItemInHand().getType() == Material.DIAMOND_AXE || player.getItemInHand().getType() == Material.DIAMOND_HOE)
		{
			damage += SaveLoad.LoadSave.Diamond;
		}
		else if(player.getItemInHand().getType() == Material.IRON_SWORD || player.getItemInHand().getType() == Material.IRON_AXE || player.getItemInHand().getType() == Material.IRON_HOE)
		{
			damage += SaveLoad.LoadSave.Iron;
		}
		else if(player.getItemInHand().getType() == Material.GOLD_SWORD || player.getItemInHand().getType() == Material.GOLD_AXE || player.getItemInHand().getType() == Material.GOLD_HOE)
		{
			damage += SaveLoad.LoadSave.Gold;
		}
		else if(player.getItemInHand().getType() == Material.STONE_SWORD || player.getItemInHand().getType() == Material.STONE_AXE || player.getItemInHand().getType() == Material.STONE_HOE)
		{
			damage += SaveLoad.LoadSave.Stone;
		}
		else if(player.getItemInHand().getType() == Material.WOOD_SWORD || player.getItemInHand().getType() == Material.WOOD_AXE || player.getItemInHand().getType() == Material.WOOD_HOE)
		{
			damage += SaveLoad.LoadSave.Wood;
		}
		else if(player.getItemInHand().getType() == Material.BOW)
		{
			damage += SaveLoad.LoadSave.Bow;
			damage += player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE) * SaveLoad.LoadSave.Sharpness;
		}
		damage += player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) * SaveLoad.LoadSave.Sharpness;
		if(player.getActivePotionEffects().toString().contains("WEAK")){
			damage = (damage/4) * 3;
		}
		return damage;
		
	//END ADDED +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	}
	
	public static int calcArmor(Player player)
	{
		PVPPlayer pvpPlayer = PvpHandler.getPvpPlayer(player);
		int armor = 500;
		for(ItemStack i:player.getInventory().getArmorContents())
		{
			int check = i.getTypeId();
			int protection = i.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) * SaveLoad.LoadSave.protect;
			armor += protection;
			switch(check)
			{
				//DIAMOND =======================
				//diamond helm
				case 310: 
					armor += SaveLoad.LoadSave.Dhelmet;
				break;
				
				//diamond chest
				case 311:
					armor += SaveLoad.LoadSave.Dchest;
				break;
				
				//diamond leggings
				case 312:
					armor += SaveLoad.LoadSave.Dleggings;
				break;
				
				//diamond boots
				case 313:
					armor += SaveLoad.LoadSave.Dboots;
				break;
				//GOLD ===========================
				//gold helm
				case 314:
					armor += SaveLoad.LoadSave.Ghelmet;
				break;
				
				//gold chest
				case 315:
					armor += SaveLoad.LoadSave.Gchest;
				break;
				
				//gold leggings
				case 316:
					armor += SaveLoad.LoadSave.Gleggings;
				break;
				
				//gold boots
				case 317:
					armor += SaveLoad.LoadSave.Gboots;
				break;
				
				//IRON ===========================
				//iron helm
				case 306:
					armor += SaveLoad.LoadSave.Ihelmet;
				break;
				
				//iron chest
				case 307:
					armor += SaveLoad.LoadSave.Ichest;
				break;
				
				//iron leggings
				case 308:
					armor += SaveLoad.LoadSave.Ileggings;
				break;
				
				//iron boots
				case 309:
					armor += SaveLoad.LoadSave.Iboots;
				break;
				
				//Chain ===========================
				//chain helm
				case 302:
					armor += SaveLoad.LoadSave.Chelmet;
				break;
				
				//chain chest
				case 303:
					armor += SaveLoad.LoadSave.Cchest;
				break;
				
				//chain leggings
				case 304:
					armor += SaveLoad.LoadSave.Cleggings;
				break;
				//chain boots
				case 305:
					armor += SaveLoad.LoadSave.Cboots;
				break;
				
				//Leather ===========================
				//leather helm
				case 298: 
					ItemStack helmet = player.getInventory().getHelmet();
					if(helmet != null && helmet.hasItemMeta() == true && helmet.getItemMeta().getLore().get(0).toString().contains(ArmorEffects.CODE_ARMOR))
					{
						Fade.setBaseColor(helmet);
						armor += 75;
					}
					if(helmet != null && helmet.hasItemMeta() == true)
					{
						ItemMeta metah = helmet.getItemMeta();
						LeatherArmorMeta meta = (LeatherArmorMeta) metah;
						if(meta.getColor().toString().contains("A06540"))
						{
							armor += SaveLoad.LoadSave.Lhelmet;
						}
						else
						{
							armor += SaveLoad.LoadSave.Ehelmet;
						}
					}
					else
					{
						armor += SaveLoad.LoadSave.Lhelmet;
					}

				break;
				
				//leather chest
				case 299: 
					ItemStack chestplate = player.getInventory().getChestplate();
					if(chestplate != null && chestplate.hasItemMeta() == true && chestplate.getItemMeta().getLore().get(0).toString().contains(ArmorEffects.CODE_ARMOR))
					{
						Fade.setBaseColor(chestplate);
						armor += 75;
					}
					if(chestplate != null && chestplate.hasItemMeta() == true)
					{
						ItemMeta metah2 = chestplate.getItemMeta();
						LeatherArmorMeta meta2 = (LeatherArmorMeta) metah2;
						if(meta2.getColor().toString().contains("A06540"))
						{
							armor += SaveLoad.LoadSave.Lchest;
						}
						else
						{
							armor += SaveLoad.LoadSave.Echest;
						}
					}
					else
					{
						armor += SaveLoad.LoadSave.Lchest;
					}
				break;
				
				//leather leggings
				case 300:
					ItemStack leggings = player.getInventory().getLeggings();
					if(leggings != null && leggings.hasItemMeta() == true && leggings.getItemMeta().getLore().get(0).toString().contains(ArmorEffects.CODE_ARMOR))
					{
						Fade.setBaseColor(leggings);
						armor += 75;
					}
					if(leggings != null && leggings.hasItemMeta() == true)
					{
						ItemMeta metah3 = leggings.getItemMeta();
						LeatherArmorMeta meta3 = (LeatherArmorMeta) metah3;
						if(meta3.getColor().toString().contains("A06540"))
						{
							armor += SaveLoad.LoadSave.Lleggings;
						}
						else
						{
							armor += SaveLoad.LoadSave.Eleggings;
						}
					}
					else
					{
						armor += SaveLoad.LoadSave.Lleggings;
					}
				break;
				
				//leather boots
				case 301: 
					ItemStack boots = player.getInventory().getBoots();
					if(boots != null && boots.hasItemMeta() == true && boots.getItemMeta().getLore().get(0).toString().contains(ArmorEffects.CODE_ARMOR))
					{
						Fade.setBaseColor(boots);
						armor += 75;
					}
					if(boots != null && boots.hasItemMeta() == true)
					{
						ItemMeta metah4 = boots.getItemMeta();
						LeatherArmorMeta meta4 = (LeatherArmorMeta) metah4;
						if(meta4.getColor().toString().contains("A06540"))
						{
							armor += SaveLoad.LoadSave.Lboots;
						}
						else
						{
							armor += SaveLoad.LoadSave.Eboots;
						}
					}
					else
					{
						armor += SaveLoad.LoadSave.Lboots;
					}
				break;
			}
		}
		
<<<<<<< HEAD
=======
		if(me.crehop.com.Subscribers.isSubscriber(player.getName()) == true){
			armor += (armor/10);
		}
>>>>>>> parent of 45fd277... Added Party System, Added Check On ItemBreak Update Health
		pvpPlayer.setMaxHealth(armor);	
		return armor;
	}
	
	public static boolean canHit(Entity damagee, Entity damager)
	{
		if(CombatUtil.preventDamageCall(damager, damagee))
			return false;
		EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, DamageCause.CUSTOM, 1D);
		if(!DungeonAPI.canhit(event))
			return false;
		if(PvpBalance.plugin.hasFaction())
		{
			if(!PvpBalance.plugin.getFactions().entityListener.canDamagerHurtDamagee(event, true))
				return false;
		}
		return true;
			
	}
}
