/*    */ package code.husky.sqlite;
/*    */ 
/*    */ import code.husky.Database;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.SQLException;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class SQLite2 extends Database
/*    */ {
/*    */   private final String dbLocation;
/*    */   private Connection connection;
/*    */ 
/*    */   public SQLite2(Plugin plugin, String dbLocation)
/*    */   {
/* 33 */     super(plugin);
/* 34 */     this.dbLocation = dbLocation;
/* 35 */     this.connection = null;
/*    */   }
/*    */ 
/*    */   public Connection openConnection()
/*    */   {
/* 40 */     File file = new File(this.dbLocation);
/* 41 */     if (!file.exists())
/*    */       try {
/* 43 */         file.createNewFile();
/*    */       } catch (IOException e) {
/* 45 */         this.plugin.getLogger().log(Level.SEVERE, "Unable to create database!");
/*    */       }
/*    */     try
/*    */     {
/* 49 */       Class.forName("org.sqlite.JDBC");
/* 50 */       this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.plugin.getDataFolder().toPath().toString() + "/" + this.dbLocation);
/*    */     } catch (SQLException e) {
/* 52 */       this.plugin.getLogger().log(Level.SEVERE, "Could not connect to SQLite server! because: " + e.getMessage());
/*    */     } catch (ClassNotFoundException e) {
/* 54 */       this.plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
/*    */     }
/* 56 */     return this.connection;
/*    */   }
/*    */ 
/*    */   public boolean checkConnection()
/*    */   {
/*    */     try {
/* 62 */       return !this.connection.isClosed();
/*    */     } catch (SQLException e) {
/* 64 */       e.printStackTrace();
/* 65 */     }return false;
/*    */   }
/*    */ 
/*    */   public Connection getConnection()
/*    */   {
/* 71 */     return this.connection;
/*    */   }
/*    */ 
/*    */   public void closeConnection()
/*    */   {
/* 76 */     if (this.connection != null)
/*    */       try {
/* 78 */         this.connection.close();
/*    */       } catch (SQLException e) {
/* 80 */         this.plugin.getLogger().log(Level.SEVERE, "Error closing the SQLite Connection!");
/* 81 */         e.printStackTrace();
/*    */       }
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     code.husky.sqlite.SQLite2
 * JD-Core Version:    0.6.2
 */