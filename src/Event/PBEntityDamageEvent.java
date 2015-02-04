/*    */ package Event;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*    */ 
/*    */ public class PBEntityDamageEvent extends Event
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private int damage;
/*    */   private Entity entity;
/*    */   private EntityDamageEvent.DamageCause cause;
/*    */ 
/*    */   public PBEntityDamageEvent(Entity entity, int damage, EntityDamageEvent.DamageCause cause)
/*    */   {
/* 19 */     this.entity = entity;
/* 20 */     this.damage = damage;
/* 21 */     this.cause = cause;
/* 22 */     this.cancelled = false;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 28 */     return handlers;
/*    */   }
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 33 */     return handlers;
/*    */   }
/*    */ 
/*    */   public boolean isCancelled()
/*    */   {
/* 39 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   public void setCancelled(boolean value)
/*    */   {
/* 45 */     this.cancelled = value;
/*    */   }
/*    */ 
/*    */   public Entity getEntity()
/*    */   {
/* 50 */     return this.entity;
/*    */   }
/*    */ 
/*    */   public int getDamage()
/*    */   {
/* 55 */     return this.damage;
/*    */   }
/*    */ 
/*    */   public EntityDamageEvent.DamageCause getCause()
/*    */   {
/* 60 */     return this.cause;
/*    */   }
/*    */ 
/*    */   public void setDamage(int damage)
/*    */   {
/* 65 */     this.damage = damage;
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Event.PBEntityDamageEvent
 * JD-Core Version:    0.6.2
 */