package Event;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
//test comments
public class PBEntityDeathEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	private final LivingEntity entity;
	private final List<ItemStack> drops;
	private int dropExp;
	
	public PBEntityDeathEvent(LivingEntity le, List<ItemStack> drops, int dropExp)
	{
		entity = le;
		this.drops = drops;
		this.dropExp = dropExp;
	}
	
	public LivingEntity getEntity()
	{
		return entity;
	}
	
	public List<ItemStack> getDrops()
	{
		return drops;
	}
	
	public int getDropExp()
	{
		return dropExp;
	}
	
	public void setDropExp(int exp)
	{
		dropExp = exp;
	}
	
	@Override
	public HandlerList getHandlers()
	{	
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
	    return handlers;
	}
}
