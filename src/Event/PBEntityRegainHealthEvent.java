package Event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class PBEntityRegainHealthEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private int amount;
	private final Entity entity;
	private final RegainReason reason;
	
	public PBEntityRegainHealthEvent(Entity entity, int amount, RegainReason reason)
	{
		this.entity = entity;
		this.amount = amount;
		this.reason = reason;
		cancelled = false;
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

	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}

	@Override
	public void setCancelled(boolean value)
	{
		cancelled = value;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public Entity getEntity()
	{
		return entity;
	}
	
	public EntityType getEntityType()
	{
		return entity.getType();
	}
	
	public RegainReason getReason()
	{
		return reason;
	}
	
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
}
