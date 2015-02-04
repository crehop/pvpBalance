/*    */ package Skills;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class Incapacitate
/*    */ {
/*    */   public static void Incapacitate(Player damagee, Player damager)
/*    */   {
/* 13 */     double d = 3.5D;
/* 14 */     PotionEffect potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 300, 3);
/* 15 */     PotionEffect potionEffect2 = new PotionEffect(PotionEffectType.BLINDNESS, 240, 4);
/* 16 */     PotionEffect potionEffect3 = new PotionEffect(PotionEffectType.SLOW, 200, 3);
/* 17 */     damagee.addPotionEffect(potionEffect);
/* 18 */     damagee.addPotionEffect(potionEffect2);
/* 19 */     damagee.addPotionEffect(potionEffect3);
/* 20 */     damager.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "YOU KNOCK BACK AND INCAPACITATED " + damagee.getDisplayName());
/* 21 */     damagee.sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU HAVE BEEN KNOCKED BACK AND INCAPACITATED BY " + damager.getDisplayName());
/* 22 */     float hForce = 1.5F;
/* 23 */     float vForce = 1.2F;
/* 24 */     Vector direction = damagee.getLocation().getDirection();
/* 25 */     Vector forward = direction.multiply(3);
/* 26 */     forward.multiply(-3.5D);
/* 27 */     Vector v = damagee.getLocation().toVector().subtract(damagee.getLocation().add(0.0D, 6.0D, 0.0D).toVector());
/* 28 */     v.add(forward);
/* 29 */     v.setY(5);
/* 30 */     v.normalize();
/* 31 */     v.multiply(hForce * d);
/* 32 */     v.setY(vForce * d);
/* 33 */     damagee.setVelocity(v);
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Skills.Incapacitate
 * JD-Core Version:    0.6.2
 */