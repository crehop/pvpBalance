/*     */ package PvpBalance;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class PlayerParticles
/*     */ {
/*     */   public static void nextParticle(Player player)
/*     */   {
/*   8 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*   9 */     if (player.hasPermission("pvpbalance.particles2")) {
/*  10 */       pvp.setParticleEffect(getValidParticleString(pvp));
/*     */     }
/*  12 */     if (!PvpBalance.particulating.contains(player))
/*  13 */       PvpBalance.particulating.add(player);
/*     */   }
/*     */ 
/*     */   public static void incrimentParticleCount(Player player) {
/*  17 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  18 */     if (player.hasPermission("pvpbalance.particles2")) {
/*  19 */       pvp.setParticleCount(getValidParticleCount(pvp));
/*     */     }
/*  21 */     if (!PvpBalance.particulating.contains(player))
/*  22 */       PvpBalance.particulating.add(player);
/*     */   }
/*     */ 
/*     */   public static void incrimentParticleSpeed(Player player) {
/*  26 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/*  27 */     if (player.hasPermission("pvpbalance.particles2")) {
/*  28 */       pvp.setParticleSpeed(getValidParticleSpeed(pvp));
/*     */     }
/*  30 */     if (!PvpBalance.particulating.contains(player))
/*  31 */       PvpBalance.particulating.add(player);
/*     */   }
/*     */ 
/*     */   private static double getValidParticleSpeed(PVPPlayer pvp) {
/*  35 */     double current = pvp.getParticleSpeed();
/*  36 */     if (pvp.getParticleSpeed() < 0.16D) {
/*  37 */       current += 0.01D;
/*     */     }
/*  39 */     if (pvp.getParticleSpeed() > 0.15D) {
/*  40 */       current = 0.0D;
/*     */     }
/*  42 */     return current;
/*     */   }
/*     */   private static int getValidParticleCount(PVPPlayer pvp) {
/*  45 */     int current = pvp.getParticleCount();
/*  46 */     if (pvp.getParticleCount() < 31) {
/*  47 */       current += 10;
/*     */     }
/*  49 */     if (pvp.getParticleCount() > 30) {
/*  50 */       current = 0;
/*     */     }
/*  52 */     return current;
/*     */   }
/*     */   private static String getValidParticleString(PVPPlayer pvp) {
/*  55 */     String currentParticleString = pvp.getParticleEffect();
/*  56 */     if (currentParticleString == "") {
/*  57 */       currentParticleString = "FIREWORKS_SPARK";
/*  58 */       pvp.setEffect(ParticleEffect.FIREWORKS_SPARK);
/*  59 */       pvp.setParticleCount(10);
/*  60 */       pvp.setParticleSpeed(0.01D);
/*     */     }
/*  62 */     else if (currentParticleString == "FIREWORKS_SPARK") {
/*  63 */       currentParticleString = "TOWN_AURA";
/*  64 */       pvp.setEffect(ParticleEffect.TOWN_AURA);
/*     */     }
/*  66 */     else if (currentParticleString == "TOWN_AURA") {
/*  67 */       currentParticleString = "CRIT";
/*  68 */       pvp.setEffect(ParticleEffect.CRIT);
/*     */     }
/*  70 */     else if (currentParticleString == "CRIT") {
/*  71 */       currentParticleString = "MAGIC_CRIT";
/*  72 */       pvp.setEffect(ParticleEffect.MAGIC_CRIT);
/*     */     }
/*  74 */     else if (currentParticleString == "MAGIC_CRIT") {
/*  75 */       currentParticleString = "MOB_SPELL";
/*  76 */       pvp.setEffect(ParticleEffect.MOB_SPELL);
/*     */     }
/*  78 */     else if (currentParticleString == "MOB_SPELL") {
/*  79 */       currentParticleString = "MOB_SPELL_AMBIENT";
/*  80 */       pvp.setEffect(ParticleEffect.MOB_SPELL_AMBIENT);
/*     */     }
/*  82 */     else if (currentParticleString == "MOB_SPELL_AMBIENT") {
/*  83 */       currentParticleString = "SPELL";
/*  84 */       pvp.setEffect(ParticleEffect.SPELL);
/*     */     }
/*  86 */     else if (currentParticleString == "SPELL") {
/*  87 */       currentParticleString = "INSTANT_SPELL";
/*  88 */       pvp.setEffect(ParticleEffect.INSTANT_SPELL);
/*     */     }
/*  90 */     else if (currentParticleString == "INSTANT_SPELL") {
/*  91 */       currentParticleString = "WITCH_MAGIC";
/*  92 */       pvp.setEffect(ParticleEffect.WITCH_MAGIC);
/*     */     }
/*  94 */     else if (currentParticleString == "WITCH_MAGIC") {
/*  95 */       currentParticleString = "NOTE";
/*  96 */       pvp.setEffect(ParticleEffect.NOTE);
/*     */     }
/*  98 */     else if (currentParticleString == "NOTE") {
/*  99 */       currentParticleString = "PORTAL";
/* 100 */       pvp.setEffect(ParticleEffect.PORTAL);
/*     */     }
/* 102 */     else if (currentParticleString == "PORTAL") {
/* 103 */       pvp.setEffect(ParticleEffect.ENCHANTMENT_TABLE);
/* 104 */       currentParticleString = "ENCHANTMENT_TABLE";
/*     */     }
/* 106 */     else if (currentParticleString == "ENCHANTMENT_TABLE") {
/* 107 */       currentParticleString = "FLAME";
/* 108 */       pvp.setEffect(ParticleEffect.FLAME);
/*     */     }
/* 110 */     else if (currentParticleString == "FLAME") {
/* 111 */       currentParticleString = "LAVA";
/* 112 */       pvp.setEffect(ParticleEffect.LAVA);
/*     */     }
/* 114 */     else if (currentParticleString == "LAVA") {
/* 115 */       currentParticleString = "SPLASH";
/* 116 */       pvp.setEffect(ParticleEffect.SPLASH);
/*     */     }
/* 118 */     else if (currentParticleString == "SPLASH") {
/* 119 */       currentParticleString = "LARGE_SMOKE";
/* 120 */       pvp.setEffect(ParticleEffect.LARGE_SMOKE);
/*     */     }
/* 122 */     else if (currentParticleString == "LARGE_SMOKE") {
/* 123 */       currentParticleString = "CLOUD";
/* 124 */       pvp.setEffect(ParticleEffect.CLOUD);
/*     */     }
/* 126 */     else if (currentParticleString == "CLOUD") {
/* 127 */       currentParticleString = "RED_DUST";
/* 128 */       pvp.setEffect(ParticleEffect.RED_DUST);
/*     */     }
/* 130 */     else if (currentParticleString == "RED_DUST") {
/* 131 */       currentParticleString = "DRIP_WATER";
/* 132 */       pvp.setEffect(ParticleEffect.DRIP_WATER);
/*     */     }
/* 134 */     else if (currentParticleString == "DRIP_WATER") {
/* 135 */       currentParticleString = "DRIP_LAVA";
/* 136 */       pvp.setEffect(ParticleEffect.DRIP_LAVA);
/*     */     }
/* 138 */     else if (currentParticleString == "DRIP_LAVA") {
/* 139 */       currentParticleString = "HEART";
/* 140 */       pvp.setEffect(ParticleEffect.HEART);
/*     */     }
/* 142 */     else if (currentParticleString == "HEART") {
/* 143 */       currentParticleString = "ANGRY_VILLAGER";
/* 144 */       pvp.setEffect(ParticleEffect.ANGRY_VILLAGER);
/*     */     }
/* 146 */     else if (currentParticleString == "ANGRY_VILLAGER") {
/* 147 */       currentParticleString = "HAPPY_VILLAGER";
/* 148 */       pvp.setEffect(ParticleEffect.HAPPY_VILLAGER);
/*     */     }
/* 150 */     else if (currentParticleString == "HAPPY_VILLAGER") {
/* 151 */       currentParticleString = "";
/* 152 */       PvpBalance.particulating.remove(pvp.getPlayer());
/*     */     }
/* 154 */     return currentParticleString;
/*     */   }
/*     */   public static void incrimentParticleHeight(Player player) {
/* 157 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 158 */     if (player.hasPermission("pvpbalance.particles2")) {
/* 159 */       pvp.setParticleHeight(getValidParticleHeight(pvp));
/*     */     }
/* 161 */     if (!PvpBalance.particulating.contains(player))
/* 162 */       PvpBalance.particulating.add(player);
/*     */   }
/*     */ 
/*     */   private static double getValidParticleHeight(PVPPlayer pvp) {
/* 166 */     double current = pvp.getParticleHeight();
/* 167 */     if (pvp.getParticleHeight() < 5.1D) {
/* 168 */       current += 0.2D;
/*     */     }
/* 170 */     if (pvp.getParticleHeight() > 5.0D) {
/* 171 */       current = 0.0D;
/*     */     }
/* 173 */     return current;
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.PlayerParticles
 * JD-Core Version:    0.6.2
 */