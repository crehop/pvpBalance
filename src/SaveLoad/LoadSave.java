package SaveLoad;

import PvpBalance.PvpBalance;

public class LoadSave
{
	
	PvpBalance plugin;
	
	//Protection
	public final int Dhelmet, Dchest, Dleggings, Dboots;
	public final int Ihelmet, Ichest, Ileggings, Iboots;
	public final int Ghelmet, Gchest, Gleggings, Gboots;
	public final int Chelmet, Cchest, Cleggings, Cboots;
	public final int Lhelmet, Lchest, Lleggings, Lboots;
	public final int Ehelmet, Echest, Eleggings, Eboots;
	public final int protect;
	
	//Damage
	
	public final int Diamond, Iron, Gold, Stone, Wood, Normal;
	public final int Sharpness, Multi;
	public final int Firetick, Voide, Contact, Drowning, Poison, Fall, Wither, Explosion, Lightning;
	
	public LoadSave(PvpBalance plugin)
	{
		this.plugin = plugin;
		
		Diamond = plugin.getSDamage().getCustomConfig().getInt("Weapon.Diamond");
		Iron = plugin.getSDamage().getCustomConfig().getInt("Weapon.Iron");
		Gold = plugin.getSDamage().getCustomConfig().getInt("Weapon.Gold");
		Stone = plugin.getSDamage().getCustomConfig().getInt("Weapon.Stone");
		Wood = plugin.getSDamage().getCustomConfig().getInt("Weapon.Wood");
		Normal = plugin.getSDamage().getCustomConfig().getInt("Weapon.Normal");
		
		Sharpness = plugin.getSDamage().getCustomConfig().getInt("Enchant.Sharpness");
		Multi = plugin.getSDamage().getCustomConfig().getInt("Mobs.Multiplier");
		
		Firetick = plugin.getSDamage().getCustomConfig().getInt("Natural.FireTick");
		Voide = plugin.getSDamage().getCustomConfig().getInt("Natural.Void");
		Contact = plugin.getSDamage().getCustomConfig().getInt("Natural.Contact");
		Drowning = plugin.getSDamage().getCustomConfig().getInt("Natural.Drowning");
		Poison = plugin.getSDamage().getCustomConfig().getInt("Natural.Poison");
		Fall = plugin.getSDamage().getCustomConfig().getInt("Natural.Fall");
		Wither = plugin.getSDamage().getCustomConfig().getInt("Natural.Wither");
		Explosion = plugin.getSDamage().getCustomConfig().getInt("Natural.Explosion");
		Lightning = plugin.getSDamage().getCustomConfig().getInt("Natural.Lightning");
		
		
		Dhelmet = plugin.getProtection().getCustomConfig().getInt("Diamond.Helmet");
		Dchest = plugin.getProtection().getCustomConfig().getInt("Diamond.Chest");
		Dleggings = plugin.getProtection().getCustomConfig().getInt("Diamond.Leggings");
		Dboots = plugin.getProtection().getCustomConfig().getInt("Diamond.Boots");
		
		Ihelmet = plugin.getProtection().getCustomConfig().getInt("Iron.Helmet");
		Ichest = plugin.getProtection().getCustomConfig().getInt("Iron.Chest");
		Ileggings = plugin.getProtection().getCustomConfig().getInt("Iron.Leggings");
		Iboots = plugin.getProtection().getCustomConfig().getInt("Iron.Boots");
		
		Ghelmet = plugin.getProtection().getCustomConfig().getInt("Gold.Helmet");
		Gchest = plugin.getProtection().getCustomConfig().getInt("Gold.Chest");
		Gleggings = plugin.getProtection().getCustomConfig().getInt("Gold.Leggings");
		Gboots  = plugin.getProtection().getCustomConfig().getInt("Gold.Boots");
		
		Chelmet = plugin.getProtection().getCustomConfig().getInt("Chain.Helmet");
		Cchest = plugin.getProtection().getCustomConfig().getInt("Chain.Chest");
		Cleggings = plugin.getProtection().getCustomConfig().getInt("Chain.Leggings");
		Cboots  = plugin.getProtection().getCustomConfig().getInt("Chain.Boots");
		
		Lhelmet = plugin.getProtection().getCustomConfig().getInt("Leather.Helmet");
		Lchest = plugin.getProtection().getCustomConfig().getInt("Leather.Chest");
		Lleggings = plugin.getProtection().getCustomConfig().getInt("Leather.Leggings");
		Lboots  = plugin.getProtection().getCustomConfig().getInt("Leather.Boots");
		
		Ehelmet = plugin.getProtection().getCustomConfig().getInt("Epic.Helmet");
		Echest = plugin.getProtection().getCustomConfig().getInt("Epic.Chest");
		Eleggings = plugin.getProtection().getCustomConfig().getInt("Epic.Leggings");
		Eboots = plugin.getProtection().getCustomConfig().getInt("Epic.Boots");
		
		protect = plugin.getProtection().getCustomConfig().getInt("Enchant.Protection");
	}
}
