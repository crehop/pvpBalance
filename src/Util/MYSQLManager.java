/*     */ package Util;
/*     */ 
/*     */ import PvpBalance.PvpBalance;
/*     */ import code.husky.mysql.MySQL;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class MYSQLManager
/*     */ {
/*     */   private MySQL db;
/*     */ 
/*     */   public MYSQLManager(Plugin plugin)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setupDb()
/*     */     throws SQLException
/*     */   {
/*  23 */     this.db = new MySQL(PvpBalance.getPlugin(), "localhost", "3306", "pvpstats", "root", "root");
/*  24 */     this.db.openConnection();
/*  25 */     Statement statement = this.db.getConnection().createStatement();
/*  26 */     statement.executeUpdate("CREATE TABLE IF NOT EXISTS `pvpstats` (`Name` varchar(32), `Kills` int, `Deaths` int, `EpicKills` int,`EpicDeaths` int, `DuelsWon` int,`DuelsLost` int, `MinutesPlayed` int)");
/*  27 */     statement.close();
/*     */   }
/*     */ 
/*     */   public void closeDB() {
/*  31 */     this.db.closeConnection();
/*     */   }
/*     */   public void storeUserData(Player player, String key, int data) throws SQLException {
/*  34 */     String playerName = player.getName().toLowerCase();
/*  35 */     if (!this.db.checkConnection()) {
/*  36 */       this.db.openConnection();
/*     */     }
/*  38 */     Statement statement = this.db.getConnection().createStatement();
/*  39 */     int uses = getUserData(player, key);
/*  40 */     if (uses != -1) {
/*  41 */       statement.executeUpdate("UPDATE pvpstats SET " + key + " = '" + data + "' WHERE Name = '" + playerName + "';");
/*     */     }
/*     */     else {
/*  44 */       statement.executeUpdate("INSERT INTO pvpstats (Name, Kills, Deaths, EpicKills, EpicDeaths, DuelsWon, DuelsLost, MinutesPlayed) VALUES ('" + playerName + "', 0, 0, 0, 0, 0, 0, 0);");
/*     */     }
/*  46 */     statement.close();
/*     */   }
/*     */ 
/*     */   public int getUserData(Player player, String key) throws SQLException {
/*  50 */     int value = -1;
/*  51 */     String playerName = player.getName().toLowerCase();
/*  52 */     if (!this.db.checkConnection()) {
/*  53 */       this.db.openConnection();
/*     */     }
/*  55 */     Statement statement = this.db.getConnection().createStatement();
/*  56 */     ResultSet rs = statement.executeQuery("SELECT * FROM `pvpstats` WHERE `Name`='" + playerName + "';");
/*  57 */     if (rs.next()) {
/*  58 */       value = rs.getInt(key);
/*  59 */       statement.close();
/*  60 */       return value;
/*     */     }
/*  62 */     statement.close();
/*  63 */     return value;
/*     */   }
/*     */   public int getUserData(String playerNameString, String key) throws SQLException {
/*  66 */     int value = -1;
/*  67 */     String playerName = playerNameString.toLowerCase();
/*  68 */     if (!this.db.checkConnection()) {
/*  69 */       this.db.openConnection();
/*     */     }
/*  71 */     Statement statement = this.db.getConnection().createStatement();
/*  72 */     ResultSet rs = statement.executeQuery("SELECT * FROM `pvpstats` WHERE `Name`='" + playerName + "';");
/*  73 */     if (rs.next()) {
/*  74 */       value = rs.getInt(key);
/*  75 */       return value;
/*     */     }
/*  77 */     statement.close();
/*  78 */     return value;
/*     */   }
/*     */ 
/*     */   public void top10(Player requester) throws SQLException {
/*  82 */     int place = 0;
/*  83 */     int place1 = 0;
/*  84 */     int place2 = 0;
/*  85 */     Statement statement = this.db.getConnection().createStatement();
/*  86 */     requester.sendMessage(ChatColor.YELLOW + "=========== Kills TOP 5 ===========");
/*  87 */     ResultSet rs = statement.executeQuery("SELECT * FROM `pvpstats`.`pvpstats` ORDER BY `Kills` DESC LIMIT 5;");
/*  88 */     while (rs.next()) {
/*  89 */       requester.sendMessage(ChatColor.AQUA + ++place + ": " + rs.getString("Name") + " " + "Kills: " + rs.getInt("Kills") + " " + "Deaths: " + rs.getInt("Deaths"));
/*     */     }
/*  91 */     requester.sendMessage(ChatColor.GREEN + "========= Epic Kills TOP 5 =========");
/*  92 */     ResultSet rs1 = statement.executeQuery("SELECT * FROM `pvpstats`.`pvpstats` ORDER BY `EpicKills` DESC LIMIT 5;");
/*  93 */     while (rs1.next()) {
/*  94 */       requester.sendMessage(ChatColor.AQUA + ++place1 + ": " + "Name: " + rs1.getString("Name") + " " + "Epic Kills: " + rs1.getInt("EpicKills") + " " + "Epic Deaths: " + rs1.getInt("EpicDeaths"));
/*     */     }
/*  96 */     requester.sendMessage(ChatColor.RED + "========= Duels Won TOP 5 ==========");
/*  97 */     ResultSet rs2 = statement.executeQuery("SELECT * FROM `pvpstats`.`pvpstats` ORDER BY `DuelsWon` DESC LIMIT 5;");
/*  98 */     while (rs2.next()) {
/*  99 */       requester.sendMessage(ChatColor.AQUA + ++place2 + ": " + "Name: " + rs2.getString("Name") + " " + "Duels Won: " + rs2.getInt("DuelsWon") + " " + "Duels Lost: " + rs2.getInt("DuelsLost"));
/*     */     }
/* 101 */     statement.close();
/* 102 */     requester.sendMessage(ChatColor.YELLOW + "=====================================");
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Util.MYSQLManager
 * JD-Core Version:    0.6.2
 */