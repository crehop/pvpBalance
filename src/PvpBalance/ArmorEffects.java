package PvpBalance;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import Util.ItemUtils;

public class ArmorEffects
{
	public static final String CODE_PAPER = "32157";
	public static final String CODE_ARMOR = "32188";

	public static void checkForGlowTick(Player player)
	{
		if(player == null)
			return;
		LeatherArmorMeta[] metas = new LeatherArmorMeta[5];
		for(ItemStack item : player.getInventory().getArmorContents())
		{
			if(item == null)
				continue;
			if(item.hasItemMeta() && (item.getTypeId() == 298 || item.getTypeId() == 299 || item.getTypeId() == 300 || item.getTypeId() == 301))
			{
				PVPPlayer pvpPlayer = PvpHandler.getPvpPlayer(player);
				if(pvpPlayer == null)
					return;
				LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
				if(ItemUtils.getLore(item) == null || ItemUtils.getLore(item).isEmpty())
					return;
				if(item.getItemMeta().getLore().get(0).toString().contains(CODE_ARMOR))
				{
					if(meta.getColor().toString().contains("A06540"))
						continue;
					
					int nr = (Fade.type(item)-1);
					if(metas[nr] != null)
					{
						meta.setColor(metas[nr].clone().getColor());
						item.setItemMeta(meta);
					}
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

	public static LeatherArmorMeta glow(ItemStack item, PVPPlayer pvpPlayer)
	{
			LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
			int blue = meta.getColor().getBlue();
			int red = meta.getColor().getRed();
			int green = meta.getColor().getGreen();
			if(Fade.type(item) == 1)
			{
				if(blue >= 90 && red >= 90 && green >= 90)
				{
					pvpPlayer.setColorUp(false);
				}
				else if(blue <= 0 && red <= 0 && green <= 0)
				{
					pvpPlayer.setColorUp(true);
				}
				
				if(pvpPlayer.isColorUp())
				{
					red += 9;
					blue += 9;
					green += 9;
				}
				else
				{
					red -= 9;
					blue -= 9;
					green -= 9;
				}
			}
			else if(Fade.type(item) == 2)
			{
				if(red >= 0 && green >= 0 && blue >= 248)
				{
					pvpPlayer.setColorUp(false);
				}
				else if(red <= 0 && green <= 0 && blue <= 128)
				{
					pvpPlayer.setColorUp(true);
				}
				if(pvpPlayer.isColorUp())
				{
					red += 0;
					green += 0;
					blue += 12;
				}
				else
				{
					red -= 0;
					green -= 0;
					blue -= 12;
				}
			}
			else if(Fade.type(item) == 3)
			{
				if(red >= 255 && green >= 0 && blue >= 0)
				{
					pvpPlayer.setColorUp(false);
				}
				else if(red <= 125 && green <= 0 && blue <= 0)
				{
					pvpPlayer.setColorUp(true);
				}
			if(pvpPlayer.isColorUp())
			{
				red += 13;
				blue = 0;
				green = 0;
			}
			else
			{
				red -= 13;
				blue = 0;
				green = 0;
			}
		}
		else if(Fade.type(item) == 4)
		{
			if(red >= 120 && green >= 250 && blue >= 0)
			{
				pvpPlayer.setColorUp(false);
			}
			else if(red <= 0 && green <= 100 && blue <= 0)
			{
				pvpPlayer.setColorUp(true);
			}
			if(pvpPlayer.isColorUp())
			{
				red += 12;
				green += 15;
				blue = 0;
			}
			else
			{
				red -= 12;
				green -= 15;
				blue = 0;
			}
		}
		else if(Fade.type(item) == 5 || item.getItemMeta().getLore().get(1) == "Polished Prized")
		{
			if(item.hasItemMeta() == true && item.getItemMeta().getLore().size() == 1)
			{
				List<String> lore = new ArrayList<String>();
				lore.add(0, item.getItemMeta().getLore().get(0));
				lore.add(1, "Polished Prized");
				meta.setLore(lore);
			}
			if(red == Fade.PRIZED.getRed() && blue == Fade.PRIZED.getBlue() && green == Fade.PRIZED.getGreen())
			{
				red = Fade.CURSED.getRed();
				blue = Fade.CURSED.getBlue();
				green = Fade.CURSED.getGreen();
			}
			else if(red == Fade.CURSED.getRed() && blue == Fade.CURSED.getBlue() && green == Fade.CURSED.getGreen())
			{
				red = Fade.FLAME.getRed();
				blue = Fade.FLAME.getBlue();
				green = Fade.FLAME.getGreen();
			}
			else if(red == Fade.FLAME.getRed() && blue == Fade.FLAME.getBlue() && green == Fade.FLAME.getGreen())
			{
				red = Fade.MITHRIL.getRed();
				blue = Fade.MITHRIL.getBlue();
				green = Fade.MITHRIL.getGreen();
			}
			else if(red == Fade.MITHRIL.getRed() && blue == Fade.MITHRIL.getBlue() && green == Fade.MITHRIL.getGreen())
			{
				red = Fade.VERITE.getRed();
				blue = Fade.VERITE.getBlue();
				green = Fade.VERITE.getGreen();
			}
			else if(red == Fade.VERITE.getRed() && blue == Fade.VERITE.getBlue() && green == Fade.VERITE.getGreen())
			{
				red = Fade.PRIZED.getRed();
				blue = Fade.PRIZED.getBlue();
				green = Fade.PRIZED.getGreen();
			}
			else
			{
				red = Fade.PRIZED.getRed();
				blue = Fade.PRIZED.getBlue();
				green = Fade.PRIZED.getGreen();
			}

		}	
		if(red < 0)
			red = 0;
		else if(red > 255)
			red = 255;

		if(green < 0)
			green = 0;
		else if(green > 255)
			green = 255;

		if(blue < 0)
			blue = 0;
		else if(blue > 255)
			blue = 255;

		Color color = Color.fromRGB(red, green, blue);
		meta.setColor(color);
		item.setItemMeta(meta);
		return meta.clone();
	}

	public static void polish(Player player)
	{
		ItemStack item = null;
		if(player.getItemInHand() == null)
		{
			player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + "You arent holding anything in your hand please hold the item you wish to polish " + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			return;
		}
		item = player.getItemInHand();
		if(item.getType() != Material.LEATHER_BOOTS && item.getType() != Material.LEATHER_CHESTPLATE && item.getType() != Material.LEATHER_HELMET && item.getType() != Material.LEATHER_LEGGINGS)
		{
			player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You are not holding the right item please hold the EPIC armor you want to polish " + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			return;
		}
		if(ItemUtils.getColor(item).toString().contains("A06540"))
		{
			player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " This is normal leather... not Epic " + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			return;
		}
		if(player.isOp())
		{
			Color color = ItemUtils.getColor(item);
			List<String> lore = new ArrayList<String>();
			lore.add(0, "Polished " + ChatColor.MAGIC + " " + CODE_ARMOR);
			lore.addAll(ItemUtils.getLore(item));
			ItemUtils.setLore(item, lore);
			if(color.getBlue() == 255 && color.getGreen() == 255 && color.getRed() == 255)
			{
				ItemUtils.setColor(item, Color.fromRGB(254, 255, 255));
				item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			}
			player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You polish the armor to a brilliant shine " + ChatColor.GOLD + "Bling Bling! ");
			return;
		}
		if(!player.getInventory().contains(Material.PAPER))
		{
			player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You did not have a Polishing Cloth" + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			return;
		}
		for(ItemStack paper : player.getInventory())
		{
			if(paper == null)
				continue;
			if(!paper.hasItemMeta())
				continue;
			if(!ItemUtils.hasLore(paper))
				continue;
			if(!ItemUtils.getLore(paper).get(0).toString().contains(CODE_PAPER))
				continue;
						
			if(paper.getAmount() <= 1)
				player.getInventory().removeItem(paper);
			else
				paper.setAmount(paper.getAmount() - 1);
			
			Color color = ItemUtils.getColor(item);
			List<String> lore = new ArrayList<String>();
			lore.add(0, "Polished " + ChatColor.MAGIC + " " + CODE_ARMOR);
			lore.addAll(ItemUtils.getLore(item));
			ItemUtils.setLore(item, lore);
			if(color.getBlue() == 255 && color.getGreen() == 255 && color.getRed() == 255)
			{
				ItemUtils.setColor(item, Color.fromRGB(254, 255, 255));
				item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			}
			player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You polish the armor to a brilliant shine " + ChatColor.GOLD + "Bling Bling! ");
			return;
		}
	}
}
