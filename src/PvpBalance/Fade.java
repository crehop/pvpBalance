/*    */ package PvpBalance;
/*    */ 
/*    */ import Util.ItemUtils;
/*    */ import org.bukkit.Color;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class Fade
/*    */ {
/* 10 */   public static final Color CURSED = Color.fromRGB(0, 0, 0);
/* 11 */   public static final Color MITHRIL = Color.fromRGB(0, 0, 128);
/* 12 */   public static final Color FLAME = Color.fromRGB(255, 0, 22);
/* 13 */   public static final Color VERITE = Color.fromRGB(0, 255, 0);
/* 14 */   public static final Color PRIZED = Color.fromRGB(255, 255, 255);
/*    */ 
/*    */   public static int type(ItemStack item)
/*    */   {
/* 18 */     if (ItemUtils.getName(item).contains("Cursed"))
/*    */     {
/* 20 */       return 1;
/*    */     }
/* 22 */     if (ItemUtils.getName(item).contains("Mithril"))
/*    */     {
/* 24 */       return 2;
/*    */     }
/* 26 */     if (ItemUtils.getName(item).contains("Flame"))
/*    */     {
/* 28 */       return 3;
/*    */     }
/* 30 */     if (ItemUtils.getName(item).contains("Verite"))
/*    */     {
/* 32 */       return 4;
/*    */     }
/*    */ 
/* 36 */     return 5;
/*    */   }
/*    */ 
/*    */   public static void setBaseColor(ItemStack item)
/*    */   {
/* 43 */     if (ItemUtils.getName(item).contains("Cursed"))
/*    */     {
/* 45 */       ItemUtils.setColor(item, CURSED);
/*    */     }
/* 47 */     else if (ItemUtils.getName(item).contains("Mithril"))
/*    */     {
/* 49 */       ItemUtils.setColor(item, MITHRIL);
/*    */     }
/* 51 */     else if (ItemUtils.getName(item).contains("Flame"))
/*    */     {
/* 53 */       ItemUtils.setColor(item, FLAME);
/*    */     }
/* 55 */     else if (ItemUtils.getName(item).contains("Verite"))
/*    */     {
/* 57 */       ItemUtils.setColor(item, VERITE);
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.Fade
 * JD-Core Version:    0.6.2
 */