/*     */ package PvpBalance;
/*     */ 
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class Effects
/*     */ {
/*  11 */   Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers();
/*     */ 
/*     */   public static void healEffect(Player player)
/*     */   {
/*     */     try {
/*  16 */       ParticleEffect.sendToLocation(ParticleEffect.HEART, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.3F, 0.62F, 0.3F, 0.65F, 150);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  20 */       e.printStackTrace();
/*  21 */       PvpBalance.logger.info("Effect igniteFirePlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void teleportGreen(Player player)
/*     */   {
/*     */     try
/*     */     {
/*  29 */       ParticleEffect.sendToLocation(ParticleEffect.HAPPY_VILLAGER, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.3F, 0.62F, 0.3F, 0.65F, 150);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  33 */       e.printStackTrace();
/*  34 */       PvpBalance.logger.info("Effect igniteFirePlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void magicWhiteSwirls(Player player)
/*     */   {
/*     */     try
/*     */     {
/*  42 */       ParticleEffect.sendToLocation(ParticleEffect.SPELL, player.getLocation(), 0.2F, 0.2F, 0.2F, 0.02F, 160);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  46 */       e.printStackTrace();
/*  47 */       PvpBalance.logger.info("Effect igniteFirePlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void igniteFirePlayers(Player player)
/*     */   {
/*     */     try
/*     */     {
/*  56 */       ParticleEffect.sendToLocation(ParticleEffect.FLAME, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.2F, 0.2F, 0.2F, 0.02F, 60);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  60 */       e.printStackTrace();
/*  61 */       PvpBalance.logger.info("Effect igniteFirePlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void igniteFireball(Entity object)
/*     */   {
/*     */     try
/*     */     {
/*  69 */       ParticleEffect.sendToLocation(ParticleEffect.FLAME, object.getLocation(), 0.1F, 0.1F, 0.1F, 0.04F, 160);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  73 */       e.printStackTrace();
/*  74 */       PvpBalance.logger.info("Effect igniteFireball!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void blockedPlayer(Player player)
/*     */   {
/*     */     try
/*     */     {
/*  82 */       ParticleEffect.sendToLocation(ParticleEffect.EXPLODE, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.2F, 0.2F, 0.2F, 0.02F, 13);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  86 */       e.printStackTrace();
/*  87 */       PvpBalance.logger.info("Effect igniteFirePlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectPoison(Player player)
/*     */   {
/*     */     try
/*     */     {
/*  95 */       ParticleEffect.sendCrackToLocation(true, 295, (byte)0, player.getLocation().add(0.0D, 1.3D, 0.0D), 0.2F, 0.2F, 0.2F, 100);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  99 */       e.printStackTrace();
/* 100 */       PvpBalance.logger.info("Effect poisonPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectBlind(Player player)
/*     */   {
/*     */     try
/*     */     {
/* 108 */       ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, player.getLocation().add(0.0D, 2.0D, 0.0D), 0.1F, 0.1F, 0.1F, 0.01F, 100);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 112 */       e.printStackTrace();
/* 113 */       PvpBalance.logger.info("Effect blindPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectSlow(Player player)
/*     */   {
/*     */     try
/*     */     {
/* 122 */       ParticleEffect.sendToLocation(ParticleEffect.CLOUD, player.getLocation().subtract(0.0D, 0.1D, 0.0D), 0.2F, -0.2F, 0.2F, 1.0E-004F, 100);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 126 */       e.printStackTrace();
/* 127 */       PvpBalance.logger.info("Effect SlowPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectSuperJump(Player player)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       ParticleEffect.sendToLocation(ParticleEffect.CLOUD, player.getLocation().subtract(0.0D, 0.1D, 0.0D), 0.4F, -0.4F, 0.4F, 0.004F, 100);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 139 */       e.printStackTrace();
/* 140 */       PvpBalance.logger.info("Effect SlowPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectWither(Player player)
/*     */   {
/*     */     try {
/* 147 */       ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.2F, 0.5F, 0.2F, 0.02F, 200);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 151 */       e.printStackTrace();
/* 152 */       PvpBalance.logger.info("Effect witherPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectSharpnessPlayers(Player player)
/*     */   {
/* 158 */     float enchantsO = 0.0F;
/* 159 */     enchantsO += player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
/* 160 */     enchantsO += player.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK) * 2.0F;
/* 161 */     enchantsO += player.getItemInHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT) * 2.0F;
/* 162 */     enchantsO += player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_INFINITE) * 3.0F;
/* 163 */     enchantsO += player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_FIRE) * 2.0F;
/* 164 */     enchantsO += player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
/* 165 */     float amount = enchantsO;
/*     */     try
/*     */     {
/* 168 */       ParticleEffect.sendToLocation(ParticleEffect.PORTAL, player.getLocation().add(0.0D, 0.2D, 0.0D), 0.2F, 0.2F, 0.2F, 0.15F, (int)amount * 5);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 172 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectSprintPlayers(Player player, float speed, int amount)
/*     */   {
/*     */     try
/*     */     {
/* 180 */       ParticleEffect.sendToLocation(ParticleEffect.FOOTSTEP, player.getLocation().add(0.0D, 0.02D, 0.0D), 0.2F, 0.0F, 0.2F, 0.15F, 1);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 184 */       e.printStackTrace();
/* 185 */       PvpBalance.logger.info("Effect sprintPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectSpeedPlayers(Player player, float speed, int amount)
/*     */   {
/*     */     try
/*     */     {
/* 193 */       ParticleEffect.sendToLocation(ParticleEffect.SMOKE, player.getLocation(), 0.3F, 0.2F, 0.3F, 0.03F, 70);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 197 */       e.printStackTrace();
/* 198 */       PvpBalance.logger.info("Effect speedPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectHealthPlayers(Player player, float speed, int amount)
/*     */   {
/*     */     try
/*     */     {
/* 206 */       ParticleEffect.sendToLocation(ParticleEffect.HEART, player.getLocation().add(0.0D, 2.2D, 0.0D), 0.0F, 0.0F, 0.0F, 0.01F, 3);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 210 */       e.printStackTrace();
/* 211 */       PvpBalance.logger.info("Effect healthPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void effectConfuse(Player player)
/*     */   {
/*     */     try
/*     */     {
/* 219 */       ParticleEffect.sendToLocation(ParticleEffect.CRIT, player.getLocation(), 0.0F, 1.0F, 0.0F, 0.02F, 6);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 223 */       e.printStackTrace();
/* 224 */       PvpBalance.logger.info("Effect confusePlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void bleed(Player player)
/*     */   {
/*     */     try
/*     */     {
/* 232 */       ParticleEffect.sendToLocation(ParticleEffect.DRIP_LAVA, player.getLocation(), 0.35F, 0.35F, 0.35F, 0.02F, 40);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 236 */       e.printStackTrace();
/* 237 */       PvpBalance.logger.info("Effect bleedPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void superSaien(Player player)
/*     */   {
/*     */     try
/*     */     {
/* 245 */       ParticleEffect.sendToLocation(ParticleEffect.FLAME, player.getLocation(), 0.3F, 0.3F, 0.3F, 0.025F, 70);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 249 */       e.printStackTrace();
/* 250 */       PvpBalance.logger.info("Effect supersaienPlayer!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void admin(Player player)
/*     */   {
/* 256 */     if (player.hasPermission("particle.admin"))
/*     */     {
/*     */       try
/*     */       {
/* 260 */         ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, player.getLocation(), 0.35F, 0.35F, 0.35F, 0.02F, 100);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 264 */         e.printStackTrace();
/* 265 */         PvpBalance.logger.info("Effect adminPlayer!");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void impactEffect(Location location) {
/*     */     try {
/* 272 */       ParticleEffect.sendToLocation(ParticleEffect.FLAME, location, 0.3F, 0.3F, 0.3F, 0.3F, 300);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 276 */       e.printStackTrace();
/* 277 */       PvpBalance.logger.info("Effect igniteFirePlayer!");
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.Effects
 * JD-Core Version:    0.6.2
 */