/*     */ package Event;
/*     */ 
/*     */ import PvpBalance.PVPPlayer;
/*     */ import PvpBalance.PvpHandler;
/*     */ import Util.InventoryManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class EventRunner
/*     */ {
/*  16 */   public static ArrayList<Player> participants = new ArrayList();
/*  17 */   public static HashMap<String, Integer> deaths = new HashMap();
/*  18 */   public static HashMap<String, Location> previousLOC = new HashMap();
/*  19 */   public static String eventName = "";
/*  20 */   private static int tick = 0;
/*  21 */   private static boolean eventActive = false;
/*  22 */   private static int totalPlayers = 0;
/*     */ 
/*     */   public static void tick()
/*     */   {
/*     */   }
/*     */ 
/*     */   public static void joinEvent(Player player)
/*     */   {
/*  63 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  64 */     if (!pvp.isInCombat()) {
/*  65 */       if (!eventActive) {
/*  66 */         player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, NO EVENTS RUNNING. NEXT EVENT IN " + ChatColor.GREEN + (3600 - tick) / 60 + " MINUTES");
/*  67 */         return;
/*     */       }
/*  69 */       if (eventName.equalsIgnoreCase(SkyArrow.getEventName())) {
/*  70 */         if (SkyArrow.active) {
/*  71 */           player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, EVENT IN PROGRESS. PLEASE WAIT TILL THE NEXT EVENT!");
/*  72 */           SkyArrow.listPlayers(player);
/*     */         }
/*  74 */         if (InventoryManager.storeInventory(player)) {
/*  75 */           storeLocation(player);
/*  76 */           participants.add(player);
/*  77 */           if (!eventActive) totalPlayers += 1;
/*  78 */           player.sendMessage(ChatColor.AQUA + "Welcome to " + ChatColor.GREEN + eventName + ChatColor.AQUA + " event will begin shortly!");
/*  79 */           pvp.setEventGrace(true);
/*  80 */           pvp.setInEvent(true);
/*  81 */           SkyArrow.join(player);
/*  82 */           return;
/*     */         }
/*     */ 
/*  85 */         player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, PLEASE PUT YOUR ARMOR IN YOUR INVENTORY AND TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " AGAIN!");
/*  86 */         return;
/*     */       }
/*     */ 
/*  89 */       if (eventName.equalsIgnoreCase(CrazyRace.getEventName())) {
/*  90 */         if (CrazyRace.active) {
/*  91 */           player.sendMessage(ChatColor.RED + "Joining event already in progress");
/*  92 */           CrazyRace.listPlayers(player);
/*     */         }
/*  94 */         if (InventoryManager.storeInventory(player)) {
/*  95 */           storeLocation(player);
/*  96 */           participants.add(player);
/*  97 */           if (!eventActive) totalPlayers += 1;
/*  98 */           player.sendMessage(ChatColor.AQUA + "Welcome to " + ChatColor.GREEN + eventName + ChatColor.AQUA + " event will begin shortly!");
/*  99 */           pvp.setEventGrace(true);
/* 100 */           pvp.setInEvent(true);
/* 101 */           CrazyRace.join(player);
/* 102 */           return;
/*     */         }
/*     */ 
/* 105 */         player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, PLEASE PUT YOUR ARMOR IN YOUR INVENTORY AND TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " AGAIN!");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 111 */       player.sendMessage(ChatColor.RED + "UNABLE TO JOIN, YOU ARE IN COMBAT! LEAVE COMBAT THEN TYPE " + ChatColor.GREEN + "/PLAY" + ChatColor.RED + " AGAIN!");
/*     */     }
/*     */   }
/*     */ 
/* 115 */   public static void endEvent() { participants.clear();
/* 116 */     deaths.clear();
/* 117 */     eventName = "";
/* 118 */     setTick(1);
/* 119 */     eventActive = false;
/* 120 */     totalPlayers = 0; }
/*     */ 
/*     */   public static void leaveEvent(Player player) {
/* 123 */     if (participants.contains(player)) {
/* 124 */       participants.remove(player);
/* 125 */       if (!eventActive) totalPlayers -= 1;
/* 126 */       if (deaths.containsKey(player.getName().toString())) deaths.remove(player.getName().toString());
/* 127 */       player.sendMessage(ChatColor.AQUA + "You have left " + ChatColor.GREEN + eventName + ChatColor.AQUA + " thank you for playing!");
/* 128 */       PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 129 */       pvp.sethealth(pvp.getMaxHealth());
/* 130 */       returnToPreviousLocation(player);
/* 131 */       InventoryManager.getInventory(player);
/* 132 */       if (getActiveEvent().equalsIgnoreCase(SkyArrow.getEventName())) {
/* 133 */         if ((SkyArrow.players.size() == 1) && (SkyArrow.isActive())) {
/* 134 */           for (Player player2 : SkyArrow.players) {
/* 135 */             SkyArrow.setWinner(player2);
/* 136 */             SkyArrow.leave(player2);
/*     */           }
/*     */         }
/* 139 */         if ((SkyArrow.winner != null) && 
/* 140 */           (SkyArrow.winner.getName().toString().equalsIgnoreCase(player.getName().toString()))) {
/* 141 */           SkyArrow.winner(player);
/*     */         }
/*     */       }
/*     */ 
/* 145 */       if ((getActiveEvent().equalsIgnoreCase(CrazyRace.getEventName())) && 
/* 146 */         (CrazyRace.winner != null) && 
/* 147 */         (CrazyRace.winner.getName().toString().equalsIgnoreCase(player.getName().toString()))) {
/* 148 */         CrazyRace.winner(player);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 154 */       player.sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU ARE NOT IN AN EVENT! ");
/*     */     }
/*     */   }
/*     */ 
/* 158 */   public static void death(Player player) { if (deaths.containsKey(player.getName().toString())) {
/* 159 */       deaths.put(player.getName().toString(), (Integer)deaths.get(player.getName().toString() + 1));
/*     */     }
/*     */     else
/* 162 */       deaths.put(player.getName().toString(), Integer.valueOf(1)); }
/*     */ 
/*     */   public static void storeLocation(Player player)
/*     */   {
/* 166 */     previousLOC.put(player.getName().toString(), player.getLocation());
/*     */   }
/*     */   public static void returnToPreviousLocation(Player player) {
/* 169 */     if (previousLOC.containsKey(player.getName().toString())) {
/* 170 */       player.teleport((Location)previousLOC.get(player.getName().toString()));
/* 171 */       previousLOC.remove(player.getName().toString());
/*     */     }
/*     */   }
/*     */ 
/* 175 */   public static void removeFromEvent(Player player) { PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 176 */     if (participants.contains(player)) {
/* 177 */       participants.remove(player);
/* 178 */       pvp.setEventGrace(false);
/* 179 */       pvp.setInEvent(false);
/* 180 */       return;
/*     */     } }
/*     */ 
/*     */   public static boolean isActive() {
/* 184 */     return eventActive;
/*     */   }
/*     */   public static String getActiveEvent() {
/* 187 */     return eventName;
/*     */   }
/*     */   public static void setTick(int newTick) {
/* 190 */     tick = newTick;
/*     */   }
/*     */   public static int getTick() {
/* 193 */     return tick;
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Event.EventRunner
 * JD-Core Version:    0.6.2
 */