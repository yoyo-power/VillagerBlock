package me.NetFire.TesCZ.VillagerBlock;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class main extends JavaPlugin {
	public static Logger log = Logger.getLogger("Minecraft");
	
	public Boolean clog = false;
	public Boolean log_deaths = true;
	public Boolean regions_use = true;
	public Boolean block_attack = true;
	public Boolean block_snowball = true;
	public Boolean block_arrow = true;
	public Boolean block_potion = true;
	public Boolean block_zombies = false;
	public Boolean block_fire = true;
	public Boolean block_fall = true;
	public Boolean block_lava = true;
	public Boolean block_egg = true;	
	public Boolean block_tnt = true;
	public Boolean block_drowning = true;
	public Boolean block_creeper = true;
	public Boolean block_trade = false;
	public Boolean block_trade_protect = true;
	public String regions_plugin = "worldguard";
	public String message_cantkill = "You don't have permissions to kill a villager.";
	public String message_cantkill_all = "Killing villagers is disabled on this server.";
	public String message_canttrade = "You can't trade with villagers..";
	public String message_canttrade_protect = "You can't trade with villagers in this area!";

	public void loguj(String what){
		log.info("[VillagerBlock] " + what);
	}
	public void onEnable(){
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvents(new events(this), this);
	    config_load();
		loguj("enabled.");
		
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		   
		}
	}
	public void onDisable(){
		loguj("disabled.");
	}
	
   public void config_load()
   {
	 reloadConfig();
     getConfig().addDefault("log", false);
     getConfig().addDefault("log_deaths", true);
     getConfig().addDefault("regions.use", true);
     getConfig().addDefault("regions.plugin", "worldguard");
     getConfig().addDefault("block.attack", true);
     getConfig().addDefault("block.snowball", true);
     getConfig().addDefault("block.arrow", true);
     getConfig().addDefault("block.potion", true);
     getConfig().addDefault("block.zombies", true);
     getConfig().addDefault("block.fire", true);
     getConfig().addDefault("block.fall", false);
     getConfig().addDefault("block.lava", true);
     getConfig().addDefault("block.egg", true);
     getConfig().addDefault("block.tnt", true);
     getConfig().addDefault("block.drowning", getConfig().get("block.drowing", true)); 
     getConfig().addDefault("block.creeper", true);
     getConfig().addDefault("trade_block.use", false);
     getConfig().addDefault("trade_block.block_only_in_protects", true);
     getConfig().addDefault("messages.cantkill", message_cantkill);
     getConfig().addDefault("messages.cantkill_all", message_cantkill_all);
     getConfig().addDefault("messages.canttrade", message_canttrade);
     getConfig().addDefault("messages.canttrade_protect", message_canttrade_protect);
     if(!getConfig().getString("regions.plugin").equalsIgnoreCase("worldguard") && !getConfig().getString("regions.plugin").equalsIgnoreCase("residence")){
    	 getConfig().set("regions.plugin", "worldguard");
    	 getConfig().set("regions.use", false);
    	 loguj("Plugin '" + getConfig().getString("regions.plugin") + "' is not supported! Regions are now disabled.");
     }
     
     getConfig().options().copyDefaults(true);
     saveConfig();
     
     clog=getConfig().getBoolean("log");
     log_deaths=getConfig().getBoolean("log_deaths");
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
     block_tnt=getConfig().getBoolean("block.tnt");
     block_drowning=getConfig().getBoolean("block.drowning");    
     block_creeper=getConfig().getBoolean("block.creeper");   
     block_trade=getConfig().getBoolean("trade_block.use");         
     block_trade_protect=getConfig().getBoolean("trade_block.block_only_in_protects");         
     message_cantkill=getConfig().getString("messages.cantkill");
     message_cantkill_all=getConfig().getString("messages.cantkill_all");
     message_canttrade=getConfig().getString("messages.canttrade");
     message_canttrade_protect=getConfig().getString("messages.canttrade_protect");
     if(!regions_use) block_trade_protect=false;

     PluginManager pm = getServer().getPluginManager();
     if(regions_use && regions_plugin.equalsIgnoreCase("worldguard")){
    	 Plugin p = pm.getPlugin("WorldGuard");
    	 if(p==null){
    		 log.warning("[VillagerBlock] WorldGuard plugin is not found on this server, disabling REGIONS_USE! But plugin is still running...");
        	 getConfig().set("regions.use", false);
        	 block_trade_protect=false;
        	 regions_use=false;
        	 saveConfig();
    	 }
     }     
     if(regions_use && regions_plugin.equalsIgnoreCase("residence")){
    	 Plugin p = pm.getPlugin("Residence");
    	 if(p==null){
    		 log.warning("[VillagerBlock] Residence plugin is not found on this server, disabling REGIONS_USE! But plugin is still running...");
        	 getConfig().set("regions.use", false);
        	 block_trade_protect=false;
        	 regions_use=false;
        	 saveConfig();
    	 }
     }              
     
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
			Location loc = target.getLocation();
			ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
			if(res==null){
				return true;
			}else{
				ResidencePermissions perms = res.getPermissions();				 
				boolean hasPermission = perms.playerHas(who.getName(), "build", true);
				if(!hasPermission){
					return false;
				}    
			}
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
			Location loc = target.getLocation();
			ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
			if(res != null) return true;
		}	   
		return false;
   }
}