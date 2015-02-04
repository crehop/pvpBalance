/*     */ package PvpBalance;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*     */ import org.bukkit.event.player.PlayerItemHeldEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.potion.Potion;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class PvpListener
/*     */   implements Listener
/*     */ {
/*     */   @EventHandler
/*     */   public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event)
/*     */   {
/*  31 */     ItemStack is = event.getItem();
/*  32 */     if (!is.getType().equals(Material.POTION))
/*  33 */       return;
/*  34 */     Player p = event.getPlayer();
/*  35 */     PVPPlayer dp = PvpHandler.getPvpPlayer(p);
/*  36 */     long time = new Date().getTime();
/*  37 */     if ((time - dp.getDefencePotionTimer()) / 1000L <= PvpHandler.getDefencePotionCD())
/*     */     {
/*  39 */       p.sendMessage(ChatColor.RED + "You Still Have " + ChatColor.AQUA + (15L - (time - dp.getDefencePotionTimer()) / 1000L) + ChatColor.RED + " Seconds Left On That");
/*  40 */       event.setCancelled(true);
/*  41 */       p.updateInventory();
/*     */     }
/*     */     else
/*     */     {
/*  45 */       dp.setDefencePotionTimer(time);
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent event)
/*     */   {
/*  69 */     ItemStack is = event.getPlayer().getItemInHand();
/*  70 */     if ((!event.getAction().equals(Action.RIGHT_CLICK_AIR)) && (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
/*  71 */       return;
/*  72 */     if (!is.getType().equals(Material.POTION))
/*  73 */       return;
/*  74 */     boolean check = false;
/*     */     try
/*     */     {
/*  77 */       Potion.fromItemStack(is);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  81 */       check = true;
/*     */     }
/*  83 */     if (!check)
/*     */     {
/*  85 */       if (!Potion.fromItemStack(is).isSplash())
/*  86 */         return;
/*     */     }
/*  88 */     Player p = event.getPlayer();
/*  89 */     PVPPlayer dp = PvpHandler.getPvpPlayer(p);
/*  90 */     long time = new Date().getTime();
/*  91 */     if ((check) || (isOffencPotion(is)))
/*     */     {
/*  93 */       if ((time - dp.getOffencePotionTimer()) / 1000L <= PvpHandler.getOffencePotionCD())
/*     */       {
/*  95 */         p.sendMessage(ChatColor.RED + "You Still Have " + ChatColor.AQUA + (15L - (time - dp.getOffencePotionTimer()) / 1000L) + ChatColor.RED + " Seconds Left On That");
/*  96 */         event.setCancelled(true);
/*  97 */         p.updateInventory();
/*     */       }
/*     */       else
/*     */       {
/* 101 */         dp.setOffencePotionTimer(time);
/*     */       }
/*     */ 
/*     */     }
/* 106 */     else if ((time - dp.getDefencePotionTimer()) / 1000L <= PvpHandler.getDefencePotionCD())
/*     */     {
/* 108 */       p.sendMessage(ChatColor.RED + "You Still Have " + ChatColor.AQUA + (15L - (time - dp.getDefencePotionTimer()) / 1000L) + ChatColor.RED + " Seconds Left On That");
/* 109 */       event.setCancelled(true);
/* 110 */       p.updateInventory();
/*     */     }
/*     */     else
/*     */     {
/* 114 */       dp.setDefencePotionTimer(time);
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onPlayerItemHeldEvent(PlayerItemHeldEvent event)
/*     */   {
/* 137 */     Player player = event.getPlayer();
/* 138 */     ItemStack is = player.getInventory().getItem(event.getNewSlot());
/* 139 */     if (is == null)
/* 140 */       return;
/* 141 */     if (!is.getType().equals(Material.POTION))
/* 142 */       return;
/* 143 */     if (is.getAmount() > 1)
/*     */     {
/* 145 */       player.getInventory().setItem(event.getNewSlot(), null);
/* 146 */       ItemStack clone = is.clone();
/* 147 */       clone.setAmount(1);
/* 148 */       for (int i = 0; i < is.getAmount() - 1; i++)
/*     */       {
/* 150 */         if (!isFull(player.getInventory()))
/* 151 */           player.getInventory().addItem(new ItemStack[] { clone });
/*     */         else
/* 153 */           player.getWorld().dropItemNaturally(player.getLocation(), clone);
/*     */       }
/* 155 */       player.updateInventory();
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
/*     */   {
/* 163 */     if ((event.getDamage() <= 0.0D) || (event.isCancelled()))
/* 164 */       return;
/* 165 */     Entity en = event.getEntity();
/* 166 */     if (!(en instanceof LivingEntity))
/* 167 */       return;
/* 168 */     LivingEntity le = (LivingEntity)en;
/* 169 */     if (!(le instanceof Player))
/* 170 */       return;
/* 171 */     Date d = new Date();
/* 172 */     en = event.getDamager();
/* 173 */     if ((en instanceof Player))
/*     */     {
/* 175 */       PVPPlayer dp = PvpHandler.getPvpPlayer((Player)en);
/* 176 */       if (!dp.isInPVP())
/*     */       {
/* 178 */         dp.getPlayer().sendMessage(ChatColor.GOLD + "You Are Now In PVP, You Have To Stay Out Of PVP For " + ChatColor.AQUA + PvpHandler.getPvpTimer() + 
/* 179 */           ChatColor.GOLD + " Seconds Before Logging Out Or You Will Die");
/*     */       }
/* 181 */       dp.setPvpTimer(d.getTime());
/* 182 */       dp = PvpHandler.getPvpPlayer((Player)le);
/* 183 */       if (!dp.isInPVP())
/*     */       {
/* 185 */         dp.getPlayer().sendMessage(ChatColor.GOLD + "You Are Now In PVP, You Have To Stay Out Of PVP For " + ChatColor.AQUA + PvpHandler.getPvpTimer() + 
/* 186 */           ChatColor.GOLD + " Seconds Before Logging Out Or You Will Die");
/*     */       }
/* 188 */       dp.setPvpTimer(d.getTime());
/*     */     }
/* 190 */     else if ((en instanceof Arrow))
/*     */     {
/* 192 */       Arrow a = (Arrow)en;
/* 193 */       if ((a.getShooter() instanceof Player))
/*     */       {
/* 195 */         PVPPlayer dp = PvpHandler.getPvpPlayer((Player)a.getShooter());
/* 196 */         if (!dp.isInPVP())
/*     */         {
/* 198 */           dp.getPlayer().sendMessage(ChatColor.GOLD + "You Are Now In PVP, You Have To Stay Out Of PVP For " + ChatColor.AQUA + PvpHandler.getPvpTimer() + 
/* 199 */             ChatColor.GOLD + " Seconds Before Logging Out Or You Will Die");
/*     */         }
/* 201 */         dp.setPvpTimer(d.getTime());
/* 202 */         dp = PvpHandler.getPvpPlayer((Player)le);
/* 203 */         if (!dp.isInPVP())
/*     */         {
/* 205 */           dp.getPlayer().sendMessage(ChatColor.GOLD + "You Are Now In PVP, You Have To Stay Out Of PVP For " + ChatColor.AQUA + PvpHandler.getPvpTimer() + 
/* 206 */             ChatColor.GOLD + " Seconds Before Logging Out Or You Will Die");
/*     */         }
/* 208 */         dp.setPvpTimer(d.getTime());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isOffencPotion(ItemStack is)
/*     */   {
/* 215 */     Potion p = Potion.fromItemStack(is);
/* 216 */     for (PotionEffect pe : p.getEffects())
/*     */     {
/* 218 */       if (pe.getType().equals(PotionEffectType.POISON))
/* 219 */         return true;
/* 220 */       if (pe.getType().equals(PotionEffectType.WEAKNESS))
/* 221 */         return true;
/* 222 */       if (pe.getType().equals(PotionEffectType.SLOW))
/* 223 */         return true;
/* 224 */       if (pe.getType().equals(PotionEffectType.HARM))
/* 225 */         return true;
/*     */     }
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isFull(PlayerInventory inv)
/*     */   {
/* 232 */     for (ItemStack is : inv.getContents())
/*     */     {
/* 234 */       if (is == null)
/* 235 */         return false;
/* 236 */       if (is.getType().equals(Material.AIR))
/* 237 */         return false;
/*     */     }
/* 239 */     return true;
/*     */   }
/*     */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.PvpListener
 * JD-Core Version:    0.6.2
 */