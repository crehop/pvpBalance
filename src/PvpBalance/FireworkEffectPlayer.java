/*    */ package PvpBalance;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import org.bukkit.FireworkEffect;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Firework;
/*    */ import org.bukkit.inventory.meta.FireworkMeta;
/*    */ 
/*    */ public class FireworkEffectPlayer
/*    */ {
/* 23 */   private Method world_getHandle = null;
/* 24 */   private Method nms_world_broadcastEntityEffect = null;
/* 25 */   private Method firework_getHandle = null;
/*    */ 
/*    */   public void playFirework(World world, Location loc, FireworkEffect fe)
/*    */     throws Exception
/*    */   {
/* 36 */     Firework fw = (Firework)world.spawn(loc, Firework.class);
/*    */ 
/* 38 */     Object nms_world = null;
/* 39 */     Object nms_firework = null;
/*    */ 
/* 43 */     if (this.world_getHandle == null)
/*    */     {
/* 45 */       this.world_getHandle = getMethod(world.getClass(), "getHandle");
/* 46 */       this.firework_getHandle = getMethod(fw.getClass(), "getHandle");
/*    */     }
/*    */ 
/* 49 */     nms_world = this.world_getHandle.invoke(world, null);
/* 50 */     nms_firework = this.firework_getHandle.invoke(fw, null);
/*    */ 
/* 52 */     if (this.nms_world_broadcastEntityEffect == null)
/*    */     {
/* 54 */       this.nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
/*    */     }
/*    */ 
/* 60 */     FireworkMeta data = fw.getFireworkMeta();
/*    */ 
/* 62 */     data.clearEffects();
/*    */ 
/* 64 */     data.setPower(1);
/*    */ 
/* 66 */     data.addEffect(fe);
/*    */ 
/* 68 */     fw.setFireworkMeta(data);
/*    */ 
/* 73 */     this.nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] { nms_firework, Byte.valueOf(17) });
/*    */ 
/* 75 */     fw.remove();
/*    */   }
/*    */ 
/*    */   private static Method getMethod(Class<?> cl, String method)
/*    */   {
/* 85 */     for (Method m : cl.getMethods()) {
/* 86 */       if (m.getName().equals(method)) {
/* 87 */         return m;
/*    */       }
/*    */     }
/* 90 */     return null;
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     PvpBalance.FireworkEffectPlayer
 * JD-Core Version:    0.6.2
 */