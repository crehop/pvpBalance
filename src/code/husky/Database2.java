/*    */ package code.husky;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public abstract class Database2
/*    */ {
/*    */   protected Plugin plugin;
/*    */ 
/*    */   protected void Database2(Plugin plugin)
/*    */   {
/* 28 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   public abstract Connection openConnection();
/*    */ 
/*    */   public abstract boolean checkConnection();
/*    */ 
/*    */   public abstract Connection getConnection();
/*    */ 
/*    */   public abstract void closeConnection();
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     code.husky.Database2
 * JD-Core Version:    0.6.2
 */