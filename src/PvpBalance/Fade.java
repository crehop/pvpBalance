package PvpBalance;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

import Util.ItemUtils;

public class Fade
{
	public static final Color CURSED = Color.fromRGB(0, 0, 0);
	public static final Color MITHRIL = Color.fromRGB(0, 0, 128);
	public static final Color FLAME = Color.fromRGB(255, 0, 22);
	public static final Color VERITE = Color.fromRGB(0, 255, 0);
	public static final Color PRIZED = Color.fromRGB(255, 255, 255);
	public static final Color TEAL = Color.fromRGB(0,128,128);
	public static final Color YELLOW = Color.fromRGB(255,255,0);
	public static final Color INDIGO = Color.fromRGB(153,50,204);
	
	public static int type(ItemStack item)
	{
		if(ItemUtils.getName(item).contains("Cursed"))
		{
			return 1;
		}
		else if(ItemUtils.getName(item).contains("Mithril"))
		{
			return 2;
		}
		else if(ItemUtils.getName(item).contains("Flame"))
		{
			return 3;
		}
		else if(ItemUtils.getName(item).contains("Verite"))
		{
			return 4;
		}
		else if(ItemUtils.getName(item).contains("Oceanic"))
		{
			return 6;
		}
		else if(ItemUtils.getName(item).contains("Nova"))
		{
			return 8;
		}
		else if(ItemUtils.getName(item).contains("Dolphin"))
		{
			return 9;
		}
		else
		{
			return 5;
		} 
		
	}
	
	public static void setBaseColor(ItemStack item)
	{
		if(ItemUtils.getName(item).contains("Cursed"))
		{
			ItemUtils.setColor(item, CURSED);
		}
		else if(ItemUtils.getName(item).contains("Mithril"))
		{
			ItemUtils.setColor(item, MITHRIL);
		}
		else if(ItemUtils.getName(item).contains("Flame"))
		{
			ItemUtils.setColor(item, FLAME);
		}
		else if(ItemUtils.getName(item).contains("Verite"))
		{
			ItemUtils.setColor(item, VERITE);
		}
		else if(ItemUtils.getName(item).contains("Teal"))
		{
			ItemUtils.setColor(item, TEAL);
		}
		else if(ItemUtils.getName(item).contains("Yellow"))
		{
			ItemUtils.setColor(item, YELLOW);
		}
		else if(ItemUtils.getName(item).contains("Indigo"))
		{
			ItemUtils.setColor(item, INDIGO);
		}
	}
}
