/*    */ package Event;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class PBEntityDeathEvent extends Event
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final LivingEntity entity;
/*    */   private final List<ItemStack> drops;
/*    */   private int dropExp;
/*    */ 
/*    */   public PBEntityDeathEvent(LivingEntity le, List<ItemStack> drops, int dropExp)
/*    */   {
/* 19 */     this.entity = le;
/* 20 */     this.drops = drops;
/* 21 */     this.dropExp = dropExp;
/*    */   }
/*    */ 
/*    */   public LivingEntity getEntity()
/*    */   {
/* 26 */     return this.entity;
/*    */   }
/*    */ 
/*    */   public List<ItemStack> getDrops()
/*    */   {
/* 31 */     return this.drops;
/*    */   }
/*    */ 
/*    */   public int getDropExp()
/*    */   {
/* 36 */     return this.dropExp;
/*    */   }
/*    */ 
/*    */   public void setDropExp(int exp)
/*    */   {
/* 41 */     this.dropExp = exp;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 47 */     return handlers;
/*    */   }
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 52 */     return handlers;
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Event.PBEntityDeathEvent
 * JD-Core Version:    0.6.2
 */