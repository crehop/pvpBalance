package PvpBalance;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PBEntityDamageEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private int damage;
	private Entity entity;
	private DamageCause cause;
	
	public PBEntityDamageEvent(Entity entity, int damage, DamageCause cause)
	{
		this.entity = entity;
		this.damage = damage;
		this.cause = cause;
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
	
	public Entity getEntity()
	{
		return entity;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public DamageCause getCause()
	{
		return cause;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
}
