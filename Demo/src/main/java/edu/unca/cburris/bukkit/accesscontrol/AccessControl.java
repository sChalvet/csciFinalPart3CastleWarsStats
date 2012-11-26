package edu.unca.cburris.bukkit.accesscontrol;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import edu.unca.cburris.bukkit.accesscontrol.util.ListStore;
import edu.unca.cburris.bukkit.commands.BanExecutor;

/*
 * This is the main class of the sample plug-in
 */
public class AccessControl extends JavaPlugin {
	/*
	 * This is called when your plug-in is enabled
	 */
	public ListStore bannedPlayers;
	
	
	@Override
	public void onEnable() {
		
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		
		(new File(pluginFolder)).mkdirs();
		
		this.bannedPlayers = new ListStore(new File(pluginFolder + File.separator + "bannedPlayers.txt"));
		this.bannedPlayers.load();
		

		

		// set the command executor for sample
		this.getCommand("demo").setExecutor(new ACCommandExecutor(this));
		this.getCommand("ban").setExecutor(new BanExecutor(this));
		this.getServer().getPluginManager().registerEvents(new ACListener(this), this);
	}

	/*
	 * This is called when your plug-in shuts down
	 */
	@Override
	public void onDisable() {
		saveDefaultConfig(); 
		this.bannedPlayers.save();
		//log.info("FirstPlugin has been disabled")
	}

}
