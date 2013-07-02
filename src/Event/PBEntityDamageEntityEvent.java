package Event;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PBEntityDamageEntityEvent extends PBEntityDamageEvent
{
	private static final HandlerList handlers = new HandlerList();
	private Entity damager;
	
	public PBEntityDamageEntityEvent(Entity entity, Entity damager, int damage, DamageCause cause)
	{
		super(entity, damage, cause);
		this.damager = damager;
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
	
	public Entity getDamager()
	{
		return damager;
	}
}
