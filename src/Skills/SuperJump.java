/*    */ package Skills;
/*    */ 
/*    */ import PvpBalance.Effects;
/*    */ import PvpBalance.PVPPlayer;
/*    */ import PvpBalance.PvpHandler;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class SuperJump
/*    */ {
/*    */   public static void Jump(Player player, double d)
/*    */   {
/* 15 */     String doubleJump = "DOUBLE JUMP!";
/* 16 */     float hForce = 1.5F;
/* 17 */     float vForce = 1.2F;
/* 18 */     Vector direction = player.getLocation().getDirection();
/* 19 */     Vector forward = direction.multiply(3);
/* 20 */     if (PvpHandler.getPvpPlayer(player).getUsedSpeedSkill())
/*    */     {
/* 22 */       doubleJump = "SPRINTING DOUBLE JUMP!";
/* 23 */       forward.multiply(2.5D);
/*    */     }
/* 25 */     Vector v = player.getLocation().toVector().subtract(player.getLocation().add(0.0D, 3.0D, 0.0D).toVector());
/* 26 */     v.add(forward);
/* 27 */     v.setY(5);
/* 28 */     v.normalize();
/* 29 */     v.multiply(hForce * d);
/* 30 */     v.setY(vForce * d);
/* 31 */     player.setVelocity(v);
/* 32 */     List list = SkillHandler.getPlayers(20, player);
/* 33 */     if (!list.isEmpty())
/*    */     {
/* 35 */       for (Player lplayer : list) {
/* 36 */         lplayer.playSound(lplayer.getLocation(), Sound.HORSE_JUMP, 3.0F, 0.533F);
/*    */       }
/*    */     }
/* 39 */     Effects.effectSuperJump(player);
/* 40 */     Effects.effectSuperJump(player);
/* 41 */     Effects.effectSuperJump(player);
/* 42 */     player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + doubleJump);
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Skills.SuperJump
 * JD-Core Version:    0.6.2
 */