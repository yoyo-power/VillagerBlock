package me.NetFire.TesCZ.VillagerBlock;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class main extends JavaPlugin {
	public static Logger log = Logger.getLogger("Minecraft");
	
	public Boolean clog = true;
	public Boolean regions_use = true;
	public Boolean block_attack = true;
	public Boolean block_snowball = true;
	public Boolean block_arrow = true;
	public Boolean block_potion = true;
	public Boolean block_zombies = false;
	public Boolean block_fire = true;
	public Boolean block_fall = false;
	public Boolean block_lava = true;
	public Boolean block_egg = true;	
	public String regions_plugin = "worldguard";
	public String message_cantkill = "You don't have permissions for kill a villager.";
	
	public void loguj(String what){
		log.info("[VillagerBlock] " + what);
	}
	public void onEnable(){
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvents(new events(this), this);
	    config_load();
	    loguj("=================================================");
	    loguj("Beta version: residences is not supported now :(");
	    loguj("=================================================");
		loguj("enabled.");
	}
	public void onDisable(){
		loguj("disabled.");
	}
	
   public void config_load()
   {
     getConfig().addDefault("log", true);
     getConfig().addDefault("regions.use", true);
     getConfig().addDefault("regions.plugin", "worldguard");
     getConfig().addDefault("block.attack", true);
     getConfig().addDefault("block.snowball", true);
     getConfig().addDefault("block.arrow", true);
     getConfig().addDefault("block.potion", true);
     getConfig().addDefault("block.zombies", false);
     getConfig().addDefault("block.fire", true);
     getConfig().addDefault("block.fall", false);
     getConfig().addDefault("block.lava", true);
     getConfig().addDefault("block.egg", true);
     getConfig().addDefault("messages.cantkill", "You don't have permissions for kill a villager.");
     // add residence support
     if(!getConfig().getString("regions.plugin").equalsIgnoreCase("worldguard")){
    	 getConfig().set("regions.plugin", "worldguard");
    	 getConfig().set("regions.use", false);
    	 loguj("Plugin '" + getConfig().getString("regions.plugin") + "' is not supported! Regions are now disabled.");
     }
     getConfig().options().copyDefaults(true);
     saveConfig();
     
     clog=getConfig().getBoolean("log");
     regions_use=getConfig().getBoolean("regions.use");
     regions_plugin=getConfig().getString("regions.plugin");
     block_attack=getConfig().getBoolean("block.attack");
     block_snowball=getConfig().getBoolean("block.snowball");
     block_arrow=getConfig().getBoolean("block.arrow");
     block_potion=getConfig().getBoolean("block.potion");
     block_zombies=getConfig().getBoolean("block.zombies");
     block_fire=getConfig().getBoolean("block.fire");
     block_fall=getConfig().getBoolean("block.fall");
     block_lava=getConfig().getBoolean("block.lava");
     block_egg=getConfig().getBoolean("block.egg");   
     message_cantkill=getConfig().getString("messages.cantkill");
  
     loguj("config loaded.");
   }

   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){	
		if(cmd.getName().equalsIgnoreCase("vreload")){ 
			if(sender.isOp() || sender.hasPermission("villagerblock.reload")){
				config_load();
				sender.sendMessage(ChatColor.GREEN + "Config reloaded.");
			}else{
				sender.sendMessage(ChatColor.DARK_RED + "You don't have permissions.");
			}		
			return true;
		}else{
			return false;
		}
   }
   
   public WorldGuardPlugin getWorldGuard() {
	  Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 	  if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	      return null; 
	  }
	  return (WorldGuardPlugin) plugin;
   }
   
   public Boolean canPlayerBuild(Player who, Entity target){
	  if(regions_plugin.equalsIgnoreCase("worldguard")){
		  if(!getWorldGuard().canBuild(who, target.getLocation())){
			  return false;
		  }
	  }
	  if(regions_plugin.equalsIgnoreCase("residence")){
			  
	  }
	  return true;
   }
   
   public Boolean isProtect(Entity target){
		if(regions_plugin.equalsIgnoreCase("worldguard")){
			RegionManager r = getWorldGuard().getRegionManager(target.getWorld());
			com.sk89q.worldedit.Vector v1 = com.sk89q.worldguard.bukkit.BukkitUtil.toVector(target.getLocation());
			ApplicableRegionSet regions = r.getApplicableRegions(v1);
			if(regions.size() != 0) return true;
		}	   
		if(regions_plugin.equalsIgnoreCase("residence")){
			  
		}	   
		return false;
   }
}