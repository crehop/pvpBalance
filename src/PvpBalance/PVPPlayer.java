/*      */ package PvpBalance;
/*      */ 
/*      */ import DuelZone.Duel;
/*      */ import Event.CrazyRace;
/*      */ import Event.EventRunner;
/*      */ import Event.PBEntityRegainHealthEvent;
/*      */ import Event.SkyArrow;
/*      */ import Party.Invite;
/*      */ import Party.Party;
/*      */ import Skills.GrappleShot;
/*      */ import Skills.SuperSpeed;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import me.frodenkvist.scoreboardmanager.SMHandler;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.GameMode;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.entity.Arrow;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.plugin.messaging.Messenger;
/*      */ import org.bukkit.potion.PotionEffect;
/*      */ import org.bukkit.potion.PotionEffectType;
/*      */ 
/*      */ public class PVPPlayer
/*      */ {
/*      */   private Player player;
/*      */   private Player lastHitBy;
/*      */   private double health;
/*      */   private double stamina;
/*      */   private double healthLastTick;
/*      */   private double maxHealth;
/*      */   private double maxStamina;
/*      */   private double cooldown;
/*      */   private double healthPercent;
/*      */   private Arrow grappleArrow;
/*      */   private Location grappleStart;
/*      */   private Location grappleEnd;
/*      */   private Player hitByGrapple;
/*      */   private int comboReady;
/*      */   private int hitCoolDown;
/*      */   private int skillCoolDown;
/*      */   private int combatCoolDown;
/*      */   private int hunger;
/*      */   private int stun;
/*      */   private int lastDamage;
/*      */   private int armorEventLastTick;
/*      */   private int wasSprinting;
/*      */   private int tackleTimer;
/*      */   private boolean canUseGrappleShot;
/*      */   private boolean isUsingGrappleShot;
/*      */   private boolean canUsePileDrive;
/*      */   private boolean inCombat;
/*      */   private boolean isDead;
/*      */   private boolean canRegen;
/*      */   private boolean god;
/*      */   private boolean pvpstats;
/*      */   private boolean colorUp;
/*      */   private boolean canUseSkill;
/*      */   private boolean firstToggle;
/*      */   private boolean newbZone;
/*   64 */   private long defencePotionTimer = 0L;
/*   65 */   private long offencePotionTimer = 0L;
/*   66 */   private long pvpTimer = 0L;
/*   67 */   private long portalTimer = 0L;
/*      */   private Party party;
/*      */   private Invite invite;
/*      */   private Party ghostParty;
/*      */   private boolean partyChat;
/*      */   private boolean isStunned;
/*      */   private boolean usedSpeedSkill;
/*      */   public static Material check;
/*      */   public static Material underfoot;
/*   76 */   private String particleEffect = "";
/*   77 */   private double particleHeight = 0.2D;
/*   78 */   private ParticleEffect effect = null;
/*   79 */   private int particleCount = 0;
/*   80 */   private double particleSpeed = 0.0D;
/*      */   public boolean flyZone;
/*      */   private boolean duelContestant;
/*      */   private boolean duelZone;
/*      */   private boolean inEvent;
/*      */   private boolean eventGrace;
/*      */ 
/*      */   public PVPPlayer(Player player)
/*      */   {
/*   89 */     this.player = player;
/*   90 */     this.health = 500.0D;
/*   91 */     this.canRegen = true;
/*   92 */     this.healthLastTick = 500.0D;
/*   93 */     this.maxHealth = 500.0D;
/*   94 */     this.maxStamina = 100.0D;
/*   95 */     this.stamina = 100.0D;
/*   96 */     this.skillCoolDown = 0;
/*   97 */     this.cooldown = 0.0D;
/*   98 */     this.isDead = false;
/*   99 */     this.hitCoolDown = 0;
/*  100 */     this.hunger = player.getFoodLevel();
/*  101 */     this.inCombat = false;
/*  102 */     this.combatCoolDown = 0;
/*  103 */     this.armorEventLastTick = 0;
/*  104 */     this.lastDamage = 0;
/*  105 */     this.wasSprinting = 0;
/*  106 */     this.usedSpeedSkill = false;
/*  107 */     this.isStunned = false;
/*  108 */     this.healthPercent = 100.0D;
/*  109 */     this.comboReady = 0;
/*  110 */     this.colorUp = false;
/*  111 */     this.flyZone = false;
/*  112 */     this.duelContestant = false;
/*      */   }
/*      */ 
/*      */   public int tackleTimer() {
/*  116 */     return this.tackleTimer;
/*      */   }
/*      */ 
/*      */   public void setTackleTimer(int tackleTimer) {
/*  120 */     if (tackleTimer < 0)
/*      */     {
/*  122 */       this.tackleTimer = 0;
/*      */     }
/*      */     else
/*      */     {
/*  126 */       this.tackleTimer = tackleTimer;
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isUsingGrappleShot() {
/*  131 */     return this.isUsingGrappleShot;
/*      */   }
/*      */ 
/*      */   public void setIsUsingGrappleShot(boolean isUsingGrappleShot) {
/*  135 */     this.isUsingGrappleShot = isUsingGrappleShot;
/*      */   }
/*      */ 
/*      */   public Player getPlayerHitByGrapple() {
/*  139 */     return this.hitByGrapple;
/*      */   }
/*      */ 
/*      */   public void setGrapplePlayer(Player player) {
/*  143 */     this.hitByGrapple = player;
/*      */   }
/*      */ 
/*      */   public Arrow getGrappleArrow() {
/*  147 */     return this.grappleArrow;
/*      */   }
/*      */ 
/*      */   public void setGrappleArrow(Arrow grappleArrow) {
/*  151 */     this.grappleArrow = grappleArrow;
/*      */   }
/*      */ 
/*      */   public void setCanUseGrappleShot(boolean canUseGrappleShot) {
/*  155 */     this.canUseGrappleShot = canUseGrappleShot;
/*      */   }
/*      */ 
/*      */   public boolean canUseGrappleShot() {
/*  159 */     return this.canUseGrappleShot;
/*      */   }
/*      */ 
/*      */   public void setIsNewbZone(boolean newbZone) {
/*  163 */     this.newbZone = newbZone;
/*      */   }
/*      */ 
/*      */   public boolean isNewbZone() {
/*  167 */     return this.newbZone;
/*      */   }
/*      */   public int getCombatCooldown() {
/*  170 */     return this.combatCoolDown;
/*      */   }
/*      */   public void setCombatCooldown(int combatCooldown) {
/*  173 */     this.combatCoolDown = combatCooldown;
/*      */   }
/*      */ 
/*      */   public Location getGrappleStart() {
/*  177 */     return this.grappleStart;
/*      */   }
/*      */ 
/*      */   public Location getGrappleEnd() {
/*  181 */     return this.grappleEnd;
/*      */   }
/*      */ 
/*      */   public void setGrappleStart(Location grappleStart) {
/*  185 */     this.grappleStart = grappleStart;
/*      */   }
/*      */ 
/*      */   public void setGrappleEnd(Location grappleEnd) {
/*  189 */     this.grappleEnd = grappleEnd;
/*      */   }
/*      */ 
/*      */   public int getComboReady() {
/*  193 */     return this.comboReady;
/*      */   }
/*      */ 
/*      */   public void setComboReady(int comboReady) {
/*  197 */     int modifiedComboReady = comboReady;
/*  198 */     if (modifiedComboReady < 0) {
/*  199 */       modifiedComboReady = 0;
/*      */     }
/*  201 */     this.comboReady = modifiedComboReady;
/*      */   }
/*      */ 
/*      */   public double getHealthPercent() {
/*  205 */     return this.healthPercent;
/*      */   }
/*      */ 
/*      */   public void setHealthPercent(double percent) {
/*  209 */     this.health /= percent;
/*  210 */     this.healthPercent = percent;
/*      */   }
/*      */ 
/*      */   public boolean getUsedSpeedSkill() {
/*  214 */     return this.usedSpeedSkill;
/*      */   }
/*      */ 
/*      */   public void setUsedSpeedSkill(boolean usedSpeedSkill) {
/*  218 */     this.usedSpeedSkill = usedSpeedSkill;
/*      */   }
/*      */ 
/*      */   public boolean wasFirstToggle() {
/*  222 */     return this.firstToggle;
/*      */   }
/*      */ 
/*      */   public void setFirstToggle(boolean firstToggle) {
/*  226 */     this.firstToggle = firstToggle;
/*      */   }
/*      */ 
/*      */   public int getWasSprinting() {
/*  230 */     return this.wasSprinting;
/*      */   }
/*      */ 
/*      */   public void setWasSprinting(int wasSprinting) {
/*  234 */     this.wasSprinting = wasSprinting;
/*      */   }
/*      */ 
/*      */   public int getStun() {
/*  238 */     return this.stun;
/*      */   }
/*      */ 
/*      */   public void setStun(int stun) {
/*  242 */     this.stun = stun;
/*      */   }
/*      */ 
/*      */   public boolean isStunned() {
/*  246 */     return this.isStunned;
/*      */   }
/*      */ 
/*      */   public void setIsStunned(boolean isStunned) {
/*  250 */     this.isStunned = isStunned;
/*      */   }
/*      */ 
/*      */   public double getStamina() {
/*  254 */     return this.stamina;
/*      */   }
/*      */ 
/*      */   public double getMaxStamina() {
/*  258 */     return this.maxStamina;
/*      */   }
/*      */ 
/*      */   public void setStamina(int stamina) {
/*  262 */     if (stamina < 0)
/*      */     {
/*  264 */       this.stamina = 0.0D;
/*      */     }
/*  266 */     else if (getMaxStamina() < stamina)
/*      */     {
/*  268 */       this.stamina = getMaxStamina();
/*      */     }
/*      */     else
/*      */     {
/*  272 */       this.stamina = stamina;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setMaxStamina(int maxStamina)
/*      */   {
/*  278 */     this.maxStamina = maxStamina;
/*      */   }
/*      */ 
/*      */   public boolean canUseSkill() {
/*  282 */     return this.canUseSkill;
/*      */   }
/*      */ 
/*      */   public int getSkillCooldown() {
/*  286 */     return this.skillCoolDown;
/*      */   }
/*      */ 
/*      */   public void setSkillCooldown(int skillCoolDown) {
/*  290 */     this.skillCoolDown = skillCoolDown;
/*      */   }
/*      */ 
/*      */   public Player getPlayer() {
/*  294 */     return this.player;
/*      */   }
/*      */ 
/*      */   public double getCombatCoolDown()
/*      */   {
/*  299 */     return this.combatCoolDown;
/*      */   }
/*      */ 
/*      */   public double getCooldown()
/*      */   {
/*  304 */     return this.cooldown;
/*      */   }
/*      */ 
/*      */   public boolean isDead()
/*      */   {
/*  309 */     return this.isDead;
/*      */   }
/*      */ 
/*      */   public boolean isColorUp()
/*      */   {
/*  314 */     return this.colorUp;
/*      */   }
/*      */ 
/*      */   public double getHealthLastTick()
/*      */   {
/*  319 */     return this.healthLastTick;
/*      */   }
/*      */ 
/*      */   public int getMaxHealth()
/*      */   {
/*  324 */     return (int)this.maxHealth;
/*      */   }
/*      */ 
/*      */   public int gethealth()
/*      */   {
/*  329 */     return (int)this.health;
/*      */   }
/*      */ 
/*      */   public int getHunger()
/*      */   {
/*  334 */     return this.hunger;
/*      */   }
/*      */ 
/*      */   public boolean isGod()
/*      */   {
/*  339 */     return this.god;
/*      */   }
/*      */ 
/*      */   public double getHitCooldown()
/*      */   {
/*  344 */     return this.hitCoolDown;
/*      */   }
/*      */ 
/*      */   public int getArmorEventLastTick()
/*      */   {
/*  349 */     return this.armorEventLastTick;
/*      */   }
/*      */ 
/*      */   public int getLastDamage()
/*      */   {
/*  354 */     return this.lastDamage;
/*      */   }
/*      */ 
/*      */   public void setLastDamage(int lastDamage)
/*      */   {
/*  359 */     this.lastDamage = lastDamage;
/*      */   }
/*      */ 
/*      */   public void setGod(boolean god)
/*      */   {
/*  364 */     this.god = god;
/*      */   }
/*      */ 
/*      */   public void setArmorEventLastTick(int armorEventLastTick)
/*      */   {
/*  369 */     this.armorEventLastTick = armorEventLastTick;
/*      */   }
/*      */ 
/*      */   public void setCombatCoolDown(int combatCoolDown)
/*      */   {
/*  374 */     this.combatCoolDown = combatCoolDown;
/*      */   }
/*      */ 
/*      */   public void setHunger(int hunger)
/*      */   {
/*  379 */     if (getHunger() - hunger < 1)
/*      */     {
/*  381 */       this.hunger = 1;
/*      */     }
/*      */     else
/*      */     {
/*  385 */       this.hunger = hunger;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setCanUseSkill(boolean canUseSkill)
/*      */   {
/*  391 */     this.canUseSkill = canUseSkill;
/*      */   }
/*      */ 
/*      */   public void setHitCoolDown(int hitCoolDown) {
/*  395 */     this.hitCoolDown = hitCoolDown;
/*      */   }
/*      */ 
/*      */   public void setCooldown(double cooldown)
/*      */   {
/*  400 */     this.cooldown = cooldown;
/*      */   }
/*      */ 
/*      */   public void setIsDead(boolean isDead)
/*      */   {
/*  405 */     this.isDead = isDead;
/*      */   }
/*      */ 
/*      */   public void setColorUp(boolean value)
/*      */   {
/*  410 */     this.colorUp = value;
/*      */   }
/*      */ 
/*      */   public void setMaxHealth(double maxHealth)
/*      */   {
/*  415 */     if ((this.health == this.maxHealth) && (this.combatCoolDown < 1) && (this.canRegen) && (!this.inCombat))
/*      */     {
/*  417 */       this.maxHealth = maxHealth;
/*  418 */       sethealth(this.maxHealth);
/*  419 */       if (this.armorEventLastTick == 1)
/*      */       {
/*  421 */         this.player.sendMessage(ChatColor.GREEN + "[HEALTH]:" + ChatColor.YELLOW + " change in armor your new health is: " + ChatColor.GREEN + this.maxHealth);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  426 */       this.maxHealth = maxHealth;
/*  427 */       if (this.armorEventLastTick == 1)
/*      */       {
/*  429 */         this.player.sendMessage(ChatColor.GREEN + "[HEALTH]:" + ChatColor.YELLOW + " change in armor your new health is: " + ChatColor.GREEN + this.maxHealth);
/*  430 */         this.player.sendMessage(ChatColor.GREEN + "[HEALTH]:" + ChatColor.RED + "Due to recent combat you will gain life to your new max");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sethealth(double health)
/*      */   {
/*  438 */     if (this.pvpstats)
/*      */     {
/*  440 */       if (this.health > health)
/*      */       {
/*  442 */         double decreasedBy = this.health - health;
/*  443 */         if (decreasedBy > 10.0D)
/*      */         {
/*  445 */           this.player.sendMessage(ChatColor.YELLOW + "[HEALTH]: " + ChatColor.RED + "- " + decreasedBy);
/*      */         }
/*      */       }
/*  448 */       else if (this.health < health)
/*      */       {
/*  450 */         double increasedBy = health - this.health;
/*  451 */         if (increasedBy > 10.0D)
/*      */         {
/*  453 */           this.player.sendMessage(ChatColor.YELLOW + "[HEALTH]: " + ChatColor.GREEN + "+ " + increasedBy);
/*      */         }
/*      */       }
/*      */     }
/*  457 */     if (this.player.getGameMode() == GameMode.SURVIVAL)
/*      */     {
/*  459 */       if (health > this.maxHealth)
/*      */       {
/*  461 */         this.health = this.maxHealth;
/*      */       }
/*      */       else
/*      */       {
/*  465 */         this.health = health;
/*      */       }
/*  467 */       if (!this.isDead)
/*      */       {
/*  469 */         setProperHealth();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void uncheckedDamage(int dealtDamage)
/*      */   {
/*  477 */     if ((this.player.getGameMode().equals(GameMode.SURVIVAL)) && (!this.god))
/*      */     {
/*  479 */       sethealth(this.health - dealtDamage);
/*  480 */       if (this.health < 0.0D) {
/*  481 */         if (isInEvent()) {
/*  482 */           if (EventRunner.getActiveEvent().equalsIgnoreCase(SkyArrow.getEventName())) {
/*  483 */             SkyArrow.simulateDeath(this.player);
/*  484 */             return;
/*      */           }
/*  486 */           if (EventRunner.getActiveEvent().equalsIgnoreCase(CrazyRace.getEventName())) {
/*  487 */             CrazyRace.simulateDeath(this.player);
/*  488 */             return;
/*      */           }
/*      */         }
/*  491 */         this.health = 0.0D;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  496 */       sethealth(this.maxHealth);
/*  497 */       this.player.setFoodLevel(20);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean damage(int dealtDamage)
/*      */   {
/*  503 */     for (PotionEffect effect : getPlayer().getActivePotionEffects())
/*      */     {
/*  505 */       if (effect.getType() == PotionEffectType.DAMAGE_RESISTANCE)
/*      */       {
/*  507 */         int level = effect.getAmplifier();
/*  508 */         dealtDamage -= level * 12;
/*  509 */         if (dealtDamage < 0)
/*      */         {
/*  511 */           dealtDamage = 0;
/*      */         }
/*      */       }
/*      */     }
/*  515 */     if (this.player.getNoDamageTicks() <= 0)
/*      */     {
/*  517 */       this.lastDamage = dealtDamage;
/*  518 */       sethealth(this.health - dealtDamage);
/*  519 */       if ((gethealth() < 0) && 
/*  520 */         (isInEvent())) {
/*  521 */         if (EventRunner.getActiveEvent().equalsIgnoreCase(SkyArrow.getEventName())) {
/*  522 */           SkyArrow.simulateDeath(this.player);
/*  523 */           return false;
/*      */         }
/*  525 */         if (EventRunner.getActiveEvent().equalsIgnoreCase(CrazyRace.getEventName())) {
/*  526 */           CrazyRace.simulateDeath(this.player);
/*  527 */           return false;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  532 */     else if ((this.player.getNoDamageTicks() <= 10) && (this.player.getNoDamageTicks() >= 1))
/*      */     {
/*  534 */       if (this.lastDamage < dealtDamage)
/*      */       {
/*  536 */         this.lastDamage = dealtDamage;
/*  537 */         sethealth(this.health - (dealtDamage - this.lastDamage));
/*  538 */         if ((gethealth() < 0) && 
/*  539 */           (isInEvent())) {
/*  540 */           if (EventRunner.getActiveEvent().equalsIgnoreCase(SkyArrow.getEventName())) {
/*  541 */             SkyArrow.simulateDeath(this.player);
/*  542 */             return false;
/*      */           }
/*  544 */           if (EventRunner.getActiveEvent().equalsIgnoreCase(CrazyRace.getEventName())) {
/*  545 */             CrazyRace.simulateDeath(this.player);
/*  546 */             return false;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  553 */         return false;
/*      */       }
/*      */     }
/*  556 */     if ((!this.player.getGameMode().equals(GameMode.SURVIVAL)) || (this.god))
/*      */     {
/*  561 */       sethealth(this.maxHealth);
/*  562 */       this.player.setFoodLevel(20);
/*      */     }
/*  564 */     return true;
/*      */   }
/*      */ 
/*      */   public void tick()
/*      */   {
/*  569 */     if (this.player.isSneaking()) {
/*  570 */       this.canUseGrappleShot = false;
/*  571 */       this.canUsePileDrive = false;
/*  572 */       this.canUseSkill = false;
/*      */     }
/*  574 */     underfoot = this.player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType();
/*  575 */     check = this.player.getLocation().subtract(0.0D, this.player.getLocation().getY(), 0.0D).add(0.0D, 1.0D, 0.0D).getBlock().getType();
/*  576 */     if (this.player.getActivePotionEffects().contains(PotionEffectType.FAST_DIGGING)) {
/*  577 */       this.player.removePotionEffect(PotionEffectType.FAST_DIGGING);
/*      */     }
/*  579 */     if ((check == Material.GLASS) && (this.player.getWorld().getName().equals("world"))) {
/*  580 */       if (!isNewbZone()) {
/*  581 */         this.player.sendMessage(ChatColor.RED + "YOU ARE ENTERING A NEWBIE ZONE GEAR/DAMAGE RESTRICTED!");
/*  582 */         setIsNewbZone(true);
/*      */       }
/*      */ 
/*      */     }
/*  586 */     else if (isNewbZone()) {
/*  587 */       setIsNewbZone(false);
/*  588 */       this.player.sendMessage(ChatColor.RED + "YOU ARE LEAVING THE NEWBIE ZONE GEAR/DAMAGE UNLIMITED!");
/*      */     }
/*      */ 
/*  592 */     if ((check == Material.NETHER_BRICK) && (this.player.getWorld().getName().equals("world"))) {
/*  593 */       Duel.addContestant(this.player);
/*      */     }
/*  595 */     if ((check == Material.GLOWSTONE) && (this.player.getWorld().getName().equals("world"))) {
/*  596 */       if (!isDuelZone()) {
/*  597 */         this.player.sendMessage(ChatColor.RED + "YOU HAVE ENTERED A DUEL ZONE, IF YOU LEAVE WHILE IN A DUEL YOU WILL DIE AND LOSE!");
/*  598 */         setDuelZone(true);
/*  599 */         if ((this.player.hasPermission("essentials.socialspy")) || (this.player.isOp())) {
/*  600 */           this.player.sendMessage(ChatColor.GREEN + "ARENA ENTRY OVERRIDE YOUR LIFE HAS BEEN SPARED");
/*      */         }
/*      */       }
/*  603 */       if ((!Duel.checkContestant(this.player)) && (!this.player.isOp())) {
/*  604 */         kill();
/*      */       }
/*      */ 
/*      */     }
/*  609 */     else if (isDuelZone()) {
/*  610 */       setDuelZone(false);
/*  611 */       this.player.sendMessage(ChatColor.RED + "YOU ARE NO LONGER IN A DUEL ZONE");
/*      */     }
/*      */ 
/*  615 */     if ((check == Material.ENDER_STONE) && (this.player.getWorld().getName().equals("world"))) {
/*  616 */       if (!this.flyZone) {
/*  617 */         this.flyZone = true;
/*  618 */         this.player.sendMessage(ChatColor.RED + "YOU FEEL WEIGHTLESS!");
/*  619 */         this.player.setAllowFlight(true);
/*  620 */         this.player.setFlying(true);
/*      */       }
/*      */ 
/*      */     }
/*  624 */     else if (this.flyZone) {
/*  625 */       this.flyZone = false;
/*  626 */       this.player.sendMessage(ChatColor.RED + "YOU NO LONGER FEEL WEIGHTLESS AND COME CRASHING DOWN!");
/*      */     }
/*      */ 
/*  631 */     if (underfoot == Material.BEDROCK) {
/*  632 */       SuperSpeed.pathspeedOn(this.player);
/*      */     }
/*  634 */     if ((underfoot == Material.SPONGE) && (CrazyRace.checkParticipant(this.player.getName().toString())) && (CrazyRace.grace < 1) && 
/*  635 */       (this.player.getWorld().getName().equalsIgnoreCase(CrazyRace.world.getName()))) CrazyRace.setWinner(this.player);
/*      */ 
/*  637 */     if (this.tackleTimer > 0)
/*      */     {
/*  639 */       if ((getPlayer().getPassenger() != null) && ((getPlayer().getPassenger() instanceof Player)))
/*      */       {
/*  641 */         PVPPlayer rider = PvpHandler.getPvpPlayer((Player)getPlayer().getPassenger());
/*  642 */         rider.setStamina((int)rider.getStamina() - 10);
/*  643 */         if (rider.getStamina() < 7.0D)
/*      */         {
/*  645 */           getPlayer().getPassenger().eject();
/*      */         }
/*  647 */         this.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 500, 3));
/*      */       }
/*      */       else
/*      */       {
/*  651 */         setTackleTimer(0);
/*      */       }
/*      */     }
/*  654 */     if (((getComboReady() == 0) && (canUsePileDrive())) || ((getComboReady() == 0) && (this.canUseGrappleShot)))
/*      */     {
/*  656 */       if (this.canUseGrappleShot)
/*      */       {
/*  658 */         this.player.sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU NO LONGER FEEL READY TO GRAPPLE-SHOT!");
/*  659 */         setCanUseGrappleShot(false);
/*  660 */         setCanUsePileDrive(false);
/*      */       }
/*  662 */       if (this.canUsePileDrive)
/*      */       {
/*  664 */         setCanUsePileDrive(false);
/*  665 */         setCanUseGrappleShot(false);
/*  666 */         this.player.sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU NO LONGER FEEL READY TO PILEDRIVE!");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  671 */     if ((getComboReady() > 0) && (this.comboReady < 9)) {
/*  672 */       this.comboReady -= 1;
/*  673 */       if (getComboReady() >= 6) {
/*  674 */         setCanUsePileDrive(true);
/*      */       }
/*      */     }
/*  677 */     checkForGrapple();
/*  678 */     if ((getComboReady() >= 10) && (this.comboReady < 99))
/*      */     {
/*  680 */       setComboReady(getComboReady() - 10);
/*      */     }
/*  682 */     if ((getComboReady() >= 100) && (this.comboReady < 999))
/*      */     {
/*  684 */       setComboReady(getComboReady() - 100);
/*      */     }
/*  686 */     this.healthPercent = (this.health / this.maxHealth);
/*  687 */     if ((!this.player.getPlayer().getAllowFlight()) && (this.player.getWorld().getName().contains("world")) && (this.skillCoolDown == 0)) {
/*  688 */       this.player.setAllowFlight(true);
/*      */     }
/*  690 */     if (this.flyZone) {
/*  691 */       this.player.setAllowFlight(true);
/*      */     }
/*  693 */     if ((!this.flyZone) && (this.player.isFlying()) && (this.player.getGameMode() == GameMode.SURVIVAL) && (this.player.getWorld().getName().contains("world"))) {
/*  694 */       this.player.setAllowFlight(false);
/*  695 */       this.player.setFlying(false);
/*      */     }
/*  697 */     if ((this.player.getFoodLevel() < 1) && (this.health > 100.0D))
/*      */     {
/*  699 */       sethealth(this.health - 10.0D);
/*      */     }
/*  701 */     else if ((this.player.getFoodLevel() < 1) && (this.health <= 100.0D))
/*      */     {
/*  703 */       this.combatCoolDown = 20;
/*      */     }
/*  705 */     if (this.combatCoolDown > 0)
/*      */     {
/*  707 */       this.combatCoolDown -= 1;
/*      */     }
/*  709 */     else if (this.combatCoolDown < 0)
/*      */     {
/*  711 */       this.combatCoolDown = 0;
/*      */     }
/*  713 */     if (this.cooldown > 0.0D)
/*      */     {
/*  715 */       this.cooldown -= 1.0D;
/*      */     }
/*  717 */     else if (this.cooldown < 0.0D)
/*      */     {
/*  719 */       this.cooldown = 0.0D;
/*      */     }
/*  721 */     if (this.hitCoolDown > 0)
/*      */     {
/*  723 */       this.hitCoolDown -= 1;
/*      */     }
/*  725 */     else if (this.hitCoolDown < 0)
/*      */     {
/*  727 */       this.hitCoolDown = 0;
/*      */     }
/*  729 */     if (this.skillCoolDown > 0)
/*      */     {
/*  731 */       this.skillCoolDown -= 1;
/*  732 */       if (this.canUseSkill) {
/*  733 */         setCanUseSkill(false);
/*      */       }
/*      */     }
/*  736 */     else if (this.skillCoolDown == 0) {
/*  737 */       if (!this.canUseSkill)
/*      */       {
/*  739 */         setCanUseSkill(true);
/*      */       }
/*      */     }
/*  742 */     else if (this.skillCoolDown < 0)
/*      */     {
/*  744 */       this.skillCoolDown = 0;
/*      */     }
/*  746 */     if (this.wasSprinting > 0)
/*      */     {
/*  748 */       this.wasSprinting -= 1;
/*      */     }
/*  750 */     else if (this.wasSprinting < 0)
/*      */     {
/*  752 */       this.wasSprinting = 0;
/*      */     }
/*  754 */     if (this.player.isSprinting()) {
/*  755 */       this.wasSprinting = 2;
/*      */     }
/*  757 */     if (this.usedSpeedSkill) {
/*  758 */       setStamina((int)this.stamina - 5);
/*  759 */       if (this.stamina <= 4.0D)
/*      */       {
/*  761 */         this.player.sendMessage(ChatColor.RED + ChatColor.RED + "YOUR OUT OF STAMINA AND STOP SPRINTING!");
/*  762 */         SuperSpeed.speedOff(this.player);
/*      */       }
/*      */     }
/*  765 */     String message = "SIDEBAR,Health," + ChatColor.BLUE + "Health:" + ChatColor.RESET + "," + (int)this.health;
/*  766 */     Bukkit.getMessenger().dispatchIncomingMessage(this.player, "Scoreboard", message.getBytes());
/*  767 */     String message2 = "SIDEBAR,Health," + ChatColor.YELLOW + "Stamina:" + ChatColor.RESET + "," + (int)this.stamina;
/*  768 */     Bukkit.getMessenger().dispatchIncomingMessage(this.player, "Scoreboard", message2.getBytes());
/*  769 */     String message3 = "SIDEBAR,Health," + ChatColor.RED + "Attackable:" + ChatColor.RESET + "," + this.combatCoolDown;
/*  770 */     Bukkit.getMessenger().dispatchIncomingMessage(this.player, "Scoreboard", message3.getBytes());
/*  771 */     String message4 = "SIDEBAR,Health," + ChatColor.RED + "Till Skill:" + ChatColor.RESET + "," + this.skillCoolDown;
/*  772 */     Bukkit.getMessenger().dispatchIncomingMessage(this.player, "Scoreboard", message4.getBytes());
/*  773 */     if (this.player.getGameMode() == GameMode.CREATIVE)
/*      */     {
/*  775 */       this.health = this.maxHealth;
/*  776 */       this.canRegen = true;
/*  777 */       this.healthLastTick = this.maxHealth;
/*  778 */       this.cooldown = 0.0D;
/*  779 */       this.isDead = false;
/*  780 */       this.hitCoolDown = 0;
/*  781 */       this.inCombat = false;
/*  782 */       this.combatCoolDown = 0;
/*  783 */       this.armorEventLastTick = 0;
/*  784 */       return;
/*      */     }
/*  786 */     if (this.armorEventLastTick > 0)
/*      */     {
/*  788 */       this.armorEventLastTick = 0;
/*  789 */       Damage.calcArmor(this.player);
/*      */     }
/*  791 */     if (this.player.getHealth() <= 0.0D)
/*      */     {
/*  793 */       this.health = 0.0D;
/*      */     }
/*  795 */     if ((this.health <= 0.0D) && (!this.isDead))
/*      */     {
/*  797 */       if (this.player.getFireTicks() > 0)
/*      */       {
/*  799 */         this.player.setFireTicks(0);
/*      */       }
/*  801 */       this.player.getActivePotionEffects().removeAll(this.player.getActivePotionEffects());
/*  802 */       this.player.setHealth(0.0D);
/*  803 */       this.isDead = true;
/*  804 */       this.combatCoolDown = 0;
/*  805 */       this.hitCoolDown = 0;
/*  806 */       return;
/*      */     }
/*  808 */     if ((this.combatCoolDown > 0) || (this.isDead) || (this.player.getFoodLevel() < 1))
/*      */     {
/*  810 */       this.canRegen = false;
/*      */     }
/*      */     else
/*      */     {
/*  814 */       this.canRegen = true;
/*      */     }
/*  816 */     if ((this.combatCoolDown < 1) && (this.inCombat))
/*      */     {
/*  819 */       this.inCombat = false;
/*      */     }
/*  822 */     else if ((this.combatCoolDown >= 1) && (!this.inCombat))
/*      */     {
/*  824 */       this.inCombat = true;
/*      */     }
/*  826 */     if ((this.health < this.maxHealth) && (this.canRegen))
/*      */     {
/*  828 */       if (this.inCombat)
/*      */       {
/*  830 */         int heal = 45;
/*  831 */         PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(this.player, heal, EntityRegainHealthEvent.RegainReason.CUSTOM);
/*  832 */         Bukkit.getPluginManager().callEvent(pberh);
/*  833 */         if (pberh.isCancelled())
/*  834 */           return;
/*  835 */         sethealth(this.health + heal);
/*      */       }
/*      */       else
/*      */       {
/*  839 */         int heal = 64;
/*  840 */         PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(this.player, heal, EntityRegainHealthEvent.RegainReason.CUSTOM);
/*  841 */         Bukkit.getPluginManager().callEvent(pberh);
/*  842 */         if (pberh.isCancelled())
/*  843 */           return;
/*  844 */         sethealth(this.health + heal);
/*      */       }
/*      */     }
/*  847 */     if (getStamina() < getMaxStamina()) {
/*  848 */       setStamina((int)getStamina() + 1);
/*      */     }
/*  850 */     if (this.health > this.maxHealth)
/*      */     {
/*  852 */       this.health = this.maxHealth;
/*      */     }
/*  854 */     this.healthLastTick = this.health;
/*      */   }
/*      */ 
/*      */   public void setProperHealth()
/*      */   {
/*  859 */     if (!this.isDead)
/*      */     {
/*  861 */       double realHealth = this.health / (this.maxHealth / 20.0D);
/*  862 */       if (realHealth <= 1.0D)
/*      */       {
/*  864 */         realHealth = 1.0D;
/*      */       }
/*  866 */       if (realHealth > 20.0D)
/*      */       {
/*  868 */         realHealth = 20.0D;
/*      */       }
/*  870 */       this.player.setHealth(realHealth);
/*  871 */       SMHandler.setHealthBar(this.health / this.maxHealth, this.player);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isPvpstats()
/*      */   {
/*  877 */     return this.pvpstats;
/*      */   }
/*      */ 
/*      */   public void setPvpstats(boolean value)
/*      */   {
/*  882 */     this.pvpstats = value;
/*      */   }
/*      */ 
/*      */   public boolean isInCombat()
/*      */   {
/*  887 */     return this.inCombat;
/*      */   }
/*      */ 
/*      */   public void setInCombat(boolean value)
/*      */   {
/*  892 */     this.inCombat = value;
/*      */   }
/*      */ 
/*      */   public boolean canHit()
/*      */   {
/*  897 */     if (this.hitCoolDown > 0)
/*      */     {
/*  899 */       return false;
/*      */     }
/*  901 */     return true;
/*      */   }
/*      */ 
/*      */   public void update()
/*      */   {
/*  906 */     String message = "SIDEBAR,Health," + ChatColor.BLUE + "Health:" + ChatColor.RESET + "," + (int)this.health;
/*  907 */     Bukkit.getMessenger().dispatchIncomingMessage(this.player, "Scoreboard", message.getBytes());
/*  908 */     String message2 = "SIDEBAR,Health," + ChatColor.GREEN + "Till Regen:" + ChatColor.RESET + "," + this.combatCoolDown / 4;
/*  909 */     Bukkit.getMessenger().dispatchIncomingMessage(this.player, "Scoreboard", message2.getBytes());
/*  910 */     SMHandler.setHealthBar(this.health / this.maxHealth, this.player);
/*      */   }
/*      */ 
/*      */   public void setDefencePotionTimer(long time)
/*      */   {
/*  915 */     this.defencePotionTimer = time;
/*      */   }
/*      */ 
/*      */   public void setOffencePotionTimer(long time)
/*      */   {
/*  920 */     this.offencePotionTimer = time;
/*      */   }
/*      */ 
/*      */   public void setPortalTimer(long time)
/*      */   {
/*  925 */     this.portalTimer = time;
/*      */   }
/*      */ 
/*      */   public long getPortalTimer()
/*      */   {
/*  930 */     return this.portalTimer;
/*      */   }
/*      */ 
/*      */   public long getOffencePotionTimer()
/*      */   {
/*  935 */     return this.offencePotionTimer;
/*      */   }
/*      */ 
/*      */   public long getDefencePotionTimer()
/*      */   {
/*  940 */     return this.defencePotionTimer;
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/*  945 */     return this.player.getName();
/*      */   }
/*      */ 
/*      */   public long getPvpTimer()
/*      */   {
/*  950 */     return this.pvpTimer;
/*      */   }
/*      */ 
/*      */   public void setPvpTimer(long timer)
/*      */   {
/*  955 */     this.pvpTimer = timer;
/*      */   }
/*      */ 
/*      */   public boolean isInPVP()
/*      */   {
/*  960 */     Date d = new Date();
/*  961 */     if ((d.getTime() - this.pvpTimer) / 1000L <= PvpHandler.getPvpTimer())
/*      */     {
/*  963 */       return true;
/*      */     }
/*  965 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isInParty()
/*      */   {
/*  970 */     if (this.party == null)
/*      */     {
/*  972 */       return false;
/*      */     }
/*  974 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isLeader()
/*      */   {
/*  979 */     if (this.party == null)
/*  980 */       return false;
/*  981 */     return this.party.isLeader(this);
/*      */   }
/*      */ 
/*      */   public void sendMessage(String s)
/*      */   {
/*  986 */     this.player.sendMessage(s);
/*      */   }
/*      */ 
/*      */   public Party getParty()
/*      */   {
/*  991 */     return this.party;
/*      */   }
/*      */ 
/*      */   public void setInvite(Invite invite)
/*      */   {
/*  996 */     this.invite = invite;
/*      */   }
/*      */ 
/*      */   public void setParty(Party p)
/*      */   {
/* 1001 */     this.party = p;
/*      */   }
/*      */ 
/*      */   public boolean hasInvite()
/*      */   {
/* 1006 */     return this.invite != null;
/*      */   }
/*      */ 
/*      */   public void sendInvite(PVPPlayer target)
/*      */   {
/* 1011 */     Invite invite = new Invite(this);
/* 1012 */     target.setInvite(invite);
/* 1013 */     target.sendMessage(ChatColor.AQUA + this.player.getName() + ChatColor.GOLD + " Has Invited You To His Party");
/* 1014 */     target.sendMessage(ChatColor.GOLD + "Type " + ChatColor.AQUA + "/party accept " + ChatColor.GOLD + " To Accept Or " + ChatColor.AQUA + "/party decline" + ChatColor.GOLD + " To Decline");
/*      */   }
/*      */   public void kill() {
/* 1017 */     if (this.player.getFireTicks() > 0)
/*      */     {
/* 1019 */       this.player.setFireTicks(0);
/*      */     }
/* 1021 */     this.player.getActivePotionEffects().removeAll(this.player.getActivePotionEffects());
/* 1022 */     this.player.setHealth(0.0D);
/* 1023 */     this.isDead = true;
/* 1024 */     this.combatCoolDown = 0;
/* 1025 */     this.hitCoolDown = 0;
/*      */   }
/*      */ 
/*      */   public void accept()
/*      */   {
/* 1030 */     if (this.invite != null)
/*      */     {
/* 1032 */       this.invite.accept(this);
/* 1033 */       this.invite = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void decline()
/*      */   {
/* 1039 */     if (this.invite != null)
/*      */     {
/* 1041 */       this.invite.decline(this);
/* 1042 */       this.invite = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean canAccept()
/*      */   {
/* 1048 */     if (this.invite.getParty() == null)
/* 1049 */       return true;
/* 1050 */     if (this.invite.isPartyFull())
/* 1051 */       return false;
/* 1052 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean hasGhostParty()
/*      */   {
/* 1057 */     return this.ghostParty != null;
/*      */   }
/*      */ 
/*      */   public void setGhostParty(Party p)
/*      */   {
/* 1062 */     this.ghostParty = p;
/*      */   }
/*      */ 
/*      */   public boolean justLeft()
/*      */   {
/* 1067 */     return this.ghostParty != null;
/*      */   }
/*      */ 
/*      */   public Party getGhostParty()
/*      */   {
/* 1072 */     return this.ghostParty;
/*      */   }
/*      */ 
/*      */   public void setPartyChat(boolean value)
/*      */   {
/* 1077 */     this.partyChat = value;
/*      */   }
/*      */ 
/*      */   public boolean usesPartyChat()
/*      */   {
/* 1082 */     return this.partyChat;
/*      */   }
/*      */ 
/*      */   public boolean canUsePileDrive() {
/* 1086 */     return this.canUsePileDrive;
/*      */   }
/*      */ 
/*      */   public void setCanUsePileDrive(boolean canUsePileDrive) {
/* 1090 */     this.canUsePileDrive = canUsePileDrive;
/*      */   }
/*      */ 
/*      */   public void checkForGrapple() {
/* 1094 */     if (this.isUsingGrappleShot)
/*      */     {
/* 1096 */       if ((getGrappleStart() != null) && (getGrappleEnd() != null) && (this.hitByGrapple == null))
/*      */       {
/* 1098 */         GrappleShot.grappleShotBlockHit(getPlayer(), getGrappleEnd().getBlock(), this);
/* 1099 */         setCanUsePileDrive(false);
/*      */       }
/* 1101 */       if ((getGrappleStart() != null) && (this.hitByGrapple != null))
/*      */       {
/* 1103 */         GrappleShot.grappleShotPlayerHit(this.hitByGrapple, getPlayer(), this);
/* 1104 */         setCanUsePileDrive(false);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/* 1109 */   public String getParticleEffect() { return this.particleEffect; }
/*      */ 
/*      */   public void setParticleEffect(String particleEffect) {
/* 1112 */     this.particleEffect = particleEffect;
/*      */   }
/*      */   public int getParticleCount() {
/* 1115 */     return this.particleCount;
/*      */   }
/*      */   public void setParticleCount(int particleCount) {
/* 1118 */     this.particleCount = particleCount;
/*      */   }
/*      */   public double getParticleSpeed() {
/* 1121 */     return this.particleSpeed;
/*      */   }
/*      */   public void setParticleSpeed(double particleSpeed) {
/* 1124 */     this.particleSpeed = particleSpeed;
/*      */   }
/*      */   public ParticleEffect getEffect() {
/* 1127 */     return this.effect;
/*      */   }
/*      */   public void setEffect(ParticleEffect effect) {
/* 1130 */     this.effect = effect;
/*      */   }
/*      */   public double getParticleHeight() {
/* 1133 */     return this.particleHeight;
/*      */   }
/*      */   public void setParticleHeight(double particleHeight) {
/* 1136 */     this.particleHeight = particleHeight;
/*      */   }
/*      */   public boolean isDuelContestant() {
/* 1139 */     return this.duelContestant;
/*      */   }
/*      */   public void setDuelContestant(boolean duelContestant) {
/* 1142 */     this.duelContestant = duelContestant;
/*      */   }
/*      */   public boolean isDuelZone() {
/* 1145 */     return this.duelZone;
/*      */   }
/*      */   public void setDuelZone(boolean duelZone) {
/* 1148 */     this.duelZone = duelZone;
/*      */   }
/*      */   public boolean isInEvent() {
/* 1151 */     return this.inEvent;
/*      */   }
/*      */   public void setInEvent(boolean inEvent) {
/* 1154 */     this.inEvent = inEvent;
/*      */   }
/*      */   public boolean isEventGrace() {
/* 1157 */     return this.eventGrace;
/*      */   }
/*      */   public void setEventGrace(boolean eventGrace) {
/* 1160 */     this.eventGrace = eventGrace;
/*      */   }
/*      */   public Player getLastHitBy() {
/* 1163 */     return this.lastHitBy;
/*      */   }
/*      */   public void setLastHitBy(Player lastHitBy) {
/* 1166 */     this.lastHitBy = lastHitBy;
/*      */   }
/*      */   public Material underFoot() {
/* 1169 */     return underfoot;
/*      */   }
/*      */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.PVPPlayer
 * JD-Core Version:    0.6.2
 */