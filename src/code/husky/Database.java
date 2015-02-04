/*    */ package code.husky;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public abstract class Database
/*    */ {
/*    */   protected Plugin plugin;
/*    */ 
/*    */   protected Database(Plugin plugin)
/*    */   {
/* 27 */     this.plugin = plugin;
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
 * Qualified Name:     code.husky.Database
 * JD-Core Version:    0.6.2
 */