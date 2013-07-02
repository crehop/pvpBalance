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
	}
}
