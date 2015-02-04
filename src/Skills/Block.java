/*    */ package Skills;
/*    */ 
/*    */ import PvpBalance.Effects;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class Block
/*    */ {
/*    */   public static void block(Player player)
/*    */   {
/* 12 */     double d = 0.9D;
/* 13 */     float hForce = 1.5F;
/* 14 */     float vForce = 1.2F;
/* 15 */     Vector direction = player.getLocation().getDirection();
/* 16 */     Vector forward = direction.multiply(-3);
/* 17 */     Vector v = player.getLocation().toVector().subtract(player.getLocation().add(0.0D, 0.0D, 0.0D).toVector());
/* 18 */     v.add(forward);
/* 19 */     v.setY(0.7D);
/* 20 */     v.normalize();
/* 21 */     v.multiply(hForce * d);
/* 22 */     v.setY(vForce * d);
/* 23 */     player.setVelocity(v);
/* 24 */     Effects.blockedPlayer(player);
/* 25 */     player.sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU HAVE BEEN BLOCKED AND FORCED BACK!");
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Skills.Block
 * JD-Core Version:    0.6.2
 */