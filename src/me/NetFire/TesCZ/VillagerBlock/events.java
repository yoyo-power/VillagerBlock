package me.NetFire.TesCZ.VillagerBlock;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.ChatColor;

public class events implements Listener {
	
  private final main plugin;
  private Map<Integer, Integer> count_fire = new HashMap<Integer, Integer>();
  private Map<Integer, Integer> count_lava = new HashMap<Integer, Integer>();
  private Map<Integer, Integer> count_drown = new HashMap<Integer, Integer>();
		  
  public events(main instance) {
    this.plugin = instance;
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onEntityDeath(EntityDeathEvent event) {
    if(plugin.log_deaths && (event.getEntity() instanceof Villager)){
    	Entity ent=event.getEntity();
    	plugin.loguj("Villager died on (" + Math.round(ent.getLocation().getX()) + "; "+ Math.round(ent.getLocation().getY()) + "; "+ Math.round(ent.getLocation().getZ()) + "; " + ent.getWorld().getName() + ")");
    }
  }
  
  @EventHandler(priority = EventPriority.HIGH)
  public void onEntityDamage(EntityDamageEvent ev) {
	  Entity target=ev.getEntity();
	  DamageCause type=ev.getCause();
	  // fire
	  if(plugin.block_fire && plugin.regions_use && plugin.isProtect(target) && (target instanceof Villager) && (type == DamageCause.FIRE || type == DamageCause.FIRE_TICK)){
		 if(plugin.clog){
			 if(count_fire.get(target.getEntityId())==null) count_fire.put(target.getEntityId(), 0);
			 if(count_fire.get(target.getEntityId()) >= 200 || count_fire.get(target.getEntityId())==0){
				 count_fire.put(target.getEntityId(), 1);
				 plugin.loguj("Some fire tried to hit villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
			 }else{
				 count_fire.put(target.getEntityId(), count_fire.get(target.getEntityId())+1);
			 }
		 }
		 ev.setCancelled(true);
	  }
	  if(plugin.block_fire && !plugin.regions_use && (target instanceof Villager) && (type == DamageCause.FIRE || type == DamageCause.FIRE_TICK)){
		 if(plugin.clog){
			 if(count_fire.get(target.getEntityId())==null) count_fire.put(target.getEntityId(), 0);
			 if(count_fire.get(target.getEntityId()) >= 200 || count_fire.get(target.getEntityId())==0){
				 count_fire.put(target.getEntityId(), 1);		 
				 plugin.loguj("Some fire tried to hit villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
			 }else{
				 count_fire.put(target.getEntityId(), count_fire.get(target.getEntityId())+1);
			 }		 
		 }
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
	  // drowing
	  if(plugin.block_drowning && plugin.regions_use && plugin.isProtect(target) && (target instanceof Villager) && type == DamageCause.DROWNING  ){
		 if(plugin.clog){
			 if(count_drown.get(target.getEntityId())==null) count_drown.put(target.getEntityId(), 0);
			 if(count_drown.get(target.getEntityId()) >= 80 || count_drown.get(target.getEntityId())==0){
				 count_drown.put(target.getEntityId(), 1);			 
				 plugin.loguj("Villager tried to drown in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
			 }else{
				 count_drown.put(target.getEntityId(), count_drown.get(target.getEntityId())+1);
			 }		
		 }
		 ev.setCancelled(true);
	  }
	  if(plugin.block_drowning && !plugin.regions_use && (target instanceof Villager) && type == DamageCause.DROWNING ){
		 if(plugin.clog){
			 if(count_drown.get(target.getEntityId())==null) count_drown.put(target.getEntityId(), 0);
			 if(count_drown.get(target.getEntityId()) >= 80 || count_drown.get(target.getEntityId())==0){
				 count_drown.put(target.getEntityId(), 1);			 
				 plugin.loguj("Villager tried to drown on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
			 }else{
				 count_drown.put(target.getEntityId(), count_drown.get(target.getEntityId())+1);
			 }
		 }
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
		 if(plugin.clog){
			 if(count_lava.get(target.getEntityId())==null) count_lava.put(target.getEntityId(), 0);
			 if(count_lava.get(target.getEntityId()) >= 200 || count_lava.get(target.getEntityId())==0){
				 count_lava.put(target.getEntityId(), 1);		 
				 plugin.loguj("Lava tried to hit villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
			 }else{
				 count_lava.put(target.getEntityId(), count_lava.get(target.getEntityId())+1);			 
			 }
		 }
		 ev.setCancelled(true);
	  }
	  if(plugin.block_lava && !plugin.regions_use && (target instanceof Villager) && type == DamageCause.LAVA){
		 if(plugin.clog){
			 if(count_lava.get(target.getEntityId())==null) count_lava.put(target.getEntityId(), 0);
			 if(count_lava.get(target.getEntityId()) >= 200 || count_lava.get(target.getEntityId())==0){
				 count_lava.put(target.getEntityId(), 1);		 
				 plugin.loguj("Lava tried to hit villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
			 }else{
				 count_lava.put(target.getEntityId(), count_lava.get(target.getEntityId())+1);			 
			 }	
		 }
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
		if(!p.isOp() && !p.hasPermission("villagerblock.cankill") && !p.isOp() && plugin.regions_use && !plugin.canPlayerBuild(p, target)){
		  p.sendMessage(ChatColor.DARK_RED + plugin.message_cantkill);
		  if(plugin.clog) plugin.loguj(p.getName() + " tried to hit villager in region on (player pos: " + Math.round(p.getLocation().getX()) + "; "+ Math.round(p.getLocation().getY()) + "; "+ Math.round(p.getLocation().getZ()) + "; " + p.getWorld().getName() + "), (villager pos: " + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		  ev.setCancelled(true);
		}
		if(!plugin.regions_use && !p.hasPermission("villagerblock.cankill") && !p.isOp()){
			if(plugin.clog) plugin.loguj(p.getName() + " tried to hit villager on (player pos: " + Math.round(p.getLocation().getX()) + "; "+ Math.round(p.getLocation().getY()) + "; "+ Math.round(p.getLocation().getZ()) + "; " + p.getWorld().getName() + "), (villager pos: " + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
			p.sendMessage(ChatColor.DARK_RED + plugin.message_cantkill_all);
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
	if(plugin.block_tnt && (player instanceof TNTPrimed) && !(target instanceof Player)){
		if(target instanceof Villager){
			if(plugin.regions_use && plugin.isProtect(target)){
				if(plugin.clog) plugin.loguj("TNT tried to damage villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}
			if(!plugin.regions_use){
				if(plugin.clog) plugin.loguj("TNT tried to damage villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}	
		}
	}	
	if(plugin.block_creeper && (player instanceof Creeper) && !(target instanceof Player)){
		if(target instanceof Villager){
			if(plugin.regions_use && plugin.isProtect(target)){
				if(plugin.clog) plugin.loguj("Creeper tried to damage villager in region on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}
			if(!plugin.regions_use){
				if(plugin.clog) plugin.loguj("Creeper tried to damage villager on (" + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
				ev.setCancelled(true);
			}	
		}
	}	
  } 
}