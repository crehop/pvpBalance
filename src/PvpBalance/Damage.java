/*     */ package PvpBalance;
/*     */ 
/*     */ import Party.Party;
/*     */ import SaveLoad.LoadSave;
/*     */ import com.palmergames.bukkit.towny.utils.CombatUtil;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ 
/*     */ public class Damage
/*     */ {
/*     */   public static LoadSave LoadSave;
/*     */ 
/*     */   public static int calcDamage(Player player)
/*     */   {
/*  28 */     int damage = 25;
/*  29 */     if ((player.getItemInHand().getType() == Material.DIAMOND_SWORD) || (player.getItemInHand().getType() == Material.DIAMOND_AXE) || (player.getItemInHand().getType() == Material.DIAMOND_HOE))
/*     */     {
/*  31 */       damage += LoadSave.Diamond;
/*     */     }
/*  33 */     else if ((player.getItemInHand().getType() == Material.IRON_SWORD) || (player.getItemInHand().getType() == Material.IRON_AXE) || (player.getItemInHand().getType() == Material.IRON_HOE))
/*     */     {
/*  35 */       damage += LoadSave.Iron;
/*     */     }
/*  37 */     else if ((player.getItemInHand().getType() == Material.GOLD_SWORD) || (player.getItemInHand().getType() == Material.GOLD_AXE) || (player.getItemInHand().getType() == Material.GOLD_HOE))
/*     */     {
/*  39 */       damage += LoadSave.Gold;
/*     */     }
/*  41 */     else if ((player.getItemInHand().getType() == Material.STONE_SWORD) || (player.getItemInHand().getType() == Material.STONE_AXE) || (player.getItemInHand().getType() == Material.STONE_HOE))
/*     */     {
/*  43 */       damage += LoadSave.Stone;
/*     */     }
/*  45 */     else if ((player.getItemInHand().getType() == Material.WOOD_SWORD) || (player.getItemInHand().getType() == Material.WOOD_AXE) || (player.getItemInHand().getType() == Material.WOOD_HOE))
/*     */     {
/*  47 */       damage += LoadSave.Wood;
/*     */     }
/*  49 */     else if (player.getItemInHand().getType() == Material.BOW)
/*     */     {
/*  51 */       damage += LoadSave.Bow;
/*  52 */       damage += player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE) * (LoadSave.Sharpness * 2);
/*     */     }
/*  54 */     damage += player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) * LoadSave.Sharpness;
/*  55 */     if (player.getActivePotionEffects().toString().contains("WEAK")) {
/*  56 */       damage = damage / 4 * 3;
/*     */     }
/*  58 */     if (PvpHandler.getPvpPlayer(player).isNewbZone()) {
/*  59 */       if (damage > 25 + LoadSave.Diamond + LoadSave.Sharpness * 2) {
/*  60 */         damage = 25 + LoadSave.Diamond + LoadSave.Sharpness * 2;
/*  61 */         player.sendMessage(ChatColor.RED + "Your sword is too powerful for this area! damage reduced!!");
/*     */       }
/*  63 */       boolean check = false;
/*  64 */       for (ItemStack i : player.getInventory().getArmorContents())
/*     */       {
/*  66 */         if (i.getType() == Material.LEATHER_HELMET) {
/*  67 */           check = true;
/*     */         }
/*  69 */         else if (i.getType() == Material.LEATHER_CHESTPLATE) {
/*  70 */           check = true;
/*     */         }
/*  72 */         else if (i.getType() == Material.LEATHER_LEGGINGS) {
/*  73 */           check = true;
/*     */         }
/*  75 */         else if (i.getType() == Material.LEATHER_BOOTS) {
/*  76 */           check = true;
/*     */         }
/*     */       }
/*  79 */       if (check) {
/*  80 */         player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "NEWBIE AREA! YOU CANNOT WEAR EPIC IN THIS AREA DAMAGE REDUCED TO 0!! REMOVE YOUR EPIC/LEATHER ARMOR");
/*  81 */         damage = 25;
/*     */       }
/*     */     }
/*  84 */     return damage;
/*     */   }
/*     */ 
/*     */   public static int calcArmor(Player player)
/*     */   {
/*  91 */     PVPPlayer pvpPlayer = PvpHandler.getPvpPlayer(player);
/*  92 */     int armor = 500;
/*  93 */     for (ItemStack i : player.getInventory().getArmorContents())
/*     */     {
/*  95 */       int check = 0;
/*  96 */       if (i.getType() == Material.DIAMOND_HELMET) {
/*  97 */         check = 310;
/*     */       }
/*  99 */       else if (i.getType() == Material.DIAMOND_CHESTPLATE) {
/* 100 */         check = 311;
/*     */       }
/* 102 */       else if (i.getType() == Material.DIAMOND_LEGGINGS) {
/* 103 */         check = 312;
/*     */       }
/* 105 */       else if (i.getType() == Material.DIAMOND_BOOTS) {
/* 106 */         check = 313;
/*     */       }
/* 108 */       else if (i.getType() == Material.GOLD_HELMET) {
/* 109 */         check = 314;
/*     */       }
/* 111 */       else if (i.getType() == Material.GOLD_CHESTPLATE) {
/* 112 */         check = 315;
/*     */       }
/* 114 */       else if (i.getType() == Material.GOLD_LEGGINGS) {
/* 115 */         check = 316;
/*     */       }
/* 117 */       else if (i.getType() == Material.GOLD_BOOTS) {
/* 118 */         check = 317;
/*     */       }
/* 120 */       else if (i.getType() == Material.IRON_HELMET) {
/* 121 */         check = 306;
/*     */       }
/* 123 */       else if (i.getType() == Material.IRON_CHESTPLATE) {
/* 124 */         check = 307;
/*     */       }
/* 126 */       else if (i.getType() == Material.IRON_LEGGINGS) {
/* 127 */         check = 308;
/*     */       }
/* 129 */       else if (i.getType() == Material.IRON_BOOTS) {
/* 130 */         check = 309;
/*     */       }
/* 132 */       else if (i.getType() == Material.CHAINMAIL_HELMET) {
/* 133 */         check = 302;
/*     */       }
/* 135 */       else if (i.getType() == Material.CHAINMAIL_CHESTPLATE) {
/* 136 */         check = 303;
/*     */       }
/* 138 */       else if (i.getType() == Material.CHAINMAIL_LEGGINGS) {
/* 139 */         check = 304;
/*     */       }
/* 141 */       else if (i.getType() == Material.CHAINMAIL_BOOTS) {
/* 142 */         check = 305;
/*     */       }
/* 144 */       else if (i.getType() == Material.LEATHER_HELMET) {
/* 145 */         check = 298;
/*     */       }
/* 147 */       else if (i.getType() == Material.LEATHER_CHESTPLATE) {
/* 148 */         check = 299;
/*     */       }
/* 150 */       else if (i.getType() == Material.LEATHER_LEGGINGS) {
/* 151 */         check = 300;
/*     */       }
/* 153 */       else if (i.getType() == Material.LEATHER_BOOTS) {
/* 154 */         check = 301;
/*     */       }
/* 156 */       int protection = i.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) * LoadSave.protect;
/* 157 */       armor += protection;
/* 158 */       switch (check)
/*     */       {
/*     */       case 310:
/* 163 */         armor += LoadSave.Dhelmet;
/* 164 */         break;
/*     */       case 311:
/* 168 */         armor += LoadSave.Dchest;
/* 169 */         break;
/*     */       case 312:
/* 173 */         armor += LoadSave.Dleggings;
/* 174 */         break;
/*     */       case 313:
/* 178 */         armor += LoadSave.Dboots;
/* 179 */         break;
/*     */       case 314:
/* 183 */         armor += LoadSave.Ghelmet;
/* 184 */         break;
/*     */       case 315:
/* 188 */         armor += LoadSave.Gchest;
/* 189 */         break;
/*     */       case 316:
/* 193 */         armor += LoadSave.Gleggings;
/* 194 */         break;
/*     */       case 317:
/* 198 */         armor += LoadSave.Gboots;
/* 199 */         break;
/*     */       case 306:
/* 204 */         armor += LoadSave.Ihelmet;
/* 205 */         break;
/*     */       case 307:
/* 209 */         armor += LoadSave.Ichest;
/* 210 */         break;
/*     */       case 308:
/* 214 */         armor += LoadSave.Ileggings;
/* 215 */         break;
/*     */       case 309:
/* 219 */         armor += LoadSave.Iboots;
/* 220 */         break;
/*     */       case 302:
/* 225 */         armor += LoadSave.Chelmet;
/* 226 */         break;
/*     */       case 303:
/* 230 */         armor += LoadSave.Cchest;
/* 231 */         break;
/*     */       case 304:
/* 235 */         armor += LoadSave.Cleggings;
/* 236 */         break;
/*     */       case 305:
/* 239 */         armor += LoadSave.Cboots;
/* 240 */         break;
/*     */       case 298:
/* 245 */         ItemStack helmet = player.getInventory().getHelmet();
/* 246 */         if ((helmet != null) && (helmet.hasItemMeta()) && (((String)helmet.getItemMeta().getLore().get(0)).toString().contains("32188")))
/*     */         {
/* 248 */           Fade.setBaseColor(helmet);
/* 249 */           armor += 75;
/*     */         }
/* 251 */         if ((helmet != null) && (helmet.hasItemMeta()))
/*     */         {
/* 253 */           ItemMeta metah = helmet.getItemMeta();
/* 254 */           LeatherArmorMeta meta = (LeatherArmorMeta)metah;
/* 255 */           if (meta.getColor().toString().contains("A06540"))
/*     */           {
/* 257 */             armor += LoadSave.Lhelmet;
/*     */           }
/*     */           else
/*     */           {
/* 261 */             armor += LoadSave.Ehelmet;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 266 */           armor += LoadSave.Lhelmet;
/*     */         }
/*     */ 
/* 269 */         break;
/*     */       case 299:
/* 273 */         ItemStack chestplate = player.getInventory().getChestplate();
/* 274 */         if ((chestplate != null) && (chestplate.hasItemMeta()) && (((String)chestplate.getItemMeta().getLore().get(0)).toString().contains("32188")))
/*     */         {
/* 276 */           Fade.setBaseColor(chestplate);
/* 277 */           armor += 75;
/*     */         }
/* 279 */         if ((chestplate != null) && (chestplate.hasItemMeta()))
/*     */         {
/* 281 */           ItemMeta metah2 = chestplate.getItemMeta();
/* 282 */           LeatherArmorMeta meta2 = (LeatherArmorMeta)metah2;
/* 283 */           if (meta2.getColor().toString().contains("A06540"))
/*     */           {
/* 285 */             armor += LoadSave.Lchest;
/*     */           }
/*     */           else
/*     */           {
/* 289 */             armor += LoadSave.Echest;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 294 */           armor += LoadSave.Lchest;
/*     */         }
/* 296 */         break;
/*     */       case 300:
/* 300 */         ItemStack leggings = player.getInventory().getLeggings();
/* 301 */         if ((leggings != null) && (leggings.hasItemMeta()) && (((String)leggings.getItemMeta().getLore().get(0)).toString().contains("32188")))
/*     */         {
/* 303 */           Fade.setBaseColor(leggings);
/* 304 */           armor += 75;
/*     */         }
/* 306 */         if ((leggings != null) && (leggings.hasItemMeta()))
/*     */         {
/* 308 */           ItemMeta metah3 = leggings.getItemMeta();
/* 309 */           LeatherArmorMeta meta3 = (LeatherArmorMeta)metah3;
/* 310 */           if (meta3.getColor().toString().contains("A06540"))
/*     */           {
/* 312 */             armor += LoadSave.Lleggings;
/*     */           }
/*     */           else
/*     */           {
/* 316 */             armor += LoadSave.Eleggings;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 321 */           armor += LoadSave.Lleggings;
/*     */         }
/* 323 */         break;
/*     */       case 301:
/* 327 */         ItemStack boots = player.getInventory().getBoots();
/* 328 */         if ((boots != null) && (boots.hasItemMeta()) && (((String)boots.getItemMeta().getLore().get(0)).toString().contains("32188")))
/*     */         {
/* 330 */           Fade.setBaseColor(boots);
/* 331 */           armor += 75;
/*     */         }
/* 333 */         if ((boots != null) && (boots.hasItemMeta()))
/*     */         {
/* 335 */           ItemMeta metah4 = boots.getItemMeta();
/* 336 */           LeatherArmorMeta meta4 = (LeatherArmorMeta)metah4;
/* 337 */           if (meta4.getColor().toString().contains("A06540"))
/*     */           {
/* 339 */             armor += LoadSave.Lboots;
/*     */           }
/*     */           else
/*     */           {
/* 343 */             armor += LoadSave.Eboots;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 348 */           armor += LoadSave.Lboots;
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/* 353 */     pvpPlayer.setMaxHealth(armor);
/* 354 */     return armor;
/*     */   }
/*     */ 
/*     */   public static boolean canHit(Entity damagee, Entity damager)
/*     */   {
/* 359 */     if (CombatUtil.preventDamageCall(PvpBalance.getTowny(), damager, damagee))
/* 360 */       return false;
/* 361 */     if (!partyCanHit(damagee, damager))
/* 362 */       return false;
/* 363 */     EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.CUSTOM, 1.0D);
/* 364 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean partyCanHit(Entity eDamagee, Entity eDamager)
/*     */   {
/* 370 */     if (!(eDamagee instanceof Player))
/* 371 */       return true;
/* 372 */     if ((eDamager instanceof Player))
/*     */     {
/* 374 */       Player player = (Player)eDamagee;
/* 375 */       Player damager = (Player)eDamager;
/* 376 */       PVPPlayer dpDamager = PvpHandler.getPvpPlayer(damager);
/* 377 */       if (dpDamager.isInParty())
/*     */       {
/* 379 */         PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/* 380 */         if (dpDamager.getParty().isMember(dp))
/*     */         {
/* 382 */           return false;
/*     */         }
/* 384 */         if (dp.justLeft())
/*     */         {
/* 386 */           if (dp.getGhostParty().isMember(dpDamager))
/*     */           {
/* 388 */             return false;
/*     */           }
/*     */         }
/*     */       }
/* 392 */       else if (dpDamager.justLeft())
/*     */       {
/* 394 */         PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/* 395 */         if (dpDamager.getGhostParty().isMember(dp))
/*     */         {
/* 397 */           return false;
/*     */         }
/* 399 */         if (dp.justLeft())
/*     */         {
/* 401 */           if (dpDamager.getGhostParty().equals(dp.getGhostParty()))
/*     */           {
/* 403 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 408 */     else if ((eDamager instanceof Arrow))
/*     */     {
/* 410 */       Arrow a = (Arrow)eDamager;
/* 411 */       if ((a.getShooter() instanceof Player))
/*     */       {
/* 413 */         Player player = (Player)eDamagee;
/* 414 */         Player damager = (Player)a.getShooter();
/* 415 */         PVPPlayer dpDamager = PvpHandler.getPvpPlayer(damager);
/* 416 */         if (dpDamager.isInParty())
/*     */         {
/* 418 */           PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/* 419 */           if (dpDamager.getParty().isMember(dp))
/*     */           {
/* 421 */             a.remove();
/* 422 */             return false;
/*     */           }
/* 424 */           if (dp.justLeft())
/*     */           {
/* 426 */             if (dp.getGhostParty().isMember(dpDamager))
/*     */             {
/* 428 */               a.remove();
/* 429 */               return false;
/*     */             }
/*     */           }
/*     */         }
/* 433 */         else if (dpDamager.justLeft())
/*     */         {
/* 435 */           PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/* 436 */           if (dpDamager.getGhostParty().isMember(dp))
/*     */           {
/* 438 */             a.remove();
/* 439 */             return false;
/*     */           }
/* 441 */           if (dp.justLeft())
/*     */           {
/* 443 */             if (dpDamager.getGhostParty().equals(dp.getGhostParty()))
/*     */             {
/* 445 */               a.remove();
/* 446 */               return false;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 452 */     return true;
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.Damage
 * JD-Core Version:    0.6.2
 */