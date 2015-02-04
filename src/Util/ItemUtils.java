/*     */ package Util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ 
/*     */ public class ItemUtils
/*     */ {
/*     */   public static ItemStack setName(ItemStack item, String name)
/*     */   {
/*  17 */     ItemMeta im = item.getItemMeta();
/*  18 */     im.setDisplayName(addChatColor(name));
/*  19 */     item.setItemMeta(im);
/*  20 */     return item;
/*     */   }
/*     */ 
/*     */   public static String getName(ItemStack item)
/*     */   {
/*  25 */     return item.getItemMeta().getDisplayName();
/*     */   }
/*     */ 
/*     */   public static ItemStack setLore(ItemStack item, List<String> lore)
/*     */   {
/*  30 */     ItemMeta im = item.getItemMeta();
/*  31 */     im.setLore(addChatColor(lore));
/*  32 */     item.setItemMeta(im);
/*     */ 
/*  34 */     return item;
/*     */   }
/*     */ 
/*     */   public static ItemStack addLore(ItemStack item, String lore)
/*     */   {
/*  39 */     ItemMeta im = item.getItemMeta();
/*  40 */     List temp = im.getLore();
/*  41 */     if (temp == null)
/*  42 */       temp = new ArrayList();
/*  43 */     temp.add(addChatColor(lore));
/*  44 */     im.setLore(temp);
/*  45 */     item.setItemMeta(im);
/*     */ 
/*  47 */     return item;
/*     */   }
/*     */ 
/*     */   public static ItemStack addLore(ItemStack item, List<String> lore)
/*     */   {
/*  52 */     ItemMeta im = item.getItemMeta();
/*  53 */     List temp = im.getLore();
/*  54 */     Iterator itr = lore.iterator();
/*  55 */     while (itr.hasNext())
/*     */     {
/*  57 */       temp.add(addChatColor((String)itr.next()));
/*     */     }
/*  59 */     im.setLore(temp);
/*  60 */     item.setItemMeta(im);
/*     */ 
/*  62 */     return item;
/*     */   }
/*     */ 
/*     */   public static List<String> getLore(ItemStack item)
/*     */   {
/*  67 */     return item.getItemMeta().getLore();
/*     */   }
/*     */ 
/*     */   public static String[] getLoreAsArray(ItemStack item)
/*     */   {
/*  72 */     if (item.getItemMeta().getLore() == null)
/*  73 */       return new String[0];
/*  74 */     String[] temp = new String[item.getItemMeta().getLore().size()];
/*  75 */     int i = 0;
/*  76 */     for (String s : item.getItemMeta().getLore())
/*     */     {
/*  78 */       temp[i] = s;
/*  79 */       i++;
/*     */     }
/*  81 */     return temp;
/*     */   }
/*     */ 
/*     */   public static ChatColor getChatColor(String s)
/*     */   {
/*  86 */     if (s.contains("&0"))
/*     */     {
/*  88 */       s = s.replace("&0", "");
/*  89 */       return ChatColor.BLACK;
/*     */     }
/*  91 */     if (s.contains("&1"))
/*     */     {
/*  93 */       s = s.replace("&1", "");
/*  94 */       return ChatColor.DARK_BLUE;
/*     */     }
/*  96 */     if (s.contains("&2"))
/*     */     {
/*  98 */       s = s.replace("&2", "");
/*  99 */       return ChatColor.DARK_GREEN;
/*     */     }
/* 101 */     if (s.contains("&3"))
/*     */     {
/* 103 */       s = s.replace("&3", "");
/* 104 */       return ChatColor.DARK_AQUA;
/*     */     }
/* 106 */     if (s.contains("&4"))
/*     */     {
/* 108 */       s = s.replace("&4", "");
/* 109 */       return ChatColor.DARK_RED;
/*     */     }
/* 111 */     if (s.contains("&5"))
/*     */     {
/* 113 */       s = s.replace("&5", "");
/* 114 */       return ChatColor.DARK_PURPLE;
/*     */     }
/* 116 */     if (s.contains("&6"))
/*     */     {
/* 118 */       s = s.replace("&6", "");
/* 119 */       return ChatColor.GOLD;
/*     */     }
/* 121 */     if (s.contains("&7"))
/*     */     {
/* 123 */       s = s.replace("&7", "");
/* 124 */       return ChatColor.GRAY;
/*     */     }
/* 126 */     if (s.contains("&8"))
/*     */     {
/* 128 */       s = s.replace("&8", "");
/* 129 */       return ChatColor.DARK_GRAY;
/*     */     }
/* 131 */     if (s.contains("&9"))
/*     */     {
/* 133 */       s = s.replace("&9", "");
/* 134 */       return ChatColor.BLUE;
/*     */     }
/* 136 */     if (s.contains("&a"))
/*     */     {
/* 138 */       s = s.replace("&a", "");
/* 139 */       return ChatColor.GREEN;
/*     */     }
/* 141 */     if (s.contains("&b"))
/*     */     {
/* 143 */       s = s.replace("&b", "");
/* 144 */       return ChatColor.AQUA;
/*     */     }
/* 146 */     if (s.contains("&c"))
/*     */     {
/* 148 */       s = s.replace("&c", "");
/* 149 */       return ChatColor.RED;
/*     */     }
/* 151 */     if (s.contains("&d"))
/*     */     {
/* 153 */       s = s.replace("&d", "");
/* 154 */       return ChatColor.LIGHT_PURPLE;
/*     */     }
/* 156 */     if (s.contains("&e"))
/*     */     {
/* 158 */       s = s.replace("&e", "");
/* 159 */       return ChatColor.YELLOW;
/*     */     }
/* 161 */     if (s.contains("&f"))
/*     */     {
/* 163 */       s = s.replace("&f", "");
/* 164 */       return ChatColor.WHITE;
/*     */     }
/* 166 */     return ChatColor.RESET;
/*     */   }
/*     */ 
/*     */   public static String removeTag(String s)
/*     */   {
/* 171 */     if (s.contains("&0"))
/*     */     {
/* 173 */       s = s.replace("&0", "");
/*     */     }
/*     */ 
/* 176 */     if (s.contains("&1"))
/*     */     {
/* 178 */       s = s.replace("&1", "");
/*     */     }
/*     */ 
/* 181 */     if (s.contains("&2"))
/*     */     {
/* 183 */       s = s.replace("&2", "");
/*     */     }
/*     */ 
/* 186 */     if (s.contains("&3"))
/*     */     {
/* 188 */       s = s.replace("&3", "");
/*     */     }
/*     */ 
/* 191 */     if (s.contains("&4"))
/*     */     {
/* 193 */       s = s.replace("&4", "");
/*     */     }
/*     */ 
/* 196 */     if (s.contains("&5"))
/*     */     {
/* 198 */       s = s.replace("&5", "");
/*     */     }
/*     */ 
/* 201 */     if (s.contains("&6"))
/*     */     {
/* 203 */       s = s.replace("&6", "");
/*     */     }
/*     */ 
/* 206 */     if (s.contains("&7"))
/*     */     {
/* 208 */       s = s.replace("&7", "");
/*     */     }
/*     */ 
/* 211 */     if (s.contains("&8"))
/*     */     {
/* 213 */       s = s.replace("&8", "");
/*     */     }
/*     */ 
/* 216 */     if (s.contains("&9"))
/*     */     {
/* 218 */       s = s.replace("&9", "");
/*     */     }
/*     */ 
/* 221 */     if (s.contains("&a"))
/*     */     {
/* 223 */       s = s.replace("&a", "");
/*     */     }
/*     */ 
/* 226 */     if (s.contains("&b"))
/*     */     {
/* 228 */       s = s.replace("&b", "");
/*     */     }
/*     */ 
/* 231 */     if (s.contains("&c"))
/*     */     {
/* 233 */       s = s.replace("&c", "");
/*     */     }
/*     */ 
/* 236 */     if (s.contains("&d"))
/*     */     {
/* 238 */       s = s.replace("&d", "");
/*     */     }
/*     */ 
/* 241 */     if (s.contains("&e"))
/*     */     {
/* 243 */       s = s.replace("&e", "");
/*     */     }
/*     */ 
/* 246 */     if (s.contains("&f"))
/*     */     {
/* 248 */       s = s.replace("&f", "");
/*     */     }
/*     */ 
/* 251 */     return s;
/*     */   }
/*     */ 
/*     */   public static String addChatColor(String lore)
/*     */   {
/* 256 */     if (lore.contains("&0"))
/*     */     {
/* 258 */       lore = lore.replace("&0", ChatColor.BLACK);
/*     */     }
/* 260 */     if (lore.contains("&1"))
/*     */     {
/* 262 */       lore = lore.replace("&1", ChatColor.DARK_BLUE);
/*     */     }
/* 264 */     if (lore.contains("&2"))
/*     */     {
/* 266 */       lore = lore.replace("&2", ChatColor.DARK_GREEN);
/*     */     }
/* 268 */     if (lore.contains("&3"))
/*     */     {
/* 270 */       lore = lore.replace("&3", ChatColor.DARK_AQUA);
/*     */     }
/* 272 */     if (lore.contains("&4"))
/*     */     {
/* 274 */       lore = lore.replace("&4", ChatColor.DARK_RED);
/*     */     }
/* 276 */     if (lore.contains("&5"))
/*     */     {
/* 278 */       lore = lore.replace("&5", ChatColor.DARK_PURPLE);
/*     */     }
/* 280 */     if (lore.contains("&6"))
/*     */     {
/* 282 */       lore = lore.replace("&6", ChatColor.GOLD);
/*     */     }
/* 284 */     if (lore.contains("&7"))
/*     */     {
/* 286 */       lore = lore.replace("&7", ChatColor.GRAY);
/*     */     }
/* 288 */     if (lore.contains("&8"))
/*     */     {
/* 290 */       lore = lore.replace("&8", ChatColor.DARK_GRAY);
/*     */     }
/* 292 */     if (lore.contains("&9"))
/*     */     {
/* 294 */       lore = lore.replace("&9", ChatColor.BLUE);
/*     */     }
/* 296 */     if (lore.contains("&a"))
/*     */     {
/* 298 */       lore = lore.replace("&a", ChatColor.GREEN);
/*     */     }
/* 300 */     if (lore.contains("&b"))
/*     */     {
/* 302 */       lore = lore.replace("&b", ChatColor.AQUA);
/*     */     }
/* 304 */     if (lore.contains("&c"))
/*     */     {
/* 306 */       lore = lore.replace("&c", ChatColor.RED);
/*     */     }
/* 308 */     if (lore.contains("&d"))
/*     */     {
/* 310 */       lore = lore.replace("&d", ChatColor.LIGHT_PURPLE);
/*     */     }
/* 312 */     if (lore.contains("&e"))
/*     */     {
/* 314 */       lore = lore.replace("&e", ChatColor.YELLOW);
/*     */     }
/* 316 */     if (lore.contains("&f"))
/*     */     {
/* 318 */       lore = lore.replace("&f", ChatColor.WHITE);
/*     */     }
/* 320 */     if (lore.contains("&k"))
/*     */     {
/* 322 */       lore = lore.replace("&k", ChatColor.MAGIC);
/*     */     }
/* 324 */     return lore;
/*     */   }
/*     */ 
/*     */   public static String[] addChatColor(String[] lore)
/*     */   {
/* 329 */     String[] temp = new String[lore.length];
/* 330 */     int i = 0;
/* 331 */     String[] arrayOfString1 = lore; int j = lore.length; for (int i = 0; i < j; i++) { String s = arrayOfString1[i];
/*     */ 
/* 333 */       if (s.contains("&0"))
/*     */       {
/* 335 */         s = s.replace("&0", ChatColor.BLACK);
/*     */       }
/* 337 */       if (s.contains("&1"))
/*     */       {
/* 339 */         s = s.replace("&1", ChatColor.DARK_BLUE);
/*     */       }
/* 341 */       if (s.contains("&2"))
/*     */       {
/* 343 */         s = s.replace("&2", ChatColor.DARK_GREEN);
/*     */       }
/* 345 */       if (s.contains("&3"))
/*     */       {
/* 347 */         s = s.replace("&3", ChatColor.DARK_AQUA);
/*     */       }
/* 349 */       if (s.contains("&4"))
/*     */       {
/* 351 */         s = s.replace("&4", ChatColor.DARK_RED);
/*     */       }
/* 353 */       if (s.contains("&5"))
/*     */       {
/* 355 */         s = s.replace("&5", ChatColor.DARK_PURPLE);
/*     */       }
/* 357 */       if (s.contains("&6"))
/*     */       {
/* 359 */         s = s.replace("&6", ChatColor.GOLD);
/*     */       }
/* 361 */       if (s.contains("&7"))
/*     */       {
/* 363 */         s = s.replace("&7", ChatColor.GRAY);
/*     */       }
/* 365 */       if (s.contains("&8"))
/*     */       {
/* 367 */         s = s.replace("&8", ChatColor.DARK_GRAY);
/*     */       }
/* 369 */       if (s.contains("&9"))
/*     */       {
/* 371 */         s = s.replace("&9", ChatColor.BLUE);
/*     */       }
/* 373 */       if (s.contains("&a"))
/*     */       {
/* 375 */         s = s.replace("&a", ChatColor.GREEN);
/*     */       }
/* 377 */       if (s.contains("&b"))
/*     */       {
/* 379 */         s = s.replace("&b", ChatColor.AQUA);
/*     */       }
/* 381 */       if (s.contains("&c"))
/*     */       {
/* 383 */         s = s.replace("&c", ChatColor.RED);
/*     */       }
/* 385 */       if (s.contains("&d"))
/*     */       {
/* 387 */         s = s.replace("&d", ChatColor.LIGHT_PURPLE);
/*     */       }
/* 389 */       if (s.contains("&e"))
/*     */       {
/* 391 */         s = s.replace("&e", ChatColor.YELLOW);
/*     */       }
/* 393 */       if (s.contains("&f"))
/*     */       {
/* 395 */         s = s.replace("&f", ChatColor.WHITE);
/*     */       }
/* 397 */       if (s.contains("&k"))
/*     */       {
/* 399 */         s = s.replace("&k", ChatColor.MAGIC);
/*     */       }
/* 401 */       temp[i] = s;
/* 402 */       i++;
/*     */     }
/*     */ 
/* 405 */     return temp;
/*     */   }
/*     */ 
/*     */   public static List<String> addChatColor(List<String> lore)
/*     */   {
/* 410 */     List temp = new ArrayList(lore.size());
/* 411 */     for (String s : lore)
/*     */     {
/* 413 */       if (s.contains("&0"))
/*     */       {
/* 415 */         s = s.replace("&0", ChatColor.BLACK);
/*     */       }
/* 417 */       if (s.contains("&1"))
/*     */       {
/* 419 */         s = s.replace("&1", ChatColor.DARK_BLUE);
/*     */       }
/* 421 */       if (s.contains("&2"))
/*     */       {
/* 423 */         s = s.replace("&2", ChatColor.DARK_GREEN);
/*     */       }
/* 425 */       if (s.contains("&3"))
/*     */       {
/* 427 */         s = s.replace("&3", ChatColor.DARK_AQUA);
/*     */       }
/* 429 */       if (s.contains("&4"))
/*     */       {
/* 431 */         s = s.replace("&4", ChatColor.DARK_RED);
/*     */       }
/* 433 */       if (s.contains("&5"))
/*     */       {
/* 435 */         s = s.replace("&5", ChatColor.DARK_PURPLE);
/*     */       }
/* 437 */       if (s.contains("&6"))
/*     */       {
/* 439 */         s = s.replace("&6", ChatColor.GOLD);
/*     */       }
/* 441 */       if (s.contains("&7"))
/*     */       {
/* 443 */         s = s.replace("&7", ChatColor.GRAY);
/*     */       }
/* 445 */       if (s.contains("&8"))
/*     */       {
/* 447 */         s = s.replace("&8", ChatColor.DARK_GRAY);
/*     */       }
/* 449 */       if (s.contains("&9"))
/*     */       {
/* 451 */         s = s.replace("&9", ChatColor.BLUE);
/*     */       }
/* 453 */       if (s.contains("&a"))
/*     */       {
/* 455 */         s = s.replace("&a", ChatColor.GREEN);
/*     */       }
/* 457 */       if (s.contains("&b"))
/*     */       {
/* 459 */         s = s.replace("&b", ChatColor.AQUA);
/*     */       }
/* 461 */       if (s.contains("&c"))
/*     */       {
/* 463 */         s = s.replace("&c", ChatColor.RED);
/*     */       }
/* 465 */       if (s.contains("&d"))
/*     */       {
/* 467 */         s = s.replace("&d", ChatColor.LIGHT_PURPLE);
/*     */       }
/* 469 */       if (s.contains("&e"))
/*     */       {
/* 471 */         s = s.replace("&e", ChatColor.YELLOW);
/*     */       }
/* 473 */       if (s.contains("&f"))
/*     */       {
/* 475 */         s = s.replace("&f", ChatColor.WHITE);
/*     */       }
/* 477 */       if (s.contains("&k"))
/*     */       {
/* 479 */         s = s.replace("&k", ChatColor.MAGIC);
/*     */       }
/* 481 */       temp.add(s);
/*     */     }
/*     */ 
/* 484 */     return temp;
/*     */   }
/*     */ 
/*     */   public static boolean setColor(ItemStack item, Color color)
/*     */   {
/* 489 */     if (!(item.getItemMeta() instanceof LeatherArmorMeta))
/* 490 */       return false;
/* 491 */     LeatherArmorMeta lam = (LeatherArmorMeta)item.getItemMeta();
/* 492 */     lam.setColor(color);
/* 493 */     item.setItemMeta(lam);
/* 494 */     return true;
/*     */   }
/*     */ 
/*     */   public static Color getColor(ItemStack item)
/*     */   {
/* 499 */     if (!(item.getItemMeta() instanceof LeatherArmorMeta))
/* 500 */       return null;
/* 501 */     LeatherArmorMeta lam = (LeatherArmorMeta)item.getItemMeta();
/* 502 */     return lam.getColor();
/*     */   }
/*     */ 
/*     */   public static boolean hasLore(ItemStack item)
/*     */   {
/* 507 */     if (!item.hasItemMeta())
/* 508 */       return false;
/* 509 */     ItemMeta im = item.getItemMeta();
/* 510 */     return im.hasLore();
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Util.ItemUtils
 * JD-Core Version:    0.6.2
 */