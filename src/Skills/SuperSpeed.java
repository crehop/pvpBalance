/*    */ package Skills;
/*    */ 
/*    */ import PvpBalance.PVPPlayer;
/*    */ import PvpBalance.PvpHandler;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ public class SuperSpeed
/*    */ {
/*    */   public static void speedOn(Player player)
/*    */   {
/* 15 */     player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "YOU SPRINT! PRESS " + ChatColor.AQUA + ChatColor.BOLD + "SNEAK" + ChatColor.GREEN + ChatColor.BOLD + " AGAIN TO STOP!");
/* 16 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 17 */     pvp.setUsedSpeedSkill(true);
/* 18 */     player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500, 3));
/*    */   }
/*    */ 
/*    */   public static void speedOff(Player player) {
/* 22 */     player.sendMessage(ChatColor.RED + ChatColor.BOLD + "YOU STOP SPRINTING!");
/* 23 */     PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
/* 24 */     pvp.setUsedSpeedSkill(false);
/* 25 */     player.setSprinting(false);
/* 26 */     player.removePotionEffect(PotionEffectType.SPEED);
/*    */   }
/*    */ 
/*    */   public static void pathspeedOn(Player player) {
/* 30 */     player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 4));
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Skills.SuperSpeed
 * JD-Core Version:    0.6.2
 */