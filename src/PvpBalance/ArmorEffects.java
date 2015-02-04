/*     */ package PvpBalance;
/*     */ 
/*     */ import Util.ItemUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ 
/*     */ public class ArmorEffects
/*     */ {
/*     */   public static final String CODE_PAPER = "32157";
/*     */   public static final String CODE_ARMOR = "32188";
/*     */ 
/*     */   public static void checkForGlowTick(Player player)
/*     */   {
/*  24 */     if (player == null)
/*  25 */       return;
/*  26 */     LeatherArmorMeta[] metas = new LeatherArmorMeta[5];
/*  27 */     for (ItemStack item : player.getInventory().getArmorContents())
/*     */     {
/*  29 */       if (item != null)
/*     */       {
/*  31 */         if ((item.hasItemMeta()) && ((item.getType() == Material.LEATHER_BOOTS) || (item.getType() == Material.LEATHER_CHESTPLATE) || 
/*  32 */           (item.getType() == Material.LEATHER_HELMET) || (item.getType() == Material.LEATHER_LEGGINGS)))
/*     */         {
/*  34 */           PVPPlayer pvpPlayer = PvpHandler.getPvpPlayer(player);
/*  35 */           if (pvpPlayer == null)
/*  36 */             return;
/*  37 */           LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
/*  38 */           if ((ItemUtils.getLore(item) == null) || (ItemUtils.getLore(item).isEmpty()))
/*  39 */             return;
/*  40 */           if (item.getItemMeta().getLore().toString().contains("32188"))
/*     */           {
/*  42 */             if (!meta.getColor().toString().contains("A06540"))
/*     */             {
/*  45 */               int nr = Fade.type(item) - 1;
/*  46 */               if (metas[nr] != null)
/*     */               {
/*  48 */                 meta.setColor(metas[nr].clone().getColor());
/*  49 */                 item.setItemMeta(meta);
/*     */               }
/*  53 */               else if ((item.getType() == Material.LEATHER_BOOTS) || (item.getType() == Material.LEATHER_CHESTPLATE) || 
/*  54 */                 (item.getType() == Material.LEATHER_HELMET) || (item.getType() == Material.LEATHER_LEGGINGS)) {
/*  55 */                 metas[nr] = glow(item, pvpPlayer);
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*  60 */           else if (item.getItemMeta().getLore().size() > 1)
/*     */           {
/*  62 */             if (item.getItemMeta().getLore().toString().contains("32188"))
/*     */             {
/*  64 */               if (!meta.getColor().toString().contains("A06540"))
/*     */               {
/*  67 */                 int nr1 = Fade.type(item) - 1;
/*  68 */                 if (metas[nr1] != null)
/*     */                 {
/*  70 */                   meta.setColor(metas[nr1].clone().getColor());
/*  71 */                   item.setItemMeta(meta);
/*     */                 }
/*  75 */                 else if ((item.getType() == Material.LEATHER_BOOTS) || (item.getType() == Material.LEATHER_CHESTPLATE) || 
/*  76 */                   (item.getType() == Material.LEATHER_HELMET) || (item.getType() == Material.LEATHER_LEGGINGS)) {
/*  77 */                   metas[nr1] = glow(item, pvpPlayer);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static LeatherArmorMeta glow(ItemStack item, PVPPlayer pvpPlayer)
/*     */   {
/*  89 */     LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
/*  90 */     int blue = meta.getColor().getBlue();
/*  91 */     int red = meta.getColor().getRed();
/*  92 */     int green = meta.getColor().getGreen();
/*  93 */     if (Fade.type(item) == 1)
/*     */     {
/*  95 */       if ((blue >= 90) && (red >= 90) && (green >= 90))
/*     */       {
/*  97 */         pvpPlayer.setColorUp(false);
/*     */       }
/*  99 */       else if ((blue <= 0) && (red <= 0) && (green <= 0))
/*     */       {
/* 101 */         pvpPlayer.setColorUp(true);
/*     */       }
/*     */ 
/* 104 */       if (pvpPlayer.isColorUp())
/*     */       {
/* 106 */         red += 9;
/* 107 */         blue += 9;
/* 108 */         green += 9;
/*     */       }
/*     */       else
/*     */       {
/* 112 */         red -= 9;
/* 113 */         blue -= 9;
/* 114 */         green -= 9;
/*     */       }
/*     */     }
/* 117 */     else if (Fade.type(item) == 2)
/*     */     {
/* 119 */       if ((red >= 0) && (green >= 0) && (blue >= 248))
/*     */       {
/* 121 */         pvpPlayer.setColorUp(false);
/*     */       }
/* 123 */       else if ((red <= 0) && (green <= 0) && (blue <= 128))
/*     */       {
/* 125 */         pvpPlayer.setColorUp(true);
/*     */       }
/* 127 */       if (pvpPlayer.isColorUp())
/*     */       {
/* 129 */         red += 0;
/* 130 */         green += 0;
/* 131 */         blue += 12;
/*     */       }
/*     */       else
/*     */       {
/* 135 */         red += 0;
/* 136 */         green += 0;
/* 137 */         blue -= 12;
/*     */       }
/*     */     }
/* 140 */     else if (Fade.type(item) == 3)
/*     */     {
/* 142 */       if ((red >= 255) && (green >= 0) && (blue >= 0))
/*     */       {
/* 144 */         pvpPlayer.setColorUp(false);
/*     */       }
/* 146 */       else if ((red <= 125) && (green <= 0) && (blue <= 0))
/*     */       {
/* 148 */         pvpPlayer.setColorUp(true);
/*     */       }
/* 150 */       if (pvpPlayer.isColorUp())
/*     */       {
/* 152 */         red += 13;
/* 153 */         blue = 0;
/* 154 */         green = 0;
/*     */       }
/*     */       else
/*     */       {
/* 158 */         red -= 13;
/* 159 */         blue = 0;
/* 160 */         green = 0;
/*     */       }
/*     */     }
/* 163 */     else if (Fade.type(item) == 4)
/*     */     {
/* 165 */       if ((red >= 120) && (green >= 250) && (blue >= 0))
/*     */       {
/* 167 */         pvpPlayer.setColorUp(false);
/*     */       }
/* 169 */       else if ((red <= 0) && (green <= 100) && (blue <= 0))
/*     */       {
/* 171 */         pvpPlayer.setColorUp(true);
/*     */       }
/* 173 */       if (pvpPlayer.isColorUp())
/*     */       {
/* 175 */         red += 12;
/* 176 */         green += 15;
/* 177 */         blue = 0;
/*     */       }
/*     */       else
/*     */       {
/* 181 */         red -= 12;
/* 182 */         green -= 15;
/* 183 */         blue = 0;
/*     */       }
/*     */     }
/* 186 */     else if ((Fade.type(item) == 5) || (item.getItemMeta().getLore().toString().contains("Polished Prized")))
/*     */     {
/* 188 */       if ((item.hasItemMeta()) && (item.getItemMeta().getLore().size() == 1))
/*     */       {
/* 190 */         List lore = new ArrayList();
/* 191 */         lore.add(0, (String)item.getItemMeta().getLore().get(0));
/* 192 */         lore.add(1, "Polished Prized");
/* 193 */         meta.setLore(lore);
/*     */       }
/* 195 */       if ((red == Fade.PRIZED.getRed()) && (blue == Fade.PRIZED.getBlue()) && (green == Fade.PRIZED.getGreen()))
/*     */       {
/* 197 */         red = Fade.CURSED.getRed();
/* 198 */         blue = Fade.CURSED.getBlue();
/* 199 */         green = Fade.CURSED.getGreen();
/*     */       }
/* 201 */       else if ((red == Fade.CURSED.getRed()) && (blue == Fade.CURSED.getBlue()) && (green == Fade.CURSED.getGreen()))
/*     */       {
/* 203 */         red = Fade.FLAME.getRed();
/* 204 */         blue = Fade.FLAME.getBlue();
/* 205 */         green = Fade.FLAME.getGreen();
/*     */       }
/* 207 */       else if ((red == Fade.FLAME.getRed()) && (blue == Fade.FLAME.getBlue()) && (green == Fade.FLAME.getGreen()))
/*     */       {
/* 209 */         red = Fade.MITHRIL.getRed();
/* 210 */         blue = Fade.MITHRIL.getBlue();
/* 211 */         green = Fade.MITHRIL.getGreen();
/*     */       }
/* 213 */       else if ((red == Fade.MITHRIL.getRed()) && (blue == Fade.MITHRIL.getBlue()) && (green == Fade.MITHRIL.getGreen()))
/*     */       {
/* 215 */         red = Fade.VERITE.getRed();
/* 216 */         blue = Fade.VERITE.getBlue();
/* 217 */         green = Fade.VERITE.getGreen();
/*     */       }
/* 219 */       else if ((red == Fade.VERITE.getRed()) && (blue == Fade.VERITE.getBlue()) && (green == Fade.VERITE.getGreen()))
/*     */       {
/* 221 */         red = Fade.PRIZED.getRed();
/* 222 */         blue = Fade.PRIZED.getBlue();
/* 223 */         green = Fade.PRIZED.getGreen();
/*     */       }
/*     */       else
/*     */       {
/* 227 */         red = Fade.PRIZED.getRed();
/* 228 */         blue = Fade.PRIZED.getBlue();
/* 229 */         green = Fade.PRIZED.getGreen();
/*     */       }
/*     */     }
/*     */ 
/* 233 */     if (red < 0)
/* 234 */       red = 0;
/* 235 */     else if (red > 255) {
/* 236 */       red = 255;
/*     */     }
/* 238 */     if (green < 0)
/* 239 */       green = 0;
/* 240 */     else if (green > 255) {
/* 241 */       green = 255;
/*     */     }
/* 243 */     if (blue < 0)
/* 244 */       blue = 0;
/* 245 */     else if (blue > 255) {
/* 246 */       blue = 255;
/*     */     }
/* 248 */     Color color = Color.fromRGB(red, green, blue);
/* 249 */     meta.setColor(color);
/* 250 */     item.setItemMeta(meta);
/* 251 */     return meta.clone();
/*     */   }
/*     */ 
/*     */   public static void polish(Player player) {
/* 255 */     boolean hasCloth = false;
/* 256 */     boolean alreadyRemoved = false;
/* 257 */     boolean correctItemInHand = false;
/* 258 */     ItemStack item = null;
/* 259 */     if (player.getItemInHand() != null)
/*     */     {
/* 261 */       item = player.getItemInHand();
/* 262 */       if ((item.getType() == Material.LEATHER_BOOTS) || (item.getType() == Material.LEATHER_CHESTPLATE) || (item.getType() == Material.LEATHER_HELMET) || (item.getType() == Material.LEATHER_LEGGINGS))
/*     */       {
/* 264 */         correctItemInHand = true;
/*     */       }
/* 266 */       if ((player.getInventory().contains(Material.PAPER)) && (item != null))
/*     */       {
/* 268 */         for (ItemStack i : player.getInventory())
/*     */         {
/*     */           try
/*     */           {
/* 272 */             ItemMeta itemMeta = null;
/* 273 */             if (i != null)
/*     */             {
/* 275 */               if (i.hasItemMeta())
/*     */               {
/* 277 */                 itemMeta = i.getItemMeta();
/*     */               }
/* 279 */               if ((i.hasItemMeta()) && (itemMeta.hasLore()) && (correctItemInHand))
/*     */               {
/* 281 */                 if (((String)i.getItemMeta().getLore().get(0)).toString().contains("32157"))
/*     */                 {
/* 283 */                   if ((i.getAmount() > 1) && (!alreadyRemoved))
/*     */                   {
/* 285 */                     alreadyRemoved = true;
/* 286 */                     hasCloth = true;
/* 287 */                     i.setAmount(i.getAmount() - 1);
/* 288 */                     if ((item.getType() == Material.LEATHER_BOOTS) || (item.getType() == Material.LEATHER_CHESTPLATE) || 
/* 289 */                       (item.getType() == Material.LEATHER_HELMET) || (
/* 289 */                       (item.getType() == Material.LEATHER_LEGGINGS) && (hasCloth)))
/*     */                     {
/* 291 */                       if (((String)i.getItemMeta().getLore().get(0)).toString().contains("32157"))
/*     */                       {
/* 293 */                         ItemMeta metah = item.getItemMeta();
/* 294 */                         LeatherArmorMeta meta = (LeatherArmorMeta)metah;
/* 295 */                         if (!meta.getColor().toString().contains("A06540"))
/*     */                         {
/* 297 */                           List lore = new ArrayList();
/* 298 */                           lore = meta.getLore();
/* 299 */                           if (lore.size() != 0) {
/* 300 */                             lore.add("Polished " + ChatColor.MAGIC + " " + "32188");
/* 301 */                             ItemUtils.setLore(item, lore);
/* 302 */                             if ((meta.getColor().getBlue() == 255) && (meta.getColor().getGreen() == 255) && (meta.getColor().getRed() == 255))
/*     */                             {
/* 304 */                               meta.setColor(Color.fromRGB(254, 255, 255));
/* 305 */                               item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
/*     */                             }
/*     */                           }
/*     */                         }
/*     */                         else
/*     */                         {
/* 311 */                           player.sendMessage("THIS IS NORMAL ARMOR DONT TRY TO CHEAT!");
/*     */                         }
/*     */                       }
/*     */                     }
/*     */                   }
/* 316 */                   else if (!alreadyRemoved)
/*     */                   {
/* 318 */                     alreadyRemoved = true;
/* 319 */                     hasCloth = true;
/* 320 */                     player.getInventory().removeItem(new ItemStack[] { i });
/* 321 */                     if ((item.getType() == Material.LEATHER_BOOTS) || (item.getType() == Material.LEATHER_CHESTPLATE) || 
/* 322 */                       (item.getType() == Material.LEATHER_HELMET) || (
/* 322 */                       (item.getType() == Material.LEATHER_LEGGINGS) && (hasCloth)))
/*     */                     {
/* 324 */                       if (((String)i.getItemMeta().getLore().get(0)).toString().contains("32157"))
/*     */                       {
/* 326 */                         ItemMeta metah = item.getItemMeta();
/* 327 */                         LeatherArmorMeta meta = (LeatherArmorMeta)metah;
/* 328 */                         if (!meta.getColor().toString().contains("A06540"))
/*     */                         {
/* 330 */                           List lore = new ArrayList();
/* 331 */                           lore = meta.getLore();
/* 332 */                           if (lore.size() != 0) {
/* 333 */                             lore.add("Polished " + ChatColor.MAGIC + " " + "32188");
/* 334 */                             ItemUtils.setLore(item, lore);
/* 335 */                             if ((meta.getColor().getBlue() == 255) && (meta.getColor().getGreen() == 255) && (meta.getColor().getRed() == 255))
/*     */                             {
/* 337 */                               meta.setColor(Color.fromRGB(254, 255, 255));
/* 338 */                               item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
/*     */                             }
/*     */                           }
/*     */                         }
/*     */                         else
/*     */                         {
/* 344 */                           player.sendMessage("THIS IS NORMAL ARMOR DONT TRY TO CHEAT!");
/*     */                         }
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           } catch (Exception e1) {
/* 353 */             e1.printStackTrace();
/* 354 */             PvpBalance.logger.info("ArmorEffects!");
/*     */           }
/*     */         }
/*     */       }
/* 358 */       if ((player.isOp()) && (!hasCloth))
/*     */       {
/* 360 */         ItemStack paper = new ItemStack(Material.PAPER);
/* 361 */         Object lore = new ArrayList();
/* 362 */         ((List)lore).add("Polishing Cloth " + ChatColor.MAGIC + " " + "32157");
/* 363 */         ((List)lore).add(ChatColor.GREEN + ChatColor.BOLD + "/rules polish");
/* 364 */         ItemUtils.setLore(paper, (List)lore);
/* 365 */         ItemUtils.setName(paper, ChatColor.GOLD + "Polishing Cloth");
/* 366 */         player.getInventory().addItem(new ItemStack[] { paper });
/* 367 */         player.sendMessage(ChatColor.GREEN + "[Armor Polish]: Greetings Administrator " + player.getDisplayName() + " a cloth has been provided please try again.");
/*     */       }
/* 369 */       if ((!correctItemInHand) && (hasCloth))
/*     */       {
/* 371 */         player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You are not holding epic armor in your hand " + ChatColor.GREEN + ChatColor.BOLD + "/rules polish");
/* 372 */         ItemUtils.setColor(item, Color.fromRGB(254, 255, 255));
/* 373 */         item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
/*     */       }
/* 375 */       if (!hasCloth)
/*     */       {
/* 377 */         player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You did not have a Polishing Cloth or are not holding the right item please say " + ChatColor.GREEN + ChatColor.BOLD + "/rules polish");
/*     */       }
/* 379 */       if ((!correctItemInHand) && (hasCloth) && (alreadyRemoved))
/*     */       {
/* 381 */         player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You polish the armor to a brilliant shine " + ChatColor.GOLD + "Bling Bling! ");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.ArmorEffects
 * JD-Core Version:    0.6.2
 */