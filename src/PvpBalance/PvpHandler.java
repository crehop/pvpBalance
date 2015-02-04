/*     */ package PvpBalance;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class PvpHandler
/*     */ {
/*  13 */   private static List<PVPPlayer> players = new ArrayList();
/*     */   private static int pvpTimer;
/*     */   private static int defencePotionCD;
/*     */   private static int offencePotionCD;
/*     */ 
/*     */   public static PVPPlayer getPvpPlayer(Player player)
/*     */   {
/*  20 */     for (PVPPlayer pp : players)
/*     */     {
/*  22 */       if (pp != null)
/*     */       {
/*  24 */         if (pp.getPlayer() == player)
/*  25 */           return pp; 
/*     */       }
/*     */     }
/*  27 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean isInList(PVPPlayer pp) {
/*  31 */     for (PVPPlayer pp1 : players)
/*     */     {
/*  33 */       if (pp1 != null)
/*     */       {
/*  37 */         if (pp1.getPlayer().getDisplayName() == pp.getPlayer().getDisplayName())
/*     */         {
/*  39 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   public static void addPvpPlayer(PVPPlayer pp)
/*     */   {
/*  47 */     if (!isInList(pp))
/*  48 */       players.add(pp);
/*     */   }
/*     */ 
/*     */   public static boolean removePvpPlayer(PVPPlayer pp)
/*     */   {
/*  54 */     return players.remove(pp);
/*     */   }
/*     */ 
/*     */   public static boolean removePvpPlayer(Player player)
/*     */   {
/*  59 */     for (PVPPlayer pp : players)
/*     */     {
/*  61 */       if (pp != null)
/*     */       {
/*  63 */         if (pp.getPlayer().equals(player))
/*  64 */           return players.remove(pp); 
/*     */       }
/*     */     }
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   public static List<PVPPlayer> getPvpPlayers()
/*     */   {
/*  71 */     return players;
/*     */   }
/*     */ 
/*     */   public static void clear()
/*     */   {
/*  76 */     players.clear();
/*     */   }
/*     */ 
/*     */   public static int getDefencePotionCD()
/*     */   {
/*  81 */     return defencePotionCD;
/*     */   }
/*     */ 
/*     */   public static int getOffencePotionCD()
/*     */   {
/*  86 */     return offencePotionCD;
/*     */   }
/*     */ 
/*     */   public static int getPvpTimer()
/*     */   {
/*  91 */     return pvpTimer;
/*     */   }
/*     */ 
/*     */   public static void load()
/*     */   {
/*  96 */     File folder = new File("plugins/PvpBalance");
/*  97 */     if (!folder.exists())
/*     */     {
/*  99 */       folder.mkdirs();
/*     */     }
/* 101 */     File tempFile = new File("plugins/PvpBalance/pvp.yml");
/* 102 */     if (!tempFile.exists())
/*     */     {
/*     */       try
/*     */       {
/* 106 */         tempFile.createNewFile();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 110 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 113 */     FileConfiguration config = new YamlConfiguration();
/*     */     try
/*     */     {
/* 116 */       config.load(tempFile);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 120 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 123 */     defencePotionCD = config.getInt("DefencePotionCD");
/* 124 */     offencePotionCD = config.getInt("OffencePotionCD");
/* 125 */     pvpTimer = config.getInt("PVPTimer");
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.PvpHandler
 * JD-Core Version:    0.6.2
 */