package me.NetFire.TesCZ.VillagerBlock;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class main extends JavaPlugin {
	public static Logger log = Logger.getLogger("Minecraft");
	
	public void loguj(String what){
		log.info("[VillagerBlock] " + what);
	}
	public void onEnable(){
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvents(new events(this), this);
		loguj("enabled.");
	}
	public void onDisable(){
		loguj("disabled.");
	}
	public WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; 
	    }
	    return (WorldGuardPlugin) plugin;
	}
}