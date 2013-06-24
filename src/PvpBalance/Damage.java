package PvpBalance;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import SaveLoad.LoadSave;

//Handles damage control
public class Damage
{
	
	public static LoadSave LoadSave;
	//ADDED++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static int calcDamage(Player player)
	{
		//Random rand = new Random();
		int damage = 25;
		if(player.getItemInHand().getType() == Material.DIAMOND_SWORD  || player.getItemInHand().getType() == Material.DIAMOND_AXE || player.getItemInHand().getType() == Material.DIAMOND_HOE)
		{
			damage += LoadSave.Diamond;
		}
		else if(player.getItemInHand().getType() == Material.IRON_SWORD || player.getItemInHand().getType() == Material.IRON_AXE || player.getItemInHand().getType() == Material.IRON_HOE)
		{
			damage += LoadSave.Iron;
		}
		else if(player.getItemInHand().getType() == Material.GOLD_SWORD || player.getItemInHand().getType() == Material.GOLD_AXE || player.getItemInHand().getType() == Material.GOLD_HOE)
		{
			damage += LoadSave.Gold;
		}
		else if(player.getItemInHand().getType() == Material.STONE_SWORD || player.getItemInHand().getType() == Material.STONE_AXE || player.getItemInHand().getType() == Material.STONE_HOE)
		{
			damage += LoadSave.Stone;
		}
		else if(player.getItemInHand().getType() == Material.WOOD_SWORD || player.getItemInHand().getType() == Material.WOOD_AXE || player.getItemInHand().getType() == Material.WOOD_HOE)
		{
			damage += LoadSave.Wood;
		}
		else if(player.getItemInHand().getType() == Material.BOW)
		{
			damage += 100;
			damage += player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE) * LoadSave.Sharpness;
		}
		damage += player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) * LoadSave.Sharpness;
		return damage;
		
	//END ADDED +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	}
	public static int calcArmor(Player player)
	{
		PVPPlayer pvpPlayer = Commands.getPVPPlayer(player);
		int armor = 500;
		for(ItemStack i:player.getInventory().getArmorContents())
		{
			int check = i.getTypeId();
			int protection = i.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) * LoadSave.protect;
			armor += protection;
			switch(check)
			{
				//DIAMOND =======================
				//diamond helm
				case 310: 
					armor += LoadSave.Dhelmet;
				break;
				
				//diamond chest
				case 311: armor += LoadSave.Dchest;
				break;
				
				//diamond leggings
				case 312: armor += LoadSave.Dleggings;
				break;
				
				//diamond boots
				case 313: armor += LoadSave.Dboots;
				break;
				//GOLD ===========================
				//gold helm
				case 314: armor += LoadSave.Ghelmet;
				break;
				
				//gold chest
				case 315: armor += LoadSave.Gchest;
				break;
				
				//gold leggings
				case 316: armor += LoadSave.Gleggings;
				break;
				
				//gold boots
				case 317: armor += LoadSave.Gboots;
				break;
				
				//IRON ===========================
				//iron helm
				case 306: armor += LoadSave.Ihelmet;
				break;
				
				//iron chest
				case 307: armor += LoadSave.Ichest;
				break;
				
				//iron leggings
				case 308: armor += LoadSave.Ileggings;
				break;
				
				//iron boots
				case 309: armor += LoadSave.Iboots;
				break;
				
				//Chain ===========================
				//chain helm
				case 302: armor += LoadSave.Chelmet;
				break;
				
				//chain chest
				case 303: armor += LoadSave.Cchest;
				break;
				
				//chain leggings
				case 304: armor += LoadSave.Cleggings;
				break;
				//chain boots
				case 305: armor += LoadSave.Cboots;
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
							armor += LoadSave.Lhelmet;
						}
						else
						{
							armor += LoadSave.Ehelmet;
						}
					}
					else
					{
						armor += LoadSave.Lhelmet;
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
							armor += LoadSave.Lchest;
						}
						else
						{
							armor += LoadSave.Echest;
						}
					}
					else
					{
						armor += LoadSave.Lchest;
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
							armor += LoadSave.Lleggings;
						}
						else
						{
							armor += LoadSave.Eleggings;
						}
					}
					else
					{
						armor += LoadSave.Lleggings;
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
							armor += LoadSave.Lboots;
						}
						else
						{
							armor += LoadSave.Eboots;
						}
					}
					else
					{
						armor += LoadSave.Lboots;
					}
					
					
				break;
			}
			
		}
		pvpPlayer.setMaxHealth(armor);	
		return armor;
	}
}
