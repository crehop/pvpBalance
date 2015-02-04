/*     */ package SaveLoad;
/*     */ 
/*     */ import PvpBalance.PvpBalance;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class LoadSave
/*     */ {
/*     */   PvpBalance plugin;
/*  13 */   public static int Dhelmet = 0; public static int Dchest = 0; public static int Dleggings = 0; public static int Dboots = 0;
/*  14 */   public static int Ihelmet = 0; public static int Ichest = 0; public static int Ileggings = 0; public static int Iboots = 0;
/*  15 */   public static int Ghelmet = 0; public static int Gchest = 0; public static int Gleggings = 0; public static int Gboots = 0;
/*  16 */   public static int Chelmet = 0; public static int Cchest = 0; public static int Cleggings = 0; public static int Cboots = 0;
/*  17 */   public static int Lhelmet = 0; public static int Lchest = 0; public static int Lleggings = 0; public static int Lboots = 0;
/*  18 */   public static int Ehelmet = 0; public static int Echest = 0; public static int Eleggings = 0; public static int Eboots = 0;
/*  19 */   public static int protect = 0; public static int HealPot = 0; public static int Regen = 0;
/*     */ 
/*  23 */   public static int HitCooldown = 0;
/*  24 */   public static int Diamond = 0; public static int Iron = 0; public static int Gold = 0; public static int Stone = 0; public static int Wood = 0; public static int Bow = 0; public static int Normal = 0;
/*  25 */   public static int Sharpness = 0; public static int Multi = 0; public static int Explosion_Mob = 0;
/*  26 */   public static int Firetick = 0; public static int Voide = 0; public static int Contact = 0; public static int Drowning = 0; public static int Poison = 0; public static int Fall = 0; public static int Wither = 0; public static int Explosion = 0; public static int Lightning = 0;
/*     */ 
/*     */   public LoadSave(PvpBalance plugin)
/*     */   {
/*  30 */     this.plugin = plugin;
/*     */ 
/*  32 */     Bow = plugin.getSDamage().getCustomConfig().getInt("Weapon.Bow");
/*  33 */     Diamond = plugin.getSDamage().getCustomConfig().getInt("Weapon.Diamond");
/*  34 */     Iron = plugin.getSDamage().getCustomConfig().getInt("Weapon.Iron");
/*  35 */     Gold = plugin.getSDamage().getCustomConfig().getInt("Weapon.Gold");
/*  36 */     Stone = plugin.getSDamage().getCustomConfig().getInt("Weapon.Stone");
/*  37 */     Wood = plugin.getSDamage().getCustomConfig().getInt("Weapon.Wood");
/*  38 */     Normal = plugin.getSDamage().getCustomConfig().getInt("Weapon.Normal");
/*  39 */     HitCooldown = plugin.getSDamage().getCustomConfig().getInt("Weapon.HitCooldown");
/*     */ 
/*  42 */     Sharpness = plugin.getSDamage().getCustomConfig().getInt("Enchant.Sharpness");
/*  43 */     Multi = plugin.getSDamage().getCustomConfig().getInt("Mobs.Multiplier");
/*  44 */     Explosion_Mob = plugin.getSDamage().getCustomConfig().getInt("Mobs.ExplosionMob");
/*     */ 
/*  46 */     HealPot = plugin.getSDamage().getCustomConfig().getInt("Natural.HealPot");
/*  47 */     Regen = plugin.getSDamage().getCustomConfig().getInt("Natural.Regen");
/*  48 */     Firetick = plugin.getSDamage().getCustomConfig().getInt("Natural.FireTick");
/*  49 */     Voide = plugin.getSDamage().getCustomConfig().getInt("Natural.Void");
/*  50 */     Contact = plugin.getSDamage().getCustomConfig().getInt("Natural.Contact");
/*  51 */     Drowning = plugin.getSDamage().getCustomConfig().getInt("Natural.Drowning");
/*  52 */     Poison = plugin.getSDamage().getCustomConfig().getInt("Natural.Poison");
/*  53 */     Fall = plugin.getSDamage().getCustomConfig().getInt("Natural.Fall");
/*  54 */     Wither = plugin.getSDamage().getCustomConfig().getInt("Natural.Wither");
/*  55 */     Explosion = plugin.getSDamage().getCustomConfig().getInt("Natural.Explosion");
/*  56 */     Lightning = plugin.getSDamage().getCustomConfig().getInt("Natural.Lightning");
/*     */ 
/*  59 */     Dhelmet = plugin.getProtection().getCustomConfig().getInt("Diamond.Helmet");
/*  60 */     Dchest = plugin.getProtection().getCustomConfig().getInt("Diamond.Chest");
/*  61 */     Dleggings = plugin.getProtection().getCustomConfig().getInt("Diamond.Leggings");
/*  62 */     Dboots = plugin.getProtection().getCustomConfig().getInt("Diamond.Boots");
/*     */ 
/*  64 */     Ihelmet = plugin.getProtection().getCustomConfig().getInt("Iron.Helmet");
/*  65 */     Ichest = plugin.getProtection().getCustomConfig().getInt("Iron.Chest");
/*  66 */     Ileggings = plugin.getProtection().getCustomConfig().getInt("Iron.Leggings");
/*  67 */     Iboots = plugin.getProtection().getCustomConfig().getInt("Iron.Boots");
/*     */ 
/*  69 */     Ghelmet = plugin.getProtection().getCustomConfig().getInt("Gold.Helmet");
/*  70 */     Gchest = plugin.getProtection().getCustomConfig().getInt("Gold.Chest");
/*  71 */     Gleggings = plugin.getProtection().getCustomConfig().getInt("Gold.Leggings");
/*  72 */     Gboots = plugin.getProtection().getCustomConfig().getInt("Gold.Boots");
/*     */ 
/*  74 */     Chelmet = plugin.getProtection().getCustomConfig().getInt("Chain.Helmet");
/*  75 */     Cchest = plugin.getProtection().getCustomConfig().getInt("Chain.Chest");
/*  76 */     Cleggings = plugin.getProtection().getCustomConfig().getInt("Chain.Leggings");
/*  77 */     Cboots = plugin.getProtection().getCustomConfig().getInt("Chain.Boots");
/*     */ 
/*  79 */     Lhelmet = plugin.getProtection().getCustomConfig().getInt("Leather.Helmet");
/*  80 */     Lchest = plugin.getProtection().getCustomConfig().getInt("Leather.Chest");
/*  81 */     Lleggings = plugin.getProtection().getCustomConfig().getInt("Leather.Leggings");
/*  82 */     Lboots = plugin.getProtection().getCustomConfig().getInt("Leather.Boots");
/*     */ 
/*  84 */     Ehelmet = plugin.getProtection().getCustomConfig().getInt("Epic.Helmet");
/*  85 */     Echest = plugin.getProtection().getCustomConfig().getInt("Epic.Chest");
/*  86 */     Eleggings = plugin.getProtection().getCustomConfig().getInt("Epic.Leggings");
/*  87 */     Eboots = plugin.getProtection().getCustomConfig().getInt("Epic.Boots");
/*     */ 
/*  89 */     protect = plugin.getProtection().getCustomConfig().getInt("Enchant.Protection");
/*     */   }
/*     */ 
/*     */   public static void reloadValues(PvpBalance plugin, Player player)
/*     */   {
/*  94 */     Bow = plugin.getSDamage().getCustomConfig().getInt("Weapon.Bow");
/*  95 */     player.sendMessage(ChatColor.DARK_GREEN + "Bow Base: " + ChatColor.RED + Bow);
/*  96 */     Diamond = plugin.getSDamage().getCustomConfig().getInt("Weapon.Diamond");
/*  97 */     player.sendMessage(ChatColor.DARK_GREEN + "Diamond Sword Base: " + ChatColor.RED + Diamond);
/*  98 */     Iron = plugin.getSDamage().getCustomConfig().getInt("Weapon.Iron");
/*  99 */     player.sendMessage(ChatColor.DARK_GREEN + "Iron Sword Base: " + ChatColor.RED + Iron);
/* 100 */     Gold = plugin.getSDamage().getCustomConfig().getInt("Weapon.Gold");
/* 101 */     player.sendMessage(ChatColor.DARK_GREEN + "Gold Sword Base: " + ChatColor.RED + Gold);
/* 102 */     Stone = plugin.getSDamage().getCustomConfig().getInt("Weapon.Stone");
/* 103 */     player.sendMessage(ChatColor.DARK_GREEN + "Stone Sword Base: " + ChatColor.RED + Stone);
/* 104 */     Wood = plugin.getSDamage().getCustomConfig().getInt("Weapon.Wood");
/* 105 */     player.sendMessage(ChatColor.DARK_GREEN + "Wooden Sword Base: " + ChatColor.RED + Wood);
/* 106 */     Normal = plugin.getSDamage().getCustomConfig().getInt("Weapon.Normal");
/* 107 */     player.sendMessage(ChatColor.DARK_GREEN + "Punch Base: " + ChatColor.RED + Normal);
/* 108 */     HitCooldown = plugin.getSDamage().getCustomConfig().getInt("Weapon.HitCooldown");
/*     */ 
/* 111 */     Sharpness = plugin.getSDamage().getCustomConfig().getInt("Enchant.Sharpness");
/* 112 */     player.sendMessage(ChatColor.DARK_GREEN + "Sharpness Modifier: " + ChatColor.RED + Sharpness);
/* 113 */     Multi = plugin.getSDamage().getCustomConfig().getInt("Mobs.Multiplier");
/* 114 */     player.sendMessage(ChatColor.DARK_GREEN + "Mob Damage Multiplier: " + ChatColor.RED + Multi);
/* 115 */     Explosion_Mob = plugin.getSDamage().getCustomConfig().getInt("Mobs.ExplosionMob");
/* 116 */     player.sendMessage(ChatColor.DARK_GREEN + "Creeper Explosion: " + ChatColor.RED + Explosion_Mob);
/*     */ 
/* 118 */     HealPot = plugin.getSDamage().getCustomConfig().getInt("Natural.HealPot");
/* 119 */     player.sendMessage(ChatColor.DARK_GREEN + "Health Potion Heal: " + ChatColor.GREEN + HealPot);
/* 120 */     Regen = plugin.getSDamage().getCustomConfig().getInt("Natural.Regen");
/* 121 */     player.sendMessage(ChatColor.DARK_GREEN + "Health Potion Heal: " + ChatColor.GREEN + Regen);
/* 122 */     Firetick = plugin.getSDamage().getCustomConfig().getInt("Natural.FireTick");
/* 123 */     player.sendMessage(ChatColor.DARK_GREEN + "Fire Tick: " + ChatColor.RED + Firetick);
/* 124 */     Voide = plugin.getSDamage().getCustomConfig().getInt("Natural.Void");
/* 125 */     player.sendMessage(ChatColor.DARK_GREEN + "Void Damage: " + ChatColor.RED + Voide);
/* 126 */     Contact = plugin.getSDamage().getCustomConfig().getInt("Natural.Contact");
/* 127 */     player.sendMessage(ChatColor.DARK_GREEN + "Cacti: " + ChatColor.RED + Contact);
/* 128 */     Drowning = plugin.getSDamage().getCustomConfig().getInt("Natural.Drowning");
/* 129 */     player.sendMessage(ChatColor.DARK_GREEN + "Drowning: " + ChatColor.RED + Drowning);
/*     */ 
/* 131 */     Poison = plugin.getSDamage().getCustomConfig().getInt("Natural.Poison");
/* 132 */     player.sendMessage(ChatColor.DARK_GREEN + "Poison: " + ChatColor.RED + Poison);
/* 133 */     Fall = plugin.getSDamage().getCustomConfig().getInt("Natural.Fall");
/* 134 */     player.sendMessage(ChatColor.DARK_GREEN + "Fall Damage: " + ChatColor.RED + Fall);
/* 135 */     Wither = plugin.getSDamage().getCustomConfig().getInt("Natural.Wither");
/* 136 */     player.sendMessage(ChatColor.DARK_GREEN + "Wither Damage: " + ChatColor.RED + Wither);
/* 137 */     Explosion = plugin.getSDamage().getCustomConfig().getInt("Natural.Explosion");
/* 138 */     player.sendMessage(ChatColor.DARK_GREEN + "Explosion: " + ChatColor.RED + Explosion);
/* 139 */     Lightning = plugin.getSDamage().getCustomConfig().getInt("Natural.Lightning");
/* 140 */     player.sendMessage(ChatColor.DARK_GREEN + "Lightning: " + ChatColor.RED + Lightning);
/*     */ 
/* 144 */     Dhelmet = plugin.getProtection().getCustomConfig().getInt("Diamond.Helmet");
/* 145 */     Dchest = plugin.getProtection().getCustomConfig().getInt("Diamond.Chest");
/* 146 */     Dleggings = plugin.getProtection().getCustomConfig().getInt("Diamond.Leggings");
/* 147 */     Dboots = plugin.getProtection().getCustomConfig().getInt("Diamond.Boots");
/*     */ 
/* 149 */     Ihelmet = plugin.getProtection().getCustomConfig().getInt("Iron.Helmet");
/* 150 */     Ichest = plugin.getProtection().getCustomConfig().getInt("Iron.Chest");
/* 151 */     Ileggings = plugin.getProtection().getCustomConfig().getInt("Iron.Leggings");
/* 152 */     Iboots = plugin.getProtection().getCustomConfig().getInt("Iron.Boots");
/*     */ 
/* 154 */     Ghelmet = plugin.getProtection().getCustomConfig().getInt("Gold.Helmet");
/* 155 */     Gchest = plugin.getProtection().getCustomConfig().getInt("Gold.Chest");
/* 156 */     Gleggings = plugin.getProtection().getCustomConfig().getInt("Gold.Leggings");
/* 157 */     Gboots = plugin.getProtection().getCustomConfig().getInt("Gold.Boots");
/*     */ 
/* 159 */     Chelmet = plugin.getProtection().getCustomConfig().getInt("Chain.Helmet");
/* 160 */     Cchest = plugin.getProtection().getCustomConfig().getInt("Chain.Chest");
/* 161 */     Cleggings = plugin.getProtection().getCustomConfig().getInt("Chain.Leggings");
/* 162 */     Cboots = plugin.getProtection().getCustomConfig().getInt("Chain.Boots");
/*     */ 
/* 164 */     Lhelmet = plugin.getProtection().getCustomConfig().getInt("Leather.Helmet");
/* 165 */     Lchest = plugin.getProtection().getCustomConfig().getInt("Leather.Chest");
/* 166 */     Lleggings = plugin.getProtection().getCustomConfig().getInt("Leather.Leggings");
/* 167 */     Lboots = plugin.getProtection().getCustomConfig().getInt("Leather.Boots");
/*     */ 
/* 169 */     Ehelmet = plugin.getProtection().getCustomConfig().getInt("Epic.Helmet");
/* 170 */     Echest = plugin.getProtection().getCustomConfig().getInt("Epic.Chest");
/* 171 */     Eleggings = plugin.getProtection().getCustomConfig().getInt("Epic.Leggings");
/* 172 */     Eboots = plugin.getProtection().getCustomConfig().getInt("Epic.Boots");
/*     */ 
/* 174 */     protect = plugin.getProtection().getCustomConfig().getInt("Enchant.Protection");
/*     */ 
/* 176 */     int fullDiamond = Dhelmet + Dchest + Dleggings + Dboots;
/* 177 */     int fullEpic = Ehelmet + Echest + Eleggings + Eboots;
/* 178 */     int fullGold = Ghelmet + Gchest + Gleggings + Eboots;
/* 179 */     int fullIron = Ihelmet + Ichest + Ileggings + Iboots;
/* 180 */     int prot1 = protect * 4;
/* 181 */     int prot2 = protect * 8;
/* 182 */     int prot3 = protect * 12;
/* 183 */     int prot4 = protect * 16;
/* 184 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Set: " + fullEpic);
/* 185 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Prot1: " + (fullEpic + prot1));
/* 186 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Prot2: " + (fullEpic + prot2));
/* 187 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Prot3: " + (fullEpic + prot3));
/* 188 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Prot4: " + (fullEpic + prot4));
/*     */ 
/* 190 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Set: " + fullDiamond);
/* 191 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Prot1: " + (fullDiamond + prot1));
/* 192 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Prot2: " + (fullDiamond + prot2));
/* 193 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Prot3: " + (fullDiamond + prot3));
/* 194 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Prot4: " + (fullDiamond + prot4));
/*     */ 
/* 196 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Set: " + fullIron);
/* 197 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Prot1: " + (fullIron + prot1));
/* 198 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Prot2: " + (fullIron + prot2));
/* 199 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Prot3: " + (fullIron + prot3));
/* 200 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Prot4: " + (fullIron + prot4));
/*     */ 
/* 202 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Set: " + fullGold);
/* 203 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Prot1: " + (fullGold + prot1));
/* 204 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Prot2: " + (fullGold + prot2));
/* 205 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Prot3: " + (fullGold + prot3));
/* 206 */     player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Prot4: " + (fullGold + prot4));
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     SaveLoad.LoadSave
 * JD-Core Version:    0.6.2
 */