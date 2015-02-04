/*     */ package PvpBalance;
/*     */ 
/*     */ import DuelZone.Duel;
/*     */ import Event.CrazyRace;
/*     */ import Event.EventRunner;
/*     */ import Event.SkyArrow;
/*     */ import Party.CommandParty;
/*     */ import Party.PartyListener;
/*     */ import SaveLoad.LoadSave;
/*     */ import SaveLoad.Save;
/*     */ import Util.ItemUtils;
/*     */ import Util.MYSQLManager;
/*     */ import com.palmergames.bukkit.towny.Towny;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import net.milkbowl.vault.economy.Economy;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Chicken;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.RegisteredServiceProvider;
/*     */ import org.bukkit.plugin.ServicesManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.plugin.messaging.Messenger;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class PvpBalance extends JavaPlugin
/*     */ {
/*     */   public static MYSQLManager mysql;
/*  40 */   public static final Logger logger = Logger.getLogger("Minecraft");
/*     */   public static PvpBalance plugin;
/*     */   public static Plugin townyPlugin;
/*     */   public static Towny towny;
/*     */   public LoadSave LoadSave;
/*  45 */   private final float SPEED = 0.2F;
/*  46 */   private final int AMOUNT = 300;
/*  47 */   private static int everyOther = 0;
/*  48 */   private boolean debug = false;
/*  49 */   private static List<Chicken> chickens = new ArrayList();
/*  50 */   public static List<Player> particulating = new ArrayList();
/*  51 */   public static List<Duel> duel = new ArrayList();
/*  52 */   private boolean faction = false;
/*  53 */   public static Economy economy = null;
/*     */   private Save sDamage;
/*     */   private Save protection;
/*  56 */   public boolean logDB = true;
/*     */ 
/*     */   public void onDisable()
/*     */   {
/*  63 */     for (PVPPlayer pp : PvpHandler.getPvpPlayers())
/*     */     {
/*  65 */       if (pp != null)
/*     */       {
/*  67 */         pp.setCombatCoolDown(0);
/*     */       }
/*     */     }
/*  69 */     PvpHandler.clear();
/*  70 */     PluginDescriptionFile pdfFile = getDescription();
/*  71 */     logger.info(pdfFile.getName() + " Has Been Disabled!!");
/*  72 */     mysql.closeDB();
/*     */   }
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*  77 */     plugin = this;
/*  78 */     townyPlugin = Bukkit.getPluginManager().getPlugin("Towny");
/*  79 */     towny = (Towny)townyPlugin;
/*  80 */     for (Player p : Bukkit.getOnlinePlayers())
/*     */     {
/*  82 */       if (p != null)
/*     */       {
/*  84 */         createNewPvPPlayer(p);
/*     */       }
/*     */     }
/*  87 */     setupEconomy();
/*  88 */     PvpHandler.load();
/*  89 */     mysql = new MYSQLManager(this);
/*  90 */     this.sDamage = new Save(this, "Damage.yml");
/*  91 */     this.protection = new Save(this, "Protection.yml");
/*  92 */     this.LoadSave = new LoadSave(this);
/*  93 */     getCommand("party").setExecutor(new CommandParty(this));
/*     */ 
/*  95 */     Bukkit.getMessenger().registerOutgoingPluginChannel(this, "Scoreboard");
/*     */ 
/*  97 */     Damage.LoadSave = this.LoadSave;
/*     */ 
/*  99 */     getServer().getPluginManager().registerEvents(new DBZListener(this, this.LoadSave), this);
/* 100 */     getServer().getPluginManager().registerEvents(new PartyListener(), this);
/* 101 */     getServer().getPluginManager().registerEvents(new PvpListener(), this);
/*     */ 
/* 103 */     PluginDescriptionFile pdfFile = getDescription();
/*     */     try {
/* 105 */       mysql.setupDb();
/*     */     } catch (SQLException e) {
/* 107 */       this.logDB = false;
/* 108 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 112 */     getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 117 */         if (PvpBalance.everyOther == 0)
/*     */         {
/* 119 */           PvpBalance.everyOther = 1;
/* 120 */           PvpBalance.this.particulating();
/* 121 */           EventRunner.tick();
/* 122 */           for (Player all : Bukkit.getServer().getOnlinePlayers())
/*     */           {
/*     */             try
/*     */             {
/* 128 */               if (all.getFireTicks() > 1) {
/* 129 */                 Effects.igniteFirePlayers(all);
/*     */               }
/*     */ 
/* 132 */               if ((all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) || (all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE)))
/*     */               {
/* 134 */                 Effects.effectSharpnessPlayers(all);
/*     */               }
/*     */ 
/* 137 */               if (all.getActivePotionEffects().toString().contains("CONFUSION"))
/*     */               {
/* 139 */                 Effects.effectConfuse(all);
/*     */               }
/*     */ 
/* 142 */               if (all.getActivePotionEffects().toString().contains("WITHER"))
/*     */               {
/* 144 */                 Effects.effectWither(all);
/*     */               }
/*     */ 
/* 147 */               if (all.getActivePotionEffects().toString().contains("POISON"))
/*     */               {
/* 149 */                 Effects.effectPoison(all);
/*     */               }
/*     */ 
/* 152 */               if (all.getActivePotionEffects().toString().contains("Blindness"))
/*     */               {
/* 154 */                 Effects.effectBlind(all);
/*     */               }
/*     */ 
/* 157 */               if (all.getActivePotionEffects().toString().contains("SPEED"))
/*     */               {
/* 159 */                 Effects.effectSpeedPlayers(all, 0.2F, 300);
/*     */               }
/* 161 */               if (all.getActivePotionEffects().toString().contains("SLOW"))
/*     */               {
/* 163 */                 Effects.effectSlow(all);
/*     */               }
/*     */ 
/* 166 */               if (all.getActivePotionEffects().toString().contains("REGENERATION"))
/*     */               {
/* 168 */                 Effects.effectHealthPlayers(all, 0.3F, 30);
/* 169 */                 PVPPlayer pvp = PvpHandler.getPvpPlayer(all);
/* 170 */                 pvp.sethealth(pvp.gethealth() + LoadSave.Regen);
/*     */               }
/*     */ 
/* 173 */               if (all.getHealth() < 9.0D)
/*     */               {
/* 175 */                 Effects.bleed(all);
/*     */               }
/*     */ 
/*     */             }
/*     */             catch (Exception e1)
/*     */             {
/* 182 */               e1.printStackTrace();
/* 183 */               PvpBalance.logger.info("Main Effect!");
/*     */             }
/*     */           }
/*     */         }
/* 187 */         else if (PvpBalance.everyOther == 1)
/*     */         {
/* 189 */           PvpBalance.everyOther = 2;
/* 190 */           PvpBalance.this.particulating();
/*     */ 
/* 192 */           for (Player all : Bukkit.getServer().getOnlinePlayers())
/*     */           {
/* 194 */             if (all != null)
/*     */             {
/*     */               try
/*     */               {
/* 198 */                 ArmorEffects.checkForGlowTick(all);
/*     */               }
/*     */               catch (Exception e1)
/*     */               {
/* 203 */                 e1.printStackTrace();
/* 204 */                 PvpBalance.logger.info("Main ArmorEffect!");
/*     */               }
/*     */             }
/*     */           }
/* 208 */           for (Player all : Bukkit.getServer().getOnlinePlayers())
/*     */           {
/*     */             try
/*     */             {
/* 214 */               if (all.getFireTicks() > 1) {
/* 215 */                 Effects.igniteFirePlayers(all);
/*     */               }
/*     */ 
/* 218 */               if ((all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) || (all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE)))
/*     */               {
/* 220 */                 Effects.effectSharpnessPlayers(all);
/*     */               }
/*     */ 
/* 223 */               if (all.getActivePotionEffects().toString().contains("CONFUSION"))
/*     */               {
/* 225 */                 Effects.effectConfuse(all);
/*     */               }
/*     */ 
/* 228 */               if (all.getActivePotionEffects().toString().contains("WITHER"))
/*     */               {
/* 230 */                 Effects.effectWither(all);
/*     */               }
/*     */ 
/* 233 */               if (all.getActivePotionEffects().toString().contains("POISON"))
/*     */               {
/* 235 */                 Effects.effectPoison(all);
/*     */               }
/*     */ 
/* 238 */               if (all.getActivePotionEffects().toString().contains("Blindness"))
/*     */               {
/* 240 */                 Effects.effectBlind(all);
/*     */               }
/*     */ 
/* 243 */               if (all.getActivePotionEffects().toString().contains("SPEED"))
/*     */               {
/* 245 */                 Effects.effectSpeedPlayers(all, 0.2F, 300);
/*     */               }
/* 247 */               if (all.getActivePotionEffects().toString().contains("SLOW"))
/*     */               {
/* 249 */                 Effects.effectSlow(all);
/*     */               }
/*     */ 
/* 254 */               if (all.getHealth() < 9.0D)
/*     */               {
/* 256 */                 Effects.bleed(all);
/*     */               }
/*     */ 
/*     */             }
/*     */             catch (Exception e1)
/*     */             {
/* 263 */               e1.printStackTrace();
/* 264 */               PvpBalance.logger.info("Main Effect!");
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/* 269 */         else if (PvpBalance.everyOther == 2)
/*     */         {
/* 271 */           for (Chicken chick : PvpBalance.chickens)
/*     */           {
/* 273 */             if (chick.getPassenger() == null)
/*     */             {
/* 275 */               chick.remove();
/* 276 */               PvpBalance.chickenList().remove(chick);
/*     */             }
/*     */           }
/* 279 */           PvpBalance.everyOther = 3;
/* 280 */           PvpBalance.this.particulating();
/* 281 */           for (Player all : Bukkit.getServer().getOnlinePlayers())
/*     */           {
/*     */             try
/*     */             {
/* 287 */               if (all.getFireTicks() > 1) {
/* 288 */                 Effects.igniteFirePlayers(all);
/*     */               }
/*     */ 
/* 291 */               if ((all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) || (all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE)))
/*     */               {
/* 293 */                 Effects.effectSharpnessPlayers(all);
/*     */               }
/*     */ 
/* 296 */               if (all.getActivePotionEffects().toString().contains("CONFUSION"))
/*     */               {
/* 298 */                 Effects.effectConfuse(all);
/*     */               }
/*     */ 
/* 301 */               if (all.getActivePotionEffects().toString().contains("WITHER"))
/*     */               {
/* 303 */                 Effects.effectWither(all);
/*     */               }
/*     */ 
/* 306 */               if (all.getActivePotionEffects().toString().contains("POISON"))
/*     */               {
/* 308 */                 Effects.effectPoison(all);
/*     */               }
/*     */ 
/* 311 */               if (all.getActivePotionEffects().toString().contains("Blindness"))
/*     */               {
/* 313 */                 Effects.effectBlind(all);
/*     */               }
/*     */ 
/* 316 */               if (all.getActivePotionEffects().toString().contains("SPEED"))
/*     */               {
/* 318 */                 Effects.effectSpeedPlayers(all, 0.2F, 300);
/*     */               }
/* 320 */               if (all.getActivePotionEffects().toString().contains("SLOW"))
/*     */               {
/* 322 */                 Effects.effectSlow(all);
/*     */               }
/*     */ 
/* 327 */               if (all.getHealth() < 9.0D)
/*     */               {
/* 329 */                 Effects.bleed(all);
/*     */               }
/*     */ 
/*     */             }
/*     */             catch (Exception e1)
/*     */             {
/* 336 */               e1.printStackTrace();
/* 337 */               PvpBalance.logger.info("Main Effect!");
/*     */             }
/*     */           }
/*     */         }
/* 341 */         else if (PvpBalance.everyOther == 3)
/*     */         {
/* 343 */           PvpBalance.everyOther = 0;
/* 344 */           PvpBalance.this.particulating();
/* 345 */           Duel.tick();
/* 346 */           for (PVPPlayer all : PvpHandler.getPvpPlayers())
/*     */           {
/*     */             try
/*     */             {
/* 350 */               all.tick();
/*     */             }
/*     */             catch (Exception e1)
/*     */             {
/* 354 */               e1.printStackTrace();
/* 355 */               PvpBalance.logger.info("Main Tick!");
/*     */             }
/*     */           }
/* 358 */           for (Player all : Bukkit.getServer().getOnlinePlayers())
/*     */           {
/*     */             try
/*     */             {
/* 364 */               if (all.getFireTicks() > 1) {
/* 365 */                 Effects.igniteFirePlayers(all);
/*     */               }
/*     */ 
/* 368 */               if ((all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) || (all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE)))
/*     */               {
/* 370 */                 Effects.effectSharpnessPlayers(all);
/*     */               }
/*     */ 
/* 373 */               if (all.getActivePotionEffects().toString().contains("CONFUSION"))
/*     */               {
/* 375 */                 Effects.effectConfuse(all);
/*     */               }
/*     */ 
/* 378 */               if (all.getActivePotionEffects().toString().contains("WITHER"))
/*     */               {
/* 380 */                 Effects.effectWither(all);
/*     */               }
/*     */ 
/* 383 */               if (all.getActivePotionEffects().toString().contains("POISON"))
/*     */               {
/* 385 */                 Effects.effectPoison(all);
/*     */               }
/*     */ 
/* 388 */               if (all.getActivePotionEffects().toString().contains("Blindness"))
/*     */               {
/* 390 */                 Effects.effectBlind(all);
/*     */               }
/*     */ 
/* 393 */               if (all.getActivePotionEffects().toString().contains("SPEED"))
/*     */               {
/* 395 */                 Effects.effectSpeedPlayers(all, 0.2F, 300);
/*     */               }
/* 397 */               if (all.getActivePotionEffects().toString().contains("SLOW"))
/*     */               {
/* 399 */                 Effects.effectSlow(all);
/*     */               }
/*     */ 
/* 403 */               if (all.isSprinting())
/*     */               {
/* 405 */                 Effects.effectSprintPlayers(all, 0.2F, 0);
/*     */               }
/*     */ 
/* 408 */               if (all.getHealth() < 9.0D)
/*     */               {
/* 410 */                 Effects.bleed(all);
/*     */               }
/*     */ 
/*     */             }
/*     */             catch (Exception e1)
/*     */             {
/* 417 */               e1.printStackTrace();
/* 418 */               PvpBalance.logger.info("Main Effect!");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     , 0L, 5L);
/*     */ 
/* 427 */     logger.info(pdfFile.getName() + " Has Been Enabled!!");
/*     */   }
/*     */   private void particulating() {
/* 430 */     for (Player player : particulating) {
/* 431 */       createParticleEffect(player);
/* 432 */       if (!player.isOnline())
/* 433 */         particulating.remove(player);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isDebug()
/*     */   {
/* 440 */     return this.debug;
/*     */   }
/*     */ 
/*     */   public void setDebug(boolean value)
/*     */   {
/* 445 */     this.debug = value;
/*     */   }
/*     */ 
/*     */   public boolean hasFaction()
/*     */   {
/* 450 */     return this.faction;
/*     */   }
/*     */ 
/*     */   public Save getSDamage()
/*     */   {
/* 456 */     return this.sDamage;
/*     */   }
/*     */ 
/*     */   public Save getProtection()
/*     */   {
/* 461 */     return this.protection;
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
/*     */   {
/* 467 */     Player player = (Player)sender;
/* 468 */     if ((commandLabel.equalsIgnoreCase("bet")) && 
/* 469 */       (args[0] != null) && 
/* 470 */       (Duel.bet < 2500) && 
/* 471 */       (Duel.checkContestant(player)) && (Duel.has2players) && (!Duel.betAccepted)) {
/* 472 */       int bet = Integer.parseInt(args[0]);
/* 473 */       Duel.setBet(bet);
/*     */     }
/*     */ 
/* 478 */     if (commandLabel.equalsIgnoreCase("acceptbet")) {
/* 479 */       Duel.acceptBet(player);
/*     */     }
/* 481 */     if (commandLabel.equalsIgnoreCase("acceptduel")) {
/* 482 */       Duel.acceptDuel(player);
/*     */     }
/* 484 */     if ((commandLabel.equalsIgnoreCase("pvpdebug")) && (player.hasPermission("particles.admin")))
/*     */     {
/* 486 */       if (!this.debug)
/*     */       {
/* 488 */         this.debug = true;
/*     */       }
/*     */       else
/* 491 */         this.debug = false;
/* 492 */       if (PvpHandler.getPvpPlayer(player) == null)
/*     */       {
/* 494 */         createNewPvPPlayer(player);
/*     */       }
/*     */     }
/* 497 */     else if ((commandLabel.equalsIgnoreCase("pvpgod")) && (player.hasPermission("particles.admin")))
/*     */     {
/* 499 */       PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
/* 500 */       if (PVPPlayer.isGod())
/*     */       {
/* 502 */         PVPPlayer.setGod(false);
/* 503 */         player.sendMessage(ChatColor.RED + ChatColor.BOLD + "GOD MODE DISABLED + Inventory STORED!");
/*     */       }
/*     */       else
/*     */       {
/* 507 */         PVPPlayer.setGod(true);
/* 508 */         player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "GOD MODE ENABLED + Inventory RETRIEVED!");
/* 509 */         player.setFoodLevel(20);
/* 510 */         PVPPlayer.sethealth(PVPPlayer.getMaxHealth());
/*     */       }
/*     */     }
/* 513 */     else if (commandLabel.equalsIgnoreCase("play")) {
/* 514 */       if (player.getGameMode() != GameMode.SURVIVAL) {
/* 515 */         player.sendMessage(ChatColor.RED + "YOU ARE NOT IN SURVIVAL MODE!");
/* 516 */         return true;
/*     */       }
/* 518 */       if (!EventRunner.participants.contains(player)) {
/* 519 */         EventRunner.joinEvent(player);
/*     */       }
/*     */       else {
/* 522 */         player.sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU ARE PLAYING!");
/* 523 */         if (EventRunner.getActiveEvent().equalsIgnoreCase(SkyArrow.getEventName())) {
/* 524 */           player.sendMessage("PLAYERS REMAINING = " + SkyArrow.players.size());
/* 525 */           int counter = 0;
/* 526 */           for (Player player2 : SkyArrow.players) {
/* 527 */             counter++;
/* 528 */             player.sendMessage(counter + ": " + player2.getName());
/*     */           }
/* 530 */           return true;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/* 535 */     else if ((commandLabel.equalsIgnoreCase("startevent")) && (player.isOp())) {
/* 536 */       if (EventRunner.getTick() < 3600) {
/* 537 */         EventRunner.setTick(3598);
/*     */       }
/*     */     }
/* 540 */     else if (commandLabel.equalsIgnoreCase("leave")) {
/* 541 */       if (EventRunner.participants.contains(player))
/*     */       {
/* 544 */         if (EventRunner.getActiveEvent().equalsIgnoreCase(SkyArrow.getEventName())) {
/* 545 */           SkyArrow.leave(player);
/*     */         }
/* 547 */         if (EventRunner.getActiveEvent().equalsIgnoreCase(CrazyRace.getEventName())) {
/* 548 */           CrazyRace.leave(player);
/*     */         }
/* 550 */         player.sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU FORFEIT THE EVENT!");
/*     */       }
/*     */     }
/* 553 */     else if ((commandLabel.equalsIgnoreCase("pvpver")) && (player.hasPermission("particles.admin")))
/*     */     {
/* 556 */       ItemStack paper = new ItemStack(Material.PAPER);
/* 557 */       List lore = new ArrayList();
/* 558 */       lore.add("Polishing Cloth " + ChatColor.MAGIC + " " + "32157");
/* 559 */       lore.add(ChatColor.GREEN + ChatColor.BOLD + "/rules polish");
/* 560 */       ItemUtils.setLore(paper, lore);
/* 561 */       ItemUtils.setName(paper, ChatColor.GOLD + "Polishing Cloth");
/* 562 */       player.getInventory().addItem(new ItemStack[] { paper });
/* 563 */       player.sendMessage(ChatColor.GREEN + "[Armor Polish]: Greetings Administrator " + player.getDisplayName() + " a cloth has been provided");
/* 564 */       player.getInventory().addItem(new ItemStack[] { paper });
/* 565 */       player.sendMessage("VERSION 1.6");
/* 566 */       LoadSave.reloadValues(this, player);
/* 567 */       player.setFoodLevel(0);
/*     */     }
/* 569 */     else if (commandLabel.equalsIgnoreCase("polish"))
/*     */     {
/* 571 */       ArmorEffects.polish(player);
/*     */     }
/* 573 */     else if ((commandLabel.equalsIgnoreCase("effect")) && (player.hasPermission("pvpbalance.particles2"))) {
/* 574 */       player.sendMessage(ChatColor.GREEN + "============= EFFECTS MENU ============");
/* 575 */       player.sendMessage(ChatColor.GREEN + "say /effecttype to change type");
/* 576 */       player.sendMessage(ChatColor.GREEN + "say /effectspeed to change speed");
/* 577 */       player.sendMessage(ChatColor.GREEN + "say /effectcount to change count");
/* 578 */       player.sendMessage(ChatColor.GREEN + "say /effectheight to change height");
/* 579 */       player.sendMessage(ChatColor.GREEN + "say /effectreset to set to none");
/*     */     }
/* 581 */     else if ((commandLabel.equalsIgnoreCase("effectreset")) && (player.hasPermission("pvpbalance.particles2"))) {
/* 582 */       particulating.remove(player);
/* 583 */       PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 584 */       pvp.setParticleCount(0);
/* 585 */       pvp.setParticleEffect("");
/* 586 */       pvp.setParticleSpeed(0.0D);
/*     */     }
/* 588 */     else if ((commandLabel.equalsIgnoreCase("effecttype")) && (player.hasPermission("pvpbalance.particles2"))) {
/* 589 */       PlayerParticles.nextParticle(player);
/* 590 */       player.sendMessage(ChatColor.GREEN + "type changed to " + ChatColor.DARK_PURPLE + PvpHandler.getPvpPlayer(player).getParticleEffect());
/*     */     }
/* 592 */     else if ((commandLabel.equalsIgnoreCase("endduel")) && (player.hasPermission("pvpbalance.op"))) {
/* 593 */       Duel.cancelDuel();
/* 594 */       player.sendMessage(ChatColor.YELLOW + "INFO =======================");
/* 595 */       if (Duel.cont1pvp != null)
/* 596 */         player.sendMessage(ChatColor.GREEN + "Contestant1 = " + Duel.cont1pvp.getPlayer().getName());
/* 597 */       if (Duel.cont2pvp != null)
/* 598 */         player.sendMessage(ChatColor.GREEN + "Contestant2 = " + Duel.cont2pvp.getPlayer().getName());
/* 599 */       player.sendMessage(ChatColor.GREEN + "EjectTimer = " + Duel.EjectTimer);
/* 600 */       player.sendMessage(ChatColor.GREEN + "Bet ammount = " + Duel.bet);
/* 601 */       player.sendMessage(ChatColor.GREEN + "DUEL CANNCELED");
/* 602 */       player.sendMessage(ChatColor.YELLOW + "============================");
/* 603 */       Duel.cancelDuel();
/*     */     }
/* 605 */     else if ((commandLabel.equalsIgnoreCase("effectheight")) && (player.hasPermission("pvpbalance.particles2"))) {
/* 606 */       PlayerParticles.incrimentParticleHeight(player);
/* 607 */       player.sendMessage(ChatColor.GREEN + "height changed to " + ChatColor.DARK_PURPLE + PvpHandler.getPvpPlayer(player).getParticleHeight());
/*     */     }
/* 609 */     else if ((commandLabel.equalsIgnoreCase("effectspeed")) && (player.hasPermission("pvpbalance.particles2"))) {
/* 610 */       PlayerParticles.incrimentParticleSpeed(player);
/* 611 */       player.sendMessage(ChatColor.GREEN + "speed changed to " + ChatColor.DARK_PURPLE + PvpHandler.getPvpPlayer(player).getParticleSpeed());
/*     */     }
/* 613 */     else if ((commandLabel.equalsIgnoreCase("effectcount")) && (player.hasPermission("pvpbalance.particles2"))) {
/* 614 */       PlayerParticles.incrimentParticleCount(player);
/* 615 */       player.sendMessage(ChatColor.GREEN + "speed changed to " + ChatColor.DARK_PURPLE + PvpHandler.getPvpPlayer(player).getParticleCount());
/*     */     } else {
/* 617 */       if (args.length > 0) {
/* 618 */         if ((!commandLabel.equalsIgnoreCase("pvpstats")) || (args[0] == null)) break label3226;
/*     */         try {
/* 620 */           if (mysql.getUserData(args[0].toString(), "Kills") != -1) {
/* 621 */             int epicKills = 0;
/* 622 */             int epicDeaths = 0;
/* 623 */             int duelsWon = 0;
/* 624 */             int duelsLost = 0;
/* 625 */             int kills = 0;
/* 626 */             int deaths = 0;
/*     */             try {
/* 628 */               epicKills = mysql.getUserData(args[0].toString(), "EpicKills");
/*     */             } catch (SQLException e5) {
/* 630 */               e5.printStackTrace();
/*     */             }
/*     */             try {
/* 633 */               epicDeaths = mysql.getUserData(args[0].toString(), "EpicDeaths");
/*     */             } catch (SQLException e4) {
/* 635 */               e4.printStackTrace();
/*     */             }
/*     */             try {
/* 638 */               duelsWon = mysql.getUserData(args[0].toString(), "DuelsWon");
/*     */             } catch (SQLException e3) {
/* 640 */               e3.printStackTrace();
/*     */             }
/*     */             try {
/* 643 */               duelsLost = mysql.getUserData(args[0].toString(), "DuelsLost");
/*     */             } catch (SQLException e2) {
/* 645 */               e2.printStackTrace();
/*     */             }
/*     */             try {
/* 648 */               kills = mysql.getUserData(args[0].toString(), "Kills");
/*     */             } catch (SQLException e1) {
/* 650 */               e1.printStackTrace();
/*     */             }
/*     */             try {
/* 653 */               deaths = mysql.getUserData(args[0].toString(), "Deaths");
/*     */             } catch (SQLException e) {
/* 655 */               e.printStackTrace();
/*     */             }
/* 657 */             player.sendMessage(ChatColor.BLUE + "======= PVP STATS =======");
/* 658 */             player.sendMessage(ChatColor.GREEN + "Name" + ChatColor.YELLOW + ":" + ChatColor.WHITE + args[0].toString());
/* 659 */             player.sendMessage(ChatColor.GREEN + "Kills" + ChatColor.YELLOW + ":" + ChatColor.WHITE + kills);
/* 660 */             player.sendMessage(ChatColor.GREEN + "Deaths" + ChatColor.YELLOW + ":" + ChatColor.WHITE + deaths);
/* 661 */             player.sendMessage(ChatColor.GREEN + "Epic Kills" + ChatColor.YELLOW + ":" + ChatColor.WHITE + epicKills);
/* 662 */             player.sendMessage(ChatColor.GREEN + "Epic Deaths" + ChatColor.YELLOW + ":" + ChatColor.WHITE + epicDeaths);
/* 663 */             player.sendMessage(ChatColor.GREEN + "Duels Won" + ChatColor.YELLOW + ":" + ChatColor.WHITE + duelsWon);
/* 664 */             player.sendMessage(ChatColor.GREEN + "Duels Lost" + ChatColor.YELLOW + ":" + ChatColor.WHITE + duelsLost);
/* 665 */             player.sendMessage(ChatColor.BLUE + "=========================");
/* 666 */             return true;
/*     */           }
/*     */         }
/*     */         catch (SQLException localSQLException1)
/*     */         {
/* 671 */           player.sendMessage(ChatColor.BLUE + "======= PVP STATS =======");
/* 672 */           player.sendMessage(ChatColor.RED + "Error: " + args[0].toString() + " not in database!");
/* 673 */           player.sendMessage(ChatColor.RED + "PLEASE TRY AGAIN");
/* 674 */           player.sendMessage(ChatColor.BLUE + "=========================");
/* 675 */           return true;
/*     */         }
/*     */       }
/* 678 */       if (commandLabel.equalsIgnoreCase("pvpstats"))
/*     */       {
/* 680 */         int epicKills = 0;
/* 681 */         int epicDeaths = 0;
/* 682 */         int duelsWon = 0;
/* 683 */         int duelsLost = 0;
/* 684 */         int kills = 0;
/* 685 */         int deaths = 0;
/*     */         try {
/* 687 */           epicKills = mysql.getUserData(player, "EpicKills");
/*     */         } catch (SQLException e5) {
/* 689 */           e5.printStackTrace();
/*     */         }
/*     */         try {
/* 692 */           epicDeaths = mysql.getUserData(player, "EpicDeaths");
/*     */         } catch (SQLException e4) {
/* 694 */           e4.printStackTrace();
/*     */         }
/*     */         try {
/* 697 */           duelsWon = mysql.getUserData(player, "DuelsWon");
/*     */         } catch (SQLException e3) {
/* 699 */           e3.printStackTrace();
/*     */         }
/*     */         try {
/* 702 */           duelsLost = mysql.getUserData(player, "DuelsLost");
/*     */         } catch (SQLException e2) {
/* 704 */           e2.printStackTrace();
/*     */         }
/*     */         try {
/* 707 */           kills = mysql.getUserData(player, "Kills");
/*     */         } catch (SQLException e1) {
/* 709 */           e1.printStackTrace();
/*     */         }
/*     */         try {
/* 712 */           deaths = mysql.getUserData(player, "Deaths");
/*     */         } catch (SQLException e) {
/* 714 */           e.printStackTrace();
/*     */         }
/* 716 */         player.sendMessage(ChatColor.BLUE + "======= PVP STATS =======");
/* 717 */         player.sendMessage(ChatColor.GREEN + "Name" + ChatColor.YELLOW + ":" + ChatColor.WHITE + player.getDisplayName());
/* 718 */         player.sendMessage(ChatColor.GREEN + "Kills" + ChatColor.YELLOW + ":" + ChatColor.WHITE + kills);
/* 719 */         player.sendMessage(ChatColor.GREEN + "Deaths" + ChatColor.YELLOW + ":" + ChatColor.WHITE + deaths);
/* 720 */         player.sendMessage(ChatColor.GREEN + "Epic Kills" + ChatColor.YELLOW + ":" + ChatColor.WHITE + epicKills);
/* 721 */         player.sendMessage(ChatColor.GREEN + "Epic Deaths" + ChatColor.YELLOW + ":" + ChatColor.WHITE + epicDeaths);
/* 722 */         player.sendMessage(ChatColor.GREEN + "Duels Won" + ChatColor.YELLOW + ":" + ChatColor.WHITE + duelsWon);
/* 723 */         player.sendMessage(ChatColor.GREEN + "Duels Lost" + ChatColor.YELLOW + ":" + ChatColor.WHITE + duelsLost);
/* 724 */         player.sendMessage(ChatColor.BLUE + "=========================");
/*     */       }
/* 726 */       else if (commandLabel.equalsIgnoreCase("pvptop")) {
/*     */         try {
/* 728 */           mysql.top10(player);
/*     */         } catch (SQLException e) {
/* 730 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 733 */     label3226: return true;
/*     */   }
/*     */ 
/*     */   public static List<Chicken> chickenList()
/*     */   {
/* 738 */     return chickens;
/*     */   }
/*     */   public void createParticleEffect(Player player) {
/* 741 */     if (!player.isOnline()) {
/* 742 */       particulating.remove(player);
/* 743 */       return;
/*     */     }
/* 745 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 746 */     if ((pvp.getParticleCount() != 0) && 
/* 747 */       (pvp.getParticleEffect() != "") && 
/* 748 */       (pvp.getEffect() != null) && 
/* 749 */       (pvp.getParticleSpeed() != 0.0D))
/*     */       try {
/* 751 */         ParticleEffect.sendToLocation(pvp.getEffect(), player.getLocation().add(0.0D, pvp.getParticleHeight(), 0.0D), 0.2F, 0.2F, 0.2F, (float)pvp.getParticleSpeed(), pvp.getParticleCount());
/*     */       } catch (Exception e) {
/* 753 */         e.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   private boolean setupEconomy()
/*     */   {
/* 762 */     RegisteredServiceProvider economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
/* 763 */     if (economyProvider != null)
/*     */     {
/* 765 */       economy = (Economy)economyProvider.getProvider();
/*     */     }
/* 767 */     return economy != null;
/*     */   }
/*     */   public static Plugin getPlugin() {
/* 770 */     return plugin;
/*     */   }
/*     */   public static MYSQLManager getMYSQL() {
/* 773 */     return mysql;
/*     */   }
/*     */   public static void createNewPvPPlayer(Player player) {
/* 776 */     PVPPlayer newPVP = new PVPPlayer(player);
/* 777 */     PvpHandler.addPvpPlayer(newPVP);
/*     */     try {
/* 779 */       mysql.storeUserData(player, "EpicKills", mysql.getUserData(player, "EpicKills"));
/* 780 */       mysql.storeUserData(player, "EpicDeaths", mysql.getUserData(player, "EpicDeaths"));
/* 781 */       mysql.storeUserData(player, "DuelsWon", mysql.getUserData(player, "DuelsWon"));
/* 782 */       mysql.storeUserData(player, "DuelsLost", mysql.getUserData(player, "DuelsLost"));
/* 783 */       mysql.storeUserData(player, "Kills", mysql.getUserData(player, "Kills"));
/* 784 */       mysql.storeUserData(player, "Deaths", mysql.getUserData(player, "Deaths"));
/*     */     }
/*     */     catch (SQLException e) {
/* 787 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/* 791 */   public static Towny getTowny() { return towny; }
/*     */ 
/*     */   public static PVPPlayer createNewPvPPlayerObject(Player player) {
/* 794 */     PVPPlayer newPVP = new PVPPlayer(player);
/* 795 */     PvpHandler.addPvpPlayer(newPVP);
/*     */     try {
/* 797 */       mysql.storeUserData(player, "EpicKills", mysql.getUserData(player, "EpicKills"));
/* 798 */       mysql.storeUserData(player, "EpicDeaths", mysql.getUserData(player, "EpicDeaths"));
/* 799 */       mysql.storeUserData(player, "DuelsWon", mysql.getUserData(player, "DuelsWon"));
/* 800 */       mysql.storeUserData(player, "DuelsLost", mysql.getUserData(player, "DuelsLost"));
/* 801 */       mysql.storeUserData(player, "Kills", mysql.getUserData(player, "Kills"));
/* 802 */       mysql.storeUserData(player, "Deaths", mysql.getUserData(player, "Deaths"));
/*     */     } catch (SQLException e) {
/* 804 */       e.printStackTrace();
/*     */     }
/* 806 */     return newPVP;
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.PvpBalance
 * JD-Core Version:    0.6.2
 */