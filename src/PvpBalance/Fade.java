package PvpBalance;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Fade
{
	public static final Color CURSED = Color.fromRGB(0, 0, 0);
	public static final Color MITHRIL = Color.fromRGB(0, 0, 128);
	public static final Color FLAME = Color.fromRGB(255, 0, 22);
	public static final Color VERITE = Color.fromRGB(0, 255, 0);
	public static final Color PRIZED = Color.fromRGB(255, 255, 255);
	
	public static int type(ItemStack item)
	{
		ItemMeta itemMeta = item.getItemMeta();
		//LeatherArmorMeta leatherMeta = (LeatherArmorMeta)iMeta;
		if(itemMeta.getDisplayName().contains("Cursed"))
		{
			return 1;
		}
		else if(itemMeta.getDisplayName().contains("Mithril"))
		{
			return 2;
		}
		else if(itemMeta.getDisplayName().contains("Flame"))
		{
			return 3;
		}
		else if(itemMeta.getDisplayName().contains("Verite"))
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
		ItemMeta iMeta = item.getItemMeta();
		LeatherArmorMeta leatherMeta = (LeatherArmorMeta)iMeta;
		if(iMeta.getDisplayName().contains("Cursed"))
		{
				leatherMeta.setColor(CURSED);
				item.setItemMeta(iMeta);
		}
		if(iMeta.getDisplayName().contains("Mithril"))
		{
			leatherMeta.setColor(MITHRIL);
			item.setItemMeta(iMeta);
		}
		if(iMeta.getDisplayName().contains("Flame"))
		{
			leatherMeta.setColor(FLAME);
			item.setItemMeta(iMeta);
		}
		if(iMeta.getDisplayName().contains("Verite"))
		{
			leatherMeta.setColor(VERITE);
			item.setItemMeta(iMeta);
		}
	}
}
