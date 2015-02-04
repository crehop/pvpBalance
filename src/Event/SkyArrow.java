/*     */ package Event;
/*     */ 
/*     */ import PvpBalance.PVPPlayer;
/*     */ import PvpBalance.PvpHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class SkyArrow
/*     */ {
/*  21 */   public static int grace = 0;
/*  22 */   public static int numberOfPlayers = 0;
/*  23 */   public static Player winner = null;
/*  24 */   public static boolean active = false;
/*  25 */   public static ArrayList<Player> players = new ArrayList();
/*     */ 
/*  27 */   public static void join(Player player) { if (players.size() == 0) {
/*  28 */       grace = 150;
/*     */     }
/*  30 */     players.add(player);
/*  31 */     teleportToStart(player);
/*  32 */     numberOfPlayers += 1;
/*  33 */     ItemStack prize = new ItemStack(Material.BOW);
/*  34 */     ItemStack prize2 = new ItemStack(Material.ARROW);
/*  35 */     ItemStack prize3 = new ItemStack(Material.BREAD);
/*  36 */     ItemStack prize4 = new ItemStack(Material.IRON_CHESTPLATE);
/*  37 */     ItemStack prize5 = new ItemStack(Material.WOOD_SWORD);
/*  38 */     ItemMeta meta = prize.getItemMeta();
/*  39 */     meta.setDisplayName(ChatColor.YELLOW + "SkyArrow BOW");
/*  40 */     List lore = new ArrayList();
/*  41 */     lore.add(ChatColor.RED + "Woot Woot");
/*  42 */     meta.setLore(lore);
/*  43 */     prize.setItemMeta(meta);
/*  44 */     prize.setAmount(1);
/*  45 */     prize.addEnchantment(Enchantment.ARROW_INFINITE, 1);
/*  46 */     prize.addEnchantment(Enchantment.DURABILITY, 2);
/*  47 */     prize2.setAmount(1);
/*  48 */     prize3.setAmount(64);
/*  49 */     player.getInventory().addItem(new ItemStack[] { prize });
/*  50 */     player.getInventory().addItem(new ItemStack[] { prize2 });
/*  51 */     player.getInventory().addItem(new ItemStack[] { prize3 });
/*  52 */     player.getInventory().addItem(new ItemStack[] { prize4 });
/*  53 */     player.getInventory().addItem(new ItemStack[] { prize5 }); }
/*     */ 
/*     */   private static void teleportToStart(Player player) {
/*  56 */     Location start = new Location(Bukkit.getWorld("world"), -956.0D, 151.0D, -25.0D);
/*  57 */     player.teleport(start);
/*     */   }
/*     */   public static void leave(Player player) {
/*  60 */     for (ItemStack item : player.getInventory().getArmorContents()) {
/*  61 */       item.setType(Material.AIR);
/*     */     }
/*  63 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  64 */     pvp.setInEvent(false);
/*  65 */     pvp.setEventGrace(false);
/*  66 */     players.remove(player);
/*  67 */     EventRunner.leaveEvent(player);
/*     */   }
/*     */   public static void reset() {
/*  70 */     grace = 0;
/*  71 */     numberOfPlayers = 0;
/*  72 */     winner = null;
/*  73 */     setActive(false);
/*  74 */     for (Player player : players) {
/*  75 */       leave(player);
/*     */     }
/*  77 */     players.clear();
/*  78 */     EventRunner.endEvent();
/*     */   }
/*     */   public static void winner(Player player) {
/*  81 */     ItemStack prize = new ItemStack(Material.DRAGON_EGG);
/*  82 */     ItemMeta meta = prize.getItemMeta();
/*  83 */     meta.setDisplayName(ChatColor.YELLOW + "Event Prize Egg");
/*  84 */     List lore = new ArrayList();
/*  85 */     lore.add(ChatColor.GREEN + "/pb use" + ChatColor.AQUA + " to open!");
/*  86 */     meta.setLore(lore);
/*  87 */     prize.setItemMeta(meta);
/*  88 */     prize.setAmount(2);
/*  89 */     ItemStack prize2 = new ItemStack(Material.NETHER_STAR);
/*  90 */     ItemMeta meta2 = prize2.getItemMeta();
/*  91 */     meta2.setDisplayName(ChatColor.YELLOW + "Armor Token");
/*  92 */     List lore2 = new ArrayList();
/*  93 */     lore2.add(ChatColor.GREEN + "/rules armor" + ChatColor.AQUA + " for more information!");
/*  94 */     meta2.setLore(lore2);
/*  95 */     prize2.setItemMeta(meta2);
/*  96 */     player.getInventory().addItem(new ItemStack[] { prize2 });
/*  97 */     Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has won " + getEventName());
/*  98 */     ItemStack prize3 = new ItemStack(Material.BOW);
/*  99 */     ItemMeta meta3 = prize3.getItemMeta();
/* 100 */     meta3.setDisplayName(ChatColor.YELLOW + "SkyArrow BOW");
/* 101 */     List lore3 = new ArrayList();
/* 102 */     lore3.add(ChatColor.RED + "Woot Woot");
/* 103 */     meta3.setLore(lore3);
/* 104 */     prize3.setItemMeta(meta3);
/* 105 */     prize3.setAmount(1);
/* 106 */     prize3.addEnchantment(Enchantment.ARROW_INFINITE, 1);
/* 107 */     player.getInventory().addItem(new ItemStack[] { prize });
/* 108 */     player.getInventory().addItem(new ItemStack[] { prize2 });
/* 109 */     player.getInventory().addItem(new ItemStack[] { prize3 });
/*     */   }
/*     */   public static void setActive(boolean state) {
/* 112 */     active = state;
/*     */   }
/*     */   public static boolean isActive() {
/* 115 */     return active;
/*     */   }
/*     */   public static void setGrace(int graceTime) {
/* 118 */     grace = graceTime;
/*     */   }
/*     */   public static int getGrace() {
/* 121 */     return grace;
/*     */   }
/*     */   public static void tick() {
/* 124 */     if (players.size() > 0) {
/* 125 */       for (Iterator it = players.iterator(); it.hasNext(); ) {
/*     */         try {
/* 127 */           Player player = (Player)it.next();
/* 128 */           if (player == null) {
/* 129 */             it.remove();
/*     */           }
/* 131 */           if (!player.isOnline()) {
/* 132 */             it.remove();
/*     */           }
/* 134 */           PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 135 */           if (!pvp.isInEvent()) pvp.setInEvent(true); 
/*     */         }
/* 137 */         catch (ConcurrentModificationException e) { e.printStackTrace(); }
/*     */ 
/*     */       }
/*     */     }
/*     */ 
/* 142 */     if ((grace <= 5) && (grace != 0)) {
/* 143 */       for (Player player : players) {
/* 144 */         player.sendMessage(ChatColor.YELLOW + "Event will start in " + ChatColor.RED + grace + ChatColor.YELLOW + " seconds.");
/*     */       }
/*     */     }
/* 147 */     if ((grace % 10 == 0) && (grace != 0)) {
/* 148 */       for (Player player : players) {
/* 149 */         player.sendMessage(ChatColor.GREEN + "Joining grace period, Event will start in " + ChatColor.RED + grace + ChatColor.GREEN + " seconds.");
/*     */       }
/*     */     }
/* 152 */     if (grace == 1) {
/* 153 */       if (players.size() == 0) {
/* 154 */         reset();
/*     */       }
/* 156 */       if (players.size() == 1) {
/* 157 */         setWinner((Player)players.get(0));
/* 158 */         leave((Player)players.get(0));
/*     */       }
/* 160 */       if (players.size() >= 5) {
/* 161 */         setActive(true);
/* 162 */         grace = 0;
/* 163 */         for (Player player : players) {
/* 164 */           PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 165 */           pvp.setEventGrace(false);
/*     */         }
/* 167 */         Bukkit.broadcastMessage(getEventName() + ChatColor.GREEN + " Has Started with " + ChatColor.RED + players.size() + ChatColor.GREEN + " players GOOD LUCK");
/*     */       }
/*     */       else {
/* 170 */         for (Player player : players) {
/* 171 */           player.sendMessage(ChatColor.RED + "Not enough players to start " + getEventName() + ", Need 5. Extending grace period 30 Seconds!");
/*     */         }
/* 173 */         grace = 30;
/*     */       }
/*     */     }
/* 176 */     if (grace > 0) {
/* 177 */       for (Player player : players) {
/* 178 */         if (player != null) {
/* 179 */           PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 180 */           if (pvp != null) pvp.setEventGrace(true);
/*     */         }
/*     */       }
/* 183 */       grace -= 1;
/*     */     }
/* 185 */     if (grace <= 0) {
/* 186 */       for (Player player : players) {
/* 187 */         PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 188 */         if (!pvp.flyZone) {
/* 189 */           leave(player);
/* 190 */           player.sendMessage("You have Exited the arena!");
/*     */         }
/*     */       }
/*     */     }
/* 194 */     if ((players.size() == 0) && (active))
/* 195 */       reset();
/*     */   }
/*     */ 
/*     */   public static boolean checkParticipant(String playername) {
/* 199 */     for (Player check : players) {
/* 200 */       if (check.getName().toString().equalsIgnoreCase(playername)) {
/* 201 */         return true;
/*     */       }
/*     */     }
/* 204 */     return false;
/*     */   }
/*     */   public static boolean checkParticipant(Player player) {
/* 207 */     for (Player check : players) {
/* 208 */       if (check.getName().toString().equalsIgnoreCase(player.getName().toString())) {
/* 209 */         return true;
/*     */       }
/*     */     }
/* 212 */     return false;
/*     */   }
/*     */   public static void setWinner(Player player) {
/* 215 */     winner = player;
/*     */   }
/*     */   public static void simulateDeath(Player player) {
/* 218 */     PVPPlayer pvpDeath = PvpHandler.getPvpPlayer(player);
/* 219 */     PVPPlayer pvpKiller = PvpHandler.getPvpPlayer(pvpDeath.getLastHitBy());
/* 220 */     pvpDeath.sethealth(pvpDeath.getMaxHealth());
/* 221 */     pvpKiller.sethealth(pvpKiller.getMaxHealth());
/* 222 */     pvpKiller.getPlayer().sendMessage(getEventName() + ChatColor.GREEN + ": You have killed " + player.getDisplayName() + " and gained full health!");
/* 223 */     leave(player);
/*     */   }
/*     */ 
/*     */   public static String getEventName()
/*     */   {
/* 228 */     return ChatColor.AQUA + "Sky" + ChatColor.RED + "Arrow";
/*     */   }
/*     */   public static void listPlayers(Player playerToTell) {
/* 231 */     playerToTell.sendMessage(getEventName() + ChatColor.YELLOW + " Remaining Players : " + ChatColor.RED + ChatColor.BOLD + players.size());
/* 232 */     int counter = 0;
/* 233 */     for (Player player : players) {
/* 234 */       counter++;
/* 235 */       playerToTell.sendMessage(ChatColor.GREEN + counter + ":" + ChatColor.AQUA + player.getName());
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Event.SkyArrow
 * JD-Core Version:    0.6.2
 */