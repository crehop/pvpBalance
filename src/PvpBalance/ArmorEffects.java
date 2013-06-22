package PvpBalance;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorEffects {
	public static final String CODE_PAPER = "32156";
	public static final String CODE_ARMOR = "32188";
	public static void checkForGlowTick(Player player){
		for(ItemStack item:player.getInventory().getArmorContents()){
			if(item.getTypeId() == 298 || item.getTypeId() == 299 || item.getTypeId() == 300 || item.getTypeId() == 301){
				PVPPlayer PVPPlayer = Commands.getPVPPlayer(player);
				ItemMeta metah = item.getItemMeta();
				LeatherArmorMeta meta = (LeatherArmorMeta) metah;
				if(item.getItemMeta().getLore().get(0).toString().contains(CODE_ARMOR)){
					if(!meta.getColor().toString().contains("A06540")){
						if(item.getTypeId() == 298){
							glow(item,PVPPlayer);
						}
						if(item.getTypeId() == 299){
							glow(item,PVPPlayer);
						}
						if(item.getTypeId() == 300){
							glow(item,PVPPlayer);
						}
						if(item.getTypeId() == 301){
							glow(item,PVPPlayer);
						}
					}
				}
			}
		}
	}
	public static void glow(ItemStack i, PVPPlayer pvpPlayer){
		ItemMeta metah = i.getItemMeta();
		LeatherArmorMeta meta = (LeatherArmorMeta) metah;
		int blue = meta.getColor().getBlue();
		int red = meta.getColor().getRed();
		int green = meta.getColor().getGreen();
		if(Fade.type(i) == 1){
			if(blue >= 100 || red >= 100 || green >= 100){
				if(i.getType() == Material.LEATHER_HELMET){
					pvpPlayer.colorUpHelmet = false;
				}
				if(i.getType() == Material.LEATHER_CHESTPLATE){
					pvpPlayer.colorUpChest = false;
				}
				if(i.getType() == Material.LEATHER_LEGGINGS){
					pvpPlayer.colorUpLeggings = false;
				}
				if(i.getType() == Material.LEATHER_BOOTS){
					pvpPlayer.colorUpBoots = false;
				}
			}
			if(blue <= 11 || red <= 11 || green <= 11){
				if(i.getType() == Material.LEATHER_HELMET){
					pvpPlayer.colorUpHelmet = true;
				}
				if(i.getType() == Material.LEATHER_CHESTPLATE){
					pvpPlayer.colorUpChest = true;
				}
				if(i.getType() == Material.LEATHER_LEGGINGS){
					pvpPlayer.colorUpLeggings = true;
				}
				if(i.getType() == Material.LEATHER_BOOTS){
					pvpPlayer.colorUpBoots = true;
				}
			}
			if(pvpPlayer.colorUpHelmet == true && i.getType() == Material.LEATHER_HELMET){
				red += 5;
				blue += 5;
				green += 5;
			}
			if(pvpPlayer.colorUpHelmet == false && i.getType() == Material.LEATHER_HELMET){
				red -= 5;
				blue -= 5;
				green -= 5;
			}
			if(pvpPlayer.colorUpChest == true && i.getType() == Material.LEATHER_CHESTPLATE){
				red += 5;
				blue += 5;
				green += 5;
			}
			if(pvpPlayer.colorUpChest == false && i.getType() == Material.LEATHER_CHESTPLATE){
				red -= 5;
				blue -= 5;
				green -= 5;
			}
			if(pvpPlayer.colorUpLeggings == true && i.getType() == Material.LEATHER_LEGGINGS){
				red += 5;
				blue += 5;
				green += 5;
			}
			if(pvpPlayer.colorUpLeggings == false && i.getType() == Material.LEATHER_LEGGINGS){
				red -= 5;
				blue -= 5;
				green -= 5;
			}
			if(pvpPlayer.colorUpBoots == true && i.getType() == Material.LEATHER_BOOTS){
				red += 5;
				blue += 5;
				green += 5;
			}
			if(pvpPlayer.colorUpBoots == false && i.getType() == Material.LEATHER_BOOTS){
				red -= 5;
				blue -= 5;
				green -= 5;
			}
		}
		if(Fade.type(i) == 2){
			if(blue >= 249){
				if(i.getType() == Material.LEATHER_HELMET){
					pvpPlayer.colorUpHelmet = false;
				}
				if(i.getType() == Material.LEATHER_CHESTPLATE){
					pvpPlayer.colorUpChest = false;
				}
				if(i.getType() == Material.LEATHER_LEGGINGS){
					pvpPlayer.colorUpLeggings = false;
				}
				if(i.getType() == Material.LEATHER_BOOTS){
					pvpPlayer.colorUpBoots = false;
				}
			}
			if(red <= 6){
				if(i.getType() == Material.LEATHER_HELMET){
					pvpPlayer.colorUpHelmet = true;
				}
				if(i.getType() == Material.LEATHER_CHESTPLATE){
					pvpPlayer.colorUpChest = true;
				}
				if(i.getType() == Material.LEATHER_LEGGINGS){
					pvpPlayer.colorUpLeggings = true;
				}
				if(i.getType() == Material.LEATHER_BOOTS){
					pvpPlayer.colorUpBoots = true;
				}
			}
			if(pvpPlayer.colorUpHelmet == true && i.getType() == Material.LEATHER_HELMET){
				red += 7;
				blue += 7;
				green += 7;
			}
			if(pvpPlayer.colorUpHelmet == false && i.getType() == Material.LEATHER_HELMET){
				red -= 7;
				blue -= 7;
				green -= 7;
			}
			if(pvpPlayer.colorUpChest == true && i.getType() == Material.LEATHER_CHESTPLATE){
				red += 7;
				blue += 7;
				green += 7;
			}
			if(pvpPlayer.colorUpChest == false && i.getType() == Material.LEATHER_CHESTPLATE){
				red -= 7;
				blue -= 7;
				green -= 7;
			}
			if(pvpPlayer.colorUpLeggings == true && i.getType() == Material.LEATHER_LEGGINGS){
				red += 7;
				blue += 7;
				green += 7;
			}
			if(pvpPlayer.colorUpLeggings == false && i.getType() == Material.LEATHER_LEGGINGS){
				red -= 7;
				blue -= 7;
				green -= 7;
			}
			if(pvpPlayer.colorUpBoots == true && i.getType() == Material.LEATHER_BOOTS){
				red += 7;
				blue += 7;
				green += 7;
			}
			if(pvpPlayer.colorUpBoots == false && i.getType() == Material.LEATHER_BOOTS){
				red -= 7;
				blue -= 7;
				green -= 7;
			}
		}
		if(Fade.type(i) == 3){
			if(red >= 204){
				if(i.getType() == Material.LEATHER_HELMET){
					pvpPlayer.colorUpHelmet = false;
				}
				if(i.getType() == Material.LEATHER_CHESTPLATE){
					pvpPlayer.colorUpChest = false;
				}
				if(i.getType() == Material.LEATHER_LEGGINGS){
					pvpPlayer.colorUpLeggings = false;
				}
				if(i.getType() == Material.LEATHER_BOOTS){
					pvpPlayer.colorUpBoots = false;
				}
			}
			if(red <= 111){
				if(i.getType() == Material.LEATHER_HELMET){
					pvpPlayer.colorUpHelmet = true;
				}
				if(i.getType() == Material.LEATHER_CHESTPLATE){
					pvpPlayer.colorUpChest = true;
				}
				if(i.getType() == Material.LEATHER_LEGGINGS){
					pvpPlayer.colorUpLeggings = true;
				}
				if(i.getType() == Material.LEATHER_BOOTS){
					pvpPlayer.colorUpBoots = true;
				}
			}
			if(pvpPlayer.colorUpHelmet == true && i.getType() == Material.LEATHER_HELMET){
				red += 5;
				blue += 3;
				green += 3;
			}
			if(pvpPlayer.colorUpHelmet == false && i.getType() == Material.LEATHER_HELMET){
				red -= 5;
				blue -= 3;
				green -= 3;
			}
			if(pvpPlayer.colorUpChest == true && i.getType() == Material.LEATHER_CHESTPLATE){
				red += 5;
				blue += 3;
				green += 3;
			}
			if(pvpPlayer.colorUpChest == false && i.getType() == Material.LEATHER_CHESTPLATE){
				red -= 5;
				blue -= 3;
				green -= 3;
			}
			if(pvpPlayer.colorUpLeggings == true && i.getType() == Material.LEATHER_LEGGINGS){
				red += 5;
				blue += 3;
				green += 3;
			}
			if(pvpPlayer.colorUpLeggings == false && i.getType() == Material.LEATHER_LEGGINGS){
				red -= 5;
				blue -= 3;
				green -= 3;
			}
			if(pvpPlayer.colorUpBoots == true && i.getType() == Material.LEATHER_BOOTS){
				red += 10;
				blue += 7;
				green += 7;
			}
			if(pvpPlayer.colorUpBoots == false && i.getType() == Material.LEATHER_BOOTS){
				red -= 5;
				blue -= 3;
				green -= 3;
			}
		}
		if(Fade.type(i) == 4){
			if(green >= 240){
				if(i.getType() == Material.LEATHER_HELMET){
					pvpPlayer.colorUpHelmet = false;
				}
				if(i.getType() == Material.LEATHER_CHESTPLATE){
					pvpPlayer.colorUpChest = false;
				}
				if(i.getType() == Material.LEATHER_LEGGINGS){
					pvpPlayer.colorUpLeggings = false;
				}
				if(i.getType() == Material.LEATHER_BOOTS){
					pvpPlayer.colorUpBoots = false;
				}
			}
			if(red <= 15 || blue <= 5){
				if(i.getType() == Material.LEATHER_HELMET){
					pvpPlayer.colorUpHelmet = true;
				}
				if(i.getType() == Material.LEATHER_CHESTPLATE){
					pvpPlayer.colorUpChest = true;
				}
				if(i.getType() == Material.LEATHER_LEGGINGS){
					pvpPlayer.colorUpLeggings = true;
				}
				if(i.getType() == Material.LEATHER_BOOTS){
					pvpPlayer.colorUpBoots = true;
				}
			}
			if(pvpPlayer.colorUpHelmet == true && i.getType() == Material.LEATHER_HELMET){
				red += 6;
				blue += 1;
				green += 7;
			}
			if(pvpPlayer.colorUpHelmet == false && i.getType() == Material.LEATHER_HELMET){
				red -= 6;
				blue -= 1;
				green -= 7;
			}
			if(pvpPlayer.colorUpChest == true && i.getType() == Material.LEATHER_CHESTPLATE){
				red += 6;
				blue += 1;
				green += 7;
			}
			if(pvpPlayer.colorUpChest == false && i.getType() == Material.LEATHER_CHESTPLATE){
				red -= 6;
				blue -= 1;
				green -= 7;
			}
			if(pvpPlayer.colorUpLeggings == true && i.getType() == Material.LEATHER_LEGGINGS){
				red += 6;
				blue += 1;
				green += 7;
			}
			if(pvpPlayer.colorUpLeggings == false && i.getType() == Material.LEATHER_LEGGINGS){
				red -= 6;
				blue -= 1;
				green -= 7;
			}
			if(pvpPlayer.colorUpBoots == true && i.getType() == Material.LEATHER_BOOTS){
				red += 6;
				blue += 1;
				green += 7;
			}
			if(pvpPlayer.colorUpBoots == false && i.getType() == Material.LEATHER_BOOTS){
				red -= 12;
				blue -= 1;
				green -= 15;
			}
		}
		meta.getColor().setBlue(blue);
		meta.getColor().setGreen(green);
		meta.getColor().setRed(red);
		Color color = Color.fromRGB(red, green, blue);
		meta.setColor(color);
		i.setItemMeta(meta);	
	}
	public static void polish(Player player){
		boolean hasCloth = false;
		boolean alreadyRemoved = false;
		boolean correctItemInHand = false;
		ItemStack item = player.getItemInHand();
		if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_LEGGINGS){
			correctItemInHand = true;
		}
		if(player.getInventory().contains(Material.PAPER)){
			for(ItemStack i: player.getInventory()){
					try{
						ItemMeta itemMeta = null;
						if(i != null){
							if(i.hasItemMeta()){
								itemMeta = i.getItemMeta();
							}
							if(i.hasItemMeta() && itemMeta.hasLore() && correctItemInHand == true){
								Bukkit.broadcastMessage("CONFIRM CHECK FOR ITEMMETA");
								if(i.getItemMeta().getLore().get(0).toString().contains(CODE_PAPER)){
									if(i.getAmount() > 1 && alreadyRemoved == false){
										alreadyRemoved = true;
										hasCloth = true;
										i.setAmount(item.getAmount() - 1);
									}
									else if(alreadyRemoved == false){
										alreadyRemoved = true;
										hasCloth = true;
										player.getInventory().removeItem(i);
									}
										
								}
							}
						}
					}
					catch (NullPointerException e1) {
							e1.printStackTrace();
						} 
			}
			if(correctItemInHand == false && hasCloth == true){
				player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You sre not holding epic armor in your hand " + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			}
			if(hasCloth == false){
				player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You did not have a Polishing Cloth please say " + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			}
		}
		if(player.isOp() && hasCloth == false){
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
		if(item.getTypeId() == 298 || item.getTypeId() == 299 || item.getTypeId() == 300 || item.getTypeId() == 301 && hasCloth == true){
			ItemMeta metah = item.getItemMeta();
			LeatherArmorMeta meta = (LeatherArmorMeta) metah;
			if(!meta.getColor().toString().contains("A06540")){
				List<String> lore = new ArrayList<String>();
				lore.add(0, "Polished " + ChatColor.MAGIC + " " + CODE_ARMOR);
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
			else{
				player.sendMessage("THIS IS NORMAL ARMOR DONT TRY TO CHEAT!");
						
			}
		}
		else{
			player.sendMessage(ChatColor.RED + "[Armor Polish]: Sorry you do not have a polishing cloth!!");
		}
	}
}
