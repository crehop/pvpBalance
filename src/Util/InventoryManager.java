/*    */ package Util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ 
/*    */ public class InventoryManager
/*    */ {
/* 18 */   private static HashMap<String, ItemStack> inventories = new HashMap();
/*    */ 
/* 20 */   public static boolean storeInventory(Player player) { ArrayList items = new ArrayList();
/* 21 */     ArrayList armor = new ArrayList();
/* 22 */     Material check = Material.AIR;
/* 23 */     for (ItemStack i : player.getInventory().getArmorContents()) {
/* 24 */       if (i.getType() != Material.AIR) {
/* 25 */         check = i.getType();
/*    */       }
/*    */     }
/* 28 */     if (check != Material.AIR) return false;
/* 29 */     int slot = 0;
/* 30 */     for (ItemStack i : player.getInventory()) {
/* 31 */       if (i != null) {
/* 32 */         inventories.put(player.getName().toString() + "|" + slot, i.clone());
/* 33 */         slot++;
/*    */       }
/*    */     }
/* 36 */     player.getInventory().clear();
/* 37 */     return true; }
/*    */ 
/*    */   public static void getInventory(Player player)
/*    */   {
/* 41 */     player.getInventory().clear();
/* 42 */     int slot = 0;
/* 43 */     for (ItemStack i : player.getInventory()) {
/* 44 */       if (inventories.containsKey(player.getName().toString() + "|" + slot)) {
/* 45 */         ItemStack temp = (ItemStack)inventories.get(player.getName().toString() + "|" + slot);
/* 46 */         player.getInventory().addItem(new ItemStack[] { temp });
/* 47 */         inventories.remove(player.getName().toString() + "|" + slot);
/*    */       }
/* 49 */       slot++;
/*    */     }
/* 51 */     player.updateInventory();
/*    */   }
/*    */ }

/* Location:           G:\MCMYADMIN2\Minecraft\decompiler\PVPBalance.jar
 * Qualified Name:     Util.InventoryManager
 * JD-Core Version:    0.6.2
 */