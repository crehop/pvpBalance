package PvpBalance;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PBDamageEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private int damage;
	private Entity entity;
	private Entity damager;
	private DamageCause cause;
	
	public PBDamageEvent(Entity entity, Entity damager, int damage, DamageCause cause)
	{
		this.entity = entity;
		this.damager = damager;
		this.damage = damage;
		this.cause = cause;
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
	
	public Entity getDamager()
	{
		return damager;
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
