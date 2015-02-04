/*     */ package Party;
/*     */ 
/*     */ import PvpBalance.PVPPlayer;
/*     */ import PvpBalance.PvpBalance;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class Party
/*     */ {
/*  15 */   private List<PVPPlayer> members = new ArrayList(5);
/*     */   private PVPPlayer leader;
/*     */ 
/*     */   public Party(PVPPlayer dp)
/*     */   {
/*  20 */     this.leader = dp;
/*  21 */     this.members.add(dp);
/*     */   }
/*     */ 
/*     */   public boolean isMember(PVPPlayer dp)
/*     */   {
/*  26 */     return this.members.contains(dp);
/*     */   }
/*     */ 
/*     */   public boolean isLeader(PVPPlayer dp)
/*     */   {
/*  31 */     return this.leader.equals(dp);
/*     */   }
/*     */ 
/*     */   public boolean addPlayer(PVPPlayer dp)
/*     */   {
/*  36 */     dp.setParty(this);
/*  37 */     if (this.members.add(dp))
/*     */     {
/*  39 */       for (PVPPlayer m : this.members)
/*     */       {
/*  41 */         if (!m.equals(dp))
/*     */         {
/*  43 */           m.sendMessage(ChatColor.AQUA + dp.getName() + ChatColor.GOLD + " Joined The Party");
/*     */         }
/*     */       }
/*  45 */       return true;
/*     */     }
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isFull()
/*     */   {
/*  52 */     return this.members.size() >= 5;
/*     */   }
/*     */ 
/*     */   public void checkDisband()
/*     */   {
/*  57 */     if (this.members.size() <= 1)
/*     */     {
/*  59 */       this.leader.sendMessage(ChatColor.GOLD + "Your Party Got Disbanded, Friendly Fire Will Stay Off For 10 More Seconds");
/*  60 */       this.leader.setParty(null);
/*  61 */       this.leader.setGhostParty(this);
/*  62 */       if (this.leader.usesPartyChat())
/*     */       {
/*  64 */         this.leader.setPartyChat(false);
/*  65 */         this.leader.sendMessage(ChatColor.DARK_AQUA + "Party Chat Disabled");
/*     */       }
/*     */ 
/*  68 */       Bukkit.getScheduler().scheduleSyncDelayedTask(PvpBalance.plugin, new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  73 */           Party.this.leader.setGhostParty(null);
/*     */         }
/*     */       }
/*     */       , 200L);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void leave(PVPPlayer dp)
/*     */   {
/*  82 */     this.members.remove(dp);
/*  83 */     dp.sendMessage(ChatColor.GOLD + "You Left The Party, Friendly Fire Will Stay Off For 10 More Seconds");
/*  84 */     for (PVPPlayer temp : this.members)
/*     */     {
/*  86 */       temp.sendMessage(ChatColor.AQUA + dp.getName() + ChatColor.GOLD + " Has Left The Party, Friendly Fire Will Stay Off For 10 More Seconds");
/*     */     }
/*  88 */     if (isLeader(dp))
/*     */     {
/*  90 */       Random object = new Random();
/*  91 */       this.leader = ((PVPPlayer)this.members.get(object.nextInt(this.members.size())));
/*  92 */       this.leader.sendMessage(ChatColor.GOLD + "You Are Now Leader Of The Party");
/*  93 */       for (PVPPlayer temp : this.members)
/*     */       {
/*  95 */         if (!isLeader(temp))
/*     */         {
/*  97 */           temp.sendMessage(ChatColor.AQUA + this.leader.getName() + ChatColor.GOLD + " Is Now Leader Of The Party");
/*     */         }
/*     */       }
/*     */     }
/* 100 */     checkDisband();
/*     */   }
/*     */ 
/*     */   public void kick(PVPPlayer dp)
/*     */   {
/* 105 */     this.members.remove(dp);
/* 106 */     dp.sendMessage(ChatColor.GOLD + "You Got Kicked From The Party, Friendly Fire Will Stay Off For 10 More Seconds");
/* 107 */     for (PVPPlayer temp : this.members)
/*     */     {
/* 109 */       temp.sendMessage(ChatColor.AQUA + dp.getName() + ChatColor.GOLD + " Got Kicked From The Party, Friendly Fire Will Stay Off For 10 More Seconds");
/*     */     }
/* 111 */     checkDisband();
/*     */   }
/*     */ 
/*     */   public void sendMessage(String sender, String msg)
/*     */   {
/* 116 */     for (PVPPlayer temp : this.members)
/*     */     {
/* 118 */       temp.sendMessage(ChatColor.DARK_AQUA + "[Party] " + ChatColor.GREEN + sender + ChatColor.DARK_AQUA + " " + msg);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Party.Party
 * JD-Core Version:    0.6.2
 */