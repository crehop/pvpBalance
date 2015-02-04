/*    */ package Skills;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class SkillHandler
/*    */ {
/*    */   public static List<Player> getPlayers(int radius, LivingEntity mob)
/*    */   {
/* 14 */     List list = new ArrayList();
/* 15 */     List near = mob.getNearbyEntities(radius, radius, radius);
/* 16 */     for (Entity check : near) {
/* 17 */       if ((check instanceof Player))
/*    */       {
/* 19 */         list.add((Player)check);
/*    */       }
/*    */     }
/*    */ 
/* 23 */     return list;
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Skills.SkillHandler
 * JD-Core Version:    0.6.2
 */