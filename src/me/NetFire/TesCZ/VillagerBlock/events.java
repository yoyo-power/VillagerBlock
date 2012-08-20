package me.NetFire.TesCZ.VillagerBlock;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.ChatColor;

public class events implements Listener {
  private final main plugin;
  
  public events(main instance) {
    this.plugin = instance;
  }
  
  @EventHandler(priority = EventPriority.HIGH)
  public void onEntityDamage(EntityDamageEvent ev) {
	  Entity target=ev.getEntity();
	  DamageCause type=ev.getCause();
	  // fire
	  if(plugin.block_fire && plugin.regions_use && plugin.isProtect(target) && (target instanceof Villager) && (type == DamageCause.FIRE || type == DamageCause.FIRE_TICK)){
		 if(plugin.clog) plugin.loguj("Some fire tried to hit villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		 ev.setCancelled(true);
	  }
	  if(plugin.block_fire && !plugin.regions_use && (target instanceof Villager) && (type == DamageCause.FIRE || type == DamageCause.FIRE_TICK)){
		 if(plugin.clog) plugin.loguj("Some fire tried to hit villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		 ev.setCancelled(true);
	  }
	  // fall
	  if(plugin.block_fire && plugin.regions_use && plugin.isProtect(target) && (target instanceof Villager) && type == DamageCause.FALL ){
		 if(plugin.clog) plugin.loguj("Villager tried to fall in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		 ev.setCancelled(true);
	  }
	  if(plugin.block_fire && !plugin.regions_use && (target instanceof Villager) && type == DamageCause.FALL){
		 if(plugin.clog) plugin.loguj("Villager tried to fall on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		 ev.setCancelled(true);
	  }
  }
  
  @EventHandler(priority=EventPriority.HIGH)
  public void onPotionSplash(PotionSplashEvent event) {
	  for (LivingEntity et : event.getAffectedEntities()) {
		  if(et instanceof Villager){
			 if(plugin.block_potion && plugin.regions_use && plugin.isProtect(et)){
				 if(plugin.clog) plugin.loguj("Some splash potion tried to hit villager in protect on (" + Math.round(et.getLocation().getX()) + "; "+ Math.round(et.getLocation().getY()) + "; "+ Math.round(et.getLocation().getZ()) + "; " + et.getWorld().getName() + ")");
				 event.setCancelled(true);
			 }
			 if(plugin.block_potion && !plugin.regions_use){
				 if(plugin.clog) plugin.loguj("Some splash potion tried to hit villager on (" + Math.round(et.getLocation().getX()) + "; "+ Math.round(et.getLocation().getY()) + "; "+ Math.round(et.getLocation().getZ()) + "; " + et.getWorld().getName() + ")");
				 event.setCancelled(true);
			 }			  
		  }
	  }
  }
  
  @EventHandler(priority=EventPriority.HIGH)
  public void onEntityDamageByBlock(EntityDamageByBlockEvent ev) {
      Entity target = ev.getEntity();
      DamageCause type = ev.getCause();
	  if(plugin.block_lava && plugin.regions_use && plugin.isProtect(target) && (target instanceof Villager) && type == DamageCause.LAVA ){
		 if(plugin.clog) plugin.loguj("Lava tried to hit villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		 ev.setCancelled(true);
	  }
	  if(plugin.block_lava && !plugin.regions_use && (target instanceof Villager) && type == DamageCause.LAVA){
		 if(plugin.clog) plugin.loguj("Lava tried to hit villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		 ev.setCancelled(true);
	  }     
  }
	  
  @EventHandler(priority=EventPriority.HIGH)
  public void EntityDamageByEntityEvent(EntityDamageByEntityEvent ev) {
	Entity player=ev.getDamager();
	Entity target=ev.getEntity();
	if(plugin.block_attack && (player instanceof Player) && !(target instanceof Player)){
	  if(target instanceof Villager){
		Player p=(Player) player;
		if(!p.isOp() && !p.hasPermission("villagerblock.cankill") && plugin.regions_use && !plugin.canPlayerBuild(p, target)){
		  p.sendMessage(ChatColor.DARK_RED + plugin.message_cantkill);
		  if(plugin.clog) plugin.loguj(p.getName() + " tried to hit villager in region on (player pos: " + Math.round(p.getLocation().getX()) + "; "+ Math.round(p.getLocation().getY()) + "; "+ Math.round(p.getLocation().getZ()) + "; " + p.getWorld().getName() + "), (villager pos: " + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		  ev.setCancelled(true);
		}
		if(!plugin.regions_use){
			if(plugin.clog) plugin.loguj(p.getName() + " tried to hit villager on (player pos: " + Math.round(p.getLocation().getX()) + "; "+ Math.round(p.getLocation().getY()) + "; "+ Math.round(p.getLocation().getZ()) + "; " + p.getWorld().getName() + "), (villager pos: " + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
			ev.setCancelled(true);
		}
	  }
	}
	if(plugin.block_snowball && (player instanceof Snowball) && !(target instanceof Player)){
		if(target instanceof Villager){
			if(plugin.regions_use && plugin.isProtect(target)){
				if(plugin.clog) plugin.loguj("Some snowball tried to hit villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}
			if(!plugin.regions_use){
				if(plugin.clog) plugin.loguj("Some snowball tried to hit villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}
		}
	}
	if(plugin.block_arrow && (player instanceof Arrow) && !(target instanceof Player)){
		if(target instanceof Villager){
			if(plugin.regions_use && plugin.isProtect(target)){
				if(plugin.clog) plugin.loguj("Some arrow tried to hit villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}
			if(!plugin.regions_use){
				if(plugin.clog) plugin.loguj("Some arrow tried to hit villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}	
		}
	}
	if(plugin.block_zombies && (player instanceof Zombie) && !(target instanceof Player)){
		if(target instanceof Villager){
			if(plugin.regions_use && plugin.isProtect(target)){
				if(plugin.clog) plugin.loguj("Some zombie tried to hit villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}
			if(!plugin.regions_use){
				if(plugin.clog) plugin.loguj("Some zombie tried to hit villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}	
		}
	}
	if(plugin.block_egg && (player instanceof Egg) && !(target instanceof Player)){
		if(target instanceof Villager){
			if(plugin.regions_use && plugin.isProtect(target)){
				if(plugin.clog) plugin.loguj("Some egg tried to hit villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}
			if(!plugin.regions_use){
				if(plugin.clog) plugin.loguj("Some egg tried to hit villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}	
		}
	}
  } 
}