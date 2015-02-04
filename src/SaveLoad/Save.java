/*    */ package SaveLoad;
/*    */ 
/*    */ import PvpBalance.PvpBalance;
/*    */ import java.io.File;
/*    */ import java.io.InputStream;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class Save
/*    */ {
/* 14 */   private FileConfiguration DataConfig = null;
/* 15 */   private File data = null;
/*    */   private PvpBalance dun;
/*    */   private String file;
/*    */   private File thefile;
/*    */ 
/*    */   public Save(PvpBalance dun, String newfile)
/*    */   {
/* 23 */     this.dun = dun;
/* 24 */     this.file = newfile;
/* 25 */     this.thefile = new File(dun.getDataFolder(), newfile);
/* 26 */     if (this.thefile.exists())
/*    */     {
/* 28 */       this.data = this.thefile;
/*    */     }
/* 30 */     reloadCustomConfig();
/* 31 */     saveCustomConfig();
/*    */   }
/*    */ 
/*    */   public void reloadCustomConfig()
/*    */   {
/* 36 */     if (this.data == null)
/*    */     {
/* 38 */       this.data = new File(this.dun.getDataFolder(), this.file);
/* 39 */       this.DataConfig = YamlConfiguration.loadConfiguration(this.data);
/* 40 */       InputStream defConfigStream = this.dun.getResource(this.file);
/* 41 */       if (defConfigStream != null)
/*    */       {
/* 43 */         YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
/* 44 */         this.DataConfig.setDefaults(defConfig);
/*    */       }
/* 46 */       getCustomConfig().options().copyDefaults(true);
/* 47 */       this.dun.getLogger().info(this.file + " did not exist! Generated a new one!");
/*    */     }
/*    */     else
/*    */     {
/* 51 */       this.DataConfig = YamlConfiguration.loadConfiguration(this.data);
/*    */     }
/*    */   }
/*    */ 
/*    */   public FileConfiguration getCustomConfig()
/*    */   {
/* 57 */     if (this.DataConfig == null)
/*    */     {
/* 59 */       reloadCustomConfig();
/*    */     }
/* 61 */     return this.DataConfig;
/*    */   }
/*    */ 
/*    */   public void saveCustomConfig()
/*    */   {
/* 66 */     if ((this.DataConfig == null) || (this.data == null))
/*    */     {
/* 68 */       return;
/*    */     }
/*    */     try
/*    */     {
/* 72 */       getCustomConfig().save(this.data);
/*    */     }
/*    */     catch (Exception ex)
/*    */     {
/* 76 */       this.dun.getLogger().log(Level.SEVERE, "Could not save config to " + this.data, ex);
/* 77 */       PvpBalance.logger.info("Save CustomConfig!");
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     SaveLoad.Save
 * JD-Core Version:    0.6.2
 */