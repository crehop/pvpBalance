/*      */ package PvpBalance;
/*      */ 
/*      */ import AdditionalEnchants.EnchantManagment;
/*      */ import DuelZone.Duel;
/*      */ import Event.CrazyRace;
/*      */ import Event.EventRunner;
/*      */ import Event.PBEntityDamageEntityEvent;
/*      */ import Event.PBEntityDamageEvent;
/*      */ import Event.PBEntityDeathEvent;
/*      */ import Event.PBEntityRegainHealthEvent;
/*      */ import Event.SkyArrow;
/*      */ import Party.Party;
/*      */ import SaveLoad.LoadSave;
/*      */ import Skills.Incapacitate;
/*      */ import Skills.PileDrive;
/*      */ import Skills.SuperJump;
/*      */ import Skills.SuperSpeed;
/*      */ import Util.MYSQLManager;
/*      */ import Util.Utils;
/*      */ import Util.WildernessProtection;
/*      */ import com.palmergames.bukkit.towny.utils.CombatUtil;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.GameMode;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.enchantments.Enchantment;
/*      */ import org.bukkit.entity.Arrow;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.HumanEntity;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.entity.Projectile;
/*      */ import org.bukkit.event.Event.Result;
/*      */ import org.bukkit.event.EventHandler;
/*      */ import org.bukkit.event.EventPriority;
/*      */ import org.bukkit.event.Listener;
/*      */ import org.bukkit.event.block.Action;
/*      */ import org.bukkit.event.block.BlockBreakEvent;
/*      */ import org.bukkit.event.block.BlockFromToEvent;
/*      */ import org.bukkit.event.block.BlockPlaceEvent;
/*      */ import org.bukkit.event.block.EntityBlockFormEvent;
/*      */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*      */ import org.bukkit.event.entity.EntityDamageEvent;
/*      */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*      */ import org.bukkit.event.entity.EntityDeathEvent;
/*      */ import org.bukkit.event.entity.EntityExplodeEvent;
/*      */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*      */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
/*      */ import org.bukkit.event.entity.EntityShootBowEvent;
/*      */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*      */ import org.bukkit.event.entity.PlayerDeathEvent;
/*      */ import org.bukkit.event.entity.ProjectileHitEvent;
/*      */ import org.bukkit.event.inventory.InventoryClickEvent;
/*      */ import org.bukkit.event.inventory.InventoryType.SlotType;
/*      */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*      */ import org.bukkit.event.player.PlayerGameModeChangeEvent;
/*      */ import org.bukkit.event.player.PlayerInteractEvent;
/*      */ import org.bukkit.event.player.PlayerItemBreakEvent;
/*      */ import org.bukkit.event.player.PlayerJoinEvent;
/*      */ import org.bukkit.event.player.PlayerQuitEvent;
/*      */ import org.bukkit.event.player.PlayerRespawnEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent;
/*      */ import org.bukkit.event.player.PlayerToggleFlightEvent;
/*      */ import org.bukkit.event.player.PlayerToggleSneakEvent;
/*      */ import org.bukkit.event.player.PlayerToggleSprintEvent;
/*      */ import org.bukkit.event.vehicle.VehicleExitEvent;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.PlayerInventory;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.plugin.messaging.Messenger;
/*      */ import org.bukkit.potion.PotionEffect;
/*      */ import org.bukkit.potion.PotionEffectType;
/*      */ import org.bukkit.scheduler.BukkitScheduler;
/*      */ 
/*      */ public class DBZListener
/*      */   implements Listener
/*      */ {
/*   95 */   static int tick = 0;
/*      */   public static PvpBalance plugin;
/*      */   public LoadSave LoadSave;
/*      */ 
/*      */   public DBZListener(PvpBalance instance, LoadSave LoadSave)
/*      */   {
/*  102 */     this.LoadSave = LoadSave;
/*  103 */     plugin = instance;
/*      */   }
/*      */   @EventHandler(priority=EventPriority.HIGHEST)
/*      */   public void antiDestruction(BlockBreakEvent event) {
/*  107 */     if ((!event.isCancelled()) && (event.getBlock().getType() == Material.COBBLESTONE)) {
/*  108 */       event.getBlock().setType(Material.AIR);
/*      */     }
/*  110 */     if ((WildernessProtection.checkForWilderness(event.getPlayer())) && (!event.getPlayer().hasPermission("towny.claimed.alltown.destroy.*")) && 
/*  111 */       (WildernessProtection.checkForProtectedBlock(event.getBlock()))) {
/*  112 */       event.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD + "SURFACE PROTECTION please find stone or a cave to start mining! if you need tools say /ekit mining!");
/*  113 */       event.setCancelled(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void stopSpongeMove(InventoryClickEvent event) {
/*  119 */     if (event.getWhoClicked().getWorld().toString().contains("test"))
/*      */       try {
/*  121 */         if ((event.getInventory().getItem(event.getSlot()).getType() != null) && 
/*  122 */           (event.getInventory().getItem(event.getSlot()).getType() == Material.SPONGE))
/*  123 */           event.setCancelled(true);
/*      */       }
/*      */       catch (NullPointerException localNullPointerException) {
/*      */       }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void inventoryInteractions(PlayerInteractEvent event) {
/*  131 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(event.getPlayer());
/*  132 */     if (((pvp.isInCombat()) || (pvp.getCombatCooldown() > 0) || (pvp.getCooldown() > 0.0D)) && 
/*  133 */       (event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType() == Material.ENDER_CHEST)) {
/*  134 */       event.setCancelled(true);
/*  135 */       pvp.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD + "CANNOT OPEN ENDERCHEST IN COMBAT!");
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void vehicleDismountEvent(PlayerTeleportEvent event)
/*      */   {
/*  142 */     Material check = event.getPlayer().getEyeLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType();
/*  143 */     if ((check == Material.FENCE) || (check == Material.IRON_FENCE) || (check == Material.NETHER_FENCE)) {
/*  144 */       event.setCancelled(true);
/*  145 */       event.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD + "STOP TRYING TO GLITCH!");
/*      */     }
/*  147 */     if (!event.getPlayer().hasPermission("essentials.kick"))
/*      */     {
/*  149 */       Effects.teleportGreen(event.getPlayer());
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void vehicleDismountEvent(VehicleExitEvent event) {
/*  155 */     if (event.getExited().getType().equals(EntityType.CHICKEN))
/*      */     {
/*  157 */       event.getExited().remove();
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void playerQuit(PlayerQuitEvent quitevent)
/*      */   {
/*  177 */     Player quitPlayer = quitevent.getPlayer();
/*  178 */     if (quitPlayer.getGameMode() == GameMode.CREATIVE) {
/*  179 */       quitPlayer.getInventory().clear();
/*      */     }
/*  181 */     PVPPlayer pp = PvpHandler.getPvpPlayer(quitPlayer);
/*  182 */     if (pp.isInEvent()) {
/*  183 */       EventRunner.leaveEvent(quitPlayer);
/*      */     }
/*  185 */     if (pp.isInCombat())
/*      */     {
/*  187 */       quitPlayer.setHealth(0.0D);
/*      */     }
/*  189 */     if (pp.isInPVP())
/*      */     {
/*  191 */       pp.sethealth(0.0D);
/*  192 */       quitevent.getPlayer().setHealth(0.0D);
/*      */     }
/*  194 */     if (pp.isInParty())
/*      */     {
/*  196 */       pp.getParty().leave(pp);
/*      */     }
/*  198 */     PvpHandler.removePvpPlayer(pp);
/*  199 */     Utils.teleportToSpawn(quitPlayer);
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void foodChangeEvent(FoodLevelChangeEvent event)
/*      */   {
/*  205 */     Random rand = new Random();
/*  206 */     if ((event.getFoodLevel() < ((Player)event.getEntity()).getFoodLevel()) && (rand.nextInt(100) > 4)) {
/*  207 */       event.setCancelled(true);
/*      */     }
/*  209 */     if ((event.getEntity() instanceof Player))
/*      */     {
/*  211 */       Player player = (Player)event.getEntity();
/*  212 */       if (PvpHandler.getPvpPlayer(player).isGod())
/*      */       {
/*  214 */         event.setCancelled(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler(priority=EventPriority.LOWEST)
/*      */   public void specialEnchantDamageEvent(EntityDamageByEntityEvent event) {
/*  221 */     Entity e = event.getEntity();
/*  222 */     if ((e instanceof Player))
/*      */     {
/*  224 */       Player damagee = (Player)e;
/*  225 */       if ((event.getDamager() instanceof Player))
/*      */       {
/*  227 */         Player damager = (Player)event.getDamager();
/*  228 */         ItemStack weapon = damager.getItemInHand();
/*  229 */         if (EnchantManagment.enchantmentType(weapon) == 1) {
/*  230 */           event.setDamage(0.1337D);
/*  231 */           event.setCancelled(true);
/*  232 */           if (EnchantManagment.executeEnchant(damager, damagee, weapon))
/*  233 */             PVPPlayer localPVPPlayer = PvpHandler.getPvpPlayer(damagee);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler(priority=EventPriority.LOWEST)
/*      */   public void arrowHit(BlockPlaceEvent event) {
/*  241 */     if ((event.getBlock().getType() == Material.SKULL_ITEM) || (event.getBlock().getType() == Material.SKULL) || (event.getBlock().getType() == Material.SKULL_ITEM)) {
/*  242 */       event.setCancelled(true);
/*  243 */       event.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD + "BLOCKED UNTIL CHEST GLITCH FIXED PLEASE STORE THIS ITEM!");
/*      */     }
/*      */   }
/*      */ 
/*  248 */   @EventHandler
/*      */   public void arrowHit(EntityDamageEvent event) { if ((event.getEntityType().toString().equalsIgnoreCase("Item_frame")) && (event.getCause().toString().equalsIgnoreCase("projectile")))
/*  249 */       event.setCancelled(true);
/*      */   }
/*      */ 
/*      */   @EventHandler(priority=EventPriority.HIGHEST)
/*      */   public void onPlayerDamageEvent(EntityDamageByEntityEvent event)
/*      */   {
/*  255 */     if (event.getDamage() == 0.1337D) {
/*  256 */       return;
/*      */     }
/*      */ 
/*  259 */     if (!Damage.partyCanHit(event.getEntity(), event.getDamager()))
/*      */     {
/*  261 */       event.setCancelled(true);
/*  262 */       return;
/*      */     }
/*  264 */     if (event.getDamage() <= 0.0D)
/*      */     {
/*  266 */       event.setCancelled(true);
/*  267 */       return;
/*      */     }
/*  269 */     double dealtDamage = 0.0D;
/*  270 */     Entity e = event.getEntity();
/*  271 */     boolean playerarrow = false;
/*  272 */     if ((e instanceof Player))
/*      */     {
/*  274 */       double rawDamage = event.getDamage();
/*  275 */       Player damagee = (Player)e;
/*  276 */       if (damagee.getNoDamageTicks() > 10)
/*      */       {
/*  278 */         event.setCancelled(true);
/*  279 */         return;
/*      */       }
/*      */ 
/*  282 */       PVPPlayer pvpDamagee = PvpHandler.getPvpPlayer(damagee);
/*  283 */       if ((pvpDamagee.isInEvent()) && 
/*  284 */         (pvpDamagee.isEventGrace())) {
/*  285 */         event.setCancelled(true);
/*  286 */         return;
/*      */       }
/*      */ 
/*  289 */       if (pvpDamagee.isGod())
/*      */       {
/*  291 */         event.setCancelled(true);
/*  292 */         return;
/*      */       }
/*  294 */       if ((event.getDamager() instanceof Arrow))
/*      */       {
/*  296 */         Arrow arrow = (Arrow)event.getDamager();
/*  297 */         Entity damager = arrow.getShooter();
/*  298 */         if ((CombatUtil.preventDamageCall(PvpBalance.getTowny(), ((Arrow)event.getDamager()).getShooter(), damagee)) && (pvpDamagee.getCombatCoolDown() <= 0.0D))
/*      */         {
/*  300 */           event.setCancelled(true);
/*  301 */           return;
/*      */         }
/*  303 */         if ((CombatUtil.preventDamageCall(PvpBalance.getTowny(), ((Arrow)event.getDamager()).getShooter(), damagee)) && (pvpDamagee.getCombatCoolDown() > 0.0D))
/*      */         {
/*  305 */           pvpDamagee.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU CAN BE ATTACKED ANYWHERE DUE TO RECENT COMBAT!");
/*      */         }
/*  307 */         if (((damager instanceof Player)) && ((event.getEntity() instanceof Player)))
/*      */         {
/*  309 */           PVPPlayer pvpDamager = PvpHandler.getPvpPlayer((Player)arrow.getShooter());
/*  310 */           pvpDamagee.setLastHitBy(pvpDamager.getPlayer());
/*  311 */           if ((pvpDamager.isInEvent()) && 
/*  312 */             (pvpDamager.isEventGrace())) {
/*  313 */             event.setCancelled(true);
/*  314 */             return;
/*      */           }
/*      */ 
/*  317 */           if ((pvpDamagee.isDuelContestant()) && (!pvpDamager.isDuelContestant())) {
/*  318 */             event.setCancelled(true);
/*  319 */             pvpDamager.sethealth(pvpDamager.gethealth() - 1000);
/*  320 */             pvpDamager.setCombatCooldown(10);
/*  321 */             pvpDamager.getPlayer().sendMessage(ChatColor.RED + "DONT TRY TO DAMAGE SOMONE IN A DUEL!");
/*  322 */             return;
/*      */           }
/*  324 */           if ((pvpDamagee.isInEvent()) && (!pvpDamager.isInEvent())) {
/*  325 */             event.setCancelled(true);
/*  326 */             pvpDamager.sethealth(pvpDamager.gethealth() - 1000);
/*  327 */             pvpDamager.setCombatCooldown(10);
/*  328 */             pvpDamager.getPlayer().sendMessage(ChatColor.RED + "DONT TRY TO DAMAGE SOMONE IN AN EVENT! SEVERE DAMAGE PENALTY!");
/*  329 */             pvpDamager.sethealth(pvpDamager.gethealth() - pvpDamager.getMaxHealth() / 3);
/*  330 */             return;
/*      */           }
/*  332 */           if (pvpDamager.isUsingGrappleShot())
/*      */           {
/*  334 */             pvpDamager.setGrapplePlayer(damagee);
/*      */           }
/*  336 */           dealtDamage = Damage.calcDamage((Player)damager);
/*  337 */           pvpDamagee.damage((int)dealtDamage);
/*  338 */           pvpDamager.setCombatCoolDown(20);
/*  339 */           pvpDamagee.setCombatCoolDown(20);
/*      */         }
/*  341 */         if (!(damager instanceof Player))
/*      */         {
/*  343 */           dealtDamage = event.getDamage() * LoadSave.Multi;
/*  344 */           pvpDamagee.damage((int)dealtDamage);
/*  345 */           pvpDamagee.setCombatCoolDown(20);
/*      */         }
/*  347 */         pvpDamagee.setCombatCoolDown(20);
/*      */       }
/*  349 */       else if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE))
/*      */       {
/*  351 */         if ((CombatUtil.preventDamageCall(PvpBalance.getTowny(), ((Projectile)event.getDamager()).getShooter(), damagee)) && (pvpDamagee.getCombatCoolDown() <= 0.0D))
/*      */         {
/*  353 */           event.setCancelled(true);
/*  354 */           return;
/*      */         }
/*  356 */         if ((CombatUtil.preventDamageCall(PvpBalance.getTowny(), ((Projectile)event.getDamager()).getShooter(), damagee)) && (pvpDamagee.getCombatCoolDown() > 0.0D))
/*      */         {
/*  358 */           pvpDamagee.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU CAN BE ATTACKED ANYWHERE DUE TO RECENT COMBAT!");
/*      */         }
/*  360 */         else if (event.getDamager().getType() == EntityType.WITHER_SKULL)
/*      */         {
/*  362 */           event.setDamage(0.0D);
/*  363 */           dealtDamage += 85.0D;
/*      */         }
/*  365 */         else if (event.getDamager().getType() == EntityType.SMALL_FIREBALL)
/*      */         {
/*  367 */           dealtDamage += 65.0D;
/*      */         }
/*  369 */         else if (event.getDamager().getType() == EntityType.FIREBALL)
/*      */         {
/*  371 */           dealtDamage += 175.0D;
/*      */         }
/*  373 */         PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, event.getDamager(), (int)dealtDamage, event.getCause());
/*  374 */         Bukkit.getPluginManager().callEvent(pbdEvent);
/*  375 */         if (pbdEvent.isCancelled())
/*      */         {
/*  377 */           event.setCancelled(true);
/*  378 */           return;
/*      */         }
/*  380 */         dealtDamage = pbdEvent.getDamage();
/*  381 */         if (!pvpDamagee.damage((int)dealtDamage))
/*      */         {
/*  383 */           event.setCancelled(true);
/*      */         }
/*      */ 
/*      */       }
/*  387 */       else if ((event.getDamager() instanceof Player))
/*      */       {
/*  389 */         Player damager = (Player)event.getDamager();
/*  390 */         PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
/*  391 */         pvpDamagee.setLastHitBy(pvpDamager.getLastHitBy());
/*  392 */         if ((CombatUtil.preventDamageCall(PvpBalance.getTowny(), damager, damagee)) && (pvpDamagee.getCombatCoolDown() <= 0.0D))
/*      */         {
/*  394 */           event.setCancelled(true);
/*  395 */           return;
/*      */         }
/*  397 */         if ((CombatUtil.preventDamageCall(PvpBalance.getTowny(), damager, damagee)) && (pvpDamagee.getCombatCoolDown() > 0.0D))
/*      */         {
/*  399 */           pvpDamagee.getPlayer().sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU CAN BE ATTACKED ANYWHERE DUE TO RECENT COMBAT!");
/*      */         }
/*  401 */         if (!pvpDamager.canHit())
/*      */         {
/*  403 */           event.setCancelled(true);
/*  404 */           return;
/*      */         }
/*      */ 
/*  407 */         if ((pvpDamagee.isDuelContestant()) && (!pvpDamager.isDuelContestant())) {
/*  408 */           event.setCancelled(true);
/*  409 */           pvpDamager.sethealth(pvpDamager.gethealth() - 1000);
/*  410 */           pvpDamager.setCombatCooldown(10);
/*  411 */           pvpDamager.getPlayer().sendMessage(ChatColor.RED + "DONT TRY TO DAMAGE SOMONE IN A DUEL!");
/*  412 */           return;
/*      */         }
/*  414 */         if ((damager.getItemInHand().getType().equals(Material.BOW)) && (!(event.getDamager() instanceof Arrow)))
/*      */         {
/*  416 */           dealtDamage = 20.0D;
/*      */         }
/*  420 */         else if ((pvpDamagee.getPlayer().isBlocking()) && (pvpDamagee.getStamina() > 10.0D)) {
/*  421 */           pvpDamagee.setStamina((int)pvpDamagee.getStamina() - 10);
/*  422 */           pvpDamagee.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "YOU BLOCK THE ATTACK! AND TAKE HALF DAMAGE!");
/*  423 */           dealtDamage = Damage.calcDamage(damager) / 2;
/*  424 */           Skills.Block.block(pvpDamager.getPlayer());
/*      */         }
/*  427 */         else if ((pvpDamager.canUsePileDrive()) && (!damager.isSneaking())) {
/*  428 */           PileDrive.pileDrive(damagee, damager);
/*      */         }
/*  430 */         else if ((pvpDamager.canUsePileDrive()) && (damager.isSneaking())) {
/*  431 */           Incapacitate.Incapacitate(damagee, damager);
/*      */         }
/*      */         else
/*      */         {
/*  435 */           if (pvpDamagee.getPlayer().isBlocking())
/*      */           {
/*  437 */             pvpDamagee.getPlayer().sendMessage(ChatColor.BOLD + ChatColor.RED + "NOT ENOUGH STAMINA TO BLOCK!");
/*      */           }
/*  439 */           dealtDamage = Damage.calcDamage(damager) + new Random().nextInt(Damage.calcDamage(damager) / 10);
/*      */         }
/*      */ 
/*  442 */         PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, (int)dealtDamage, event.getCause());
/*  443 */         Bukkit.getPluginManager().callEvent(pbdEvent);
/*  444 */         if (pbdEvent.isCancelled())
/*      */         {
/*  446 */           event.setCancelled(true);
/*  447 */           return;
/*      */         }
/*  449 */         dealtDamage = pbdEvent.getDamage();
/*  450 */         if (!pvpDamagee.damage((int)dealtDamage))
/*      */         {
/*  452 */           event.setCancelled(true);
/*  453 */           return;
/*      */         }
/*      */ 
/*  456 */         String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + pvpDamagee.gethealth();
/*  457 */         Bukkit.getMessenger().dispatchIncomingMessage(damager, "Scoreboard", message.getBytes());
/*  458 */         pvpDamager.setHitCoolDown(LoadSave.HitCooldown);
/*  459 */         pvpDamager.setCombatCoolDown(20);
/*  460 */         pvpDamagee.setCombatCoolDown(20);
/*  461 */         if ((PvpBalance.plugin.isDebug()) || (pvpDamager.isPvpstats()))
/*      */         {
/*  463 */           damager.sendMessage(ChatColor.RED + "DAMAGE DEALT: " + dealtDamage);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  469 */         if (((event.getDamager() instanceof Arrow)) && ((((Arrow)event.getDamager()).getShooter() instanceof Player)))
/*      */         {
/*  471 */           Player damager = (Player)((Arrow)event.getDamager()).getShooter();
/*  472 */           PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
/*  473 */           dealtDamage = Damage.calcDamage(damager);
/*  474 */           pvpDamager.setCombatCoolDown(20);
/*  475 */           pvpDamagee.setCombatCoolDown(20);
/*      */         }
/*      */         else
/*      */         {
/*  479 */           dealtDamage = rawDamage * (LoadSave.Multi / 2);
/*  480 */           pvpDamagee.damage((int)dealtDamage);
/*  481 */           pvpDamagee.setCombatCoolDown(20);
/*      */         }
/*  483 */         PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, event.getDamager(), (int)dealtDamage, event.getCause());
/*  484 */         Bukkit.getPluginManager().callEvent(pbdEvent);
/*  485 */         if (pbdEvent.isCancelled())
/*      */         {
/*  487 */           event.setCancelled(true);
/*  488 */           return;
/*      */         }
/*  490 */         dealtDamage = pbdEvent.getDamage();
/*  491 */         if (!pvpDamagee.damage((int)dealtDamage))
/*      */         {
/*  493 */           event.setCancelled(true);
/*  494 */           return;
/*      */         }
/*      */       }
/*  497 */       event.setDamage(0.0D);
/*  498 */       event.setCancelled(false);
/*      */     }
/*  500 */     else if ((e instanceof LivingEntity))
/*      */     {
/*  502 */       if ((!(event.getDamager() instanceof Player)) && (!(event.getDamager() instanceof Arrow)))
/*  503 */         return;
/*  504 */       double health = ((LivingEntity)e).getHealth() - event.getDamage();
/*  505 */       if (health < 0.0D)
/*  506 */         health = 0.0D;
/*  507 */       String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + (int)health;
/*  508 */       if ((event.getDamager() instanceof Player))
/*      */       {
/*  510 */         Bukkit.getMessenger().dispatchIncomingMessage((Player)event.getDamager(), "Scoreboard", message.getBytes());
/*      */       }
/*      */       else
/*      */       {
/*  514 */         if (!(((Arrow)event.getDamager()).getShooter() instanceof Player))
/*  515 */           return;
/*  516 */         Bukkit.getMessenger().dispatchIncomingMessage((Player)((Arrow)event.getDamager()).getShooter(), "Scoreboard", message.getBytes());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void playerJoin(PlayerJoinEvent event) {
/*  523 */     Player player = event.getPlayer();
/*  524 */     PVPPlayer newPVP = PvpBalance.createNewPvPPlayerObject(player);
/*  525 */     if (player.getWorld().getName().contains("world"))
/*      */     {
/*  527 */       if (player.hasPermission("particles.admin"))
/*      */       {
/*  529 */         player.sendMessage(ChatColor.RED + "Welcome Administrator :" + player.getName() + ChatColor.GREEN + " Please Enjoy your stay on the medieval lords server.. a personal Concierge will be with you shortly to handle your every whim");
/*  530 */         newPVP.setGod(true);
/*      */       }
/*      */       else
/*      */       {
/*  534 */         Utils.teleportToSpawn(player);
/*      */       }
/*      */     }
/*  537 */     Damage.calcArmor(event.getPlayer());
/*  538 */     if (player.getHealth() > 0.0D)
/*  539 */       newPVP.sethealth(newPVP.getMaxHealth());
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void respawn(PlayerRespawnEvent event)
/*      */   {
/*  545 */     Player player = event.getPlayer();
/*  546 */     for (PotionEffect effect : player.getActivePotionEffects()) {
/*  547 */       PotionEffectType potion = effect.getType();
/*  548 */       player.removePotionEffect(potion);
/*      */     }
/*  550 */     if (PvpHandler.getPvpPlayer(player) == null)
/*      */     {
/*  552 */       PvpBalance.createNewPvPPlayer(player);
/*      */     }
/*  554 */     Damage.calcArmor(player);
/*  555 */     PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
/*  556 */     PVPPlayer.setIsDead(false);
/*  557 */     PVPPlayer.sethealth(PVPPlayer.getMaxHealth());
/*  558 */     Utils.teleportToSpawn(player);
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void projectileHitEvent(ProjectileHitEvent event) {
/*  563 */     if ((event.getEntity().getShooter() instanceof Player)) {
/*  564 */       Player player = (Player)event.getEntity().getShooter();
/*  565 */       PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  566 */       if ((pvp != null) && (pvp.isUsingGrappleShot()))
/*      */       {
/*  568 */         pvp.setGrappleEnd(event.getEntity().getLocation());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void entityDeath(EntityDeathEvent event)
/*      */   {
/*  576 */     if (event.getEntityType().equals(EntityType.CHICKEN))
/*      */     {
/*  578 */       event.getDrops().clear();
/*  579 */       event.getEntity().remove();
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void stopGamemodeDupe(PlayerGameModeChangeEvent event) {
/*  585 */     if ((event.getNewGameMode() == GameMode.SURVIVAL) && (event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
/*  586 */       event.getPlayer().getInventory().clear();
/*  587 */       event.getPlayer().sendMessage(ChatColor.RED + "Creative Inventory cleared to stop duping");
/*  588 */       event.getPlayer().getInventory().clear();
/*  589 */       event.getPlayer().getItemInHand().setType(Material.AIR);
/*  590 */       event.getPlayer().getItemOnCursor().setType(Material.AIR);
/*  591 */       event.getPlayer().getInventory().getBoots().setType(Material.AIR);
/*  592 */       event.getPlayer().getInventory().getChestplate().setType(Material.AIR);
/*  593 */       event.getPlayer().getInventory().getLeggings().setType(Material.AIR);
/*  594 */       event.getPlayer().getInventory().getHelmet().setType(Material.AIR);
/*  595 */       event.getPlayer().sendMessage(ChatColor.RED + "Creative Inventory cleared to stop duping");
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void blockfromto(BlockFromToEvent event) {
/*  601 */     if ((event.getBlock().getType() == Material.WATER) && (event.getToBlock().getType() == Material.STONE)) {
/*  602 */       event.setCancelled(true);
/*  603 */       event.getToBlock().setType(Material.GRAVEL);
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void playerToggleFlightEvent(PlayerToggleFlightEvent event) {
/*  609 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(event.getPlayer());
/*  610 */     if ((!pvp.flyZone) && (pvp.getStamina() > 10.0D) && (pvp.canUseSkill()) && (event.getPlayer().getGameMode() == GameMode.SURVIVAL) && (event.getPlayer().getWorld().getName().contains("world"))) {
/*  611 */       SuperJump.Jump(event.getPlayer(), 0.9D);
/*  612 */       pvp.setSkillCooldown(7);
/*  613 */       pvp.setCanUseSkill(false);
/*  614 */       pvp.setStamina((int)(pvp.getStamina() - 10.0D));
/*  615 */       event.setCancelled(true);
/*  616 */       pvp.getPlayer().setAllowFlight(false);
/*  617 */       pvp.getPlayer().setFlying(false);
/*      */     }
/*  619 */     else if ((pvp.flyZone = 0) != 0) {
/*  620 */       event.setCancelled(true);
/*  621 */       event.getPlayer().setAllowFlight(false);
/*  622 */       event.getPlayer().setFlying(false);
/*      */     }
/*  624 */     else if ((pvp.flyZone = (event.getPlayer().getGameMode() == GameMode.SURVIVAL) && (event.getPlayer().getWorld().getName().contains("world")) ? 1 : 0) != 0) {
/*  625 */       event.setCancelled(true);
/*  626 */       event.getPlayer().setAllowFlight(true);
/*  627 */       event.getPlayer().setFlying(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void playerToggleSprintEvent(PlayerToggleSprintEvent event) {
/*  633 */     Player player = event.getPlayer();
/*  634 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  635 */     pvp.setWasSprinting(2);
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void playerToggleSneakEvent(PlayerToggleSneakEvent event) {
/*  640 */     Player player = event.getPlayer();
/*  641 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  642 */     if (pvp.wasFirstToggle())
/*      */     {
/*  644 */       pvp.setFirstToggle(false);
/*  645 */       return;
/*      */     }
/*  647 */     if ((pvp.getWasSprinting() > 0) && (pvp.canUseSkill()) && (pvp.getStamina() > 5.0D) && (!pvp.getUsedSpeedSkill())) {
/*  648 */       event.setCancelled(true);
/*  649 */       player.setSneaking(false);
/*  650 */       SuperSpeed.speedOn(player);
/*  651 */       pvp.setUsedSpeedSkill(true);
/*  652 */       pvp.setFirstToggle(true);
/*  653 */       return;
/*      */     }
/*      */ 
/*  656 */     if (pvp.getUsedSpeedSkill())
/*      */     {
/*  658 */       pvp.setUsedSpeedSkill(false);
/*  659 */       pvp.setCanUseSkill(false);
/*  660 */       SuperSpeed.speedOff(player);
/*      */     }
/*      */   }
/*      */ 
/*  665 */   @EventHandler
/*      */   public static void getExplosion(EntityExplodeEvent event) { List explosionBlocks = new ArrayList();
/*  666 */     explosionBlocks.addAll(event.blockList());
/*  667 */     double height = 0.04D;
/*  668 */     int every3 = 0;
/*  669 */     Location loc = event.getLocation();
/*      */     try {
/*  671 */       ParticleEffect.sendToLocation(ParticleEffect.FLAME, loc, 1.8F, 1.0F, 1.8F, 0.141F, 2000);
/*  672 */       ParticleEffect.sendToLocation(ParticleEffect.LAVA, loc, 1.8F, 0.8F, 1.8F, 0.281F, 200);
/*      */     }
/*      */     catch (Exception e) {
/*  675 */       e.printStackTrace();
/*      */     } }
/*      */ 
/*      */   @EventHandler
/*      */   public void antiSnow(EntityBlockFormEvent event) {
/*  680 */     if (event.getEntity().toString() == "CraftSnowman")
/*  681 */       event.setCancelled(true);
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void regenEvent(EntityRegainHealthEvent event)
/*      */   {
/*  687 */     if ((event.getEntity() instanceof Player))
/*      */     {
/*  689 */       if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC)
/*      */       {
/*  691 */         Player player = (Player)event.getEntity();
/*  692 */         if (PvpHandler.getPvpPlayer(player) == null)
/*      */         {
/*  694 */           PvpBalance.createNewPvPPlayer(player);
/*      */         }
/*  696 */         PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
/*  697 */         int heal = LoadSave.HealPot;
/*  698 */         PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(player, heal, event.getRegainReason());
/*  699 */         Bukkit.getPluginManager().callEvent(pberh);
/*  700 */         if (pberh.isCancelled())
/*      */         {
/*  702 */           event.setCancelled(true);
/*  703 */           return;
/*      */         }
/*  705 */         PVPPlayer.sethealth(PVPPlayer.gethealth() + pberh.getAmount());
/*      */       }
/*      */     }
/*  708 */     event.setCancelled(true);
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void playerinteract(PlayerInteractEvent event)
/*      */   {
/*  715 */     if ((event.getPlayer().getItemInHand().getType() == Material.SKULL_ITEM) || (event.getPlayer().getItemInHand().getType() == Material.SKULL)) {
/*  716 */       event.setCancelled(true);
/*      */     }
/*  718 */     Player player = event.getPlayer();
/*  719 */     if ((event.useItemInHand() == Event.Result.DEFAULT) && (!event.isBlockInHand()) && ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)))
/*      */     {
/*  721 */       if (isArmor(player.getItemInHand()))
/*      */       {
/*  723 */         Damage.calcArmor(player);
/*  724 */         PvpHandler.getPvpPlayer(player).setArmorEventLastTick(1);
/*      */       }
/*      */     }
/*      */ 
/*  728 */     if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
/*  729 */       if ((player.getItemInHand().getType() == Material.DIAMOND_SWORD) || (player.getItemInHand().getType() == Material.IRON_SWORD) || 
/*  730 */         (player.getItemInHand().getType() == Material.GOLD_SWORD) || (player.getItemInHand().getType() == Material.WOOD_SWORD) || 
/*  731 */         (player.getItemInHand().getType() == Material.IRON_AXE) || (player.getItemInHand().getType() == Material.GOLD_AXE) || 
/*  732 */         (player.getItemInHand().getType() == Material.WOOD_AXE) || (player.getItemInHand().getType() == Material.DIAMOND_AXE) || 
/*  733 */         (player.getItemInHand().getType() == Material.IRON_HOE) || (player.getItemInHand().getType() == Material.GOLD_HOE) || 
/*  734 */         (player.getItemInHand().getType() == Material.WOOD_HOE) || (player.getItemInHand().getType() == Material.DIAMOND_HOE)) {
/*  735 */         PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  736 */         if (pvp.getComboReady() < 7)
/*      */         {
/*  738 */           pvp.setComboReady(pvp.getComboReady() + 2);
/*  739 */           if ((pvp.getComboReady() >= 6) && (pvp.getStamina() >= 51.0D) && (!pvp.canUsePileDrive())) {
/*  740 */             player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "COMBO READY! HIT PLAYER TO PILEDRIVE, SNEAK HIT TO INCAPACITATE!");
/*  741 */             pvp.setCanUsePileDrive(true);
/*  742 */             pvp.setCanUseGrappleShot(false);
/*      */           }
/*  744 */           if ((pvp.getComboReady() >= 6) && (pvp.getStamina() < 51.0D)) {
/*  745 */             player.sendMessage(ChatColor.RED + ChatColor.BOLD + "PILEDRIVE NEEDS 50 STAMINA!");
/*      */           }
/*      */         }
/*      */       }
/*  749 */       if (player.getItemInHand().getType() == Material.BOW) {
/*  750 */         PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  751 */         if (pvp.getComboReady() < 7)
/*      */         {
/*  753 */           pvp.setComboReady(pvp.getComboReady() + 2);
/*  754 */           if ((pvp.getComboReady() >= 7) && (pvp.getStamina() >= 25.0D)) {
/*  755 */             player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "GRAPLE READY SHOOT ARROW TO ACTIVATE!");
/*  756 */             pvp.setCanUsePileDrive(false);
/*  757 */             pvp.setCanUseGrappleShot(true);
/*      */           }
/*  759 */           if ((pvp.getComboReady() >= 7) && (pvp.getStamina() < 25.0D))
/*  760 */             player.sendMessage(ChatColor.RED + ChatColor.BOLD + "GRAPPLE NEEDS 25 STAMINA!");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler(priority=EventPriority.HIGHEST)
/*      */   public void onEntityDamageEvent(EntityDamageEvent event)
/*      */   {
/*  773 */     if ((event instanceof EntityDamageByEntityEvent))
/*  774 */       return;
/*  775 */     int damage = 0;
/*  776 */     if ((event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) && (event.getEntityType() == EntityType.CHICKEN) && (event.getEntity().getPassenger() != null))
/*      */     {
/*  778 */       event.setCancelled(true);
/*      */     }
/*  780 */     if ((event.getEntity() instanceof Player))
/*      */     {
/*  782 */       Player player = (Player)event.getEntity();
/*  783 */       PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  784 */       if (event.getCause().equals(EntityDamageEvent.DamageCause.STARVATION))
/*      */       {
/*  786 */         event.setDamage(0.0D);
/*  787 */         return;
/*      */       }
/*  789 */       if (PvpHandler.getPvpPlayer(player).isGod())
/*      */       {
/*  791 */         event.setCancelled(true);
/*  792 */         return;
/*      */       }
/*  794 */       if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK))
/*      */       {
/*  796 */         int theDamage = LoadSave.Firetick;
/*      */         int level;
/*  797 */         for (PotionEffect effect : pvp.getPlayer().getActivePotionEffects())
/*      */         {
/*  799 */           if (effect.getType() == PotionEffectType.FIRE_RESISTANCE)
/*      */           {
/*  801 */             level = effect.getAmplifier();
/*  802 */             theDamage /= level;
/*      */           }
/*      */         }
/*  805 */         for (ItemStack item : pvp.getPlayer().getInventory().getArmorContents())
/*      */         {
/*  807 */           theDamage -= item.getEnchantmentLevel(Enchantment.PROTECTION_FIRE) / 2;
/*      */         }
/*  809 */         if (theDamage < 0)
/*      */         {
/*  811 */           theDamage = 0;
/*      */         }
/*  813 */         pvp.uncheckedDamage(theDamage);
/*  814 */         int prevNoDamageTicks = player.getNoDamageTicks();
/*  815 */         player.damage(0.0D);
/*  816 */         player.setNoDamageTicks(prevNoDamageTicks);
/*  817 */         event.setCancelled(true);
/*  818 */         return;
/*      */       }
/*  820 */       if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID))
/*      */       {
/*  822 */         damage = LoadSave.Voide;
/*      */       }
/*  824 */       else if (event.getCause().equals(EntityDamageEvent.DamageCause.CONTACT))
/*      */       {
/*  826 */         damage = LoadSave.Contact;
/*      */       } else {
/*  828 */         if (event.getCause().equals(EntityDamageEvent.DamageCause.DROWNING))
/*      */         {
/*  830 */           int theDamage = LoadSave.Firetick;
/*  831 */           for (PotionEffect effect : pvp.getPlayer().getActivePotionEffects())
/*      */           {
/*  833 */             if (effect.getType() == PotionEffectType.POISON)
/*      */             {
/*  835 */               int level = effect.getAmplifier();
/*  836 */               theDamage *= level;
/*      */             }
/*  838 */             if (effect.getType() == PotionEffectType.DAMAGE_RESISTANCE)
/*      */             {
/*  840 */               int level = effect.getAmplifier();
/*  841 */               theDamage /= level;
/*      */             }
/*      */           }
/*  844 */           pvp.uncheckedDamage(theDamage);
/*  845 */           int prevNoDamageTicks = player.getNoDamageTicks();
/*  846 */           player.damage(0.0D);
/*  847 */           player.setNoDamageTicks(prevNoDamageTicks);
/*  848 */           event.setCancelled(true);
/*  849 */           return;
/*      */         }
/*  851 */         if (event.getCause().equals(EntityDamageEvent.DamageCause.POISON))
/*      */         {
/*  853 */           pvp.uncheckedDamage(LoadSave.Poison);
/*  854 */           int prevNoDamageTicks = player.getNoDamageTicks();
/*  855 */           player.damage(0.0D);
/*  856 */           player.setNoDamageTicks(prevNoDamageTicks);
/*  857 */           event.setCancelled(true);
/*  858 */           return;
/*      */         }
/*  860 */         if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
/*      */         {
/*  862 */           event.setCancelled(true);
/*  863 */           return;
/*      */         }
/*  865 */         if (event.getCause().equals(EntityDamageEvent.DamageCause.WITHER))
/*      */         {
/*  867 */           pvp.uncheckedDamage(LoadSave.Wither);
/*  868 */           int prevNoDamageTicks = player.getNoDamageTicks();
/*  869 */           player.damage(0.0D);
/*  870 */           player.setNoDamageTicks(prevNoDamageTicks);
/*  871 */           event.setCancelled(true);
/*  872 */           return;
/*      */         }
/*      */ 
/*  875 */         if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))
/*      */         {
/*  877 */           damage = LoadSave.Explosion_Mob;
/*  878 */           pvp.setCombatCoolDown(20);
/*      */         }
/*  880 */         else if (event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION))
/*      */         {
/*  882 */           damage = LoadSave.Explosion;
/*  883 */           pvp.setCombatCoolDown(20);
/*      */         }
/*  885 */         else if (event.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING))
/*      */         {
/*  887 */           damage = LoadSave.Lightning;
/*  888 */           pvp.setCombatCoolDown(20);
/*      */         }
/*  890 */         else if (event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION))
/*      */         {
/*  892 */           damage = LoadSave.Drowning;
/*  893 */           pvp.setCombatCoolDown(20);
/*      */         }
/*  896 */         else if ((!event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) && (!event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)))
/*      */         {
/*  898 */           damage = (int)event.getDamage();
/*  899 */           pvp.setCombatCoolDown(20);
/*      */         }
/*      */       }
/*  902 */       PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
/*  903 */       Bukkit.getPluginManager().callEvent(pbdEvent);
/*  904 */       if (pbdEvent.isCancelled())
/*      */       {
/*  906 */         event.setCancelled(true);
/*  907 */         return;
/*      */       }
/*  909 */       pvp.uncheckedDamage(damage);
/*  910 */       if (pvp.getCombatCooldown() < 10)
/*      */       {
/*  912 */         pvp.setCombatCooldown(pvp.getCombatCooldown() + 5);
/*      */       }
/*  914 */       event.setCancelled(true);
/*  915 */       player.damage(0.0D);
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void command(PlayerCommandPreprocessEvent event) {
/*  921 */     Player player = event.getPlayer();
/*  922 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  923 */     if (pvp.isInEvent()) {
/*  924 */       if (event.getMessage().equalsIgnoreCase("/leave")) {
/*  925 */         if (SkyArrow.checkParticipant(player)) {
/*  926 */           SkyArrow.leave(player);
/*      */         }
/*  928 */         if (CrazyRace.checkParticipant(player)) {
/*  929 */           SkyArrow.leave(player);
/*      */         }
/*      */       }
/*  932 */       if ((event.getMessage().equalsIgnoreCase("/msg")) || (event.getMessage().equalsIgnoreCase("/message")) || (event.getMessage().equalsIgnoreCase("/r"))) {
/*  933 */         return;
/*      */       }
/*  935 */       if (event.getMessage().equalsIgnoreCase("/play")) {
/*  936 */         player.sendMessage(ChatColor.RED + "You are already playing.");
/*  937 */         if (EventRunner.getActiveEvent().equalsIgnoreCase(SkyArrow.getEventName())) {
/*  938 */           SkyArrow.listPlayers(player);
/*      */         }
/*  940 */         else if (EventRunner.getActiveEvent().equalsIgnoreCase(CrazyRace.getEventName()))
/*  941 */           CrazyRace.listPlayers(player);
/*      */       }
/*      */       else
/*      */       {
/*  945 */         event.setCancelled(true);
/*  946 */         player.sendMessage(ChatColor.BOLD + ChatColor.RED + "Commands Cannot be used while in an event, type " + ChatColor.GREEN + "/play" + ChatColor.RED + " to get players remaining");
/*  947 */         player.sendMessage(ChatColor.BOLD + ChatColor.RED + "Or type " + ChatColor.GREEN + "/leave" + ChatColor.RED + " to Quit");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler(priority=EventPriority.HIGHEST)
/*      */   public void onPlayerDeath(final PlayerDeathEvent event)
/*      */   {
/*  957 */     Player player = event.getEntity();
/*  958 */     PVPPlayer pvpPlayer = PvpHandler.getPvpPlayer(player);
/*  959 */     MYSQLManager mysql = PvpBalance.mysql;
/*  960 */     if (Duel.checkContestant(event.getEntity())) {
/*  961 */       Duel.playerDeath(event.getEntity());
/*      */     }
/*  963 */     pvpPlayer.setIsDead(true);
/*      */ 
/*  965 */     if (((event.getEntity().getKiller() instanceof Player)) && (event.getEntity().getWorld().getName().equalsIgnoreCase("world"))) {
/*  966 */       PVPPlayer pvpKiller = PvpHandler.getPvpPlayer(event.getEntity().getKiller());
/*  967 */       if (pvpPlayer.getMaxHealth() >= 6300)
/*      */         try {
/*  969 */           mysql.storeUserData(event.getEntity(), "EpicDeaths", mysql.getUserData(event.getEntity(), "EpicDeaths") + 1);
/*  970 */           mysql.storeUserData(event.getEntity().getKiller(), "EpicKills", mysql.getUserData(event.getEntity(), "EpicKills") + 1);
/*      */         } catch (SQLException e) {
/*  972 */           e.printStackTrace();
/*      */         }
/*      */       try
/*      */       {
/*  976 */         mysql.storeUserData(event.getEntity(), "Deaths", mysql.getUserData(event.getEntity(), "Deaths") + 1);
/*  977 */         mysql.storeUserData(event.getEntity().getKiller(), "Kills", mysql.getUserData(event.getEntity(), "Kills") + 1);
/*      */       } catch (SQLException e) {
/*  979 */         e.printStackTrace();
/*      */       }
/*      */     }
/*  982 */     Bukkit.getScheduler().scheduleSyncDelayedTask(PvpBalance.plugin, new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/*  987 */         PBEntityDeathEvent pbede = new PBEntityDeathEvent(event.getEntity(), event.getDrops(), event.getDroppedExp());
/*  988 */         Bukkit.getPluginManager().callEvent(pbede);
/*  989 */         event.setDroppedExp(pbede.getDropExp());
/*      */       }
/*      */     }
/*      */     , 1L);
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void onInventoryClick(InventoryClickEvent event)
/*      */   {
/*  997 */     if (event.isShiftClick())
/*      */     {
/*  999 */       if (isArmor(event.getCurrentItem()))
/*      */       {
/* 1001 */         Player player = (Player)event.getWhoClicked();
/* 1002 */         if (PvpHandler.getPvpPlayer(player) == null)
/*      */         {
/* 1004 */           PvpBalance.createNewPvPPlayer(player);
/*      */         }
/* 1006 */         PvpHandler.getPvpPlayer(player).setArmorEventLastTick(1);
/*      */       }
/*      */     }
/* 1009 */     if (event.getSlotType() == InventoryType.SlotType.ARMOR)
/*      */     {
/* 1011 */       Player player = (Player)event.getWhoClicked();
/* 1012 */       if (PvpHandler.getPvpPlayer(player) == null)
/*      */       {
/* 1014 */         PvpBalance.createNewPvPPlayer(player);
/*      */       }
/* 1016 */       PvpHandler.getPvpPlayer(player).setArmorEventLastTick(1);
/*      */     }
/*      */   }
/*      */ 
/* 1021 */   @EventHandler
/*      */   public void shotBow(EntityShootBowEvent event) { if ((event.getEntity() instanceof Player)) {
/* 1022 */       Player player = (Player)event.getEntity();
/* 1023 */       PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 1024 */       pvp.setComboReady(pvp.getComboReady() - 2);
/* 1025 */       if (event.getForce() < 0.95D) {
/* 1026 */         event.setCancelled(true);
/* 1027 */         player.sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU MUST PULL ALL THE WAY BACK TO FIRE BOW!");
/* 1028 */         return;
/*      */       }
/* 1030 */       if (pvp.canUseGrappleShot())
/*      */       {
/* 1032 */         if (pvp.getStamina() < 25.0D)
/*      */         {
/* 1034 */           player.sendMessage(ChatColor.RED + ChatColor.BOLD + "CANNOT USE GRAPPLE SHOT NOT ENOUGH STAMINA (25)!");
/*      */         }
/* 1036 */         else if ((pvp.getStamina() > 25.0D) && (pvp.getSkillCooldown() == 0)) {
/* 1037 */           player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "YOU USE GRAPPLE SHOT!");
/* 1038 */           pvp.setGrappleArrow((Arrow)event.getProjectile());
/* 1039 */           pvp.setSkillCooldown(5);
/* 1040 */           pvp.setGrappleStart(pvp.getPlayer().getLocation());
/* 1041 */           pvp.setGrappleEnd(null);
/* 1042 */           pvp.setIsUsingGrappleShot(true);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @EventHandler
/*      */   public void onPlayerItemBreakEvent(PlayerItemBreakEvent event)
/*      */   {
/* 1051 */     Damage.calcArmor(event.getPlayer());
/*      */   }
/*      */ 
/*      */   private boolean isArmor(ItemStack is)
/*      */   {
/* 1056 */     if (is == null)
/* 1057 */       return false;
/* 1058 */     int check = 0;
/* 1059 */     if (is.getType() == Material.DIAMOND_HELMET) {
/* 1060 */       check = 310;
/*      */     }
/* 1062 */     else if (is.getType() == Material.DIAMOND_CHESTPLATE) {
/* 1063 */       check = 311;
/*      */     }
/* 1065 */     else if (is.getType() == Material.DIAMOND_LEGGINGS) {
/* 1066 */       check = 312;
/*      */     }
/* 1068 */     else if (is.getType() == Material.DIAMOND_BOOTS) {
/* 1069 */       check = 313;
/*      */     }
/* 1071 */     else if (is.getType() == Material.GOLD_HELMET) {
/* 1072 */       check = 314;
/*      */     }
/* 1074 */     else if (is.getType() == Material.GOLD_CHESTPLATE) {
/* 1075 */       check = 315;
/*      */     }
/* 1077 */     else if (is.getType() == Material.GOLD_LEGGINGS) {
/* 1078 */       check = 316;
/*      */     }
/* 1080 */     else if (is.getType() == Material.GOLD_BOOTS) {
/* 1081 */       check = 317;
/*      */     }
/* 1083 */     else if (is.getType() == Material.IRON_HELMET) {
/* 1084 */       check = 306;
/*      */     }
/* 1086 */     else if (is.getType() == Material.IRON_CHESTPLATE) {
/* 1087 */       check = 307;
/*      */     }
/* 1089 */     else if (is.getType() == Material.IRON_LEGGINGS) {
/* 1090 */       check = 308;
/*      */     }
/* 1092 */     else if (is.getType() == Material.IRON_BOOTS) {
/* 1093 */       check = 309;
/*      */     }
/* 1095 */     else if (is.getType() == Material.CHAINMAIL_HELMET) {
/* 1096 */       check = 302;
/*      */     }
/* 1098 */     else if (is.getType() == Material.CHAINMAIL_CHESTPLATE) {
/* 1099 */       check = 303;
/*      */     }
/* 1101 */     else if (is.getType() == Material.CHAINMAIL_LEGGINGS) {
/* 1102 */       check = 304;
/*      */     }
/* 1104 */     else if (is.getType() == Material.CHAINMAIL_BOOTS) {
/* 1105 */       check = 305;
/*      */     }
/* 1107 */     else if (is.getType() == Material.LEATHER_HELMET) {
/* 1108 */       check = 298;
/*      */     }
/* 1110 */     else if (is.getType() == Material.LEATHER_CHESTPLATE) {
/* 1111 */       check = 299;
/*      */     }
/* 1113 */     else if (is.getType() == Material.LEATHER_LEGGINGS) {
/* 1114 */       check = 300;
/*      */     }
/* 1116 */     else if (is.getType() == Material.LEATHER_BOOTS) {
/* 1117 */       check = 301;
/*      */     }
/* 1119 */     switch (check)
/*      */     {
/*      */     case 298:
/*      */     case 299:
/*      */     case 300:
/*      */     case 301:
/*      */     case 302:
/*      */     case 303:
/*      */     case 304:
/*      */     case 305:
/*      */     case 306:
/*      */     case 307:
/*      */     case 308:
/*      */     case 309:
/*      */     case 310:
/*      */     case 311:
/*      */     case 312:
/*      */     case 313:
/*      */     case 314:
/*      */     case 315:
/*      */     case 316:
/*      */     case 317:
/* 1141 */       return true;
/*      */     }
/* 1143 */     return false;
/*      */   }
/*      */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.DBZListener
 * JD-Core Version:    0.6.2
 */