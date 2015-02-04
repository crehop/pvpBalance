/*    */ package Skills;
/*    */ 
/*    */ import PvpBalance.Damage;
/*    */ import PvpBalance.PVPPlayer;
/*    */ import PvpBalance.PvpBalance;
/*    */ import PvpBalance.PvpHandler;
/*    */ import com.palmergames.bukkit.towny.utils.CombatUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class Tackle
/*    */ {
/*    */   public static void tackle(Player player)
/*    */   {
/* 17 */     for (Entity near : player.getNearbyEntities(2.0D, 2.0D, 2.0D))
/* 18 */       if ((near instanceof Player))
/*    */       {
/* 20 */         if ((Damage.partyCanHit(near, player)) && (!CombatUtil.preventDamageCall(PvpBalance.getTowny(), near, player)))
/* 21 */           near.setPassenger(player);
/* 22 */         PVPPlayer pvpDamagee = PvpHandler.getPvpPlayer((Player)near);
/* 23 */         Player tackled = (Player)near;
/* 24 */         pvpDamagee.setTackleTimer(10);
/* 25 */         pvpDamagee.damage(Damage.calcDamage(player) * 2);
/* 26 */         player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "YOU TACKLE " + tackled.getDisplayName() + " CROUCH TO EXIT");
/* 27 */         ((Player)near).sendMessage(ChatColor.RED + ChatColor.BOLD + player.getDisplayName() + " HAS TACKLED YOU!");
/* 28 */         break;
/*    */       }
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Skills.Tackle
 * JD-Core Version:    0.6.2
 */