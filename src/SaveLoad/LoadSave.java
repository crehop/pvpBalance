package SaveLoad;

import PvpBalance.Main;

public class LoadSave {
	
	Main m;
	
	//Protection
	public int Dhelmet, Dchest, Dleggings, Dboots;
	public int Ihelmet, Ichest, Ileggings, Iboots;
	public int Ghelmet, Gchest, Gleggings, Gboots;
	public int Chelmet, Cchest, Cleggings, Cboots;
	public int Lhelmet, Lchest, Lleggings, Lboots;
	public int Ehelmet, Echest, Eleggings, Eboots;
	public int protect;
	
	//Damage
	
	public int Diamond, Iron, Gold, Stone, Wood, Normal;
	public int Sharpness, Multi;
	public int Firetick, Voide, Contact, Drowning, Poison, Fall, Wither;
	
	public LoadSave(Main m)
	{
		this.m = m;
		LoadProtection();
		LoadDamage();
	}
	
	public void LoadDamage()
	{
		Diamond = m.sDamage.getCustomConfig().getInt("Weapon.Diamond");
		Iron = m.sDamage.getCustomConfig().getInt("Weapon.Iron");
		Gold = m.sDamage.getCustomConfig().getInt("Weapon.Gold");
		Stone = m.sDamage.getCustomConfig().getInt("Weapon.Stone");
		Wood = m.sDamage.getCustomConfig().getInt("Weapon.Wood");
		Normal = m.sDamage.getCustomConfig().getInt("Weapon.Normal");
		
		Sharpness = m.sDamage.getCustomConfig().getInt("Enchant.Sharpness");
		Multi = m.sDamage.getCustomConfig().getInt("Mobs.Multiplier");
		
		Firetick = m.sDamage.getCustomConfig().getInt("Natural.FireTick");
		Voide = m.sDamage.getCustomConfig().getInt("Natural.Void");
		Contact = m.sDamage.getCustomConfig().getInt("Natural.Contact");
		Drowning = m.sDamage.getCustomConfig().getInt("Natural.Drowning");
		Poison = m.sDamage.getCustomConfig().getInt("Natural.Poison");
		Fall = m.sDamage.getCustomConfig().getInt("Natural.Fall");
		Wither = m.sDamage.getCustomConfig().getInt("Natural.Wither");		
	}
	
	public void LoadProtection()
	{
		Dhelmet = m.Protection.getCustomConfig().getInt("Diamond.Helmet");
		Dchest = m.Protection.getCustomConfig().getInt("Diamond.Chest");
		Dleggings = m.Protection.getCustomConfig().getInt("Diamond.Leggings");
		Dboots = m.Protection.getCustomConfig().getInt("Diamond.Boots");
		
		Ihelmet = m.Protection.getCustomConfig().getInt("Iron.Helmet");
		Ichest = m.Protection.getCustomConfig().getInt("Iron.Chest");
		Ileggings = m.Protection.getCustomConfig().getInt("Iron.Leggings");
		Iboots = m.Protection.getCustomConfig().getInt("Iron.Boots");
		
		Ghelmet = m.Protection.getCustomConfig().getInt("Gold.Helmet");
		Gchest = m.Protection.getCustomConfig().getInt("Gold.Chest");
		Gleggings = m.Protection.getCustomConfig().getInt("Gold.Leggings");
		Gboots  = m.Protection.getCustomConfig().getInt("Gold.Boots");
		
		Chelmet = m.Protection.getCustomConfig().getInt("Chain.Helmet");
		Cchest = m.Protection.getCustomConfig().getInt("Chain.Chest");
		Cleggings = m.Protection.getCustomConfig().getInt("Chain.Leggings");
		Cboots  = m.Protection.getCustomConfig().getInt("Chain.Boots");
		
		Lhelmet = m.Protection.getCustomConfig().getInt("Leather.Helmet");
		Lchest = m.Protection.getCustomConfig().getInt("Leather.Chest");
		Lleggings = m.Protection.getCustomConfig().getInt("Leather.Leggings");
		Lboots  = m.Protection.getCustomConfig().getInt("Leather.Boots");
		
		Ehelmet = m.Protection.getCustomConfig().getInt("Epic.Helmet");
		Echest = m.Protection.getCustomConfig().getInt("Epic.Chest");
		Eleggings = m.Protection.getCustomConfig().getInt("Epic.Leggings");
		Eboots = m.Protection.getCustomConfig().getInt("Epic.Boots");
		
		protect = m.Protection.getCustomConfig().getInt("Enchant.Protection");
	}

}
