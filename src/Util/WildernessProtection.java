/*    */ package Util;
/*    */ 
/*    */ import com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus;
/*    */ import com.palmergames.bukkit.towny.object.WorldCoord;
/*    */ import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class WildernessProtection
/*    */ {
/*    */   public static boolean checkForWilderness(Player player)
/*    */   {
/* 11 */     String status = PlayerCacheUtil.getTownBlockStatus(player, WorldCoord.parseWorldCoord(player)).toString();
/* 12 */     if (status == "UNCLAIMED_ZONE") {
/* 13 */       return true;
/*    */     }
/* 15 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean checkForProtectedBlock(Block block) {
/* 19 */     if ((block.getType() == Material.LOG) || (block.getType() == Material.LOG_2)) {
/* 20 */       return true;
/*    */     }
/* 22 */     if ((block.getType() == Material.LEAVES) || (block.getType() == Material.LEAVES_2)) {
/* 23 */       return true;
/*    */     }
/* 25 */     if ((block.getType() == Material.GRASS) || (block.getType() == Material.CACTUS) || (block.getType() == Material.LONG_GRASS) || (block.getType() == Material.PACKED_ICE) || (block.getType() == Material.RED_ROSE) || (block.getType() == Material.SAND) || (block.getType() == Material.SNOW_BLOCK) || (block.getType() == Material.WATER_LILY) || (block.getType() == Material.ICE) || (block.getType() == Material.YELLOW_FLOWER) || (block.getType() == Material.DOUBLE_PLANT) || (block.getType() == Material.HUGE_MUSHROOM_1) || (block.getType() == Material.HUGE_MUSHROOM_2) || (block.getType() == Material.MYCEL) || (block.getType() == Material.SANDSTONE)) {
/* 26 */       return true;
/*    */     }
/* 28 */     return false;
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Util.WildernessProtection
 * JD-Core Version:    0.6.2
 */