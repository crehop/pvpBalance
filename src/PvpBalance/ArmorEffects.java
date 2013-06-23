package PvpBalance;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorEffects
{
	public static final String CODE_PAPER = "32156";
	public static final String CODE_ARMOR = "32188";
	
	public static void checkForGlowTick(Player player)
	{
		LeatherArmorMeta[] metas = new LeatherArmorMeta[5];
		for(ItemStack item : player.getInventory().getArmorContents())
		{
			if((item != null && item.hasItemMeta()) && (item.getTypeId() == 298 || item.getTypeId() == 299 || item.getTypeId() == 300 || item.getTypeId() == 301))
			{
				PVPPlayer pvpPlayer = Commands.getPVPPlayer(player);
				LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
				if(item.getItemMeta().getLore().get(0).toString().contains(CODE_ARMOR))
				{
					if(!meta.getColor().toString().contains("A06540"))
					{
						int nr = Fade.type(item)-1;
						if(metas[nr] != null)
							((LeatherArmorMeta)item.getItemMeta()).setColor(metas[nr].clone().getColor());
						else
						{
							switch(item.getTypeId())
							{
							case 298:
							case 299:
							case 300:
							case 301:
								metas[nr] = glow(item,pvpPlayer);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public static LeatherArmorMeta glow(ItemStack item, PVPPlayer pvpPlayer)
	{
			LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
			int blue = meta.getColor().getBlue();
			int red = meta.getColor().getRed();
			int green = meta.getColor().getGreen();
			if(Fade.type(item) == 1)
			{
				if(blue >= 90 || red >= 90 || green >= 90)
				{
					pvpPlayer.colorUpHelmet = false;
					/*if(item.getType() == Material.LEATHER_HELMET)
					{
						pvpPlayer.colorUpHelmet = false;
					}
					else if(item.getType() == Material.LEATHER_CHESTPLATE)
					{
						pvpPlayer.colorUpChest = false;
					}
					else if(item.getType() == Material.LEATHER_LEGGINGS)
					{
						pvpPlayer.colorUpLeggings = false;
					}
					else if(item.getType() == Material.LEATHER_BOOTS)
					{
						pvpPlayer.colorUpBoots = false;
					}*/
				}
				else if(blue <= 0 || red <= 0 || green <= 0)
				{
					pvpPlayer.colorUpHelmet = true;
					/*if(item.getType() == Material.LEATHER_HELMET)
					{
						pvpPlayer.colorUpHelmet = true;
					}
					else if(item.getType() == Material.LEATHER_CHESTPLATE)
					{
						pvpPlayer.colorUpChest = true;
					}
					else if(item.getType() == Material.LEATHER_LEGGINGS)
					{
						pvpPlayer.colorUpLeggings = true;
					}
					else if(item.getType() == Material.LEATHER_BOOTS)
					{
						pvpPlayer.colorUpBoots = true;
					}*/
				}
				if(pvpPlayer.colorUpHelmet == true && item.getType() == Material.LEATHER_HELMET)
				{
					red += 9;
					blue += 9;
					green += 9;
				}
				else if(pvpPlayer.colorUpHelmet == false && item.getType() == Material.LEATHER_HELMET)
				{
					red -= 9;
					blue -= 9;
					green -= 9;
				}
				/*if(pvpPlayer.colorUpChest == true && item.getType() == Material.LEATHER_CHESTPLATE)
				{
					red += 5;
					blue += 5;
					green += 5;
				}
				else if(pvpPlayer.colorUpChest == false && item.getType() == Material.LEATHER_CHESTPLATE)
				{
					red -= 5;
					blue -= 5;
					green -= 5;
				}
				if(pvpPlayer.colorUpLeggings == true && item.getType() == Material.LEATHER_LEGGINGS)
				{
					red += 5;
					blue += 5;
					green += 5;
				}
				else if(pvpPlayer.colorUpLeggings == false && item.getType() == Material.LEATHER_LEGGINGS)
				{
					red -= 5;
					blue -= 5;
					green -= 5;
				}
				if(pvpPlayer.colorUpBoots == true && item.getType() == Material.LEATHER_BOOTS)
				{
					red += 5;
					blue += 5;
					green += 5;
				}
				else if(pvpPlayer.colorUpBoots == false && item.getType() == Material.LEATHER_BOOTS)
				{
					red -= 5;
					blue -= 5;
					green -= 5;
				}*/
			}
			else if(Fade.type(item) == 2)
			{
				if(red >= 100 || green >= 180 || blue >= 255)
				{
					pvpPlayer.colorUpHelmet = false;
					/*if(item.getType() == Material.LEATHER_HELMET)
					{
						pvpPlayer.colorUpHelmet = false;
					}
					if(item.getType() == Material.LEATHER_CHESTPLATE)
					{
						pvpPlayer.colorUpChest = false;
					}
					if(item.getType() == Material.LEATHER_LEGGINGS)
					{
						pvpPlayer.colorUpLeggings = false;
					}
					if(item.getType() == Material.LEATHER_BOOTS)
					{
						pvpPlayer.colorUpBoots = false;
					}*/
				}
				else if(red >= 0 || green >= 0 || blue >= 205)
				{
					pvpPlayer.colorUpHelmet = true;
					/*if(item.getType() == Material.LEATHER_HELMET)
					{
						pvpPlayer.colorUpHelmet = true;
					}
					if(item.getType() == Material.LEATHER_CHESTPLATE)
					{
						pvpPlayer.colorUpChest = true;
					}
					if(item.getType() == Material.LEATHER_LEGGINGS)
					{
						pvpPlayer.colorUpLeggings = true;
					}
					if(item.getType() == Material.LEATHER_BOOTS)
					{
						pvpPlayer.colorUpBoots = true;
					}*/
				}
				if(pvpPlayer.colorUpHelmet == true && item.getType() == Material.LEATHER_HELMET)
				{
					red += 10;
					green += 18;
					blue += 5;
				}
				else if(pvpPlayer.colorUpHelmet == false && item.getType() == Material.LEATHER_HELMET)
				{
					red -= 10;
					green -= 18;
					blue -= 5;
				}
				/*if(pvpPlayer.colorUpChest == true && item.getType() == Material.LEATHER_CHESTPLATE)
				{
					red += 7;
					blue += 7;
					green += 7;
				}
				if(pvpPlayer.colorUpChest == false && item.getType() == Material.LEATHER_CHESTPLATE)
				{
					red -= 7;
					blue -= 7;
					green -= 7;
				}
				if(pvpPlayer.colorUpLeggings == true && item.getType() == Material.LEATHER_LEGGINGS)
				{
					red += 7;
					blue += 7;
					green += 7;
				}
				if(pvpPlayer.colorUpLeggings == false && item.getType() == Material.LEATHER_LEGGINGS)
				{
					red -= 7;
					blue -= 7;
					green -= 7;
				}
				if(pvpPlayer.colorUpBoots == true && item.getType() == Material.LEATHER_BOOTS)
				{
					red += 7;
					blue += 7;
					green += 7;
				}
				if(pvpPlayer.colorUpBoots == false && item.getType() == Material.LEATHER_BOOTS)
				{
					red -= 7;
					blue -= 7;
					green -= 7;
				}*/
			}
			else if(Fade.type(item) == 3)
			{
				if(red >= 255 || green >= 0 || blue >= 0)
				{
					pvpPlayer.colorUpHelmet = false;
					/*if(item.getType() == Material.LEATHER_HELMET)
					{
						pvpPlayer.colorUpHelmet = false;
					}
					if(item.getType() == Material.LEATHER_CHESTPLATE)
					{
						pvpPlayer.colorUpChest = false;
					}
					if(item.getType() == Material.LEATHER_LEGGINGS)
					{
						pvpPlayer.colorUpLeggings = false;
					}
					if(item.getType() == Material.LEATHER_BOOTS)
					{
						pvpPlayer.colorUpBoots = false;
					}*/
				}
				else if(red <= 125 || green <= 0 || blue <= 0)
				{
					pvpPlayer.colorUpHelmet = true;
					/*if(item.getType() == Material.LEATHER_HELMET)
					{
						pvpPlayer.colorUpHelmet = true;
					}
					if(item.getType() == Material.LEATHER_CHESTPLATE)
					{
						pvpPlayer.colorUpChest = true;
					}
					if(item.getType() == Material.LEATHER_LEGGINGS)
					{
						pvpPlayer.colorUpLeggings = true;
					}
					if(item.getType() == Material.LEATHER_BOOTS)
					{
						pvpPlayer.colorUpBoots = true;
					}*/
				}
			if(pvpPlayer.colorUpHelmet == true && item.getType() == Material.LEATHER_HELMET)
			{
				red += 13;
				blue += 0;
				green += 0;
			}
			else if(pvpPlayer.colorUpHelmet == false && item.getType() == Material.LEATHER_HELMET)
			{
				red -= 13;
				blue -= 0;
				green -= 0;
			}
			/*if(pvpPlayer.colorUpChest == true && item.getType() == Material.LEATHER_CHESTPLATE)
			{
				red += 5;
				blue += 3;
				green += 3;
			}
			if(pvpPlayer.colorUpChest == false && item.getType() == Material.LEATHER_CHESTPLATE)
			{
				red -= 5;
				blue -= 3;
				green -= 3;
			}
			if(pvpPlayer.colorUpLeggings == true && item.getType() == Material.LEATHER_LEGGINGS)
			{
				red += 5;
				blue += 3;
				green += 3;
			}
			if(pvpPlayer.colorUpLeggings == false && item.getType() == Material.LEATHER_LEGGINGS)
			{
				red -= 5;
				blue -= 3;
				green -= 3;
			}
			if(pvpPlayer.colorUpBoots == true && item.getType() == Material.LEATHER_BOOTS)
			{
				red += 10;
				blue += 7;
				green += 7;
			}
			if(pvpPlayer.colorUpBoots == false && item.getType() == Material.LEATHER_BOOTS)
			{
				red -= 5;
				blue -= 3;
				green -= 3;
			}*/
		}
		else if(Fade.type(item) == 4)
		{
			if(red >= 120 || green >= 250 || blue >= 0)
			{
				pvpPlayer.colorUpHelmet = false;
				/*if(item.getType() == Material.LEATHER_HELMET)
				{
					pvpPlayer.colorUpHelmet = false;
				}
				if(item.getType() == Material.LEATHER_CHESTPLATE)
				{
					pvpPlayer.colorUpChest = false;
				}
				if(item.getType() == Material.LEATHER_LEGGINGS)
				{
					pvpPlayer.colorUpLeggings = false;
				}
				if(item.getType() == Material.LEATHER_BOOTS)
				{
					pvpPlayer.colorUpBoots = false;
				}*/
			}
			else if(red <= 0 || green <= 100 || blue <= 0)
			{
				pvpPlayer.colorUpHelmet = true;
				/*if(item.getType() == Material.LEATHER_HELMET)
				{
					pvpPlayer.colorUpHelmet = true;
				}
				if(item.getType() == Material.LEATHER_CHESTPLATE)
				{
					pvpPlayer.colorUpChest = true;
				}
				if(item.getType() == Material.LEATHER_LEGGINGS)
				{
					pvpPlayer.colorUpLeggings = true;
				}
				if(item.getType() == Material.LEATHER_BOOTS)
				{
					pvpPlayer.colorUpBoots = true;
				}*/
			}
			if(pvpPlayer.colorUpHelmet == true && item.getType() == Material.LEATHER_HELMET)
			{
				red += 12;
				green += 15;
				blue += 0;
			}
			else if(pvpPlayer.colorUpHelmet == false && item.getType() == Material.LEATHER_HELMET)
			{
				red -= 12;
				green -= 15;
				blue -= 0;
			}
			/*if(pvpPlayer.colorUpChest == true && item.getType() == Material.LEATHER_CHESTPLATE)
			{
				red += 6;
				blue += 1;
				green += 7;
			}
			if(pvpPlayer.colorUpChest == false && item.getType() == Material.LEATHER_CHESTPLATE)
			{
				red -= 6;
				blue -= 1;
				green -= 7;
			}
			if(pvpPlayer.colorUpLeggings == true && item.getType() == Material.LEATHER_LEGGINGS)
			{
				red += 6;
				blue += 1;
				green += 7;
			}
			if(pvpPlayer.colorUpLeggings == false && item.getType() == Material.LEATHER_LEGGINGS)
			{
				red -= 6;
				blue -= 1;
				green -= 7;
			}
			if(pvpPlayer.colorUpBoots == true && item.getType() == Material.LEATHER_BOOTS)
			{
				red += 6;
				blue += 1;
				green += 7;
			}
			if(pvpPlayer.colorUpBoots == false && item.getType() == Material.LEATHER_BOOTS)
			{
				red -= 12;
				blue -= 1;
				green -= 15;
			}*/
		}
		//meta.getColor().setBlue(blue);
		//meta.getColor().setGreen(green);
		//meta.getColor().setRed(red);
		Color color = Color.fromRGB(red, green, blue);
		meta.setColor(color);
		item.setItemMeta(meta);
		return meta.clone();
	}
	
	public static void polish(Player player)
	{
		boolean hasCloth = false;
		boolean alreadyRemoved = false;
		boolean correctItemInHand = false;
		ItemStack item = null;
		if(player.getItemInHand() != null)
		{
			item = player.getItemInHand();
			if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_LEGGINGS)
			{
				correctItemInHand = true;
			}
			if(player.getInventory().contains(Material.PAPER) && item != null)
			{
				for(ItemStack i: player.getInventory())
				{
						try
						{
							ItemMeta itemMeta = null;
							if(i != null){
								if(i.hasItemMeta())
								{
									itemMeta = i.getItemMeta();
								}
								if(i.hasItemMeta() && itemMeta.hasLore() && correctItemInHand == true)
								{
									if(i.getItemMeta().getLore().get(0).toString().contains(CODE_PAPER))
									{
										if(i.getAmount() > 1 && alreadyRemoved == false)
										{
											alreadyRemoved = true;
											hasCloth = true;
											i.setAmount(i.getAmount() - 1);
										}
										else if(alreadyRemoved == false)
										{
											alreadyRemoved = true;
											hasCloth = true;
											player.getInventory().removeItem(i);
										}
									}
									if(item.getTypeId() == 298 || item.getTypeId() == 299 || item.getTypeId() == 300 || item.getTypeId() == 301 && hasCloth == true)
									{
										ItemMeta metah = item.getItemMeta();
										LeatherArmorMeta meta = (LeatherArmorMeta) metah;
										if(!meta.getColor().toString().contains("A06540"))
										{
											List<String> lore = new ArrayList<String>();
											lore.add(0, "Polished " + ChatColor.MAGIC + " " + CODE_ARMOR);
											meta.setLore(lore);
											item.setItemMeta(meta);
											if(meta.getColor().getBlue() == 255 && meta.getColor().getGreen() == 255 && meta.getColor().getRed() == 255)
											{
												meta.setColor(Color.fromRGB(254, 255, 255));
												item.setItemMeta(meta);
												item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
											}
										}
										else
										{
											player.sendMessage("THIS IS NORMAL ARMOR DONT TRY TO CHEAT!");
													
										}	
									}
								}
							}
						}
						catch (NullPointerException e1)
						{
								e1.printStackTrace();
						} 
				}
			}
		}
		if(player.isOp() && hasCloth == false)
		{
			ItemStack i = new ItemStack(Material.PAPER);
			ItemMeta imeta = i.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add(0, "Polishing Cloth " + ChatColor.MAGIC + " " + CODE_PAPER);
			lore.add(1, ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			imeta.setLore(lore);
			imeta.setDisplayName(ChatColor.GOLD + "Polishing Cloth");
			i.setItemMeta(imeta);
			player.getInventory().addItem(i);
			player.sendMessage(ChatColor.GREEN + "[Armor Polish]: Greetings Administrator " + player.getDisplayName() + " a cloth has been provided please try again.");
		}
		if(correctItemInHand == false && hasCloth == true)
		{
			player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You sre not holding epic armor in your hand " + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
		}
		if(hasCloth == false)
		{
			player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You did not have a Polishing Cloth or are not holding the right item please say " + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
		}
		if(correctItemInHand == false && hasCloth == true && alreadyRemoved == true)
		{
			player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You polish the armor to a briliant shine " + ChatColor.GOLD + "Bling Bling! ");
		}
	}
}
