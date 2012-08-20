package me.NetFire.TesCZ.VillagerBlock;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class events implements Listener {
  private final main plugin;

  public events(main instance) {
    this.plugin = instance;
  }
  // todo - kontrola na: *eggs*, splashs, fire, apod-.
  // + fix nullpointeru
  
  @EventHandler(priority=EventPriority.HIGH)
  public void EntityDamageByEntityEvent(EntityDamageByEntityEvent ev) {
	Entity player=ev.getDamager();
	Entity target=ev.getEntity();
	if((player instanceof Player) && !(target instanceof Player)){
	  if(target.getType().getName().equalsIgnoreCase("Villager")){
		Player p=(Player) player;
		if(!p.isOp() && !p.hasPermission("villagerblock.cankill") && !plugin.getWorldGuard().canBuild(p, target.getLocation().getBlock().getRelative(0, 0, 0))){
		  p.sendMessage(ChatColor.RED + "Zde nemuzes zabijet vesnicany!");
		  plugin.loguj(p.getName() + " tried to hit villager on (player pos: " + Math.round(p.getLocation().getX()) + "; "+ Math.round(p.getLocation().getY()) + "; "+ Math.round(p.getLocation().getZ()) + "; " + p.getWorld().getName() + "), (villager pos: " + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		  ev.setCancelled(true);
		}
	  }
	}else{
	  if(target.getType().getName().equalsIgnoreCase("Villager") && (player.getType().getName().equalsIgnoreCase("snowball") || player.getType().getName().equalsIgnoreCase("arrow"))){
		 plugin.loguj(player.getType().getName() + " tried to hit villager on (villager pos: " + Math.round(target.getLocation().getX()) + "; "+ Math.round(target.getLocation().getY()) + "; "+ Math.round(target.getLocation().getZ()) + "; " + target.getWorld().getName() + ")");
		 ev.setCancelled(true);
	  }
	}
  }
}