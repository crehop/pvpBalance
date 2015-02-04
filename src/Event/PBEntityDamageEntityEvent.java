/*    */ package Event;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*    */ 
/*    */ public class PBEntityDamageEntityEvent extends PBEntityDamageEvent
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private Entity damager;
/*    */ 
/*    */   public PBEntityDamageEntityEvent(Entity entity, Entity damager, int damage, EntityDamageEvent.DamageCause cause)
/*    */   {
/* 16 */     super(entity, damage, cause);
/* 17 */     this.damager = damager;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 23 */     return handlers;
/*    */   }
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 28 */     return handlers;
/*    */   }
/*    */ 
/*    */   public Entity getDamager()
/*    */   {
/* 33 */     return this.damager;
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Event.PBEntityDamageEntityEvent
 * JD-Core Version:    0.6.2
 */