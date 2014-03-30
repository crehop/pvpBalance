package Util;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryManager{
	private static HashMap<String, ItemStack> inventories = new HashMap<>();
    public static boolean storeInventory(Player player){
    	ArrayList<ItemStack> items = new ArrayList<>();
    	ArrayList<ItemStack> armor = new ArrayList<>();
    	Material check = Material.AIR;
    	for(ItemStack i:player.getInventory().getArmorContents()){
    		if(i.getType() != Material.AIR){
    			check = i.getType();
    		}
    	}
    	if(check != Material.AIR) return false;
    	int slot = 0;
    	for(ItemStack i:player.getInventory()){
    		if(i != null){
    			inventories.put(player.getName().toString() +"|" + slot  , i.clone() );
    			slot++;
    		}
    	}
	    player.getInventory().clear();
	    return true;
    }
    @SuppressWarnings("deprecation")
	public static void getInventory(Player player){
    	player.getInventory().clear();
    	int slot = 0;
    	for(ItemStack i:player.getInventory()){
    		if(inventories.containsKey(player.getName().toString() +"|" + slot)){
    			ItemStack temp = inventories.get(player.getName().toString() +"|" + slot);
    			player.getInventory().addItem(temp);
    	    	inventories.remove(player.getName().toString() +"|" + slot);
    		}
    		slot++;
    	}
    	player.updateInventory();
    }
}