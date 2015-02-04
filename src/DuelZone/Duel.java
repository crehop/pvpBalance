/*     */ package DuelZone;
/*     */ 
/*     */ import PvpBalance.PVPPlayer;
/*     */ import PvpBalance.PvpBalance;
/*     */ import PvpBalance.PvpHandler;
/*     */ import Util.MYSQLManager;
/*     */ import java.sql.SQLException;
/*     */ import net.milkbowl.vault.economy.Economy;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class Duel
/*     */ {
/*     */   public static int EjectTimer;
/*     */   private static Player cont1;
/*     */   private static Player cont2;
/*     */   public static Location ejectLoc;
/*  18 */   public static int bet = 0;
/*  19 */   private static int winnings = 0;
/*     */   public static PVPPlayer cont1pvp;
/*     */   public static PVPPlayer cont2pvp;
/*  22 */   public static boolean has2players = false;
/*  23 */   public static boolean betAccepted = false;
/*  24 */   public static boolean betAccepted1 = false;
/*  25 */   public static boolean betAccepted2 = false;
/*  26 */   public static boolean duelAccepted = false;
/*  27 */   public static boolean duelAccepted1 = false;
/*  28 */   public static boolean duelAccepted2 = false;
/*     */   private static Player winner;
/*  30 */   public static boolean inprogress = false;
/*  31 */   public static int winState = -1;
/*  32 */   public static int bidTimer = -1;
/*  33 */   public static int acceptTimer = -1;
/*     */   private static boolean cancelled;
/*     */ 
/*     */   public static void payout(Player cont)
/*     */   {
/*  37 */     if (cont.getName() == cont1.getName()) {
/*  38 */       Bukkit.broadcastMessage(ChatColor.YELLOW + "[" + 
/*  39 */         ChatColor.LIGHT_PURPLE + "DUEL" + ChatColor.YELLOW + "]" + 
/*  40 */         ChatColor.LIGHT_PURPLE + ":" + ChatColor.RED + 
/*  41 */         cont1.getName() + ChatColor.YELLOW + 
/*  42 */         " WON IN A DUEL AGAINST " + ChatColor.RED + 
/*  43 */         cont2.getName() + ChatColor.YELLOW + " AND WON " + 
/*  44 */         ChatColor.GREEN + "$" + winnings + ChatColor.YELLOW + 
/*  45 */         " to duel enter the " + ChatColor.GOLD + "GOLDEN" + 
/*  46 */         ChatColor.YELLOW + " gate at spawn");
/*     */     }
/*     */     else {
/*  49 */       Bukkit.broadcastMessage(ChatColor.YELLOW + "[" + 
/*  50 */         ChatColor.LIGHT_PURPLE + "DUEL" + ChatColor.YELLOW + "]" + 
/*  51 */         ChatColor.LIGHT_PURPLE + ":" + ChatColor.RED + 
/*  52 */         cont2.getName() + ChatColor.YELLOW + 
/*  53 */         " WON IN A DUEL AGAINST " + ChatColor.RED + 
/*  54 */         cont1.getName() + ChatColor.YELLOW + " AND WON " + 
/*  55 */         ChatColor.GREEN + "$" + winnings + ChatColor.YELLOW + 
/*  56 */         " to duel enter the " + ChatColor.GOLD + "GOLDEN" + 
/*  57 */         ChatColor.YELLOW + " gate at spawn");
/*     */     }
/*     */ 
/*  60 */     PvpBalance.economy.depositPlayer(cont.getName(), winnings);
/*  61 */     PvpBalance.economy.depositPlayer("realmtaxs", 
/*  62 */       winnings / 10);
/*  63 */     cont.sendMessage(ChatColor.GREEN + "$" + winnings + 
/*  64 */       " Has been deposited in your bank account!");
/*  65 */     winnings = 0;
/*     */   }
/*     */ 
/*     */   public static boolean checkContestant(Player player) {
/*  69 */     if ((cont1 != null) && 
/*  70 */       (player.getName() == cont1.getName())) {
/*  71 */       return true;
/*     */     }
/*     */ 
/*  74 */     if ((cont2 != null) && 
/*  75 */       (player.getName() == cont2.getName())) {
/*  76 */       return true;
/*     */     }
/*     */ 
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   public static void setBet(int bet) {
/*  83 */     if ((checkBet(bet)) && (bet < 2499)) {
/*  84 */       bet = bet;
/*  85 */       bidTimer = -1;
/*  86 */       cont1.sendMessage(ChatColor.BLUE + "BET ENTERED" + ChatColor.GREEN + 
/*  87 */         " $" + bet + ChatColor.BLUE + " please say " + 
/*  88 */         ChatColor.YELLOW + "/acceptbet" + ChatColor.BLUE + 
/*  89 */         " TO CONFIRM");
/*  90 */       cont2.sendMessage(ChatColor.BLUE + "BET ENTERED" + ChatColor.GREEN + 
/*  91 */         " $" + bet + ChatColor.BLUE + " please say " + 
/*  92 */         ChatColor.YELLOW + "/acceptbet" + ChatColor.BLUE + 
/*  93 */         " TO CONFIRM");
/*  94 */     } else if (bet > 2499) {
/*  95 */       if (cont1 != null) {
/*  96 */         cont1.sendMessage(ChatColor.RED + ChatColor.BOLD + 
/*  97 */           "BET ALREADY ENTERED $" + bet + " PLEASE SAY" + 
/*  98 */           ChatColor.GREEN + " /acceptbet" + ChatColor.RED + 
/*  99 */           " TO ACCEPT");
/*     */       }
/* 101 */       if (cont2 != null)
/* 102 */         cont2.sendMessage(ChatColor.RED + ChatColor.BOLD + 
/* 103 */           "BET ALREADY ENTERED $" + bet + " PLEASE SAY" + 
/* 104 */           ChatColor.GREEN + " /acceptbet" + ChatColor.RED + 
/* 105 */           " TO ACCEPT");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean checkBet(int bet)
/*     */   {
/* 111 */     if (bet < 2500) {
/* 112 */       cont1.sendMessage(ChatColor.RED + "Bet not high enough try again! must be at least $2500");
/* 113 */       cont2.sendMessage(ChatColor.RED + "Bet not high enough try again! must be at least $2500");
/* 114 */       return false;
/*     */     }
/* 116 */     if (PvpBalance.economy.getBalance(cont1.getName()) < bet) {
/* 117 */       cont1.sendMessage(ChatColor.RED + cont1.getName() + 
/* 118 */         " Does not have enough funds for this bet! try again!");
/* 119 */       cont2.sendMessage(ChatColor.RED + cont1.getName() + 
/* 120 */         " Does not have enough funds for this bet! try again!");
/* 121 */       cancelDuel();
/* 122 */       return false;
/* 123 */     }if (PvpBalance.economy.getBalance(cont2.getName()) < bet) {
/* 124 */       cont1.sendMessage(ChatColor.RED + cont2.getName() + 
/* 125 */         " Does not have enough funds for this bet! try again!");
/* 126 */       cont2.sendMessage(ChatColor.RED + cont2.getName() + 
/* 127 */         " Does not have enough funds for this bet! try again!");
/* 128 */       cancelDuel();
/* 129 */       return false;
/*     */     }
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */   public static void cancelDuel() {
/* 135 */     if (cont1 != null) {
/* 136 */       cont1.sendMessage(ChatColor.RED + ChatColor.BOLD + 
/* 137 */         "DUEL ENDED");
/* 138 */       if (cont1pvp != null)
/* 139 */         cont1pvp.setCombatCoolDown(0);
/*     */     }
/* 141 */     if (cont2 != null) {
/* 142 */       cont2.sendMessage(ChatColor.RED + ChatColor.BOLD + 
/* 143 */         "DUEL ENDED");
/* 144 */       if (cont2pvp != null)
/* 145 */         cont2pvp.setCombatCooldown(0);
/*     */     }
/* 147 */     PVPPlayer cont1pvp = PvpHandler.getPvpPlayer(cont1);
/* 148 */     PVPPlayer cont2pvp = PvpHandler.getPvpPlayer(cont2);
/* 149 */     eject(cont1);
/* 150 */     eject(cont2);
/* 151 */     winState = -1;
/* 152 */     if (cont1pvp != null)
/* 153 */       cont1pvp.setDuelContestant(false);
/* 154 */     if (cont2pvp != null)
/* 155 */       cont2pvp.setDuelContestant(false);
/* 156 */     has2players = false;
/* 157 */     cont1 = null;
/* 158 */     cont2 = null;
/* 159 */     bet = 0;
/* 160 */     winnings = 0;
/* 161 */     cont1pvp = null;
/* 162 */     cont2pvp = null;
/* 163 */     betAccepted = false;
/* 164 */     betAccepted1 = false;
/* 165 */     betAccepted2 = false;
/* 166 */     duelAccepted = false;
/* 167 */     duelAccepted1 = false;
/* 168 */     duelAccepted2 = false;
/* 169 */     winner = null;
/* 170 */     inprogress = false;
/* 171 */     EjectTimer = 0;
/* 172 */     winState = -1;
/* 173 */     bidTimer = -1;
/* 174 */     acceptTimer = -1;
/* 175 */     has2players = false;
/*     */   }
/*     */ 
/*     */   public static void acceptBet(Player player) {
/* 179 */     if (player.getName() == cont1.getName()) {
/* 180 */       betAccepted1 = true;
/* 181 */       cont1.sendMessage(ChatColor.GREEN + ChatColor.BOLD + 
/* 182 */         "BET ACCEPTED! $" + bet);
/*     */     }
/* 184 */     if (player.getName() == cont2.getName()) {
/* 185 */       betAccepted2 = true;
/* 186 */       cont2.sendMessage(ChatColor.GREEN + ChatColor.BOLD + 
/* 187 */         "BET ACCEPTED! $" + bet);
/* 188 */     } else if ((player.getName() != cont1.getName()) && 
/* 189 */       (player.getName() != cont2.getName())) {
/* 190 */       player.sendMessage(ChatColor.RED + ChatColor.BOLD + 
/* 191 */         "YOU ARE NOT CURRENTLY DUELING");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void addContestant(Player player) {
/* 196 */     if (!checkContestant(player)) {
/* 197 */       if (cont1 == null) {
/* 198 */         cont1 = player;
/* 199 */         EjectTimer = 10;
/* 200 */       } else if ((cont2 == null) && (player != cont1)) {
/* 201 */         cont2 = player;
/* 202 */         EjectTimer = 10;
/* 203 */       } else if ((cont1 != null) && (cont2 != null)) {
/* 204 */         player.sendMessage(ChatColor.RED + 
/* 205 */           "DUEL IN PROGRESS PLEASE WAIT " + EjectTimer + " SECONDS");
/* 206 */         eject(player);
/*     */       }
/* 208 */       if ((cont1 != null) && (cont2 != null) && (!inprogress))
/* 209 */         beginBid();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void beginBid()
/*     */   {
/* 215 */     EjectTimer = 20;
/* 216 */     bidTimer = 20;
/* 217 */     cont1.sendMessage(ChatColor.GREEN + ChatColor.BOLD + 
/* 218 */       "DUEL INITIATED, PLEASE SAY " + ChatColor.YELLOW + 
/* 219 */       "/bet <AMOUNT>");
/* 220 */     cont2.sendMessage(ChatColor.GREEN + ChatColor.BOLD + 
/* 221 */       "DUEL INITIATED, PLEASE SAY " + ChatColor.YELLOW + 
/* 222 */       "/bet <AMOUNT>");
/*     */   }
/*     */ 
/*     */   public static void playerDeath(Player player) {
/* 226 */     if ((cont1.getName() == player.getName()) && (winState < 1)) {
/*     */       try {
/* 228 */         PvpBalance.mysql.storeUserData(cont2, "DuelsWon", PvpBalance.mysql.getUserData(cont2, "DuelsWon") + 1);
/* 229 */         PvpBalance.mysql.storeUserData(cont1, "DuelsLost", PvpBalance.mysql.getUserData(cont1, "DuelsLost") + 1);
/*     */       }
/*     */       catch (SQLException e) {
/* 232 */         e.printStackTrace();
/*     */       }
/* 234 */       payout(cont2);
/* 235 */       winner = cont2;
/* 236 */       winState = 20;
/*     */     }
/* 238 */     if ((cont2.getName() == player.getName()) && (winState < 1)) {
/*     */       try {
/* 240 */         PvpBalance.mysql.storeUserData(cont1, "DuelsWon", PvpBalance.mysql.getUserData(cont1, "DuelsWon") + 1);
/* 241 */         PvpBalance.mysql.storeUserData(cont2, "DuelsLost", PvpBalance.mysql.getUserData(cont2, "DuelsLost") + 1);
/*     */       }
/*     */       catch (SQLException e) {
/* 244 */         e.printStackTrace();
/*     */       }
/* 246 */       payout(cont1);
/* 247 */       winner = cont1;
/* 248 */       winState = 20;
/*     */     } else {
/* 250 */       PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 251 */       pvp.setDuelContestant(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void tick() {
/* 256 */     if ((cont1 != null) && (cont2 != null))
/* 257 */       has2players = true;
/*     */     else {
/* 259 */       has2players = false;
/*     */     }
/* 261 */     if ((cont1 != null) && (duelAccepted) && 
/* 262 */       (!cont1.isOnline()) && (winState == -1) && (duelAccepted)) {
/* 263 */       if (cont2.isOnline()) {
/* 264 */         payout(cont2);
/*     */       }
/*     */       else {
/* 267 */         cancelDuel();
/*     */       }
/*     */     }
/*     */ 
/* 271 */     if ((cont2 != null) && (duelAccepted) && 
/* 272 */       (!cont2.isOnline()) && (winState == -1)) {
/* 273 */       if (cont1.isOnline()) {
/* 274 */         payout(cont1);
/*     */       }
/*     */       else {
/* 277 */         cancelDuel();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 282 */     if ((has2players) && (!cont1.isOnline()) && (!cont2.isOnline())) {
/* 283 */       cancelDuel();
/*     */     }
/* 285 */     if ((!duelAccepted) && (EjectTimer % 5 == 0) && 
/* 286 */       ((Duel.has2players = 1) != 0)) {
/* 287 */       if (cont1 != null) {
/* 288 */         cont1.sendMessage(ChatColor.RED + "Duel will cancel in " + 
/* 289 */           EjectTimer + " if bet/duel not accepted");
/* 290 */         if (cont2 == null) {
/* 291 */           cont1.sendMessage(ChatColor.RED + 
/* 292 */             "Waiting for another player");
/*     */         }
/*     */       }
/* 295 */       if (cont2 != null) {
/* 296 */         cont2.sendMessage(ChatColor.RED + "Duel will cancel in " + 
/* 297 */           EjectTimer + " if bet/duel not accepted");
/* 298 */         if (cont1 == null) {
/* 299 */           cont2.sendMessage(ChatColor.RED + 
/* 300 */             "Waiting for another player");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 305 */     if (winState > 0) {
/* 306 */       winState -= 1;
/* 307 */       if (winState % 5 == 0)
/* 308 */         winner.sendMessage("You will be ejected from the duel area in " + 
/* 309 */           winState + " seconds " + ChatColor.RED + 
/* 310 */           " DO NOT LEAVE BEFORE THIS RUNS OUT!");
/* 311 */     } else if (winState == 0) {
/* 312 */       winState = -1;
/* 313 */       cancelDuel();
/*     */     }
/* 315 */     if (duelAccepted) {
/* 316 */       inprogress = true;
/* 317 */       cont1pvp.setCombatCoolDown(20);
/* 318 */       cont2pvp.setCombatCooldown(20);
/* 319 */       if ((!cont1pvp.isDuelZone()) && (winState == -1)) {
/* 320 */         cont1.teleport(cont2);
/* 321 */         cont1pvp.sethealth(cont1pvp.gethealth() - 500);
/* 322 */         cont1.sendMessage("You have been penalized for leaving the arena! - 500 health");
/*     */       }
/* 324 */       if ((!cont2pvp.isDuelZone()) && (winState == -1)) {
/* 325 */         cont2.teleport(cont1);
/* 326 */         cont2pvp.sethealth(cont1pvp.gethealth() - 500);
/* 327 */         cont2.sendMessage("You have been penalized for leaving the arena! - 500 health");
/*     */       }
/*     */     }
/* 330 */     if ((ejectLoc == null) && (cont1 != null)) {
/* 331 */       ejectLoc = new Location(cont1.getWorld(), -645.0D, 82.0D, 346.0D);
/*     */     }
/* 333 */     if ((betAccepted1) && (betAccepted2) && 
/* 334 */       (!betAccepted)) {
/* 335 */       EjectTimer = 20;
/* 336 */       betAccepted = true;
/* 337 */       cont1.sendMessage(ChatColor.GREEN + ChatColor.BOLD + 
/* 338 */         "BET ACCEPTED TO BEGIN SAY " + ChatColor.YELLOW + 
/* 339 */         "/acceptduel");
/* 340 */       cont2.sendMessage(ChatColor.GREEN + ChatColor.BOLD + 
/* 341 */         "BET ACCEPTED TO BEGIN SAY " + ChatColor.YELLOW + 
/* 342 */         "/acceptduel");
/*     */     }
/* 344 */     if ((duelAccepted1) && (duelAccepted2) && 
/* 345 */       (!duelAccepted)) {
/* 346 */       duelAccepted = true;
/* 347 */       EjectTimer = 20;
/* 348 */       cont1.sendMessage(ChatColor.GREEN + ChatColor.BOLD + 
/* 349 */         "DUEL ACCEPTED!");
/* 350 */       cont1.sendMessage(ChatColor.GREEN + ChatColor.BOLD + 
/* 351 */         "DUEL ACCEPTED!");
/* 352 */       startDuel();
/*     */     }
/* 354 */     if (EjectTimer == 1) {
/* 355 */       if (cont1 != null)
/* 356 */         eject(cont1);
/* 357 */       if (cont2 != null)
/* 358 */         eject(cont2);
/* 359 */       cancelDuel();
/* 360 */       EjectTimer = 0;
/*     */     }
/* 362 */     if (EjectTimer > 0) {
/* 363 */       EjectTimer -= 1;
/*     */     }
/* 365 */     if (bidTimer > 0) {
/* 366 */       bidTimer -= 1;
/* 367 */       if (bidTimer == 0) {
/* 368 */         cont1.sendMessage(ChatColor.YELLOW + 
/* 369 */           "[" + 
/* 370 */           ChatColor.LIGHT_PURPLE + 
/* 371 */           "DUEL" + 
/* 372 */           ChatColor.YELLOW + 
/* 373 */           "]" + 
/* 374 */           ChatColor.LIGHT_PURPLE + 
/* 375 */           ":" + 
/* 376 */           ChatColor.RED + 
/* 377 */           cont1.getName() + 
/* 378 */           ChatColor.GREEN + 
/* 379 */           " Duel canceled, no bid amount entered please try again!");
/* 380 */         cont2.sendMessage(ChatColor.YELLOW + 
/* 381 */           "[" + 
/* 382 */           ChatColor.LIGHT_PURPLE + 
/* 383 */           "DUEL" + 
/* 384 */           ChatColor.YELLOW + 
/* 385 */           "]" + 
/* 386 */           ChatColor.LIGHT_PURPLE + 
/* 387 */           ":" + 
/* 388 */           ChatColor.RED + 
/* 389 */           cont2.getName() + 
/* 390 */           ChatColor.GREEN + 
/* 391 */           " Duel canceled, no bid amount entered please try again!");
/* 392 */         cancelDuel();
/*     */       }
/*     */     }
/* 395 */     if (acceptTimer > 1)
/* 396 */       acceptTimer -= 1;
/*     */   }
/*     */ 
/*     */   private static void win(Player winner)
/*     */   {
/*     */   }
/*     */ 
/*     */   private static void eject(Player player) {
/* 404 */     if (player != null)
/* 405 */       player.teleport(ejectLoc);
/*     */   }
/*     */ 
/*     */   private static void startDuel() {
/* 409 */     cancelled = false;
/* 410 */     cont1pvp = PvpHandler.getPvpPlayer(cont1);
/* 411 */     cont2pvp = PvpHandler.getPvpPlayer(cont2);
/* 412 */     cont1pvp.setDuelZone(true);
/* 413 */     cont2pvp.setDuelZone(true);
/* 414 */     if (readyCheck()) {
/* 415 */       inprogress = true;
/* 416 */       Bukkit.broadcastMessage(ChatColor.YELLOW + "[" + 
/* 417 */         ChatColor.LIGHT_PURPLE + "DUEL" + ChatColor.YELLOW + "] " + 
/* 418 */         ChatColor.LIGHT_PURPLE + ":" + ChatColor.RED + 
/* 419 */         cont1.getName() + ChatColor.YELLOW + " AND " + 
/* 420 */         ChatColor.RED + cont2.getName() + ChatColor.YELLOW + 
/* 421 */         " have started a duel! Go in the portal at " + 
/* 422 */         ChatColor.GREEN + "/spawn" + ChatColor.YELLOW + 
/* 423 */         " to watch enter the " + ChatColor.GOLD + "GOLDEN" + 
/* 424 */         ChatColor.YELLOW + " gate at spawn");
/*     */ 
/* 426 */       PvpBalance.economy.withdrawPlayer(cont1.getName(), bet);
/* 427 */       PvpBalance.economy.withdrawPlayer(cont2.getName(), bet);
/* 428 */       winnings = bet * 2;
/* 429 */       winnings -= winnings / 10;
/* 430 */       EjectTimer = 500;
/* 431 */       cont1.teleport(new Location(cont1.getWorld(), -633.0D, 77.0D, 334.0D));
/* 432 */       cont2.teleport(new Location(cont2.getWorld(), -592.0D, 77.0D, 334.0D));
/* 433 */       cont1pvp.setDuelContestant(true);
/* 434 */       cont2pvp.setDuelContestant(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean readyCheck() {
/* 439 */     if ((cont1 == null) || (cont2 == null)) {
/* 440 */       return false;
/*     */     }
/* 442 */     if (!betAccepted) {
/* 443 */       return false;
/*     */     }
/* 445 */     if (!checkBet(bet)) {
/* 446 */       return false;
/*     */     }
/* 448 */     return true;
/*     */   }
/*     */ 
/*     */   public static void acceptDuel(Player player) {
/* 452 */     if (player.getName() == cont1.getName()) {
/* 453 */       duelAccepted1 = true;
/*     */     }
/* 455 */     if (player.getName() == cont2.getName())
/* 456 */       duelAccepted2 = true;
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     DuelZone.Duel
 * JD-Core Version:    0.6.2
 */