package Util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;

public class WildernessProtection {
	public static boolean checkForWilderness(Player player){
		String status = PlayerCacheUtil.getTownBlockStatus(player, WorldCoord.parseWorldCoord(player)).toString();
		if(status == "UNCLAIMED_ZONE"){
			return true;
		}
		return false;
	}

	public static boolean checkForProtectedBlock(Block block) {
		if(block.getType() == Material.LOG || block.getType() == Material.LOG_2){
			return true;
		}
		else if(block.getType() == Material.LEAVES || block.getType() == Material.LEAVES_2){
			return true;
		}
		else if(block.getType() == Material.GRASS || block.getType() == Material.CACTUS || block.getType() == Material.LONG_GRASS || block.getType() == Material.PACKED_ICE || block.getType() == Material.RED_ROSE || block.getType() == Material.SAND || block.getType() == Material.SNOW_BLOCK || block.getType() == Material.WATER_LILY || block.getType() == Material.ICE || block.getType() == Material.YELLOW_FLOWER || block.getType() == Material.DOUBLE_PLANT || block.getType() == Material.HUGE_MUSHROOM_1 || block.getType() == Material.HUGE_MUSHROOM_2 || block.getType() == Material.MYCEL || block.getType() == Material.SANDSTONE){
			return true;
		}
		return false;
	}

}
