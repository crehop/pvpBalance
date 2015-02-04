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
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class CrazyRace
/*     */ {
/*  23 */   public static Location starter = new Location(Bukkit.getWorld("event"), -52.0D, 38.0D, 9.0D);
/*  24 */   public static int grace = 0;
/*  25 */   public static int numberOfPlayers = 0;
/*  26 */   public static Player winner = null;
/*  27 */   public static boolean active = false;
/*  28 */   public static ArrayList<Player> players2 = new ArrayList();
/*  29 */   public static int timer = 0;
/*  30 */   public static World world = Bukkit.getWorld("event");
/*     */ 
/*     */   public static void join(Player player) {
/*  33 */     if (players2.size() == 0) {
/*  34 */       grace = 150;
/*  35 */       starter.getBlock().setType(Material.COAL);
/*     */     }
/*  37 */     players2.add(player);
/*  38 */     teleportToStart(player);
/*  39 */     numberOfPlayers += 1;
/*  40 */     ItemStack prize = new ItemStack(Material.BREAD);
/*  41 */     prize.setAmount(64);
/*  42 */     player.getInventory().addItem(new ItemStack[] { prize });
/*     */   }
/*     */   private static void teleportToStart(Player player) {
/*  45 */     Location start = new Location(Bukkit.getWorld("event"), -44.0D, 40.0D, -4.0D);
/*  46 */     player.teleport(start);
/*     */   }
/*     */   public static void leave(Player player) {
/*  49 */     for (ItemStack item : player.getInventory().getArmorContents()) {
/*  50 */       item.setType(Material.AIR);
/*     */     }
/*  52 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  53 */     pvp.setInEvent(false);
/*  54 */     pvp.setEventGrace(false);
/*  55 */     for (Iterator it = players2.iterator(); it.hasNext(); ) {
/*  56 */       Player player2 = (Player)it.next();
/*  57 */       if (player2.getName().equalsIgnoreCase(player.getName())) {
/*  58 */         it.remove();
/*     */       }
/*     */     }
/*  61 */     EventRunner.leaveEvent(player);
/*     */   }
/*     */   public static void reset() {
/*  64 */     setActive(false);
/*  65 */     timer = 0;
/*  66 */     grace = 0;
/*  67 */     numberOfPlayers = 0;
/*  68 */     winner = null;
/*  69 */     starter.getBlock().setType(Material.COAL_BLOCK);
/*  70 */     for (Player player : players2) {
/*  71 */       leave(player);
/*     */     }
/*  73 */     players2.clear();
/*  74 */     EventRunner.endEvent();
/*     */   }
/*     */   public static void winner(Player player) {
/*  77 */     ItemStack prize = new ItemStack(Material.DRAGON_EGG);
/*  78 */     ItemMeta meta = prize.getItemMeta();
/*  79 */     meta.setDisplayName(ChatColor.YELLOW + "Event Prize Egg");
/*  80 */     List lore = new ArrayList();
/*  81 */     lore.add(ChatColor.GREEN + "/pb use" + ChatColor.AQUA + " to open!");
/*  82 */     meta.setLore(lore);
/*  83 */     prize.setItemMeta(meta);
/*  84 */     ItemStack prize2 = new ItemStack(Material.NETHER_STAR);
/*  85 */     ItemMeta meta2 = prize2.getItemMeta();
/*  86 */     meta2.setDisplayName(ChatColor.YELLOW + "Armor Token");
/*  87 */     List lore2 = new ArrayList();
/*  88 */     lore2.add(ChatColor.GREEN + "/rules armor" + ChatColor.AQUA + " for more information!");
/*  89 */     meta2.setLore(lore2);
/*  90 */     prize2.setItemMeta(meta2);
/*  91 */     player.getInventory().addItem(new ItemStack[] { prize2 });
/*  92 */     Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has won " + getEventName());
/*  93 */     ItemStack prize3 = new ItemStack(Material.DIAMOND_BOOTS);
/*  94 */     prize3.addEnchantment(Enchantment.DURABILITY, 3);
/*  95 */     prize3.addEnchantment(Enchantment.THORNS, 3);
/*  96 */     ItemMeta meta3 = prize3.getItemMeta();
/*  97 */     meta3.setDisplayName(getEventName() + ChatColor.RED + " Boots");
/*  98 */     List lore3 = new ArrayList();
/*  99 */     lore3.add(ChatColor.RED + "Zoom Zoom");
/* 100 */     meta3.setLore(lore3);
/* 101 */     prize3.setItemMeta(meta3);
/* 102 */     prize3.setAmount(1);
/* 103 */     prize.setAmount(2);
/* 104 */     player.getInventory().addItem(new ItemStack[] { prize });
/* 105 */     player.getInventory().addItem(new ItemStack[] { prize2 });
/* 106 */     player.getInventory().addItem(new ItemStack[] { prize3 });
/* 107 */     reset();
/*     */   }
/*     */   public static void setActive(boolean state) {
/* 110 */     active = state;
/*     */   }
/*     */   public static boolean isActive() {
/* 113 */     return active;
/*     */   }
/*     */   public static void setGrace(int graceTime) {
/* 116 */     grace = graceTime;
/*     */   }
/*     */   public static int getGrace() {
/* 119 */     return grace;
/*     */   }
/*     */   public static void tick() {
/* 122 */     if (winner != null) {
/* 123 */       evacuate();
/*     */     }
/* 125 */     if ((players2.size() < 1) && (grace < 1)) {
/* 126 */       reset();
/*     */     }
/* 128 */     if (players2.size() > 0) {
/* 129 */       for (Iterator it = players2.iterator(); it.hasNext(); ) {
/*     */         try {
/* 131 */           Player player = (Player)it.next();
/* 132 */           if (player.getName() == null) {
/* 133 */             it.remove();
/*     */           }
/* 135 */           if (!player.isOnline()) {
/* 136 */             it.remove();
/*     */           }
/* 138 */           if (!player.getLocation().getWorld().toString().equalsIgnoreCase(starter.getWorld().toString())) {
/* 139 */             simulateDeath(player);
/*     */           }
/* 141 */           if (player.getLocation().getY() < 22.0D) {
/* 142 */             simulateDeath(player);
/*     */           }
/* 144 */           PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 145 */           if (!pvp.isInEvent()) pvp.setInEvent(true); 
/*     */         }
/* 147 */         catch (ConcurrentModificationException e) { e.printStackTrace(); }
/*     */ 
/*     */       }
/*     */     }
/* 151 */     if (isActive()) {
/* 152 */       if (timer > 0) {
/* 153 */         timer -= 1;
/*     */       }
/* 155 */       if (timer == 0) {
/* 156 */         evacuate();
/* 157 */         Bukkit.broadcastMessage(getEventName() + ChatColor.RED + " : Noone has won, 20 Minutes has passed. Ending Event!");
/* 158 */         reset();
/*     */       }
/*     */     }
/* 161 */     if ((grace <= 5) && (grace != 0)) {
/* 162 */       for (Player player : players2) {
/* 163 */         player.sendMessage(ChatColor.YELLOW + "Event will start in " + ChatColor.RED + grace + ChatColor.YELLOW + " seconds.");
/*     */       }
/*     */     }
/* 166 */     if ((grace % 10 == 0) && (grace != 0)) {
/* 167 */       for (Player player : players2) {
/* 168 */         player.sendMessage(ChatColor.GREEN + "Joining grace period, Event will start in " + ChatColor.RED + grace + ChatColor.GREEN + " seconds.");
/*     */       }
/*     */     }
/* 171 */     if (grace == 1) {
/* 172 */       if (players2.size() >= 1) {
/* 173 */         timer = 1200;
/* 174 */         setActive(true);
/* 175 */         starter.getBlock().setType(Material.REDSTONE_BLOCK);
/*     */ 
/* 177 */         Bukkit.broadcastMessage(getEventName() + ChatColor.GREEN + " Has Started with " + ChatColor.RED + players2.size() + ChatColor.GREEN + " players GOOD LUCK");
/*     */       }
/*     */       else {
/* 180 */         for (Player player : players2) {
/* 181 */           player.sendMessage(ChatColor.RED + "Not enough players to start " + getEventName() + ", Need 5. Extending grace period 30 Seconds!");
/*     */         }
/* 183 */         grace = 30;
/*     */       }
/*     */     }
/* 186 */     if (grace > 0) {
/* 187 */       grace -= 1;
/*     */     }
/*     */ 
/* 192 */     if ((players2.size() == 0) && (active))
/* 193 */       reset();
/*     */   }
/*     */ 
/*     */   private static void evacuate() {
/* 197 */     for (Iterator it = players2.iterator(); it.hasNext(); ) {
/* 198 */       Player player = (Player)it.next();
/* 199 */       it.remove();
/* 200 */       leave(player);
/*     */     }
/*     */   }
/*     */ 
/* 204 */   public static boolean checkParticipant(String playername) { for (Player check : players2) {
/* 205 */       if (check.getName().toString().equalsIgnoreCase(playername)) {
/* 206 */         return true;
/*     */       }
/*     */     }
/* 209 */     return false; }
/*     */ 
/*     */   public static boolean checkParticipant(Player player) {
/* 212 */     for (Player check : players2) {
/* 213 */       if (check.getName().toString().equalsIgnoreCase(player.getName().toString())) {
/* 214 */         return true;
/*     */       }
/*     */     }
/* 217 */     return false;
/*     */   }
/*     */   public static void setWinner(Player player) {
/* 220 */     winner = player;
/* 221 */     evacuate();
/*     */   }
/*     */   public static void simulateDeath(Player player) {
/* 224 */     PVPPlayer pvpDeath = PvpHandler.getPvpPlayer(player);
/* 225 */     if (pvpDeath != null) pvpDeath.sethealth(pvpDeath.getMaxHealth());
/* 226 */     player.sendMessage(ChatColor.RED + "You can type " + ChatColor.GREEN + "/leave" + ChatColor.RED + " to quit " + getEventName());
/* 227 */     teleportToStart(player);
/*     */   }
/*     */   public static String getEventName() {
/* 230 */     return ChatColor.AQUA + "C" + ChatColor.GOLD + "r" + ChatColor.DARK_RED + "a" + ChatColor.GREEN + "z" + ChatColor.LIGHT_PURPLE + "y" + ChatColor.YELLOW + " Race";
/*     */   }
/*     */   public static void listPlayers(Player playerToTell) {
/* 233 */     playerToTell.sendMessage(getEventName() + ChatColor.YELLOW + " Remaining Players : " + ChatColor.RED + ChatColor.BOLD + players2.size());
/* 234 */     int counter = 0;
/* 235 */     for (Player player : players2) {
/* 236 */       counter++;
/* 237 */       playerToTell.sendMessage(ChatColor.GREEN + counter + ":" + ChatColor.AQUA + player.getName());
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Event.CrazyRace
 * JD-Core Version:    0.6.2
 */