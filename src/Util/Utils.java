/*    */ package Util;
/*    */ 
/*    */ import DuelZone.Duel;
/*    */ import PvpBalance.PvpBalance;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.BlockIterator;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class Utils
/*    */ {
/*    */   public static Vector getTargetVector(Location shooter, Location target)
/*    */   {
/* 19 */     Location first_location = shooter.add(0.0D, 1.0D, 0.0D);
/* 20 */     Location second_location = target.add(0.0D, 1.0D, 0.0D);
/* 21 */     Vector vector = second_location.toVector().subtract(first_location.toVector());
/* 22 */     return vector;
/*    */   }
/*    */ 
/*    */   public static Duel getDuel(Player player) {
/* 26 */     for (Duel check : PvpBalance.duel) {
/* 27 */       if (Duel.checkContestant(player)) {
/* 28 */         return check;
/*    */       }
/*    */     }
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   public static Entity getTarget(Player player) {
/* 35 */     List nearbyE = player.getNearbyEntities(20.0D, 20.0D, 20.0D);
/* 36 */     ArrayList nearPlayers = new ArrayList();
/* 37 */     for (Entity e : nearbyE)
/*    */     {
/* 39 */       if ((e instanceof Player))
/*    */       {
/* 41 */         nearPlayers.add((Player)e);
/*    */       }
/*    */     }
/* 44 */     Entity target = null;
/* 45 */     BlockIterator bItr = new BlockIterator(player, 20);
/*    */ 
/* 50 */     while (bItr.hasNext())
/*    */     {
/* 53 */       Block block = bItr.next();
/* 54 */       int bx = block.getX();
/* 55 */       int by = block.getY();
/* 56 */       int bz = block.getZ();
/* 57 */       for (Player e : nearPlayers)
/*    */       {
/* 59 */         Location loc = e.getLocation();
/* 60 */         double ex = loc.getX();
/* 61 */         double ey = loc.getY();
/* 62 */         double ez = loc.getZ();
/* 63 */         if ((bx - 0.75D <= ex) && (ex <= bx + 1.75D) && (bz - 0.75D <= ez) && (ez <= bz + 1.75D) && (by - 1 <= ey) && (ey <= by + 2.5D))
/*    */         {
/* 65 */           target = e;
/* 66 */           break;
/*    */         }
/*    */       }
/* 69 */       if (target == null)
/*    */       {
/* 71 */         for (Entity e : nearbyE)
/*    */         {
/* 73 */           Location loc = e.getLocation();
/* 74 */           double ex = loc.getX();
/* 75 */           double ey = loc.getY();
/* 76 */           double ez = loc.getZ();
/* 77 */           if ((bx - 0.75D <= ex) && (ex <= bx + 1.75D) && (bz - 0.75D <= ez) && (ez <= bz + 1.75D) && (by - 1 <= ey) && (ey <= by + 2.5D))
/*    */           {
/* 79 */             target = e;
/* 80 */             break;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 85 */     return target;
/*    */   }
/*    */   public static void teleportToSpawn(Player player) {
/* 88 */     if (player.getWorld().getName().equals("world")) {
/* 89 */       Location spawn = new Location(player.getWorld(), -730.5D, 105.0D, 319.5D);
/* 90 */       player.teleport(spawn);
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Util.Utils
 * JD-Core Version:    0.6.2
 */