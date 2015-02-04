/*    */ package Event;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
/*    */ 
/*    */ public class PBEntityRegainHealthEvent extends Event
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private int amount;
/*    */   private final Entity entity;
/*    */   private final EntityRegainHealthEvent.RegainReason reason;
/*    */ 
/*    */   public PBEntityRegainHealthEvent(Entity entity, int amount, EntityRegainHealthEvent.RegainReason reason)
/*    */   {
/* 20 */     this.entity = entity;
/* 21 */     this.amount = amount;
/* 22 */     this.reason = reason;
/* 23 */     this.cancelled = false;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 29 */     return handlers;
/*    */   }
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 34 */     return handlers;
/*    */   }
/*    */ 
/*    */   public boolean isCancelled()
/*    */   {
/* 40 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   public void setCancelled(boolean value)
/*    */   {
/* 46 */     this.cancelled = value;
/*    */   }
/*    */ 
/*    */   public int getAmount()
/*    */   {
/* 51 */     return this.amount;
/*    */   }
/*    */ 
/*    */   public Entity getEntity()
/*    */   {
/* 56 */     return this.entity;
/*    */   }
/*    */ 
/*    */   public EntityType getEntityType()
/*    */   {
/* 61 */     return this.entity.getType();
/*    */   }
/*    */ 
/*    */   public EntityRegainHealthEvent.RegainReason getReason()
/*    */   {
/* 66 */     return this.reason;
/*    */   }
/*    */ 
/*    */   public void setAmount(int amount)
/*    */   {
/* 71 */     this.amount = amount;
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Event.PBEntityRegainHealthEvent
 * JD-Core Version:    0.6.2
 */