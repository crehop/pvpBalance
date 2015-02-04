/*     */ package Party;
/*     */ 
/*     */ import PvpBalance.PVPPlayer;
/*     */ import PvpBalance.PvpHandler;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.ThrownPotion;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.PotionSplashEvent;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class PartyListener
/*     */   implements Listener
/*     */ {
/*     */   @EventHandler
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
/*     */   {
/*  24 */     if (!(event.getEntity() instanceof Player))
/*  25 */       return;
/*  26 */     if ((event.getDamager() instanceof Player))
/*     */     {
/*  28 */       Player player = (Player)event.getEntity();
/*  29 */       Player damager = (Player)event.getDamager();
/*  30 */       PVPPlayer dpDamager = PvpHandler.getPvpPlayer(damager);
/*  31 */       if (dpDamager.isInParty())
/*     */       {
/*  33 */         PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/*  34 */         if (dpDamager.getParty().isMember(dp))
/*     */         {
/*  36 */           event.setCancelled(true);
/*     */         }
/*  38 */         else if (dp.justLeft())
/*     */         {
/*  40 */           if (dp.getGhostParty().isMember(dpDamager))
/*     */           {
/*  42 */             event.setCancelled(true);
/*     */           }
/*     */         }
/*     */       }
/*  46 */       else if (dpDamager.justLeft())
/*     */       {
/*  48 */         PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/*  49 */         if (dpDamager.getGhostParty().isMember(dp))
/*     */         {
/*  51 */           event.setCancelled(true);
/*     */         }
/*  53 */         else if (dp.justLeft())
/*     */         {
/*  55 */           if (dpDamager.getGhostParty().equals(dp.getGhostParty()))
/*     */           {
/*  57 */             event.setCancelled(true);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  62 */     else if ((event.getDamager() instanceof Arrow))
/*     */     {
/*  64 */       Arrow a = (Arrow)event.getDamager();
/*  65 */       if ((a.getShooter() instanceof Player))
/*     */       {
/*  67 */         Player player = (Player)event.getEntity();
/*  68 */         Player damager = (Player)a.getShooter();
/*  69 */         PVPPlayer dpDamager = PvpHandler.getPvpPlayer(damager);
/*  70 */         if (dpDamager.isInParty())
/*     */         {
/*  72 */           PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/*  73 */           if (dpDamager.getParty().isMember(dp))
/*     */           {
/*  75 */             a.remove();
/*  76 */             event.setCancelled(true);
/*     */           }
/*  78 */           else if (dp.justLeft())
/*     */           {
/*  80 */             if (dp.getGhostParty().isMember(dpDamager))
/*     */             {
/*  82 */               a.remove();
/*  83 */               event.setCancelled(true);
/*     */             }
/*     */           }
/*     */         }
/*  87 */         else if (dpDamager.justLeft())
/*     */         {
/*  89 */           PVPPlayer dp = PvpHandler.getPvpPlayer(player);
/*  90 */           if (dpDamager.getGhostParty().isMember(dp))
/*     */           {
/*  92 */             a.remove();
/*  93 */             event.setCancelled(true);
/*     */           }
/*  95 */           else if (dp.justLeft())
/*     */           {
/*  97 */             if (dpDamager.getGhostParty().equals(dp.getGhostParty()))
/*     */             {
/*  99 */               a.remove();
/* 100 */               event.setCancelled(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onPotionSplashEvent(PotionSplashEvent event)
/*     */   {
/* 111 */     if (!isOffencive(event.getEntity()))
/* 112 */       return;
/* 113 */     if (!(event.getEntity().getShooter() instanceof Player))
/* 114 */       return;
/* 115 */     PVPPlayer dp = PvpHandler.getPvpPlayer((Player)event.getEntity().getShooter());
/* 116 */     if (dp.isInParty())
/*     */     {
/* 118 */       for (LivingEntity le : event.getAffectedEntities())
/*     */       {
/* 120 */         if ((le instanceof Player))
/*     */         {
/* 122 */           PVPPlayer temp = PvpHandler.getPvpPlayer((Player)le);
/* 123 */           if (dp.getParty().isMember(temp))
/* 124 */             event.setIntensity(le, 0.0D);
/* 125 */           else if (temp.justLeft())
/*     */           {
/* 127 */             if (temp.getGhostParty().isMember(dp))
/* 128 */               event.setIntensity(le, 0.0D); 
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 132 */     else if (dp.justLeft())
/*     */     {
/* 134 */       for (LivingEntity le : event.getAffectedEntities())
/*     */       {
/* 136 */         if ((le instanceof Player))
/*     */         {
/* 138 */           PVPPlayer temp = PvpHandler.getPvpPlayer((Player)le);
/* 139 */           if (dp.getGhostParty().isMember(temp))
/* 140 */             event.setIntensity(le, 0.0D);
/* 141 */           else if (temp.justLeft())
/*     */           {
/* 143 */             if (temp.getGhostParty().equals(dp.getGhostParty()))
/* 144 */               event.setIntensity(le, 0.0D);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
/* 153 */     PVPPlayer dp = PvpHandler.getPvpPlayer(event.getPlayer());
/* 154 */     if (!dp.usesPartyChat())
/* 155 */       return;
/* 156 */     event.setCancelled(true);
/* 157 */     dp.getParty().sendMessage(dp.getName(), event.getMessage());
/*     */   }
/*     */ 
/*     */   private boolean isOffencive(ThrownPotion tp)
/*     */   {
/* 162 */     for (PotionEffect pe : tp.getEffects())
/*     */     {
/* 164 */       if (pe.getType().equals(PotionEffectType.POISON))
/* 165 */         return true;
/* 166 */       if (pe.getType().equals(PotionEffectType.WEAKNESS))
/* 167 */         return true;
/* 168 */       if (pe.getType().equals(PotionEffectType.SLOW))
/* 169 */         return true;
/* 170 */       if (pe.getType().equals(PotionEffectType.HARM))
/* 171 */         return true;
/*     */     }
/* 173 */     return false;
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Party.PartyListener
 * JD-Core Version:    0.6.2
 */