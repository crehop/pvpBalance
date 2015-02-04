/*    */ package Skills;
/*    */ 
/*    */ import PvpBalance.Damage;
/*    */ import PvpBalance.FireworkEffectPlayer;
/*    */ import PvpBalance.PVPPlayer;
/*    */ import PvpBalance.PvpBalance;
/*    */ import PvpBalance.PvpHandler;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Color;
/*    */ import org.bukkit.FireworkEffect;
/*    */ import org.bukkit.FireworkEffect.Builder;
/*    */ import org.bukkit.FireworkEffect.Type;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Chicken;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ public class PileDrive
/*    */ {
/*    */   public static boolean clear(Player damagee)
/*    */   {
/* 24 */     int check = 0;
/* 25 */     if (!correctType(damagee.getLocation().add(1.0D, 0.0D, 0.0D).getBlock()))
/*    */     {
/* 30 */       check++;
/*    */     }
/* 32 */     if (!correctType(damagee.getLocation().subtract(1.0D, 0.0D, 0.0D).getBlock()))
/*    */     {
/* 37 */       check++;
/*    */     }
/* 39 */     if (!correctType(damagee.getLocation().add(0.0D, 0.0D, 1.0D).getBlock()))
/*    */     {
/* 44 */       check++;
/*    */     }
/* 46 */     if (!correctType(damagee.getLocation().subtract(0.0D, 0.0D, 1.0D).getBlock()))
/*    */     {
/* 51 */       check++;
/*    */     }
/* 53 */     if (!correctType(damagee.getLocation().add(1.0D, 0.0D, 0.0D).subtract(0.0D, 0.0D, 1.0D).getBlock()))
/*    */     {
/* 58 */       check += 10;
/*    */     }
/* 60 */     if (!correctType(damagee.getLocation().add(1.0D, 0.0D, 1.0D).getBlock()))
/*    */     {
/* 65 */       check += 10;
/*    */     }
/* 67 */     if (!correctType(damagee.getLocation().subtract(1.0D, 0.0D, 0.0D).add(0.0D, 0.0D, 1.0D).getBlock()))
/*    */     {
/* 72 */       check++;
/*    */     }
/* 74 */     if (!correctType(damagee.getLocation().subtract(1.0D, 0.0D, 1.0D).getBlock()))
/*    */     {
/* 79 */       check += 10;
/*    */     }
/* 81 */     if (check == 0)
/*    */     {
/* 83 */       return true;
/*    */     }
/* 85 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean correctType(Block check) {
/* 89 */     if ((check.getType() == Material.AIR) || (check.getType() == Material.LONG_GRASS) || (check.getType() == Material.RED_ROSE) || (check.getType() == Material.YELLOW_FLOWER) || (check.getType() == Material.GRASS))
/*    */     {
/* 91 */       return true;
/*    */     }
/* 93 */     return false;
/*    */   }
/*    */ 
/*    */   public static void pileDrive(Player damagee, Player damager) {
/* 97 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(damagee);
/* 98 */     PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
/* 99 */     if (pvpDamager.getStamina() >= 51.0D)
/*    */     {
/* 101 */       PotionEffect potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 140, 2);
/* 102 */       PotionEffect potionEffect2 = new PotionEffect(PotionEffectType.BLINDNESS, 80, 2);
/* 103 */       PotionEffect potionEffect3 = new PotionEffect(PotionEffectType.SLOW, 100, 2);
/* 104 */       damagee.sendMessage(ChatColor.RED + ChatColor.BOLD + " " + damager.getName().toUpperCase() + " USED PILEDRIVE ON YOU! PRESS SHIFT TO STAND UP!");
/* 105 */       damagee.addPotionEffect(potionEffect);
/* 106 */       damagee.addPotionEffect(potionEffect2);
/* 107 */       damagee.addPotionEffect(potionEffect3);
/* 108 */       pvp.damage(Damage.calcDamage(damager) * 2);
/* 109 */       pvpDamager.setComboReady(0);
/* 110 */       pvpDamager.setCanUsePileDrive(false);
/* 111 */       pvpDamager.setStamina((int)pvpDamager.getStamina() - 51);
/* 112 */       damager.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "YOU PILEDRIVE " + damagee.getDisplayName().toUpperCase() + "!!!");
/* 113 */       FireworkEffectPlayer fireworkPlayer = new FireworkEffectPlayer();
/* 114 */       FireworkEffect bonk = FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.YELLOW).withColor(Color.BLACK).flicker(true).withFade(Color.YELLOW).build();
/*    */       try {
/* 116 */         fireworkPlayer.playFirework(damagee.getWorld(), damagee.getEyeLocation(), bonk);
/*    */       } catch (Exception e) {
/* 118 */         e.printStackTrace();
/*    */       }
/* 120 */       if (clear(damagee))
/*    */       {
/* 122 */         Chicken chicken = (Chicken)damagee.getWorld().spawn(damagee.getLocation(), Chicken.class);
/* 123 */         chicken.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 100000));
/* 124 */         chicken.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 100000));
/* 125 */         chicken.setPassenger(damagee);
/* 126 */         PvpBalance.chickenList().add(chicken);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Skills.PileDrive
 * JD-Core Version:    0.6.2
 */