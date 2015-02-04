/*     */ package Party;
/*     */ 
/*     */ import PvpBalance.PVPPlayer;
/*     */ import PvpBalance.PvpBalance;
/*     */ import PvpBalance.PvpHandler;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class CommandParty
/*     */   implements CommandExecutor
/*     */ {
/*     */   private PvpBalance dun;
/*     */ 
/*     */   public CommandParty(PvpBalance dun)
/*     */   {
/*  20 */     this.dun = dun;
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
/*     */   {
/*  25 */     Player player = (Player)sender;
/*  26 */     if ((commandLabel.equalsIgnoreCase("party")) || (commandLabel.equalsIgnoreCase("p")))
/*     */     {
/*  28 */       if (args.length == 0)
/*     */       {
/*  30 */         PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/*  31 */         if (!dp.isInParty())
/*     */         {
/*  33 */           dp.sendMessage(ChatColor.RED + "You Need To Be In A Party To Use The Party Chat");
/*  34 */           return false;
/*     */         }
/*  36 */         if (dp.usesPartyChat())
/*     */         {
/*  38 */           dp.setPartyChat(false);
/*  39 */           dp.sendMessage(ChatColor.DARK_AQUA + "Party Chat Disabled");
/*     */         }
/*     */         else
/*     */         {
/*  43 */           dp.setPartyChat(true);
/*  44 */           dp.sendMessage(ChatColor.DARK_AQUA + "Party Chat Enabled");
/*     */         }
/*     */       }
/*  47 */       else if (args.length == 1)
/*     */       {
/*  49 */         if (args[0].equalsIgnoreCase("accept"))
/*     */         {
/*  51 */           PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/*  52 */           if (!dp.hasInvite())
/*     */           {
/*  54 */             dp.sendMessage(ChatColor.RED + "You Dont Have An Invitation Right Now!");
/*  55 */             return false;
/*     */           }
/*  57 */           if (!dp.canAccept())
/*     */           {
/*  59 */             dp.sendMessage(ChatColor.RED + "That Party Is Full!");
/*  60 */             dp.setInvite(null);
/*  61 */             return false;
/*     */           }
/*  63 */           dp.accept();
/*  64 */           dp.sendMessage(ChatColor.GOLD + "You Joined The Party, Type " + ChatColor.AQUA + "/party leave" + ChatColor.GOLD + " To Leave The Party");
/*     */         }
/*  66 */         else if (args[0].equalsIgnoreCase("decline"))
/*     */         {
/*  68 */           PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/*  69 */           if (!dp.hasInvite())
/*     */           {
/*  71 */             dp.sendMessage(ChatColor.RED + "You Dont Have An Invitation Right Now!");
/*  72 */             return false;
/*     */           }
/*  74 */           dp.decline();
/*  75 */           dp.sendMessage(ChatColor.GOLD + "You Declined The Invitation");
/*     */         }
/*  77 */         else if (args[0].endsWith("leave"))
/*     */         {
/*  79 */           final PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/*  80 */           if (!dp.isInParty())
/*     */           {
/*  82 */             dp.sendMessage(ChatColor.RED + "You Are Not In A Party!");
/*  83 */             return false;
/*     */           }
/*  85 */           dp.getParty().leave(dp);
/*  86 */           dp.setGhostParty(dp.getParty());
/*  87 */           dp.setParty(null);
/*  88 */           if (dp.usesPartyChat())
/*     */           {
/*  90 */             dp.setPartyChat(false);
/*  91 */             dp.sendMessage(ChatColor.DARK_AQUA + "Party Chat Disabled");
/*     */           }
/*     */ 
/*  94 */           Bukkit.getScheduler().scheduleSyncDelayedTask(this.dun, new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*  98 */               dp.setGhostParty(null);
/*     */             }
/*     */           }
/*     */           , 200L);
/*     */         }
/*     */       }
/* 104 */       else if (args.length == 2)
/*     */       {
/* 106 */         if ((args[0].equalsIgnoreCase("invite")) || (args[0].equalsIgnoreCase("inv")))
/*     */         {
/* 108 */           Player target = this.dun.getServer().getPlayer(args[1]);
/* 109 */           if (target == null)
/*     */           {
/* 111 */             player.sendMessage(ChatColor.RED + "Player Not Found!");
/* 112 */             return false;
/*     */           }
/* 114 */           PVPPlayer dpTarget = PvpHandler.getPvpPlayer(target);
/* 115 */           if (dpTarget.isInParty())
/*     */           {
/* 117 */             player.sendMessage(ChatColor.RED + "That Player Is Already In A Party!");
/* 118 */             return false;
/*     */           }
/* 120 */           PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/* 121 */           if (dp.isInParty())
/*     */           {
/* 123 */             if (dp.isLeader())
/*     */             {
/* 125 */               if (dp.getParty().isFull())
/*     */               {
/* 127 */                 dp.sendMessage(ChatColor.RED + "You Cannot Invite More People To Your Party It Is Full!");
/* 128 */                 return false;
/*     */               }
/*     */ 
/* 135 */               dp.sendInvite(dpTarget);
/* 136 */               dp.sendMessage(ChatColor.AQUA + "Invitation Sent");
/*     */             }
/*     */             else
/*     */             {
/* 140 */               player.sendMessage(ChatColor.RED + "Only The Party Leader Can Invite People!");
/* 141 */               return false;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 146 */             dp.sendInvite(dpTarget);
/* 147 */             dp.sendMessage(ChatColor.AQUA + "Invitation Sent");
/*     */           }
/*     */ 
/*     */         }
/* 151 */         else if (args[0].equalsIgnoreCase("kick"))
/*     */         {
/* 153 */           PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/* 154 */           if (!dp.isInParty())
/*     */           {
/* 156 */             dp.sendMessage(ChatColor.RED + "You Can Only Use This Command While You're Part Of A Party!");
/* 157 */             return false;
/*     */           }
/* 159 */           if (!dp.isLeader())
/*     */           {
/* 161 */             dp.sendMessage(ChatColor.RED + "Only The Party Leader Can Use This Command!");
/* 162 */             return false;
/*     */           }
/* 164 */           Player target = this.dun.getServer().getPlayer(args[1]);
/* 165 */           if (target == null)
/*     */           {
/* 167 */             player.sendMessage(ChatColor.RED + "Player Not Found!");
/* 168 */             return false;
/*     */           }
/* 170 */           final PVPPlayer dpTarget = PvpHandler.getPvpPlayer(target);
/* 171 */           if (!dp.getParty().isMember(dpTarget))
/*     */           {
/* 173 */             player.sendMessage(ChatColor.RED + "That Player Is Not Part Of Your Party!");
/* 174 */             return false;
/*     */           }
/* 176 */           if (dp.equals(dpTarget))
/*     */           {
/* 178 */             player.sendMessage(ChatColor.RED + "You Cant Kick Yourself!");
/* 179 */             return false;
/*     */           }
/* 181 */           dpTarget.setGhostParty(dp.getParty());
/* 182 */           dpTarget.setParty(null);
/* 183 */           dp.getParty().kick(dpTarget);
/* 184 */           if (dpTarget.usesPartyChat())
/*     */           {
/* 186 */             dpTarget.setPartyChat(false);
/* 187 */             dpTarget.sendMessage(ChatColor.DARK_AQUA + "Party Chat Disabled");
/*     */           }
/* 189 */           Bukkit.getScheduler().scheduleSyncDelayedTask(this.dun, new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 193 */               dpTarget.setGhostParty(null);
/*     */             }
/*     */           }
/*     */           , 200L);
/*     */         }
/*     */       }
/*     */     }
/* 200 */     return true;
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Party.CommandParty
 * JD-Core Version:    0.6.2
 */