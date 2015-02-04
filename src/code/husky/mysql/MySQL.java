/*     */ package code.husky.mysql;
/*     */ 
/*     */ import code.husky.Database;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class MySQL extends Database
/*     */ {
/*     */   private final String user;
/*     */   private final String database;
/*     */   private final String password;
/*     */   private final String port;
/*     */   private final String hostname;
/*     */   private Connection connection;
/*     */ 
/*     */   public MySQL(Plugin plugin, String hostname, String port, String database, String username, String password)
/*     */   {
/*  46 */     super(plugin);
/*  47 */     this.hostname = hostname;
/*  48 */     this.port = port;
/*  49 */     this.database = database;
/*  50 */     this.user = username;
/*  51 */     this.password = password;
/*  52 */     this.connection = null;
/*     */   }
/*     */ 
/*     */   public Connection openConnection()
/*     */   {
/*     */     try {
/*  58 */       Class.forName("com.mysql.jdbc.Driver");
/*  59 */       this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
/*     */     } catch (SQLException e) {
/*  61 */       this.plugin.getLogger().log(Level.SEVERE, "Could not connect to MySQL server! because: " + e.getMessage());
/*     */     } catch (ClassNotFoundException e) {
/*  63 */       this.plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
/*     */     }
/*  65 */     return this.connection;
/*     */   }
/*     */ 
/*     */   public boolean checkConnection()
/*     */   {
/*  70 */     return this.connection != null;
/*     */   }
/*     */ 
/*     */   public Connection getConnection()
/*     */   {
/*  75 */     return this.connection;
/*     */   }
/*     */ 
/*     */   public void closeConnection()
/*     */   {
/*  80 */     if (this.connection != null)
/*     */       try {
/*  82 */         this.connection.close();
/*     */       } catch (SQLException e) {
/*  84 */         this.plugin.getLogger().log(Level.SEVERE, "Error closing the MySQL Connection!");
/*  85 */         e.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   public ResultSet querySQL(String query)
/*     */   {
/*  91 */     Connection c = null;
/*     */ 
/*  93 */     if (checkConnection())
/*  94 */       c = getConnection();
/*     */     else {
/*  96 */       c = openConnection();
/*     */     }
/*     */ 
/*  99 */     Statement s = null;
/*     */     try
/*     */     {
/* 102 */       s = c.createStatement();
/*     */     } catch (SQLException e1) {
/* 104 */       e1.printStackTrace();
/*     */     }
/*     */ 
/* 107 */     ResultSet ret = null;
/*     */     try
/*     */     {
/* 110 */       ret = s.executeQuery(query);
/*     */     } catch (SQLException e) {
/* 112 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 115 */     closeConnection();
/*     */ 
/* 117 */     return ret;
/*     */   }
/*     */ 
/*     */   public void updateSQL(String update)
/*     */   {
/* 122 */     Connection c = null;
/*     */ 
/* 124 */     if (checkConnection())
/* 125 */       c = getConnection();
/*     */     else {
/* 127 */       c = openConnection();
/*     */     }
/*     */ 
/* 130 */     Statement s = null;
/*     */     try
/*     */     {
/* 133 */       s = c.createStatement();
/* 134 */       s.executeUpdate(update);
/*     */     } catch (SQLException e1) {
/* 136 */       e1.printStackTrace();
/*     */     }
/*     */ 
/* 139 */     closeConnection();
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     code.husky.mysql.MySQL
 * JD-Core Version:    0.6.2
 */