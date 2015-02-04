/*    */ package Skills;
/*    */ 
/*    */ import PvpBalance.Effects;
/*    */ import PvpBalance.FireworkEffectPlayer;
/*    */ import PvpBalance.PVPPlayer;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Color;
/*    */ import org.bukkit.FireworkEffect;
/*    */ import org.bukkit.FireworkEffect.Builder;
/*    */ import org.bukkit.FireworkEffect.Type;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class GrappleShot
/*    */ {
/*    */   public static void grappleShotPlayerHit(Player damagee, Player damager, PVPPlayer pvp)
/*    */   {
/* 19 */     pvp.setStamina((int)(pvp.getStamina() - 25.0D));
/* 20 */     pvp.getPlayer().sendMessage(ChatColor.GREEN + ChatColor.BOLD + "YOU FIRE A GRAPPLE SHOT AND PULL " + damagee.getDisplayName() + " TO YOU!");
/* 21 */     Vector direction = damager.getLocation().add(0.0D, 6.0D, 0.0D).toVector().subtract(damagee.getLocation().toVector()).normalize();
/* 22 */     direction.multiply(3);
/* 23 */     damagee.setVelocity(direction);
/* 24 */     FireworkEffectPlayer player = new FireworkEffectPlayer();
/* 25 */     FireworkEffect pulled = FireworkEffect.builder().with(FireworkEffect.Type.CREEPER).withColor(Color.RED).withFade(Color.GRAY).build();
/*    */     try {
/* 27 */       player.playFirework(damagee.getWorld(), damagee.getLocation().add(0.0D, 1.0D, 0.0D), pulled);
/*    */     }
/*    */     catch (Exception e) {
/* 30 */       e.printStackTrace();
/*    */     }
/* 32 */     Effects.magicWhiteSwirls(damager);
/* 33 */     pvp.setGrappleEnd(null);
/* 34 */     pvp.setGrappleStart(null);
/* 35 */     pvp.setGrapplePlayer(null);
/* 36 */     pvp.setGrappleArrow(null);
/* 37 */     pvp.setCanUseGrappleShot(false);
/* 38 */     pvp.setIsUsingGrappleShot(false);
/*    */   }
/*    */ 
/*    */   public static void grappleShotBlockHit(Player shooter, Block hit, PVPPlayer pvp) {
/* 42 */     pvp.setStamina((int)(pvp.getStamina() - 25.0D));
/* 43 */     pvp.getPlayer().sendMessage(ChatColor.GREEN + ChatColor.BOLD + "YOU FIRE A GRAPPLE SHOT AND PULL YOURSELF TO THE BLOCK!");
/* 44 */     Location delta = shooter.getLocation().add(hit.getLocation().add(0.0D, 5.0D, 0.0D));
/* 45 */     Vector direction = delta.getDirection();
/* 46 */     direction.multiply(3);
/* 47 */     shooter.setVelocity(direction);
/* 48 */     Effects.magicWhiteSwirls(shooter);
/* 49 */     Effects.magicWhiteSwirls(shooter);
/* 50 */     Effects.magicWhiteSwirls(shooter);
/* 51 */     pvp.setGrappleEnd(null);
/* 52 */     pvp.setGrappleStart(null);
/* 53 */     pvp.setGrapplePlayer(null);
/* 54 */     pvp.setGrappleArrow(null);
/* 55 */     pvp.setCanUseGrappleShot(false);
/* 56 */     pvp.setIsUsingGrappleShot(false);
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Skills.GrappleShot
 * JD-Core Version:    0.6.2
 */